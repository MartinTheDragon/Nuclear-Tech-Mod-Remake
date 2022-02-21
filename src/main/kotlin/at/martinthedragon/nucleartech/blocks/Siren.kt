package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.blocks.entities.SirenBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.phys.BlockHitResult

class Siren(properties: Properties) : BaseEntityBlock(properties) {
    init { registerDefaultState(stateDefinition.any().setValue(BlockStateProperties.POWERED, false)) }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) { builder.add(BlockStateProperties.POWERED) }
    override fun getStateForPlacement(context: BlockPlaceContext): BlockState = defaultBlockState().setValue(BlockStateProperties.POWERED, false)
    override fun getRenderShape(state: BlockState) = RenderShape.MODEL

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, handIn: InteractionHand, hit: BlockHitResult) = openMenu<SirenBlockEntity>(level, pos, player)

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, p_196243_5_: Boolean) {
        dropBlockEntityContents<SirenBlockEntity>(state, level, pos, newState)
        @Suppress("DEPRECATION") super.onRemove(state, level, pos, newState, p_196243_5_)
    }

    override fun neighborChanged(state: BlockState, level: Level, pos: BlockPos, block: Block, neighborPos: BlockPos, isMoving: Boolean) {
        val flag = level.hasNeighborSignal(pos)
        if (flag != state.getValue(BlockStateProperties.POWERED)) {
            val sirenTileEntity = level.getBlockEntity(pos)
            if (sirenTileEntity is SirenBlockEntity)
                if (flag) sirenTileEntity.startPlaying()
                else sirenTileEntity.stopPlaying()
            level.setBlock(pos, state.setValue(BlockStateProperties.POWERED, flag), 0b11)
        }
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = SirenBlockEntity(pos, state)
}
