package at.martinthedragon.nucleartech.fluid

import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler

// will only make a fluid handler 'appear' as if it had that many tanks, the distribution is still left to the fluid handler alone
class FluidHandlerSubView(private val fluidHandler: IFluidHandler, private val tankRange: IntRange) : IFluidHandler {
    init {
        require(tankRange.first >= 0) { "Range cannot start in the negative range" }
        require(tankRange.last < fluidHandler.tanks) { "End of range must be below fluid handler's tank count" }
        if (fluidHandler.tanks > 0) require(!tankRange.isEmpty()) { "Range $tankRange is empty" }
    }

    private fun translateIndex(index: Int) = index + tankRange.first

    override fun getTanks() = tankRange.count()
    override fun getFluidInTank(tank: Int) = fluidHandler.getFluidInTank(translateIndex(tank))
    override fun getTankCapacity(tank: Int) = fluidHandler.getTankCapacity(translateIndex(tank))
    override fun isFluidValid(tank: Int, stack: FluidStack) = fluidHandler.isFluidValid(translateIndex(tank), stack)

    override fun fill(resource: FluidStack, action: IFluidHandler.FluidAction) = fluidHandler.fill(resource, action)
    override fun drain(resource: FluidStack, action: IFluidHandler.FluidAction) = fluidHandler.drain(resource, action)
    override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction) = fluidHandler.drain(maxDrain, action)
}
