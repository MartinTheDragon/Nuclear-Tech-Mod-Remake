package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.api.block.entities.SoundLoopBlockEntity
import at.martinthedragon.nucleartech.api.block.entities.TickingServerBlockEntity
import at.martinthedragon.nucleartech.api.fluid.trait.getTraitForFluidStack
import at.martinthedragon.nucleartech.capability.item.AccessLimitedInputItemHandler
import at.martinthedragon.nucleartech.capability.item.AccessLimitedOutputItemHandler
import at.martinthedragon.nucleartech.energy.EnergyStorageExposed
import at.martinthedragon.nucleartech.energy.transferEnergy
import at.martinthedragon.nucleartech.fluid.*
import at.martinthedragon.nucleartech.fluid.trait.CoolableFluidTrait
import at.martinthedragon.nucleartech.fluid.trait.FluidTraitManager
import at.martinthedragon.nucleartech.item.FluidIdentifierItem
import at.martinthedragon.nucleartech.item.canTransferItem
import at.martinthedragon.nucleartech.item.transferItemsBetweenItemHandlers
import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import at.martinthedragon.nucleartech.menu.TurbineMenu
import at.martinthedragon.nucleartech.menu.slots.data.FluidStackDataSlot
import at.martinthedragon.nucleartech.menu.slots.data.IntDataSlot
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.fluids.FluidAttributes
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler
import kotlin.math.min

class TurbineBlockEntity(pos: BlockPos, state: BlockState) : BaseMachineBlockEntity(BlockEntityTypes.turbineBlockEntityType.get(), pos, state), TickingServerBlockEntity {
    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(7, ItemStack.EMPTY)
    override val defaultName get() = LangKeys.CONTAINER_TURBINE.get()

    override fun createMenu(windowID: Int, inventory: Inventory) = TurbineMenu(windowID, inventory, this)

    override fun isItemValid(slot: Int, stack: ItemStack): Boolean = when (slot) {
        0 -> FluidTraitManager.getTraitForFluidStack<CoolableFluidTrait>(FluidStack(FluidIdentifierItem.getFluid(stack), 1000))?.let { it.trait.getEfficiency(it, CoolableFluidTrait.CoolingType.Turbine) > 0F } == true
        1 -> true
        2 -> stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent
        3 -> true
        4 -> stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent
        5 -> false
        6 -> stack.getCapability(CapabilityEnergy.ENERGY).isPresent
        else -> false
    }

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(IntDataSlot.create(this::energy, this::energy::set))
        val isClient = isClientSide()
        menu.track(FluidStackDataSlot.create(inputTank, isClient))
        menu.track(FluidStackDataSlot.create(outputTank, isClient))
    }

    override val shouldPlaySoundLoop = false
    override val soundLoopEvent get() = throw IllegalStateException("No sound loop for turbine")
    override val soundLoopStateMachine = SoundLoopBlockEntity.NoopStateMachine(this)

    val energyStorage = EnergyStorageExposed(MAX_ENERGY)

    var energy: Int
        get() = energyStorage.energyStored
        private set(value) { energyStorage.energy = value }

    private var forcedType: Fluid = NTechFluids.steam.source.get()
    val inputTank = FluidInputTank(MAX_INPUT) { it.fluid.isSame(forcedType) }.apply { forceFluid(FluidStack(forcedType, 0)) }
    val outputTank = FluidOutputTank(MAX_OUTPUT).apply { forceFluid(FluidStack(getResultingFluid(forcedType), 0)) }

    private fun getResultingFluid(fluid: Fluid) = FluidTraitManager.getTraitForFluidStack<CoolableFluidTrait>(FluidStack(fluid, 1000))?.let { it.trait.getFluidCoolingTo(it) } ?: Fluids.EMPTY

    private fun updateTankTypes() {
        inputTank.forceFluid(FluidStack(forcedType, 0))
        outputTank.forceFluid(FluidStack(getResultingFluid(forcedType), 0))
    }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        val fluidId = mainInventory[0]
        if (!fluidId.isEmpty) {
            val fluid = FluidIdentifierItem.getFluid(fluidId)
            if (!fluid.isSame(Fluids.EMPTY) && !fluid.isSame(forcedType)) {
                forcedType = fluid
                updateTankTypes()
            }
            if (canTransferItem(mainInventory[0], mainInventory[1], this))
                transferItemsBetweenItemHandlers(AccessLimitedOutputItemHandler(this, 0), AccessLimitedInputItemHandler(this, 1), 1)
        }

        if (energy > 0) {
            val energyItemSlot = mainInventory[6]
            if (!energyItemSlot.isEmpty) transferEnergy(energyStorage, energyItemSlot)
        }

        var fluidTransferResult = tryEmptyFluidContainerAndMove(mainInventory[2], inputTank, AccessLimitedInputItemHandler(this, 3), inputTank.space, true)
        if (fluidTransferResult.success) mainInventory[2] = fluidTransferResult.result

        val fluidTrait = FluidTraitManager.getTraitForFluidStack<CoolableFluidTrait>(inputTank.fluid)
        if (fluidTrait != null) {
            val outputFluid = fluidTrait.trait.getFluidCoolingTo(fluidTrait)
            if (outputFluid != null) {
                if (outputFluid.isSame(outputTank.fluid.rawFluid)) {
                    val efficiency = fluidTrait.trait.getEfficiency(fluidTrait, CoolableFluidTrait.CoolingType.Turbine) * 0.85F
                    if (efficiency > 0F) {
                        val requiredAmount = fluidTrait.trait.getFluidAmountRequired(fluidTrait)
                        val producedAmount = fluidTrait.trait.getFluidAmountProduced(fluidTrait)
                        val operations = min(inputTank.fluidAmount / requiredAmount, min(outputTank.space / producedAmount, 6_000 / requiredAmount))
                        inputTank.forceFluid(FluidStack(inputTank.fluid.rawFluid, inputTank.fluidAmount - operations * requiredAmount))
                        outputTank.forceFluid(FluidStack(outputTank.fluid.rawFluid, outputTank.fluidAmount + operations * producedAmount))
                        energy = (energy + (operations * fluidTrait.trait.getHeatEnergy(fluidTrait) * efficiency).toInt()).coerceAtMost(MAX_ENERGY)
                        markDirty()
                    }
                } else updateTankTypes()
            }
        }

        fluidTransferResult = tryFillFluidContainerAndMove(mainInventory[4], outputTank, AccessLimitedInputItemHandler(this, 5), outputTank.fluidAmount, true)
        if (fluidTransferResult.success) mainInventory[4] = fluidTransferResult.result
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("Energy", energy)
        tag.put("InputTank", inputTank.writeToNBT(CompoundTag()).apply { putString("FluidName", forcedType.registryName!!.toString()) })
        tag.put("OutputTank", outputTank.writeToNBT(CompoundTag()))
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        energy = tag.getInt("Energy")
        inputTank.readFromNBT(tag.getCompound("InputTank"))
        outputTank.readFromNBT(tag.getCompound("OutputTank"))
        forcedType = inputTank.fluid.rawFluid
        updateTankTypes()
    }

    private val fluidCapabilityDelegate = object : IFluidHandler {
        override fun getTanks() = 2

        override fun getFluidInTank(tank: Int): FluidStack = when (tank) {
            0 -> inputTank.fluid
            1 -> outputTank.fluid
            else -> FluidStack.EMPTY
        }

        override fun getTankCapacity(tank: Int): Int = when (tank) {
            0 -> inputTank.capacity
            1 -> outputTank.capacity
            else -> 0
        }

        override fun isFluidValid(tank: Int, stack: FluidStack): Boolean = when (tank) {
            0 -> inputTank.isFluidValid(stack)
            1 -> outputTank.isFluidValid(stack)
            else -> false
        }

        override fun fill(resource: FluidStack, action: IFluidHandler.FluidAction) = inputTank.fill(resource, action)
        override fun drain(resource: FluidStack, action: IFluidHandler.FluidAction) = outputTank.drain(resource, action)
        override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction) = outputTank.drain(maxDrain, action)
    }

    override fun registerCapabilityHandlers() {
        super.registerCapabilityHandlers()
        registerCapabilityHandler(CapabilityEnergy.ENERGY, this::energyStorage)
        registerCapabilityHandler(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, this::fluidCapabilityDelegate)
    }

    companion object {
        const val MAX_ENERGY = 1_000_000
        const val MAX_INPUT = FluidAttributes.BUCKET_VOLUME * 64
        const val MAX_OUTPUT = FluidAttributes.BUCKET_VOLUME * 128
    }
}
