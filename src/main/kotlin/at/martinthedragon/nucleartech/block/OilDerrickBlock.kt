package at.martinthedragon.nucleartech.block

import at.martinthedragon.nucleartech.api.block.entities.createServerTickerChecked
import at.martinthedragon.nucleartech.api.block.multi.MultiBlockPlacer
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.entity.OilDerrickBlockEntity
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

class OilDerrickBlock(properties: Properties) : BaseEntityBlock(properties) {
    override fun isPathfindable(state: BlockState, level: BlockGetter, pos: BlockPos, path: PathComputationType) = false
    override fun getRenderShape(state: BlockState) = RenderShape.ENTITYBLOCK_ANIMATED

    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) = setMachineCustomName<OilDerrickBlockEntity>(level, pos, stack)

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, p_196243_5_: Boolean) {
        dropMultiBlockEntityContentsAndRemoveStructure<OilDerrickBlockEntity>(state, level, pos, newState, ::placeMultiBlock)
        @Suppress("DEPRECATION") super.onRemove(state, level, pos, newState, p_196243_5_)
    }

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult) = openMenu<OilDerrickBlockEntity>(level, pos, player)

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = OilDerrickBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = if (level.isClientSide) null else createServerTickerChecked(type, BlockEntityTypes.oilDerrickBlockEntityType.get())

    companion object {
        fun placeMultiBlock(placer: MultiBlockPlacer) = with(placer) {
            place(1, 0, 0, NTechBlocks.genericMultiBlockPort.get().defaultBlockState())
            place(-1, 0, 0, NTechBlocks.genericMultiBlockPort.get().defaultBlockState())
            place(0, 0, 1, NTechBlocks.genericMultiBlockPort.get().defaultBlockState())
            place(0, 0, -1, NTechBlocks.genericMultiBlockPort.get().defaultBlockState())
            fill(-1, 0, -1, 1, 5, 1, NTechBlocks.genericMultiBlockPart.get().defaultBlockState())
        }
    }
}
