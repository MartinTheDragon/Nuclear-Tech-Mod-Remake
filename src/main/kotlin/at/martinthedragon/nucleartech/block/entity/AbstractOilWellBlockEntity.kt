package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.NTechTags
import at.martinthedragon.nucleartech.api.block.entities.SoundLoopBlockEntity
import at.martinthedragon.nucleartech.block.NTechBlocks
import at.martinthedragon.nucleartech.capability.item.AccessLimitedInputItemHandler
import at.martinthedragon.nucleartech.energy.EnergyStorageExposed
import at.martinthedragon.nucleartech.energy.transferEnergy
import at.martinthedragon.nucleartech.fluid.*
import at.martinthedragon.nucleartech.item.upgrades.*
import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import at.martinthedragon.nucleartech.menu.slots.data.ByteDataSlot
import at.martinthedragon.nucleartech.menu.slots.data.FluidStackDataSlot
import at.martinthedragon.nucleartech.menu.slots.data.IntDataSlot
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.MutableComponent
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler
import kotlin.math.min

abstract class AbstractOilWellBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState, val maxEnergy: Int) : ProgressingMachineBlockEntity(type, pos, state),
    ContainerFluidHandler, SpeedUpgradeableMachine, PowerSavingUpgradeableMachine, OverdriveUpgradeableMachine, AfterBurnUpgradeableMachine
{
    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(8, ItemStack.EMPTY)

    override val upgradeSlots = 1..3

    override fun isItemValid(slot: Int, stack: ItemStack): Boolean = when(slot) {
        0 -> stack.getCapability(CapabilityEnergy.ENERGY).isPresent
        in upgradeSlots -> MachineUpgradeItem.isValidForBE(this, stack)
        4 -> stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent
        5 -> true
        6 -> stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent
        7 -> true
        else -> false
    }

    override fun inventoryChanged(slot: Int) {
        super.inventoryChanged(slot)
        checkChangedUpgradeSlot(slot)
    }

    val energyStorage = EnergyStorageExposed(maxEnergy, 10_000, 0)

    var energy: Int
        get() = energyStorage.energy
        set(value) { energyStorage.energy = value }

    val oilTank = FluidOutputTank(64_000, ::isValidOil).apply { forceFluid(FluidStack(NTechFluids.oil.source.get(), 0)) }
    val gasTank = FluidOutputTank(64_000, ::isValidGas).apply { forceFluid(FluidStack(NTechFluids.gas.source.get(), 0)) }

    protected open fun isValidOil(fluid: FluidStack): Boolean = fluid.fluid == NTechFluids.oil.source.get()
    protected open fun isValidGas(fluid: FluidStack): Boolean = fluid.fluid == NTechFluids.gas.source.get()

    open val tanks = arrayOf(oilTank, gasTank)

    override fun getTanks() = tanks.size
    override fun getFluidInTank(tank: Int): FluidStack = if (tank > tanks.lastIndex) FluidStack.EMPTY else tanks[tank].fluid
    override fun getTankCapacity(tank: Int) = if (tank > tanks.lastIndex) 0 else tanks[tank].capacity
    override fun isFluidValid(tank: Int, stack: FluidStack) = if (tank > tanks.lastIndex) false else tanks[tank].isFluidValid(stack)

    override fun fill(resource: FluidStack, action: IFluidHandler.FluidAction) = 0

    override fun drain(resource: FluidStack, action: IFluidHandler.FluidAction): FluidStack {
        val result = oilTank.drain(resource, action)
        return if (result.isEmpty) gasTank.drain(resource, action)
        else result
    }

    override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction?): FluidStack {
        val result = oilTank.drain(maxDrain, action)
        return if (result.isEmpty) gasTank.drain(maxDrain, action)
        else result
    }

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(IntDataSlot.create(this::energy, this::energy::set))
        menu.track(ByteDataSlot.create(this::status) { status = it })

        val isClient = isClientSide()
        menu.track(FluidStackDataSlot.create(oilTank, isClient))
        menu.track(FluidStackDataSlot.create(gasTank, isClient))
    }

    abstract override fun getRenderBoundingBox(): AABB

    override val shouldPlaySoundLoop = false
    override val soundLoopEvent: SoundEvent get() = throw IllegalStateException("No sound loop for oil wells")
    @Suppress("LeakingThis")
    override val soundLoopStateMachine = SoundLoopBlockEntity.NoopStateMachine(this)

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        super.serverTick(level, pos, state)

        val energyItem = mainInventory[0]
        if (!energyItem.isEmpty) transferEnergy(energyItem, energyStorage)

        var fluidTransferResult = tryFillFluidContainerAndMove(mainInventory[4], oilTank, AccessLimitedInputItemHandler(this, 5), oilTank.fluidAmount, true)
        if (fluidTransferResult.success) mainInventory[4] = fluidTransferResult.result

        fluidTransferResult = tryFillFluidContainerAndMove(mainInventory[6], gasTank, AccessLimitedInputItemHandler(this, 7), gasTank.fluidAmount, true)
        if (fluidTransferResult.success) mainInventory[6] = fluidTransferResult.result

        val toBurn = min(gasTank.fluidAmount, afterBurnUpgradeLevel * 10)
        if (toBurn > 0) {
            energyStorage.receiveEnergy(gasTank.drain(toBurn, IFluidHandler.FluidAction.EXECUTE).amount * 5, false)
        }

        if (energy < consumption) status = STATUS_NO_POWER
        else if (!canProgress) status = STATUS_ERROR
    }

    override fun checkCanProgress() = energy >= consumption && oilTank.space > 0 && gasTank.space > 0

    override val maxSpeedUpgradeLevel = 3
    override var speedUpgradeLevel = 0

    override val maxPowerSavingUpgradeLevel = 3
    override var powerSavingUpgradeLevel = 0

    override val maxOverdriveUpgradeLevel = 3
    override var overdriveUpgradeLevel = 0

    override val maxAfterBurnUpgradeLevel = 3
    override var afterBurnUpgradeLevel = 0

    override fun resetUpgrades() {
        super<SpeedUpgradeableMachine>.resetUpgrades()
        super<PowerSavingUpgradeableMachine>.resetUpgrades()
        super<OverdriveUpgradeableMachine>.resetUpgrades()
        super<AfterBurnUpgradeableMachine>.resetUpgrades()
    }

    override fun getUpgradeInfoForEffect(effect: MachineUpgradeItem.UpgradeEffect<*>): List<MutableComponent> = when(effect) {
        is SpeedUpgrade -> listOf(LangKeys.UPGRADE_INFO_DELAY.format("-${effect.tier * 25}%"), LangKeys.UPGRADE_INFO_CONSUMPTION.format("+${effect.tier * 25}%"))
        is PowerSavingUpgrade -> listOf(LangKeys.UPGRADE_INFO_CONSUMPTION.format("-${effect.tier * 25}%"), LangKeys.UPGRADE_INFO_DELAY.format("+${effect.tier * 10}%"))
        is OverdriveUpgrade -> listOf(LangKeys.UPGRADE_INFO_DELAY.format("÷${effect.tier + 1}"), LangKeys.UPGRADE_INFO_CONSUMPTION.format("×${effect.tier + 1}"))
        is AfterBurnUpgrade -> listOf(LangKeys.UPGRADE_INFO_BURN_GAS.format(effect.tier * 10, "${effect.tier * 50}HE/t"))
        else -> emptyList()
    }

    override fun getSupportedUpgrades() = listOf(SpeedUpgrade(maxSpeedUpgradeLevel), PowerSavingUpgrade(maxPowerSavingUpgradeLevel), OverdriveUpgrade(maxOverdriveUpgradeLevel), AfterBurnUpgrade(maxAfterBurnUpgradeLevel))

    abstract val baseConsumption: Int
    abstract val baseDelay: Int

    protected val consumption get() = (baseConsumption + (baseConsumption / 4 * speedUpgradeLevel) - (baseConsumption / 4 * powerSavingUpgradeLevel)) * (overdriveUpgradeLevel + 1)
    protected val delay get() = ((baseDelay - (baseDelay / 4 * speedUpgradeLevel) + (baseDelay / 10 * powerSavingUpgradeLevel)) / (overdriveUpgradeLevel + 1)).coerceAtLeast(1)

    override val maxProgress get() = delay

    override fun tickProgress() {
        MachineUpgradeItem.applyUpgrades(this, mainInventory.subList(1, 4))
        super.tickProgress()
        energy -= consumption
    }

    var status: Byte = 0
        protected set

    override fun onProgressFinished() {
        status = STATUS_OK
        for (y in drillStart downTo drillDepth) {
            if (!levelUnchecked.getBlockState(blockPos.atY(y)).`is`(NTechBlocks.oilPipe.get())) {
                if (trySuck(y))
                    break
                else {
                    status = STATUS_LOOKING_FOR_OIL
                    tryDrill(y)
                }
                break
            }

            if (y == drillDepth) status = STATUS_NO_OIL_SOURCE
        }
    }

    protected open val drillStart get() = blockPos.y - 1
    protected open val drillDepth get() = getLevelUnchecked().minBuildHeight + 5

    protected open fun tryDrill(y: Int) {
        val pos = blockPos.atY(y)
        if (levelUnchecked.getBlockState(pos).getExplosionResistance(levelUnchecked, pos, null) < 1000) {
            onDrill(y)
            levelUnchecked.setBlockAndUpdate(pos, NTechBlocks.oilPipe.get().defaultBlockState())
        } else status = STATUS_ERROR
    }

    protected val trace = mutableSetOf<BlockPos>()

    protected fun trySuck(y: Int): Boolean {
        val pos = blockPos.atY(y)
        if (!canSuckBlock(levelUnchecked.getBlockState(pos))) return false

        trace.clear()
        return suckRecursive(pos, 0)
    }

    protected fun suckRecursive(pos: BlockPos, recursionDepth: Int): Boolean {
        if (pos in trace) return false
        trace += pos
        if (recursionDepth > 64) return false

        val block = levelUnchecked.getBlockState(pos)

        if (isEmptyDeposit(block)) {
            for (direction in Direction.values().toList().shuffled()) {
                if (suckRecursive(pos.relative(direction), recursionDepth + 1))
                    return true
            }
        } else if (isOilDeposit(block)) {
            doSuck(pos, block)
            return true
        }

        return false
    }

    protected open fun doSuck(pos: BlockPos, oilDeposit: BlockState) {
        onSuck(pos)
        levelUnchecked.setBlockAndUpdate(pos, emptyOilDeposit(oilDeposit))
    }

    protected open fun canSuckBlock(block: BlockState) = isOilDeposit(block) || isEmptyDeposit(block)
    protected open fun isOilDeposit(block: BlockState) = block.`is`(NTechTags.Blocks.ORES_OIL)
    protected open fun isEmptyDeposit(block: BlockState) = block.`is`(NTechBlocks.emptyOilDeposit.get()) || block.`is`(NTechBlocks.emptyDeepslateOilDeposit.get())

    protected open fun emptyOilDeposit(block: BlockState) = when {
        block.`is`(NTechBlocks.deepslateOilDeposit.get()) -> NTechBlocks.emptyDeepslateOilDeposit.get().defaultBlockState()
        else -> NTechBlocks.emptyOilDeposit.get().defaultBlockState()
    }

    protected open fun onDrill(y: Int) {}
    protected open fun onSuck(pos: BlockPos) {}

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("Energy", energy)

        tag.put("OilTank", oilTank.writeToNBT(CompoundTag()))
        tag.put("GasTank", gasTank.writeToNBT(CompoundTag()))
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        energy = tag.getInt("Energy")

        oilTank.readFromNBT(tag.getCompound("OilTank"))
        gasTank.readFromNBT(tag.getCompound("GasTank"))
    }

    init {
        registerCapabilityHandler(CapabilityEnergy.ENERGY, ::energyStorage)
        registerCapabilityHandler(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, { this })
    }

    companion object {
        const val STATUS_OK: Byte = 0
        const val STATUS_NO_OIL_SOURCE: Byte = 1
        const val STATUS_ERROR: Byte = 2
        const val STATUS_OUT_OF_FLUID: Byte = 3
        const val STATUS_LOOKING_FOR_OIL: Byte = 4
        const val STATUS_NO_POWER: Byte = 5
    }
}
