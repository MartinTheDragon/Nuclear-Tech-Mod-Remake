package at.martinthedragon.nucleartech.api.fluid.trait

import net.minecraftforge.fluids.FluidStack

public interface FluidTraitManager {
    public fun getTraitsForFluidStack(fluidStack: FluidStack): List<AttachedFluidTrait<*>>

    public fun getTraitForFluidStack(fluidStack: FluidStack, fluidTrait: FluidTrait): AttachedFluidTrait<*>? =
        getTraitsForFluidStack(fluidStack).lastOrNull { it.trait == fluidTrait }
}

@Suppress("UNCHECKED_CAST")
public inline fun <reified T : FluidTrait> FluidTraitManager.getTraitForFluidStack(fluidStack: FluidStack): AttachedFluidTrait<T>? =
    getTraitsForFluidStack(fluidStack).lastOrNull { it.trait is T } as AttachedFluidTrait<T>?
