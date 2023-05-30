package at.martinthedragon.nucleartech.fluid

import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.IFluidTank

interface MutableFluidTank : IFluidTank {
    fun setFluid(stack: FluidStack)
    fun forceFluid(stack: FluidStack)
}
