package at.martinthedragon.nucleartech.api.items

import net.minecraft.world.item.ItemStack
import net.minecraftforge.event.entity.living.LivingHurtEvent

public interface DamageHandler {
    public fun handleDamage(event: LivingHurtEvent, stack: ItemStack)
}
