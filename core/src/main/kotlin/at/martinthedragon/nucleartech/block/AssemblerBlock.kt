package at.martinthedragon.nucleartech.block

import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPos
import at.martinthedragon.nucleartech.coremodules.minecraft.core.Direction
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.context.BlockPlaceContext
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.BaseEntityBlock
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.Block
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.entity.BlockEntity
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state.BlockState
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state.StateDefinition
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state.properties.BlockStateProperties

class AssemblerBlock(properties: Properties) : BaseEntityBlock(properties) {
    init { defaultBlockState = stateDefinition.any().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH) }

    override fun createDefaultBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) { builder.add(BlockStateProperties.HORIZONTAL_FACING) }
    override fun getStateForPlacement(context: BlockPlaceContext) = defaultBlockState.setValue(BlockStateProperties.HORIZONTAL_FACING, context.horizontalDirection.opposite)

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? {
        TODO("Not yet implemented")
    }
}
