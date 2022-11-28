package at.martinthedragon.nucleartech.block.entity.rbmk

import at.martinthedragon.nucleartech.api.block.entities.SoundLoopBlockEntity
import at.martinthedragon.nucleartech.api.block.entities.TickingServerBlockEntity
import at.martinthedragon.nucleartech.block.entity.BaseMachineBlockEntity
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.rbmk.RBMKPart
import at.martinthedragon.nucleartech.fluid.FluidInputTank
import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import kotlin.math.min

class RBMKInletBlockEntity(pos: BlockPos, state: BlockState) : BaseMachineBlockEntity(BlockEntityTypes.rbmkInletBlockEntityType.get(), pos, state), TickingServerBlockEntity {
    //region Boilerplate
    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(0, ItemStack.EMPTY)

    override val defaultName: Component get() = throw UnsupportedOperationException("No menu for RBMK inlets")
    override fun canOpen(player: Player) = false
    override fun createMenu(windowID: Int, inventory: Inventory): AbstractContainerMenu = throw UnsupportedOperationException("No menu for RBMK inlets")
    override fun isItemValid(slot: Int, stack: ItemStack) = false

    override val shouldPlaySoundLoop = false
    override val soundLoopEvent: SoundEvent get() = throw UnsupportedOperationException("No sound loop for RBMK inlets")
    override val soundLoopStateMachine = SoundLoopBlockEntity.NoopStateMachine(this)

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        throw UnsupportedOperationException("No menu for RBMK inlets")
    }
    //endregion

    private val waterTank = FluidInputTank(32_000) { it.fluid.isSame(Fluids.WATER) }.also { it.forceFluid(FluidStack(Fluids.WATER, 0)) }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        if (waterTank.fluidAmount > 0) for (direction in RBMKBase.HEAT_DIRECTIONS) {
            val adjacentPos = pos.relative(direction)
            val adjacent = level.getBlockState(adjacentPos)
            val adjacentBlock = adjacent.block

            val rbmkPart: RBMKBase = if (adjacentBlock is RBMKPart) adjacentBlock.getTopRBMKBase(level, adjacentPos, adjacent) ?: continue else continue

            val provision = min(RBMKBase.MAX_WATER - rbmkPart.water, waterTank.fluidAmount)
            rbmkPart.water += provision
            waterTank.forceFluid(FluidStack(Fluids.WATER, (waterTank.fluidAmount - provision).coerceAtLeast(0)))
        }
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.put("WaterTank", waterTank.writeToNBT(CompoundTag()))
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        waterTank.readFromNBT(tag.getCompound("WaterTank"))
    }

    override fun registerCapabilityHandlers() {
        registerCapabilityHandler(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, this::waterTank)
    }
}
