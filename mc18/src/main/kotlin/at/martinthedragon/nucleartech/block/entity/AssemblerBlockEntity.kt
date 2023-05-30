package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.NTechSoundsCore
import at.martinthedragon.nucleartech.capability.item.AccessLimitedInputItemHandler
import at.martinthedragon.nucleartech.capability.item.AccessLimitedOutputItemHandler
import at.martinthedragon.nucleartech.energy.EnergyStorageExposed
import at.martinthedragon.nucleartech.energy.transferEnergy
import at.martinthedragon.nucleartech.item.AssemblyTemplateItem
import at.martinthedragon.nucleartech.item.canTransferItem
import at.martinthedragon.nucleartech.item.upgrades.MachineUpgradeItem
import at.martinthedragon.nucleartech.item.upgrades.OverdriveUpgrade
import at.martinthedragon.nucleartech.item.upgrades.PowerSavingUpgrade
import at.martinthedragon.nucleartech.item.upgrades.SpeedUpgrade
import at.martinthedragon.nucleartech.menu.AssemblerMenu
import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import at.martinthedragon.nucleartech.menu.slots.data.IntDataSlot
import at.martinthedragon.nucleartech.recipe.AssemblyRecipe
import at.martinthedragon.nucleartech.recipe.containerSatisfiesRequirements
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.items.CapabilityItemHandler

class AssemblerBlockEntity(pos: BlockPos, state: BlockState) : RecipeMachineBlockEntity<AssemblyRecipe>(BlockEntityTypes.assemblerBlockEntityType.get(), pos, state),
    SpeedUpgradeableMachine, PowerSavingUpgradeableMachine, OverdriveUpgradeableMachine, IODelegatedBlockEntity
{
    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(18, ItemStack.EMPTY)

    override val upgradeSlots = 1..3

    override fun isItemValid(slot: Int, stack: ItemStack): Boolean = when (slot) {
        0 -> stack.getCapability(CapabilityEnergy.ENERGY).isPresent
        in upgradeSlots -> MachineUpgradeItem.isValidForBE(this, stack)
        4 -> stack.item is AssemblyTemplateItem
        else -> true
    }

    override fun inventoryChanged(slot: Int) {
        super.inventoryChanged(slot)
        checkChangedUpgradeSlot(slot)
        if (slot == 4) checkCanProgress()
    }

    private val energyStorage = EnergyStorageExposed(MAX_ENERGY, 1000, 0)

    var energy: Int
        get() = energyStorage.energyStored
        set(value) { energyStorage.energy = value }

    override fun createMenu(windowID: Int, inventory: Inventory) = AssemblerMenu(windowID, inventory, this)
    override val defaultName = LangKeys.CONTAINER_ASSEMBLER.get()

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(IntDataSlot.create(this::energy, this::energy::set))
        menu.track(IntDataSlot.create(this::progress) { progress = it })
        menu.track(IntDataSlot.create(this::maxProgress, this::maxProgress::set))
    }

    override fun getRenderBoundingBox(): AABB = AABB(blockPos.offset(-3, 0, -3), blockPos.offset(3, 2, 3))

    var renderTick: Int = 0
        private set

    override val soundLoopEvent = NTechSoundsCore.assemblerOperate.get()

    override fun clientTick(level: Level, pos: BlockPos, state: BlockState) {
        super.clientTick(level, pos, state)

        if (canProgress && !isRemoved) {
            renderTick++
            if (renderTick >= 1800) renderTick = 0
        } else renderTick = 0
    }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        super.serverTick(level, pos, state)

        val energyItem = mainInventory[0]
        if (!energyItem.isEmpty) transferEnergy(energyItem, energyStorage)
    }

    override fun checkCanProgress() = energy >= consumption && super.checkCanProgress()

    override val maxSpeedUpgradeLevel = 3
    override var speedUpgradeLevel = 0

    override val maxPowerSavingUpgradeLevel = 3
    override var powerSavingUpgradeLevel = 0

    override val maxOverdriveUpgradeLevel = 9
    override var overdriveUpgradeLevel = 0


    override fun resetUpgrades() {
        super<SpeedUpgradeableMachine>.resetUpgrades()
        super<PowerSavingUpgradeableMachine>.resetUpgrades()
        super<OverdriveUpgradeableMachine>.resetUpgrades()
    }

    override fun getUpgradeInfoForEffect(effect: MachineUpgradeItem.UpgradeEffect<*>) = when (effect) {
        is SpeedUpgrade -> listOf(LangKeys.UPGRADE_INFO_DELAY.format("-${effect.tier * 25}%"), LangKeys.UPGRADE_INFO_CONSUMPTION.format("+${effect.tier * 300}HE/t"))
        is PowerSavingUpgrade -> listOf(LangKeys.UPGRADE_INFO_CONSUMPTION.format("-${effect.tier * 30}HE/t"), LangKeys.UPGRADE_INFO_DELAY.format("+${effect.tier * 5}%"))
        is OverdriveUpgrade -> listOf(LangKeys.UPGRADE_INFO_DELAY.format("÷${effect.tier + 1}"), LangKeys.UPGRADE_INFO_CONSUMPTION.format("×${effect.tier + 1}"))
        else -> emptyList()
    }

    override fun getSupportedUpgrades() = listOf(SpeedUpgrade(maxSpeedUpgradeLevel), PowerSavingUpgrade(maxPowerSavingUpgradeLevel), OverdriveUpgrade(maxOverdriveUpgradeLevel))

    private val consumption get() = (100 + (speedUpgradeLevel * 300) - (powerSavingUpgradeLevel * 30)) * (overdriveUpgradeLevel + 1)
    private val speed get() = ((100 - (speedUpgradeLevel  * 25) + (powerSavingUpgradeLevel * 5)) / (overdriveUpgradeLevel + 1)).coerceAtLeast(1)

    override var maxProgress = 100
        private set

    override fun tickProgress() {
        MachineUpgradeItem.applyUpgrades(this, mainInventory.subList(1, 4))
        super.tickProgress()
        val recipe = recipe
        if (recipe != null)
            maxProgress = recipe.duration * speed / 100
        energy -= consumption
    }

    override fun findPossibleRecipe() = AssemblyTemplateItem.getRecipeFromStack(mainInventory[4], getLevelUnchecked().recipeManager)

    override fun matchesRecipe(recipe: AssemblyRecipe) = recipe.matches(SimpleContainer(*mainInventory.subList(5, 17).toTypedArray()), getLevelUnchecked()) && canTransferItem(recipe.resultItem, mainInventory[17])

    override fun processRecipe(recipe: AssemblyRecipe) {
        recipe.ingredientsList.containerSatisfiesRequirements(SimpleContainer(*mainInventory.subList(5, 17).toTypedArray()), true)
        val recipeResult = recipe.resultItem
        val resultStack = mainInventory[17]
        if (resultStack.isEmpty) mainInventory[17] = recipeResult.copy()
        else if (resultStack.sameItem(recipeResult)) resultStack.grow(recipeResult.count)
        // else ???
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        energy = tag.getInt("Energy")
        maxProgress = tag.getInt("MaxProgress")
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("Energy", energy)
        tag.putInt("MaxProgress", maxProgress)
    }


    private val inputInventory = AccessLimitedInputItemHandler(this, 5..16)
    private val outputInventory = AccessLimitedOutputItemHandler(this, 17)

    init {
        registerCapabilityHandler(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, this::inputInventory, Direction.WEST)
        registerCapabilityHandler(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, this::outputInventory, Direction.EAST)
        registerCapabilityHandler(CapabilityEnergy.ENERGY, this::energyStorage)
    }

    override val ioConfigurations = IODelegatedBlockEntity.fromTriples(blockPos, getHorizontalBlockRotation(),
        Triple(BlockPos(-1, 0, 1), Direction.WEST, listOf(IOConfiguration(IOConfiguration.Mode.IN, IODelegatedBlockEntity.DEFAULT_ITEM_ACTION))),
        Triple(BlockPos(2, 0, 0), Direction.EAST, listOf(IOConfiguration(IOConfiguration.Mode.OUT, IODelegatedBlockEntity.DEFAULT_ITEM_ACTION))),
        Triple(BlockPos(0, 0, -1), Direction.NORTH, listOf(IOConfiguration(IOConfiguration.Mode.IN, IODelegatedBlockEntity.DEFAULT_ENERGY_ACTION))),
        Triple(BlockPos(1, 0, -1), Direction.NORTH, listOf(IOConfiguration(IOConfiguration.Mode.IN, IODelegatedBlockEntity.DEFAULT_ENERGY_ACTION))),
        Triple(BlockPos(0, 0, 2), Direction.SOUTH, listOf(IOConfiguration(IOConfiguration.Mode.IN, IODelegatedBlockEntity.DEFAULT_ENERGY_ACTION))),
        Triple(BlockPos(1, 0, 2), Direction.SOUTH, listOf(IOConfiguration(IOConfiguration.Mode.IN, IODelegatedBlockEntity.DEFAULT_ENERGY_ACTION))),
    )

    companion object {
        const val MAX_ENERGY = 100_000
    }
}
