package at.martinthedragon.nucleartech.hazard.type

import at.martinthedragon.nucleartech.gold
import at.martinthedragon.nucleartech.hazard.modifier.HazardModifier
import at.martinthedragon.nucleartech.ntmTranslation
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import kotlin.math.ceil

class HeatHazard : HazardType {
    override fun tick(target: LivingEntity, itemStack: ItemStack, level: Float) {
        // TODO config

        // TODO reacher, gloves

        if (!target.isInWaterOrRain && level > 0) {
            // TODO 528
            target.setSecondsOnFire(ceil(level).toInt())
        }
    }

    override fun tickDropped(itemEntity: ItemEntity, level: Float) {}

    override fun appendHoverText(itemStack: ItemStack, level: Float, modifiers: List<HazardModifier>, player: Player?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        tooltip += ntmTranslation("hazard.heat").gold()
    }
}
