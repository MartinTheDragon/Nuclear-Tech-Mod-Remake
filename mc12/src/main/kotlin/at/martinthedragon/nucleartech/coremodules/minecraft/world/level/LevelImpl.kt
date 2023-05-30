package at.martinthedragon.nucleartech.coremodules.minecraft.world.level

import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPos
import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPosImpl
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.Block
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.entity.BlockEntity
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.chunk.LevelChunk
import at.martinthedragon.nucleartech.sorcerer.Injection

@Injection @JvmInline
value class LevelImpl(val delegate: net.minecraft.world.World) : Level {
    override val isClientSide: Boolean get() = delegate.isRemote

    override fun hasChunkAt(pos: BlockPos) = delegate.isChunkGeneratedAt(pos.x, pos.z)

    override fun getChunkAt(pos: BlockPos): LevelChunk {
        TODO("Not yet implemented")
    }

    override fun getBlockEntity(pos: BlockPos): BlockEntity? {
        TODO("Not yet implemented")
    }

    override fun isLoaded(pos: BlockPos): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateNeighbourForOutputSignal(pos: BlockPos, block: Block) {
        TODO("Not yet implemented")
    }
}
