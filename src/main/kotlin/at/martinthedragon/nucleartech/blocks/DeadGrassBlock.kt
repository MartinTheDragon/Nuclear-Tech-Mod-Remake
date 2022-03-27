package at.martinthedragon.nucleartech.blocks

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.SnowLayerBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.lighting.LayerLightEngine
import java.util.*

open class DeadGrassBlock(properties: Properties) : Block(properties) {
    protected fun canExist(state: BlockState, levelReader: LevelReader, pos: BlockPos): Boolean {
        val abovePos = pos.above()
        val aboveState = levelReader.getBlockState(abovePos)
        return when { // TODO fallout
            aboveState.`is`(Blocks.SNOW) && aboveState.getValue(SnowLayerBlock.LAYERS) == 1 -> true
            aboveState.fluidState.amount == 8 -> false
            else -> {
                val i = LayerLightEngine.getLightBlockInto(levelReader, state, pos, aboveState, abovePos, Direction.UP, aboveState.getLightBlock(levelReader, abovePos))
                i < levelReader.maxLightLevel
            }
        }
    }

    // TODO auto cleanup
    @Suppress("DEPRECATION")
    override fun randomTick(state: BlockState, level: ServerLevel, pos: BlockPos, random: Random) {
        if (!canExist(state, level, pos) && level.isAreaLoaded(pos, 3))
            level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState())
    }
}
