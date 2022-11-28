package at.martinthedragon.nucleartech.block.entity.rbmk

import at.martinthedragon.nucleartech.api.block.entities.SoundLoopBlockEntity
import at.martinthedragon.nucleartech.block.entity.BaseMachineBlockEntity
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.rbmk.RBMKPart
import at.martinthedragon.nucleartech.extensions.getOrNull
import at.martinthedragon.nucleartech.fluid.FluidOutputTank
import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler

class RBMKSteamConnectorBlockEntity(pos: BlockPos, state: BlockState) : BaseMachineBlockEntity(BlockEntityTypes.rbmkSteamConnectorBlockEntityType.get(), pos, state) {
    //region Boilerplate
    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(0, ItemStack.EMPTY)

    override val defaultName: Component get() = throw UnsupportedOperationException("No menu for RBMK steam connectors")
    override fun canOpen(player: Player) = false
    override fun createMenu(windowID: Int, inventory: Inventory): AbstractContainerMenu = throw UnsupportedOperationException("No menu for RBMK steam connectors")
    override fun isItemValid(slot: Int, stack: ItemStack) = false

    override val shouldPlaySoundLoop = false
    override val soundLoopEvent: SoundEvent get() = throw UnsupportedOperationException("No sound loop for RBMK steam connectors")
    override val soundLoopStateMachine = SoundLoopBlockEntity.NoopStateMachine(this)

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        throw UnsupportedOperationException("No menu for RBMK steam connectors")
    }
    //endregion

    private val nullWaterTank = object : FluidOutputTank(0) {
        override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction) =
            if (action.simulate()) FluidStack(Fluids.WATER, 1) else FluidStack.EMPTY

        override fun drain(resource: FluidStack, action: IFluidHandler.FluidAction) =
            if (resource.fluid.isSame(Fluids.WATER) && action.simulate()) FluidStack(Fluids.WATER, 1) else FluidStack.EMPTY
    }.also { it.forceFluid(FluidStack(Fluids.WATER, 0)) }

    private val nullTank = FluidOutputTank(0)

    override fun registerCapabilityHandlers() {
        registerCapabilityHandler(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, this::nullWaterTank, Direction.UP)
        registerCapabilityHandler(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, {
            if (!hasLevel()) return@registerCapabilityHandler nullTank
            val abovePos = blockPos.offset(0, 2, 0)
            val aboveBlockState = levelUnchecked.getBlockState(abovePos)
            val aboveBlock = aboveBlockState.block
            return@registerCapabilityHandler if (aboveBlock is RBMKPart) {
                val boiler = aboveBlock.getTopRBMKBase(levelUnchecked, abovePos, aboveBlockState) as? RBMKBoilerBlockEntity ?: return@registerCapabilityHandler nullTank
                val steamCapability = boiler.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, Direction.UP)
                steamCapability.addListener { reviveCaps() } // when the boiler's capability gets invalidated, refresh ours
                steamCapability.getOrNull() ?: nullTank
            } else nullTank
        }, Direction.DOWN)
    }
}
