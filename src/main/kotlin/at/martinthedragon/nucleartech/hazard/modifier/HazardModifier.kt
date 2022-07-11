package at.martinthedragon.nucleartech.hazard.modifier

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack

interface HazardModifier {
    fun modify(stack: ItemStack, holder: LivingEntity?, level: Float): Float
}

fun List<HazardModifier>.evaluateAllModifiers(stack: ItemStack, holder: LivingEntity?, level: Float) =
    fold(level) { acc, hazardModifier -> hazardModifier.modify(stack, holder, acc) }
