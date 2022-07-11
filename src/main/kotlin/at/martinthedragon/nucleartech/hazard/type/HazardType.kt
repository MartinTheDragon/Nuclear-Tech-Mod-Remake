package at.martinthedragon.nucleartech.hazard.type

import at.martinthedragon.nucleartech.hazard.modifier.HazardModifier
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag

/**
 * Defines the behaviour of a hazard inside an inventory or in the world.
 */
interface HazardType {
    /**
     * Ticks the [itemStack] inside the inventory of the [target].
     *
     * Called on client and server.
     */
    fun tick(target: LivingEntity, itemStack: ItemStack, level: Float)

    /**
     * Ticks the [itemEntity] entity in the world.
     *
     * Called on client and server.
     */
    fun tickDropped(itemEntity: ItemEntity, level: Float)

    /**
     * Adds [tooltip] information for the [itemStack].
     *
     * Called on client only.
     */
    fun appendHoverText(itemStack: ItemStack, level: Float, modifiers: List<HazardModifier>, player: Player?, tooltip: MutableList<Component>, flag: TooltipFlag)
}
