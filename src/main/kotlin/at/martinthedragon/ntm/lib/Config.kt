package at.martinthedragon.ntm.lib

import at.martinthedragon.ntm.blocks.ModBlocks
import at.martinthedragon.ntm.blocks.advancedblocks.CustomizedBlock
import at.martinthedragon.ntm.blocks.advancedblocks.OreBlock
import at.martinthedragon.ntm.main.NTM
import at.martinthedragon.ntm.worldgen.OreGenerationSettings
import com.electronwill.nightconfig.core.file.CommentedFileConfig
import com.electronwill.nightconfig.core.io.WritingMode
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.loading.FMLPaths
import net.minecraftforge.registries.ForgeRegistries
import java.nio.file.Path

// IMPORTANT: Do not access Config before all ForgeRegistries have been registered!
object Config {
    private const val categoryWorldGeneration = "worldgen"
    private const val categoryOreGeneration = "oregen"

    private val commonBuilder = ForgeConfigSpec.Builder()
    private val clientBuilder = ForgeConfigSpec.Builder()

    lateinit var commonConfig: ForgeConfigSpec

    lateinit var generateOres: ForgeConfigSpec.BooleanValue
    lateinit var oreGenerationBlacklist: ForgeConfigSpec.ConfigValue<List<String>>
    private val oreGenerationConfigs = emptyMap<CustomizedBlock, OreGenSettingsWrapper>().toMutableMap()

    fun generateWorldGenerationConfigs() {
        NTM.logger.debug("Creating world generation configs...")
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
                    for (blockName in ModBlocks.ores) if(blockName == it as String) { matchFound = true; break }
                    matchFound
                }

        makeForEachOreConfig()

        commonBuilder.pop(2)

        commonConfig = commonBuilder.build()
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, commonConfig)
        loadConfig(commonConfig, FMLPaths.CONFIGDIR.get().resolve("ntm-common.toml"))
        NTM.logger.debug("Configs created.")
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
        for (blockName in ModBlocks.ores) {
            val block = ForgeRegistries.BLOCKS.getValue(ResourceLocation(MODID, blockName)) as OreBlock?
            if (block == null) {
                NTM.logger.fatal("Could not get block $blockName for ore config initialization. Config object class might have been accessed before blocks were registered.")
                continue
            }

            commonBuilder.comment("The generation settings for ${block.registryName}").push(block.registryName)
            val biomeBlacklist: ForgeConfigSpec.ConfigValue<List<String>> = commonBuilder.comment(
                    "In which biomes this ore should not generate.",
                    "A list of string values.",
                    "Append a value with an exclamation mark and the ore will generate only in the specified biome.",
                    "If one value is appended by an exclamation mark all the other values must too.",
                    "Needs restart to take effect.")
                    .defineList("biomeBlacklist", block.oreGenerationSettings.biomeBlacklist) {
                        if (it == "THEEND" || it == "!THEEND")
                            true
                        else if ((it as String).first() == '!')
                            ForgeRegistries.BIOMES.containsKey(ResourceLocation(it.drop(1)))
                        else
                            ForgeRegistries.BIOMES.containsKey(ResourceLocation(it))
                    }
            val size: ForgeConfigSpec.ConfigValue<Int> = commonBuilder.comment(
                    "The average amount of ore in a vein.",
                    "Default: ${block.oreGenerationSettings.size}",
                    "Needs restart to take effect.")
                    .define("size", block.oreGenerationSettings.size)
            val count: ForgeConfigSpec.ConfigValue<Int> = commonBuilder.comment(
                    "How many veins spawn in a chunk.",
                    "Default: ${block.oreGenerationSettings.count}",
                    "Needs restart to take effect.")
                    .define("count", block.oreGenerationSettings.count)
            val bottomOffset: ForgeConfigSpec.ConfigValue<Int> = commonBuilder.comment(
                    "The y-axis' value at which the ore starts to generate.",
                    "Default: ${block.oreGenerationSettings.bottomOffset}",
                    "Needs restart to take effect.")
                    .define("bottomOffset", block.oreGenerationSettings.bottomOffset)
            val topOffset: ForgeConfigSpec.ConfigValue<Int> = commonBuilder.comment(
                    "The offset of the 'maximum' value.",
                    "This is actually completely useless, so why should you use it?",
                    "Default: ${block.oreGenerationSettings.topOffset}",
                    "Needs restart to take effect.")
                    .define("topOffset", block.oreGenerationSettings.topOffset)
            val maximum: ForgeConfigSpec.ConfigValue<Int> = commonBuilder.comment(
                    "This value plus the 'bottomOffset' is the y-axis' value at which the ore stops to generate.",
                    "Default: ${block.oreGenerationSettings.maximum}",
                    "Needs restart to take effect.")
                    .define("maximum", block.oreGenerationSettings.maximum)
            commonBuilder.pop()

            oreGenerationConfigs[block] = OreGenSettingsWrapper(biomeBlacklist, size, count, bottomOffset, topOffset, maximum)
        }
    }

    fun getOreGenerationConfigs(block: OreBlock): OreGenerationSettings?  {
        val blacklist = oreGenerationConfigs[block]?.biomeBlacklist
        val size = oreGenerationConfigs[block]?.size
        val count = oreGenerationConfigs[block]?.count
        val bottomOffset = oreGenerationConfigs[block]?.bottomOffset
        val topOffset = oreGenerationConfigs[block]?.topOffset
        val maximum = oreGenerationConfigs[block]?.maximum
        if (blacklist != null && size != null && count != null && bottomOffset != null && topOffset != null && maximum != null)
            return OreGenerationSettings(size.get(), count.get(), bottomOffset.get(), topOffset.get(), maximum.get(), blacklist.get())
        return null
    }
}

data class OreGenSettingsWrapper(val biomeBlacklist: ForgeConfigSpec.ConfigValue<List<String>>,
                                 val size: ForgeConfigSpec.ConfigValue<Int>,
                                 val count: ForgeConfigSpec.ConfigValue<Int>,
                                 val bottomOffset: ForgeConfigSpec.ConfigValue<Int>,
                                 val topOffset: ForgeConfigSpec.ConfigValue<Int>,
                                 val maximum: ForgeConfigSpec.ConfigValue<Int>)
