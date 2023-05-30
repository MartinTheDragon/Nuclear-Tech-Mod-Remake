package at.martinthedragon.nucleartech.coremodules.minecraft.world.item.context

import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPos
import at.martinthedragon.nucleartech.coremodules.minecraft.core.Direction
import at.martinthedragon.nucleartech.coremodules.minecraft.world.InteractionHand
import at.martinthedragon.nucleartech.coremodules.minecraft.world.entity.player.Player
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.ItemStack
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.Level

interface UseOnContext {
    val player: Player?
    val hand: InteractionHand
    val level: Level
    val itemInHand: ItemStack
    val clickedPos: BlockPos

    val horizontalDirection get() = player?.getDirection() ?: Direction.NORTH
}
