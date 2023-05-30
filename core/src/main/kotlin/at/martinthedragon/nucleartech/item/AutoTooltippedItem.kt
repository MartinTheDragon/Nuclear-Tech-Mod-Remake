package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.NTech
import at.martinthedragon.nucleartech.coremodules.minecraft.client.resources.language.I18n
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.Component
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.TextComponent
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.gray
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.red
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.Item
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.ItemStack
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.Level

open class AutoTooltippedItem(properties: Properties) : Item(properties) {
    override fun appendHoverText(itemStack: ItemStack, level: Level?, tooltip: MutableList<Component>, extended: Boolean) {
        autoTooltip(itemStack, tooltip)
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

    val brokenPolaroid = "${baseString}11"
    if (NTech.polaroidBroken && I18n.exists(brokenPolaroid)) {
        for (brokenPolaroidText in I18n.get(brokenPolaroid).split('\n'))
            tooltip.add(TextComponent(brokenPolaroidText).gray())
    } else {
        if (exists) for (translation in translations)
            tooltip.add(TextComponent(translation).gray())
        else if (!ignoreMissing) tooltip.add(TextComponent("Missing Translation").red())
    }

    return exists
}
