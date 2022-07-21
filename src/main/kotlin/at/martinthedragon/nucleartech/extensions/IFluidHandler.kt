package at.martinthedragon.nucleartech.extensions

import at.martinthedragon.nucleartech.fluid.FluidHandlerSubView
import at.martinthedragon.nucleartech.fluid.SimpleFluidHandler
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.fluids.capability.templates.FluidTank

fun IFluidHandler.containsFluid(fluidStack: FluidStack): Boolean {
    for (i in 0 until tanks) {
        if (getFluidInTank(i).containsFluid(fluidStack)) return true
    }
    return false
}

fun IFluidHandler.containsFluids(vararg fluidStacks: FluidStack): Boolean {
    val handlerFluids = (0 until tanks).map(this::getFluidInTank).filterNot(FluidStack::isEmpty).map(FluidStack::copy)
    val requiredFluids = fluidStacks.filterNot(FluidStack::isEmpty).map(FluidStack::copy)
    for (requiredFluid in requiredFluids) for (handlerFluid in handlerFluids) {
        if (!handlerFluid.isFluidEqual(requiredFluid)) continue
        val amountNeeded = requiredFluid.amount
        handlerFluid.shrink(amountNeeded)
        requiredFluid.shrink(amountNeeded)
    }
    if (handlerFluids.any { it.amount < 0 }) return false
    return requiredFluids.all(FluidStack::isEmpty)
}

fun IFluidHandler.getFluids(): List<FluidStack> = buildList {
    for (tank in 0 until tanks) add(getFluidInTank(tank).copy())
}

fun IFluidHandler.acceptFluids(fluids: Collection<FluidStack>, simulate: Boolean): List<FluidStack> {
    if (fluids.all(FluidStack::isEmpty)) return emptyList()

    val handler = if (simulate) copy() else this

    val remainingStacks = mutableListOf<FluidStack>()
    for (fluid in fluids) {
        val remainder = fluid.amount - handler.fill(fluid, IFluidHandler.FluidAction.EXECUTE)
        if (remainder > 0) remainingStacks += FluidStack(fluid, remainder)
    }
    return remainingStacks
}

// won't copy the validator
fun IFluidHandler.copy(): IFluidHandler = SimpleFluidHandler(getFluids().mapIndexed { index, fluid -> FluidTank(getTankCapacity(index)).apply { setFluid(fluid) } })

fun IFluidHandler.subView(fromIndex: Int, toIndex: Int): IFluidHandler = FluidHandlerSubView(this, fromIndex until toIndex)
