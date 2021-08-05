package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.NuclearTags
import net.minecraft.block.BlockState
import net.minecraft.particles.ParticleTypes
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import java.util.*

class GlowingMycelium(properties: Properties) : DeadGrass(properties) {
    // TODO contamination effect

    // TODO configurable mycelium
    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        @Suppress("DEPRECATION")
        super.randomTick(state, world, pos, random)

        if (random.nextInt(8) == 0)
            for (i in -1..1) { // spread
                for (j in -1..1) {
                    for (k in -1..1) {
                        val otherBlockPos = pos.offset(i, j, k)
                        val otherBlock = world.getBlockState(otherBlockPos)
                        if (otherBlock.isSolidRender(world, otherBlockPos) && canExist(state, world, otherBlockPos) && (otherBlock.`is`(NuclearTags.Blocks.GLOWING_MYCELIUM_SPREADABLE))) {
                            world.setBlockAndUpdate(otherBlockPos, ModBlocks.glowingMycelium.get().defaultBlockState())
                        }
                    }
                }
            }

        if (random.nextInt(10) == 0) { // add new mushrooms
            var count = 0
            for (i in -5..4) {
                for (j in -5..5) {
                    for (k in -5..4) {
                        val block = world.getBlockState(pos.offset(i, j, k))
                        if (block.`is`(ModBlocks.glowingMushroom.get())) {
                            count++
                        }
                    }
                }
            }
            if (count == 0) world.setBlockAndUpdate(pos.above(), ModBlocks.glowingMushroom.get().defaultBlockState())
        }
    }

    override fun animateTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        if (random.nextInt(10) == 0) {
            world.addParticle(
                ParticleTypes.MYCELIUM,
                pos.x.toDouble() + random.nextDouble(),
                pos.y.toDouble() + 1.1,
                pos.z.toDouble() + random.nextDouble(),
                0.0, 0.0, 0.0
            )
        }
    }
}
