package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.api.block.entities.ExperienceRecipeResultBlockEntity
import at.martinthedragon.nucleartech.capability.item.AccessLimitedInputItemHandler
import at.martinthedragon.nucleartech.energy.EnergyStorageExposed
import at.martinthedragon.nucleartech.energy.transferEnergy
import at.martinthedragon.nucleartech.extensions.subView
import at.martinthedragon.nucleartech.item.NTechItems
import at.martinthedragon.nucleartech.item.ShredderBladeItem
import at.martinthedragon.nucleartech.item.insertAllItemsStacked
import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import at.martinthedragon.nucleartech.menu.ShredderMenu
import at.martinthedragon.nucleartech.menu.slots.data.BooleanDataSlot
import at.martinthedragon.nucleartech.menu.slots.data.IntDataSlot
import at.martinthedragon.nucleartech.recipe.RecipeTypes
import at.martinthedragon.nucleartech.recipe.ShreddingRecipe
import at.martinthedragon.nucleartech.recipe.getItems
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.RecipeHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.items.ItemHandlerHelper
import kotlin.jvm.optionals.getOrNull

class ShredderBlockEntity(pos: BlockPos, state: BlockState) : ProgressingMachineBlockEntity(BlockEntityTypes.shredderBlockEntityType.get(), pos, state), RecipeHolder, ExperienceRecipeResultBlockEntity {
    var energy: Int
        get() = energyStorage.energyStored
        private set(value) { energyStorage.energy = value }

    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(30, ItemStack.EMPTY)

    override fun isItemValid(slot: Int, stack: ItemStack): Boolean = when (slot) {
        9 -> stack.getCapability(CapabilityEnergy.ENERGY).isPresent
        10, 11 -> stack.item is ShredderBladeItem
        else -> true
    }

    override fun inventoryChanged(slot: Int) {
        if (slot in 0..8 || slot in 12..29) canProgressCached = null
    }

    private val energyStorage = EnergyStorageExposed(MAX_ENERGY, ENERGY_TRANSFER_RATE, 0)

    override fun createMenu(windowID: Int, inventory: Inventory) = ShredderMenu(windowID, inventory, this)
    override val defaultName = LangKeys.CONTAINER_SHREDDER.get()

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(IntDataSlot.create(this::energy) { energy = it })
        menu.track(IntDataSlot.create(this::progress) { progress = it })
        menu.track(BooleanDataSlot.create(this::canProgress) { canProgress = it })
    }

    private val recipesUsed = Object2IntOpenHashMap<ResourceLocation>()

    override fun setRecipeUsed(recipe: Recipe<*>?) {
        if (recipe == null) return
        recipesUsed.addTo(recipe.id, 1)
    }

    override fun getRecipeUsed(): Recipe<*>? = null

    override fun getExperienceToDrop(player: Player?): Float =
        recipesUsed.object2IntEntrySet().mapNotNull { (recipeID, amount) ->
            (level!!.recipeManager.byKey(recipeID).orElse(null) as? ShreddingRecipe)?.experience?.times(amount)
        }.sum()

    override fun getRecipesToAward(player: Player): List<Recipe<*>> =
        recipesUsed.keys.mapNotNull { player.level.recipeManager.byKey(it).orElse(null) }

    override fun clearUsedRecipes() {
        recipesUsed.clear()
    }

    override val soundLoopEvent: SoundEvent = SoundEvents.MINECART_RIDING
    override val soundLoopPitch = .75F

    override val maxProgress = SHREDDING_TIME

    private var canProgressCached: Boolean? = null

    override fun checkCanProgress() = energy > MIN_ENERGY_THRESHOLD &&
        !subView(0..8).isEmpty &&
        isValidShredderBlade(mainInventory[10]) && isValidShredderBlade(mainInventory[11]) &&
        (canProgressCached == true || canProgressCached == null && process(true).also { canProgressCached = it })

    private fun isValidShredderBlade(stack: ItemStack) = stack.item is ShredderBladeItem && (stack.damageValue < stack.maxDamage || !stack.isDamageableItem)

    override fun onProgressFinished() {
        process(false)
        canProgressCached = null
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun process(simulate: Boolean): Boolean {
        val input = subView(0..8)
        val output = AccessLimitedInputItemHandler(this, 12..29)
        val level = getLevelUnchecked()
        val recipeManager = level.recipeManager
        val container = SimpleContainer(1)
        val resultItems = mutableListOf<ItemStack>()
        var scrapCount = 0
        for (item in input.getItems(false)) { // TODO still a bit of a mess, but not as bad
            container.setItem(0, item.copy())
            val recipe = recipeManager.getRecipeFor(RecipeTypes.SHREDDING, container, level).getOrNull()
            if (recipe == null) {
                if (!item.isEmpty && ItemHandlerHelper.insertItemStacked(output, ItemStack(NTechItems.scrap.get(), scrapCount + 1), true).isEmpty) {
                    if (!simulate) item.shrink(1)
                    scrapCount++
                }
                continue
            } else if (insertAllItemsStacked(output, resultItems + recipe.resultItem, true).isEmpty()) {
                resultItems += recipe.resultItem
                if (!simulate) {
                    item.shrink(1)
                    recipesUsed.addTo(recipe.recipeID, 1)
                }
            }
        }
        if (!simulate) {
            damageShredderBlade(mainInventory[10])
            damageShredderBlade(mainInventory[11])
        }
        if (scrapCount > 0) resultItems += ItemStack(NTechItems.scrap.get(), scrapCount)
        val remaining = insertAllItemsStacked(output, resultItems, simulate)
        return remaining != resultItems
    }

    private fun damageShredderBlade(stack: ItemStack) {
        if (stack.isDamageableItem && stack.damageValue < stack.maxDamage)
            stack.damageValue++
    }

    override fun tickProgress() {
        super.tickProgress()
        energy -= ENERGY_CONSUMPTION_RATE
    }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        super.serverTick(level, pos, state)

        val energyItemSlot = mainInventory[9]
        if (!energyItemSlot.isEmpty) transferEnergy(energyItemSlot, energyStorage)
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("Energy", energy)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        energy = tag.getInt("Energy")
    }

    init {
        registerCapabilityHandler(CapabilityEnergy.ENERGY, this::energyStorage)
    }

    companion object {
        const val MAX_ENERGY = 40_000
        const val SHREDDING_TIME = 60
        const val ENERGY_CONSUMPTION_RATE = 20
        const val MIN_ENERGY_THRESHOLD = SHREDDING_TIME * ENERGY_CONSUMPTION_RATE / 10
        const val ENERGY_TRANSFER_RATE = MAX_ENERGY / 100
    }
}
