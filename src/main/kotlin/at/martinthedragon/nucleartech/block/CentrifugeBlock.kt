package at.martinthedragon.nucleartech.block

import at.martinthedragon.nucleartech.api.block.entities.createSidedTickerChecked
import at.martinthedragon.nucleartech.api.block.multi.MultiBlockPlacer
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.entity.CentrifugeBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.BlockHitResult

class CentrifugeBlock(properties: Properties) : BaseEntityBlock(properties) {
    override fun isPathfindable(state: BlockState, level: BlockGetter, pos: BlockPos, path: PathComputationType) = false
    override fun getRenderShape(state: BlockState) = RenderShape.ENTITYBLOCK_ANIMATED
    override fun getShadeBrightness(state: BlockState, level: BlockGetter, pos: BlockPos) = 1F

    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) = setMachineCustomName<CentrifugeBlockEntity>(level, pos, stack)

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, p_196243_5_: Boolean) {
        dropMultiBlockEntityContentsAndRemoveStructure<CentrifugeBlockEntity>(state, level, pos, newState, Companion::placeMultiBlock)
        @Suppress("DEPRECATION") super.onRemove(state, level, pos, newState, p_196243_5_)
    }

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult) = openMenu<CentrifugeBlockEntity>(level, pos, player)

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = CentrifugeBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = createSidedTickerChecked(level.isClientSide, type, BlockEntityTypes.centrifugeBlockEntityType.get())

    companion object {
        fun placeMultiBlock(placer: MultiBlockPlacer) = with(placer) {
            fill(0, 1, 0, 0, 3, 0, NTechBlocks.genericMultiBlockPart.get().defaultBlockState())
        }
    }

}
