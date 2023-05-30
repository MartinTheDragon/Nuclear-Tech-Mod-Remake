package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.NTechTags
import at.martinthedragon.nucleartech.NTechSoundsCore
import at.martinthedragon.nucleartech.api.block.entities.ExperienceRecipeResultBlockEntity
import at.martinthedragon.nucleartech.api.block.entities.SoundLoopBlockEntity
import at.martinthedragon.nucleartech.item.canTransferItem
import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import at.martinthedragon.nucleartech.menu.PressMenu
import at.martinthedragon.nucleartech.menu.slots.data.IntDataSlot
import at.martinthedragon.nucleartech.recipe.PressingRecipe
import at.martinthedragon.nucleartech.recipe.RecipeTypes
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.RecipeHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraftforge.common.ForgeHooks
import kotlin.jvm.optionals.getOrNull
import kotlin.math.abs

class SteamPressBlockEntity(pos: BlockPos, state: BlockState) : RecipeMachineBlockEntity<PressingRecipe>(BlockEntityTypes.steamPressHeadBlockEntityType.get(), pos, state), RecipeHolder, ExperienceRecipeResultBlockEntity {
    var litDuration = 0
        private set
    var litTime = 0
        private set
    private val isLit: Boolean
        get() = litTime > 0
    var power = 0
        private set

    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(4, ItemStack.EMPTY)

    override fun isItemValid(slot: Int, stack: ItemStack) = when (slot) {
        1 -> stack.`is`(NTechTags.Items.STAMPS)
        2 -> ForgeHooks.getBurnTime(stack, null) > 0
        else -> true
    }

    override fun createMenu(windowID: Int, inventory: Inventory) = PressMenu(windowID, inventory, this)
    override val defaultName = LangKeys.CONTAINER_STEAM_PRESS.get()

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(IntDataSlot.create(this::litTime, this::litTime::set))
        menu.track(IntDataSlot.create(this::litDuration, this::litDuration::set))
        menu.track(IntDataSlot.create(this::progress) { progress = it })
        menu.track(IntDataSlot.create(this::power, this::power::set))
    }

    override fun getRenderBoundingBox() = AABB(blockPos.offset(.0, -1.0, .0), blockPos.offset(1.0, 1.0, 1.0))

    private val recipesUsed = Object2IntOpenHashMap<ResourceLocation>()

    override fun setRecipeUsed(recipe: Recipe<*>?) {
        if (recipe == null) return
        recipesUsed.addTo(recipe.id, 1)
    }

    override fun getRecipeUsed(): Recipe<*>? = null

    override fun awardUsedRecipes(player: Player) {}

    override fun clearUsedRecipes() = recipesUsed.clear()

    override fun getExperienceToDrop(player: Player?): Float =
        recipesUsed.object2IntEntrySet().mapNotNull { (recipeID, amount) ->
            (level!!.recipeManager.byKey(recipeID).orElse(null) as? PressingRecipe)?.experience?.times(amount)
        }.sum()

    override fun getRecipesToAward(player: Player): List<Recipe<*>> =
        recipesUsed.keys.mapNotNull { player.level.recipeManager.byKey(it).orElse(null) }

    override val shouldPlaySoundLoop = false
    override val soundLoopEvent get() = throw IllegalStateException("No sound loop for steam press")
    override val soundLoopStateMachine = SoundLoopBlockEntity.NoopStateMachine(this)

    @OptIn(ExperimentalStdlibApi::class)
    override fun findPossibleRecipe() = getLevelUnchecked().recipeManager.getRecipeFor(RecipeTypes.PRESSING, this, getLevelUnchecked()).getOrNull()

    override fun matchesRecipe(recipe: PressingRecipe) = !mainInventory[0].isEmpty && !mainInventory[1].isEmpty &&
        recipe.matches(this, getLevelUnchecked()) && canTransferItem(recipe.resultItem, mainInventory[3], this)

    @Suppress("UsePropertyAccessSyntax")
    override fun processRecipe(recipe: PressingRecipe) {
        val recipeResult = recipe.resultItem
        val resultStack = mainInventory[3]
        if (resultStack.isEmpty) mainInventory[3] = recipeResult.copy()
        else if (resultStack.sameItem(recipeResult)) resultStack.grow(recipeResult.count)
        if (!isClientSide()) setRecipeUsed(recipe)
        mainInventory[0].shrink(1)
        val stamp = mainInventory[1]
        if (stamp.hurt(1, level!!.random, null)) {
            if (stamp.damageValue >= stamp.maxDamage)
                stamp.shrink(1)
        }
        getLevelUnchecked().playSound(null, blockPos, NTechSoundsCore.pressOperate.get(), SoundSource.BLOCKS, 1.5F, 1F)
    }

    override val progressRegression: Int get() = abs(progressSpeed)
    override val progressSpeed get() = power * 25 / MAX_POWER * if (isRetracting) -1 else 1
    override val maxProgress = PRESS_TIME
    private var isRetracting = false

    override fun checkCanProgress() = super.checkCanProgress() && power >= powerNeeded

    override fun tickProgress() {
        super.tickProgress()
        if (progress <= 0) isRetracting = false
    }

    override fun resetProgress() {
        isRetracting = true
    }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        if (isLit)
            litTime--

        val fuel = mainInventory[2]
        if (!fuel.isEmpty && !isLit && recipe != null) {
            litTime = ForgeHooks.getBurnTime(fuel, null) / 8
            litDuration = litTime
            if (isLit) {
                setChanged()
                if (fuel.hasContainerItem()) mainInventory[2] = fuel.containerItem
                else if (!fuel.isEmpty) {
                    fuel.shrink(1)
                    if (fuel.isEmpty) mainInventory[2] = fuel.containerItem
                }
            }
        }

        if (isLit && power < MAX_POWER) power++ else if (!isLit && power > 0) power--

        val progress = progress
        super.serverTick(level, pos, state)
        if (progress != this.progress)
            sendContinuousUpdatePacket()
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("BurnTime", litTime)
        tag.putInt("Power", power)
        tag.putBoolean("IsRetracting", isRetracting)
        val recipesNbt = CompoundTag()
        for ((recipeID, amount) in recipesUsed)
            recipesNbt.putInt(recipeID.toString(), amount)
        tag.put("RecipesUsed", recipesNbt)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        litTime = tag.getInt("BurnTime")
        litDuration = ForgeHooks.getBurnTime(mainInventory[2], null)
        power = tag.getInt("Power")
        isRetracting = tag.getBoolean("IsRetracting")
        val recipesNbt = tag.getCompound("RecipesUsed")
        for (string in recipesNbt.allKeys)
            recipesUsed[ResourceLocation(string)] = recipesNbt.getInt(string)
    }

    override fun getContinuousUpdateTag() = super.getContinuousUpdateTag().apply {
        putInt("Progress", progress)
    }

    override fun handleContinuousUpdatePacket(tag: CompoundTag) {
        super.handleContinuousUpdatePacket(tag)
        progress = tag.getInt("Progress")
    }

    companion object {
        const val MAX_POWER = 700
        const val PRESS_TIME = 200
        val powerNeeded: Int
            get() = MAX_POWER / 3
    }
}
