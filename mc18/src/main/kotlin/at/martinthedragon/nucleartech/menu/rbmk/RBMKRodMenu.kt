package at.martinthedragon.nucleartech.menu.rbmk

import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKRodBlockEntity
import at.martinthedragon.nucleartech.menu.*
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class RBMKRodMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: RBMKRodBlockEntity
) : NTechContainerMenu<RBMKRodBlockEntity>(MenuTypes.rbmkRodMenu.get(), windowID, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

    init {
        addSlot(SlotItemHandler(inv, 0, 80, 45))
        addPlayerInventory(this::addSlot, playerInventory, 8, 104)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (index == 0) {
                if (!moveItemStackTo(itemStack, 1, slots.size, true)) return ItemStack.EMPTY
            } else {
                if (!blockEntity.isItemValid(0, itemStack) || !moveItemStackTo(itemStack, 0, 1, false))
                    if (!tryMoveInPlayerInventory(index, 1, itemStack)) return ItemStack.EMPTY
            }

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()

            if (itemStack.count == returnStack.count) return ItemStack.EMPTY

            slot.onTake(player, itemStack)
        }
        return returnStack
    }

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            RBMKRodMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
