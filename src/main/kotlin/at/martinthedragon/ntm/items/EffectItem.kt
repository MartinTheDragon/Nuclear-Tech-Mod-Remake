package at.martinthedragon.ntm.items

import at.martinthedragon.ntm.Radiation
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World

open class EffectItem(val effectTypes: List<EffectTypes>, properties: Properties, val radPerSecond: Float = 0f) : Item(properties) {
    enum class EffectTypes(val effectInfo: ITextComponent) {
        Radioactive(TranslationTextComponent("item.ntm.any.tooltip.radiation.radioactive").mergeStyle(TextFormatting.GREEN)),
        Blinding(TranslationTextComponent("item.ntm.any.tooltip.radiation.blinding").mergeStyle(TextFormatting.DARK_AQUA)),
        Hot(TranslationTextComponent("item.ntm.any.tooltip.radiation.hot").mergeStyle(TextFormatting.GOLD))
    }

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<ITextComponent>, flagIn: ITooltipFlag) {
        for (effectType in effectTypes) {
            tooltip.add(effectType.effectInfo)
        }
        if (radPerSecond > 0f)
            tooltip.add(StringTextComponent("$radPerSecond RAD/s").mergeStyle(TextFormatting.YELLOW))
    }

    override fun inventoryTick(
        stack: ItemStack,
        world: World,
        entity: Entity,
        itemSlot: Int,
        isSelected: Boolean
    ) {
        if (entity !is LivingEntity) return
        if (EffectTypes.Radioactive in effectTypes) Radiation.addEntityIrradiation(entity, radPerSecond * stack.count / 20f)
        if (EffectTypes.Blinding in effectTypes) entity.addPotionEffect(EffectInstance(Effects.BLINDNESS, 100))
        if (EffectTypes.Hot in effectTypes) entity.setFire(5)
    }
}
