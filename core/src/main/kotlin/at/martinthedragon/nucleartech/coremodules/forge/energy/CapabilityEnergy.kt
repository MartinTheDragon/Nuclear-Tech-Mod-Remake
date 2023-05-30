package at.martinthedragon.nucleartech.coremodules.forge.energy

import at.martinthedragon.nucleartech.coremodules.InjectionStatic
import at.martinthedragon.nucleartech.coremodules.forge.common.capabilities.Capability

interface CapabilityEnergy {
    val energyCapability: Capability<IEnergyStorage>

    companion object {
        val ENERGY get() = InjectionStatic.capabilityEnergy.energyCapability
    }
}
