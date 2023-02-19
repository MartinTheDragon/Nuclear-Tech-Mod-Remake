package at.martinthedragon.nucleartech.entity

import at.martinthedragon.nucleartech.api.explosion.Explosion
import at.martinthedragon.nucleartech.api.explosion.NuclearExplosionMk4Params
import at.martinthedragon.nucleartech.api.explosion.SmallNukeExplosionParams
import at.martinthedragon.nucleartech.explosion.SmallNukeExplosion
import at.martinthedragon.nucleartech.hazard.EntityContaminationEffects
import at.martinthedragon.nucleartech.world.DamageSources
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.EntitySelector
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.monster.Creeper
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB
import net.minecraftforge.event.ForgeEventFactory

class NuclearCreeper(
    entityType: EntityType<out NuclearCreeper>,
    level: Level
) : Creeper(entityType, level) {
    // TODO achievement

    init {
        maxSwell = 75
    }

    override fun tick() {
        for (entity in level.getEntities(this, AABB(blockPosition()).inflate(5.0), EntitySelector.LIVING_ENTITY_STILL_ALIVE)) {
            entity as LivingEntity
            EntityContaminationEffects.contaminate(entity, EntityContaminationEffects.HazardType.Radiation, EntityContaminationEffects.ContaminationType.Creative, 0.25F)
        }

        super.tick()

        if (health < maxHealth && tickCount % 10 == 0) {
            heal(1F)
        }
    }

    override fun hurt(source: DamageSource, amount: Float): Boolean {
        if (source == DamageSources.radiation) { // TODO mud also heals
            heal(amount)
            return false
        }

        return super.hurt(source, amount)
    }

    override fun explodeCreeper() {
        if (!level.isClientSide) {
            discard()
            val grief = ForgeEventFactory.getMobGriefingEvent(level, this)

            if (isPowered) {
                SmallNukeExplosion.createAndStart(level, position(), 0F, SmallNukeExplosionParams(
                    damageRadius = if (grief) 0 else 100,
                    shrapnel = false,
                    actualExplosion = { level, pos, _, _ -> if (grief) NukeExplosion.create(level, pos, 100F, NuclearExplosionMk4Params(muted = true)) else Explosion { false } } // TODO strength config
                ))
            } else {
                SmallNukeExplosion.createAndStart(level, position(), 0F, if (grief) SmallNukeExplosion.MEDIUM else SmallNukeExplosion.SAFE)
            }
        }
    }

    override fun canDropMobsSkull() = false

    companion object {
        fun createAttributes(): AttributeSupplier = Monster.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 50.0)
            .add(Attributes.MOVEMENT_SPEED, .3)
            .build()
    }
}
