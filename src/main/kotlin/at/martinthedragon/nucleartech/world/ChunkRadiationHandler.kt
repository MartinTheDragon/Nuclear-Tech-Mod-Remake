package at.martinthedragon.nucleartech.world

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import kotlin.math.max

interface ChunkRadiationHandler {
    fun getRadiation(world: LevelAccessor, pos: BlockPos): Float
    fun setRadiation(world: Level, pos: BlockPos, radiation: Float)
    fun incrementRadiation(world: Level, pos: BlockPos, radiation: Float) {
        setRadiation(world, pos, getRadiation(world, pos) + radiation)
    }
    fun decrementRadiation(world: Level, pos: BlockPos, radiation: Float) {
        setRadiation(world, pos, max(getRadiation(world, pos) - radiation, 0F))
    }
}
