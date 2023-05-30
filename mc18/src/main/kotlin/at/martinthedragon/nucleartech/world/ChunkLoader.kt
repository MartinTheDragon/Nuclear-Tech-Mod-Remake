package at.martinthedragon.nucleartech.world

import net.minecraft.world.level.ChunkPos

// TODO make this a little less crude
interface ChunkLoader {
    val forcedChunks: Set<ChunkPos>
    val tickingForcedChunks: Set<ChunkPos>

    fun forceChunks(chunks: Set<ChunkPos>)
    fun forceTickingChunks(chunks: Set<ChunkPos>)

    fun unForceChunks()
    fun unForceTickingChunks()
}
