package at.martinthedragon.nucleartech.fluid

import at.martinthedragon.nucleartech.extensions.ifPresentInline
import net.minecraft.world.item.ItemStack
import net.minecraftforge.fluids.FluidActionResult
import net.minecraftforge.fluids.FluidUtil
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.ItemHandlerHelper

// FluidUtil#tryEmptyContainerAndStow but without the bullshit
fun tryEmptyFluidContainerAndMove(container: ItemStack, fluidDestination: IFluidHandler, output: IItemHandler, maxAmount: Int, doDrain: Boolean): FluidActionResult {
    if (container.isEmpty) return FluidActionResult.FAILURE

    val emptiedSimulated = tryEmptyFluidContainer(container, fluidDestination, maxAmount, false)
    if (emptiedSimulated.success) {
        val remainder = ItemHandlerHelper.insertItemStacked(output, emptiedSimulated.result, true)
        if (remainder.isEmpty) {
            val emptiedReal = tryEmptyFluidContainer(container, fluidDestination, maxAmount, doDrain)
            ItemHandlerHelper.insertItemStacked(output, emptiedReal.result, !doDrain)
            return FluidActionResult(container.copy().apply { shrink(1) })
        }
    }

    return FluidActionResult.FAILURE
}

// FluidUtil#tryFillContainerAndStow but also without the bullshit
fun tryFillFluidContainerAndMove(container: ItemStack, fluidSource: IFluidHandler, output: IItemHandler, maxAmount: Int, doFill: Boolean): FluidActionResult {
    if (container.isEmpty) FluidActionResult.FAILURE

    val filledSimulated = FluidUtil.tryFillContainer(container, fluidSource, maxAmount, null, false)
    if (filledSimulated.isSuccess) {
        val remainder = ItemHandlerHelper.insertItemStacked(output, filledSimulated.result, true)
        if (remainder.isEmpty) {
            val filledReal = FluidUtil.tryFillContainer(container, fluidSource, maxAmount, null, doFill)
            ItemHandlerHelper.insertItemStacked(output, filledReal.result, !doFill)
            return FluidActionResult(container.copy().apply { shrink(1) })
        }
    }

    return FluidActionResult.FAILURE
}

// FluidUtil#tryEmptyContainer but actually functioning properly ([B]orge moment)
fun tryEmptyFluidContainer(container: ItemStack, fluidDestination: IFluidHandler, maxAmount: Int, doDrain: Boolean): FluidActionResult {
    val copy = ItemHandlerHelper.copyStackWithSize(container, 1)
    return FluidUtil.getFluidHandler(copy).ifPresentInline {
        val transferResult = FluidUtil.tryFluidTransfer(fluidDestination, it, maxAmount, doDrain)
        if (transferResult.isEmpty) return@ifPresentInline FluidActionResult.FAILURE
        if (!doDrain) it.drain(maxAmount, IFluidHandler.FluidAction.EXECUTE) // after-correction so we get the right container (we're operating on a copy, so it's fine)
        return@ifPresentInline FluidActionResult(it.container)
    } ?: FluidActionResult.FAILURE
}
