package at.martinthedragon.nucleartech.capability

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.util.LazyOptional

/**
 * Extends the [ICapabilityProvider] interface by a function that gives providers the ability to act
 * as a "virtual" provider for a certain position somewhere else in the world. Primarily useful for
 * implementing multi-block block entities' ports.
 */
interface WorldlyCapabilityProvider : ICapabilityProvider {
    fun <T : Any> getCapability(cap: Capability<T>, pos: BlockPos, side: Direction?): LazyOptional<T> =
        getCapability(cap, side)
}
