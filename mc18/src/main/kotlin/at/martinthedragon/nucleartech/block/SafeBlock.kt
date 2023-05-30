package at.martinthedragon.nucleartech.block

import at.martinthedragon.nucleartech.block.entity.SafeBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.phys.BlockHitResult

// TODO when adding keys and locks make them work with safes
class SafeBlock(properties: Properties) : BaseEntityBlock(properties) {
    init { registerDefaultState(stateDefinition.any().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH)) }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) { builder.add(HorizontalDirectionalBlock.FACING) }
    override fun getStateForPlacement(context: BlockPlaceContext): BlockState = defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, context.horizontalDirection.opposite)
    override fun getRenderShape(state: BlockState) = RenderShape.MODEL

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, handIn: InteractionHand, hit: BlockHitResult) = openMenu<SafeBlockEntity>(level, pos, player)
    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) = setBlockEntityCustomName<SafeBlockEntity>(level, pos, stack)

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, p_196243_5_: Boolean) {
        dropBlockEntityContents<SafeBlockEntity>(state, level, pos, newState)
        @Suppress("DEPRECATION") super.onRemove(state, level, pos, newState, p_196243_5_)
    }

    override fun rotate(state: BlockState, rotation: Rotation): BlockState = state.setValue(HorizontalDirectionalBlock.FACING, rotation.rotate(state.getValue(HorizontalDirectionalBlock.FACING)))
    override fun mirror(state: BlockState, mirror: Mirror): BlockState = @Suppress("DEPRECATION") state.rotate(mirror.getRotation(state.getValue(HorizontalDirectionalBlock.FACING)))

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = SafeBlockEntity(pos, state)
}
