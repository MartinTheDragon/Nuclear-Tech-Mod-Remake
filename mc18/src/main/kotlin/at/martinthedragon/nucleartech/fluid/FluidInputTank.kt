package at.martinthedragon.nucleartech.fluid

import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler

class FluidInputTank(capacity: Int, validator: (FluidStack) -> Boolean = { true }) : NTechFluidTank(capacity, validator) {
    override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction): FluidStack = FluidStack.EMPTY
    override fun drain(resource: FluidStack, action: IFluidHandler.FluidAction): FluidStack = FluidStack.EMPTY
}
