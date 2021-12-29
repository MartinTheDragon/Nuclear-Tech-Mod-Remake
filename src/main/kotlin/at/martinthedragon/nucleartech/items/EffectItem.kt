package at.martinthedragon.nucleartech.items

import at.martinthedragon.nucleartech.hazards.EntityContaminationEffects
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

open class EffectItem(val effectTypes: List<EffectTypes>, properties: Properties, val radPerSecond: Float = 0f) : Item(properties) {
    enum class EffectTypes(val effectInfo: Component) {
        Radioactive(TranslatableComponent("item.nucleartech.any.tooltip.radiation.radioactive").withStyle(ChatFormatting.GREEN)),
        Blinding(TranslatableComponent("item.nucleartech.any.tooltip.radiation.blinding").withStyle(ChatFormatting.DARK_AQUA)),
        Hot(TranslatableComponent("item.nucleartech.any.tooltip.radiation.hot").withStyle(ChatFormatting.GOLD))
    }

    override fun appendHoverText(stack: ItemStack, worldIn: Level?, tooltip: MutableList<Component>, flagIn: TooltipFlag) {
        // separate from other tooltip
        if (autoTooltip(stack, tooltip, true)) tooltip.add(TextComponent.EMPTY)

        for (effectType in effectTypes) tooltip.add(effectType.effectInfo)
        if (radPerSecond > 0f)
            tooltip.add(TextComponent("${radPerSecond * stack.count} RAD/s").withStyle(ChatFormatting.YELLOW))
    }

    override fun inventoryTick(
        stack: ItemStack,
        world: Level,
        entity: Entity,
        itemSlot: Int,
        isSelected: Boolean
    ) {
        if (entity !is LivingEntity) return
        if (entity is Player && entity.isCreative) return
        if (EffectTypes.Radioactive in effectTypes) EntityContaminationEffects.contaminate(entity, EntityContaminationEffects.HazardType.Radiation, EntityContaminationEffects.ContaminationType.Creative, radPerSecond * stack.count / 20f)
        if (EffectTypes.Blinding in effectTypes) entity.addEffect(MobEffectInstance(MobEffects.BLINDNESS, 100))
        if (EffectTypes.Hot in effectTypes) entity.setSecondsOnFire(5)
    }
}
