package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.api.block.multi.MultiBlockPlacer
import at.martinthedragon.nucleartech.block.multi.RotatedMultiBlockPlacer
import net.minecraft.core.BlockPos
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

open class SpecialModelMultiBlockPlacerItem(block: Block, blockEntityFunc: (BlockPos, BlockState) -> BlockEntity, val placerFunc: (MultiBlockPlacer) -> Unit, properties: Properties, override val additionalRange: Float = 0F) : SpecialModelBlockItem(block, blockEntityFunc, properties), IncreasedRangeItem {
    override fun canPlace(context: BlockPlaceContext, state: BlockState): Boolean {
        return super.canPlace(context, state) && RotatedMultiBlockPlacer(context.horizontalDirection.opposite).apply(placerFunc).canPlaceAt(context.level, context.clickedPos)
    }

    override fun placeBlock(context: BlockPlaceContext, state: BlockState): Boolean {
        return super.placeBlock(context, state) && RotatedMultiBlockPlacer(context.horizontalDirection.opposite).apply(placerFunc).finish(context.level, context.clickedPos)
    }
}
