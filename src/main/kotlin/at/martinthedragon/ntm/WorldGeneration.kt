package at.martinthedragon.ntm

import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.WorldGenRegistries
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.BiomeGenerationSettings
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.placement.DepthAverageConfig
import net.minecraft.world.gen.placement.Placement
import net.minecraftforge.fml.common.ObfuscationReflectionHelper
import java.util.function.Supplier


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

    // they changed world generation. i will not try anything because everything is weird. TODO make proper ore generation once it's not weird anymore, maybe something with lambda expressions
    fun registerOreGeneration() { // thanks: https://forums.minecraftforge.net/topic/90560-1162-how-to-add-custom-ore-to-world-generation
        // Do not generate ores if they have been turned off in the config
        if (Config.getConfigValue(listOf(Config.catWorldGen, Config.catOreGen, "generateOres")))
            for (ore in ores) {
                Registry.register(
                        WorldGenRegistries.field_243653_e /* Feature Registering */,
                        ore.registryName!! /* Resource Location */,
                        Feature.field_236289_V_ /* no_surface_ore */.withConfiguration(
                                OreFeatureConfig(
                                        OreFeatureConfig.FillerBlockType.field_241882_a /* base_stone_overworld */,
                                        ore.defaultState,
                                        64
                                )
                        ).withPlacement(Placement.field_242910_o /* depth */.configure(
                                DepthAverageConfig(12, 12)
                        )).func_242728_a() /* spreadHorizontally */.func_242731_b(1) /* repeat */
                )


                @Suppress("DEPRECATION")
                for ((_, biome) in WorldGenRegistries.field_243657_i.func_239659_c_() /* Collection of Biome Entries */) {
                    if (biome.category != Biome.Category.NETHER && biome.category != Biome.Category.THEEND) {
                        val decoration = GenerationStage.Decoration.UNDERGROUND_ORES
                        val biomeFeatures = ArrayList(biome.func_242440_e().func_242498_c()) /* List of Configured Features */

                        while (biomeFeatures.size <= decoration.ordinal)
                            biomeFeatures.add(ArrayList())


                        // have to do this cause the list is immutable
                        val features = ArrayList(biomeFeatures[decoration.ordinal])
                        features += Supplier { WorldGenRegistries.field_243653_e.getOrDefault(ore.registryName) }
                        biomeFeatures[decoration.ordinal] = features

                        // i can't believe how stupid that is
                        /* Change field_242484_f that contains the Configured Features of the Biome*/
                        ObfuscationReflectionHelper.setPrivateValue(BiomeGenerationSettings::class.java, biome.func_242440_e(), biomeFeatures, "field_242484_f")
                    }
                }
            }
    }

    data class OreGenerationSettings(val yStart: Int, val yEnd: Int, val countVeins: Int, val sizeVein: Int, val biomeBlackList: List<String>)
}
