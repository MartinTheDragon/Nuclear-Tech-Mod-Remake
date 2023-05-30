package at.martinthedragon.nucleartech.block.entity.rbmk

import at.martinthedragon.nucleartech.api.block.entities.SoundLoopBlockEntity
import at.martinthedragon.nucleartech.api.block.entities.TickingServerBlockEntity
import at.martinthedragon.nucleartech.block.entity.BaseMachineBlockEntity
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.rbmk.RBMKPart
import at.martinthedragon.nucleartech.fluid.FluidOutputTank
import at.martinthedragon.nucleartech.fluid.NTechFluids
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
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import kotlin.math.min

class RBMKOutletBlockEntity(pos: BlockPos, state: BlockState) : BaseMachineBlockEntity(BlockEntityTypes.rbmkOutletBlockEntityType.get(), pos, state), TickingServerBlockEntity {
    //region Boilerplate
    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(0, ItemStack.EMPTY)

    override val defaultName: Component get() = throw UnsupportedOperationException("No menu for RBMK outlets")
    override fun canOpen(player: Player) = false
    override fun createMenu(windowID: Int, inventory: Inventory): AbstractContainerMenu = throw UnsupportedOperationException("No menu for RBMK outlets")
    override fun isItemValid(slot: Int, stack: ItemStack) = false

    override val shouldPlaySoundLoop = false
    override val soundLoopEvent: SoundEvent get() = throw UnsupportedOperationException("No sound loop for RBMK outlets")
    override val soundLoopStateMachine = SoundLoopBlockEntity.NoopStateMachine(this)

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        throw UnsupportedOperationException("No menu for RBMK outlets")
    }
    //endregion

    private val steamTank = FluidOutputTank(32_000) { it.fluid.isSame(NTechFluids.steamSuperHot.source.get()) }.also { it.forceFluid(FluidStack(NTechFluids.steamSuperHot.source.get(), 0)) }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        if (steamTank.space > 0) for (direction in RBMKBase.HEAT_DIRECTIONS) {
            val adjacentPos = pos.relative(direction)
            val adjacent = level.getBlockState(adjacentPos)
            val adjacentBlock = adjacent.block

            val rbmkPart: RBMKBase = if (adjacentBlock is RBMKPart) adjacentBlock.getTopRBMKBase(level, adjacentPos, adjacent) ?: continue else continue

            val deduction = min(steamTank.space, rbmkPart.steam)
            rbmkPart.steam -= deduction
            steamTank.forceFluid(FluidStack(NTechFluids.steamSuperHot.source.get(), (steamTank.fluidAmount + deduction).coerceAtMost(steamTank.capacity)))
        }
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.put("SteamTank", steamTank.writeToNBT(CompoundTag()))
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        steamTank.readFromNBT(tag.getCompound("SteamTank"))
    }

    override fun registerCapabilityHandlers() {
        registerCapabilityHandler(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, this::steamTank)
    }
}
