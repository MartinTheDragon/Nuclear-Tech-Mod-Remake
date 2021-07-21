package at.martinthedragon.nucleartech.items

import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack

fun canTransferItem(from: ItemStack, to: ItemStack, inventory: IInventory? = null): Boolean = when {
    from.isEmpty -> false
    to.isEmpty -> true
    !to.sameItem(from) -> false
    else -> to.count + from.count <= (inventory?.maxStackSize ?: 64) && to.count + from.count <= to.maxStackSize
}
