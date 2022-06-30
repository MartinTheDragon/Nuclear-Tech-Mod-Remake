package at.martinthedragon.nucleartech.hazard.type

import at.martinthedragon.nucleartech.capability.Capabilities
import at.martinthedragon.nucleartech.capability.contamination.addAsbestos
import at.martinthedragon.nucleartech.capability.contamination.addIrradiation
import at.martinthedragon.nucleartech.hazard.modifier.HazardModifier
import at.martinthedragon.nucleartech.ntmTranslation
import at.martinthedragon.nucleartech.white
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import kotlin.math.min

class AsbestosHazard : HazardType {
    override fun tick(target: LivingEntity, itemStack: ItemStack, level: Float) {
        // TODO asbestos config

        // TODO asbestos protection

        Capabilities.getContamination(target)?.addAsbestos(min(level.toInt(), 10))
    }

    override fun tickDropped(itemEntity: ItemEntity, level: Float) {}

    override fun appendHoverText(itemStack: ItemStack, level: Float, modifiers: List<HazardModifier>, player: Player?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        tooltip += ntmTranslation("hazard.asbestos").white()
    }
}
