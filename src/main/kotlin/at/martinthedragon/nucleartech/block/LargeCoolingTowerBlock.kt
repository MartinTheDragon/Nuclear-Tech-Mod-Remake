package at.martinthedragon.nucleartech.block

import at.martinthedragon.nucleartech.api.block.entities.createSidedTickerChecked
import at.martinthedragon.nucleartech.api.block.multi.MultiBlockPlacer
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.entity.LargeCoolingTowerBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.pathfinder.PathComputationType

class LargeCoolingTowerBlock(properties: Properties) : BaseEntityBlock(properties) {
    override fun isPathfindable(state: BlockState, level: BlockGetter, pos: BlockPos, path: PathComputationType) = false
    override fun getRenderShape(state: BlockState) = RenderShape.ENTITYBLOCK_ANIMATED
    override fun getShadeBrightness(state: BlockState, level: BlockGetter, pos: BlockPos) = 1F

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, piston: Boolean) {
        dropMultiBlockEntityContentsAndRemoveStructure<LargeCoolingTowerBlockEntity>(state, level, pos, newState, ::placeMultiBlock)
        @Suppress("DEPRECATION") super.onRemove(state, level, pos, newState, piston)
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = LargeCoolingTowerBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = createSidedTickerChecked(level.isClientSide, type, BlockEntityTypes.largeCoolingTowerBlockEntityType.get())

    companion object {
        fun placeMultiBlock(placer: MultiBlockPlacer) = with(placer) {
            fill(-4, 0, -4, 4, 12, 4, NTechBlocks.genericMultiBlockPart.get().defaultBlockState())
            place(4, 0, 0, NTechBlocks.genericMultiBlockPort.get().defaultBlockState())
            place(-4, 0, 0, NTechBlocks.genericMultiBlockPort.get().defaultBlockState())
            place(0, 0, 4, NTechBlocks.genericMultiBlockPort.get().defaultBlockState())
            place(0, 0, -4, NTechBlocks.genericMultiBlockPort.get().defaultBlockState())
            place(4, 0, 3, NTechBlocks.genericMultiBlockPort.get().defaultBlockState())
            place(4, 0, -3, NTechBlocks.genericMultiBlockPort.get().defaultBlockState())
            place(-4, 0, 3, NTechBlocks.genericMultiBlockPort.get().defaultBlockState())
            place(-4, 0, -3, NTechBlocks.genericMultiBlockPort.get().defaultBlockState())
            place(3, 0, 4, NTechBlocks.genericMultiBlockPort.get().defaultBlockState())
            place(-3, 0, 4, NTechBlocks.genericMultiBlockPort.get().defaultBlockState())
            place(3, 0, -4, NTechBlocks.genericMultiBlockPort.get().defaultBlockState())
            place(-3, 0, -4, NTechBlocks.genericMultiBlockPort.get().defaultBlockState())
        }
    }
}
