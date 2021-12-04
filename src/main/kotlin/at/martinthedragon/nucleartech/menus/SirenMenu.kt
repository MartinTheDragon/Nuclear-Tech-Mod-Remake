package at.martinthedragon.nucleartech.menus

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.blocks.entities.SirenBlockEntity
import at.martinthedragon.nucleartech.items.SirenTrack
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class SirenMenu(windowId: Int, playerInventory: Inventory, val tileEntity: SirenBlockEntity) : AbstractContainerMenu(MenuTypes.sirenMenu.get(), windowId) {
    init {
        val inv = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

        addSlot(SlotItemHandler(inv, 0, 8, 35))

        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
    }

    override fun stillValid(playerIn: Player) =
        playerIn.level.getBlockState(tileEntity.blockPos).block == ModBlocks.siren.get()

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (index != 0) {
                var successful = false
                if (itemStack.item is SirenTrack && moveItemStackTo(itemStack, 0, 1, false)) successful = true
                if (!successful && !tryMoveInPlayerInventory(index, 1, itemStack)) return ItemStack.EMPTY
            } else if (!moveItemStackTo(itemStack, 1, slots.size, false)) return ItemStack.EMPTY

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()

            if (itemStack.count == returnStack.count) return ItemStack.EMPTY

            slot.onTake(player, itemStack)
        }
        return returnStack
    }

    override fun removed(player: Player) {
        super.removed(player)
        tileEntity.stopOpen(player)
    }

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            SirenMenu(windowId, playerInventory, getTileEntityForContainer(buffer))
    }
}
