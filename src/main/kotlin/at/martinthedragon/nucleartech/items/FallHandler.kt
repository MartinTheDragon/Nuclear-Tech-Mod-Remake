package at.martinthedragon.nucleartech.items

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraftforge.event.entity.living.LivingFallEvent
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent

interface FallHandler {
    fun handleFall(entity: LivingEntity, distance: Float, multiplier: Float, stack: ItemStack)

    fun handleFall(event: PlayerFlyableFallEvent, stack: ItemStack) = handleFall(event.entityLiving, event.distance, event.multiplier, stack)
    fun handleFall(event: LivingFallEvent, stack: ItemStack) = handleFall(event.entityLiving, event.distance, event.damageMultiplier, stack)
}
