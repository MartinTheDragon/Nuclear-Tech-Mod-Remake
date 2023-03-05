package at.martinthedragon.nucleartech.api.fluid.trait

import net.minecraftforge.fluids.FluidStack

public interface FluidTraitManager {
    public fun getTraitsForFluidStack(fluidStack: FluidStack): List<AttachedFluidTrait>

    public fun getTraitForFluidStack(fluidStack: FluidStack, fluidTrait: FluidTrait): AttachedFluidTrait? =
        getTraitsForFluidStack(fluidStack).findLast { it.trait == fluidTrait }
}
