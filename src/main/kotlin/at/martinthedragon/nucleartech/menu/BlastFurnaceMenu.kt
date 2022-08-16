package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.api.menu.slot.ExperienceResultSlot
import at.martinthedragon.nucleartech.block.entity.BlastFurnaceBlockEntity
import at.martinthedragon.nucleartech.recipe.RecipeTypes
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class BlastFurnaceMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: BlastFurnaceBlockEntity,
) : NTechContainerMenu<BlastFurnaceBlockEntity>(MenuTypes.blastFurnaceMenu.get(), windowID, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)
    private val level = playerInventory.player.level

    init {
        addSlot(SlotItemHandler(inv, 0, 80, 18))
        addSlot(SlotItemHandler(inv, 1, 80, 54))
        addSlot(SlotItemHandler(inv, 2, 8, 36))
        addSlot(ExperienceResultSlot(blockEntity, playerInventory.player, inv, 3, 134, 36))
        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (index == 3) {
                if (!moveItemStackTo(itemStack, 4, slots.size, true)) return ItemStack.EMPTY
                slot.onQuickCraft(itemStack, returnStack)
            } else if (index != 0 && index != 1 && index != 2) {
                var successful = false
                when {
                    canBlast(itemStack) && (moveItemStackTo(itemStack, 0, 1, false) || moveItemStackTo(itemStack, 1, 2, false)) -> successful = true
                    AbstractFurnaceBlockEntity.isFuel(itemStack) && moveItemStackTo(itemStack, 2, 3, false) -> successful = true
                }
                if (!successful && !tryMoveInPlayerInventory(index, 4, itemStack)) return ItemStack.EMPTY
            } else if (!moveItemStackTo(itemStack, 4, slots.size, false)) return ItemStack.EMPTY

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()

            if (itemStack.count == returnStack.count) return ItemStack.EMPTY

            slot.onTake(player, itemStack)
        }
        return returnStack
    }

    private fun canBlast(itemStack: ItemStack) =
        level.recipeManager.getRecipeFor(RecipeTypes.BLASTING, SimpleContainer(itemStack), level).isPresent

    override fun stillValid(player: Player) = playerInventory.stillValid(player)

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            BlastFurnaceMenu(windowId, playerInventory, getBlockEntityForContainer(buffer))
    }
}
