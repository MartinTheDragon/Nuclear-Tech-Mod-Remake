package at.martinthedragon.nucleartech.world.gen.features.meteoriteplacers

import at.martinthedragon.nucleartech.RegistriesAndLifecycle
import at.martinthedragon.nucleartech.world.gen.features.configurations.MeteoriteFeatureConfiguration
import com.mojang.datafixers.Products
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.BlockPos
import net.minecraft.world.level.LevelSimulatedReader
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import java.util.*
import java.util.function.BiConsumer

abstract class MeteoritePlacer(val radius: Int) {
    abstract fun placeMeteorite(level: LevelSimulatedReader, meteoriteConsumer: BiConsumer<BlockPos, BlockState?>, random: Random, origin: BlockPos, blockStateProvider: BlockStateProvider, configuration: MeteoriteFeatureConfiguration)

    protected abstract fun type(): MeteoritePlacerType<*>

    companion object {
        val CODEC: Codec<MeteoritePlacer> = RegistriesAndLifecycle.METEORITE_PLACER_REGISTRY.get().codec.dispatch(MeteoritePlacer::type, MeteoritePlacerType<*>::codec)

        @JvmStatic
        protected fun <P : MeteoritePlacer> meteoritePlacerParts(instance: RecordCodecBuilder.Instance<P>): Products.P1<RecordCodecBuilder.Mu<P>, Int> = instance.group(
            Codec.intRange(1, 20).fieldOf("radius").forGetter(MeteoritePlacer::radius)
        )
    }
}
