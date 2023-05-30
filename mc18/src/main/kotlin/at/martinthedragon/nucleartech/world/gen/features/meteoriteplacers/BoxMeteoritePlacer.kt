package at.martinthedragon.nucleartech.world.gen.features.meteoriteplacers

import at.martinthedragon.nucleartech.world.gen.features.configurations.MeteoriteFeatureConfiguration
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.BlockPos
import net.minecraft.world.level.LevelSimulatedReader
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import java.util.*
import java.util.function.BiConsumer

class BoxMeteoritePlacer(radius: Int) : MeteoritePlacer(radius) {
    override fun placeMeteorite(level: LevelSimulatedReader, meteoriteConsumer: BiConsumer<BlockPos, BlockState?>, random: Random, origin: BlockPos, blockStateProvider: BlockStateProvider, configuration: MeteoriteFeatureConfiguration) {
        for (a in -radius..radius) for (b in -radius..radius) for (c in -radius..radius) {
            val pos = origin.offset(a, b, c)
            meteoriteConsumer.accept(pos, blockStateProvider.getState(random, pos))
        }
    }

    override fun type() = MeteoritePlacerType.BOX_PLACER.get()

    companion object {
        val CODEC: Codec<BoxMeteoritePlacer> = RecordCodecBuilder.create { builder ->
            meteoritePlacerParts(builder).apply(builder, ::BoxMeteoritePlacer)
        }
    }
}
