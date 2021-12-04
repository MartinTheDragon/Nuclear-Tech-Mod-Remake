package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.capabilites.Capabilities
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.animal.Cow
import net.minecraft.world.entity.animal.MushroomCow
import net.minecraft.world.entity.monster.Creeper
import net.minecraft.world.entity.monster.Skeleton
import net.minecraft.world.entity.monster.Zombie
import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level

object Radiation {
    internal var irradiatedEntityList = listOf<LivingEntity>() // TODO still a bit spaghetti, consider doing it differently

    @JvmStatic
    fun addEntityIrradiation(entity: LivingEntity, radiation: Float) {
        if (entity.isDeadOrDying) return
        val cap = Capabilities.getContamination(entity) ?: return
        cap.setIrradiation((cap.getIrradiation() + radiation).coerceIn(0F, 2500F))
    }

    @JvmStatic
    fun setEntityIrradiation(entity: LivingEntity, radiation: Float) {
        if (entity.isDeadOrDying) return
        val cap = Capabilities.getContamination(entity) ?: return
        cap.setIrradiation(radiation.coerceIn(0F, 2500F))
    }

    @JvmStatic
    fun getEntityIrradiation(entity: LivingEntity): Float {
        if (entity.isDeadOrDying) return 0F
        val cap = Capabilities.getContamination(entity) ?: return 0F
        return cap.getIrradiation()
    }

    internal fun applyRadiationEffects(world: Level) {
        if (!world.isClientSide) {
            // 3000 IQ strategy to get irradiated entities without causing ConcurrentModificationException follows (now with access transformation):
            if (world.gameTime % 20 == 0L) { // polling rate
                irradiatedEntityList = listOf()
            }
            for (entity in irradiatedEntityList) {
                val irradiation = getEntityIrradiation(entity)
                when {
                    entity is Creeper && irradiation >= 200 && !entity.isDeadOrDying -> if (world.random.nextInt(3) == 0) {
                        // TODO spawn nuclear creeper
                    } else entity.hurt(DamageSources.radiation, 100f)
                    entity is Cow && entity !is MushroomCow && irradiation >= 50 -> entity.convertTo(EntityType.MOOSHROOM, true)
                    entity is Villager && irradiation >= 500 -> entity.convertTo(EntityType.ZOMBIE, true)
                }

                if (irradiation > 2500)
                    setEntityIrradiation(entity, 2500f)

                // TODO add nuclear creeper to exclusions
                if (irradiation < 200 || entity is MushroomCow || entity is Zombie || entity is Skeleton)
                    continue

                if ((entity is Player && (entity.isCreative || entity.isSpectator)) || entity.isInvulnerable || entity.isInvulnerableTo(DamageSources.radiation))
                    continue

                when {
                    irradiation >= 1000 -> {
                        entity.hurt(DamageSources.radiation, 3.4028235e38f)
                        setEntityIrradiation(entity, 0f)
                    }
                    irradiation >= 800 -> {
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(MobEffectInstance(MobEffects.CONFUSION, 5 * 20))
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10 * 20, 2))
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(MobEffectInstance(MobEffects.WEAKNESS, 10 * 20, 2))
                        if (world.random.nextInt(500) == 0)
                            entity.addEffect(MobEffectInstance(MobEffects.POISON, 3 * 20, 2))
                        if (world.random.nextInt(700) == 0)
                            entity.addEffect(MobEffectInstance(MobEffects.WITHER, 3 * 20, 1))
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(MobEffectInstance(MobEffects.HUNGER, 5 * 20, 3))
                    }
                    irradiation >= 600 -> {
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(MobEffectInstance(MobEffects.CONFUSION, 5 * 20))
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10 * 20, 2))
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(MobEffectInstance(MobEffects.WEAKNESS, 10 * 20, 2))
                        if (world.random.nextInt(500) == 0)
                            entity.addEffect(MobEffectInstance(MobEffects.POISON, 3 * 20, 1))
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(MobEffectInstance(MobEffects.HUNGER, 3 * 20, 3))
                    }
                    irradiation >= 400 -> {
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(MobEffectInstance(MobEffects.CONFUSION, 5 * 20))
                        if (world.random.nextInt(500) == 0)
                            entity.addEffect(MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5 * 20))
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(MobEffectInstance(MobEffects.WEAKNESS, 5 * 20,1))
                        if (world.random.nextInt(500) == 0)
                            entity.addEffect(MobEffectInstance(MobEffects.HUNGER, 3 * 20, 2))
                    }
                    irradiation >= 200 -> {
                        if (world.random.nextInt(300) == 0)
                            entity.addEffect(MobEffectInstance(MobEffects.CONFUSION, 5 * 20))
                        if (world.random.nextInt(500) == 0)
                            entity.addEffect(MobEffectInstance(MobEffects.WEAKNESS, 5 * 20))
                        if (world.random.nextInt(700) == 0)
                            entity.addEffect(MobEffectInstance(MobEffects.HUNGER, 3 * 20, 2))
                    }
                }
            }
        }
    }
}
