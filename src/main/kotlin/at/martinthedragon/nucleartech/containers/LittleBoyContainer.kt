package at.martinthedragon.nucleartech.containers

import at.martinthedragon.nucleartech.tileentities.LittleBoyTileEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.Container
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketBuffer
import net.minecraft.util.IIntArray
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler
import net.minecraft.util.IntArray as MinecraftIntArray

class LittleBoyContainer(
    windowID: Int,
    val playerInventory: PlayerInventory,
    val tileEntity: LittleBoyTileEntity,
    val data: IIntArray = MinecraftIntArray(1)
) : Container(ContainerTypes.littleBoyContainer.get(), windowID) {
    private val inv = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

    init {
        for (i in 0..4) addSlot(SlotItemHandler(inv, i, 26 + 18 * i, 36))
        addPlayerInventory(this::addSlot, playerInventory, 8, 140)
        addDataSlots(data)
    }

    override fun quickMoveStack(player: PlayerEntity, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot != null && slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (index !in 0..4) {
                if (!moveItemStackTo(itemStack, 0, 5, false) && !tryMoveInPlayerInventory(index, 5, itemStack)) return ItemStack.EMPTY
            } else if (!moveItemStackTo(itemStack, 5, slots.size, false)) return ItemStack.EMPTY

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()

            if (itemStack.count == returnStack.count) return ItemStack.EMPTY

            slot.onTake(player, itemStack)
        }

        return returnStack
    }

    override fun stillValid(player: PlayerEntity) = player.inventory.stillValid(player)

    fun getBombCompletion() = data[0]

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: PlayerInventory, buffer: PacketBuffer) =
            LittleBoyContainer(windowID, playerInventory, getTileEntityForContainer(buffer))
    }
}
