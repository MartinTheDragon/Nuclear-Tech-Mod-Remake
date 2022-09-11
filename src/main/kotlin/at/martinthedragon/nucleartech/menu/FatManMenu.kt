package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.block.entity.FatManBlockEntity
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class FatManMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: FatManBlockEntity,
    val data: ContainerData = SimpleContainerData(1)
) : NTechContainerMenu<FatManBlockEntity>(MenuTypes.fatManMenu.get(), windowID, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

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

    fun getBombCompletion() = data[0]

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            FatManMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
