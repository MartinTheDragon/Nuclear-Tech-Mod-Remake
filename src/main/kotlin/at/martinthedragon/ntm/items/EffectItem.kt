package at.martinthedragon.ntm.items

import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World

open class EffectItem(val effectTypes: List<EffectTypes>, properties: Properties, val radPerSecond: Float = 0f) : Item(properties) {
    enum class EffectTypes(val effectInfo: ITextComponent) {
        Radioactive(TranslationTextComponent("item.ntm.any.tooltip.radiation.radioactive").func_240701_a_(TextFormatting.GREEN)),
        Blinding(TranslationTextComponent("item.ntm.any.tooltip.radiation.blinding").func_240701_a_(TextFormatting.DARK_AQUA)),
        Hot(TranslationTextComponent("item.ntm.any.tooltip.radiation.hot").func_240701_a_(TextFormatting.GOLD))
    }

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<ITextComponent>, flagIn: ITooltipFlag) {
        for (effectType in effectTypes) {
            tooltip.add(effectType.effectInfo)
        }
        if (radPerSecond > 0f)
            tooltip.add(StringTextComponent("$radPerSecond RAD/s").func_240701_a_(TextFormatting.YELLOW))
    }
}
