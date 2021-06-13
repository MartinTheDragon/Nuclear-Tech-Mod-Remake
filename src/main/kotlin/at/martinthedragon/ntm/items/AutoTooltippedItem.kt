package at.martinthedragon.ntm.items

import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.Style
import net.minecraft.util.text.TextFormatting
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World

open class AutoTooltippedItem(properties: Properties) : Item(properties) {
    override fun appendHoverText(stack: ItemStack, worldIn: World?, tooltip: MutableList<ITextComponent>, flagIn: ITooltipFlag) {
        autoTooltip(stack, tooltip)
    }
}

fun autoTooltip(stack: ItemStack, tooltip: MutableList<ITextComponent>) {
    val translationKey = "${stack.descriptionId}.desc"

    var test = 0

    while (I18n.exists(translationKey + test))
        test++

    for (i in 0 until test)
        tooltip.add(TranslationTextComponent(translationKey + i).withStyle(Style.EMPTY.applyFormat(TextFormatting.GRAY)))
}
