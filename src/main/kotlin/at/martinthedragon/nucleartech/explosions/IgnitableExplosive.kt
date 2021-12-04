package at.martinthedragon.nucleartech.explosions

import at.martinthedragon.nucleartech.blocks.entities.BombBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level

/**
 * Intended to be inherited by a [net.minecraft.world.level.block.Block] and makes the block able to be detonated by a detonator.
 */
interface IgnitableExplosive {
    enum class DetonationResult {
        /** The explosive was detonated */
        Success,
        /** An explosive at the specified position cannot be found */
        InvalidPosition,
        /** An unexpected different [net.minecraft.world.level.block.entity.BlockEntity] is at the position, or there isn't one present */
        InvalidTileEntity,
        /** The explosive is missing components required for detonation */
        Incomplete,
        /** The explosive did not detonate because of some internal logic */
        Prohibited,
        /** The bomb was not detonated, and the reason why is unknown */
        Unknown
    }

    /**
     * Detonates the explosive at the given [pos] in a [level] and returns a [DetonationResult].
     *
     * Defaults to accessing a [net.minecraft.world.level.block.entity.BlockEntity] at [pos] and checking if it implements
     * [at.martinthedragon.nucleartech.entity.BombBlockEntity], and if so, tries to detonate it.
     */
    fun detonate(level: Level, pos: BlockPos): DetonationResult {
        if (level.isClientSide) return DetonationResult.Unknown
        val blockState = level.getBlockState(pos)
        return when {
            blockState.block !is IgnitableExplosive -> DetonationResult.InvalidPosition
            !blockState.hasBlockEntity() -> DetonationResult.InvalidTileEntity
            else -> {
                val tileEntity = level.getBlockEntity(pos) ?: return DetonationResult.InvalidTileEntity
                when {
                    tileEntity !is BombBlockEntity<*> -> DetonationResult.InvalidTileEntity
                    !tileEntity.isComplete() -> DetonationResult.Incomplete
                    !tileEntity.canDetonate() -> DetonationResult.Prohibited
                    tileEntity.detonate() -> DetonationResult.Success
                    else -> DetonationResult.Unknown
                }
            }
        }
    }
}
