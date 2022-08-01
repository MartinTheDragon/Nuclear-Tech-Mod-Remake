package at.martinthedragon.nucleartech.block

import at.martinthedragon.nucleartech.api.block.entities.createServerTickerChecked
import at.martinthedragon.nucleartech.api.block.multi.MultiBlockPlacer
import at.martinthedragon.nucleartech.api.explosion.IgnitableExplosive
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.entity.LaunchPadBlockEntity
import at.martinthedragon.nucleartech.block.multi.MultiBlockPart
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.BlockHitResult

class LaunchPadBlock(properties: Properties) : BaseEntityBlock(properties), IgnitableExplosive {
    override fun isPathfindable(state: BlockState, level: BlockGetter, pos: BlockPos, path: PathComputationType) = false
    override fun getRenderShape(state: BlockState) = RenderShape.ENTITYBLOCK_ANIMATED

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult) = openMenu<LaunchPadBlockEntity>(level, pos, player)
    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) = setBlockEntityCustomName<LaunchPadBlockEntity>(level, pos, stack)

    override fun onPlace(oldState: BlockState, level: Level, pos: BlockPos, newState: BlockState, something: Boolean) {
        if (!newState.`is`(oldState.block) && level.hasNeighborSignal(pos)) detonate(level, pos)
    }

    override fun neighborChanged(blockState: BlockState, level: Level, pos: BlockPos, neighbor: Block, neighborPos: BlockPos, something: Boolean) {
        if (level.hasNeighborSignal(pos)) detonate(level, pos)
    }

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, p_196243_5_: Boolean) {
        dropMultiBlockEntityContentsAndRemoveStructure<LaunchPadBlockEntity>(state, level, pos, newState, LaunchPadBlock::placeMultiBlock)
        @Suppress("DEPRECATION") super.onRemove(state, level, pos, newState, p_196243_5_)
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = LaunchPadBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = if (level.isClientSide) null else createServerTickerChecked(type, BlockEntityTypes.launchPadBlockEntityType.get())

    class LaunchPadPartBlock : MultiBlockPart(LaunchPadBlockEntity::LaunchPadPartBlockEntity), IgnitableExplosive {
        override fun onPlace(oldState: BlockState, level: Level, pos: BlockPos, newState: BlockState, something: Boolean) {
            if (!newState.`is`(oldState.block) && level.hasNeighborSignal(pos)) detonate(level, pos)
        }

        override fun neighborChanged(blockState: BlockState, level: Level, pos: BlockPos, neighbor: Block, neighborPos: BlockPos, something: Boolean) {
            if (level.hasNeighborSignal(pos)) detonate(level, pos)
        }

        override fun detonate(level: Level, pos: BlockPos): IgnitableExplosive.DetonationResult {
            val blockEntity = level.getBlockEntity(pos)
            if (blockEntity !is LaunchPadBlockEntity.LaunchPadPartBlockEntity) return IgnitableExplosive.DetonationResult.InvalidBlockEntity
            return super.detonate(level, blockEntity.core)
        }
    }

    companion object {
        fun placeMultiBlock(placer: MultiBlockPlacer) = with(placer) {
            fill(-1, 0, -1, 1, 0, 1, NTechBlocks.launchPadPart.get().defaultBlockState())
        }
    }
}
