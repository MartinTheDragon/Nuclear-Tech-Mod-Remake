package at.martinthedragon.nucleartech.item

import net.minecraft.ChatFormatting
import net.minecraft.client.resources.language.I18n
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

open class AutoTooltippedItem(properties: Properties) : Item(properties) {
    override fun appendHoverText(stack: ItemStack, worldIn: Level?, tooltip: MutableList<Component>, flagIn: TooltipFlag) {
        autoTooltip(stack, tooltip)
    }
}

/**
 * Automatically splits translations by `\n` delimiters and appends them to the tooltip. Returns whether the translation
 * exists.
 */
fun autoTooltip(stack: ItemStack, tooltip: MutableList<Component>, ignoreMissing: Boolean = false): Boolean {
    val baseString = "${stack.descriptionId}.desc"
    val translations = I18n.get(baseString).split('\n')
    val exists = I18n.exists(baseString)

    if (exists) for (translation in translations)
        tooltip.add(TextComponent(translation).withStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)))
    else if (!ignoreMissing) tooltip.add(TextComponent("Missing Translation").withStyle(ChatFormatting.RED))

    return exists
}
