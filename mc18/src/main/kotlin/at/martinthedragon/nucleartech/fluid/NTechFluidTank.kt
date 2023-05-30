package at.martinthedragon.nucleartech.fluid

import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.templates.FluidTank

open class NTechFluidTank(capacity: Int, validator: (FluidStack) -> Boolean = { true }) : FluidTank(capacity, validator), MutableFluidTank {
    override fun forceFluid(stack: FluidStack) {
        super.setFluid(stack)
    }
}
