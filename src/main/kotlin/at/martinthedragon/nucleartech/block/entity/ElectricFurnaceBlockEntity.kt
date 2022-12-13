package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.api.block.entities.ExperienceRecipeResultBlockEntity
import at.martinthedragon.nucleartech.api.block.entities.SoundLoopBlockEntity
import at.martinthedragon.nucleartech.energy.EnergyStorageExposed
import at.martinthedragon.nucleartech.energy.transferEnergy
import at.martinthedragon.nucleartech.item.canTransferItem
import at.martinthedragon.nucleartech.item.upgrades.MachineUpgradeItem
import at.martinthedragon.nucleartech.item.upgrades.PowerSavingUpgrade
import at.martinthedragon.nucleartech.item.upgrades.SpeedUpgrade
import at.martinthedragon.nucleartech.menu.ElectricFurnaceMenu
import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import at.martinthedragon.nucleartech.menu.slots.data.BooleanDataSlot
import at.martinthedragon.nucleartech.menu.slots.data.IntDataSlot
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.RecipeHolder
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.AbstractCookingRecipe
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.item.crafting.SmeltingRecipe
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.energy.CapabilityEnergy

class ElectricFurnaceBlockEntity(pos: BlockPos, state: BlockState) : RecipeMachineBlockEntity<SmeltingRecipe>(BlockEntityTypes.electricFurnaceBlockEntityType.get(), pos, state),
    RecipeHolder, ExperienceRecipeResultBlockEntity, SpeedUpgradeableMachine, PowerSavingUpgradeableMachine
{
    var energy: Int
        get() = energyStorage.energyStored
        private set(value) { energyStorage.energy = value }

    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(4, ItemStack.EMPTY)

    override val upgradeSlots = 3..3

    override fun isItemValid(slot: Int, stack: ItemStack): Boolean =
        when (slot) {
            1 -> stack.getCapability(CapabilityEnergy.ENERGY).isPresent
            3 -> MachineUpgradeItem.isValidForBE(this, stack)
            else -> true
        }

    override fun inventoryChanged(slot: Int) {
        super.inventoryChanged(slot)
        checkChangedUpgradeSlot(slot)
    }

    val energyStorage = EnergyStorageExposed(MAX_ENERGY, ENERGY_TRANSFER_RATE, 0)

    override fun createMenu(windowID: Int, inventory: Inventory) = ElectricFurnaceMenu(windowID, inventory, this)
    override val defaultName = LangKeys.CONTAINER_ELECTRIC_FURNACE.get()

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(IntDataSlot.create(this::progress) { progress = it })
        menu.track(IntDataSlot.create(this::speedUpgradeLevel, this::speedUpgradeLevel::set))
        menu.track(IntDataSlot.create(this::powerSavingUpgradeLevel, this::powerSavingUpgradeLevel::set))
        menu.track(IntDataSlot.create(this::energy, this::energy::set))
        menu.track(BooleanDataSlot.create(this::canProgress) { canProgress = it })
    }

    override val shouldPlaySoundLoop = false
    override val soundLoopEvent get() = throw IllegalStateException("No sound loop for electric furnace")
    override val soundLoopStateMachine = SoundLoopBlockEntity.NoopStateMachine(this)

    override val maxSpeedUpgradeLevel = 3
    override var speedUpgradeLevel = 0

    override val maxPowerSavingUpgradeLevel = 3
    override var powerSavingUpgradeLevel = 0

    override fun resetUpgrades() {
        super<SpeedUpgradeableMachine>.resetUpgrades()
        super<PowerSavingUpgradeableMachine>.resetUpgrades()
    }

    override fun getUpgradeInfoForEffect(effect: MachineUpgradeItem.UpgradeEffect<*>) = when (effect) {
        is SpeedUpgrade -> listOf(LangKeys.UPGRADE_INFO_DELAY.format("-${effect.tier * 25}%"), LangKeys.UPGRADE_INFO_CONSUMPTION.format("+${effect.tier * 50}HE/t"))
        is PowerSavingUpgrade -> listOf(LangKeys.UPGRADE_INFO_CONSUMPTION.format("-${effect.tier * 15}HE/t"), LangKeys.UPGRADE_INFO_DELAY.format("+${effect.tier * 10}%"))
        else -> emptyList()
    }

    override fun getSupportedUpgrades() = listOf(SpeedUpgrade(maxSpeedUpgradeLevel), PowerSavingUpgrade(maxPowerSavingUpgradeLevel))

    private val consumption get() = 50 + (speedUpgradeLevel * 50) - (powerSavingUpgradeLevel * 15)

    override val maxProgress get() = 100 - (speedUpgradeLevel * 25) + (powerSavingUpgradeLevel * 10)

    override val progressRegression = 2

    override fun checkCanProgress() = energy > MIN_ENERGY_THRESHOLD && super.checkCanProgress()

    override fun tickProgress() {
        MachineUpgradeItem.applyUpgrades(this, listOf(mainInventory[3]))
        super.tickProgress()
        energy -= consumption
    }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        val energyItemSlot = mainInventory[1]
        if (!energyItemSlot.isEmpty) transferEnergy(energyItemSlot, energyStorage)

        val wasCooking = canProgress

        super.serverTick(level, pos, state)

        if (wasCooking != canProgress) {
            level.setBlockAndUpdate(worldPosition, level.getBlockState(worldPosition).setValue(BlockStateProperties.LIT, canProgress))
        }
    }

    override fun findPossibleRecipe(): SmeltingRecipe = getLevelUnchecked().recipeManager.getRecipeFor(RecipeType.SMELTING, this, getLevelUnchecked()).orElse(null)

    override fun matchesRecipe(recipe: SmeltingRecipe) =
        if (mainInventory[0].isEmpty) false
        else canTransferItem(recipe.resultItem, mainInventory[2], this) && recipe.matches(this, getLevelUnchecked())

    @Suppress("UsePropertyAccessSyntax")
    override fun processRecipe(recipe: SmeltingRecipe) {
        val result = recipe.resultItem
        val resultsStack = mainInventory[2]
        if (resultsStack.isEmpty) mainInventory[2] = result.copy()
        else resultsStack.grow(result.count)
        setRecipeUsed(recipe)
        mainInventory[0].shrink(1)
    }

    private val recipesUsed = Object2IntOpenHashMap<ResourceLocation>()

    override fun getRecipeUsed(): Recipe<*>? = null

    override fun setRecipeUsed(recipe: Recipe<*>?) {
        if (recipe == null) return
        recipesUsed.addTo(recipe.id, 1)
    }

    override fun awardUsedRecipes(player: Player) {}

    override fun clearUsedRecipes() = recipesUsed.clear()

    override fun getExperienceToDrop(player: Player?): Float =
        recipesUsed.object2IntEntrySet().mapNotNull { (recipeID, amount) ->
            (level!!.recipeManager.byKey(recipeID).orElse(null) as? AbstractCookingRecipe)?.experience?.times(amount)
        }.sum() / 2F

    override fun getRecipesToAward(player: Player): List<Recipe<*>> =
        recipesUsed.keys.mapNotNull { player.level.recipeManager.byKey(it).orElse(null) }

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
        const val MAX_ENERGY = 400_000
        const val MIN_ENERGY_THRESHOLD = 500
        const val ENERGY_TRANSFER_RATE = MAX_ENERGY / 100
    }
}
