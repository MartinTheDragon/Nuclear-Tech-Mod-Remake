package at.martinthedragon.nucleartech.hazards

import at.martinthedragon.nucleartech.capabilites.contamination.*
import at.martinthedragon.nucleartech.entities.NuclearCreeperEntity
import at.martinthedragon.nucleartech.networking.ContaminationValuesUpdateMessage
import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import at.martinthedragon.nucleartech.world.ChunkRadiation
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.monster.SkeletonEntity
import net.minecraft.entity.monster.ZombieEntity
import net.minecraft.entity.passive.CatEntity
import net.minecraft.entity.passive.MooshroomEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraftforge.fml.network.NetworkDirection
import kotlin.math.pow

object EntityContaminationEffects {
    fun update(entity: LivingEntity) {
        if (entity.level.isClientSide) return

        val capability = CapabilityContaminationHandler.getCapability(entity) ?: return
        if (entity.tickCount % 20 == 0) {
            capability.setRadPerSecond(capability.getCumulativeRadiation())
            capability.setCumulativeRadiation(0F)
        }

        if (entity is ServerPlayerEntity) {
            capability as? EntityContaminationHandler ?: throw RuntimeException("Custom contamination handlers aren't supported yet")
            NuclearPacketHandler.INSTANCE.sendTo(ContaminationValuesUpdateMessage(capability.serializeNBT()), entity.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT)
        }

        val bombTimer = capability.getBombTimer()
        if (bombTimer > 0) {
            capability.setBombTimer(bombTimer - 1)
            // TODO explode
        }

        // TODO burn in nether if 528 mode enabled
        handleRadiation(entity, capability)
    }

    private fun handleRadiation(entity: LivingEntity, capability: IContaminationHandlerModifiable) {
        if (isRadiationImmune(entity)) return

        val world = entity.level
        if (world.isClientSide) return // TODO client-side effects

        val chunkRadiation = ChunkRadiation.getRadiation(world, entity.blockPosition())
        if (chunkRadiation > 0) {
            contaminate(entity, capability, HazardType.Radiation, ContaminationType.Creative, chunkRadiation / 20F)
        }

        // TODO nether radiation
        // TODO all the nice vomitting
    }

    fun isRadiationImmune(entity: Entity): Boolean {
        // TODO has mutation effect

        return entity is NuclearCreeperEntity ||
                entity is MooshroomEntity ||
                entity is ZombieEntity ||
                entity is SkeletonEntity ||
                entity is CatEntity
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
        val capability = entity.getCapability(CapabilityContaminationHandler.contaminationHandlerCapability)
        if (capability !is IContaminationHandlerModifiable) throw Error("LivingEntity ${entity.name} hasn't gotten a modifiable IIrradiationHandler")
        return contaminate(entity, capability, hazardType, contaminationType, amount)
    }

    fun contaminate(
        entity: LivingEntity,
        capability: IContaminationHandlerModifiable,
        hazardType: HazardType,
        contaminationType: ContaminationType,
        amount: Float
    ): Boolean {
        if (hazardType == HazardType.Radiation) capability.setCumulativeRadiation(capability.getCumulativeRadiation() + amount)

        if (entity is PlayerEntity) {
            // TODO check for armor

            if (entity.isCreative && contaminationType != ContaminationType.None && contaminationType != ContaminationType.Digamma2) return false
            if (entity.tickCount < 200) return false
        }

        if (hazardType == HazardType.Radiation && isRadiationImmune(entity)) return false // TODO check config

        when (hazardType) {
            HazardType.Radiation -> capability.addIrradiation(amount * (if (contaminationType == ContaminationType.Bypass) 1F else calculateRadiationMod(entity)))
            HazardType.Digamma -> capability.addDigamma(amount)
        }

        return true
    }

    fun calculateRadiationMod(entity: LivingEntity): Float = if (entity is PlayerEntity) {
        10F.pow(-0F) // TODO hazmat
    } else 1F
}
