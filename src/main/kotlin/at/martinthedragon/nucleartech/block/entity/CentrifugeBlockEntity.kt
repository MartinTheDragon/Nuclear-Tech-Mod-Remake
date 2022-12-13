package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.SoundEvents
import at.martinthedragon.nucleartech.capability.item.AccessLimitedInputItemHandler
import at.martinthedragon.nucleartech.energy.EnergyStorageExposed
import at.martinthedragon.nucleartech.energy.transferEnergy
import at.martinthedragon.nucleartech.extensions.subView
import at.martinthedragon.nucleartech.item.insertAllItemsStacked
import at.martinthedragon.nucleartech.item.upgrades.MachineUpgradeItem
import at.martinthedragon.nucleartech.item.upgrades.OverdriveUpgrade
import at.martinthedragon.nucleartech.item.upgrades.PowerSavingUpgrade
import at.martinthedragon.nucleartech.item.upgrades.SpeedUpgrade
import at.martinthedragon.nucleartech.menu.CentrifugeMenu
import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import at.martinthedragon.nucleartech.menu.slots.data.IntDataSlot
import at.martinthedragon.nucleartech.recipe.CentrifugeRecipe
import at.martinthedragon.nucleartech.recipe.RecipeTypes
import at.martinthedragon.nucleartech.recipe.containerSatisfiesRequirements
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraftforge.energy.CapabilityEnergy
import kotlin.jvm.optionals.getOrNull

class CentrifugeBlockEntity(pos: BlockPos, state: BlockState) : RecipeMachineBlockEntity<CentrifugeRecipe>(BlockEntityTypes.centrifugeBlockEntityType.get(), pos, state),
    SpeedUpgradeableMachine, PowerSavingUpgradeableMachine, OverdriveUpgradeableMachine
{
    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(8, ItemStack.EMPTY)

    override val upgradeSlots = 1..2

    override fun isItemValid(slot: Int, stack: ItemStack): Boolean = when (slot) {
        0 -> stack.getCapability(CapabilityEnergy.ENERGY).isPresent
        in upgradeSlots -> MachineUpgradeItem.isValidForBE(this, stack)
        else -> true
    }

    override fun inventoryChanged(slot: Int) {
        super.inventoryChanged(slot)
        checkChangedUpgradeSlot(slot)
    }

    override val soundLoopEvent get() = SoundEvents.centrifugeOperate.get()
    override val defaultName = LangKeys.CONTAINER_CENTRIFUGE.get()

    override fun createMenu(windowID: Int, inventory: Inventory) = CentrifugeMenu(windowID, inventory, this)

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(IntDataSlot.create(this::energy, this::energy::set))
        menu.track(IntDataSlot.create(this::progress) { progress = it })
    }

    override fun getRenderBoundingBox() = AABB(blockPos, blockPos.offset(1, 4, 1))

    val energyStorage = EnergyStorageExposed(MAX_ENERGY)

    var energy: Int
        get() = energyStorage.energyStored
        set(value) { energyStorage.energy = value }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        super.serverTick(level, pos, state)

        val energyItem = mainInventory[0]
        if (!energyItem.isEmpty) transferEnergy(energyItem, energyStorage)
    }

    override fun checkCanProgress() = super.checkCanProgress() && energy >= consumption

    override val maxSpeedUpgradeLevel = 3
    override var speedUpgradeLevel = 0

    override val maxPowerSavingUpgradeLevel = 3
    override var powerSavingUpgradeLevel = 0

    override val maxOverdriveUpgradeLevel = 3
    override var overdriveUpgradeLevel = 0

    override fun resetUpgrades() {
        super<SpeedUpgradeableMachine>.resetUpgrades()
        super<PowerSavingUpgradeableMachine>.resetUpgrades()
        super<OverdriveUpgradeableMachine>.resetUpgrades()
    }

    override fun getUpgradeInfoForEffect(effect: MachineUpgradeItem.UpgradeEffect<*>) = when (effect) {
        is SpeedUpgrade -> listOf(LangKeys.UPGRADE_INFO_DELAY.format("÷${effect.tier + 1}"), LangKeys.UPGRADE_INFO_CONSUMPTION.format("+${effect.tier * 200}HE/t"))
        is PowerSavingUpgrade -> listOf(LangKeys.UPGRADE_INFO_CONSUMPTION.format("÷${effect.tier + 1}"))
        is OverdriveUpgrade -> listOf(LangKeys.UPGRADE_INFO_DELAY.format("÷${effect.tier * 5}"), LangKeys.UPGRADE_INFO_CONSUMPTION.format("+${effect.tier * 10_000}HE/t"))
        else -> emptyList()
    }

    override fun getSupportedUpgrades() = listOf(SpeedUpgrade(maxSpeedUpgradeLevel), PowerSavingUpgrade(powerSavingUpgradeLevel), OverdriveUpgrade(maxOverdriveUpgradeLevel))

    override val maxProgress = 200

    override val progressSpeed: Int get() = (1 + speedUpgradeLevel) * (overdriveUpgradeLevel * 5).coerceAtLeast(1)
    private val consumption get() = (200 + speedUpgradeLevel * 200 + overdriveUpgradeLevel * 10_000) / (1 + powerSavingUpgradeLevel)

    override fun tickProgress() {
        MachineUpgradeItem.applyUpgrades(this, mainInventory.subList(1, 3))
        super.tickProgress()
        energy -= consumption + 1
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun findPossibleRecipe() = levelUnchecked.recipeManager.getRecipeFor(RecipeTypes.CENTRIFUGE, this, levelUnchecked).getOrNull()

    override fun matchesRecipe(recipe: CentrifugeRecipe) = recipe.matches(this, levelUnchecked) && insertAllItemsStacked(AccessLimitedInputItemHandler(this, 4..7), recipe.resultsList, true).isEmpty()

    override fun processRecipe(recipe: CentrifugeRecipe) {
        listOf(recipe.ingredient).containerSatisfiesRequirements(subView(3, 4), true)
        insertAllItemsStacked(AccessLimitedInputItemHandler(this, 4..7), recipe.resultsList, false)
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("Energy", energy)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        energy = tag.getInt("Energy")
    }

    override fun registerCapabilityHandlers() {
        super.registerCapabilityHandlers()
        registerCapabilityHandler(CapabilityEnergy.ENERGY, this::energyStorage)
    }

    companion object {
        const val MAX_ENERGY = 100_000
    }
}
