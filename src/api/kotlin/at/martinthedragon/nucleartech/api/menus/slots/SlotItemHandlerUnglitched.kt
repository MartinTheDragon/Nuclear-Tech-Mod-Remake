package at.martinthedragon.nucleartech.api.menus.slots

import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.IItemHandlerModifiable
import net.minecraftforge.items.SlotItemHandler

/**
 * Instead of taking a different route for [getMaxStackSize] when the [itemHandler] is an instance of [IItemHandlerModifiable],
 * it will use regular behaviour that won't trigger the item handler's onContentsChanged method, which can cause weird de-syncs and glitches.
 */
public class SlotItemHandlerUnglitched(itemHandler: IItemHandler, private val index: Int, xPosition: Int, yPosition: Int) : SlotItemHandler(itemHandler, index, xPosition, yPosition) {
    override fun getMaxStackSize(stack: ItemStack): Int {
        val maxAdd = stack.copy()
        val maxInput = stack.maxStackSize
        maxAdd.count = maxInput

        val handler = itemHandler
        val currentStack = handler.getStackInSlot(index)
        val remainder = handler.insertItem(index, maxAdd, true)
        val current = currentStack.count
        val added = maxInput - remainder.count
        return current + added
    }
}
