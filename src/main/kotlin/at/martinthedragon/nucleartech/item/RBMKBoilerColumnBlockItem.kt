package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.block.rbmk.RBMKBoilerBlock
import at.martinthedragon.nucleartech.block.rbmk.RBMKBoilerColumnBlock
import net.minecraft.core.BlockPos
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class RBMKBoilerColumnBlockItem(topBlock: RBMKBoilerBlock, blockEntityFunc: (BlockPos, BlockState) -> BlockEntity, properties: Properties) : RBMKColumnBlockItem(topBlock, blockEntityFunc, properties) {
    override fun getPlacementState(context: BlockPlaceContext, offset: Int, maxOffset: Int): BlockState? {
        val superState = super.getPlacementState(context, offset, maxOffset)
        return if (offset == maxOffset) superState?.setValue(RBMKBoilerColumnBlock.IO, true)
        else superState
    }
}
