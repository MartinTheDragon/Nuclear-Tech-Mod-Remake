package at.martinthedragon.ntm.containers

import at.martinthedragon.ntm.ModBlocks
import at.martinthedragon.ntm.tileentities.SirenTileEntity
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.Slot
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class SirenContainer(windowId: Int, playerInventory: PlayerInventory, val tileEntity: SirenTileEntity) : Container(ContainerTypes.sirenContainer, windowId) {
    init {
        val inv = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

        addSlot(SlotItemHandler(inv, 0, 8, 35))

        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
    }

    private fun addPlayerInventory(
            addSlot: (Slot) -> Slot,
            playerInventory: IInventory,
            xStart: Int,
            yStart: Int,
            slotCreator: (inventory: IInventory, index: Int, x: Int, y: Int) -> Slot = ::Slot
    ) {
        val slotSize = 18
        val rows = 3
        val columns = 9

        for (i in 0 until rows)
            for (j in 0 until columns) {
                addSlot(slotCreator(playerInventory, j + i * 9 + 9, xStart + j * slotSize, yStart + i * slotSize))
            }
        val newYStart = yStart + slotSize * rows + 4

        for (i in 0 until columns) {
            addSlot(slotCreator(playerInventory, i, xStart + i * slotSize, newYStart))
        }
    }

    override fun canInteractWith(playerIn: PlayerEntity) =
        playerIn.world.getBlockState(tileEntity.pos).block == ModBlocks.siren.block

    override fun transferStackInSlot(playerIn: PlayerEntity, index: Int): ItemStack {
        var itemstack = ItemStack.EMPTY
        val slot = inventorySlots[index]
        if (slot != null && slot.hasStack) {
            val itemstack1 = slot.stack
            itemstack = itemstack1.copy()
            if (index < 15) {
                if (!mergeItemStack(itemstack1, 15, inventorySlots.size, true)) {
                    return ItemStack.EMPTY
                }
            } else if (!mergeItemStack(itemstack1, 0, 15, false)) {
                return ItemStack.EMPTY
            }
            if (itemstack1.isEmpty) {
                slot.putStack(ItemStack.EMPTY)
            } else {
                slot.onSlotChanged()
            }
        }

        return itemstack!!
    }

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: PlayerInventory, buffer: PacketBuffer) =
                SirenContainer(windowId, playerInventory, DistExecutor.safeRunForDist({
                    DistExecutor.SafeSupplier { Minecraft.getInstance().world?.getTileEntity(buffer.readBlockPos()) }
                }) {
                    throw IllegalAccessException("Cannot call function on server")
                } as SirenTileEntity)
    }
}
