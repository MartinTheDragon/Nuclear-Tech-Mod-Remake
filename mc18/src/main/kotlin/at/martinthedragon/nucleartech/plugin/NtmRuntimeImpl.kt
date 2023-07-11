package at.martinthedragon.nucleartech.plugin

import at.martinthedragon.nucleartech.api.NuclearTechRuntime
import at.martinthedragon.nucleartech.fluid.trait.FluidTraitManager
import at.martinthedragon.nucleartech.world.ChunkRadiation

class NtmRuntimeImpl : NuclearTechRuntime {
    override fun getChunkRadiationHandler() = ChunkRadiation
    override fun getFluidTraitManager() = FluidTraitManager
}
