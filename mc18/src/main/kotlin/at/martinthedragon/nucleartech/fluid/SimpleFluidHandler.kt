package at.martinthedragon.nucleartech.fluid

import at.martinthedragon.nucleartech.extensions.isIndexInRange
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.IFluidTank
import net.minecraftforge.fluids.capability.IFluidHandler

class SimpleFluidHandler(private val tanks: List<IFluidTank>) : IFluidHandler {
    constructor(vararg tanks: IFluidTank) : this(tanks.toList())

    override fun getTanks() = tanks.size
    override fun getFluidInTank(tank: Int): FluidStack = if (tanks.isIndexInRange(tank)) tanks[tank].fluid else FluidStack.EMPTY
    override fun getTankCapacity(tank: Int) = if (tanks.isIndexInRange(tank)) tanks[tank].capacity else 0
    override fun isFluidValid(tank: Int, stack: FluidStack) = if (tanks.isIndexInRange(tank)) tanks[tank].isFluidValid(stack) else false

    override fun fill(resource: FluidStack, action: IFluidHandler.FluidAction): Int {
        val amount = resource.amount
        var remaining = amount
        for (tank in tanks) {
            remaining -= tank.fill(FluidStack(resource, remaining), action)
        }
        return amount - remaining
    }

    override fun drain(resource: FluidStack, action: IFluidHandler.FluidAction): FluidStack {
        var drained = 0
        for (tank in tanks) {
            drained += tank.drain(FluidStack(resource, resource.amount - drained), action).amount
            if (drained >= resource.amount) break
        }
        return FluidStack(resource, drained)
    }

    override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction): FluidStack {
        var drained = 0
        var firstDrain: FluidStack = FluidStack.EMPTY
        for (tank in tanks) {
            val resource = tank.drain(maxDrain - drained, action)
            if (resource.isEmpty) continue
            if (drained == 0) firstDrain = resource
            if (resource.isFluidEqual(firstDrain)) drained += resource.amount
            if (drained >= maxDrain) break
        }
        return FluidStack(firstDrain, drained)
    }
}
