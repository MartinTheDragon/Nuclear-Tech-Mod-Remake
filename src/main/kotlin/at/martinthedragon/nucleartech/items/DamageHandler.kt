package at.martinthedragon.nucleartech.items

import net.minecraft.world.item.ItemStack
import net.minecraftforge.event.entity.living.LivingHurtEvent

interface DamageHandler {
    fun handleDamage(event: LivingHurtEvent, stack: ItemStack)
}
