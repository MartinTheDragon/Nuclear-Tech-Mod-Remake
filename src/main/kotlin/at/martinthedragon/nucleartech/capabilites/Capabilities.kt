package at.martinthedragon.nucleartech.capabilites

import at.martinthedragon.nucleartech.capabilites.contamination.ContaminationHandler
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
