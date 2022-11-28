package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.api.item.ScrewdriverInteractable
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level

class ScrewdriverItem(properties: Properties) : AutoTooltippedItem(properties) {
    override fun useOn(context: UseOnContext): InteractionResult {
        val player = context.player ?: return InteractionResult.FAIL
        val hand = context.hand
        val block = context.level.getBlockState(context.clickedPos).block
        if (block is ScrewdriverInteractable && block.onScrew(context).shouldAwardStats()) {
            context.itemInHand.hurtAndBreak(1, player) {
                it.broadcastBreakEvent(hand)
            }
            return InteractionResult.sidedSuccess(context.level.isClientSide)
        }
        return InteractionResult.PASS
    }

    override fun appendHoverText(stack: ItemStack, level: Level?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        autoTooltip(stack, tooltip, true)
    }
}
