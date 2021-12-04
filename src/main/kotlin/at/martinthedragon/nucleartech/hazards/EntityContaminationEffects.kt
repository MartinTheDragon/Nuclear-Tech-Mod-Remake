package at.martinthedragon.nucleartech.hazards

import at.martinthedragon.nucleartech.capabilites.Capabilities
import at.martinthedragon.nucleartech.capabilites.contamination.*
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.entities.NuclearCreeper
import at.martinthedragon.nucleartech.networking.ContaminationValuesUpdateMessage
import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import at.martinthedragon.nucleartech.world.ChunkRadiation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.animal.Cat
import net.minecraft.world.entity.animal.MushroomCow
import net.minecraft.world.entity.monster.Skeleton
import net.minecraft.world.entity.monster.Zombie
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
        val capability = Capabilities.getContamination(entity)
        if (capability !is ContaminationHandler) throw Error("LivingEntity ${entity.name} hasn't gotten a modifiable IIrradiationHandler")
        return contaminate(entity, capability, hazardType, contaminationType, amount)
    }

    fun contaminate(
        entity: LivingEntity,
        capability: ContaminationHandler,
        hazardType: HazardType,
        contaminationType: ContaminationType,
        amount: Float
    ): Boolean {
        if (hazardType == HazardType.Radiation) capability.setCumulativeRadiation(capability.getCumulativeRadiation() + amount)

        if (entity is Player) {
            // TODO check for armor

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

    fun calculateRadiationMod(entity: LivingEntity): Float = if (entity is Player) {
        10F.pow(-0F) // TODO hazmat
    } else 1F
}
