package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.block.rbmk.RBMKBaseBlock
import at.martinthedragon.nucleartech.block.rbmk.RBMKPart
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext

class RBMKLidItem(properties: Properties) : Item(properties) {
    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level
        val clickedPos = context.clickedPos
        val blockState = level.getBlockState(clickedPos)
        val block = blockState.block

        if (block !is RBMKPart) return InteractionResult.PASS

        if (block.getTopRBMKBase(level, clickedPos, blockState)?.hasLid() == true)
            return InteractionResult.FAIL

        val lidType = when (context.itemInHand.item) {
            NTechItems.rbmkLid.get() -> RBMKBaseBlock.LidType.CONCRETE
            NTechItems.rbmkGlassLid.get() -> RBMKBaseBlock.LidType.LEAD_GLASS
            else -> return InteractionResult.FAIL
        }

        val topBlockPos = block.getTopBlockPos(level, clickedPos, blockState)
        level.setBlockAndUpdate(topBlockPos, level.getBlockState(topBlockPos).setValue(RBMKBaseBlock.LID_TYPE, lidType))
        // TODO sound effects

        if (!level.isClientSide)
            context.itemInHand.shrink(1)

        return InteractionResult.sidedSuccess(level.isClientSide)
    }
}
