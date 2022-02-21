package at.martinthedragon.nucleartech.blocks.multi

import net.minecraft.core.BlockPos
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.state.BlockState

/**
 * API for prototyping a multi block structure using [place] and [fill], and placing it in a world using [finish]
 */
interface MultiBlockPlacer {
    /** Will place [blockState] at [relativePos] from origin point (0, 0, 0) in an internal buffer */
    fun place(relativePos: BlockPos, blockState: BlockState)

    fun place(x: Int, y: Int, z: Int, blockState: BlockState) =
        place(BlockPos(x, y, z), blockState)

    /** Replaces all empty block states in a cuboid volume with [blockState] in the internal buffer */
    fun fill(from: BlockPos, to: BlockPos, blockState: BlockState)

    fun fill(fromX: Int, fromY: Int, fromZ: Int, toX: Int, toY: Int, toZ: Int, blockState: BlockState) =
        fill(BlockPos(fromX, fromY, fromZ), BlockPos(toX, toY, toZ), blockState)

    /** Returns a new immutable map with the current placement data */
    fun getPlacementData(): Map<BlockPos, BlockState>

    /** Checks whether the structure won't intersect other blocks or entities in [level] at [pos] */
    fun canPlaceAt(level: LevelAccessor, pos: BlockPos): Boolean

    /**
     * Finalizes the structure and places it at [pos] in [level] and returns whether it was successful
     *
     * The structure gets the origin point (0, 0, 0) translated to [pos].
     *
     * No block will get placed at the origin.
     *
     * Also clears the internal buffer.
     */
    fun finish(level: LevelAccessor, pos: BlockPos): Boolean
}
