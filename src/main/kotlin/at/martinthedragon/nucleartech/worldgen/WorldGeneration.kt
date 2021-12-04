package at.martinthedragon.nucleartech.worldgen

//import at.martinthedragon.nucleartech.ModBlocks
//import at.martinthedragon.nucleartech.NuclearTech
//import at.martinthedragon.nucleartech.RegistriesAndLifecycle.FEATURES
//import at.martinthedragon.nucleartech.worldgen.features.HugeGlowingMushroomFeature
//import at.martinthedragon.nucleartech.worldgen.features.OilBubbleFeature
//import net.minecraft.core.Registry
//import net.minecraft.data.BuiltinRegistries
//import net.minecraft.data.worldgen.features.OreFeatures
//import net.minecraft.resources.ResourceLocation
//import net.minecraft.world.level.biome.Biome
//import net.minecraft.world.level.biome.BiomeGenerationSettings
//import net.minecraft.world.level.biome.Biomes
//import net.minecraft.world.level.block.Blocks
//import net.minecraft.world.level.block.HugeMushroomBlock
//import net.minecraft.world.level.levelgen.GenerationStep
//import net.minecraft.world.level.levelgen.VerticalAnchor
//import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
//import net.minecraft.world.level.levelgen.feature.Feature
//import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration
//import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration
//import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration
//import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration
//import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider
//import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest
//import net.minecraftforge.event.world.BiomeLoadingEvent
//import net.minecraftforge.eventbus.api.EventPriority
//import net.minecraftforge.eventbus.api.SubscribeEvent
//import net.minecraftforge.fml.common.Mod
//import net.minecraftforge.fmllegacy.RegistryObject
//
//@Suppress("unused")
//@Mod.EventBusSubscriber(modid = NuclearTech.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
//object WorldGeneration {
//    object Features {
//        val OIL_BUBBLE: RegistryObject<OilBubbleFeature> = FEATURES.register("oil_bubble") { OilBubbleFeature(NoneFeatureConfiguration.CODEC) }
//        val HUGE_GLOWING_MUSHROOM: RegistryObject<HugeGlowingMushroomFeature> = FEATURES.register("huge_glowing_mushroom") { HugeGlowingMushroomFeature(HugeMushroomFeatureConfiguration.CODEC) }
//    }
//
//    object ConfiguredFeatures {
//        val ORE_THORIUM = register("ore_thorium", Feature.ORE.configured(OreConfiguration(OreFeatures.NATURAL_STONE, ModBlocks.thoriumOre.get().defaultBlockState(), 5)).placed(VerticalAnchor.bottom(), VerticalAnchor.absolute(30)).squared().count(7))
//        val ORE_URANIUM = register("ore_uranium", Feature.ORE.configured(OreConfiguration(OreFeatures.NATURAL_STONE, ModBlocks.uraniumOre.get().defaultBlockState(), 5)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(25)).squared().count(6))
//        val ORE_TITANIUM = register("ore_titanium", Feature.ORE.configured(OreConfiguration(OreFeatures.NATURAL_STONE, ModBlocks.titaniumOre.get().defaultBlockState(), 6)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(35)).squared().count(8))
//        val ORE_SULFUR = register("ore_sulfur", Feature.ORE.configured(OreConfiguration(OreFeatures.NATURAL_STONE, ModBlocks.sulfurOre.get().defaultBlockState(), 8)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(35)).squared().count(5))
//        val ORE_NITER = register("ore_niter", Feature.ORE.configured(OreConfiguration(OreFeatures.NATURAL_STONE, ModBlocks.niterOre.get().defaultBlockState(), 6)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(35)).squared().count(6))
//        val ORE_COPPER = register("ore_copper", Feature.ORE.configured(OreConfiguration(OreFeatures.NATURAL_STONE, ModBlocks.copperOre.get().defaultBlockState(), 6)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(50)).squared().count(12))
//        val ORE_TUNGSTEN = register("ore_tungsten", Feature.ORE.configured(OreConfiguration(OreFeatures.NATURAL_STONE, ModBlocks.tungstenOre.get().defaultBlockState(), 8)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(35)).squared().count(10))
//        val ORE_ALUMINIUM = register("ore_aluminium", Feature.ORE.configured(OreConfiguration(OreFeatures.NATURAL_STONE, ModBlocks.aluminiumOre.get().defaultBlockState(), 6)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(45)).squared().count(7))
//        val ORE_FLUORITE = register("ore_fluorite", Feature.ORE.configured(OreConfiguration(OreFeatures.NATURAL_STONE, ModBlocks.fluoriteOre.get().defaultBlockState(), 4)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(40)).squared().count(6))
//        val ORE_BERYLLIUM = register("ore_beryllium", Feature.ORE.configured(OreConfiguration(OreFeatures.NATURAL_STONE, ModBlocks.berylliumOre.get().defaultBlockState(), 4)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(35)).squared().count(6))
//        val ORE_LEAD = register("ore_lead", Feature.ORE.configured(OreConfiguration(OreFeatures.NATURAL_STONE, ModBlocks.leadOre.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(35)).squared().count(6))
//        val ORE_LIGNITE = register("ore_lignite", Feature.ORE.configured(OreConfiguration(OreFeatures.NATURAL_STONE, ModBlocks.ligniteOre.get().defaultBlockState(), 24)).rangeUniform(VerticalAnchor.absolute(35), VerticalAnchor.absolute(60)).squared().count(2))
//        val ORE_AUSTRALIUM = register("ore_australium", Feature.ORE.configured(OreConfiguration(OreFeatures.NATURAL_STONE, ModBlocks.australianOre.get().defaultBlockState(), 4)).rangeUniform(VerticalAnchor.absolute(15), VerticalAnchor.absolute(30)).squared().count(2))
//        val ORE_WEIDANIUM = register("ore_weidanium", Feature.ORE.configured(OreConfiguration(OreFeatures.NATURAL_STONE, ModBlocks.weidite.get().defaultBlockState(), 4)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(25)).squared().count(2))
//        val ORE_REIIUM = register("ore_reiium", Feature.ORE.configured(OreConfiguration(OreFeatures.NATURAL_STONE, ModBlocks.reiite.get().defaultBlockState(), 4)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(35)).squared().count(2))
//        val ORE_UNOBTAINIUM = register("ore_unobtainium", Feature.ORE.configured(OreConfiguration(OreFeatures.NATURAL_STONE, ModBlocks.brightblendeOre.get().defaultBlockState(), 4)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(128)).squared().count(2))
//        val ORE_DAFFERGON = register("ore_daffergon", Feature.ORE.configured(OreConfiguration(OreFeatures.NATURAL_STONE, ModBlocks.dellite.get().defaultBlockState(), 4)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(10)).squared().count(2))
//        val ORE_VERTICIUM = register("ore_verticium", Feature.ORE.configured(OreConfiguration(OreFeatures.NATURAL_STONE, ModBlocks.dollarGreenMineral.get().defaultBlockState(), 4)).rangeUniform(VerticalAnchor.absolute(25), VerticalAnchor.absolute(50)).squared().count(2))
//        val ORE_RARE_EARTH = register("ore_rare_earth", Feature.ORE.configured(OreConfiguration(OreFeatures.NATURAL_STONE, ModBlocks.rareEarthOre.get().defaultBlockState(), 5)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(25)).squared().count(6))
//        val ORE_URANIUM_NETHER = register("ore_uranium_nether", Feature.ORE.configured(OreConfiguration(OreFeatures.NETHERRACK, ModBlocks.netherUraniumOre.get().defaultBlockState(), 6)).range(net.minecraft.data.worldgen.Features.Decorators.RANGE_10_10).squared().count(8))
//        val ORE_PLUTONIUM_NETHER = register("ore_plutonium_nether", Feature.ORE.configured(OreConfiguration(OreFeatures.NETHERRACK, ModBlocks.netherPlutoniumOre.get().defaultBlockState(), 4)).range(net.minecraft.data.worldgen.Features.Decorators.RANGE_10_10).squared().count(6))
//        val ORE_TUNGSTEN_NETHER = register("ore_tungsten_nether", Feature.ORE.configured(OreConfiguration(OreFeatures.NETHERRACK, ModBlocks.netherTungstenOre.get().defaultBlockState(), 10)).range(net.minecraft.data.worldgen.Features.Decorators.RANGE_10_10).squared().count(10))
//        val ORE_SULFUR_NETHER = register("ore_sulfur_nether", Feature.ORE.configured(OreConfiguration(OreFeatures.NETHERRACK, ModBlocks.netherSulfurOre.get().defaultBlockState(), 12)).range(net.minecraft.data.worldgen.Features.Decorators.RANGE_10_10).squared().count(26))
//        val ORE_PHOSPHORUS_NETHER = register("ore_phosphorus_nether", Feature.ORE.configured(OreConfiguration(OreFeatures.NETHERRACK, ModBlocks.netherPhosphorusOre.get().defaultBlockState(), 3)).range(net.minecraft.data.worldgen.Features.Decorators.RANGE_10_10).squared().count(24))
//        val ORE_TRIXITE_END = register("ore_trixite_end", Feature.ORE.configured(OreConfiguration(CustomRuleTests.END_STONE, ModBlocks.trixite.get().defaultBlockState(), 6)).range(net.minecraft.data.worldgen.Features.Decorators.RANGE_10_10).squared().count(8))
//
//        val OIL_BUBBLE = register("oil_bubble", Features.OIL_BUBBLE.get().configured(FeatureConfiguration.NONE).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(25)).squared().rarity(25))
//
//        val HUGE_GLOWING_MUSHROOM = register("huge_glowing_mushroom", Features.HUGE_GLOWING_MUSHROOM.get().configured(HugeMushroomFeatureConfiguration(SimpleStateProvider(ModBlocks.glowingMushroomBlock.get().defaultBlockState()), SimpleStateProvider(ModBlocks.glowingMushroomStem.get().defaultBlockState().setValue(HugeMushroomBlock.UP, false).setValue(HugeMushroomBlock.DOWN, false)), 4)))
//
//        private fun <FC : FeatureConfiguration?> register(name: String, configuredFeature: ConfiguredFeature<FC, *>): ConfiguredFeature<FC, *> =
//            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ResourceLocation(NuclearTech.MODID, name), configuredFeature)
//    }
//
//    object CustomRuleTests {
//        val END_STONE = BlockMatchTest(Blocks.END_STONE)
//    }
//
//    @SubscribeEvent(priority = EventPriority.HIGH)
//    @JvmStatic
//    fun addBiomeFeaturesEvent(event: BiomeLoadingEvent) {
//        addOreFeatures(event)
//    }
//
//    private fun addOreFeatures(event: BiomeLoadingEvent) {
//        when (event.category) {
//            Biome.BiomeCategory.NETHER -> addNetherOres(event.generation)
//            Biome.BiomeCategory.THEEND -> addEndOres(event.generation)
//            else -> {
//                if (event.name == Biomes.THE_END.location()) return
//
//                addDefaultOres(event.generation)
//
//                if (event.category != Biome.BiomeCategory.EXTREME_HILLS &&
//                    event.category != Biome.BiomeCategory.OCEAN &&
//                    event.category != Biome.BiomeCategory.DESERT
//                ) {
//                    addLignite(event.generation)
//                }
//            }
//        }
//    }
//
//    private fun addDefaultOres(builder: BiomeGenerationSettings.Builder) {
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_URANIUM)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_THORIUM)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_TITANIUM)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_SULFUR)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_NITER)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_COPPER)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_TUNGSTEN)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_ALUMINIUM)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_FLUORITE)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_BERYLLIUM)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_LEAD)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_AUSTRALIUM)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_WEIDANIUM)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_REIIUM)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_UNOBTAINIUM)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_DAFFERGON)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_VERTICIUM)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_RARE_EARTH)
//
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.OIL_BUBBLE)
//    }
//
//    private fun addLignite(builder: BiomeGenerationSettings.Builder) {
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_LIGNITE)
//    }
//
//    private fun addNetherOres(builder: BiomeGenerationSettings.Builder) {
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_URANIUM_NETHER)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_PLUTONIUM_NETHER)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_TUNGSTEN_NETHER)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_SULFUR_NETHER)
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_PHOSPHORUS_NETHER)
//    }
//
//    private fun addEndOres(builder: BiomeGenerationSettings.Builder) {
//        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ConfiguredFeatures.ORE_TRIXITE_END)
//    }
//}
