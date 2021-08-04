package at.martinthedragon.nucleartech.explosions

import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
 * Intended to be inherited by a [net.minecraft.block.Block] and makes the block able to be detonated by a detonator.
 */
interface IgnitableExplosive {
    enum class DetonationResult {
        /** The explosive was detonated */
        Success,
        /** An explosive at the specified position cannot be found */
        InvalidPosition,
        /** The explosive is missing components required for detonation */
        Incomplete,
        /** The explosive did not detonate because of some internal logic */
        Prohibited,
        /** The bomb was not detonated, and the reason why is unknown */
        Unknown
    }

    /**
     * Detonates the explosive at the given [pos] in a [world] and returns a [DetonationResult].
     */
    fun detonate(world: World, pos: BlockPos): DetonationResult
}
