package at.martinthedragon.nucleartech.hazard

import at.martinthedragon.nucleartech.capability.Capabilities
import at.martinthedragon.nucleartech.capability.contamination.ContaminationHandler
import at.martinthedragon.nucleartech.capability.contamination.EntityContaminationHandler
import at.martinthedragon.nucleartech.capability.contamination.addDigamma
import at.martinthedragon.nucleartech.capability.contamination.addIrradiation
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.entity.EntityTypes
import at.martinthedragon.nucleartech.entity.NuclearCreeper
import at.martinthedragon.nucleartech.networking.ContaminationValuesUpdateMessage
import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import at.martinthedragon.nucleartech.world.ChunkRadiation
import at.martinthedragon.nucleartech.world.DamageSources
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.animal.Cat
import net.minecraft.world.entity.animal.Cow
import net.minecraft.world.entity.animal.MushroomCow
import net.minecraft.world.entity.monster.Creeper
import net.minecraft.world.entity.monster.Skeleton
import net.minecraft.world.entity.monster.Zombie
import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.entity.player.Player
import net.minecraftforge.network.NetworkDirection
import kotlin.math.pow

object EntityContaminationEffects {
    fun update(entity: LivingEntity) {
        if (entity.level.isClientSide) return

        val capability = Capabilities.getContamination(entity) ?: return
        if (entity.tickCount % 20 == 0) {
            capability.setRadPerSecond(capability.getCumulativeRadiation())
            capability.setCumulativeRadiation(0F)
        }

        if (entity is ServerPlayer) {
            capability as? EntityContaminationHandler ?: throw RuntimeException("Custom contamination handlers aren't supported yet")
            NuclearPacketHandler.INSTANCE.sendTo(ContaminationValuesUpdateMessage(capability.serializeNBT()), entity.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT)
        }

        val bombTimer = capability.getBombTimer()
        if (bombTimer > 0) {
            capability.setBombTimer(bombTimer - 1)
            // TODO explode
        }

        // TODO either make nether mobs immune, or prevent their spawns
        if (NuclearConfig.general.enable528Mode.get() && !entity.fireImmune() && entity.level.dimensionType().ultraWarm()) // 528 effect
            entity.setSecondsOnFire(5)

        val effects = capability.getContaminationEffects()
        for (effect in effects) effect.tick(entity)
        effects.removeAll(effects.filter { it.shouldBeRemoved(entity) }.toSet())

        handleRadiation(entity, capability)
    }

    private fun handleRadiation(entity: LivingEntity, capability: ContaminationHandler) {
        if (isRadiationImmune(entity)) return

        val world = entity.level
        if (world.isClientSide) return // TODO client-side effects

        val chunkRadiation = ChunkRadiation.getRadiation(world, entity.blockPosition()).run {
            val netherRadiation = NuclearConfig.radiation.netherRadiation.get()
            if (world.dimensionType().ultraWarm() && netherRadiation > 0 && this < netherRadiation) netherRadiation.toFloat()
            else this
        }
        if (chunkRadiation > 0) {
            contaminate(entity, capability, HazardType.Radiation, ContaminationType.Creative, chunkRadiation / 20F)
        }

        val irradiation = capability.getIrradiation()

        when {
            entity is Creeper && irradiation >= 200 && !entity.isDeadOrDying -> if (world.random.nextInt(3) == 0) entity.convertTo(EntityTypes.nuclearCreeper.get(), true) else entity.hurt(DamageSources.radiation, 100F)
            entity is Cow && entity !is MushroomCow && irradiation >= 50 -> entity.convertTo(EntityType.MOOSHROOM, true)
            entity is Villager && irradiation >= 500 -> entity.convertTo(EntityType.ZOMBIE, true)
        }

        if (irradiation > 2500) capability.setIrradiation(2500F)

        if ((entity is Player && (entity.isCreative || entity.isSpectator)) || entity.isInvulnerable || entity.isInvulnerableTo(
                DamageSources.radiation) || isRadiationImmune(entity)) return

        val random = world.random

        when {
            irradiation >= 1000 -> {
                entity.hurt(DamageSources.radiation, 3.4028235e38F)
                capability.setIrradiation(0F)
            }
            irradiation >= 800 -> {
                if (random.nextInt(300) == 0) entity.addEffect(MobEffectInstance(MobEffects.CONFUSION, 5 * 20))
                if (random.nextInt(300) == 0) entity.addEffect(MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10 * 20, 2))
                if (random.nextInt(300) == 0) entity.addEffect(MobEffectInstance(MobEffects.WEAKNESS, 10 * 20, 2))
                if (random.nextInt(500) == 0) entity.addEffect(MobEffectInstance(MobEffects.POISON, 3 * 20, 2))
                if (random.nextInt(700) == 0) entity.addEffect(MobEffectInstance(MobEffects.WITHER, 3 * 20, 1))
                if (random.nextInt(300) == 0) entity.addEffect(MobEffectInstance(MobEffects.HUNGER, 5 * 20, 3))
            }
            irradiation >= 600 -> {
                if (random.nextInt(300) == 0) entity.addEffect(MobEffectInstance(MobEffects.CONFUSION, 5 * 20))
                if (random.nextInt(300) == 0) entity.addEffect(MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10 * 20, 2))
                if (random.nextInt(300) == 0) entity.addEffect(MobEffectInstance(MobEffects.WEAKNESS, 10 * 20, 2))
                if (random.nextInt(500) == 0) entity.addEffect(MobEffectInstance(MobEffects.POISON, 3 * 20, 1))
                if (random.nextInt(300) == 0) entity.addEffect(MobEffectInstance(MobEffects.HUNGER, 3 * 20, 3))
            }
            irradiation >= 400 -> {
                if (random.nextInt(300) == 0) entity.addEffect(MobEffectInstance(MobEffects.CONFUSION, 5 * 20))
                if (random.nextInt(500) == 0) entity.addEffect(MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5 * 20))
                if (random.nextInt(300) == 0) entity.addEffect(MobEffectInstance(MobEffects.WEAKNESS, 5 * 20,1))
                if (random.nextInt(500) == 0) entity.addEffect(MobEffectInstance(MobEffects.HUNGER, 3 * 20, 2))
            }
            irradiation >= 200 -> {
                if (random.nextInt(300) == 0) entity.addEffect(MobEffectInstance(MobEffects.CONFUSION, 5 * 20))
                if (random.nextInt(500) == 0) entity.addEffect(MobEffectInstance(MobEffects.WEAKNESS, 5 * 20))
                if (random.nextInt(700) == 0) entity.addEffect(MobEffectInstance(MobEffects.HUNGER, 3 * 20, 2))
            }
        }

        // TODO all the nice vomitting
    }

    fun isRadiationImmune(entity: Entity): Boolean {
        // TODO has mutation effect

        return entity is NuclearCreeper ||
                entity is MushroomCow ||
                entity is Zombie ||
                entity is Skeleton ||
                entity is Cat
    }

    enum class HazardType {
        Radiation,
        Digamma
    }

    enum class ContaminationType {
        Faraday,
        Hazmat,
        HeavyHazmat,
        Digamma,
        Digamma2,
        Creative,
        Bypass,
        None
    }

    fun contaminate(entity: LivingEntity, hazardType: HazardType, contaminationType: ContaminationType, amount: Float): Boolean {
        val capability = Capabilities.getContamination(entity) ?: return false
        return contaminate(entity, capability, hazardType, contaminationType, amount)
    }

    fun contaminate(
        entity: LivingEntity,
        capability: ContaminationHandler,
        hazardType: HazardType,
        contaminationType: ContaminationType,
        amount: Float
    ): Boolean {
        if (amount < 0) {
            decontaminate(capability, hazardType, -amount)
            return true
        }
        if (hazardType == HazardType.Radiation) capability.setCumulativeRadiation(capability.getCumulativeRadiation() + amount)

        if (entity is Player) {
            // TODO check for armor

            if (entity.isSpectator) return false
            if (entity.isCreative && contaminationType != ContaminationType.None && contaminationType != ContaminationType.Digamma2) return false
            if (entity.tickCount < 200) return false
        }

        if (hazardType == HazardType.Radiation && (isRadiationImmune(entity) || !NuclearConfig.radiation.enableEntityIrradiation.get())) return false

        when (hazardType) {
            HazardType.Radiation -> capability.addIrradiation(amount * (if (contaminationType == ContaminationType.Bypass) 1F else calculateRadiationMod(entity)))
            HazardType.Digamma -> capability.addDigamma(amount)
        }

        return true
    }

    fun calculateRadiationMod(entity: LivingEntity): Float = if (entity is Player) 10F.pow(-HazmatValues.getPlayerResistance(entity)) else 1F

    fun decontaminate(capability: ContaminationHandler, hazardType: HazardType, amount: Float) {
        when (hazardType) {
            HazardType.Radiation -> capability.setIrradiation((capability.getIrradiation() - amount).coerceAtLeast(0F))
            HazardType.Digamma -> capability.setDigamma((capability.getDigamma() - amount).coerceAtLeast(0F))
        }
    }
}
