package at.martinthedragon.nucleartech.coremodules.minecraft.world.level

import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPos
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.Block
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.entity.BlockEntity
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state.BlockState
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.chunk.LevelChunk
import at.martinthedragon.nucleartech.sorcerer.Injected

@Injected
interface Level {
    val isClientSide: Boolean
    fun hasChunkAt(pos: BlockPos): Boolean
    fun getChunkAt(pos: BlockPos): LevelChunk
    fun getBlockState(pos: BlockPos): BlockState
    fun getBlockEntity(pos: BlockPos): BlockEntity?

    fun isLoaded(pos: BlockPos): Boolean

    fun updateNeighbourForOutputSignal(pos: BlockPos, block: Block)
}
