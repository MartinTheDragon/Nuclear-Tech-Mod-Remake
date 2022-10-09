package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.fluid.NTechFluids
import at.martinthedragon.nucleartech.menu.OilWellMenu
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler

class PumpjackBlockEntity(pos: BlockPos, state: BlockState) : AbstractOilWellBlockEntity(BlockEntityTypes.pumpjackBlockEntityType.get(), pos, state, 250_000), IODelegatedBlockEntity {
    override val baseConsumption = 200
    override val baseDelay = 25

    override val defaultName = LangKeys.CONTAINER_PUMPJACK.get()

    override fun createMenu(windowID: Int, inventory: Inventory) = OilWellMenu(windowID, inventory, this)

    override fun getRenderBoundingBox() = AABB(blockPos.offset(-7, 0, -7), blockPos.offset(8, 6, 8))

    override fun onSuck(pos: BlockPos) {
        oilTank.fill(FluidStack(NTechFluids.oil.source.get(), 750), IFluidHandler.FluidAction.EXECUTE)
        gasTank.fill(FluidStack(NTechFluids.gas.source.get(), 50 + levelUnchecked.random.nextInt(201)), IFluidHandler.FluidAction.EXECUTE)
    }

    private val speed: Int get() = if (status == STATUS_OK) 5 + speedUpgradeLevel * 2 + overdriveUpgradeLevel * 10 else 0

    private var speedClient = 0
    var rotO = 0
        private set
    var rot = 0
        private set

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        val previousSpeed = speed
        super.serverTick(level, pos, state)
        if (previousSpeed != speed) sendContinuousUpdatePacket()
    }

    override fun clientTick(level: Level, pos: BlockPos, state: BlockState) {
        super.clientTick(level, pos, state)

        if (canProgress) {
            rotO = rot
            rot += speedClient

            if (rot >= 360) {
                rotO -= 360
                rot -= 360
            }
        }
    }

    override fun getContinuousUpdateTag() = super.getContinuousUpdateTag().apply {
        putInt("Speed", speed)
    }

    override fun handleContinuousUpdatePacket(tag: CompoundTag) {
        super.handleContinuousUpdatePacket(tag)
        speedClient = tag.getInt("Speed")
    }

    init {
        registerCapabilityHandler(CapabilityEnergy.ENERGY, BlockPos(-1, 0, 2), this::energyStorage, Direction.WEST)
        registerCapabilityHandler(CapabilityEnergy.ENERGY, BlockPos(-1, 0, 4), this::energyStorage, Direction.WEST)
        registerCapabilityHandler(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, BlockPos(1, 0, 2), this::gasTank, Direction.EAST)
        registerCapabilityHandler(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, BlockPos(1, 0, 4), this::oilTank, Direction.EAST)
    }

    override val ioConfigurations = IODelegatedBlockEntity.fromTriples(blockPos, getHorizontalBlockRotation(),
        Triple(BlockPos(1, 0, 2), Direction.EAST, listOf(IOConfiguration(IOConfiguration.Mode.OUT, IODelegatedBlockEntity.DEFAULT_FLUID_ACTION))),
        Triple(BlockPos(1, 0, 4), Direction.EAST, listOf(IOConfiguration(IOConfiguration.Mode.OUT, IODelegatedBlockEntity.DEFAULT_FLUID_ACTION))),
        Triple(BlockPos(-1, 0, 2), Direction.WEST, listOf(IOConfiguration(IOConfiguration.Mode.IN, IODelegatedBlockEntity.DEFAULT_ENERGY_ACTION))),
        Triple(BlockPos(-1, 0, 4), Direction.WEST, listOf(IOConfiguration(IOConfiguration.Mode.IN, IODelegatedBlockEntity.DEFAULT_ENERGY_ACTION))),
    )
}
