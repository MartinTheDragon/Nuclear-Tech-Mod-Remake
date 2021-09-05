package at.martinthedragon.nucleartech.containers

import at.martinthedragon.nucleartech.tileentities.FatManTileEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.Container
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketBuffer
import net.minecraft.util.IIntArray
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler
import net.minecraft.util.IntArray as MinecraftIntArray

class FatManContainer(
    windowID: Int,
    val playerInventory: PlayerInventory,
    val tileEntity: FatManTileEntity,
    val data: IIntArray = MinecraftIntArray(1)
) : Container(ContainerTypes.fatManContainer.get(), windowID) {
    private val inv = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

    init {
        addSlot(SlotItemHandler(inv, 0, 8, 17))
        addSlot(SlotItemHandler(inv, 1, 44, 17))
        addSlot(SlotItemHandler(inv, 2, 8, 53))
        addSlot(SlotItemHandler(inv, 3, 44, 53))
        addSlot(SlotItemHandler(inv, 4, 26, 35))
        addSlot(SlotItemHandler(inv, 5, 98, 35))
        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
        addDataSlots(data)
    }

    override fun quickMoveStack(player: PlayerEntity, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot != null && slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (index !in 0..5) {
                if (!moveItemStackTo(itemStack, 0, 6, false) && !tryMoveInPlayerInventory(index, 6, itemStack)) return ItemStack.EMPTY
            } else if (!moveItemStackTo(itemStack, 6, slots.size, false)) return ItemStack.EMPTY

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()

            if (itemStack.count == returnStack.count) return ItemStack.EMPTY

            slot.onTake(player, itemStack)
        }
        return returnStack
    }

    override fun stillValid(player: PlayerEntity) = playerInventory.stillValid(player)

    fun getBombCompletion() = data[0]

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: PlayerInventory, buffer: PacketBuffer) =
            FatManContainer(windowID, playerInventory, getTileEntityForContainer(buffer))
    }
}
