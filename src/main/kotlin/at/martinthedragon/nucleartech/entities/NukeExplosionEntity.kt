package at.martinthedragon.nucleartech.entities

import at.martinthedragon.nucleartech.DamageSources
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.world.ChunkRadiation
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.passive.CatEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.IPacket
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.RayTraceContext
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.fml.network.NetworkHooks
import java.util.*
import kotlin.math.ceil
import kotlin.math.min
import kotlin.math.pow

class NukeExplosionEntity(entityType: EntityType<NukeExplosionEntity>, world: World) : Entity(entityType, world) {
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
            remove()
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
            else -> remove()
        }
    }

    override fun remove(keepData: Boolean) {
        super.remove(keepData)
        if (nukeCloudEntityUUID != null) {
            ((level as? ServerWorld)?.getEntity(nukeCloudEntityUUID!!) as? NukeCloudEntity)?.finish()
        }
        if (hasFallout) {
            val fallout = FalloutRainEntity(EntityTypes.falloutRainEntity.get(), level)
            fallout.moveTo(this@NukeExplosionEntity.position())
            fallout.setScale(((this@NukeExplosionEntity.length * 1.8 + extraFallout) * NuclearConfig.explosions.falloutRange.get() / 100.0).toInt())
            level.addFreshEntity(fallout)
        }
    }

    override fun defineSynchedData() {}

    override fun readAdditionalSaveData(nbt: CompoundNBT) {
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

    override fun addAdditionalSaveData(nbt: CompoundNBT) {
        nbt.putInt("Strength", strength)
        nbt.putInt("Speed", speed)
        nbt.putInt("Length", length)
        nbt.putBoolean("HasFallout", hasFallout)
        nbt.putInt("ExtraFallout", extraFallout)
        if (explosion != null) nbt.put("ExplosionData", explosion!!.serialize())
        if (nukeCloudEntityUUID != null) nbt.putUUID("NukeCloudEntity", nukeCloudEntityUUID!!)
    }

    override fun getAddEntityPacket(): IPacket<*> = NetworkHooks.getEntitySpawningPacket(this)

    companion object {
        fun dealDamage(world: World, pos: Vector3d, radius: Double, maxDamage: Float = 250F) {
            val entities = world.getEntities(null,
                AxisAlignedBB(pos.x, pos.y, pos.z, pos.x, pos.y, pos.z).inflate(radius, radius, radius),
                Companion::canExplode
            )

            for (entity in entities) {
                val distance = pos.distanceTo(entity.position())
                if (distance <= radius) {
                    world.clip(RayTraceContext(pos, entity.position(), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, null))
                    val damage = maxDamage * (radius - distance) /  radius
                    entity.hurt(DamageSources.nuclearBlast, damage.toFloat())
                    entity.setSecondsOnFire(5)

                    val knockBack = Vector3d(
                        entity.x - pos.x,
                        entity.y + entity.eyeHeight - pos.y,
                        entity.z - pos.z
                    ).normalize()

                    entity.deltaMovement = knockBack
                }
            }
        }

        fun canExplode(entity: Entity): Boolean = when {
            entity is PlayerEntity && entity.isCreative -> false
            entity is CatEntity -> false
            else -> true
        }

        fun create(
            world: World,
            pos: Vector3d,
            strength: Int,
            hasFallout: Boolean = true,
            extraFallout: Int = 0,
            muted: Boolean = false,
            withVfx: Boolean = true
        ): Boolean {
            return if (world.isClientSide) false
            else {
                val cloudEntityUUID = if (withVfx) NukeCloudEntity.create(world, pos, strength * .0025F, isMuted = muted) else null
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
