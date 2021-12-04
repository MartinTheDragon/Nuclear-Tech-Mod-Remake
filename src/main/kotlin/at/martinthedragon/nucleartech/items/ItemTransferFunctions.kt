package at.martinthedragon.nucleartech.items

import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack

fun canTransferItem(from: ItemStack, to: ItemStack, inventory: Container? = null): Boolean = when {
    from.isEmpty -> false
    to.isEmpty -> true
    !to.sameItem(from) -> false
    else -> to.count + from.count <= (inventory?.maxStackSize ?: 64) && to.count + from.count <= to.maxStackSize
}
