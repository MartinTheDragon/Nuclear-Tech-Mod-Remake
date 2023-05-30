package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.Component
import at.martinthedragon.nucleartech.coremodules.minecraft.world.InteractionResult
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.ItemStack
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.context.UseOnContext
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.Level


class ScrewdriverItem(properties: Properties) : AutoTooltippedItem(properties) {
    override fun useOn(context: UseOnContext): InteractionResult {
        val player = context.player ?: return InteractionResult.FAIL
        val hand = context.hand
        val block = context.level.getBlockState(context.clickedPos).block
        if (block is ScrewdriverInteractable) {
            val result = block.onScrew(context)
            if (result.shouldAwardStats) {
                context.itemInHand.hurtAndBreak(1, player) {
                    it.broadcastBreakEvent(hand)
                }
            }
            return result
        }
        return InteractionResult.PASS
    }

    override fun appendHoverText(itemStack: ItemStack, level: Level?, tooltip: MutableList<Component>, extended: Boolean) {
        autoTooltip(itemStack, tooltip, true)
    }
}
