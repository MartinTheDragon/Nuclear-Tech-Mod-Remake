package at.martinthedragon.ntm.worldgen

import at.martinthedragon.ntm.blocks.ModBlocks
import at.martinthedragon.ntm.blocks.advancedblocks.CustomizedBlock
import at.martinthedragon.ntm.lib.Config
import at.martinthedragon.ntm.lib.MODID
import net.minecraft.block.Blocks
import net.minecraft.util.ResourceLocation
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.Biomes
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.feature.ReplaceBlockConfig
import net.minecraft.world.gen.placement.CountRangeConfig
import net.minecraft.world.gen.placement.Placement
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.registries.ForgeRegistries


@Suppress("unused", "UNUSED_PARAMETER")
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object WorldGeneration {
    @JvmStatic
    @SubscribeEvent
    fun generateOres(event: FMLCommonSetupEvent) {
        if (!Config.generateOres.get()) return

        for (biome in ForgeRegistries.BIOMES.values) {
            for (block in ModBlocks.oreList) {
                if (block.registryName in Config.oreGenerationBlacklist.get()) continue

                val biomeBlacklist = emptyList<Biome>().toMutableList()
                val configs = Config.getOreGenerationConfigs(block) ?: continue
                configLoop@for (s in configs.biomeBlacklist) {
                    if (s.first() == '!') {
                        biomeBlacklist.addAll(ForgeRegistries.BIOMES.values)
                        configs.biomeBlacklist.forEach {
                            if (it == "!THEEND")
                                biomeBlacklist.removeAll(listOf(
                                        ForgeRegistries.BIOMES.getValue(ResourceLocation("minecraft:the_end")),
                                        ForgeRegistries.BIOMES.getValue(ResourceLocation("minecraft:small_end_islands")),
                                        ForgeRegistries.BIOMES.getValue(ResourceLocation("minecraft:end_midlands")),
                                        ForgeRegistries.BIOMES.getValue(ResourceLocation("minecraft:end_highlands")),
                                        ForgeRegistries.BIOMES.getValue(ResourceLocation("minecraft:end_barrens"))
                                ))
                            else
                                biomeBlacklist.remove(ForgeRegistries.BIOMES.getValue(ResourceLocation(it.drop(1))))
                        }
                        break@configLoop
                    } else {
                        if (s == "THEEND")
                            biomeBlacklist.addAll(listOf(
                                    ForgeRegistries.BIOMES.getValue(ResourceLocation("minecraft:the_end"))!!,
                                    ForgeRegistries.BIOMES.getValue(ResourceLocation("minecraft:small_end_islands"))!!,
                                    ForgeRegistries.BIOMES.getValue(ResourceLocation("minecraft:end_midlands"))!!,
                                    ForgeRegistries.BIOMES.getValue(ResourceLocation("minecraft:end_highlands"))!!,
                                    ForgeRegistries.BIOMES.getValue(ResourceLocation("minecraft:end_barrens"))!!
                            ))
                        else {
                            val currentValue = ForgeRegistries.BIOMES.getValue(ResourceLocation(s))
                            if (currentValue != null) biomeBlacklist.add(currentValue)
                        }
                    }
                }

                if (biome in biomeBlacklist) continue

                val fillerBlockType = if (biome == ForgeRegistries.BIOMES.getValue(ResourceLocation("minecraft", "nether")))
                    OreFeatureConfig.FillerBlockType.NETHERRACK else OreFeatureConfig.FillerBlockType.NATURAL_STONE

                if (biome.category == Biome.Category.THEEND) {
                    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                            Biome.createDecoratedFeature(
                                    Feature.EMERALD_ORE,
                                    ReplaceBlockConfig(Blocks.END_STONE.defaultState, block.defaultState),
                                    Placement.COUNT_RANGE,
                                    CountRangeConfig(
                                            configs.count,
                                            configs.bottomOffset,
                                            configs.topOffset,
                                            configs.maximum
                                    )
                            ))
                    continue
                }

                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                        Biome.createDecoratedFeature(
                                Feature.ORE,
                                OreFeatureConfig(
                                        fillerBlockType,
                                        block.defaultState,
                                        configs.size
                                ),
                                Placement.COUNT_RANGE,
                                CountRangeConfig(
                                        configs.count,
                                        configs.bottomOffset,
                                        configs.topOffset,
                                        configs.maximum)
                        ))
            }
        }
    }
}

data class OreGenerationSettings(val biomeBlacklist: List<String>, val size: Int, val count: Int, val bottomOffset: Int, val topOffset: Int, val maximum: Int) {
    constructor(biomeBlacklist: List<String>, size: Int, count: Int, spawnRange: IntRange) : this(biomeBlacklist, size, count, spawnRange.first, 0, spawnRange.last - spawnRange.first)
}