package at.martinthedragon.nucleartech.items

import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.IItemHandler

fun canTransferItem(from: ItemStack, to: ItemStack, inventory: Container? = null): Boolean = when {
    from.isEmpty -> false
    to.isEmpty -> true
    !to.sameItem(from) -> false
    else -> to.count + from.count <= (inventory?.maxStackSize ?: 64) && to.count + from.count <= to.maxStackSize
}

inline fun transferItemsBetweenItemHandlers(first: IItemHandler, second: IItemHandler, amount: Int = Int.MAX_VALUE, invert: Boolean = false, filter: (ItemStack) -> Boolean = { true }) {
    val from: IItemHandler
    val to: IItemHandler
    if (invert) {
        from = second
        to = first
    } else {
        from = first
        to = second
    }

    var remaining = amount
    for (originSlot in 0 until from.slots) {
        val impostorStack = from.extractItem(originSlot, remaining, true)
        if (impostorStack == ItemStack.EMPTY || !filter(impostorStack)) continue
        val originalCount = impostorStack.count
        for (destinationSlot in 0 until to.slots) {
            if (!to.isItemValid(destinationSlot, impostorStack)) continue
            if (remaining <= 0) return
            val remainingCount = to.insertItem(destinationSlot, impostorStack, true).count
            if (remainingCount < originalCount) {
                remaining -= originalCount - to.insertItem(destinationSlot, from.extractItem(originSlot, originalCount - remainingCount, false), false).count
            }
        }
    }
}
