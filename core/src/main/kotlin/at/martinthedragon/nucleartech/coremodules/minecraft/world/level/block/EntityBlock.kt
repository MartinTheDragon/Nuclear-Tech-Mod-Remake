package at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block

import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPos
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.Level
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.entity.BlockEntity
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.entity.BlockEntityTicker
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.entity.BlockEntityType
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state.BlockState

interface EntityBlock {
    fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity?
    fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? = null
}
