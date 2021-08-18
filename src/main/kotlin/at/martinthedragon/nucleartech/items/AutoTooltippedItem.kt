package at.martinthedragon.nucleartech.items

import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.text.*
import net.minecraft.world.World

open class AutoTooltippedItem(properties: Properties) : Item(properties) {
    override fun appendHoverText(stack: ItemStack, worldIn: World?, tooltip: MutableList<ITextComponent>, flagIn: ITooltipFlag) {
        autoTooltip(stack, tooltip)
    }
}

fun autoTooltip(stack: ItemStack, tooltip: MutableList<ITextComponent>) {
    val translations = I18n.get("${stack.descriptionId}.desc").split('\n')

    for (translation in translations)
        tooltip.add(StringTextComponent(translation).withStyle(Style.EMPTY.applyFormat(TextFormatting.GRAY)))
}
