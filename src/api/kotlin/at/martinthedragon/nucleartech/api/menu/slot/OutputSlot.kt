package at.martinthedragon.nucleartech.api.menu.slot

import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler

public open class OutputSlot(inventory: IItemHandler, index: Int, xPosition: Int, yPosition: Int) : SlotItemHandler(inventory, index, xPosition, yPosition) {
    final override fun mayPlace(stack: ItemStack): Boolean = false
}
