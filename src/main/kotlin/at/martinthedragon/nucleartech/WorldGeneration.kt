package at.martinthedragon.nucleartech

import net.minecraft.block.Blocks
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.WorldGenRegistries
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.BiomeGenerationSettings
import net.minecraft.world.biome.Biomes
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.IFeatureConfig
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest
import net.minecraft.world.gen.placement.Placement
import net.minecraft.world.gen.placement.TopSolidRangeConfig
import net.minecraftforge.event.world.BiomeLoadingEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraft.world.gen.feature.Features as VanillaFeatures

@Suppress("unused")
@Mod.EventBusSubscriber(modid = NuclearTech.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object WorldGeneration {
    object Features {
        val ORE_URANIUM = register("ore_uranium", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.uraniumOre.get().defaultBlockState(), 5)).range(25).squared().count(6))
        val ORE_THORIUM = register("ore_thorium", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.thoriumOre.get().defaultBlockState(), 5)).range(30).squared().count(7))
        val ORE_TITANIUM = register("ore_titanium", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.titaniumOre.get().defaultBlockState(), 6)).range(35).squared().count(8))
        val ORE_SULFUR = register("ore_sulfur", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.sulfurOre.get().defaultBlockState(), 8)).range(35).squared().count(5))
        val ORE_NITER = register("ore_niter", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.niterOre.get().defaultBlockState(), 6)).range(35).squared().count(6))
        val ORE_COPPER = register("ore_copper", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.copperOre.get().defaultBlockState(), 6)).range(50).squared().count(12))
        val ORE_TUNGSTEN = register("ore_tungsten", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.tungstenOre.get().defaultBlockState(), 8)).range(35).squared().count(10))
        val ORE_ALUMINIUM = register("ore_aluminium", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.aluminiumOre.get().defaultBlockState(), 6)).range(45).squared().count(7))
        val ORE_FLUORITE = register("ore_fluorite", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.fluoriteOre.get().defaultBlockState(), 4)).range(40).squared().count(6))
        val ORE_BERYLLIUM = register("ore_beryllium", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.berylliumOre.get().defaultBlockState(), 4)).range(35).squared().count(6))
        val ORE_LEAD = register("ore_lead", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.leadOre.get().defaultBlockState(), 9)).range(35).squared().count(6))
        val ORE_LIGNITE = register("ore_lignite", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.ligniteOre.get().defaultBlockState(), 24)).decorated(Placement.RANGE.configured(TopSolidRangeConfig(35, 35, 60))).squared().count(2))
        val ORE_AUSTRALIUM = register("ore_australium", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.australianOre.get().defaultBlockState(), 4)).decorated(Placement.RANGE.configured(TopSolidRangeConfig(15, 15, 30))).squared().count(2))
        val ORE_WEIDANIUM = register("ore_weidanium", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.weidite.get().defaultBlockState(), 4)).range(25).squared().count(2))
        val ORE_REIIUM = register("ore_reiium", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.reiite.get().defaultBlockState(), 4)).range(35).squared().count(2))
        val ORE_UNOBTAINIUM = register("ore_unobtainium", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.brightblendeOre.get().defaultBlockState(), 4)).range(128).squared().count(2))
        val ORE_DAFFERGON = register("ore_daffergon", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.dellite.get().defaultBlockState(), 4)).range(10).squared().count(2))
        val ORE_VERTICIUM = register("ore_verticium", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.dollarGreenMineral.get().defaultBlockState(), 4)).decorated(Placement.RANGE.configured(TopSolidRangeConfig(25, 25, 50))).squared().count(2))
        val ORE_RARE_EARTH = register("ore_rare_earth", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.rareEarthOre.get().defaultBlockState(), 5)).range(25).squared().count(6))
        val ORE_URANIUM_NETHER = register("ore_uranium_nether", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, ModBlocks.netherUraniumOre.get().defaultBlockState(), 6)).decorated(VanillaFeatures.Placements.RANGE_10_20_ROOFED).squared().count(8))
        val ORE_PLUTONIUM_NETHER = register("ore_plutonium_nether", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, ModBlocks.netherPlutoniumOre.get().defaultBlockState(), 4)).decorated(VanillaFeatures.Placements.RANGE_10_20_ROOFED).squared().count(6))
        val ORE_TUNGSTEN_NETHER = register("ore_tungsten_nether", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, ModBlocks.netherTungstenOre.get().defaultBlockState(), 10)).decorated(VanillaFeatures.Placements.RANGE_10_20_ROOFED).squared().count(10))
        val ORE_SULFUR_NETHER = register("ore_sulfur_nether", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, ModBlocks.netherSulfurOre.get().defaultBlockState(), 12)).decorated(VanillaFeatures.Placements.RANGE_10_20_ROOFED).squared().count(26))
        val ORE_PHOSPHORUS_NETHER = register("ore_phosphorus_nether", Feature.ORE.configured(OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, ModBlocks.netherPhosphorusOre.get().defaultBlockState(), 3)).decorated(VanillaFeatures.Placements.RANGE_10_20_ROOFED).squared().count(24))
        val ORE_TRIXITE_END = register("ore_trixite_end", Feature.ORE.configured(OreFeatureConfig(FillerBlockTypes.END_STONE, ModBlocks.trixite.get().defaultBlockState(), 6)).decorated(VanillaFeatures.Placements.RANGE_10_20_ROOFED).squared().count(8))

        private fun <FC : IFeatureConfig?> register(name: String, configuredFeature: ConfiguredFeature<FC, *>): ConfiguredFeature<FC, *> =
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "${NuclearTech.MODID}:$name", configuredFeature)
    }

    object FillerBlockTypes {
        val END_STONE = BlockMatchRuleTest(Blocks.END_STONE)
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    @JvmStatic
    fun addBiomeFeaturesEvent(event: BiomeLoadingEvent) {
        addOreFeatures(event)
    }

    private fun addOreFeatures(event: BiomeLoadingEvent) {
        when (event.category) {
            Biome.Category.NETHER -> addNetherOres(event.generation)
            Biome.Category.THEEND -> addEndOres(event.generation)
            else -> {
                if (event.name == Biomes.THE_END.location()) return

                addDefaultOres(event.generation)

                if (event.category != Biome.Category.EXTREME_HILLS &&
                    event.category != Biome.Category.OCEAN &&
                    event.category != Biome.Category.DESERT
                ) {
                    addLignite(event.generation)
                }
            }
        }
    }

    private fun addDefaultOres(builder: BiomeGenerationSettings.Builder) {
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_URANIUM)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_THORIUM)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_TITANIUM)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_SULFUR)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_NITER)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_COPPER)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_TUNGSTEN)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_ALUMINIUM)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_FLUORITE)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_BERYLLIUM)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_LEAD)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_AUSTRALIUM)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_WEIDANIUM)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_REIIUM)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_UNOBTAINIUM)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_DAFFERGON)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_VERTICIUM)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_RARE_EARTH)
    }

    private fun addLignite(builder: BiomeGenerationSettings.Builder) {
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_LIGNITE)
    }

    private fun addNetherOres(builder: BiomeGenerationSettings.Builder) {
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_URANIUM_NETHER)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_PLUTONIUM_NETHER)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_TUNGSTEN_NETHER)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_SULFUR_NETHER)
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_PHOSPHORUS_NETHER)
    }

    private fun addEndOres(builder: BiomeGenerationSettings.Builder) {
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_TRIXITE_END)
    }
}
