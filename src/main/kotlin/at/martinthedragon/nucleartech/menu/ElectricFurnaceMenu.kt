package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.api.menu.slot.ExperienceResultSlot
import at.martinthedragon.nucleartech.block.entity.ElectricFurnaceBlockEntity
import at.martinthedragon.nucleartech.item.upgrades.MachineUpgradeItem
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeType
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class ElectricFurnaceMenu(
    windowId: Int,
    playerInventory: Inventory,
    blockEntity: ElectricFurnaceBlockEntity,
) : NTechContainerMenu<ElectricFurnaceBlockEntity>(MenuTypes.electricFurnaceMenu.get(), windowId, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)
    private val level = playerInventory.player.level

    init {
        addSlot(SlotItemHandler(inv, 0, 56, 17))
        addSlot(SlotItemHandler(inv, 1, 56, 53))
        addSlot(ExperienceResultSlot(blockEntity, playerInventory.player, inv, 2, 116, 35))
        addSlot(SlotItemHandler(inv, 3, 147, 34))
        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (index == 2) {
                if (!moveItemStackTo(itemStack, 3, slots.size, true)) return ItemStack.EMPTY
                slot.onQuickCraft(itemStack, returnStack)
            } else if (index != 0 && index != 1) {
                var successful = false
                when {
                    canCook(itemStack) && moveItemStackTo(itemStack, 0, 1, false) -> successful = true
                    itemStack.getCapability(CapabilityEnergy.ENERGY).isPresent && moveItemStackTo(itemStack, 1, 2, false) -> successful = true
                    MachineUpgradeItem.isValidForBE(blockEntity, itemStack) && moveItemStackTo(itemStack, 3, 4, false) -> successful = true
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

    private fun canCook(itemStack: ItemStack) =
        level.recipeManager.getRecipeFor(RecipeType.SMELTING, SimpleContainer(itemStack), level).isPresent

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            ElectricFurnaceMenu(windowId, playerInventory, getBlockEntityForContainer(buffer))
    }
}
