package at.martinthedragon.nucleartech.items

import net.minecraft.world.item.ItemStack
import net.minecraftforge.event.entity.living.LivingAttackEvent

interface AttackHandler {
    fun handleAttack(event: LivingAttackEvent, stack: ItemStack)
}
