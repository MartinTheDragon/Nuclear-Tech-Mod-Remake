package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.api.block.entities.ExperienceRecipeResultBlockEntity
import at.martinthedragon.nucleartech.api.block.entities.SoundLoopBlockEntity
import at.martinthedragon.nucleartech.item.canTransferItem
import at.martinthedragon.nucleartech.menu.BlastFurnaceMenu
import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import at.martinthedragon.nucleartech.menu.slots.data.BooleanDataSlot
import at.martinthedragon.nucleartech.menu.slots.data.IntDataSlot
import at.martinthedragon.nucleartech.recipe.BlastingRecipe
import at.martinthedragon.nucleartech.recipe.RecipeTypes
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.RecipeHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.common.ForgeHooks

class BlastFurnaceBlockEntity(pos: BlockPos, state: BlockState) : RecipeMachineBlockEntity<BlastingRecipe>(BlockEntityTypes.blastFurnaceBlockEntityType.get(), pos, state), RecipeHolder, ExperienceRecipeResultBlockEntity {
    var litTime = 0
        private set
    private val isLit: Boolean
        get() = litTime > 0

    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(4, ItemStack.EMPTY)

    override fun isItemValid(slot: Int, stack: ItemStack): Boolean =
        if (slot == 2) ForgeHooks.getBurnTime(stack, RecipeTypes.BLASTING) > 0
        else true

    private val recipesUsed = Object2IntOpenHashMap<ResourceLocation>()

    override fun createMenu(windowID: Int, inventory: Inventory) = BlastFurnaceMenu(windowID, inventory, this)
    override val defaultName = LangKeys.CONTAINER_BLAST_FURNACE.get()

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(IntDataSlot.create(this::litTime, this::litTime::set))
        menu.track(IntDataSlot.create(this::progress) { progress = it })
        menu.track(BooleanDataSlot.create(this::canProgress) { canProgress = it })
    }

    override val shouldPlaySoundLoop = false
    override val soundLoopEvent get() = throw IllegalStateException("No sound loop for blast furnace")
    override val soundLoopStateMachine = SoundLoopBlockEntity.NoopStateMachine(this)

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
            (getLevelUnchecked().recipeManager.byKey(recipeID).orElse(null) as? BlastingRecipe)?.experience?.times(amount)
        }.sum()

    override fun getRecipesToAward(player: Player): List<Recipe<*>> =
        recipesUsed.keys.mapNotNull { player.level.recipeManager.byKey(it).orElse(null) }

    override fun checkCanProgress() = isLit && super.checkCanProgress()

    override fun findPossibleRecipe(): BlastingRecipe? = getLevelUnchecked().recipeManager.getRecipeFor(RecipeTypes.BLASTING, this, getLevelUnchecked()).orElse(null)

    override fun matchesRecipe(recipe: BlastingRecipe): Boolean =
        if (mainInventory[0].isEmpty || mainInventory[1].isEmpty) false
        else recipe.matches(this, getLevelUnchecked()) && canTransferItem(recipe.resultItem, mainInventory[3], this)

    @Suppress("UsePropertyAccessSyntax")
    override fun processRecipe(recipe: BlastingRecipe) {
        val recipeResult = recipe.resultItem
        val resultStack = mainInventory[3]
        if (resultStack.isEmpty) mainInventory[3] = recipeResult.copy()
        else if (resultStack.sameItem(recipeResult)) resultStack.grow(recipeResult.count)
        if (!isClientSide()) setRecipeUsed(recipe)
        mainInventory[0].shrink(1)
        mainInventory[1].shrink(1)
    }

    override val maxProgress = MAX_BLAST_TIME
    override val progressRegression = 2

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        val wasProgressing = canProgress

        // TODO rtg pellet provides infinite fuel
        val fuel = mainInventory[2]
        if (!fuel.isEmpty) {
            val previousLitTime = litTime
            val litTimeToAdd = ForgeHooks.getBurnTime(fuel, RecipeTypes.BLASTING) / 8
            if (litTime + litTimeToAdd <= MAX_BURN_TIME)
                litTime += litTimeToAdd
            if (previousLitTime != litTime) {
                setChanged()
                if (fuel.hasContainerItem()) mainInventory[2] = fuel.containerItem
                else if (!fuel.isEmpty) {
                    fuel.shrink(1)
                    if (fuel.isEmpty) mainInventory[2] = fuel.containerItem
                }
            }
        }

        super.serverTick(level, pos, state)

        if (wasProgressing != canProgress) {
            level.setBlock(pos, level.getBlockState(pos).setValue(BlockStateProperties.LIT, isLit && canProgress), 0b11)
        }
    }

    override fun tickProgress() {
        super.tickProgress()
        litTime--
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("BurnTime", litTime)
        val recipesNbt = CompoundTag()
        for ((recipeID, amount) in recipesUsed)
            recipesNbt.putInt(recipeID.toString(), amount)
        tag.put("RecipesUsed", recipesNbt)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        litTime = tag.getInt("BurnTime")
        val recipesNbt = tag.getCompound("RecipesUsed")
        for (string in recipesNbt.allKeys)
            recipesUsed[ResourceLocation(string)] = recipesNbt.getInt(string)
    }

    companion object {
        const val MAX_BURN_TIME = 12800
        const val MAX_BLAST_TIME = 400
    }
}
