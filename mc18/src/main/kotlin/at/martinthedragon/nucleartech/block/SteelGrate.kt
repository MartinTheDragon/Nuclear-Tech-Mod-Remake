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
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class SteelGrate(properties: Properties) : Block(properties) {
    init { registerDefaultState(stateDefinition.any().setValue(BlockStateProperties.LEVEL_FLOWING, 8).setValue(BlockStateProperties.WATERLOGGED, false)) }

    override fun getFluidState(blockState: BlockState): FluidState = if (blockState.getValue(BlockStateProperties.WATERLOGGED)) Fluids.WATER.getSource(false) else Fluids.EMPTY.defaultFluidState()

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(builder)
        builder.add(BlockStateProperties.LEVEL_FLOWING, BlockStateProperties.WATERLOGGED)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        val fluidState = context.level.getFluidState(context.clickedPos)
        return defaultBlockState().setValue(BlockStateProperties.LEVEL_FLOWING, (((context.clickLocation.y - context.clickedPos.y) * 8.0).toInt() + 1).coerceIn(1, 8)).setValue(BlockStateProperties.WATERLOGGED, fluidState.type == Fluids.WATER)
    }

    private val shapesByLayers = arrayOf(
        box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
        box(0.0, 2.0, 0.0, 16.0, 4.0, 16.0),
        box(0.0, 4.0, 0.0, 16.0, 6.0, 16.0),
        box(0.0, 6.0, 0.0, 16.0, 8.0, 16.0),
        box(0.0, 8.0, 0.0, 16.0, 10.0, 16.0),
        box(0.0, 10.0, 0.0, 16.0, 12.0, 16.0),
        box(0.0, 12.0, 0.0, 16.0, 14.0, 16.0),
        box(0.0, 14.0, 0.0, 16.0, 16.0, 16.0),
    )

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape = shapesByLayers[state.getValue(BlockStateProperties.LEVEL_FLOWING) - 1]

    override fun useShapeForLightOcclusion(state: BlockState) = true

    override fun updateShape(state: BlockState, sourceDirection: Direction, otherState: BlockState, level: LevelAccessor, pos: BlockPos, otherPos: BlockPos): BlockState {
        if (state.getValue(BlockStateProperties.WATERLOGGED))
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level))
        return state
    }

    override fun propagatesSkylightDown(state: BlockState, level: BlockGetter, pos: BlockPos) = true

    override fun getLightBlock(state: BlockState, getter: BlockGetter, pos: BlockPos) = 2

    override fun isPathfindable(state: BlockState, level: BlockGetter, pos: BlockPos, pathType: PathComputationType) = false
}
