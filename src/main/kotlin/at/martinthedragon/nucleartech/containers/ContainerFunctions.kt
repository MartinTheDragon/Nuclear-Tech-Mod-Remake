package at.martinthedragon.nucleartech.containers

import net.minecraft.client.Minecraft
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.Slot
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketBuffer
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.fml.DistExecutor

fun addPlayerInventory(
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

/** Should not be used outside Container.quickMoveStack */
fun Container.tryMoveInPlayerInventory(index: Int, inventoryStart: Int, itemStack: ItemStack): Boolean {
    if (index >= inventoryStart && index < (slots.size - 9).coerceAtLeast(inventoryStart + 1)) {
        if (!moveItemStackTo(itemStack, (slots.size - 9).coerceAtLeast(inventoryStart), slots.size, false))
            return false
    } else if (index >= (slots.size - 9) && index < slots.size && !moveItemStackTo(itemStack, inventoryStart, (slots.size - 9), false))
        return false
    return true
}

@Suppress("UNCHECKED_CAST")
fun <T : TileEntity> getTileEntityForContainer(buffer: PacketBuffer): T {
    return DistExecutor.safeRunForDist({ DistExecutor.SafeSupplier {
        val pos = buffer.readBlockPos()
        Minecraft.getInstance().level?.getBlockEntity(pos) ?: throw IllegalStateException("Invalid tile entity position sent from server: $pos")
    }}) {
        throw IllegalAccessException("Cannot call function on server")
    } as? T ?: throw IllegalStateException("Cannot open container on wrong tile entity")
}
