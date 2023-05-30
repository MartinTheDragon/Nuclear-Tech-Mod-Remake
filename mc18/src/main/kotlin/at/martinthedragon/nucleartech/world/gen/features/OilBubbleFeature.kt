package at.martinthedragon.nucleartech.world.gen.features

import at.martinthedragon.nucleartech.block.NTechBlocks
import at.martinthedragon.nucleartech.math.placeSpherical
import com.mojang.serialization.Codec
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration

class OilBubbleFeature(codec: Codec<NoneFeatureConfiguration>) : Feature<NoneFeatureConfiguration>(codec) {
    private val stoneTest = { state: BlockState -> state.`is`(BlockTags.STONE_ORE_REPLACEABLES) }
    private val deepslateTest = { state: BlockState -> state.`is`(BlockTags.DEEPSLATE_ORE_REPLACEABLES) }

    override fun place(context: FeaturePlaceContext<NoneFeatureConfiguration>): Boolean {
        val radius = 7 + context.random().nextInt(9)

        val oilDeposit = NTechBlocks.oilDeposit.get().defaultBlockState()
        val deepOilDeposit = NTechBlocks.deepslateOilDeposit.get().defaultBlockState()

        val worldGen = context.level()
        placeSpherical(context.origin(), radius) {
            when {
                worldGen.isStateAtPosition(it, stoneTest) -> setBlock(context.level(), it, oilDeposit)
                worldGen.isStateAtPosition(it, deepslateTest) -> setBlock(context.level(), it, deepOilDeposit)
            }
        }

        return true
    }
}
