package at.martinthedragon.nucleartech.menus

import at.martinthedragon.nucleartech.blocks.entities.FatManBlockEntity
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class FatManMenu(
    windowID: Int,
    val playerInventory: Inventory,
    val tileEntity: FatManBlockEntity,
    val data: ContainerData = SimpleContainerData(1)
) : AbstractContainerMenu(MenuTypes.fatManMenu.get(), windowID) {
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

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasItem()) {
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

    override fun stillValid(player: Player) = playerInventory.stillValid(player)

    fun getBombCompletion() = data[0]

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            FatManMenu(windowID, playerInventory, getTileEntityForContainer(buffer))
    }
}
