package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.NTechTags
import at.martinthedragon.nucleartech.math.toVec3Middle
import at.martinthedragon.nucleartech.recipe.RecipeTypes
import at.martinthedragon.nucleartech.recipe.anvil.AnvilSmithingRecipe
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
        if (!level.getBlockState(pos).`is`(NTechTags.Blocks.ANVIL)) false else player.distanceToSqr(pos.toVec3Middle()) <= 64
    }, true)

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 3, intArrayOf(2)) {
        val slot = quickMoveToWhichSlot(itemStack, slots.first().hasItem())
        slot..slot
    }

    private fun quickMoveToWhichSlot(stack: ItemStack, firstSlotHasItem: Boolean): Int {
        val found = recipes.any { it.tier <= tier && it.isMissingIngredient(stack, firstSlotHasItem) }
        return if (!found || !firstSlotHasItem) 0 else 1
    }

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) = AnvilMenu(windowID, playerInventory, ContainerLevelAccess.NULL, buffer.readInt())
    }
}
