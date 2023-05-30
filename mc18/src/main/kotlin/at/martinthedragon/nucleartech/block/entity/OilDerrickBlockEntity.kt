package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.entity.OilSpill
import at.martinthedragon.nucleartech.fluid.NTechFluids
import at.martinthedragon.nucleartech.math.toVec3Middle
import at.martinthedragon.nucleartech.menu.OilWellMenu
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraftforge.fluids.FluidStack

class OilDerrickBlockEntity(pos: BlockPos, state: BlockState) : AbstractOilWellBlockEntity(BlockEntityTypes.oilDerrickBlockEntityType.get(), pos, state, 100_000), IODelegatedBlockEntity {
    override val baseConsumption = 100
    override val baseDelay = 50

    override val defaultName = LangKeys.CONTAINER_OIL_DERRICK.get()

    override fun createMenu(windowID: Int, inventory: Inventory) = OilWellMenu(windowID, inventory, this)

    override fun getRenderBoundingBox() = AABB(blockPos.offset(-1, 0, -1), blockPos.offset(2, 7, 2))

    // TODO when drilling: uranium -> radon, asbestos -> particles

    override fun onSuck(pos: BlockPos) {
        OilSpill.spawnOilSpills(levelUnchecked, blockPos.toVec3Middle().add(0.0, 5.0, 0.0), 3)
        levelUnchecked.playSound(null, blockPos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 2F, .5F)

        oilTank.forceFluid(FluidStack(NTechFluids.oil.source.get(), (oilTank.fluidAmount + 500).coerceAtMost(oilTank.capacity)))
        gasTank.forceFluid(FluidStack(NTechFluids.gas.source.get(), (oilTank.fluidAmount + 100 + levelUnchecked.random.nextInt(401)).coerceAtMost(oilTank.capacity)))
    }

    override val ioConfigurations = IODelegatedBlockEntity.fromTriples(blockPos, getHorizontalBlockRotation(),
        Triple(BlockPos(1, 0, 0), Direction.EAST, listOf(IOConfiguration(IOConfiguration.Mode.IN, IODelegatedBlockEntity.DEFAULT_ENERGY_ACTION), IOConfiguration(IOConfiguration.Mode.OUT, IODelegatedBlockEntity.DEFAULT_FLUID_ACTION))),
        Triple(BlockPos(-1, 0, 0), Direction.WEST, listOf(IOConfiguration(IOConfiguration.Mode.IN, IODelegatedBlockEntity.DEFAULT_ENERGY_ACTION), IOConfiguration(IOConfiguration.Mode.OUT, IODelegatedBlockEntity.DEFAULT_FLUID_ACTION))),
        Triple(BlockPos(0, 0, 1), Direction.SOUTH, listOf(IOConfiguration(IOConfiguration.Mode.IN, IODelegatedBlockEntity.DEFAULT_ENERGY_ACTION), IOConfiguration(IOConfiguration.Mode.OUT, IODelegatedBlockEntity.DEFAULT_FLUID_ACTION))),
        Triple(BlockPos(0, 0, -1), Direction.NORTH, listOf(IOConfiguration(IOConfiguration.Mode.IN, IODelegatedBlockEntity.DEFAULT_ENERGY_ACTION), IOConfiguration(IOConfiguration.Mode.OUT, IODelegatedBlockEntity.DEFAULT_FLUID_ACTION))),
    )
}
