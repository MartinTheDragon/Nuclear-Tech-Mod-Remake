package at.martinthedragon.nucleartech.block.entity

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level

interface ExtendedMultiBlockInfo {
    fun providesMenu(level: Level, pos: BlockPos, player: Player): Boolean
}
