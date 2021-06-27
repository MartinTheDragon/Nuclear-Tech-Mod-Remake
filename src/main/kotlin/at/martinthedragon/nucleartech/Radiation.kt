package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.capabilites.CapabilityIrradiationHandler
import at.martinthedragon.nucleartech.capabilites.IIrradiationHandlerModifiable
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.merchant.villager.VillagerEntity
import net.minecraft.entity.monster.CreeperEntity
import net.minecraft.entity.monster.SkeletonEntity
import net.minecraft.entity.monster.ZombieEntity
import net.minecraft.entity.monster.ZombieVillagerEntity
import net.minecraft.entity.passive.CowEntity
import net.minecraft.entity.passive.MooshroomEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld

object Radiation {
    var irradiatedEntityList = listOf<LivingEntity>()

    fun addEntityIrradiation(entity: LivingEntity, radiation: Float) {
        if (entity.isDeadOrDying) return
        val cap = entity.getCapability(CapabilityIrradiationHandler.irradiationHandlerCapability).orElseThrow(::RuntimeException)
        if (cap !is IIrradiationHandlerModifiable) throw RuntimeException("LivingEntity ${entity.name} hasn't gotten a modifiable IIrradiationHandler")
        val newIrradiation = (cap.getIrradiation() + radiation).coerceIn(0f, 2500f)
        cap.setIrradiation(newIrradiation)
    }

    fun setEntityIrradiation(entity: LivingEntity, radiation: Float) {
        if (entity.isDeadOrDying) return
        val cap = entity.getCapability(CapabilityIrradiationHandler.irradiationHandlerCapability).orElseThrow(::RuntimeException)
        if (cap !is IIrradiationHandlerModifiable) throw RuntimeException("LivingEntity ${entity.name} hasn't gotten a modifiable IIrradiationHandler")
        val newIrradiation = radiation.coerceIn(0f, 2500f)
        cap.setIrradiation(newIrradiation)
    }

    fun getEntityIrradiation(entity: LivingEntity): Float {
        if (entity.isDeadOrDying) return 0f
        if (!entity.getCapability(CapabilityIrradiationHandler.irradiationHandlerCapability).isPresent) return 0f
        val cap = entity.getCapability(CapabilityIrradiationHandler.irradiationHandlerCapability).orElseThrow(::RuntimeException)
        if (cap !is IIrradiationHandlerModifiable) throw RuntimeException("LivingEntity ${entity.name} hasn't gotten a modifiable IIrradiationHandler")
        return cap.getIrradiation()
    }

    fun applyRadiationEffects(world: World) {
        if (!world.isClientSide) {
            // 3000 IQ strategy to get irradiated entities without causing ConcurrentModificationException follows (now with access transformation):
            if (world.gameTime % 20 == 0L) { // polling rate
                irradiatedEntityList = (world as ServerWorld).chunkSource.chunkMap.entityMap.values
                    .map { it.entity }
                    .filterIsInstance<LivingEntity>()
                    .filter { getEntityIrradiation(it) > 0 }
            }
            loop@ for (entity in irradiatedEntityList) {
                val irradiation = getEntityIrradiation(entity)
                when {
                    entity is CreeperEntity && irradiation >= 200 && !entity.isDeadOrDying -> {
                        if (world.random.nextInt(3) == 0) {
                            // TODO spawn nuclear creeper
                        } else
                            entity.hurt(DamageSources.radiation, 100f)
                        continue@loop
                    }
                    entity is CowEntity && entity !is MooshroomEntity && irradiation >= 50 -> {
                        val creep = MooshroomEntity(EntityType.MOOSHROOM, world)
                        creep.isBaby = entity.isBaby
                        creep.moveTo(entity.blockPosition(), entity.xRot, entity.yRot)
                        if (!entity.isDeadOrDying && !world.isClientSide)
                            world.addFreshEntity(creep)
                        entity.remove()
                        continue@loop
                    }
                    entity is VillagerEntity && irradiation >= 500 -> {
                        val creep = ZombieVillagerEntity(EntityType.ZOMBIE_VILLAGER, world)
                        creep.isBaby = entity.isBaby
                        creep.moveTo(entity.blockPosition(), entity.xRot, entity.yRot)
                        if (!entity.isDeadOrDying && !world.isClientSide)
                            world.addFreshEntity(creep)
                        entity.remove()
                        continue@loop
                    }
                }

                // TODO add nuclear creeper to exclusions
                if (irradiation < 200 || entity is MooshroomEntity || entity is ZombieEntity || entity is SkeletonEntity)
                    continue

                if (irradiation > 2500)
                    setEntityIrradiation(entity, 2500f)

                if ((entity is PlayerEntity && (entity.isCreative || entity.isSpectator)) || entity.isInvulnerable || entity.isInvulnerableTo(DamageSources.radiation))
                    continue

                when {
                    irradiation >= 1000 -> {
                        entity.hurt(DamageSources.radiation, 3.4028235e38f)
                        setEntityIrradiation(entity, 0f)
                    }
                    irradiation >= 800 -> {
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(EffectInstance(Effects.CONFUSION, 5 * 20))
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(EffectInstance(Effects.MOVEMENT_SLOWDOWN, 10 * 20, 2))
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(EffectInstance(Effects.WEAKNESS, 10 * 20, 2))
                        if (world.random.nextInt(500) == 0)
                            entity.addEffect(EffectInstance(Effects.POISON, 3 * 20, 2))
                        if (world.random.nextInt(700) == 0)
                            entity.addEffect(EffectInstance(Effects.WITHER, 3 * 20, 1))
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(EffectInstance(Effects.HUNGER, 5 * 20, 3))
                    }
                    irradiation >= 600 -> {
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(EffectInstance(Effects.CONFUSION, 5 * 20))
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(EffectInstance(Effects.MOVEMENT_SLOWDOWN, 10 * 20, 2))
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(EffectInstance(Effects.WEAKNESS, 10 * 20, 2))
                        if (world.random.nextInt(500) == 0)
                            entity.addEffect(EffectInstance(Effects.POISON, 3 * 20, 1))
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(EffectInstance(Effects.HUNGER, 3 * 20, 3))
                    }
                    irradiation >= 400 -> {
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(EffectInstance(Effects.CONFUSION, 5 * 20))
                        if (world.random.nextInt(500) == 0)
                            entity.addEffect(EffectInstance(Effects.MOVEMENT_SLOWDOWN, 5 * 20))
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(EffectInstance(Effects.WEAKNESS, 5 * 20,1))
                        if (world.random.nextInt(500) == 0)
                            entity.addEffect(EffectInstance(Effects.HUNGER, 3 * 20, 2))
                    }
                    irradiation >= 200 -> {
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(EffectInstance(Effects.CONFUSION, 5 * 20))
                        if (world.random.nextInt(500) == 0)
                            entity.addEffect(EffectInstance(Effects.WEAKNESS, 5 * 20))
                        if (world.random.nextInt(700) == 0)
                            entity.addEffect(EffectInstance(Effects.HUNGER, 3 * 20, 2))
                    }
                }
            }
        }
    }
}
