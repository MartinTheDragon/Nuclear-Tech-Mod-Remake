package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.api.item.TargetDesignator
import at.martinthedragon.nucleartech.block.entity.LaunchPadBlockEntity
import at.martinthedragon.nucleartech.item.MissileItem
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.item.ItemStack
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class LaunchPadMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: LaunchPadBlockEntity,
    val data: ContainerData = SimpleContainerData(1)
) : NTechContainerMenu<LaunchPadBlockEntity>(MenuTypes.launchPadMenu.get(), windowID, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

    init {
        addSlot(SlotItemHandler(inv, 0, 26, 17))
        addSlot(SlotItemHandler(inv, 1, 80, 17))
        addSlot(SlotItemHandler(inv, 2, 134, 17))
        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
        addDataSlots(data)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (index !in 0..2) {
                var successful = false
                when {
                    itemStack.item is MissileItem<*> && moveItemStackTo(itemStack, 0, 1, false) -> successful = true
                    itemStack.item is TargetDesignator && moveItemStackTo(itemStack, 1, 2, false) -> successful = true
                    itemStack.getCapability(CapabilityEnergy.ENERGY).isPresent && moveItemStackTo(itemStack, 2, 3, false) -> successful = true
                }
                if (!successful && !tryMoveInPlayerInventory(index, 3, itemStack)) return ItemStack.EMPTY
            } else if (!moveItemStackTo(itemStack, 3, slots.size, false)) return ItemStack.EMPTY

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()

            if (itemStack.count == returnStack.count) return ItemStack.EMPTY

            slot.onTake(player, itemStack)
        }
        return returnStack
    }

    fun getEnergy() = data[0]

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            LaunchPadMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
