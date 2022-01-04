package at.martinthedragon.nucleartech.world.gen

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.RegistriesAndLifecycle.FEATURES
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.world.gen.features.HugeGlowingMushroomFeature
import at.martinthedragon.nucleartech.world.gen.features.OilBubbleFeature
import net.minecraft.core.Registry
import net.minecraft.data.BuiltinRegistries
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.Biomes
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.HugeMushroomBlock
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
import net.minecraft.world.level.levelgen.placement.*
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder
import net.minecraftforge.event.world.BiomeLoadingEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.registries.RegistryObject
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
        val OIL_BUBBLE: RegistryObject<OilBubbleFeature> = FEATURES.register("oil_bubble") { OilBubbleFeature(NoneFeatureConfiguration.CODEC) }
        val HUGE_GLOWING_MUSHROOM: RegistryObject<HugeGlowingMushroomFeature> = FEATURES.register("huge_glowing_mushroom") { HugeGlowingMushroomFeature(HugeMushroomFeatureConfiguration.CODEC) }
    }

    private object OreFeatures {
        val NATURAL_STONE: RuleTest = VanillaOreFeatures.NATURAL_STONE
        val STONE_ORE_REPLACEABLES: RuleTest = VanillaOreFeatures.STONE_ORE_REPLACEABLES
        val DEEPSLATE_ORE_REPLACEABLES: RuleTest = VanillaOreFeatures.DEEPSLATE_ORE_REPLACEABLES
        val NETHERRACK: RuleTest = VanillaOreFeatures.NETHERRACK
        val NETHER_ORE_REPLACEABLES: RuleTest = VanillaOreFeatures.NETHER_ORE_REPLACEABLES
        val END_STONE: RuleTest = BlockMatchTest(Blocks.END_STONE)
        val ORE_URANIUM_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.uraniumOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.deepslateUraniumOre.get().defaultBlockState()))
        val ORE_THORIUM_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.thoriumOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.deepslateThoriumOre.get().defaultBlockState()))
        val ORE_TITANIUM_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.titaniumOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.deepslateTitaniumOre.get().defaultBlockState()))
        val ORE_SULFUR_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.sulfurOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.deepslateSulfurOre.get().defaultBlockState()))
        val ORE_NITER_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.niterOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.deepslateNiterOre.get().defaultBlockState()))
        val ORE_TUNGSTEN_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.tungstenOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.deepslateTungstenOre.get().defaultBlockState()))
        val ORE_ALUMINIUM_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.aluminiumOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.deepslateAluminiumOre.get().defaultBlockState()))
        val ORE_FLUORITE_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.fluoriteOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.deepslateFluoriteOre.get().defaultBlockState()))
        val ORE_BERYLLIUM_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.berylliumOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.deepslateBerylliumOre.get().defaultBlockState()))
        val ORE_LEAD_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.leadOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.deepslateLeadOre.get().defaultBlockState()))
        val ORE_ASBESTOS_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.asbestosOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.deepslateAsbestosOre.get().defaultBlockState()))
        val ORE_RARE_EARTH_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.rareEarthOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.deepslateRareEarthOre.get().defaultBlockState()))
        val ORE_COBALT_TARGET_LIST = listOf(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.cobaltOre.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.deepslateCobaltOre.get().defaultBlockState()))
        val ORE_URANIUM_SMALL = register("ore_uranium_small", Feature.ORE.configured(OreConfiguration(ORE_URANIUM_TARGET_LIST, 3, .5F)))
        val ORE_URANIUM_LARGE = register("ore_uranium_large", Feature.ORE.configured(OreConfiguration(ORE_URANIUM_TARGET_LIST, 12, .7F)))
        val ORE_URANIUM_BURIED = register("ore_uranium_buried", Feature.ORE.configured(OreConfiguration(ORE_URANIUM_TARGET_LIST, 6, 1F)))
        val ORE_THORIUM = register("ore_thorium", Feature.ORE.configured(OreConfiguration(ORE_THORIUM_TARGET_LIST, 5)))
        val ORE_THORIUM_SMALL = register("ore_thorium_small", Feature.ORE.configured(OreConfiguration(ORE_THORIUM_TARGET_LIST, 3)))
        val ORE_TITANIUM = register("ore_titanium", Feature.ORE.configured(OreConfiguration(ORE_TITANIUM_TARGET_LIST, 6, .4F)))
        val ORE_SULFUR = register("ore_sulfur", Feature.ORE.configured(OreConfiguration(ORE_SULFUR_TARGET_LIST, 10, .2F)))
        val ORE_SULFUR_SMALL = register("ore_sulfur_small", Feature.ORE.configured(OreConfiguration(ORE_SULFUR_TARGET_LIST, 4)))
        val ORE_NITER = register("ore_niter", Feature.ORE.configured(OreConfiguration(ORE_NITER_TARGET_LIST, 8, .3F)))
        val ORE_NITER_SMALL = register("ore_niter_small", Feature.ORE.configured(OreConfiguration(ORE_NITER_TARGET_LIST, 3)))
        val ORE_TUNGSTEN = register("ore_tungsten", Feature.ORE.configured(OreConfiguration(ORE_TUNGSTEN_TARGET_LIST, 3)))
        val ORE_TUNGSTEN_BURIED = register("ore_tungsten_buried", Feature.ORE.configured(OreConfiguration(ORE_TUNGSTEN_TARGET_LIST, 9, 1F)))
        val ORE_ALUMINIUM = register("ore_aluminium", Feature.ORE.configured(OreConfiguration(ORE_ALUMINIUM_TARGET_LIST, 5)))
        val ORE_ALUMINIUM_SMALL = register("ore_aluminium_small", Feature.ORE.configured(OreConfiguration(ORE_ALUMINIUM_TARGET_LIST, 3)))
        val ORE_FLUORITE = register("ore_fluorite", Feature.ORE.configured(OreConfiguration(ORE_FLUORITE_TARGET_LIST, 4)))
        val ORE_FLUORITE_BURIED = register("ore_fluorite_buried", Feature.ORE.configured(OreConfiguration(ORE_FLUORITE_TARGET_LIST, 15, 1F)))
        val ORE_BERYLLIUM = register("ore_beryllium", Feature.ORE.configured(OreConfiguration(ORE_BERYLLIUM_TARGET_LIST, 4)))
        val ORE_BERYLLIUM_SMALL = register("ore_beryllium_small", Feature.ORE.configured(OreConfiguration(ORE_BERYLLIUM_TARGET_LIST, 2)))
        val ORE_LEAD = register("ore_lead", Feature.ORE.configured(OreConfiguration(ORE_LEAD_TARGET_LIST, 10, .6F)))
        val ORE_LIGNITE = register("ore_lignite", Feature.ORE.configured(OreConfiguration(STONE_ORE_REPLACEABLES, ModBlocks.ligniteOre.get().defaultBlockState(), 30, .2F)))
        val ORE_ASBESTOS = register("ore_asbestos", Feature.ORE.configured(OreConfiguration(ORE_ASBESTOS_TARGET_LIST, 10, 1F)))
        val ORE_RARE_EARTH_SMALL = register("ore_rare_earth_small", Feature.ORE.configured(OreConfiguration(ORE_RARE_EARTH_TARGET_LIST, 1)))
        val ORE_RARE_EARTH_LARGE = register("ore_rare_earth_large", Feature.ORE.configured(OreConfiguration(ORE_RARE_EARTH_TARGET_LIST, 15)))
        val ORE_RARE_EARTH_BURIED = register("ore_rare_earth_buried", Feature.ORE.configured(OreConfiguration(ORE_RARE_EARTH_TARGET_LIST, 5, 1F)))
        val ORE_COBALT_SMALL = register("ore_cobalt_small", Feature.ORE.configured(OreConfiguration(ORE_COBALT_TARGET_LIST, 3)))
        val ORE_COBALT_LARGE = register("ore_cobalt_large", Feature.ORE.configured(OreConfiguration(ORE_COBALT_TARGET_LIST, 15, .25F)))
        val ORE_NETHER_URANIUM = register("ore_nether_uranium", Feature.ORE.configured(OreConfiguration(NETHERRACK, ModBlocks.netherUraniumOre.get().defaultBlockState(), 6)))
        val ORE_NETHER_PLUTONIUM = register("ore_nether_plutonium", Feature.ORE.configured(OreConfiguration(NETHERRACK, ModBlocks.netherPlutoniumOre.get().defaultBlockState(), 4)))
        val ORE_NETHER_TUNGSTEN = register("ore_nether_tungsten", Feature.ORE.configured(OreConfiguration(NETHERRACK, ModBlocks.netherTungstenOre.get().defaultBlockState(), 10)))
        val ORE_NETHER_SULFUR = register("ore_nether_sulfur", Feature.ORE.configured(OreConfiguration(NETHERRACK, ModBlocks.netherSulfurOre.get().defaultBlockState(), 18)))
        val ORE_NETHER_PHOSPHORUS = register("ore_nether_phosphorus", Feature.ORE.configured(OreConfiguration(NETHERRACK, ModBlocks.netherPhosphorusOre.get().defaultBlockState(), 5)))
        val ORE_END_TRIXITE = register("ore_end_trixite", Feature.ORE.configured(OreConfiguration(END_STONE, ModBlocks.trixite.get().defaultBlockState(), 4, .1F)))

        val OIL_BUBBLE = register("oil_bubble", Features.OIL_BUBBLE.get().configured(FeatureConfiguration.NONE))
    }

    private object OrePlacements {
        val ORE_URANIUM = register("ore_uranium", OreFeatures.ORE_URANIUM_SMALL.placed(commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(25)))))
        val ORE_URANIUM_LARGE = register("ore_uranium_large", OreFeatures.ORE_URANIUM_LARGE.placed(rareOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-40), VerticalAnchor.aboveBottom(80)))))
        val ORE_URANIUM_BURIED = register("ore_uranium_buried", OreFeatures.ORE_URANIUM_BURIED.placed(commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-40), VerticalAnchor.aboveBottom(80)))))
        val ORE_THORIUM = register("ore_thorium_lower", OreFeatures.ORE_THORIUM.placed(commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0)))))
        val ORE_THORIUM_MIDDLE = register("ore_thorium_middle", OreFeatures.ORE_THORIUM_SMALL.placed(commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-30), VerticalAnchor.absolute(20)))))
        val ORE_TITANIUM = register("ore_titanium", OreFeatures.ORE_TITANIUM.placed(commonOrePlacement(14, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-100), VerticalAnchor.aboveBottom(100)))))
        val ORE_SULFUR_LOWER = register("ore_sulfur_lower", OreFeatures.ORE_SULFUR.placed(commonOrePlacement(12, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-40), VerticalAnchor.aboveBottom(40)))))
        val ORE_SULFUR_SMALL = register("ore_sulfur_small", OreFeatures.ORE_SULFUR_SMALL.placed(commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(50)))))
        val ORE_NITER = register("ore_niter", OreFeatures.ORE_NITER_SMALL.placed(commonOrePlacement(30, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(80)))))
        val ORE_NITER_EXTRA = register("ore_niter_extra", OreFeatures.ORE_NITER.placed(commonOrePlacement(50, HeightRangePlacement.triangle(VerticalAnchor.absolute(30), VerticalAnchor.absolute(128)))))
        val ORE_TUNGSTEN_MIDDLE = register("ore_tungsten_middle", OreFeatures.ORE_TUNGSTEN.placed(commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.absolute(-20), VerticalAnchor.absolute(40)))))
        val ORE_TUNGSTEN_BURIED = register("ore_tungsten_buried", OreFeatures.ORE_TUNGSTEN_BURIED.placed(commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.absolute(0)))))
        val ORE_ALUMINIUM = register("ore_aluminium", OreFeatures.ORE_ALUMINIUM_SMALL.placed(commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(40)))))
        val ORE_ALUMINIUM_MIDDLE = register("ore_aluminium_middle", OreFeatures.ORE_ALUMINIUM.placed(commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-15), VerticalAnchor.absolute(30)))))
        val ORE_FLUORITE_LOWER = register("ore_fluorite_lower", OreFeatures.ORE_FLUORITE.placed(commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(10)))))
        val ORE_FLUORITE_BURIED = register("ore_fluorite_buried", OreFeatures.ORE_FLUORITE_BURIED.placed(commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-30), VerticalAnchor.absolute(50)))))
        val ORE_BERYLLIUM = register("ore_beryllium", OreFeatures.ORE_BERYLLIUM_SMALL.placed(commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(30)))))
        val ORE_BERYLLIUM_LOWER = register("ore_beryllium_lower", OreFeatures.ORE_BERYLLIUM.placed(commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-40), VerticalAnchor.aboveBottom(40)))))
        val ORE_LEAD = register("ore_lead", OreFeatures.ORE_LEAD.placed(commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.absolute(30)))))
        val ORE_LIGNITE = register("ore_lignite", OreFeatures.ORE_LIGNITE.placed(commonOrePlacement(1, HeightRangePlacement.triangle(VerticalAnchor.absolute(30), VerticalAnchor.absolute(128)))))
        val ORE_ASBESTOS = register("ore_asbestos", OreFeatures.ORE_ASBESTOS.placed(commonOrePlacement(3, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(20), VerticalAnchor.absolute(40)))))
        val ORE_ASBESTOS_MIDDLE = register("ore_asbestos_middle", OreFeatures.ORE_ASBESTOS.placed(commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.top()))))
        val ORE_RARE_EARTH = register("ore_rare_earth", OreFeatures.ORE_RARE_EARTH_SMALL.placed(commonOrePlacement(100, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()))))
        val ORE_RARE_EARTH_LARGE = register("ore_rare_earth_large", OreFeatures.ORE_RARE_EARTH_LARGE.placed(rareOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(-30), VerticalAnchor.absolute(50)))))
        val ORE_RARE_EARTH_BURIED = register("ore_rare_earth_buried", OreFeatures.ORE_RARE_EARTH_BURIED.placed(commonOrePlacement(15, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-100), VerticalAnchor.aboveBottom(100)))))
        val ORE_COBALT = register("ore_cobalt", OreFeatures.ORE_COBALT_SMALL.placed(commonOrePlacement(5, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(15)))))
        val ORE_COBALT_EXTRA = register("ore_cobalt_extra", OreFeatures.ORE_COBALT_LARGE.placed(rareOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.absolute(70)))))
        val ORE_URANIUM_NETHER = register("ore_uranium_nether", OreFeatures.ORE_NETHER_URANIUM.placed(commonOrePlacement(8, PlacementUtils.RANGE_10_10)))
        val ORE_PLUTONIUM_NETHER = register("ore_plutonium_nether", OreFeatures.ORE_NETHER_PLUTONIUM.placed(commonOrePlacement(6, PlacementUtils.RANGE_10_10)))
        val ORE_TUNGSTEN_NETHER = register("ore_tungsten_nether", OreFeatures.ORE_NETHER_TUNGSTEN.placed(commonOrePlacement(10, PlacementUtils.RANGE_10_10)))
        val ORE_TUNGSTEN_DELTAS = register("ore_tungsten_deltas", OreFeatures.ORE_NETHER_TUNGSTEN.placed(commonOrePlacement(10, PlacementUtils.RANGE_10_10)))
        val ORE_SULFUR_NETHER = register("ore_sulfur_nether", OreFeatures.ORE_NETHER_SULFUR.placed(commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(10), VerticalAnchor.aboveBottom(74)))))
        val ORE_PHOSPHORUS_NETHER = register("ore_phosphorus_nether", OreFeatures.ORE_NETHER_PHOSPHORUS.placed(commonOrePlacement(16, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(54), VerticalAnchor.belowTop(10)))))
        val ORE_TRIXITE_END = register("ore_trixite_end", OreFeatures.ORE_END_TRIXITE.placed(commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(16), VerticalAnchor.absolute(80)))))

        val OIL_BUBBLE = register("oil_bubble", OreFeatures.OIL_BUBBLE.placed(rareOrePlacement(25, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))))

        fun orePlacement(first: PlacementModifier, second: PlacementModifier) = listOf(first, InSquarePlacement.spread(), second, BiomeFilter.biome())
        fun commonOrePlacement(count: Int, modifier: PlacementModifier) = orePlacement(CountPlacement.of(count), modifier)
        fun rareOrePlacement(rarity: Int, modifier: PlacementModifier) = orePlacement(RarityFilter.onAverageOnceEvery(rarity), modifier)
    }


    object TreeFeatures {
        val HUGE_GLOWING_MUSHROOM = register("huge_glowing_mushroom", Features.HUGE_GLOWING_MUSHROOM.get().configured(HugeMushroomFeatureConfiguration(BlockStateProvider.simple(ModBlocks.glowingMushroomBlock.get()), BlockStateProvider.simple(ModBlocks.glowingMushroomStem.get().defaultBlockState().setValue(HugeMushroomBlock.UP, false).setValue(HugeMushroomBlock.DOWN, false)), 4)))
    }

    private fun <FC : FeatureConfiguration> register(name: String, feature: ConfiguredFeature<FC, *>): ConfiguredFeature<FC, *> = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ntm(name), feature)
    private fun register(name: String, feature: PlacedFeature): PlacedFeature = Registry.register(BuiltinRegistries.PLACED_FEATURE, ntm(name), feature)
}
