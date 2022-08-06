package at.martinthedragon.nucleartech.hazard.type

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.capability.Capabilities
import at.martinthedragon.nucleartech.capability.contamination.addDigamma
import at.martinthedragon.nucleartech.extensions.red
import at.martinthedragon.nucleartech.hazard.modifier.HazardModifier
import at.martinthedragon.nucleartech.hazard.modifier.evaluateAllModifiers
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import kotlin.math.floor

class DigammaHazard : HazardType {
    override fun tick(target: LivingEntity, itemStack: ItemStack, level: Float) {
        if (!target.level.isClientSide) {
            Capabilities.getContamination(target)?.addDigamma(level / 20F * itemStack.count)
        }
    }

    override fun tickDropped(itemEntity: ItemEntity, level: Float) {}

    override fun appendHoverText(itemStack: ItemStack, level: Float, modifiers: List<HazardModifier>, player: Player?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        val digamma = modifiers.evaluateAllModifiers(itemStack, player, level)

        with(tooltip) {
            add(LangKeys.HAZARD_DIGAMMA.red())
            add(TextComponent("${floor(digamma * itemStack.count * 10_000F) / 10F} mDRX/s"))
        }
    }
}
