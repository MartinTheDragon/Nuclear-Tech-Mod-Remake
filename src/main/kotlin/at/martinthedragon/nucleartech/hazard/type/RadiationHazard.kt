package at.martinthedragon.nucleartech.hazard.type

import at.martinthedragon.nucleartech.green
import at.martinthedragon.nucleartech.hazard.EntityContaminationEffects
import at.martinthedragon.nucleartech.hazard.modifier.HazardModifier
import at.martinthedragon.nucleartech.hazard.modifier.evaluateAllModifiers
import at.martinthedragon.nucleartech.ntmTranslation
import at.martinthedragon.nucleartech.yellow
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import kotlin.math.floor

class RadiationHazard : HazardType {
    override fun tick(target: LivingEntity, itemStack: ItemStack, level: Float) {
        if (level <= 0 || target.level.isClientSide) return

        val radiation = level / 20F * itemStack.count // TODO reacher
        EntityContaminationEffects.contaminate(target, EntityContaminationEffects.HazardType.Radiation, EntityContaminationEffects.ContaminationType.Creative, radiation)
    }

    override fun tickDropped(itemEntity: ItemEntity, level: Float) {
        // TODO direct hit radiation
    }

    override fun appendHoverText(itemStack: ItemStack, level: Float, modifiers: List<HazardModifier>, player: Player?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        val radiation = modifiers.evaluateAllModifiers(itemStack, player, level)

        if (radiation < 1E-5) return

        with(tooltip) {
            add(ntmTranslation("hazard.radiation").green())
            add(TextComponent("${floor(radiation * itemStack.count * 1000F) / 1000F} RAD/s").yellow())
        }
    }
}
