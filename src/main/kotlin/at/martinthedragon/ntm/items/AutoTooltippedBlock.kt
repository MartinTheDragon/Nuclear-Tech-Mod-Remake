package at.martinthedragon.ntm.items

import net.minecraft.block.Block
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent
import net.minecraft.world.World

class AutoTooltippedBlockItem(block: Block, properties: Properties) : BlockItem(block, properties) {
    override fun appendHoverText(stack: ItemStack, worldIn: World?, tooltip: MutableList<ITextComponent>, flagIn: ITooltipFlag) {
        autoTooltip(stack, tooltip)
    }
}
