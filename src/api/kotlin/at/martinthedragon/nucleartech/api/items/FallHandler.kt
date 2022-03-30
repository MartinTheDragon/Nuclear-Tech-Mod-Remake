package at.martinthedragon.nucleartech.api.items

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraftforge.event.entity.living.LivingFallEvent
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent

public interface FallHandler {
    public fun handleFall(entity: LivingEntity, distance: Float, multiplier: Float, stack: ItemStack)

    public fun handleFall(event: PlayerFlyableFallEvent, stack: ItemStack): Unit = handleFall(event.entityLiving, event.distance, event.multiplier, stack)
    public fun handleFall(event: LivingFallEvent, stack: ItemStack): Unit = handleFall(event.entityLiving, event.distance, event.damageMultiplier, stack)
}
