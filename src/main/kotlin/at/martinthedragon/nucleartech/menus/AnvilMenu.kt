package at.martinthedragon.nucleartech.menus

import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.math.toVec3Middle
import at.martinthedragon.nucleartech.recipes.RecipeTypes
import at.martinthedragon.nucleartech.recipes.anvil.AnvilSmithingRecipe
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.ResultContainer
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class AnvilMenu(windowID: Int, val playerInventory: Inventory, val access: ContainerLevelAccess, val tier: Int) : AbstractContainerMenu(MenuTypes.anvilMenu.get(), windowID) {
    private val player = playerInventory.player
    private val level = player.level
    private val resultSlot = ResultContainer()
    private val inputSlots = object : SimpleContainer(2) {
        override fun setChanged() {
            super.setChanged()
            this@AnvilMenu.slotsChanged(this)
        }
    }
    private val recipes = level.recipeManager.getAllRecipesFor(RecipeTypes.SMITHING)
    private var selectedRecipe: AnvilSmithingRecipe? = null

    init {
        addSlot(Slot(inputSlots, 0, 17, 27))
        addSlot(Slot(inputSlots, 1, 53, 27))
        addSlot(object : Slot(resultSlot, 2, 89, 27) {
            override fun mayPlace(stack: ItemStack) = false
            override fun mayPickup(player: Player) = this@AnvilMenu.mayPickup()
            override fun onTake(player: Player, stack: ItemStack) = this@AnvilMenu.onTake(player, stack)
        })
        addPlayerInventory(this::addSlot, playerInventory, 8, 140)
    }

    // For smithing
    private fun mayPickup(): Boolean = selectedRecipe != null && selectedRecipe!!.matches(inputSlots, level)

    // For smithing
    private fun onTake(player: Player, stack: ItemStack) {
        if (selectedRecipe == null) return
        val mirrorInt = selectedRecipe!!.matchesInt(inputSlots)
        if (mirrorInt == -1) return
        stack.onCraftedBy(player.level, player, stack.count)
        resultSlot.awardUsedRecipes(player)
        shrinkStackInSlot(0, selectedRecipe!!.getAmountConsumed(0, mirrorInt == 1))
        shrinkStackInSlot(1, selectedRecipe!!.getAmountConsumed(1, mirrorInt == 1))
    }

    private fun shrinkStackInSlot(slot: Int, amount: Int) {
        val stack = inputSlots.getItem(slot)
        stack.shrink(amount)
        inputSlots.setItem(slot, stack)
    }

    override fun slotsChanged(container: Container) {
        super.slotsChanged(container)
        if (container == inputSlots) createResult()
    }

    private fun createResult() {
        val matchingRecipes = level.recipeManager.getRecipesFor(RecipeTypes.SMITHING, inputSlots, level).filter { it.tier <= tier }
        if (matchingRecipes.isEmpty()) resultSlot.setItem(0, ItemStack.EMPTY)
        else {
            selectedRecipe = matchingRecipes.first()
            val result = selectedRecipe!!.assemble(inputSlots)
            resultSlot.recipeUsed = selectedRecipe!!
            resultSlot.setItem(0, result)
        }
    }

    override fun removed(player: Player) {
        super.removed(player)
        access.execute { _, _ -> clearContainer(player, inputSlots) }
    }

    override fun stillValid(player: Player): Boolean = access.evaluate({ level, pos ->
        if (!level.getBlockState(pos).`is`(NuclearTags.Blocks.ANVIL)) false else player.distanceToSqr(pos.toVec3Middle()) <= 64
    }, true)

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
                val quickMoveSlot = quickMoveToWhichSlot(itemStack, slots.first().hasItem())
                if (!moveItemStackTo(itemStack, quickMoveSlot, 2, false) && !tryMoveInPlayerInventory(index, 3, itemStack)) return ItemStack.EMPTY
            } else if (!moveItemStackTo(itemStack, 3, slots.size, false)) return ItemStack.EMPTY

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()

            if (itemStack.count == returnStack.count) return ItemStack.EMPTY

            slot.onTake(player, itemStack)
        }
        return returnStack
    }

    private fun quickMoveToWhichSlot(stack: ItemStack, firstSlotHasItem: Boolean): Int {
        val found = recipes.any { it.tier <= tier && it.isMissingIngredient(stack, firstSlotHasItem) }
        return if (!found || !firstSlotHasItem) 0 else 1
    }

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) = AnvilMenu(windowID, playerInventory, ContainerLevelAccess.NULL, buffer.readInt())
    }
}
