package at.martinthedragon.nucleartech.hazard.type

import at.martinthedragon.nucleartech.darkAqua
import at.martinthedragon.nucleartech.hazard.modifier.HazardModifier
import at.martinthedragon.nucleartech.ntmTranslation
import net.minecraft.network.chat.Component
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import kotlin.math.ceil

class BlindingHazard : HazardType {
    override fun tick(target: LivingEntity, itemStack: ItemStack, level: Float) {
        // TODO protection

        target.addEffect(MobEffectInstance(MobEffects.BLINDNESS, ceil(level).toInt(), 0, false, false, true))
    }

    override fun tickDropped(itemEntity: ItemEntity, level: Float) {}

    override fun appendHoverText(itemStack: ItemStack, level: Float, modifiers: List<HazardModifier>, player: Player?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        tooltip += ntmTranslation("hazard.blinding").darkAqua()
    }
}
