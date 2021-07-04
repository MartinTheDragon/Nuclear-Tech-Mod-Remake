package at.martinthedragon.nucleartech.worldgen.features

import at.martinthedragon.nucleartech.ModBlocks
import com.mojang.serialization.Codec
import net.minecraft.tags.BlockTags
import net.minecraft.util.math.BlockPos
import net.minecraft.world.ISeedReader
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.NoFeatureConfig
import java.util.*

class OilBubbleFeature(codec: Codec<NoFeatureConfig>) : Feature<NoFeatureConfig>(codec) {
    override fun place(seedReader: ISeedReader, generator: ChunkGenerator, random: Random, pos: BlockPos, config: NoFeatureConfig): Boolean {
        val radius = 7 + random.nextInt(9)
        val radiusSquared = radius * radius
        val radiusSquaredHalved = radiusSquared / 2

        for (xIteration in -radius until radius) {
            val x = xIteration + pos.x
            val xBorder = xIteration * xIteration
            for (yIteration in -radius until radius) {
                val y = yIteration + pos.y
                val yBorder = xBorder + yIteration * yIteration
                for (zIteration in -radius until radius) {
                    val z = zIteration + pos.z
                    val zBorder = yBorder + zIteration * zIteration
                    if (zBorder < radiusSquaredHalved) {
                        val oilOrePos = BlockPos(x, y, z)
                        if (seedReader.getBlockState(oilOrePos).block in BlockTags.BASE_STONE_OVERWORLD) {
                            setBlock(seedReader, oilOrePos, ModBlocks.oilDeposit.get().defaultBlockState())
                        }
                    }
                }
            }
        }

        return true
    }
}
