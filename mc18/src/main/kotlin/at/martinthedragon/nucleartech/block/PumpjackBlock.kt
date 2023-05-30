package at.martinthedragon.nucleartech.block

import at.martinthedragon.nucleartech.api.block.entities.createSidedTickerChecked
import at.martinthedragon.nucleartech.api.block.multi.MultiBlockPlacer
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.entity.PumpjackBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.BlockHitResult

class PumpjackBlock(properties: Properties) : BaseEntityBlock(properties) {
    init { registerDefaultState(stateDefinition.any().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH)) }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) { builder.add(HorizontalDirectionalBlock.FACING) }
    override fun getStateForPlacement(context: BlockPlaceContext): BlockState = defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, context.horizontalDirection.opposite)
    override fun isPathfindable(state: BlockState, level: BlockGetter, pos: BlockPos, path: PathComputationType) = false
    override fun getRenderShape(state: BlockState) = RenderShape.ENTITYBLOCK_ANIMATED

    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) = setMachineCustomName<PumpjackBlockEntity>(level, pos, stack)

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, p_196243_5_: Boolean) {
        dropMultiBlockEntityContentsAndRemoveStructure<PumpjackBlockEntity>(state, level, pos, newState, ::placeMultiBlock)
        @Suppress("DEPRECATION") super.onRemove(state, level, pos, newState, p_196243_5_)
    }

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult) = openMenu<PumpjackBlockEntity>(level, pos, player)

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = PumpjackBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = createSidedTickerChecked(level.isClientSide, type, BlockEntityTypes.pumpjackBlockEntityType.get())

    companion object {
        fun placeMultiBlock(placer: MultiBlockPlacer) = with(placer) {
            fill(0, 0, 0, 0, 3, 6, NTechBlocks.genericMultiBlockPart.get().defaultBlockState())
            place(1, 0, 1, NTechBlocks.genericMultiBlockPart.get().defaultBlockState())
            place(1, 0, 2, NTechBlocks.genericMultiBlockPort.get().defaultBlockState())
            place(1, 0, 3, NTechBlocks.genericMultiBlockPart.get().defaultBlockState())
            place(1, 0, 4, NTechBlocks.genericMultiBlockPort.get().defaultBlockState())
            place(1, 0, 5, NTechBlocks.genericMultiBlockPart.get().defaultBlockState())
            place(-1, 0, 2, NTechBlocks.genericMultiBlockPort.get().defaultBlockState())
            place(-1, 0, 3, NTechBlocks.genericMultiBlockPart.get().defaultBlockState())
            place(-1, 0, 4, NTechBlocks.genericMultiBlockPort.get().defaultBlockState())
        }
    }
}
