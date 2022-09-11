package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.block.entity.SafeBlockEntity
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class SafeMenu(windowId: Int, playerInventory: Inventory, blockEntity: SafeBlockEntity) : NTechContainerMenu<SafeBlockEntity>(MenuTypes.safeMenu.get(), windowId, playerInventory, blockEntity) {
    init {
        val inv = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

        for (i in 0 until 3)
            for (j in 0 until 5) {
                addSlot(SlotItemHandler(inv, j + i * 5, 44 + j * 18, 18 + i * 18))
            }

        addPlayerInventory(this::addSlot, playerInventory, 8, 86)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (index !in 0..14) {
                if (!moveItemStackTo(itemStack, 0, 15, false) && !tryMoveInPlayerInventory(index, 15, itemStack)) return ItemStack.EMPTY
            } else if (!moveItemStackTo(itemStack, 15, slots.size, false)) return ItemStack.EMPTY

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()

            if (itemStack.count == returnStack.count) return ItemStack.EMPTY

            slot.onTake(player, itemStack)
        }
        return returnStack
    }

    override fun removed(player: Player) {
        super.removed(player)
        blockEntity.stopOpen(player)
    }

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            SafeMenu(windowId, playerInventory, getBlockEntityForContainer(buffer))
    }
}
