package at.martinthedragon.nucleartech.entities

import at.martinthedragon.nucleartech.DamageSources
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.world.ChunkRadiation
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.animal.Cat
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import net.minecraftforge.network.NetworkHooks
import java.util.*
import kotlin.math.ceil
import kotlin.math.min
import kotlin.math.pow

class NukeExplosionEntity(entityType: EntityType<NukeExplosionEntity>, world: Level) : Entity(entityType, world) {
    var strength = 0
    var speed = 0
    var length = 0
    var hasFallout = true
    var extraFallout = 0

    // TODO maybe ocean detonations can be made less expensive if the biomes are checked
    private var explosion: NukeExplosionRay? = null
    private var nukeCloudEntityUUID: UUID? = null

    override fun tick() {
        super.tick()

        if (level.isClientSide) return

        if (strength == 0) {
            remove(RemovalReason.DISCARDED)
            return
        }

        if (hasFallout && explosion != null) {
            val rad = min(length * .5F * length.toFloat().pow(1.5F) / 35F, 15_000F) * .25F
            ChunkRadiation.incrementRadiation(level, blockPosition(), rad)
        }

        dealDamage(level, position(), (length * 2).toDouble())

        if (explosion == null) explosion = NukeExplosionRay(level, blockPosition(), strength, length)

        when {
            !explosion!!.initialized -> explosion!!.collectTips(speed * 10)
            explosion!!.tipsCount > 0 -> explosion!!.processTips(NuclearConfig.explosions.explosionSpeed.get())
            else -> remove(RemovalReason.DISCARDED)
        }
    }

    override fun remove(reason: RemovalReason) {
        super.remove(reason)
        if (nukeCloudEntityUUID != null) {
            ((level as? ServerLevel)?.getEntity(nukeCloudEntityUUID!!) as? MushroomCloudEntity)?.finish()
        }
        if (hasFallout) {
            val fallout = FalloutRainEntity(EntityTypes.falloutRainEntity.get(), level)
            fallout.moveTo(this@NukeExplosionEntity.position())
            fallout.setScale(((this@NukeExplosionEntity.length * 1.8 + extraFallout) * NuclearConfig.explosions.falloutRange.get() / 100.0).toInt())
            level.addFreshEntity(fallout)
        }
    }

    override fun defineSynchedData() {}

    override fun readAdditionalSaveData(nbt: CompoundTag) {
        strength = nbt.getInt("Strength")
        speed = nbt.getInt("Speed")
        length = nbt.getInt("Length")
        hasFallout = nbt.getBoolean("HasFallout")
        extraFallout = nbt.getInt("ExtraFallout")
        val explosionNbt = nbt.getCompound("ExplosionData")
        if (!explosionNbt.isEmpty) explosion = NukeExplosionRay.deserialize(level, explosionNbt)
        if (nbt.hasUUID("NukeCloudEntity")) {
            nukeCloudEntityUUID = nbt.getUUID("NukeCloudEntity")
        }
    }

    override fun addAdditionalSaveData(nbt: CompoundTag) {
        nbt.putInt("Strength", strength)
        nbt.putInt("Speed", speed)
        nbt.putInt("Length", length)
        nbt.putBoolean("HasFallout", hasFallout)
        nbt.putInt("ExtraFallout", extraFallout)
        if (explosion != null) nbt.put("ExplosionData", explosion!!.serialize())
        if (nukeCloudEntityUUID != null) nbt.putUUID("NukeCloudEntity", nukeCloudEntityUUID!!)
    }

    override fun getAddEntityPacket(): Packet<*> = NetworkHooks.getEntitySpawningPacket(this)

    companion object {
        fun dealDamage(world: Level, pos: Vec3, radius: Double, maxDamage: Float = 250F) {
            val entities = world.getEntities(null,
                AABB(pos.x, pos.y, pos.z, pos.x, pos.y, pos.z).inflate(radius, radius, radius),
                Companion::canExplode
            )

            for (entity in entities) {
                val distance = pos.distanceTo(entity.position())
                if (distance <= radius) {
                    world.clip(ClipContext(pos, entity.position(), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null))
                    val damage = maxDamage * (radius - distance) /  radius
                    entity.hurt(DamageSources.nuclearBlast, damage.toFloat())
                    entity.setSecondsOnFire(5)

                    val knockBack = Vec3(
                        entity.x - pos.x,
                        entity.y + entity.eyeHeight - pos.y,
                        entity.z - pos.z
                    ).normalize()

                    entity.deltaMovement = knockBack
                }
            }
        }

        fun canExplode(entity: Entity): Boolean = when {
            entity is Player && entity.isCreative -> false
            entity is Cat -> false
            else -> true
        }

        fun create(
            world: Level,
            pos: Vec3,
            strength: Int,
            hasFallout: Boolean = true,
            extraFallout: Int = 0,
            muted: Boolean = false,
            withVfx: Boolean = true
        ): Boolean {
            return if (world.isClientSide) false
            else {
                val cloudEntityUUID = if (withVfx) MushroomCloudEntity.create(world, pos, strength * .0025F, isMuted = muted) else null
                val explosionEntity = NukeExplosionEntity(EntityTypes.nukeExplosionEntity.get(), world).apply {
                    this@apply.strength = strength
                    speed = ceil(100000F / strength).toInt()
                    length = strength / 2
                    this.hasFallout = hasFallout
                    this.extraFallout = extraFallout
                    nukeCloudEntityUUID = cloudEntityUUID
                    moveTo(pos)
                }
                world.addFreshEntity(explosionEntity)
            }
        }
    }
}
