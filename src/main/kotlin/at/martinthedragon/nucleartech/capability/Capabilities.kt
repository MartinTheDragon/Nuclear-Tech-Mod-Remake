package at.martinthedragon.nucleartech.capability

import at.martinthedragon.nucleartech.capability.contamination.ContaminationHandler
import net.minecraft.world.entity.LivingEntity
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.CapabilityToken

object Capabilities {
    val CONTAMINATION_CAPABILITY: Capability<ContaminationHandler> = CapabilityManager.get(object : CapabilityToken<ContaminationHandler>() {})

    fun getContamination(entity: LivingEntity) =
        entity.getCapability(CONTAMINATION_CAPABILITY)
            .takeIf { it.isPresent }
            ?.orElseThrow(::Error)
}
