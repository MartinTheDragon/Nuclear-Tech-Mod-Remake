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
        ModBlocks.uraniumOre, ModBlocks.thoriumOre, ModBlocks.titaniumOre,
        ModBlocks.sulfurOre, ModBlocks.niterOre, ModBlocks.copperOre,
        ModBlocks.tungstenOre, ModBlocks.aluminiumOre, ModBlocks.fluoriteOre,
        ModBlocks.berylliumOre, ModBlocks.leadOre, ModBlocks.ligniteOre,
        ModBlocks.asbestosOre, ModBlocks.australianOre, ModBlocks.weidite,
        ModBlocks.reiite, ModBlocks.brightblendeOre, ModBlocks.dellite,
        ModBlocks.dollarGreenMineral, ModBlocks.rareEarthOre
    )
    val netherOres = listOf(
        ModBlocks.netherUraniumOre, ModBlocks.netherPlutoniumOre,
        ModBlocks.netherTungstenOre, ModBlocks.netherSulfurOre,
        ModBlocks.netherPhosphorusOre
    )
    val endOres = listOf(
        ModBlocks.trixite
    )

    private val END_STONE = BlockMatchRuleTest(Blocks.END_STONE)

    fun generateOres(event: BiomeLoadingEvent) {
        when(event.category) {
            Biome.Category.NETHER -> for (ore in netherOres) {
                val oreGenConfig = Config.defaultNetherOreGenSettings.getValue(ore.get().registryName!!.path)
                event.generation.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                    Feature.ORE.configured(OreFeatureConfig(
                        OreFeatureConfig.FillerBlockType.NETHERRACK,
                        ore.get().defaultBlockState(),
                        oreGenConfig.sizeVein
                    )).decorated(Placement.RANGE.configured(
                        TopSolidRangeConfig(oreGenConfig.yStart, 0, oreGenConfig.yEnd - oreGenConfig.yStart)
                    )).squared().count(oreGenConfig.countVeins) // func_242731_b: set FeatureSpread vein count in one chunk
                )
            }
            Biome.Category.THEEND -> for (ore in endOres) {
                val oreGenConfig = Config.defaultEndOreGenSettings.getValue(ore.get().registryName!!.path)
                event.generation.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                    Feature.ORE.configured(OreFeatureConfig(
                        END_STONE,
                        ore.get().defaultBlockState(),
                        oreGenConfig.sizeVein
                    )).decorated(Placement.RANGE.configured(
                        TopSolidRangeConfig(oreGenConfig.yStart, 0, oreGenConfig.yEnd - oreGenConfig.yStart)
                    )).squared().count(oreGenConfig.countVeins)
                )
            }
            else -> for (ore in ores) {
                val oreGenConfig = Config.defaultOreGenSettings.getValue(ore.get().registryName!!.path)
                event.generation.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                    Feature.ORE.configured(OreFeatureConfig(
                        OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                        ore.get().defaultBlockState(),
                        oreGenConfig.sizeVein
                    )).decorated(Placement.RANGE.configured(
                        TopSolidRangeConfig(oreGenConfig.yStart, 0, oreGenConfig.yEnd - oreGenConfig.yStart)
                    )).squared().count(oreGenConfig.countVeins)
                )
            }
        }
    }

    data class OreGenerationSettings(val yStart: Int, val yEnd: Int, val countVeins: Int, val sizeVein: Int, val biomeBlackList: List<String> = emptyList())
}
