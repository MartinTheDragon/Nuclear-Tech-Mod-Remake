package at.martinthedragon.ntm.lib

// import net.minecraftforge.fml.common.Mod
import at.martinthedragon.ntm.blocks.ModBlocks
import at.martinthedragon.ntm.blocks.advancedblocks.CustomizedBlock
import at.martinthedragon.ntm.main.Main
import at.martinthedragon.ntm.worldgen.OreGenerationSettings
import com.electronwill.nightconfig.core.file.CommentedFileConfig
import com.electronwill.nightconfig.core.io.WritingMode
import net.minecraft.block.Block
import net.minecraft.util.ResourceLocation
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.loading.FMLPaths
import net.minecraftforge.registries.ForgeRegistries
import java.nio.file.Path

// @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object Config {
    val categoryWorldGeneration = "worldgen"
    val categoryOreGeneration = "oregen"

    private val commonBuilder = ForgeConfigSpec.Builder()
    private val clientBuilder = ForgeConfigSpec.Builder()

    val commonConfig: ForgeConfigSpec

    val generateOres: ForgeConfigSpec.BooleanValue
    val oreGenerationBlacklist: ForgeConfigSpec.ConfigValue<List<String>>
    private val oreGenerationConfigs = emptyMap<CustomizedBlock, OreGenSettingsWrapper>().toMutableMap()

    init {
        Main.LOGGER.debug("Creating configs...")
        commonBuilder.comment("World generation settings.", "All of these will only be used when a new world gets generated.").push(categoryWorldGeneration)
        commonBuilder.comment("Ore generation settings.").push(categoryOreGeneration)
        generateOres = commonBuilder.comment(
                "If ores of this mod should be generated at all.",
                "Needs restart to take effect.")
                .define("generateOres", true)
        oreGenerationBlacklist = commonBuilder.comment(
                "A list of blocks that should be excluded from world generation.",
                "Use the registry name of an ore without the modid.",
                "A valid value for this setting would be [\"uranium_ore\"]",
                "Needs restart to take effect.")
                .defineList("oreGenerationBlacklist", listOf("nether_plutonium_ore")) {
                    var matchFound = false
                    for (block in ModBlocks.oreList) if(block.registryName == it as String) { matchFound = true; break }
                    matchFound
                }

        makeForEachOreConfig()

        commonBuilder.pop(2)

        commonConfig = commonBuilder.build()
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, commonConfig)
        loadConfig(commonConfig, FMLPaths.CONFIGDIR.get().resolve("ntm-common.toml"))
        Main.LOGGER.debug("Configs created.")
    }

    fun loadConfig(spec: ForgeConfigSpec, path: Path) {
        val configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build()
        configData.load()
        spec.setConfig(configData)
    }

    private fun makeForEachOreConfig() {
        for (block in ModBlocks.oreList) {
            commonBuilder.comment("The generation settings for ${block.registryName}").push(block.registryName)
            val biomeBlacklist: ForgeConfigSpec.ConfigValue<List<String>> = commonBuilder.comment(
                    "In which biomes this ore should not generate.",
                    "A list of string values.",
                    "Append a value with an exclamation mark and the ore will generate only in the specified biome.",
                    "If one value is appended by an exclamation mark all the other values must too.",
                    "Needs restart to take effect.")
                    .defineList("biomeBlacklist", block.customProperties.defaultOreGenerationSettings!!.biomeBlacklist) {
                        if (it == "THEEND" || it == "!THEEND")
                            true
                        else if ((it as String).first() == '!')
                            ForgeRegistries.BIOMES.containsKey(ResourceLocation(it.drop(1)))
                        else
                            ForgeRegistries.BIOMES.containsKey(ResourceLocation(it))
                    }
            val size: ForgeConfigSpec.ConfigValue<Int> = commonBuilder.comment(
                    "The average amount of ore in a vein.",
                    "Default: ${block.customProperties.defaultOreGenerationSettings.size}",
                    "Needs restart to take effect.")
                    .define("size", block.customProperties.defaultOreGenerationSettings.size)
            val count: ForgeConfigSpec.ConfigValue<Int> = commonBuilder.comment(
                    "How many veins spawn in a chunk.",
                    "Default: ${block.customProperties.defaultOreGenerationSettings.count}",
                    "Needs restart to take effect.")
                    .define("count", block.customProperties.defaultOreGenerationSettings.count)
            val bottomOffset: ForgeConfigSpec.ConfigValue<Int> = commonBuilder.comment(
                    "The y-axis' value at which the ore starts to generate.",
                    "Default: ${block.customProperties.defaultOreGenerationSettings.bottomOffset}",
                    "Needs restart to take effect.")
                    .define("bottomOffset", block.customProperties.defaultOreGenerationSettings.bottomOffset)
            val topOffset: ForgeConfigSpec.ConfigValue<Int> = commonBuilder.comment(
                    "The offset of the 'maximum' value.",
                    "This is actually completely useless, so why should you use it?",
                    "Default: ${block.customProperties.defaultOreGenerationSettings.topOffset}",
                    "Needs restart to take effect.")
                    .define("topOffset", block.customProperties.defaultOreGenerationSettings.topOffset)
            val maximum: ForgeConfigSpec.ConfigValue<Int> = commonBuilder.comment(
                    "This value plus the 'bottomOffset' is the y-axis' value at which the ore stops to generate.",
                    "Default: ${block.customProperties.defaultOreGenerationSettings.maximum}",
                    "Needs restart to take effect.")
                    .define("maximum", block.customProperties.defaultOreGenerationSettings.maximum)
            commonBuilder.pop()

            oreGenerationConfigs[block] = OreGenSettingsWrapper(biomeBlacklist, size, count, bottomOffset, topOffset, maximum)
        }
    }

    fun getOreGenerationConfigs(block: CustomizedBlock): OreGenerationSettings?  {
        val blacklist = oreGenerationConfigs[block]?.biomeBlacklist
        val size = oreGenerationConfigs[block]?.size
        val count = oreGenerationConfigs[block]?.count
        val bottomOffset = oreGenerationConfigs[block]?.bottomOffset
        val topOffset = oreGenerationConfigs[block]?.topOffset
        val maximum = oreGenerationConfigs[block]?.maximum
        if (blacklist != null && size != null && count != null && bottomOffset != null && topOffset != null && maximum != null)
            return OreGenerationSettings(blacklist.get(), size.get(), count.get(), bottomOffset.get(), topOffset.get(), maximum.get())
        return null
    }
}

data class OreGenSettingsWrapper(val biomeBlacklist: ForgeConfigSpec.ConfigValue<List<String>>,
                                 val size: ForgeConfigSpec.ConfigValue<Int>,
                                 val count: ForgeConfigSpec.ConfigValue<Int>,
                                 val bottomOffset: ForgeConfigSpec.ConfigValue<Int>,
                                 val topOffset: ForgeConfigSpec.ConfigValue<Int>,
                                 val maximum: ForgeConfigSpec.ConfigValue<Int>)
