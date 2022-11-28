package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.block.rbmk.RBMKPart
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext

class MeltdownToolItem(properties: Properties) : Item(properties) {
    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level
        val clickedPos = context.clickedPos
        val blockState = level.getBlockState(clickedPos)
        val block = blockState.block

        val rbmkBase = if (block is RBMKPart) block.getTopRBMKBase(level, clickedPos, blockState) ?: return InteractionResult.FAIL else return InteractionResult.PASS
        if (!level.isClientSide) rbmkBase.meltdown()

        return InteractionResult.sidedSuccess(level.isClientSide)
    }
}
