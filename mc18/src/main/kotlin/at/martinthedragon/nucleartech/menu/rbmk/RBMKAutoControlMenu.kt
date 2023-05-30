package at.martinthedragon.nucleartech.menu.rbmk

import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKAutoControlBlockEntity
import at.martinthedragon.nucleartech.menu.*
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

class RBMKAutoControlMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: RBMKAutoControlBlockEntity
) : NTechContainerMenu<RBMKAutoControlBlockEntity>(MenuTypes.rbmkAutoControlMenu.get(), windowID, playerInventory, blockEntity) {
    init {
        addPlayerInventory(this::addSlot, playerInventory, 8, 104)
    }

    override fun clickMenuButton(player: Player, index: Int): Boolean {
        if (index in RBMKAutoControlBlockEntity.ControlFunction.values().indices) {
            blockEntity.function = RBMKAutoControlBlockEntity.ControlFunction.values()[index]
            blockEntity.markDirty()
            return true
        }
        return false
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (!tryMoveInPlayerInventory(index, 0, itemStack)) return ItemStack.EMPTY

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()

            if (itemStack.count == returnStack.count) return ItemStack.EMPTY

            slot.onTake(player, itemStack)
        }
        return returnStack
    }


    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            RBMKAutoControlMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
