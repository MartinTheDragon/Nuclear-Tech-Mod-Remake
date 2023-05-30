package at.martinthedragon.nucleartech.world.gen.features.meteoriteplacers

import at.martinthedragon.nucleartech.math.placeOctahedral
import at.martinthedragon.nucleartech.world.gen.features.configurations.MeteoriteFeatureConfiguration
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.BlockPos
import net.minecraft.world.level.LevelSimulatedReader
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import java.util.*
import java.util.function.BiConsumer

class StarMeteoritePlacer(radius: Int) : MeteoritePlacer(radius) {
    override fun placeMeteorite(level: LevelSimulatedReader, meteoriteConsumer: BiConsumer<BlockPos, BlockState?>, random: Random, origin: BlockPos, blockStateProvider: BlockStateProvider, configuration: MeteoriteFeatureConfiguration) {
        placeOctahedral(origin, radius + .5F) {
            meteoriteConsumer.accept(it, blockStateProvider.getState(random, it))
        }

        val halfRadius = radius / 2
        for (x in -halfRadius..halfRadius) for (y in -halfRadius..halfRadius) for (z in -halfRadius..halfRadius) {
            val pos = origin.offset(x, y, z)
            meteoriteConsumer.accept(pos, blockStateProvider.getState(random, pos))
        }
    }

    override fun type() = MeteoritePlacerType.STAR_PLACER.get()

    companion object {
        val CODEC: Codec<StarMeteoritePlacer> = RecordCodecBuilder.create { builder ->
            meteoritePlacerParts(builder).apply(builder, ::StarMeteoritePlacer)
        }
    }
}
