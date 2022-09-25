package at.martinthedragon.nucleartech.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class SteelScaffoldBlock(properties: Properties) : Block(properties) {
    init { registerDefaultState(stateDefinition.any().setValue(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.X).setValue(BlockStateProperties.WATERLOGGED, false)) }

    override fun getFluidState(blockState: BlockState): FluidState = if (blockState.getValue(BlockStateProperties.WATERLOGGED)) Fluids.WATER.getSource(false) else Fluids.EMPTY.defaultFluidState()

    private val xShell = box(2.0, 0.0, 0.0, 14.0, 16.0, 16.0)
    private val xHollow = box(4.0, 0.0, 2.0, 12.0, 16.0, 14.0)
    private val zShell = box(0.0, 0.0, 2.0, 16.0, 16.0, 14.0)
    private val zHollow = box(2.0, 0.0, 4.0, 14.0, 16.0, 12.0)
    private val xShape = Shapes.join(xShell, xHollow, BooleanOp.ONLY_FIRST)
    private val zShape = Shapes.join(zShell, zHollow, BooleanOp.ONLY_FIRST)

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(builder)
        builder.add(BlockStateProperties.HORIZONTAL_AXIS, BlockStateProperties.WATERLOGGED)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        val fluidState = context.level.getFluidState(context.clickedPos)
        return defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_AXIS, context.horizontalDirection.axis).setValue(BlockStateProperties.WATERLOGGED, fluidState.type == Fluids.WATER)
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape = when (state.getValue(BlockStateProperties.HORIZONTAL_AXIS)) {
        Direction.Axis.X -> xShape
        Direction.Axis.Z -> zShape
        else -> xShell
    }

    override fun updateShape(state: BlockState, sourceDirection: Direction, otherState: BlockState, level: LevelAccessor, pos: BlockPos, otherPos: BlockPos): BlockState {
        if (state.getValue(BlockStateProperties.WATERLOGGED))
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level))
        return state
    }

    override fun isPathfindable(state: BlockState, level: BlockGetter, pos: BlockPos, pathType: PathComputationType) = false
}
