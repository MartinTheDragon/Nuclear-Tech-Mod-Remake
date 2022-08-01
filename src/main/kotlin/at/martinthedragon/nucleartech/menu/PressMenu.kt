package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.NTechTags
import at.martinthedragon.nucleartech.api.menu.slot.ExperienceResultSlot
import at.martinthedragon.nucleartech.block.entity.SteamPressBlockEntity
import at.martinthedragon.nucleartech.recipe.RecipeTypes
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

class PressMenu(
    windowId: Int,
    val playerInventory: Inventory,
    val tileEntity: SteamPressBlockEntity,
    val data: ContainerData = SimpleContainerData(4)
) : AbstractContainerMenu(MenuTypes.steamPressMenu.get(), windowId) {
    private val inv = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)
    private val level = playerInventory.player.level

    init {
        addSlot(SlotItemHandler(inv, 0, 80, 53))
        addSlot(SlotItemHandler(inv, 1, 80, 17))
        addSlot(SlotItemHandler(inv, 2, 26, 53))
        addSlot(ExperienceResultSlot(tileEntity, playerInventory.player, inv, 3, 140, 35))
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
                    canPress(itemStack) && moveItemStackTo(itemStack, 0, 1, false) -> successful = true
                    itemStack.`is`(NTechTags.Items.STAMPS) && moveItemStackTo(itemStack, 1, 2, false) -> successful = true
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

    private fun canPress(itemStack: ItemStack) =
        level.recipeManager.getRecipeFor(RecipeTypes.PRESSING, SimpleContainer(itemStack), level).isPresent

    override fun stillValid(player: Player) = playerInventory.stillValid(player)

    fun getBurnProgress(): Int {
        val burnTime = data[0]
        val burnDuration = data[1]
        return if (burnTime != 0 && burnDuration != 0) burnTime * 13 / burnDuration else 0
    }

    fun getPressProgress() = data[2]

    fun isPressing() = data[2] > 0

    fun getPower() = data[3]

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            PressMenu(windowId, playerInventory, getBlockEntityForContainer(buffer))
    }
}
