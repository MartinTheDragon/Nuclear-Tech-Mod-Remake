package at.martinthedragon.ntm.items.advanceditems

import at.martinthedragon.ntm.blocks.advancedblocks.CustomizedBlock
import at.martinthedragon.ntm.lib.MODID
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.Style
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World

@Suppress("MemberVisibilityCanBePrivate")
class CustomizedBlockItem(block: CustomizedBlock, val customProperties: CustomizedItem.CustomizedProperties) : BlockItem(block, customProperties) {
    init {
        setRegistryName(MODID, block.registryName)
    }

    override fun getBurnTime(itemStack: ItemStack?): Int = customProperties.burnTime * 20
    override fun hasEffect(itemStack: ItemStack): Boolean = customProperties.glint || itemStack.isEnchanted

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<ITextComponent>, flagIn: ITooltipFlag) {
        if (worldIn != null && worldIn.isRemote) {
            var i = 0
            val list = emptyList<ITextComponent>().toMutableList()
            while (true) {
                val translationKey = "${this.translationKey}.lore$i"
                val tmp = I18n.format(translationKey)
                if (tmp == translationKey) break
                else {
                    list.add(StringTextComponent(tmp).setStyle(Style().setColor(TextFormatting.GRAY)))
                    i++
                }
            }
            tooltip.addAll(list)
        }
    }
}
