package at.martinthedragon.nucleartech.menus

import net.minecraft.client.Minecraft
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.Container
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.DistExecutor.SafeSupplier

inline fun addPlayerInventory(
    addSlot: (Slot) -> Slot,
    playerInventory: Container,
    xStart: Int,
    yStart: Int,
    slotCreator: (inventory: Container, index: Int, x: Int, y: Int) -> Slot = ::Slot
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
fun AbstractContainerMenu.tryMoveInPlayerInventory(index: Int, inventoryStart: Int, itemStack: ItemStack): Boolean {
    if (index >= inventoryStart && index < (slots.size - 9).coerceAtLeast(inventoryStart + 1)) {
        if (!moveItemStackTo(itemStack, (slots.size - 9).coerceAtLeast(inventoryStart), slots.size, false))
            return false
    } else if (index >= (slots.size - 9) && index < slots.size && !moveItemStackTo(itemStack, inventoryStart, (slots.size - 9), false))
        return false
    return true
}

@Suppress("UNCHECKED_CAST")
fun <T : BlockEntity> getBlockEntityForContainer(buffer: FriendlyByteBuf): T = DistExecutor.safeRunForDist({ ClientBlockEntityGetter(buffer) }) { ServerBlockEntityGetter() }

private class ServerBlockEntityGetter : SafeSupplier<Nothing> {
    override fun get(): Nothing {
        throw IllegalAccessException("Cannot call function on server")
    }
}

private class ClientBlockEntityGetter<out T : BlockEntity>(private val buffer: FriendlyByteBuf) : SafeSupplier<@UnsafeVariance T> {
    @Suppress("UNCHECKED_CAST")
    override fun get(): T {
        val pos = buffer.readBlockPos()
        return (Minecraft.getInstance().level?.getBlockEntity(pos) ?: throw IllegalStateException("Invalid block entity position sent from server: $pos"))
            as? T ?: throw IllegalStateException("Cannot open container on wrong block entity")
    }
}
