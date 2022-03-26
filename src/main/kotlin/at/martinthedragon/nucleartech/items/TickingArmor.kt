package at.martinthedragon.nucleartech.items

import net.minecraft.world.item.ItemStack
import net.minecraftforge.event.entity.living.LivingEvent

interface TickingArmor {
    fun handleTick(event: LivingEvent.LivingUpdateEvent, stack: ItemStack)
}
