package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.api.block.entities.ExperienceRecipeResultBlockEntity
import at.martinthedragon.nucleartech.api.block.entities.TickingServerBlockEntity
import at.martinthedragon.nucleartech.item.canTransferItem
import at.martinthedragon.nucleartech.menu.BlastFurnaceMenu
import at.martinthedragon.nucleartech.recipe.BlastingRecipe
import at.martinthedragon.nucleartech.recipe.RecipeTypes
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.ContainerHelper
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.player.StackedContents
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.RecipeHolder
import net.minecraft.world.inventory.StackedContentsCompatible
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler

class BlastFurnaceBlockEntity(pos: BlockPos, state: BlockState) : BaseContainerBlockEntity(BlockEntityTypes.blastFurnaceBlockEntityType.get(), pos, state),
    TickingServerBlockEntity, RecipeHolder, StackedContentsCompatible, ExperienceRecipeResultBlockEntity
{
    private var litTime = 0
    private val isLit: Boolean
        get() = litTime > 0
    private var blastingProgress = 0
    private val isBlasting: Boolean
        get() = blastingProgress > 0

    private val dataAccess = object : ContainerData {
        override fun get(index: Int): Int = when (index) {
            0 -> litTime
            1 -> blastingProgress
            else -> 0
        }

        override fun set(index: Int, value: Int) {
            when (index) {
                0 -> litTime = value
                1 -> blastingProgress = value
            }
        }

        override fun getCount() = 2
    }

    private val items = NonNullList.withSize(4, ItemStack.EMPTY)
    private val inventory = object : ItemStackHandler(items) {
        override fun onContentsChanged(slot: Int) {
            setChanged()
        }

        override fun isItemValid(slot: Int, stack: ItemStack): Boolean =
            if (slot == 2) ForgeHooks.getBurnTime(stack, RecipeTypes.BLASTING) > 0
            else true
    }

    private val recipesUsed = Object2IntOpenHashMap<ResourceLocation>()

    override fun load(nbt: CompoundTag) {
        super.load(nbt)
        items.clear()
        ContainerHelper.loadAllItems(nbt, items)
        litTime = nbt.getInt("BurnTime")
        blastingProgress = nbt.getInt("BlastingTime")
        val recipesNbt = nbt.getCompound("RecipesUsed")
        for (string in recipesNbt.allKeys)
            recipesUsed[ResourceLocation(string)] = recipesNbt.getInt(string)
    }

    override fun saveAdditional(nbt: CompoundTag) {
        super.saveAdditional(nbt)
        ContainerHelper.saveAllItems(nbt, items)
        nbt.putInt("BurnTime", litTime)
        nbt.putInt("BlastingTime", blastingProgress)
        val recipesNbt = CompoundTag()
        for ((recipeID, amount) in recipesUsed)
            recipesNbt.putInt(recipeID.toString(), amount)
        nbt.put("RecipesUsed", recipesNbt)
    }

    override fun clearContent() {
        items.clear()
    }

    override fun getContainerSize() = items.size

    override fun isEmpty() = items.all { it.isEmpty }

    override fun getItem(slot: Int): ItemStack = items[slot]

    override fun removeItem(slot: Int, amount: Int): ItemStack = ContainerHelper.removeItem(items, slot, amount)

    override fun removeItemNoUpdate(slot: Int): ItemStack = ContainerHelper.takeItem(items, slot)

    override fun setItem(slot: Int, itemStack: ItemStack) {
        val item = items[slot]
        val itemEqualToPrevious = !itemStack.isEmpty && itemStack.sameItem(item) && ItemStack.tagMatches(itemStack, item)
        items[slot] = itemStack
        if (itemStack.count > maxStackSize)
            itemStack.count = maxStackSize

        if (slot == 0 && !itemEqualToPrevious) {
            blastingProgress = 0
            setChanged()
        }
    }

    override fun stillValid(player: Player): Boolean = if (level!!.getBlockEntity(worldPosition) != this) false
    else player.distanceToSqr(worldPosition.x + .5, worldPosition.y + .5, worldPosition.z + .5) <= 64

    override fun createMenu(windowID: Int, playerInventory: Inventory): AbstractContainerMenu {
        return BlastFurnaceMenu(windowID, playerInventory, this, dataAccess)
    }

    override fun getDefaultName(): Component = TranslatableComponent("container.${NuclearTech.MODID}.blast_furnace")

    override fun setRecipeUsed(recipe: Recipe<*>?) {
        if (recipe == null) return
        recipesUsed.addTo(recipe.id, 1)
    }

    override fun getRecipeUsed(): Recipe<*>? = null

    override fun awardUsedRecipes(player: Player) {}

    override fun clearUsedRecipes() {
        recipesUsed.clear()
    }

    override fun getExperienceToDrop(player: Player?): Float =
        recipesUsed.object2IntEntrySet().mapNotNull { (recipeID, amount) ->
            (level!!.recipeManager.byKey(recipeID).orElse(null) as? BlastingRecipe)?.experience?.times(amount)
        }.sum()

    override fun getRecipesToAward(player: Player): List<Recipe<*>> =
        recipesUsed.keys.mapNotNull { player.level.recipeManager.byKey(it).orElse(null) }

    override fun fillStackedContents(recipeItemHelper: StackedContents) {
        for (itemStack in items) recipeItemHelper.accountStack(itemStack)
    }

    fun canBlast(): Boolean = canBlast(level!!.recipeManager.getRecipeFor(RecipeTypes.BLASTING, this, level!!).orElse(null))

    private fun canBlast(recipe: Recipe<*>?): Boolean =
        if (items[0].isEmpty || items[1].isEmpty || recipe == null) false
        else canTransferItem(recipe.resultItem, items[3], this)

    private fun getBurnDuration(fuel: ItemStack): Int = if (fuel.isEmpty) 0 else ForgeHooks.getBurnTime(fuel, RecipeTypes.BLASTING)

    @Suppress("UsePropertyAccessSyntax")
    private fun blast(recipe: Recipe<*>?): Boolean {
        if (recipe == null || !canBlast(recipe)) return false
        val recipeResult = recipe.resultItem
        val resultStack = items[3]
        if (resultStack.isEmpty) items[3] = recipeResult.copy()
        else if (resultStack.sameItem(recipeResult)) resultStack.grow(recipeResult.count)
        if (!level!!.isClientSide) setRecipeUsed(recipe)
        items[0].shrink(1)
        items[1].shrink(1)
        return true
    }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        val wasLit = isLit
        val wasBlasting = isBlasting
        var contentsChanged = false

        val fuel = items[2]
        val recipe = level.recipeManager.getRecipeFor(RecipeTypes.BLASTING, this, level).orElse(null)

        if (!fuel.isEmpty) {
            val previousLitTime = litTime
            val litTimeToAdd = getBurnDuration(fuel) / 8
            if (litTime + litTimeToAdd <= MAX_BURN_TIME)
                litTime += litTimeToAdd
            if (previousLitTime != litTime) {
                contentsChanged = true
                if (fuel.hasContainerItem()) items[2] = fuel.containerItem
                else if (!fuel.isEmpty) {
                    fuel.shrink(1)
                    if (fuel.isEmpty) items[2] = fuel.containerItem
                }
            }
        }

        // TODO rtg pellet provides infinite fuel

        if (isLit && canBlast(recipe)) {
            litTime--
            blastingProgress++
            if (blastingProgress >= MAX_BLAST_TIME) {
                if (blast(recipe)) {
                    blastingProgress = 0
                    contentsChanged = true
                }
            }
        } else if (blastingProgress > 0)
            blastingProgress = (blastingProgress - 2).coerceAtLeast(0)

        if (wasLit != isLit) contentsChanged = true

        if (wasBlasting != isBlasting) {
            level.setBlock(pos, level.getBlockState(pos).setValue(BlockStateProperties.LIT, isLit && canBlast(recipe)), 0b11)
        }

        if (contentsChanged) setChanged()
    }

    private val inventoryCapability = LazyOptional.of(this::inventory)

    override fun <T : Any?> getCapability(cap: Capability<T>, direction: Direction?): LazyOptional<T> {
        if (!remove && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventoryCapability.cast()
        }
        return super.getCapability(cap, direction)
    }

    override fun invalidateCaps() {
        super.invalidateCaps()
        inventoryCapability.invalidate()
    }

    companion object {
        const val MAX_BURN_TIME = 12800
        const val MAX_BLAST_TIME = 400
    }
}
