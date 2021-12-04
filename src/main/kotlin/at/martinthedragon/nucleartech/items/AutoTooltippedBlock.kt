package at.martinthedragon.nucleartech.items

import net.minecraft.network.chat.Component
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block

class AutoTooltippedBlockItem(block: Block, properties: Properties) : BlockItem(block, properties) {
    override fun appendHoverText(stack: ItemStack, worldIn: Level?, tooltip: MutableList<Component>, flagIn: TooltipFlag) {
        autoTooltip(stack, tooltip)
    }
}
