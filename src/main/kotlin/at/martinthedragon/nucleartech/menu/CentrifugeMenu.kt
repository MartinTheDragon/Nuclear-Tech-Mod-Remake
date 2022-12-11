package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.api.menu.slot.ResultSlot
import at.martinthedragon.nucleartech.block.entity.CentrifugeBlockEntity
import at.martinthedragon.nucleartech.item.upgrades.MachineUpgradeItem
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class CentrifugeMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: CentrifugeBlockEntity
) : NTechContainerMenu<CentrifugeBlockEntity>(MenuTypes.centrifugeMenu.get(), windowID, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

    init {
        addSlot(SlotItemHandler(inv, 0, 9, 50))
        addSlot(SlotItemHandler(inv, 1, 149, 22))
        addSlot(SlotItemHandler(inv, 2, 149, 40))
        addSlot(SlotItemHandler(inv, 3, 36, 50))
        for (i in 0 until 4) addSlot(ResultSlot(playerInventory.player, inv, 4 + i, 63 + i * 20, 50))
        addPlayerInventory(this::addSlot, playerInventory, 8, 104)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (index in 4..7) {
                if (!moveItemStackTo(itemStack, 8, slots.size, true)) return ItemStack.EMPTY
                slot.onQuickCraft(itemStack, returnStack)
            } else if (index !in 0..7) {
                var successful = false
                when {
                    itemStack.getCapability(CapabilityEnergy.ENERGY).isPresent && moveItemStackTo(itemStack, 0, 1, false) -> successful = true
                    MachineUpgradeItem.isValidFor(blockEntity, itemStack) && moveItemStackTo(itemStack, 1, 3, false) -> successful = true
                    moveItemStackTo(itemStack, 3, 4, false) -> successful = true
                }
                if (!successful && !tryMoveInPlayerInventory(index, 8, itemStack)) return ItemStack.EMPTY
            } else if (!moveItemStackTo(itemStack, 8, slots.size, false)) return ItemStack.EMPTY

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()

            if (itemStack.count == returnStack.count) return ItemStack.EMPTY

            slot.onTake(player, itemStack)
        }
        return returnStack
    }

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            CentrifugeMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
