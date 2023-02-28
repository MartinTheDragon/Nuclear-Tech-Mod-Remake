package at.martinthedragon.nucleartech.world.gen

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.RegistriesAndLifecycle.FEATURES
import at.martinthedragon.nucleartech.block.NTechBlocks
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.registerK
import at.martinthedragon.nucleartech.world.gen.features.HugeGlowingMushroomFeature
import at.martinthedragon.nucleartech.world.gen.features.MeteoriteFeature
import at.martinthedragon.nucleartech.world.gen.features.OilBubbleFeature
import at.martinthedragon.nucleartech.world.gen.features.configurations.MeteoriteFeatureConfiguration
import at.martinthedragon.nucleartech.world.gen.features.meteoriteplacers.*
import net.minecraft.core.Holder
import net.minecraft.data.BuiltinRegistries
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.util.random.SimpleWeightedRandomList
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.Biomes
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.HugeMushroomBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.VerticalAnchor
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider
import net.minecraft.world.level.levelgen.placement.*
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder
import net.minecraftforge.event.world.BiomeLoadingEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraft.data.worldgen.features.OreFeatures as VanillaOreFeatures

@Suppress("unused")
object WorldGen {
    @Mod.EventBusSubscriber(modid = NuclearTech.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    object BiomeFeatures {
        @SubscribeEvent(priority = EventPriority.HIGH)
        @JvmStatic
        fun addBiomeFeatures(event: BiomeLoadingEvent) {
            val name = event.name
            val builder = event.generation
            when (event.category) {
                Biome.BiomeCategory.NETHER -> {
                    addNetherOres(builder)
                    if (name == Biomes.BASALT_DELTAS.location()) addBasaltDeltasOres(builder)
                }
                Biome.BiomeCategory.THEEND -> {
                    addEndOres(builder)
                }
                else -> {
                    addDefaultOres(builder)
                    if (name == Biomes.BADLANDS.location()) addBadlandsOres(builder)
                    if (name == Biomes.DRIPSTONE_CAVES.location()) addDripstoneCavesOres(builder)
                }
            }

            // ensure load
            OreFeatures
            OrePlacements
            TreeFeatures
            MeteoriteFeatures
        }

        private fun addDefaultOres(builder: BiomeGenerationSettingsBuilder) {
            builder
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_URANIUM)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_URANIUM_LARGE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_URANIUM_BURIED)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_THORIUM)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_THORIUM_MIDDLE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TITANIUM)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_SULFUR_LOWER)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_SULFUR_SMALL)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_NITER)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUNGSTEN_MIDDLE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUNGSTEN_BURIED)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ALUMINIUM)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ALUMINIUM_MIDDLE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_FLUORITE_LOWER)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_BERYLLIUM)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_BERYLLIUM_LOWER)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_LEAD)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_LIGNITE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ASBESTOS)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ASBESTOS_MIDDLE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_RARE_EARTH)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_RARE_EARTH_LARGE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_RARE_EARTH_BURIED)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COBALT)

                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.OIL_BUBBLE)
        }

        private fun addBadlandsOres(builder: BiomeGenerationSettingsBuilder) {
            builder
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_NITER_EXTRA)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COBALT_EXTRA)
        }

        private fun addDripstoneCavesOres(builder: BiomeGenerationSettingsBuilder) {
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_FLUORITE_BURIED)
        }

        private fun addNetherOres(builder: BiomeGenerationSettingsBuilder) {
            builder
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_URANIUM_NETHER)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_PLUTONIUM_NETHER)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUNGSTEN_NETHER)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_SULFUR_NETHER)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_PHOSPHORUS_NETHER)
        }

        private fun addBasaltDeltasOres(builder: BiomeGenerationSettingsBuilder) {
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUNGSTEN_DELTAS)
        }

        private fun addEndOres(builder: BiomeGenerationSettingsBuilder) {
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TRIXITE_END)
        }
    }

    object Features {
        val OIL_BUBBLE = FEATURES.registerK("oil_bubble") { OilBubbleFeature(NoneFeatureConfiguration.CODEC) }
        val HUGE_GLOWING_MUSHROOM = FEATURES.registerK("huge_glowing_mushroom") { HugeGlowingMushroomFeature(HugeMushroomFeatureConfiguration.CODEC) }
        val METEORITE = FEATURES.registerK("meteorite") { MeteoriteFeature(MeteoriteFeatureConfiguration.CODEC) }
    }

    private object OreFeatures {
        val NATURAL_STONE: RuleTest = VanillaOreFeatures.NATURAL_STONE
        val STONE_ORE_REPLACEABLES: RuleTest = VanillaOreFeatures.STONE_ORE_REPLACEABLES
        val DEEPSLATE_ORE_REPLACEABLES: RuleTest = VanillaOreFeatures.DEEPSLATE_ORE_REPLACEABLES
        val NETHERRACK: RuleTest = VanillaOreFeatures.NETHERRACK
        val NETHER_ORE_REPLACEABLES: RuleTest = VanillaOreFeatures.NETHER_ORE_REPLACEABLES
        val END_STONE: RuleTest = BlockMatchTest(Blocks.END_STONE)
        val ORE_URANIUM_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.uraniumOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateUraniumOre.get().defaultBlockState()))
        val ORE_THORIUM_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.thoriumOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateThoriumOre.get().defaultBlockState()))
        val ORE_TITANIUM_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.titaniumOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateTitaniumOre.get().defaultBlockState()))
        val ORE_SULFUR_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.sulfurOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateSulfurOre.get().defaultBlockState()))
        val ORE_NITER_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.niterOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateNiterOre.get().defaultBlockState()))
        val ORE_TUNGSTEN_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.tungstenOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateTungstenOre.get().defaultBlockState()))
        val ORE_ALUMINIUM_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.aluminiumOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateAluminiumOre.get().defaultBlockState()))
        val ORE_FLUORITE_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.fluoriteOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateFluoriteOre.get().defaultBlockState()))
        val ORE_BERYLLIUM_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.berylliumOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateBerylliumOre.get().defaultBlockState()))
        val ORE_LEAD_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.leadOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateLeadOre.get().defaultBlockState()))
        val ORE_ASBESTOS_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.asbestosOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateAsbestosOre.get().defaultBlockState()))
        val ORE_RARE_EARTH_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.rareEarthOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateRareEarthOre.get().defaultBlockState()))
        val ORE_COBALT_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, NTechBlocks.cobaltOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, NTechBlocks.deepslateCobaltOre.get().defaultBlockState()))
        val ORE_URANIUM_SMALL = register("ore_uranium_small", Feature.ORE, OreConfiguration(ORE_URANIUM_TARGET_LIST, 3, .5F))
        val ORE_URANIUM_LARGE = register("ore_uranium_large", Feature.ORE, OreConfiguration(ORE_URANIUM_TARGET_LIST, 12, .7F))
        val ORE_URANIUM_BURIED = register("ore_uranium_buried", Feature.ORE, OreConfiguration(ORE_URANIUM_TARGET_LIST, 6, 1F))
        val ORE_THORIUM = register("ore_thorium", Feature.ORE, OreConfiguration(ORE_THORIUM_TARGET_LIST, 5))
        val ORE_THORIUM_SMALL = register("ore_thorium_small", Feature.ORE, OreConfiguration(ORE_THORIUM_TARGET_LIST, 3))
        val ORE_TITANIUM = register("ore_titanium", Feature.ORE, OreConfiguration(ORE_TITANIUM_TARGET_LIST, 6, .4F))
        val ORE_SULFUR = register("ore_sulfur", Feature.ORE, OreConfiguration(ORE_SULFUR_TARGET_LIST, 10, .2F))
        val ORE_SULFUR_SMALL = register("ore_sulfur_small", Feature.ORE, OreConfiguration(ORE_SULFUR_TARGET_LIST, 4))
        val ORE_NITER = register("ore_niter", Feature.ORE, OreConfiguration(ORE_NITER_TARGET_LIST, 8, .3F))
        val ORE_NITER_SMALL = register("ore_niter_small", Feature.ORE, OreConfiguration(ORE_NITER_TARGET_LIST, 3))
        val ORE_TUNGSTEN = register("ore_tungsten", Feature.ORE, OreConfiguration(ORE_TUNGSTEN_TARGET_LIST, 3))
        val ORE_TUNGSTEN_BURIED = register("ore_tungsten_buried", Feature.ORE, OreConfiguration(ORE_TUNGSTEN_TARGET_LIST, 9, 1F))
        val ORE_ALUMINIUM = register("ore_aluminium", Feature.ORE, OreConfiguration(ORE_ALUMINIUM_TARGET_LIST, 5))
        val ORE_ALUMINIUM_SMALL = register("ore_aluminium_small", Feature.ORE, OreConfiguration(ORE_ALUMINIUM_TARGET_LIST, 3))
        val ORE_FLUORITE = register("ore_fluorite", Feature.ORE, OreConfiguration(ORE_FLUORITE_TARGET_LIST, 4))
        val ORE_FLUORITE_BURIED = register("ore_fluorite_buried", Feature.ORE, OreConfiguration(ORE_FLUORITE_TARGET_LIST, 15, 1F))
        val ORE_BERYLLIUM = register("ore_beryllium", Feature.ORE, OreConfiguration(ORE_BERYLLIUM_TARGET_LIST, 4))
        val ORE_BERYLLIUM_SMALL = register("ore_beryllium_small", Feature.ORE, OreConfiguration(ORE_BERYLLIUM_TARGET_LIST, 2))
        val ORE_LEAD = register("ore_lead", Feature.ORE, OreConfiguration(ORE_LEAD_TARGET_LIST, 10, .6F))
        val ORE_LIGNITE = register("ore_lignite", Feature.ORE, OreConfiguration(STONE_ORE_REPLACEABLES, NTechBlocks.ligniteOre.get().defaultBlockState(), 30, .2F))
        val ORE_ASBESTOS = register("ore_asbestos", Feature.ORE, OreConfiguration(ORE_ASBESTOS_TARGET_LIST, 10, 1F))
        val ORE_RARE_EARTH_SMALL = register("ore_rare_earth_small", Feature.ORE, OreConfiguration(ORE_RARE_EARTH_TARGET_LIST, 1))
        val ORE_RARE_EARTH_LARGE = register("ore_rare_earth_large", Feature.ORE, OreConfiguration(ORE_RARE_EARTH_TARGET_LIST, 15))
        val ORE_RARE_EARTH_BURIED = register("ore_rare_earth_buried", Feature.ORE, OreConfiguration(ORE_RARE_EARTH_TARGET_LIST, 5, 1F))
        val ORE_COBALT_SMALL = register("ore_cobalt_small", Feature.ORE, OreConfiguration(ORE_COBALT_TARGET_LIST, 3))
        val ORE_COBALT_LARGE = register("ore_cobalt_large", Feature.ORE, OreConfiguration(ORE_COBALT_TARGET_LIST, 15, .25F))
        val ORE_NETHER_URANIUM = register("ore_nether_uranium", Feature.ORE, OreConfiguration(NETHERRACK, NTechBlocks.netherUraniumOre.get().defaultBlockState(), 6))
        val ORE_NETHER_PLUTONIUM = register("ore_nether_plutonium", Feature.ORE, OreConfiguration(NETHERRACK, NTechBlocks.netherPlutoniumOre.get().defaultBlockState(), 4))
        val ORE_NETHER_TUNGSTEN = register("ore_nether_tungsten", Feature.ORE, OreConfiguration(NETHERRACK, NTechBlocks.netherTungstenOre.get().defaultBlockState(), 10))
        val ORE_NETHER_SULFUR = register("ore_nether_sulfur", Feature.ORE, OreConfiguration(NETHERRACK, NTechBlocks.netherSulfurOre.get().defaultBlockState(), 18))
        val ORE_NETHER_PHOSPHORUS = register("ore_nether_phosphorus", Feature.ORE, OreConfiguration(NETHERRACK, NTechBlocks.netherPhosphorusOre.get().defaultBlockState(), 5))
        val ORE_END_TRIXITE = register("ore_end_trixite", Feature.ORE, OreConfiguration(END_STONE, NTechBlocks.trixite.get().defaultBlockState(), 4, .1F))

        val OIL_BUBBLE = register("oil_bubble", Features.OIL_BUBBLE.get(), FeatureConfiguration.NONE)
    }

    private object OrePlacements {
        val ORE_URANIUM = register("ore_uranium", OreFeatures.ORE_URANIUM_SMALL, commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(25))))
        val ORE_URANIUM_LARGE = register("ore_uranium_large", OreFeatures.ORE_URANIUM_LARGE, rareOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-40), VerticalAnchor.aboveBottom(80))))
        val ORE_URANIUM_BURIED = register("ore_uranium_buried", OreFeatures.ORE_URANIUM_BURIED, commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-40), VerticalAnchor.aboveBottom(80))))
        val ORE_THORIUM = register("ore_thorium_lower", OreFeatures.ORE_THORIUM, commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0))))
        val ORE_THORIUM_MIDDLE = register("ore_thorium_middle", OreFeatures.ORE_THORIUM_SMALL, commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-30), VerticalAnchor.absolute(20))))
        val ORE_TITANIUM = register("ore_titanium", OreFeatures.ORE_TITANIUM, commonOrePlacement(14, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-100), VerticalAnchor.aboveBottom(100))))
        val ORE_SULFUR_LOWER = register("ore_sulfur_lower", OreFeatures.ORE_SULFUR, commonOrePlacement(12, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-40), VerticalAnchor.aboveBottom(40))))
        val ORE_SULFUR_SMALL = register("ore_sulfur_small", OreFeatures.ORE_SULFUR_SMALL, commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(50))))
        val ORE_NITER = register("ore_niter", OreFeatures.ORE_NITER_SMALL, commonOrePlacement(30, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(80))))
        val ORE_NITER_EXTRA = register("ore_niter_extra", OreFeatures.ORE_NITER, commonOrePlacement(50, HeightRangePlacement.triangle(VerticalAnchor.absolute(30), VerticalAnchor.absolute(128))))
        val ORE_TUNGSTEN_MIDDLE = register("ore_tungsten_middle", OreFeatures.ORE_TUNGSTEN, commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.absolute(-20), VerticalAnchor.absolute(40))))
        val ORE_TUNGSTEN_BURIED = register("ore_tungsten_buried", OreFeatures.ORE_TUNGSTEN_BURIED, commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.absolute(0))))
        val ORE_ALUMINIUM = register("ore_aluminium", OreFeatures.ORE_ALUMINIUM_SMALL, commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(40))))
        val ORE_ALUMINIUM_MIDDLE = register("ore_aluminium_middle", OreFeatures.ORE_ALUMINIUM, commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-15), VerticalAnchor.absolute(30))))
        val ORE_FLUORITE_LOWER = register("ore_fluorite_lower", OreFeatures.ORE_FLUORITE, commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(10))))
        val ORE_FLUORITE_BURIED = register("ore_fluorite_buried", OreFeatures.ORE_FLUORITE_BURIED, commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-30), VerticalAnchor.absolute(50))))
        val ORE_BERYLLIUM = register("ore_beryllium", OreFeatures.ORE_BERYLLIUM_SMALL, commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(30))))
        val ORE_BERYLLIUM_LOWER = register("ore_beryllium_lower", OreFeatures.ORE_BERYLLIUM, commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-40), VerticalAnchor.aboveBottom(40))))
        val ORE_LEAD = register("ore_lead", OreFeatures.ORE_LEAD, commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.absolute(30))))
        val ORE_LIGNITE = register("ore_lignite", OreFeatures.ORE_LIGNITE, commonOrePlacement(1, HeightRangePlacement.triangle(VerticalAnchor.absolute(30), VerticalAnchor.absolute(128))))
        val ORE_ASBESTOS = register("ore_asbestos", OreFeatures.ORE_ASBESTOS, commonOrePlacement(3, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(20), VerticalAnchor.absolute(40))))
        val ORE_ASBESTOS_MIDDLE = register("ore_asbestos_middle", OreFeatures.ORE_ASBESTOS, commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.top())))
        val ORE_RARE_EARTH = register("ore_rare_earth", OreFeatures.ORE_RARE_EARTH_SMALL, commonOrePlacement(100, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top())))
        val ORE_RARE_EARTH_LARGE = register("ore_rare_earth_large", OreFeatures.ORE_RARE_EARTH_LARGE, rareOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(-30), VerticalAnchor.absolute(50))))
        val ORE_RARE_EARTH_BURIED = register("ore_rare_earth_buried", OreFeatures.ORE_RARE_EARTH_BURIED, commonOrePlacement(15, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-100), VerticalAnchor.aboveBottom(100))))
        val ORE_COBALT = register("ore_cobalt", OreFeatures.ORE_COBALT_SMALL, commonOrePlacement(5, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(15))))
        val ORE_COBALT_EXTRA = register("ore_cobalt_extra", OreFeatures.ORE_COBALT_LARGE, rareOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.absolute(70))))
        val ORE_URANIUM_NETHER = register("ore_uranium_nether", OreFeatures.ORE_NETHER_URANIUM, commonOrePlacement(8, PlacementUtils.RANGE_10_10))
        val ORE_PLUTONIUM_NETHER = register("ore_plutonium_nether", OreFeatures.ORE_NETHER_PLUTONIUM, commonOrePlacement(6, PlacementUtils.RANGE_10_10))
        val ORE_TUNGSTEN_NETHER = register("ore_tungsten_nether", OreFeatures.ORE_NETHER_TUNGSTEN, commonOrePlacement(10, PlacementUtils.RANGE_10_10))
        val ORE_TUNGSTEN_DELTAS = register("ore_tungsten_deltas", OreFeatures.ORE_NETHER_TUNGSTEN, commonOrePlacement(10, PlacementUtils.RANGE_10_10))
        val ORE_SULFUR_NETHER = register("ore_sulfur_nether", OreFeatures.ORE_NETHER_SULFUR, commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(10), VerticalAnchor.aboveBottom(74))))
        val ORE_PHOSPHORUS_NETHER = register("ore_phosphorus_nether", OreFeatures.ORE_NETHER_PHOSPHORUS, commonOrePlacement(16, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(54), VerticalAnchor.belowTop(10))))
        val ORE_TRIXITE_END = register("ore_trixite_end", OreFeatures.ORE_END_TRIXITE, commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(16), VerticalAnchor.absolute(80))))

        val OIL_BUBBLE = register("oil_bubble", OreFeatures.OIL_BUBBLE, rareOrePlacement(25, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))))

        fun orePlacement(first: PlacementModifier, second: PlacementModifier) = listOf(first, InSquarePlacement.spread(), second, BiomeFilter.biome())
        fun commonOrePlacement(count: Int, modifier: PlacementModifier) = orePlacement(CountPlacement.of(count), modifier)
        fun rareOrePlacement(rarity: Int, modifier: PlacementModifier) = orePlacement(RarityFilter.onAverageOnceEvery(rarity), modifier)
    }

    object TreeFeatures {
        val HUGE_GLOWING_MUSHROOM = register("huge_glowing_mushroom", Features.HUGE_GLOWING_MUSHROOM.get(), HugeMushroomFeatureConfiguration(BlockStateProvider.simple(
            NTechBlocks.glowingMushroomBlock.get()), BlockStateProvider.simple(NTechBlocks.glowingMushroomStem.get().defaultBlockState().setValue(HugeMushroomBlock.UP, false).setValue(HugeMushroomBlock.DOWN, false)), 4))
    }

    object MeteoriteFeatures {
        private val SOLID_PROVIDER: SimpleStateProvider = BlockStateProvider.simple(NTechBlocks.meteorite.get())
        private val MOLTEN_PROVIDER: SimpleStateProvider = BlockStateProvider.simple(NTechBlocks.hotMeteoriteCobblestone.get())
        private val COBBLE_PROVIDER: SimpleStateProvider = BlockStateProvider.simple(NTechBlocks.meteoriteCobblestone.get())
        private val TREASURE_PROVIDER: SimpleStateProvider = BlockStateProvider.simple(NTechBlocks.meteoriteTreasure.get())
        private val BROKEN_PROVIDER: SimpleStateProvider = BlockStateProvider.simple(NTechBlocks.brokenMeteorite.get())
        private val BROKEN_TREASURE_PROVIDER: WeightedStateProvider = WeightedStateProvider(SimpleWeightedRandomList.builder<BlockState>().add(NTechBlocks.brokenMeteorite.get().defaultBlockState(), 99).add(NTechBlocks.meteoriteTreasure.get().defaultBlockState(), 1))
        private val MOLTEN_MIXED_PROVIDER: WeightedStateProvider = WeightedStateProvider(SimpleWeightedRandomList.builder<BlockState>().add(NTechBlocks.hotMeteoriteCobblestone.get().defaultBlockState(), 1).add(NTechBlocks.brokenMeteorite.get().defaultBlockState(), 1))
        private val COBBLE_MIXED_PROVIDER: WeightedStateProvider = WeightedStateProvider(SimpleWeightedRandomList.builder<BlockState>().add(NTechBlocks.meteoriteCobblestone.get().defaultBlockState(), 1).add(NTechBlocks.brokenMeteorite.get().defaultBlockState(), 1))
        private val RANDOM_ORE_WEIGHTED_LIST = { SimpleWeightedRandomList.builder<BlockState>()
            .add(NTechBlocks.meteorUraniumOre.get().defaultBlockState(), 3)
            .add(NTechBlocks.meteorThoriumOre.get().defaultBlockState(), 5)
            .add(NTechBlocks.meteorTitaniumOre.get().defaultBlockState(), 6)
            .add(NTechBlocks.meteorSulfurOre.get().defaultBlockState(), 7)
            .add(NTechBlocks.meteorCopperOre.get().defaultBlockState(), 8)
            .add(NTechBlocks.meteorTungstenOre.get().defaultBlockState(), 5)
            .add(NTechBlocks.meteorAluminiumOre.get().defaultBlockState(), 7)
            .add(NTechBlocks.meteorLeadOre.get().defaultBlockState(), 6)
            .add(NTechBlocks.meteorLithiumOre.get().defaultBlockState(), 4)
            .add(NTechBlocks.starmetalOre.get().defaultBlockState(), 1)
        }
        private val RANDOM_ORE_PROVIDER: WeightedStateProvider = WeightedStateProvider(RANDOM_ORE_WEIGHTED_LIST())

        private val MOLTEN_COBBLE_ORE_VARIANT = listOf(listOf(MOLTEN_PROVIDER), listOf(COBBLE_PROVIDER), listOf(BROKEN_PROVIDER, COBBLE_PROVIDER), listOf(RANDOM_ORE_PROVIDER))
        private val MOLTEN_COBBLE_TREASURE_VARIANT = listOf(listOf(MOLTEN_PROVIDER), listOf(COBBLE_PROVIDER), listOf(BROKEN_TREASURE_PROVIDER), listOf(SOLID_PROVIDER, TREASURE_PROVIDER))
        private val COBBLE_COBBLE_ORE_VARIANT = listOf(listOf(COBBLE_PROVIDER), listOf(COBBLE_PROVIDER), listOf(BROKEN_PROVIDER), listOf(RANDOM_ORE_PROVIDER))
        private val COBBLE_COBBLE_TREASURE_VARIANT = listOf(listOf(COBBLE_PROVIDER), listOf(COBBLE_PROVIDER), listOf(BROKEN_TREASURE_PROVIDER), listOf(SOLID_PROVIDER, TREASURE_PROVIDER))
        private val BROKEN_MIX_ORE_VARIANT = listOf(listOf(BROKEN_TREASURE_PROVIDER), listOf(BROKEN_TREASURE_PROVIDER, COBBLE_MIXED_PROVIDER), listOf(BROKEN_PROVIDER), listOf(RANDOM_ORE_PROVIDER))
        private val BROKEN_MIX_TREASURE_VARIANT = listOf(listOf(BROKEN_TREASURE_PROVIDER), listOf(BROKEN_TREASURE_PROVIDER, COBBLE_MIXED_PROVIDER), listOf(BROKEN_TREASURE_PROVIDER), listOf(SOLID_PROVIDER, TREASURE_PROVIDER))
        private val MIX_MIX_ORE_VARIANT = listOf(listOf(MOLTEN_MIXED_PROVIDER), listOf(COBBLE_MIXED_PROVIDER), listOf(BROKEN_PROVIDER), listOf(RANDOM_ORE_PROVIDER))
        private val MIX_MIX_TREASURE_VARIANT = listOf(listOf(MOLTEN_MIXED_PROVIDER), listOf(COBBLE_MIXED_PROVIDER), listOf(BROKEN_TREASURE_PROVIDER), listOf(SOLID_PROVIDER, TREASURE_PROVIDER))

        private val BOX1_PLACER = BoxMeteoritePlacer(1)
        private val SPHERE2_PLACER = SphereMeteoritePlacer(2)
        private val SPHERE3_PLACER = SphereMeteoritePlacer(3)
        private val SPHERE4_PLACER = SphereMeteoritePlacer(4)
        private val STAR1_PLACER = StarMeteoritePlacer(1)
        private val STAR2_PLACER = StarMeteoritePlacer(2)

        private val LARGE1_VARIANT = listOf(SPHERE3_PLACER, STAR2_PLACER, STAR1_PLACER, SingleBlockMeteoritePlacer)
        private val LARGE2_VARIANT = listOf(SPHERE3_PLACER, SPHERE2_PLACER, STAR1_PLACER, SingleBlockMeteoritePlacer)
        private val LARGE3_VARIANT = listOf(SPHERE3_PLACER, SPHERE2_PLACER, BOX1_PLACER, SingleBlockMeteoritePlacer)
        private val LARGE4_VARIANT = listOf(SPHERE3_PLACER, SPHERE2_PLACER, BOX1_PLACER, SingleBlockMeteoritePlacer) // has additional RANDOM_ORE STAR1
        private val LARGE5_VARIANT = listOf(SPHERE3_PLACER, SPHERE2_PLACER, STAR2_PLACER, SingleBlockMeteoritePlacer) // has additional RANDOM_ORE STAR1

        private val MEDIUM1_VARIANT = listOf(SPHERE2_PLACER, NopMeteoritePlacer, NopMeteoritePlacer, SingleBlockMeteoritePlacer) // has no RANDOM_ORE core
        private val MEDIUM2_VARIANT = listOf(SPHERE2_PLACER, STAR1_PLACER, NopMeteoritePlacer, SingleBlockMeteoritePlacer)
        private val MEDIUM3_VARIANT = listOf(SPHERE2_PLACER, BOX1_PLACER, NopMeteoritePlacer, SingleBlockMeteoritePlacer)
        private val MEDIUM4_VARIANT = listOf(SPHERE2_PLACER, BOX1_PLACER, STAR1_PLACER, SingleBlockMeteoritePlacer)
        private val MEDIUM5_VARIANT = listOf(SPHERE2_PLACER, NopMeteoritePlacer, BOX1_PLACER, SingleBlockMeteoritePlacer) // has extra variant with RANDOM_ORE STAR1

        val LARGE1_MOLTEN_COBBLE_ORE_VARIANT = register("meteorite_large1_molten_cobble_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MOLTEN_COBBLE_ORE_VARIANT zip LARGE1_VARIANT))
        val LARGE1_MOLTEN_COBBLE_TREASURE_VARIANT = register("meteorite_large1_molten_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MOLTEN_COBBLE_TREASURE_VARIANT zip LARGE1_VARIANT))
        val LARGE1_COBBLE_COBBLE_ORE_VARIANT = register("meteorite_large1_cobble_cobble_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(COBBLE_COBBLE_ORE_VARIANT zip LARGE1_VARIANT))
        val LARGE1_COBBLE_COBBLE_TREASURE_VARIANT = register("meteorite_large1_cobble_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(COBBLE_COBBLE_TREASURE_VARIANT zip LARGE1_VARIANT))
        val LARGE1_BROKEN_MIX_ORE_VARIANT = register("meteorite_large1_broken_mix_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(BROKEN_MIX_ORE_VARIANT zip LARGE1_VARIANT))
        val LARGE1_BROKEN_MIX_TREASURE_VARIANT = register("meteorite_large1_broken_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(BROKEN_MIX_TREASURE_VARIANT zip LARGE1_VARIANT))
        val LARGE1_MIX_MIX_ORE_VARIANT = register("meteorite_large1_mix_mix_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MIX_MIX_ORE_VARIANT zip LARGE1_VARIANT))
        val LARGE1_MIX_MIX_TREASURE_VARIANT = register("meteorite_large1_mix_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MIX_MIX_TREASURE_VARIANT zip LARGE1_VARIANT))
        val LARGE2_MOLTEN_COBBLE_ORE_VARIANT = register("meteorite_large2_molten_cobble_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MOLTEN_COBBLE_ORE_VARIANT zip LARGE2_VARIANT))
        val LARGE2_MOLTEN_COBBLE_TREASURE_VARIANT = register("meteorite_large2_molten_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MOLTEN_COBBLE_TREASURE_VARIANT zip LARGE2_VARIANT))
        val LARGE2_COBBLE_COBBLE_ORE_VARIANT = register("meteorite_large2_cobble_cobble_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(COBBLE_COBBLE_ORE_VARIANT zip LARGE2_VARIANT))
        val LARGE2_COBBLE_COBBLE_TREASURE_VARIANT = register("meteorite_large2_cobble_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(COBBLE_COBBLE_TREASURE_VARIANT zip LARGE2_VARIANT))
        val LARGE2_BROKEN_MIX_ORE_VARIANT = register("meteorite_large2_broken_mix_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(BROKEN_MIX_ORE_VARIANT zip LARGE2_VARIANT))
        val LARGE2_BROKEN_MIX_TREASURE_VARIANT = register("meteorite_large2_broken_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(BROKEN_MIX_TREASURE_VARIANT zip LARGE2_VARIANT))
        val LARGE2_MIX_MIX_ORE_VARIANT = register("meteorite_large2_mix_mix_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MIX_MIX_ORE_VARIANT zip LARGE2_VARIANT))
        val LARGE2_MIX_MIX_TREASURE_VARIANT = register("meteorite_large2_mix_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MIX_MIX_TREASURE_VARIANT zip LARGE2_VARIANT))
        val LARGE3_MOLTEN_COBBLE_ORE_VARIANT = register("meteorite_large3_molten_cobble_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MOLTEN_COBBLE_ORE_VARIANT zip LARGE3_VARIANT))
        val LARGE3_MOLTEN_COBBLE_TREASURE_VARIANT = register("meteorite_large3_molten_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MOLTEN_COBBLE_TREASURE_VARIANT zip LARGE3_VARIANT))
        val LARGE3_COBBLE_COBBLE_ORE_VARIANT = register("meteorite_large3_cobble_cobble_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(COBBLE_COBBLE_ORE_VARIANT zip LARGE3_VARIANT))
        val LARGE3_COBBLE_COBBLE_TREASURE_VARIANT = register("meteorite_large3_cobble_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(COBBLE_COBBLE_TREASURE_VARIANT zip LARGE3_VARIANT))
        val LARGE3_BROKEN_MIX_ORE_VARIANT = register("meteorite_large3_broken_mix_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(BROKEN_MIX_ORE_VARIANT zip LARGE3_VARIANT))
        val LARGE3_BROKEN_MIX_TREASURE_VARIANT = register("meteorite_large3_broken_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(BROKEN_MIX_TREASURE_VARIANT zip LARGE3_VARIANT))
        val LARGE3_MIX_MIX_ORE_VARIANT = register("meteorite_large3_mix_mix_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MIX_MIX_ORE_VARIANT zip LARGE3_VARIANT))
        val LARGE3_MIX_MIX_TREASURE_VARIANT = register("meteorite_large3_mix_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MIX_MIX_TREASURE_VARIANT zip LARGE3_VARIANT))
        val LARGE4_MOLTEN_COBBLE_ORE_VARIANT = register("meteorite_large4_molten_cobble_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((MOLTEN_COBBLE_ORE_VARIANT zip LARGE4_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val LARGE4_MOLTEN_COBBLE_TREASURE_VARIANT = register("meteorite_large4_molten_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((MOLTEN_COBBLE_TREASURE_VARIANT zip LARGE4_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val LARGE4_COBBLE_COBBLE_ORE_VARIANT = register("meteorite_large4_cobble_cobble_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((COBBLE_COBBLE_ORE_VARIANT zip LARGE4_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val LARGE4_COBBLE_COBBLE_TREASURE_VARIANT = register("meteorite_large4_cobble_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((COBBLE_COBBLE_TREASURE_VARIANT zip LARGE4_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val LARGE4_BROKEN_MIX_ORE_VARIANT = register("meteorite_large4_broken_mix_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((BROKEN_MIX_ORE_VARIANT zip LARGE4_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val LARGE4_BROKEN_MIX_TREASURE_VARIANT = register("meteorite_large4_broken_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((BROKEN_MIX_TREASURE_VARIANT zip LARGE4_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val LARGE4_MIX_MIX_ORE_VARIANT = register("meteorite_large4_mix_mix_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((MIX_MIX_ORE_VARIANT zip LARGE4_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val LARGE4_MIX_MIX_TREASURE_VARIANT = register("meteorite_large4_mix_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((MIX_MIX_TREASURE_VARIANT zip LARGE4_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val LARGE5_MOLTEN_COBBLE_ORE_VARIANT = register("meteorite_large5_molten_cobble_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((MOLTEN_COBBLE_ORE_VARIANT zip LARGE5_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val LARGE5_MOLTEN_COBBLE_TREASURE_VARIANT = register("meteorite_large5_molten_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((MOLTEN_COBBLE_TREASURE_VARIANT zip LARGE5_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val LARGE5_COBBLE_COBBLE_ORE_VARIANT = register("meteorite_large5_cobble_cobble_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((COBBLE_COBBLE_ORE_VARIANT zip LARGE5_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val LARGE5_COBBLE_COBBLE_TREASURE_VARIANT = register("meteorite_large5_cobble_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((COBBLE_COBBLE_TREASURE_VARIANT zip LARGE5_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val LARGE5_BROKEN_MIX_ORE_VARIANT = register("meteorite_large5_broken_mix_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((BROKEN_MIX_ORE_VARIANT zip LARGE5_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val LARGE5_BROKEN_MIX_TREASURE_VARIANT = register("meteorite_large5_broken_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((BROKEN_MIX_TREASURE_VARIANT zip LARGE5_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val LARGE5_MIX_MIX_ORE_VARIANT = register("meteorite_large5_mix_mix_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((MIX_MIX_ORE_VARIANT zip LARGE5_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val LARGE5_MIX_MIX_TREASURE_VARIANT = register("meteorite_large5_mix_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((MIX_MIX_TREASURE_VARIANT zip LARGE5_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))

        val MEDIUM1_MOLTEN_COBBLE_TREASURE_VARIANT = register("meteorite_medium1_molten_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MOLTEN_COBBLE_TREASURE_VARIANT zip MEDIUM1_VARIANT))
        val MEDIUM1_COBBLE_COBBLE_TREASURE_VARIANT = register("meteorite_medium1_cobble_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(COBBLE_COBBLE_TREASURE_VARIANT zip MEDIUM1_VARIANT))
        val MEDIUM1_BROKEN_MIX_TREASURE_VARIANT = register("meteorite_medium1_broken_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(BROKEN_MIX_TREASURE_VARIANT zip MEDIUM1_VARIANT))
        val MEDIUM1_MIX_MIX_TREASURE_VARIANT = register("meteorite_medium1_mix_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MIX_MIX_TREASURE_VARIANT zip MEDIUM1_VARIANT))
        val MEDIUM2_MOLTEN_COBBLE_ORE_VARIANT = register("meteorite_medium2_molten_cobble_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MOLTEN_COBBLE_ORE_VARIANT zip MEDIUM2_VARIANT))
        val MEDIUM2_MOLTEN_COBBLE_TREASURE_VARIANT = register("meteorite_medium2_molten_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MOLTEN_COBBLE_TREASURE_VARIANT zip MEDIUM2_VARIANT))
        val MEDIUM2_COBBLE_COBBLE_ORE_VARIANT = register("meteorite_medium2_cobble_cobble_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(COBBLE_COBBLE_ORE_VARIANT zip MEDIUM2_VARIANT))
        val MEDIUM2_COBBLE_COBBLE_TREASURE_VARIANT = register("meteorite_medium2_cobble_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(COBBLE_COBBLE_TREASURE_VARIANT zip MEDIUM2_VARIANT))
        val MEDIUM2_BROKEN_MIX_ORE_VARIANT = register("meteorite_medium2_broken_mix_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(BROKEN_MIX_ORE_VARIANT zip MEDIUM2_VARIANT))
        val MEDIUM2_BROKEN_MIX_TREASURE_VARIANT = register("meteorite_medium2_broken_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(BROKEN_MIX_TREASURE_VARIANT zip MEDIUM2_VARIANT))
        val MEDIUM2_MIX_MIX_ORE_VARIANT = register("meteorite_medium2_mix_mix_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MIX_MIX_ORE_VARIANT zip MEDIUM2_VARIANT))
        val MEDIUM2_MIX_MIX_TREASURE_VARIANT = register("meteorite_medium2_mix_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MIX_MIX_TREASURE_VARIANT zip MEDIUM2_VARIANT))
        val MEDIUM3_MOLTEN_COBBLE_ORE_VARIANT = register("meteorite_medium3_molten_cobble_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MOLTEN_COBBLE_ORE_VARIANT zip MEDIUM3_VARIANT))
        val MEDIUM3_MOLTEN_COBBLE_TREASURE_VARIANT = register("meteorite_medium3_molten_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MOLTEN_COBBLE_TREASURE_VARIANT zip MEDIUM3_VARIANT))
        val MEDIUM3_COBBLE_COBBLE_ORE_VARIANT = register("meteorite_medium3_cobble_cobble_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(COBBLE_COBBLE_ORE_VARIANT zip MEDIUM3_VARIANT))
        val MEDIUM3_COBBLE_COBBLE_TREASURE_VARIANT = register("meteorite_medium3_cobble_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(COBBLE_COBBLE_TREASURE_VARIANT zip MEDIUM3_VARIANT))
        val MEDIUM3_BROKEN_MIX_ORE_VARIANT = register("meteorite_medium3_broken_mix_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(BROKEN_MIX_ORE_VARIANT zip MEDIUM3_VARIANT))
        val MEDIUM3_BROKEN_MIX_TREASURE_VARIANT = register("meteorite_medium3_broken_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(BROKEN_MIX_TREASURE_VARIANT zip MEDIUM3_VARIANT))
        val MEDIUM3_MIX_MIX_ORE_VARIANT = register("meteorite_medium3_mix_mix_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MIX_MIX_ORE_VARIANT zip MEDIUM3_VARIANT))
        val MEDIUM3_MIX_MIX_TREASURE_VARIANT = register("meteorite_medium3_mix_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MIX_MIX_TREASURE_VARIANT zip MEDIUM3_VARIANT))
        val MEDIUM4_MOLTEN_COBBLE_ORE_VARIANT = register("meteorite_medium4_molten_cobble_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MOLTEN_COBBLE_ORE_VARIANT zip MEDIUM4_VARIANT))
        val MEDIUM4_MOLTEN_COBBLE_TREASURE_VARIANT = register("meteorite_medium4_molten_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MOLTEN_COBBLE_TREASURE_VARIANT zip MEDIUM4_VARIANT))
        val MEDIUM4_COBBLE_COBBLE_ORE_VARIANT = register("meteorite_medium4_cobble_cobble_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(COBBLE_COBBLE_ORE_VARIANT zip MEDIUM4_VARIANT))
        val MEDIUM4_COBBLE_COBBLE_TREASURE_VARIANT = register("meteorite_medium4_cobble_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(COBBLE_COBBLE_TREASURE_VARIANT zip MEDIUM4_VARIANT))
        val MEDIUM4_BROKEN_MIX_ORE_VARIANT = register("meteorite_medium4_broken_mix_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(BROKEN_MIX_ORE_VARIANT zip MEDIUM4_VARIANT))
        val MEDIUM4_BROKEN_MIX_TREASURE_VARIANT = register("meteorite_medium4_broken_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(BROKEN_MIX_TREASURE_VARIANT zip MEDIUM4_VARIANT))
        val MEDIUM4_MIX_MIX_ORE_VARIANT = register("meteorite_medium4_mix_mix_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MIX_MIX_ORE_VARIANT zip MEDIUM4_VARIANT))
        val MEDIUM4_MIX_MIX_TREASURE_VARIANT = register("meteorite_medium4_mix_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MIX_MIX_TREASURE_VARIANT zip MEDIUM4_VARIANT))
        val MEDIUM5_MOLTEN_COBBLE_ORE_VARIANT = register("meteorite_medium5_molten_cobble_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MOLTEN_COBBLE_ORE_VARIANT zip MEDIUM5_VARIANT))
        val MEDIUM5_MOLTEN_COBBLE_TREASURE_VARIANT = register("meteorite_medium5_molten_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MOLTEN_COBBLE_TREASURE_VARIANT zip MEDIUM5_VARIANT))
        val MEDIUM5_COBBLE_COBBLE_ORE_VARIANT = register("meteorite_medium5_cobble_cobble_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(COBBLE_COBBLE_ORE_VARIANT zip MEDIUM5_VARIANT))
        val MEDIUM5_COBBLE_COBBLE_TREASURE_VARIANT = register("meteorite_medium5_cobble_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(COBBLE_COBBLE_TREASURE_VARIANT zip MEDIUM5_VARIANT))
        val MEDIUM5_BROKEN_MIX_ORE_VARIANT = register("meteorite_medium5_broken_mix_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(BROKEN_MIX_ORE_VARIANT zip MEDIUM5_VARIANT))
        val MEDIUM5_BROKEN_MIX_TREASURE_VARIANT = register("meteorite_medium5_broken_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(BROKEN_MIX_TREASURE_VARIANT zip MEDIUM5_VARIANT))
        val MEDIUM5_MIX_MIX_ORE_VARIANT = register("meteorite_medium5_mix_mix_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MIX_MIX_ORE_VARIANT zip MEDIUM5_VARIANT))
        val MEDIUM5_MIX_MIX_TREASURE_VARIANT = register("meteorite_medium5_mix_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of(MIX_MIX_TREASURE_VARIANT zip MEDIUM5_VARIANT))
        val MEDIUM6_MOLTEN_COBBLE_ORE_VARIANT = register("meteorite_medium6_molten_cobble_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((MOLTEN_COBBLE_ORE_VARIANT zip MEDIUM5_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val MEDIUM6_MOLTEN_COBBLE_TREASURE_VARIANT = register("meteorite_medium6_molten_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((MOLTEN_COBBLE_TREASURE_VARIANT zip MEDIUM5_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val MEDIUM6_COBBLE_COBBLE_ORE_VARIANT = register("meteorite_medium6_cobble_cobble_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((COBBLE_COBBLE_ORE_VARIANT zip MEDIUM5_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val MEDIUM6_COBBLE_COBBLE_TREASURE_VARIANT = register("meteorite_medium6_cobble_cobble_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((COBBLE_COBBLE_TREASURE_VARIANT zip MEDIUM5_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val MEDIUM6_BROKEN_MIX_ORE_VARIANT = register("meteorite_medium6_broken_mix_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((BROKEN_MIX_ORE_VARIANT zip MEDIUM5_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val MEDIUM6_BROKEN_MIX_TREASURE_VARIANT = register("meteorite_medium6_broken_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((BROKEN_MIX_TREASURE_VARIANT zip MEDIUM5_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val MEDIUM6_MIX_MIX_ORE_VARIANT = register("meteorite_medium6_mix_mix_ore_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((MIX_MIX_ORE_VARIANT zip MEDIUM5_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))
        val MEDIUM6_MIX_MIX_TREASURE_VARIANT = register("meteorite_medium6_mix_mix_treasure_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration.of((MIX_MIX_TREASURE_VARIANT zip MEDIUM5_VARIANT) + (listOf(RANDOM_ORE_PROVIDER) to STAR1_PLACER)))

        val SMALL_MOLTEN_VARIANT = register("meteorite_small_molten_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration(listOf(MeteoriteFeatureConfiguration.MeteoritePlacerConfiguration(listOf(MOLTEN_PROVIDER), BOX1_PLACER), MeteoriteFeatureConfiguration.MeteoritePlacerConfiguration(listOf(SOLID_PROVIDER, TREASURE_PROVIDER), SingleBlockMeteoritePlacer))))
        val SMALL_COBBLE_VARIANT = register("meteorite_small_cobble_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration(listOf(MeteoriteFeatureConfiguration.MeteoritePlacerConfiguration(listOf(COBBLE_PROVIDER), BOX1_PLACER), MeteoriteFeatureConfiguration.MeteoritePlacerConfiguration(listOf(SOLID_PROVIDER, TREASURE_PROVIDER), SingleBlockMeteoritePlacer))))
        val SMALL_BROKEN_VARIANT = register("meteorite_small_broken_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration(listOf(MeteoriteFeatureConfiguration.MeteoritePlacerConfiguration(listOf(BROKEN_TREASURE_PROVIDER), BOX1_PLACER), MeteoriteFeatureConfiguration.MeteoritePlacerConfiguration(listOf(SOLID_PROVIDER, TREASURE_PROVIDER), SingleBlockMeteoritePlacer))))
        val SMALL_MIX_VARIANT = register("meteorite_small_mix_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration(listOf(MeteoriteFeatureConfiguration.MeteoritePlacerConfiguration(listOf(MOLTEN_MIXED_PROVIDER), BOX1_PLACER), MeteoriteFeatureConfiguration.MeteoritePlacerConfiguration(listOf(SOLID_PROVIDER, TREASURE_PROVIDER), SingleBlockMeteoritePlacer))))

        val SPECIAL_SOLID_BOX_VARIANT = register("meteorite_special_solid_box_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration(listOf(MeteoriteFeatureConfiguration.MeteoritePlacerConfiguration(listOf(SOLID_PROVIDER), BOX1_PLACER))))
        val SPECIAL_GIANT_ORE_SPHERE_VARIANT = register("meteorite_special_giant_ore_sphere_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration(listOf(MeteoriteFeatureConfiguration.MeteoritePlacerConfiguration(listOf(BROKEN_PROVIDER), SPHERE4_PLACER), MeteoriteFeatureConfiguration.MeteoritePlacerConfiguration(listOf(RANDOM_ORE_PROVIDER), SPHERE3_PLACER))))
        val SPECIAL_LARGE_ORE_SPHERE_VARIANT = register("meteorite_special_large_ore_sphere_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration(listOf(MeteoriteFeatureConfiguration.MeteoritePlacerConfiguration(listOf(WeightedStateProvider(RANDOM_ORE_WEIGHTED_LIST().let { it.add(NTechBlocks.brokenMeteorite.get().defaultBlockState(), it.build().unwrap().size) })), SPHERE3_PLACER))))
        val SPECIAL_MEDIUM_ORE_SPHERE_VARIANT = register("meteorite_special_medium_ore_sphere_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration(listOf(MeteoriteFeatureConfiguration.MeteoritePlacerConfiguration(listOf(WeightedStateProvider(RANDOM_ORE_WEIGHTED_LIST().let { it.add(NTechBlocks.brokenMeteorite.get().defaultBlockState(), it.build().unwrap().size / 2) })), SPHERE2_PLACER))))
        val SPECIAL_SMALL_ORE_BOX_VARIANT = register("meteorite_special_small_ore_box_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration(listOf(MeteoriteFeatureConfiguration.MeteoritePlacerConfiguration(listOf(RANDOM_ORE_PROVIDER), BOX1_PLACER))))
        val SPECIAL_LARGE_TREASURE_SPHERE_VARIANT = register("meteorite_special_large_treasure_sphere_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration(listOf(MeteoriteFeatureConfiguration.MeteoritePlacerConfiguration(listOf(TREASURE_PROVIDER, BROKEN_PROVIDER), SPHERE3_PLACER))))
        val SPECIAL_MEDIUM_TREASURE_SPHERE_VARIANT = register("meteorite_special_medium_treasure_sphere_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration(listOf(MeteoriteFeatureConfiguration.MeteoritePlacerConfiguration(listOf(TREASURE_PROVIDER, TREASURE_PROVIDER, BROKEN_PROVIDER), SPHERE2_PLACER))))
        val SPECIAL_SMALL_TREASURE_BOX_VARIANT = register("meteorite_special_small_treasure_box_variant", Features.METEORITE.get(), MeteoriteFeatureConfiguration(listOf(MeteoriteFeatureConfiguration.MeteoritePlacerConfiguration(listOf(TREASURE_PROVIDER), BOX1_PLACER))))

        val LARGE_METEORITE_VARIANTS = listOf(LARGE1_MOLTEN_COBBLE_ORE_VARIANT, LARGE1_MOLTEN_COBBLE_TREASURE_VARIANT, LARGE1_COBBLE_COBBLE_ORE_VARIANT, LARGE1_COBBLE_COBBLE_TREASURE_VARIANT, LARGE1_BROKEN_MIX_ORE_VARIANT, LARGE1_BROKEN_MIX_TREASURE_VARIANT, LARGE1_MIX_MIX_ORE_VARIANT, LARGE1_MIX_MIX_TREASURE_VARIANT, LARGE2_MOLTEN_COBBLE_ORE_VARIANT, LARGE2_MOLTEN_COBBLE_TREASURE_VARIANT, LARGE2_COBBLE_COBBLE_ORE_VARIANT, LARGE2_COBBLE_COBBLE_TREASURE_VARIANT, LARGE2_BROKEN_MIX_ORE_VARIANT, LARGE2_BROKEN_MIX_TREASURE_VARIANT, LARGE2_MIX_MIX_ORE_VARIANT, LARGE2_MIX_MIX_TREASURE_VARIANT, LARGE3_MOLTEN_COBBLE_ORE_VARIANT, LARGE3_MOLTEN_COBBLE_TREASURE_VARIANT, LARGE3_COBBLE_COBBLE_ORE_VARIANT, LARGE3_COBBLE_COBBLE_TREASURE_VARIANT, LARGE3_BROKEN_MIX_ORE_VARIANT, LARGE3_BROKEN_MIX_TREASURE_VARIANT, LARGE3_MIX_MIX_ORE_VARIANT, LARGE3_MIX_MIX_TREASURE_VARIANT, LARGE4_MOLTEN_COBBLE_ORE_VARIANT, LARGE4_MOLTEN_COBBLE_TREASURE_VARIANT, LARGE4_COBBLE_COBBLE_ORE_VARIANT, LARGE4_COBBLE_COBBLE_TREASURE_VARIANT, LARGE4_BROKEN_MIX_ORE_VARIANT, LARGE4_BROKEN_MIX_TREASURE_VARIANT, LARGE4_MIX_MIX_ORE_VARIANT, LARGE4_MIX_MIX_TREASURE_VARIANT, LARGE5_MOLTEN_COBBLE_ORE_VARIANT, LARGE5_MOLTEN_COBBLE_TREASURE_VARIANT, LARGE5_COBBLE_COBBLE_ORE_VARIANT, LARGE5_COBBLE_COBBLE_TREASURE_VARIANT, LARGE5_BROKEN_MIX_ORE_VARIANT, LARGE5_BROKEN_MIX_TREASURE_VARIANT, LARGE5_MIX_MIX_ORE_VARIANT, LARGE5_MIX_MIX_TREASURE_VARIANT)
        val MEDIUM_METEORITE_VARIANTS = listOf(MEDIUM1_MOLTEN_COBBLE_TREASURE_VARIANT, MEDIUM1_COBBLE_COBBLE_TREASURE_VARIANT, MEDIUM1_BROKEN_MIX_TREASURE_VARIANT, MEDIUM1_MIX_MIX_TREASURE_VARIANT, MEDIUM2_MOLTEN_COBBLE_ORE_VARIANT, MEDIUM2_MOLTEN_COBBLE_TREASURE_VARIANT, MEDIUM2_COBBLE_COBBLE_ORE_VARIANT, MEDIUM2_COBBLE_COBBLE_TREASURE_VARIANT, MEDIUM2_BROKEN_MIX_ORE_VARIANT, MEDIUM2_BROKEN_MIX_TREASURE_VARIANT, MEDIUM2_MIX_MIX_ORE_VARIANT, MEDIUM2_MIX_MIX_TREASURE_VARIANT, MEDIUM3_MOLTEN_COBBLE_ORE_VARIANT, MEDIUM3_MOLTEN_COBBLE_TREASURE_VARIANT, MEDIUM3_COBBLE_COBBLE_ORE_VARIANT, MEDIUM3_COBBLE_COBBLE_TREASURE_VARIANT, MEDIUM3_BROKEN_MIX_ORE_VARIANT, MEDIUM3_BROKEN_MIX_TREASURE_VARIANT, MEDIUM3_MIX_MIX_ORE_VARIANT, MEDIUM3_MIX_MIX_TREASURE_VARIANT, MEDIUM4_MOLTEN_COBBLE_ORE_VARIANT, MEDIUM4_MOLTEN_COBBLE_TREASURE_VARIANT, MEDIUM4_COBBLE_COBBLE_ORE_VARIANT, MEDIUM4_COBBLE_COBBLE_TREASURE_VARIANT, MEDIUM4_BROKEN_MIX_ORE_VARIANT, MEDIUM4_BROKEN_MIX_TREASURE_VARIANT, MEDIUM4_MIX_MIX_ORE_VARIANT, MEDIUM4_MIX_MIX_TREASURE_VARIANT, MEDIUM5_MOLTEN_COBBLE_ORE_VARIANT, MEDIUM5_MOLTEN_COBBLE_TREASURE_VARIANT, MEDIUM5_COBBLE_COBBLE_ORE_VARIANT, MEDIUM5_COBBLE_COBBLE_TREASURE_VARIANT, MEDIUM5_BROKEN_MIX_ORE_VARIANT, MEDIUM5_BROKEN_MIX_TREASURE_VARIANT, MEDIUM5_MIX_MIX_ORE_VARIANT, MEDIUM5_MIX_MIX_TREASURE_VARIANT, MEDIUM6_MOLTEN_COBBLE_ORE_VARIANT, MEDIUM6_MOLTEN_COBBLE_TREASURE_VARIANT, MEDIUM6_COBBLE_COBBLE_ORE_VARIANT, MEDIUM6_COBBLE_COBBLE_TREASURE_VARIANT, MEDIUM6_BROKEN_MIX_ORE_VARIANT, MEDIUM6_BROKEN_MIX_TREASURE_VARIANT, MEDIUM6_MIX_MIX_ORE_VARIANT, MEDIUM6_MIX_MIX_TREASURE_VARIANT)
        val SMALL_METEORITE_VARIANTS = listOf(SMALL_MOLTEN_VARIANT, SMALL_COBBLE_VARIANT, SMALL_BROKEN_VARIANT, SMALL_MIX_VARIANT)

        // TODO nuclear and taint meteorites
    }

    private fun <FC : FeatureConfiguration, F : Feature<FC>> register(name: String, feature: F, config: FC): Holder<ConfiguredFeature<FC, *>> = BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, ntm(name).toString(), ConfiguredFeature(feature, config))
    private fun register(name: String, feature: Holder<out ConfiguredFeature<*, *>>, modifiers: List<PlacementModifier>): Holder<PlacedFeature> = BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, ntm(name), PlacedFeature(Holder.hackyErase(feature), modifiers.toList()))
}
