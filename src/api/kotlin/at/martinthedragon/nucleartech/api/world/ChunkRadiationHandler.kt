package at.martinthedragon.nucleartech.api.world

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import kotlin.math.max

public interface ChunkRadiationHandler {
    public fun getRadiation(world: LevelAccessor, pos: BlockPos): Float

    public fun setRadiation(world: Level, pos: BlockPos, radiation: Float)

    public fun incrementRadiation(world: Level, pos: BlockPos, radiation: Float) {
        setRadiation(world, pos, getRadiation(world, pos) + radiation)
    }

    public fun decrementRadiation(world: Level, pos: BlockPos, radiation: Float) {
        setRadiation(world, pos, max(getRadiation(world, pos) - radiation, 0F))
    }
}
