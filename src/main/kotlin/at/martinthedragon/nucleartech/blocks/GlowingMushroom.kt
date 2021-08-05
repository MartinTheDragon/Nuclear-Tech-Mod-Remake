package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.worldgen.WorldGeneration
import net.minecraft.block.BlockState
import net.minecraft.block.MushroomBlock
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorldReader
import net.minecraft.world.server.ServerWorld
import java.util.*

class GlowingMushroom(properties: Properties) : MushroomBlock(properties) {
    override fun canSurvive(state: BlockState, world: IWorldReader, pos: BlockPos): Boolean =
        world.getBlockState(pos.below()).`is`(NuclearTags.Blocks.GLOWING_MUSHROOM_GROW_BLOCK)

    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        // TODO check mycelium config
        if (world.getBlockState(pos.below()).`is`(ModBlocks.deadGrass.get()) && random.nextInt(5) == 0)
            world.setBlockAndUpdate(pos.below(), ModBlocks.glowingMycelium.get().defaultBlockState()) // create new mycelium
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

    override fun growMushroom(world: ServerWorld, pos: BlockPos, state: BlockState, random: Random): Boolean {
        world.removeBlock(pos, false)
        val feature = WorldGeneration.ConfiguredFeatures.BIG_GLOWING_MUSHROOM
        return if (feature.place(world, world.chunkSource.getGenerator(), random, pos)) {
            true
        } else {
            world.setBlockAndUpdate(pos, state)
            false
        }
    }
}
