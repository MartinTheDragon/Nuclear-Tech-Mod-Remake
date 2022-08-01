package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.api.block.multi.MultiBlockPlacer
import at.martinthedragon.nucleartech.block.multi.RotatedMultiBlockPlacer
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

class MultiBlockPlacerItem(block: Block, val placerFunc: (MultiBlockPlacer) -> Unit, properties: Properties) : BlockItem(block, properties) {
    override fun canPlace(context: BlockPlaceContext, state: BlockState): Boolean {
        return super.canPlace(context, state) && RotatedMultiBlockPlacer(context.horizontalDirection.opposite).apply(placerFunc).canPlaceAt(context.level, context.clickedPos)
    }

    override fun placeBlock(context: BlockPlaceContext, state: BlockState): Boolean {
        return super.placeBlock(context, state) && RotatedMultiBlockPlacer(context.horizontalDirection.opposite).apply(placerFunc).finish(context.level, context.clickedPos)
    }
}
