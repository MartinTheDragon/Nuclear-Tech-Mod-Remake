package at.martinthedragon.nucleartech.world.gen.features

import at.martinthedragon.nucleartech.world.gen.features.configurations.MeteoriteFeatureConfiguration
import com.mojang.serialization.Codec
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
import kotlin.random.asKotlinRandom

class MeteoriteFeature(codec: Codec<MeteoriteFeatureConfiguration>) : Feature<MeteoriteFeatureConfiguration>(codec) {
    override fun place(context: FeaturePlaceContext<MeteoriteFeatureConfiguration>): Boolean {
        val level = context.level()
        val random = context.random()
        val kotlinRandom = random.asKotlinRandom()
        val origin = context.origin()
        val config = context.config()

        if (config.placers.isEmpty()) return false

        val sortedPlacers = config.placers.sortedByDescending { it.placer.radius }
        val consumedPlacers = sortedPlacers zip List(sortedPlacers.size) { mutableMapOf<BlockPos, BlockState?>() }

        val maxRadius = consumedPlacers.maxOf { it.first.placer.radius }
        if (origin.y - maxRadius < level.minBuildHeight + 1 || origin.y + maxRadius > level.maxBuildHeight - 1) return false

        for ((configuredPlacer, stateMap) in consumedPlacers) {
            val (providers, placer) = configuredPlacer
            if (providers.isEmpty()) continue
            placer.placeMeteorite(level, { pos, state -> stateMap[pos] = state }, random, origin, providers.random(kotlinRandom), config)
        }

        consumedPlacers
            .map { it.second }
            .fold(mutableMapOf<BlockPos, BlockState?>()) { acc, map -> acc.putAll(map.filter { it.value != null }); acc }
            .forEach { (pos, state) -> if (state != null) {
                val target = level.getBlockState(pos)
                if (target.getExplosionResistance(level, pos, null) < 10_000)
                    level.setBlock(pos, state, Block.UPDATE_ALL)
            }}

        return true
    }
}
