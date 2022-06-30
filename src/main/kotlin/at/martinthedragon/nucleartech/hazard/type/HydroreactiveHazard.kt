package at.martinthedragon.nucleartech.hazard.type

import at.martinthedragon.nucleartech.hazard.modifier.HazardModifier
import at.martinthedragon.nucleartech.ntmTranslation
import at.martinthedragon.nucleartech.red
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Explosion

class HydroreactiveHazard : HazardType {
    override fun tick(target: LivingEntity, itemStack: ItemStack, level: Float) {
        // TODO config

        if (target.isInWater) {
            itemStack.count = 0
            target.level.explode(null, target.x, target.y, target.z, level, false, Explosion.BlockInteraction.BREAK)
        }
    }

    override fun tickDropped(itemEntity: ItemEntity, level: Float) {
        if (itemEntity.isInWaterOrRain) {
            itemEntity.discard()
            itemEntity.level.explode(null, itemEntity.x, itemEntity.y, itemEntity.z, level, false, Explosion.BlockInteraction.BREAK)
        }
    }

    override fun appendHoverText(itemStack: ItemStack, level: Float, modifiers: List<HazardModifier>, player: Player?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        tooltip += ntmTranslation("hazard.hydroreactive").red()
    }
}
