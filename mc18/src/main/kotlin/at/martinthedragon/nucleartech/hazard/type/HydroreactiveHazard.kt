package at.martinthedragon.nucleartech.hazard.type

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.extensions.red
import at.martinthedragon.nucleartech.hazard.modifier.HazardModifier
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Explosion
import kotlin.math.ln

class HydroreactiveHazard : HazardType {
    override fun tick(target: LivingEntity, itemStack: ItemStack, level: Float) {
        // TODO config

        if (!target.level.isClientSide && target.isInWater) {
            target.level.explode(null, target.x, target.y, target.z, level * (ln(itemStack.count.toFloat()) + 1), false, Explosion.BlockInteraction.DESTROY)
            itemStack.count = 0
        }
    }

    override fun tickDropped(itemEntity: ItemEntity, level: Float) {
        if (!itemEntity.level.isClientSide && itemEntity.isInWaterOrRain) {
            itemEntity.discard()
            itemEntity.level.explode(null, itemEntity.x, itemEntity.y, itemEntity.z, level * (ln(itemEntity.item.count.toFloat()) + 1), false, Explosion.BlockInteraction.DESTROY)
        }
    }

    override fun appendHoverText(itemStack: ItemStack, level: Float, modifiers: List<HazardModifier>, player: Player?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        tooltip += LangKeys.HAZARD_HYDROREACTIVE.red()
    }
}
