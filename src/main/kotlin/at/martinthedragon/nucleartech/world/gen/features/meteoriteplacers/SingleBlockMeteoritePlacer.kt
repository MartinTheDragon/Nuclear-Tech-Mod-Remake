package at.martinthedragon.nucleartech.world.gen.features.meteoriteplacers

import at.martinthedragon.nucleartech.world.gen.features.configurations.MeteoriteFeatureConfiguration
import net.minecraft.core.BlockPos
import net.minecraft.world.level.LevelSimulatedReader
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import java.util.*
import java.util.function.BiConsumer

object SingleBlockMeteoritePlacer : MeteoritePlacer(0) {
    override fun placeMeteorite(level: LevelSimulatedReader, meteoriteConsumer: BiConsumer<BlockPos, BlockState?>, random: Random, origin: BlockPos, blockStateProvider: BlockStateProvider, configuration: MeteoriteFeatureConfiguration) {
        meteoriteConsumer.accept(origin, blockStateProvider.getState(random, origin))
    }

    override fun type() = MeteoritePlacerType.SINGLE_BLOCK_PLACER.get()
}
