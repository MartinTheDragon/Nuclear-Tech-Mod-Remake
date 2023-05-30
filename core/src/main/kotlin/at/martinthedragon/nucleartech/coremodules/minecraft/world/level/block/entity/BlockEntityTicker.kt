package at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.entity

import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPos
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.Level
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state.BlockState

fun interface BlockEntityTicker<T : BlockEntity> {
    fun tick(level: Level, pos: BlockPos, state: BlockState, blockEntity: T)
}
