package at.martinthedragon.nucleartech.world

import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import kotlin.math.max

interface ChunkRadiationHandler {
    fun getRadiation(world: World, pos: BlockPos): Float
    fun setRadiation(world: World, pos: BlockPos, radiation: Float)
    fun incrementRadiation(world: World, pos: BlockPos, radiation: Float) {
        setRadiation(world, pos, getRadiation(world, pos) + radiation)
    }
    fun decrementRadiation(world: World, pos: BlockPos, radiation: Float) {
        setRadiation(world, pos, max(getRadiation(world, pos) - radiation, 0F))
    }
}
