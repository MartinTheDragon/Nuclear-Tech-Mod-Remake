package at.martinthedragon.nucleartech.api.item

import net.minecraft.world.item.ItemStack
import net.minecraftforge.event.entity.living.LivingAttackEvent

public interface AttackHandler {
    public fun handleAttack(event: LivingAttackEvent, stack: ItemStack)
}
