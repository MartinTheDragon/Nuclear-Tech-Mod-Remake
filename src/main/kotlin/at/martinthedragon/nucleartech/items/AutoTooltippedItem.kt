package at.martinthedragon.nucleartech.items

import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.Style
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World

open class AutoTooltippedItem(properties: Properties) : Item(properties) {
    override fun appendHoverText(stack: ItemStack, worldIn: World?, tooltip: MutableList<ITextComponent>, flagIn: ITooltipFlag) {
        autoTooltip(stack, tooltip)
    }
}

/**
 * Automatically splits translations by `\n` delimiters and appends them to the tooltip. Returns whether the translation
 * exists.
 */
fun autoTooltip(stack: ItemStack, tooltip: MutableList<ITextComponent>, ignoreMissing: Boolean = false): Boolean {
    val baseString = "${stack.descriptionId}.desc"
    val translations = I18n.get(baseString).split('\n')
    val exists = I18n.exists(baseString)

    if (exists) for (translation in translations)
        tooltip.add(StringTextComponent(translation).withStyle(Style.EMPTY.applyFormat(TextFormatting.GRAY)))
    else if (!ignoreMissing) tooltip.add(StringTextComponent("Missing Translation").withStyle(TextFormatting.RED))

    return exists
}
