package at.martinthedragon.nucleartech.world.gen.features.configurations

import at.martinthedragon.nucleartech.world.gen.features.meteoriteplacers.MeteoritePlacer
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider

class MeteoriteFeatureConfiguration(
    val placers: List<MeteoritePlacerConfiguration>
) : FeatureConfiguration {
    data class MeteoritePlacerConfiguration(
        val providers: List<BlockStateProvider>,
        val placer: MeteoritePlacer,
    ) {
        companion object {
            val CODEC: Codec<MeteoritePlacerConfiguration> = RecordCodecBuilder.create { builder ->
                builder.group(
                    Codec.list(BlockStateProvider.CODEC).fieldOf("providers").forGetter(MeteoritePlacerConfiguration::providers),
                    MeteoritePlacer.CODEC.fieldOf("placer").forGetter(MeteoritePlacerConfiguration::placer)
                ).apply(builder, ::MeteoritePlacerConfiguration)
            }
        }
    }

    companion object {
        val CODEC: Codec<MeteoriteFeatureConfiguration> = RecordCodecBuilder.create { builder ->
            builder.group(
                Codec.list(MeteoritePlacerConfiguration.CODEC).fieldOf("placers").forGetter(MeteoriteFeatureConfiguration::placers)
            ).apply(builder, ::MeteoriteFeatureConfiguration)
        }

        fun of(placers: List<Pair<List<BlockStateProvider>, MeteoritePlacer>>) =
            MeteoriteFeatureConfiguration(placers.map { (provider, placer) -> MeteoritePlacerConfiguration(provider, placer) })
    }
}
