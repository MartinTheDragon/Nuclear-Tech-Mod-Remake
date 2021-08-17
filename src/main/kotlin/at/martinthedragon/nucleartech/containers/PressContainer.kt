package at.martinthedragon.nucleartech.containers

import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.containers.slots.ExperienceResultSlot
import at.martinthedragon.nucleartech.recipes.RecipeTypes
import at.martinthedragon.nucleartech.tileentities.SteamPressTopTileEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.container.RecipeBookContainer
import net.minecraft.inventory.container.Slot
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.RecipeBookCategory
import net.minecraft.item.crafting.RecipeItemHelper
import net.minecraft.network.PacketBuffer
import net.minecraft.tags.ItemTags
import net.minecraft.tileentity.AbstractFurnaceTileEntity
import net.minecraft.util.IIntArray
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler
import net.minecraft.util.IntArray as MinecraftIntArray

class PressContainer(
    windowId: Int,
    val playerInventory: PlayerInventory,
    val tileEntity: SteamPressTopTileEntity,
    val data: IIntArray = MinecraftIntArray(4)
) : RecipeBookContainer<SteamPressTopTileEntity>(ContainerTypes.steamPressContainer.get(), windowId) {
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

    private fun addPlayerInventory(
        addSlot: (Slot) -> Slot,
        playerInventory: IInventory,
        xStart: Int,
        yStart: Int,
        slotCreator: (inventory: IInventory, index: Int, x: Int, y: Int) -> Slot = ::Slot
    ) {
        val slotSize = 18
        val rows = 3
        val columns = 9

        for (i in 0 until rows)
            for (j in 0 until columns) {
                addSlot(slotCreator(playerInventory, j + i * 9 + 9, xStart + j * slotSize, yStart + i * slotSize))
            }
        val newYStart = yStart + slotSize * rows + 4

        for (i in 0 until columns) {
            addSlot(slotCreator(playerInventory, i, xStart + i * slotSize, newYStart))
        }
    }

    override fun quickMoveStack(player: PlayerEntity, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot != null && slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (index == 3) {
                if (!moveItemStackTo(itemStack, 4, slots.size, true)) return ItemStack.EMPTY
                slot.onQuickCraft(itemStack, returnStack)
            } else if (index != 0 && index != 1 && index != 2) {
                var successful = false
                when {
                    canPress(itemStack) && moveItemStackTo(itemStack, 0, 1, false) -> successful = true
                    itemStack.item in ItemTags.getAllTags().getTagOrEmpty(NuclearTags.Items.STAMPS.name) && moveItemStackTo(itemStack, 1, 2, false) -> successful = true
                    AbstractFurnaceTileEntity.isFuel(itemStack) && moveItemStackTo(itemStack, 2, 3, false) -> successful = true
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
        level.recipeManager.getRecipeFor(RecipeTypes.PRESSING, Inventory(itemStack), level).isPresent

    override fun stillValid(player: PlayerEntity) = playerInventory.stillValid(player)

    override fun fillCraftSlotsStackedContents(recipeItemHelper: RecipeItemHelper) {
        tileEntity.fillStackedContents(recipeItemHelper)
    }

    override fun clearCraftingContent() {
        tileEntity.clearContent()
    }

    override fun recipeMatches(recipe: IRecipe<in SteamPressTopTileEntity>) = recipe.matches(tileEntity, level)

    override fun getResultSlotIndex() = 3

    override fun getGridWidth() = 1

    override fun getGridHeight() = 1

    override fun getSize() = 4

    override fun getRecipeBookType() = RecipeBookCategory.CRAFTING

    fun getBurnProgress(): Int {
        val burnTime = data[0]
        val burnDuration = data[1]
        return if (burnTime != 0 && burnDuration != 0) burnTime * 13 / burnDuration else 0
    }

    fun getPressProgress() = data[2]

    fun isPressing() = data[2] > 0

    fun getPower() = data[3]

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: PlayerInventory, buffer: PacketBuffer) =
            PressContainer(windowId, playerInventory, getTileEntityForContainer(buffer))
    }
}
