package at.martinthedragon.nucleartech.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.SimpleWaterloggedBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class SteelBeamBlock(properties: Properties) : RotatedPillarBlock(properties), SimpleWaterloggedBlock {
    init { registerDefaultState(stateDefinition.any().setValue(BlockStateProperties.WATERLOGGED, false)) }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(builder)
        builder.add(BlockStateProperties.WATERLOGGED)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        val fluidState = context.level.getFluidState(context.clickedPos)
        return super.getStateForPlacement(context)?.setValue(BlockStateProperties.WATERLOGGED, fluidState.type == Fluids.WATER)
    }

    override fun getFluidState(blockState: BlockState): FluidState = if (blockState.getValue(BlockStateProperties.WATERLOGGED)) Fluids.WATER.getSource(false) else Fluids.EMPTY.defaultFluidState()

    private val xAABB = Block.box(0.0, 7.0, 7.0, 16.0, 9.0, 9.0)
    private val yAABB = Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 9.0)
    private val zAABB = Block.box(7.0, 7.0, 0.0, 9.0, 9.0, 16.0)

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape = when (state.getValue(AXIS)) {
        Direction.Axis.X -> xAABB
        Direction.Axis.Y -> yAABB
        Direction.Axis.Z -> zAABB
        else -> yAABB
    }

    override fun updateShape(state: BlockState, sourceDirection: Direction, otherState: BlockState, level: LevelAccessor, pos: BlockPos, otherPos: BlockPos): BlockState {
        if (state.getValue(BlockStateProperties.WATERLOGGED))
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level))
        return state
    }

    override fun isPathfindable(state: BlockState, level: BlockGetter, pos: BlockPos, pathType: PathComputationType) = false
}
