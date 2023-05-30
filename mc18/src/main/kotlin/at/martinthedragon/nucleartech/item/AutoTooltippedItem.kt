package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.extensions.gray
import at.martinthedragon.nucleartech.extensions.red
import net.minecraft.client.resources.language.I18n
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

//open class AutoTooltippedItem(properties: Properties) : Item(properties) {
//    override fun appendHoverText(stack: ItemStack, level: Level?, tooltip: MutableList<Component>, flag: TooltipFlag) {
//        autoTooltip(stack, tooltip)
//    }
//}
//
///**
// * Automatically splits translations by `\n` delimiters and appends them to the tooltip. Returns whether the translation
// * exists.
// */
//fun autoTooltip(stack: ItemStack, tooltip: MutableList<Component>, ignoreMissing: Boolean = false): Boolean {
//    val baseString = "${stack.descriptionId}.desc"
//    val translations = I18n.get(baseString).split('\n')
//    val exists = I18n.exists(baseString)
//
//    val brokenPolaroid = "${baseString}11"
//    if (NuclearTech.polaroidBroken && I18n.exists(brokenPolaroid)) {
//        for (brokenPolaroidText in I18n.get(brokenPolaroid).split('\n'))
//            tooltip.add(TextComponent(brokenPolaroidText).gray())
//    } else {
//        if (exists) for (translation in translations)
//            tooltip.add(TextComponent(translation).gray())
//        else if (!ignoreMissing) tooltip.add(TextComponent("Missing Translation").red())
//    }
//
//    return exists
//}
