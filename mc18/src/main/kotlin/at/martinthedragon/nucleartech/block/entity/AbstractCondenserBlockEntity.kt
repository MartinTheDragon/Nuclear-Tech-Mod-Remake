package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.NTechTags
import at.martinthedragon.nucleartech.api.block.entities.SoundLoopBlockEntity
import at.martinthedragon.nucleartech.api.block.entities.TickingServerBlockEntity
import at.martinthedragon.nucleartech.fluid.FluidInputTank
import at.martinthedragon.nucleartech.fluid.FluidOutputTank
import at.martinthedragon.nucleartech.fluid.NTechFluids
import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler
import kotlin.math.min

abstract class AbstractCondenserBlockEntity(type: BlockEntityType<out AbstractCondenserBlockEntity>, pos: BlockPos, state: BlockState, inputCapacity: Int, outputCapacity: Int) : BaseMachineBlockEntity(type, pos, state),
    TickingServerBlockEntity, ExtendedMultiBlockInfo
{
    override val shouldPlaySoundLoop = false
    override val soundLoopEvent get() = throw IllegalStateException("No sound loop for turbine")
    @Suppress("LeakingThis")
    override val soundLoopStateMachine = SoundLoopBlockEntity.NoopStateMachine(this)

    override fun providesMenu(level: Level, pos: BlockPos, player: Player) = false
    override fun canOpen(player: Player) = false
    override fun createMenu(windowID: Int, inventory: Inventory) =
        throw UnsupportedOperationException("Condensers have no menu")

    override val defaultName: Component = TextComponent.EMPTY

    override fun isItemValid(slot: Int, stack: ItemStack) = false
    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {}

    override val hasInventory = false
    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(0, ItemStack.EMPTY)

    @Suppress("DEPRECATION")
    val inputTank = FluidInputTank(inputCapacity) { it.fluid.`is`(NTechTags.Fluids.SPENT_STEAM) }.apply { forceFluid(FluidStack(NTechFluids.spentSteam.source.get(), 0)) }
    val outputTank = FluidOutputTank(outputCapacity) { it.fluid.isSame(Fluids.WATER) }.apply { forceFluid(FluidStack(Fluids.WATER, 0)) }

    protected var conversionTimeout = 0
        private set

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        val timeoutWasOver = conversionTimeout == 0

        val convert = min(inputTank.fluidAmount, outputTank.space)
        inputTank.forceFluid(FluidStack(inputTank.fluid.rawFluid, inputTank.fluidAmount - convert))

        // TODO firestorm behavior
        outputTank.forceFluid(FluidStack(Fluids.WATER, inputTank.fluidAmount + convert))

        if (conversionTimeout > 0) conversionTimeout--
        if (convert > 0) conversionTimeout = 20

        if (timeoutWasOver != (conversionTimeout == 0)) sendContinuousUpdatePacket()
    }

    override fun getContinuousUpdateTag() = super.getContinuousUpdateTag().apply {
        putBoolean("WaterTimedOut", conversionTimeout == 0)
    }

    override fun handleContinuousUpdatePacket(tag: CompoundTag) {
        super.handleContinuousUpdatePacket(tag)
        conversionTimeout = if (tag.getBoolean("WaterTimedOut")) 0 else 1
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.put("InputTank", inputTank.writeToNBT(CompoundTag()))
        tag.put("OutputTank", outputTank.writeToNBT(CompoundTag()))
        tag.putInt("ConversionTimeout", conversionTimeout)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        inputTank.readFromNBT(tag.getCompound("InputTank"))
        outputTank.readFromNBT(tag.getCompound("OutputTank"))
        conversionTimeout = tag.getInt("ConversionTimeout")
    }

    protected open val fluidCapabilityDelegate = object : IFluidHandler {
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
        registerCapabilityHandler(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, this::fluidCapabilityDelegate)
    }
}
