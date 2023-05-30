package at.martinthedragon.nucleartech.coremodules.forge.common.capabilities

import at.martinthedragon.nucleartech.coremodules.forge.common.util.LazyOptional
import at.martinthedragon.nucleartech.coremodules.minecraft.core.Direction

interface ICapabilityProvider {
    fun <T : Any> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T>
}

fun <T : Any> ICapabilityProvider.getCapability(cap: Capability<T>): LazyOptional<T> =
    getCapability(cap, null)
