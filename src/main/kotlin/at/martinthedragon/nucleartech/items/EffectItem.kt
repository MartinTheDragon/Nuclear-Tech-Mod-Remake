package at.martinthedragon.nucleartech.items

import at.martinthedragon.nucleartech.Radiation
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
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
        Radioactive(TranslationTextComponent("item.nucleartech.any.tooltip.radiation.radioactive").withStyle(TextFormatting.GREEN)),
        Blinding(TranslationTextComponent("item.nucleartech.any.tooltip.radiation.blinding").withStyle(TextFormatting.DARK_AQUA)),
        Hot(TranslationTextComponent("item.nucleartech.any.tooltip.radiation.hot").withStyle(TextFormatting.GOLD))
    }

    override fun appendHoverText(stack: ItemStack, worldIn: World?, tooltip: MutableList<ITextComponent>, flagIn: ITooltipFlag) {
        for (effectType in effectTypes) {
            tooltip.add(effectType.effectInfo)
        }
        if (radPerSecond > 0f)
            tooltip.add(StringTextComponent("${radPerSecond * stack.count} RAD/s").withStyle(TextFormatting.YELLOW))
    }

    override fun inventoryTick(
        stack: ItemStack,
        world: World,
        entity: Entity,
        itemSlot: Int,
        isSelected: Boolean
    ) {
        if (entity !is LivingEntity) return
        if (entity is PlayerEntity && entity.isCreative) return
        if (EffectTypes.Radioactive in effectTypes) Radiation.addEntityIrradiation(entity, radPerSecond * stack.count / 20f)
        if (EffectTypes.Blinding in effectTypes) entity.addEffect(EffectInstance(Effects.BLINDNESS, 100))
        if (EffectTypes.Hot in effectTypes) entity.setSecondsOnFire(5)
    }
}
