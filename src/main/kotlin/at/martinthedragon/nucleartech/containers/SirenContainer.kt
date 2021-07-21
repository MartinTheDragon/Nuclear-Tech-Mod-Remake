package at.martinthedragon.nucleartech.containers

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.items.SirenTrack
import at.martinthedragon.nucleartech.tileentities.SirenTileEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.Container
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketBuffer
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class SirenContainer(windowId: Int, playerInventory: PlayerInventory, val tileEntity: SirenTileEntity) : Container(ContainerTypes.sirenContainer.get(), windowId) {
    init {
        val inv = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

        addSlot(SlotItemHandler(inv, 0, 8, 35))

        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
    }

    override fun stillValid(playerIn: PlayerEntity) =
        playerIn.level.getBlockState(tileEntity.blockPos).block == ModBlocks.siren.get().block

    override fun quickMoveStack(player: PlayerEntity, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot != null && slot.hasItem()) {
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

    override fun removed(player: PlayerEntity) {
        super.removed(player)
        tileEntity.stopOpen(player)
    }

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: PlayerInventory, buffer: PacketBuffer) =
                SirenContainer(windowId, playerInventory, getTileEntityForContainer(buffer))
    }
}
