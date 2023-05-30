package at.martinthedragon.nucleartech.block

import at.martinthedragon.nucleartech.api.block.entities.createServerTickerChecked
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.entity.transmitters.FluidPipeBlockEntity
import at.martinthedragon.nucleartech.math.component1
import at.martinthedragon.nucleartech.math.component2
import at.martinthedragon.nucleartech.math.component3
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class CoatedUniversalFluidPipeBlock(properties: Properties) : BaseEntityBlock(properties) {
    override fun getRenderShape(state: BlockState) = RenderShape.MODEL

    override fun onBlockStateChange(level: LevelReader, pos: BlockPos, oldState: BlockState, newState: BlockState) {
        if (!oldState.`is`(newState.block)) {
            if (level is ClientLevel) BlockTints.invalidate(level, BlockTints.FLUID_DUCT_COLOR_RESOLVER, pos)
            val blockEntity = level.getBlockEntity(pos)
            if (blockEntity is FluidPipeBlockEntity) blockEntity.placeInWorld()
        }
    }

    override fun neighborChanged(state: BlockState, level: Level, pos: BlockPos, neighbor: Block, neighborPos: BlockPos, p_60514_: Boolean) {
        handleNeighborChange(level, pos, neighborPos)
    }

    override fun onNeighborChange(state: BlockState, level: LevelReader, pos: BlockPos, neighbor: BlockPos) {
        handleNeighborChange(level, pos, neighbor)
    }

    private fun handleNeighborChange(level: LevelReader, pos: BlockPos, neighbor: BlockPos) {
        val blockEntity = level.getBlockEntity(pos)
        if (blockEntity is FluidPipeBlockEntity) {
            val (x, y, z) = pos
            val (nx, ny, nz) = neighbor
            val direction = Direction.getNearest((nx - x).toFloat(), (ny - y).toFloat(), (nz - z).toFloat())
            blockEntity.neighborChanged(direction)
        }
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = FluidPipeBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = if (level.isClientSide) null else createServerTickerChecked(type, BlockEntityTypes.fluidPipeBlockEntityType.get())
}
