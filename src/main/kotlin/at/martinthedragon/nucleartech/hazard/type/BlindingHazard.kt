package at.martinthedragon.nucleartech.hazard.type

import at.martinthedragon.nucleartech.darkAqua
import at.martinthedragon.nucleartech.hazard.modifier.HazardModifier
import at.martinthedragon.nucleartech.ntmTranslation
import net.minecraft.network.chat.Component
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.entity.EntityTypeTest
import kotlin.math.ceil
import kotlin.math.max

class BlindingHazard : HazardType {
    override fun tick(target: LivingEntity, itemStack: ItemStack, level: Float) {
        // TODO protection

        target.addEffect(MobEffectInstance(MobEffects.BLINDNESS, ceil(max(level * 20, 40F)).toInt(), ceil(level).toInt(), false, false, true))
    }

    override fun tickDropped(itemEntity: ItemEntity, level: Float) {
        val world = itemEntity.level
        for (entity in world.getEntities(EntityTypeTest.forClass(LivingEntity::class.java), itemEntity.boundingBox.inflate(max(level * 2.0, 3.0)), Entity::isAlive)) {
            // could be inverse square for more of 'muh realism
            entity.addEffect(MobEffectInstance(MobEffects.BLINDNESS, max(ceil(max(level * 20, 40F) / entity.distanceTo(itemEntity)).toInt(), 1), ceil(level).toInt(), false, false, true), itemEntity)
        }
    }

    override fun appendHoverText(itemStack: ItemStack, level: Float, modifiers: List<HazardModifier>, player: Player?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        tooltip += ntmTranslation("hazard.blinding").darkAqua()
    }
}
