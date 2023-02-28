package at.martinthedragon.nucleartech.entity

import at.martinthedragon.nucleartech.SoundEvents
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.explosion.ExplosionLarge
import at.martinthedragon.nucleartech.explosion.ExplosionVNT
import at.martinthedragon.nucleartech.particle.ModParticles
import at.martinthedragon.nucleartech.world.DamageSources
import at.martinthedragon.nucleartech.world.gen.WorldGen
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntitySelector
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MoverType
import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import net.minecraftforge.network.NetworkHooks
import java.util.*
import kotlin.random.asKotlinRandom

class Meteor(entityType: EntityType<Meteor>, level: Level) : Entity(entityType, level) {
    constructor(level: Level) : this(EntityTypes.meteor.get(), level)

    init {
        noCulling = true
    }

    override fun tick() {
        super.tick()

        val level = level

        setDeltaMovement(deltaMovement.x, (deltaMovement.y - 0.03).coerceAtLeast(-2.5), deltaMovement.z)
        move(MoverType.SELF, deltaMovement)

        if (!level.isClientSide && onGround) {
            level as ServerLevel
            ExplosionVNT.createStandard(level, position(), 5F + random.nextFloat(), this).apply {
                blockProcessor = ExplosionVNT.BlockProcessor.Default(dropChanceMutator = ExplosionVNT.DropChanceMutator.Default(0F), blockMutator = ExplosionVNT.BlockMutator.fire())
                syncer = null
            }.explode()

            val particleCount = NuclearConfig.client.meteorTrailsPerTick.get() * 10
            if (particleCount > 0) {
                ExplosionLarge.spawnCloud(level, position().add(0.0, 5.0, 0.0), particleCount)
                ExplosionLarge.spawnCloud(level, position().add(5.0, 0.0, 0.0), particleCount)
                ExplosionLarge.spawnCloud(level, position().add(-5.0, 0.0, 0.0), particleCount)
                ExplosionLarge.spawnCloud(level, position().add(0.0, 0.0, 5.0), particleCount)
                ExplosionLarge.spawnCloud(level, position().add(0.0, 0.0, -5.0), particleCount)
            }

            for (entity in level.getEntities(this, AABB(blockPosition()).inflate(7.5), EntitySelector.ENTITY_STILL_ALIVE)) {
                entity.hurt(DamageSources.meteorite, 1000F)
            }

            placeRandomMeteorite(level, blockPosition(), random)
            level.playSound(null, x, y, z, SoundEvents.meteorImpact.get(), SoundSource.BLOCKS, 10_000F, .5F + random.nextFloat() * .1F)

            discard()
        }

        if (level.isClientSide) {
            val particleSourcePos = position().subtract(deltaMovement)
            for (i in 0 until NuclearConfig.client.meteorTrailsPerTick.get()) {
                val particlePos = particleSourcePos.add(random.nextGaussian(), random.nextGaussian(), random.nextGaussian())
                level.addParticle(ModParticles.ROCKET_FLAME.get(), true, particlePos.x, particlePos.y, particlePos.z, 0.0, 0.0, 0.0)
            }
        }
    }

    override fun defineSynchedData() {}

    override fun readAdditionalSaveData(tag: CompoundTag) {}

    override fun addAdditionalSaveData(tag: CompoundTag) {}

    override fun getAddEntityPacket(): Packet<*> = NetworkHooks.getEntitySpawningPacket(this)

    override fun shouldRenderAtSqrDistance(sqrDistance: Double) = true

    companion object {
        fun spawnMeteorAtPlayer(player: ServerPlayer, repel: Boolean) {
            Meteor(player.level).apply {
                moveTo(player.x + random.nextInt(201) - 100, level.maxBuildHeight + 200.0, player.z + random.nextInt(201) - 100)

                val motion: Vec3 = if (repel) {
                    // TODO safe meteor
                    Vec3(x - player.x, 0.0, z - player.z).normalize().multiply(random.nextDouble(), 0.0, random.nextDouble()).subtract(0.5, 0.0, 0.5)
                } else {
                    Vec3(random.nextDouble() - .5, 0.0, 0.0).yRot(random.nextFloat(360F))
                }

                setDeltaMovement(motion.x, -2.5, motion.z)
                level.addFreshEntity(this)
            }
        }

        fun placeRandomMeteorite(level: ServerLevel, pos: BlockPos, random: Random) {
            val kotlinRandom = random.asKotlinRandom()
            val generator = level.chunkSource.generator
            val meteorites = WorldGen.MeteoriteFeatures
            when (random.nextInt(300)) {
                0 -> meteorites.SPECIAL_SOLID_BOX_VARIANT.value().place(level, generator, random, pos)
                1 -> meteorites.SPECIAL_GIANT_ORE_SPHERE_VARIANT.value().place(level, generator, random, pos)
                2 -> meteorites.SPECIAL_LARGE_ORE_SPHERE_VARIANT.value().place(level, generator, random, pos)
                3 -> meteorites.SPECIAL_MEDIUM_ORE_SPHERE_VARIANT.value().place(level, generator, random, pos)
                4 -> meteorites.SPECIAL_SMALL_ORE_BOX_VARIANT.value().place(level, generator, random, pos)
                5 -> meteorites.SPECIAL_LARGE_TREASURE_SPHERE_VARIANT.value().place(level, generator, random, pos)
                6 -> meteorites.SPECIAL_MEDIUM_TREASURE_SPHERE_VARIANT.value().place(level, generator, random, pos)
                7 -> meteorites.SPECIAL_SMALL_TREASURE_BOX_VARIANT.value().place(level, generator, random, pos)
                else -> when (random.nextInt(3)) {
                    0 -> meteorites.LARGE_METEORITE_VARIANTS.random(kotlinRandom).value().place(level, generator, random, pos)
                    1 -> meteorites.MEDIUM_METEORITE_VARIANTS.random(kotlinRandom).value().place(level, generator, random, pos)
                    2 -> meteorites.SMALL_METEORITE_VARIANTS.random(kotlinRandom).value().place(level, generator, random, pos)
                }
            }
        }
    }
}
