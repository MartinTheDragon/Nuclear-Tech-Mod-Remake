package at.martinthedragon.nucleartech.entity

import at.martinthedragon.nucleartech.particle.sendParticles
import at.martinthedragon.nucleartech.world.DamageSources
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.projectile.ThrowableProjectile
import net.minecraft.world.level.Level
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.HitResult

open class Shrapnel(entityType: EntityType<out Shrapnel>, level: Level) : ThrowableProjectile(entityType, level) {
    constructor(level: Level) : this(EntityTypes.shrapnel.get(), level)

    override fun defineSynchedData() {
        entityData.define(TRAIL, false)
    }

    fun setHasTrail(trail: Boolean) {
        entityData.set(TRAIL, trail)
    }

    override fun tick() {
        super.tick()

        if (level.isClientSide && entityData.get(TRAIL)) {
            level.addParticle(ParticleTypes.FLAME, true, x, y + 0.5, z, 0.0, 0.0, 0.0)
        }
    }

    override fun onHit(hitResult: HitResult) {
        if (tickCount > 5 && !level.isClientSide) {
            for (i in 0..4) {
                (level as ServerLevel).sendParticles(ParticleTypes.LAVA, true, x, y, z, 1, 0.0, 0.0, 0.0, 0.0)
            }

            level.playSound(null, x, y, z, SoundEvents.LAVA_EXTINGUISH, SoundSource.NEUTRAL, 1F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F)
            super.onHit(hitResult)

            discard()
        }
    }

    override fun onHitEntity(hitResult: EntityHitResult) {
        super.onHitEntity(hitResult)
        hitResult.entity.hurt(DamageSources.shrapnel, 15F)
    }

    override fun readAdditionalSaveData(tag: CompoundTag) {
        super.readAdditionalSaveData(tag)
        entityData.set(TRAIL, tag.getBoolean("Trail"))
    }

    override fun addAdditionalSaveData(tag: CompoundTag) {
        super.addAdditionalSaveData(tag)
        tag.putBoolean("Trail", entityData.get(TRAIL))
    }

    companion object {
        private val TRAIL: EntityDataAccessor<Boolean> = SynchedEntityData.defineId(Shrapnel::class.java, EntityDataSerializers.BOOLEAN)
    }
}
