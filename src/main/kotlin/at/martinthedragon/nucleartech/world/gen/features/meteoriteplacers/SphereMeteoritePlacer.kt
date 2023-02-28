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
import kotlin.math.abs

class SphereMeteoritePlacer(radius: Int) : MeteoritePlacer(radius) {
    override fun placeMeteorite(level: LevelSimulatedReader, meteoriteConsumer: BiConsumer<BlockPos, BlockState?>, random: Random, origin: BlockPos, blockStateProvider: BlockStateProvider, configuration: MeteoriteFeatureConfiguration) {
        val octahedronRadius = radius + 2
        placeOctahedral(origin, octahedronRadius + .5F) {
            val offset = it.subtract(origin)
            // a meteorite "sphere" is just an octahedron with its edges shaved off
            if (!(abs(offset.x) + abs(offset.y) + abs(offset.z) == octahedronRadius && (offset.x == 0 || offset.y == 0 || offset.z == 0)))
                meteoriteConsumer.accept(it, blockStateProvider.getState(random, it))
        }
        val extraDeletionRange = radius + 1
        meteoriteConsumer.accept(origin.above(extraDeletionRange), null)
        meteoriteConsumer.accept(origin.below(extraDeletionRange), null)
        meteoriteConsumer.accept(origin.north(extraDeletionRange), null)
        meteoriteConsumer.accept(origin.south(extraDeletionRange), null)
        meteoriteConsumer.accept(origin.east(extraDeletionRange), null)
        meteoriteConsumer.accept(origin.west(extraDeletionRange), null)
    }

    override fun type() = MeteoritePlacerType.SPHERE_PLACER.get()

    companion object {
        val CODEC: Codec<SphereMeteoritePlacer> = RecordCodecBuilder.create { builder ->
            meteoritePlacerParts(builder).apply(builder, ::SphereMeteoritePlacer)
        }
    }
}
