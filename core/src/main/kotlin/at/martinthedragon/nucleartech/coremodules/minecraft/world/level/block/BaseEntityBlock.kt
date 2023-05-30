package at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block

import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPos
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.Level
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state.BlockState

abstract class BaseEntityBlock protected constructor(properties: Properties) : Block(properties), EntityBlock {
    override fun triggerEvent(state: BlockState, level: Level, pos: BlockPos, paramA: Int, paramB: Int): Boolean =
        level.getBlockEntity(pos)?.triggerEvent(paramA, paramB) ?: false
}
