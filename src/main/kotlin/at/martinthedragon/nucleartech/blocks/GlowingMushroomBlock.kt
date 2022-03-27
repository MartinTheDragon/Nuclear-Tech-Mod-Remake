package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.world.gen.WorldGen
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.MushroomBlock
import net.minecraft.world.level.block.state.BlockState
import java.util.*
import java.util.function.Supplier

class GlowingMushroomBlock(properties: Properties) : MushroomBlock(properties, Supplier { WorldGen.TreeFeatures.HUGE_GLOWING_MUSHROOM }) {
    override fun canSurvive(state: BlockState, world: LevelReader, pos: BlockPos): Boolean =
        world.getBlockState(pos.below()).`is`(NuclearTags.Blocks.GLOWING_MUSHROOM_GROW_BLOCK)

    override fun randomTick(state: BlockState, world: ServerLevel, pos: BlockPos, random: Random) {
        if (NuclearConfig.world.enableGlowingMyceliumSpread.get() &&
            world.getBlockState(pos.below()).`is`(ModBlocks.deadGrass.get()) &&
            random.nextInt(5) == 0
        ) {
            world.setBlockAndUpdate(pos.below(), ModBlocks.glowingMycelium.get().defaultBlockState()) // create new mycelium
        }
        if (random.nextInt(if (world.getBlockState(pos.below()).`is`(ModBlocks.glowingMycelium.get())) 10 else 1000) != 0) return
        var currentPos = pos
        var count = 5
        for (blockPos in BlockPos.betweenClosed(currentPos.offset(-5, -1, -5), currentPos.offset(5, 1, 5))) {
            if (world.getBlockState(blockPos).`is`(this)) {
                count--
                if (count <= 0) return
            }
        }
        var newPos = currentPos.offset(
            random.nextInt(4) - 1,
            random.nextInt(2) - random.nextInt(2),
            random.nextInt(4) - 1
        )
        for (k in 0..3) { // spread
            if (world.isEmptyBlock(newPos) && state.canSurvive(world, newPos)) {
                currentPos = newPos
            }
            newPos = currentPos.offset(
                random.nextInt(4) - 1,
                random.nextInt(2) - random.nextInt(2),
                random.nextInt(4) - 1
            )
        }
        if (world.isEmptyBlock(newPos) && state.canSurvive(world, newPos)) {
            world.setBlock(newPos, state, 2)
        }
    }
}
