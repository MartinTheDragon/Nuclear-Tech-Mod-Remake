package at.martinthedragon.nucleartech.capability

import at.martinthedragon.nucleartech.coremodules.forge.common.capabilities.Capability
import at.martinthedragon.nucleartech.coremodules.forge.common.capabilities.ICapabilityProvider
import at.martinthedragon.nucleartech.coremodules.forge.common.util.LazyOptional
import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPos
import at.martinthedragon.nucleartech.coremodules.minecraft.core.Direction

interface WordlyCapabilityProvider : ICapabilityProvider {
    fun <T : Any> getCapability(cap: Capability<T>, pos: BlockPos, side: Direction?): LazyOptional<T> =
        getCapability(cap, side)
}
