package at.martinthedragon.nucleartech.containers

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.tileentities.SirenTileEntity
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

class SirenContainer(windowId: Int, playerInventory: PlayerInventory, val tileEntity: SirenTileEntity) : Container(ContainerTypes.sirenContainer.get(), windowId) {
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

    override fun stillValid(playerIn: PlayerEntity) =
        playerIn.level.getBlockState(tileEntity.blockPos).block == ModBlocks.siren.get().block

    override fun quickMoveStack(player: PlayerEntity, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot != null && slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (index < SLOT_COUNT) {
                if (!moveItemStackTo(itemStack, SLOT_COUNT, slots.size, true))
                    return ItemStack.EMPTY
            } else if (!moveItemStackTo(itemStack, 0, SLOT_COUNT, false))
                return ItemStack.EMPTY

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()
        }

        return returnStack
    }

    override fun removed(player: PlayerEntity) {
        super.removed(player)
        tileEntity.stopOpen(player)
    }

    companion object {
        const val SLOT_COUNT = 1

        fun fromNetwork(windowId: Int, playerInventory: PlayerInventory, buffer: PacketBuffer) =
                SirenContainer(windowId, playerInventory, DistExecutor.safeRunForDist({
                    DistExecutor.SafeSupplier { Minecraft.getInstance().level?.getBlockEntity(buffer.readBlockPos()) }
                }) {
                    throw IllegalAccessException("Cannot call function on server")
                } as SirenTileEntity)
    }
}
