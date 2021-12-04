package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.config.NuclearConfig
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import java.util.*

class GlowingMycelium(properties: Properties) : DeadGrass(properties) {
    // TODO contamination effect

    override fun randomTick(state: BlockState, level: ServerLevel, pos: BlockPos, random: Random) {
        @Suppress("DEPRECATION")
        super.randomTick(state, level, pos, random)

        if (NuclearConfig.world.enableGlowingMyceliumSpread.get() && random.nextInt(8) == 0) for (i in -1..1) { // spread
            for (j in -1..1) {
                for (k in -1..1) {
                    val otherBlockPos = pos.offset(i, j, k)
                    val otherBlock = level.getBlockState(otherBlockPos)
                    if (otherBlock.isSolidRender(level, otherBlockPos) && canExist(state, level, otherBlockPos) && (otherBlock.`is`(NuclearTags.Blocks.GLOWING_MYCELIUM_SPREADABLE))) {
                        level.setBlockAndUpdate(otherBlockPos, ModBlocks.glowingMycelium.get().defaultBlockState())
                    }
                }
            }
        }

        // add new mushrooms
        if (level.isEmptyBlock(pos.above()) && random.nextInt(10) == 0 && ModBlocks.glowingMushroom.get().defaultBlockState().canSurvive(level, pos.above())) {
            var count = 0
            for (i in -5..4) {
                for (j in -5..5) {
                    for (k in -5..4) {
                        val block = level.getBlockState(pos.offset(i, j, k))
                        if (block.`is`(ModBlocks.glowingMushroom.get())) {
                            count++
                        }
                    }
                }
            }
            if (count == 0) level.setBlockAndUpdate(pos.above(), ModBlocks.glowingMushroom.get().defaultBlockState())
        }
    }

    override fun animateTick(state: BlockState, world: Level, pos: BlockPos, random: Random) {
        if (random.nextInt(5) == 0) {
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
