package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.api.blocks.multi.MultiBlockPlacer
import at.martinthedragon.nucleartech.blocks.entities.AssemblerBlockEntity
import at.martinthedragon.nucleartech.blocks.entities.BlockEntityTypes
import at.martinthedragon.nucleartech.blocks.entities.createServerTickerChecked
import at.martinthedragon.nucleartech.blocks.multi.MultiBlockPart
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.BlockHitResult

class AssemblerBlock(properties: Properties) : BaseEntityBlock(properties) {
    init { registerDefaultState(stateDefinition.any().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH)) }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) { builder.add(HorizontalDirectionalBlock.FACING) }
    override fun getStateForPlacement(context: BlockPlaceContext): BlockState = defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, context.horizontalDirection.opposite)
    override fun isPathfindable(state: BlockState, level: BlockGetter, pos: BlockPos, path: PathComputationType) = false
    override fun getRenderShape(state: BlockState) = RenderShape.ENTITYBLOCK_ANIMATED
    override fun getShadeBrightness(state: BlockState, level: BlockGetter, pos: BlockPos) = 1F

    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) = setBlockEntityCustomName<AssemblerBlockEntity>(level, pos, stack)

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, p_196243_5_: Boolean) {
        dropMultiBlockEntityContentsAndRemoveStructure<AssemblerBlockEntity>(state, level, pos, newState, Companion::placeMultiBlock)
        @Suppress("DEPRECATION") super.onRemove(state, level, pos, newState, p_196243_5_)
    }

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult) = openMenu<AssemblerBlockEntity>(level, pos, player)

    override fun rotate(state: BlockState, rotation: Rotation) = state
    override fun mirror(state: BlockState, mirror: Mirror) = state

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = AssemblerBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = if (level.isClientSide) null else createServerTickerChecked(type, BlockEntityTypes.assemblerBlockEntityType.get())

    class AssemblerPart : MultiBlockPart(ModBlocks.assembler.get()) {
        override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult) = openMultiBlockMenu<AssemblerBlockEntity, AssemblerBlockEntity.AssemblerPartBlockEntity>(level, pos, player)
        override fun newBlockEntity(pos: BlockPos, state: BlockState) = AssemblerBlockEntity.AssemblerPartBlockEntity(pos, state)
    }

    companion object {
        fun placeMultiBlock(placer: MultiBlockPlacer) = with(placer) {
            fill(-2, 0, -2, 1, 1, 1, ModBlocks.assemblerPart.get().defaultBlockState())
        }
    }
}
