package at.martinthedragon.ntm

import net.minecraft.block.Blocks
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest
import net.minecraft.world.gen.placement.Placement
import net.minecraft.world.gen.placement.TopSolidRangeConfig
import net.minecraftforge.event.world.BiomeLoadingEvent


object WorldGeneration {
    val netherBiomes = listOf("minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas")
    val ores = listOf(
        B.uraniumOre, B.thoriumOre, B.titaniumOre,
        B.sulfurOre, B.niterOre, B.copperOre,
        B.tungstenOre, B.aluminiumOre, B.fluoriteOre,
        B.berylliumOre, B.leadOre, B.ligniteOre,
        B.asbestosOre, B.australianOre, B.weidite,
        B.reiite, B.brightblendeOre, B.dellite,
        B.dollarGreenMineral, B.rareEarthOre
    )
    val netherOres = listOf(
        B.netherUraniumOre, B.netherPlutoniumOre,
        B.netherTungstenOre, B.netherSulfurOre,
        B.netherPhosphorusOre
    )
    val endOres = listOf(
        B.trixite
    )

    private val END_STONE = BlockMatchRuleTest(Blocks.END_STONE)

    fun generateOres(event: BiomeLoadingEvent) {
        when(event.category) {
            Biome.Category.NETHER -> for (ore in netherOres) {
                val oreGenConfig = Config.defaultNetherOreGenSettings.getValue(ore.registryName!!.path)
                event.generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                    Feature.ORE.withConfiguration(OreFeatureConfig(
                        OreFeatureConfig.FillerBlockType.NETHERRACK,
                        ore.defaultState,
                        oreGenConfig.sizeVein
                    )).withPlacement(Placement.RANGE.configure(
                        TopSolidRangeConfig(oreGenConfig.yStart, 0, oreGenConfig.yEnd - oreGenConfig.yStart)
                    )).square().func_242731_b(oreGenConfig.countVeins) // func_242731_b: set FeatureSpread vein count in one chunk
                )
            }
            Biome.Category.THEEND -> for (ore in endOres) {
                val oreGenConfig = Config.defaultEndOreGenSettings.getValue(ore.registryName!!.path)
                event.generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                    Feature.ORE.withConfiguration(OreFeatureConfig(
                        END_STONE,
                        ore.defaultState,
                        oreGenConfig.sizeVein
                    )).withPlacement(Placement.RANGE.configure(
                        TopSolidRangeConfig(oreGenConfig.yStart, 0, oreGenConfig.yEnd - oreGenConfig.yStart)
                    )).square().func_242731_b(oreGenConfig.countVeins)
                )
            }
            else -> for (ore in ores) {
                val oreGenConfig = Config.defaultOreGenSettings.getValue(ore.registryName!!.path)
                event.generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                    Feature.ORE.withConfiguration(OreFeatureConfig(
                        OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
                        ore.defaultState,
                        oreGenConfig.sizeVein
                    )).withPlacement(Placement.RANGE.configure(
                        TopSolidRangeConfig(oreGenConfig.yStart, 0, oreGenConfig.yEnd - oreGenConfig.yStart)
                    )).square().func_242731_b(oreGenConfig.countVeins)
                )
            }
        }
    }

    data class OreGenerationSettings(val yStart: Int, val yEnd: Int, val countVeins: Int, val sizeVein: Int, val biomeBlackList: List<String> = emptyList())
}
