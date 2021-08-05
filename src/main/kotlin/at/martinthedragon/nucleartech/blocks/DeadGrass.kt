package at.martinthedragon.nucleartech.blocks

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.SnowBlock
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorldReader
import net.minecraft.world.lighting.LightEngine
import net.minecraft.world.server.ServerWorld
import java.util.*

open class DeadGrass(properties: Properties) : Block(properties) {
    protected fun canExist(state: BlockState, worldReader: IWorldReader, pos: BlockPos): Boolean {
        val abovePos = pos.above()
        val aboveState = worldReader.getBlockState(abovePos)
        return when { // TODO fallout
            aboveState.`is`(Blocks.SNOW) && aboveState.getValue(SnowBlock.LAYERS) == 1 -> true
            aboveState.fluidState.amount == 8 -> false
            else -> {
                val i = LightEngine.getLightBlockInto(worldReader, state, pos, aboveState, abovePos, Direction.UP, aboveState.getLightBlock(worldReader, abovePos))
                i < worldReader.maxLightLevel
            }
        }
    }

    // TODO auto cleanup
    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        if (!canExist(state, world, pos) && world.isAreaLoaded(pos, 3))
            world.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState())
    }
}
