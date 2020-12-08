package at.martinthedragon.ntm

import at.martinthedragon.ntm.WorldGeneration.OreGenerationSettings
import at.martinthedragon.ntm.WorldGeneration.netherBiomes
import com.electronwill.nightconfig.core.file.CommentedFileConfig
import com.electronwill.nightconfig.core.io.ParsingMode
import com.electronwill.nightconfig.core.io.WritingMode
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.loading.FMLPaths
import net.minecraftforge.registries.ForgeRegistries
import java.lang.ClassCastException

object Config {
    const val catWorldGen = "worldgen"
    const val catOreGen = "oregen"

    val commonBuilder = ForgeConfigSpec.Builder()
    lateinit var config: ForgeConfigSpec
    private lateinit var configData: CommentedFileConfig

    var defaultOreGenSettings = mutableMapOf(
        "uranium_ore" to OreGenerationSettings(0, 25, 6, 5, netherBiomes),
        "thorium_ore" to OreGenerationSettings(0, 30, 7, 5, netherBiomes),
        "titanium_ore" to OreGenerationSettings(0, 35, 8, 6, netherBiomes),
        "sulfur_ore" to OreGenerationSettings(0, 35, 5, 8, netherBiomes),
        "niter_ore" to OreGenerationSettings(0, 35, 6, 6, netherBiomes),
        "copper_ore" to OreGenerationSettings(0, 50, 12, 6, netherBiomes),
        "tungsten_ore" to OreGenerationSettings(0, 35, 10, 8, netherBiomes),
        "aluminium_ore" to OreGenerationSettings(0, 45, 7, 6, netherBiomes),
        "fluorite_ore" to OreGenerationSettings(0, 40, 6, 4, netherBiomes),
        "beryllium_ore" to OreGenerationSettings(0, 35, 6, 4, netherBiomes),
        "lead_ore" to OreGenerationSettings(0, 35, 6, 9, netherBiomes),
        "lignite_ore" to OreGenerationSettings(35, 60, 2, 24, netherBiomes),
        "australian_ore" to OreGenerationSettings(15, 30, 2, 4, netherBiomes),
        "weidite" to OreGenerationSettings(0, 25, 2, 4, netherBiomes),
        "reiite" to OreGenerationSettings(0, 35, 2, 4, netherBiomes),
        "brightblende_ore" to OreGenerationSettings(0, 128, 2, 4, netherBiomes),
        "dellite" to OreGenerationSettings(0, 10, 2, 4, netherBiomes),
        "dollar_green_mineral" to OreGenerationSettings(25, 50, 2, 4, netherBiomes),
        "rare_earth_ore" to OreGenerationSettings(0, 25, 6, 5, netherBiomes)
    ).withDefault { OreGenerationSettings(0, 50, 5, 5) }

    var defaultNetherOreGenSettings = mutableMapOf(
        "nether_uranium_ore" to OreGenerationSettings(0, 127, 8, 6),
        "nether_plutonium_ore" to OreGenerationSettings(0, 127, 6, 4),
        "nether_tungsten_ore" to OreGenerationSettings(0, 127, 10, 10),
        "nether_sulfur_ore" to OreGenerationSettings(0, 127, 26, 12),
        "nether_phosphorus_ore" to OreGenerationSettings(0, 127, 24, 3)
    ).withDefault { OreGenerationSettings(0, 127, 5, 5) }

    var defaultEndOreGenSettings = mutableMapOf(
        "trixite" to OreGenerationSettings(0, 127, 8, 6)
    )

    fun createConfig() {
        commonBuilder.comment("World generation settings.", "All of these will only be used when a new world gets generated").push(catWorldGen)
        commonBuilder.comment("Ore generation settings").push(catOreGen)

        commonBuilder.comment("If ores of this mod should be generated at all").define("generateOres", true)

        for (ore in WorldGeneration.ores) {
            commonBuilder.comment("Ore generation settings for ${ore.registryName}").push(ore.registryName!!.path)

            defaultOreGenSettings.getValue(ore.registryName!!.path).let { default ->
                // oreGenSettings[ore.registryName!!.path] = WorldGeneration.OreGenerationSettings(
                        commonBuilder.comment("At which y-value the ore starts appearing", "Default: ${default.yStart}")
                                .define("yStart", default.yStart)//.get(),
                        commonBuilder.comment("At which y-value the ore stops appearing", "Default: ${default.yEnd}")
                                .define("yEnd", default.yEnd)//.get(),
                        commonBuilder.comment("How many veins in a chunk get generated", "Default: ${default.countVeins}")
                                .define("countVeins", default.countVeins)//.get(),
                        commonBuilder.comment("How many ores a vein has on average", "Default: ${default.sizeVein}")
                                .define("sizeVein", default.sizeVein)//.get(),
                        commonBuilder.comment(
                                "In which biomes this ore should not generate",
                                "Prefix a biome name with '!' to let it only generate in that biome",
                                "Default: ${default.biomeBlackList}"
                        ).defineList("biomeBlackList", default.biomeBlackList) {
                            it is String && ForgeRegistries.BIOMES.containsKey(ResourceLocation(it.removePrefix("!")))
                        }//.get()
                //)
            }
            commonBuilder.pop()
        }

        commonBuilder.pop(2)
        config = commonBuilder.build()

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, config)
        configData = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve("ntm-common.toml"))
                .sync()
                .autosave()
                .autoreload()
                .writingMode(WritingMode.REPLACE)
                .parsingMode(ParsingMode.REPLACE)
                .build()
        configData.load()
        config.setConfig(configData)
    }

    inline fun <reified T> getConfigValue(path: List<String>): T {
        return try {
            @Suppress("UNCHECKED_CAST")
            config.spec.get<ForgeConfigSpec.ValueSpec>(path).default as T
        } catch (ex: ClassCastException) {
            throw kotlin.IllegalArgumentException("Config value at ${path.joinToString(".")} was not of type ${T::class.simpleName}")
        }
    }
}
