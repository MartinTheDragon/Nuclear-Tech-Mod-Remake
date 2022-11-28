package at.martinthedragon.nucleartech.menu.rbmk

import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKBoilerBlockEntity
import at.martinthedragon.nucleartech.menu.*
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

class RBMKBoilerMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: RBMKBoilerBlockEntity
) : NTechContainerMenu<RBMKBoilerBlockEntity>(MenuTypes.rbmkBoilerMenu.get(), windowID, playerInventory, blockEntity) {
    init {
        addPlayerInventory(this::addSlot, playerInventory, 8, 104)
    }

    override fun clickMenuButton(player: Player, index: Int): Boolean {
        if (index == 0) {
            blockEntity.cycleSteamType()
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
            RBMKBoilerMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
