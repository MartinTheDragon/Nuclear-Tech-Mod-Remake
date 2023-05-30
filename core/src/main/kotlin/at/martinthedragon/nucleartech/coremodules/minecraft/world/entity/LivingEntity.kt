package at.martinthedragon.nucleartech.coremodules.minecraft.world.entity

import at.martinthedragon.nucleartech.coremodules.minecraft.world.InteractionHand
import at.martinthedragon.nucleartech.sorcerer.Linkage
import at.martinthedragon.nucleartech.sorcerer.Linkage.CompatibleVersions.MC18

@Linkage(MC18, "net.minecraft.world.entity.LivingEntity")
interface LivingEntity : Entity {
    fun broadcastBreakEvent(hand: InteractionHand)
}
