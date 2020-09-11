package at.martinthedragon.ntm.containers.slots

import at.martinthedragon.ntm.items.SirenTrack
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.container.Slot
import net.minecraft.item.ItemStack

class SirenTrackSlot(inventoryIn: IInventory, xPosition: Int, yPosition: Int) : Slot(inventoryIn, 1, xPosition, yPosition) {
    override fun isItemValid(stack: ItemStack) = stack.item is SirenTrack
}
