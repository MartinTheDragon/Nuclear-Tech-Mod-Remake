package at.martinthedragon.nucleartech.explosions

import at.martinthedragon.nucleartech.tileentities.BombTileEntity
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
        /** An unexpected different [net.minecraft.tileentity.TileEntity] is at the position, or there isn't one present */
        InvalidTileEntity,
        /** The explosive is missing components required for detonation */
        Incomplete,
        /** The explosive did not detonate because of some internal logic */
        Prohibited,
        /** The bomb was not detonated, and the reason why is unknown */
        Unknown
    }

    /**
     * Detonates the explosive at the given [pos] in a [world] and returns a [DetonationResult].
     *
     * Defaults to accessing a [net.minecraft.tileentity.TileEntity] at [pos] and checking if it implements
     * [at.martinthedragon.nucleartech.tileentities.BombTileEntity], and if so, tries to detonate it.
     */
    fun detonate(world: World, pos: BlockPos): DetonationResult {
        if (world.isClientSide) return DetonationResult.Unknown
        val blockState = world.getBlockState(pos)
        return when {
            blockState.block !is IgnitableExplosive -> DetonationResult.InvalidPosition
            !blockState.hasTileEntity() -> DetonationResult.InvalidTileEntity
            else -> {
                val tileEntity = world.getBlockEntity(pos) ?: return DetonationResult.InvalidTileEntity
                when {
                    tileEntity !is BombTileEntity<*> -> DetonationResult.InvalidTileEntity
                    !tileEntity.isComplete() -> DetonationResult.Incomplete
                    !tileEntity.canDetonate() -> DetonationResult.Prohibited
                    tileEntity.detonate() -> DetonationResult.Success
                    else -> DetonationResult.Unknown
                }
            }
        }
    }
}
