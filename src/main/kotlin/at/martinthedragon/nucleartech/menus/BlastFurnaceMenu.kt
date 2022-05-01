package at.martinthedragon.nucleartech.menus

import at.martinthedragon.nucleartech.api.menus.slots.ExperienceResultSlot
import at.martinthedragon.nucleartech.blocks.entities.BlastFurnaceBlockEntity
import at.martinthedragon.nucleartech.recipes.RecipeTypes
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class BlastFurnaceMenu(
    windowID: Int,
    val playerInventory: Inventory,
    val tileEntity: BlastFurnaceBlockEntity,
    val data: ContainerData = SimpleContainerData(2)
) : AbstractContainerMenu(MenuTypes.blastFurnaceMenu.get(), windowID) {
    private val inv = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)
    private val level = playerInventory.player.level

    init {
        addSlot(SlotItemHandler(inv, 0, 80, 18))
        addSlot(SlotItemHandler(inv, 1, 80, 54))
        addSlot(SlotItemHandler(inv, 2, 8, 36))
        addSlot(ExperienceResultSlot(tileEntity, playerInventory.player, inv, 3, 134, 36))
        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
        addDataSlots(data)
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

    fun canBlast() = tileEntity.canBlast()

    private fun canBlast(itemStack: ItemStack) =
        level.recipeManager.getRecipeFor(RecipeTypes.BLASTING, SimpleContainer(itemStack), level).isPresent

    override fun stillValid(player: Player) = playerInventory.stillValid(player)

    fun getBurnTime() = data[0]

    fun getBlastProgress() = data[1]

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            BlastFurnaceMenu(windowId, playerInventory, getBlockEntityForContainer(buffer))
    }
}
