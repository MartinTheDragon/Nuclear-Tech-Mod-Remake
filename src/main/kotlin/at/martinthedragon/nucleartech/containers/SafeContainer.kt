package at.martinthedragon.nucleartech.containers

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.tileentities.SafeTileEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.Container
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketBuffer
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class SafeContainer(windowId: Int, playerInventory: PlayerInventory, val tileEntity: SafeTileEntity) : Container(ContainerTypes.safeContainer.get(), windowId) {
    init {
        val inv = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

        for (i in 0 until 3)
            for (j in 0 until 5) {
                addSlot(SlotItemHandler(inv, j + i * 5, 44 + j * 18, 18 + i * 18))
            }

        addPlayerInventory(this::addSlot, playerInventory, 8, 86)
    }

    override fun stillValid(player: PlayerEntity): Boolean =
        player.level.getBlockState(tileEntity.blockPos).block == ModBlocks.safe.get().block

    override fun quickMoveStack(player: PlayerEntity, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot != null && slot.hasItem()) {
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

    override fun removed(player: PlayerEntity) {
        super.removed(player)
        tileEntity.stopOpen(player)
    }

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: PlayerInventory, buffer: PacketBuffer) =
                SafeContainer(windowId, playerInventory, getTileEntityForContainer(buffer))
    }
}
