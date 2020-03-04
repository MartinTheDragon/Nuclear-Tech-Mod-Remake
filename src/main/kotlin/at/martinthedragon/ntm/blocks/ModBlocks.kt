package at.martinthedragon.ntm.blocks

import at.martinthedragon.ntm.blocks.advancedblocks.*
import at.martinthedragon.ntm.lib.MODID
import at.martinthedragon.ntm.main.NTM
import at.martinthedragon.ntm.worldgen.OreGenerationSettings
import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.item.Rarity
import net.minecraftforge.common.ToolType
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

typealias NTMBlock = CustomizedBlock
typealias NTMProp = CustomizedBlock.CustomizedProperties

@Suppress("unused")
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object ModBlocks {
    private val blockCache = emptySet<Block>().toMutableSet()
    val blocks = emptySet<String>().toMutableSet()
    val ores = emptySet<String>().toMutableSet() // used by Config and WorldGeneration

    // Ores

    val URANIUM_ORE = OreBlock("uranium_ore", NTMProp(Material.ROCK, hardnessAndResistance = 3f, harvestLevel = 2), OreGenerationSettings(5, 6, 0..25)).also { blockCache += it }
    val THORIUM_ORE = OreBlock("thorium_ore", NTMProp(Material.ROCK, hardnessAndResistance = 3f, harvestLevel = 2), OreGenerationSettings(5, 7, 0..30)).also { blockCache += it }
    val TITANIUM_ORE = OreBlock("titanium_ore", NTMProp(Material.ROCK, hardnessAndResistance = 3f, harvestLevel = 2), OreGenerationSettings(6, 8, 0..35)).also { blockCache += it }
    val SULFUR_ORE = OreBlock("sulfur_ore", NTMProp(Material.ROCK, hardness = 2f, resistance = 3f, harvestLevel = 1, xpDrop = 1..3), OreGenerationSettings(8, 5, 0..35)).also { blockCache += it }
    val NITER_ORE = OreBlock("niter_ore", NTMProp(Material.ROCK, hardness = 2f, resistance = 3f, harvestLevel = 1, xpDrop = 2..4), OreGenerationSettings(6, 6, 0..35)).also { blockCache += it }
    val COPPER_ORE = OreBlock("copper_ore", NTMProp(Material.ROCK, hardnessAndResistance = 3f, harvestLevel = 1), OreGenerationSettings(6, 12, 0..50)).also { blockCache += it }
    val TUNGSTEN_ORE = OreBlock("tungsten_ore", NTMProp(Material.ROCK, hardnessAndResistance = 3f, harvestLevel = 2), OreGenerationSettings(8, 10, 0..35)).also { blockCache += it }
    val ALUMINIUM_ORE = OreBlock("aluminium_ore", NTMProp(Material.ROCK, hardnessAndResistance = 3f, harvestLevel = 2), OreGenerationSettings(6, 7, 0..45)).also { blockCache += it }
    val FLUORITE_ORE = OreBlock("fluorite_ore", NTMProp(Material.ROCK, hardness = 2f, resistance = 3f, harvestLevel = 1, xpDrop = 2..5), OreGenerationSettings(4, 6, 0..40)).also { blockCache += it }
    val BERYLLIUM_ORE = OreBlock("beryllium_ore", NTMProp(Material.ROCK, hardnessAndResistance = 3f, harvestLevel = 2), OreGenerationSettings(4, 6, 0..35)).also { blockCache += it }
    val LEAD_ORE = OreBlock("lead_ore", NTMProp(Material.ROCK, hardnessAndResistance = 3f, harvestLevel = 2), OreGenerationSettings(9, 6, 0..35)).also { blockCache += it }
    val OIL_DEPOSIT = NTMBlock("oil_deposit", NTMProp(Material.ROCK, hardness = 1f, resistance = 2f, harvestLevel = 0)).also { blockCache += it } // TODO implement oil generation
    val EMPTY_OIL_DEPOSIT = NTMBlock("empty_oil_deposit", NTMProp(Material.ROCK, hardness = 1f, resistance = 2f, harvestLevel = 0)).also { blockCache += it }
    val TAR_SAND = NTMBlock("tar_sand", NTMProp(Material.SAND, MaterialColor.LIGHT_GRAY, hardnessAndResistance = 1f, harvestLevel = -1, harvestTool = ToolType.SHOVEL, soundType = SoundType.SAND)).also { blockCache += it } // TODO implement tar sand generation
    val LIGNITE_ORE = OreBlock("lignite_ore", NTMProp(Material.ROCK, hardnessAndResistance = 3f, harvestLevel = 0, xpDrop = 0..1), OreGenerationSettings(24, 2, 35..60)).also { blockCache += it }
    val SCHRABIDIUM_ORE = NTMBlock("schrabidium_ore", NTMProp(Material.ROCK, MaterialColor.CYAN, hardness = 20f, resistance = 50f, harvestLevel = 3, lightValue = 7, rarity = Rarity.RARE)).also { blockCache += it } // TODO replace uranium ore with schrabidium ore on nuclear explosion
    val AUSTRALIAN_ORE = OreBlock("australian_ore", NTMProp(Material.ROCK, MaterialColor.YELLOW, hardnessAndResistance = 6f, harvestLevel = 2, rarity = Rarity.UNCOMMON), OreGenerationSettings(4, 2, 15..30)).also { blockCache += it } // TODO find out how to generate special ores in a special way
    val WEIDITE = OreBlock("weidite", NTMProp(Material.ROCK, MaterialColor.ORANGE_TERRACOTTA, hardnessAndResistance = 6f, harvestLevel = 2, rarity = Rarity.UNCOMMON), OreGenerationSettings(4, 2, 0..25)).also { blockCache += it }
    val REIITE = OreBlock("reiite", NTMProp(Material.ROCK, MaterialColor.RED, hardnessAndResistance = 6f, harvestLevel = 2, rarity = Rarity.UNCOMMON), OreGenerationSettings(4, 2, 0..35)).also { blockCache += it }
    val BRIGHTBLENDE_ORE = OreBlock("brightblende_ore", NTMProp(Material.ROCK, MaterialColor.BLUE, hardnessAndResistance = 6f, harvestLevel = 2, rarity = Rarity.UNCOMMON), OreGenerationSettings(4, 2, 0..128)).also { blockCache += it }
    val DELLITE = OreBlock("dellite", NTMProp(Material.ROCK, MaterialColor.PURPLE, hardnessAndResistance = 6f, harvestLevel = 2, rarity = Rarity.UNCOMMON), OreGenerationSettings(4, 2, 0..10)).also { blockCache += it }
    val DOLLAR_GREEN_MINERAL = OreBlock("dollar_green_mineral", NTMProp(Material.ROCK, MaterialColor.GREEN, hardnessAndResistance = 6f, harvestLevel = 2, rarity = Rarity.UNCOMMON), OreGenerationSettings(4, 2, 25..50)).also { blockCache += it }
    val RARE_EARTH_ORE = OreBlock("rare_earth_ore", NTMProp(Material.ROCK, MaterialColor.NETHERRACK, hardness = 4f, resistance = 3f, harvestLevel = 2, rarity = Rarity.UNCOMMON, xpDrop = 3..6), OreGenerationSettings(5, 6, 0..25)).also { blockCache += it }
    val NETHER_URANIUM_ORE = OreBlock("nether_uranium_ore", NTMProp(Material.ROCK, MaterialColor.NETHERRACK, hardnessAndResistance = 3f, harvestLevel = 2), OreGenerationSettings(6, 8, 0..127, listOf("!minecraft:nether"))).also { blockCache += it }
    val NETHER_PLUTONIUM_ORE = OreBlock("nether_plutonium_ore", NTMProp(Material.ROCK, MaterialColor.NETHERRACK, hardnessAndResistance = 3f, harvestLevel = 2), OreGenerationSettings(4, 6, 0..127, listOf("!minecraft:nether"))).also { blockCache += it }
    val NETHER_TUNGSTEN_ORE = OreBlock("nether_tungsten_ore", NTMProp(Material.ROCK, MaterialColor.NETHERRACK, hardnessAndResistance = 3f, harvestLevel = 2), OreGenerationSettings(10, 10, 0..127, listOf("!minecraft:nether"))).also { blockCache += it }
    val NETHER_SULFUR_ORE = OreBlock("nether_sulfur_ore", NTMProp(Material.ROCK, MaterialColor.NETHERRACK, hardness = 2f, resistance = 3f, harvestLevel = 1, xpDrop = 1..2), OreGenerationSettings(12, 26, 0..127, listOf("!minecraft:nether"))).also { blockCache += it }
    val NETHER_FIRE_ORE = OreBlock("nether_fire_ore", NTMProp(Material.ROCK, MaterialColor.NETHERRACK, hardness = 2f, resistance = 3f, harvestLevel = 1, xpDrop = 2..4), OreGenerationSettings(3, 24, 0..127, listOf("!minecraft:nether"))).also { blockCache += it }
    val NETHER_SCHRABIDIUM_ORE = NTMBlock("nether_schrabidium_ore", NTMProp(Material.ROCK, MaterialColor.CYAN, hardness = 20f, resistance = 50f, harvestLevel = 3, lightValue = 7, rarity = Rarity.RARE)).also { blockCache += it } // TODO replace nether uranium ore with nether schrabidium ore on nuclear explosion
    val METEOR_URANIUM_ORE = NTMBlock("meteor_uranium_ore", NTMProp(Material.ROCK, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 3)).also { blockCache += it } // TODO make meteors
    val METEOR_THORIUM_ORE = NTMBlock("meteor_thorium_ore", NTMProp(Material.ROCK, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 3)).also { blockCache += it }
    val METEOR_TITANIUM_ORE = NTMBlock("meteor_titanium_ore", NTMProp(Material.ROCK, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 3)).also { blockCache += it }
    val METEOR_SULFUR_ORE = NTMBlock("meteor_sulfur_ore", NTMProp(Material.ROCK, MaterialColor.GRAY, hardnessAndResistance = 5f, harvestLevel = 3, xpDrop = 5..8)).also { blockCache += it }
    val METEOR_COPPER_ORE = NTMBlock("meteor_copper_ore", NTMProp(Material.ROCK, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 3)).also { blockCache += it }
    val METEOR_TUNGSTEN_ORE = NTMBlock("meteor_tungsten_ore", NTMProp(Material.ROCK, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 3)).also { blockCache += it }
    val METEOR_ALUMINIUM_ORE = NTMBlock("meteor_aluminium_ore", NTMProp(Material.ROCK, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 3)).also { blockCache += it }
    val METEOR_LEAD_ORE = NTMBlock("meteor_lead_ore", NTMProp(Material.ROCK, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 3)).also { blockCache += it }
    val METEOR_LITHIUM_ORE = NTMBlock("meteor_lithium_ore", NTMProp(Material.ROCK, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 3)).also { blockCache += it }
    val STARMETAL_ORE = NTMBlock("starmetal_ore", NTMProp(Material.ROCK, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 3)).also { blockCache += it }
    val TRIXITE = OreBlock("trixite", NTMProp(Material.ROCK, MaterialColor.SAND, hardness = 4f, resistance = 9f, harvestLevel = 2), OreGenerationSettings(1, 100, 0..127, listOf("!THEEND"))).also { blockCache += it }

    // Metal Blocks

    val URANIUM_BLOCK = NTMBlock("uranium_block", NTMProp(Material.IRON, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 2, soundType = SoundType.METAL)).also { blockCache += it }
    val THORIUM_BLOCK = NTMBlock("thorium_block", NTMProp(Material.IRON, MaterialColor.BROWN, hardnessAndResistance = 6f, harvestLevel = 2, soundType = SoundType.METAL)).also { blockCache += it }
    val TITANIUM_BLOCK = NTMBlock("titanium_block", NTMProp(Material.IRON, MaterialColor.IRON, hardnessAndResistance = 6f, harvestLevel = 2, soundType = SoundType.METAL)).also { blockCache += it }
    val SULFUR_BLOCK = NTMBlock("sulfur_block", NTMProp(Material.ROCK, MaterialColor.YELLOW, hardness = 4f, resistance = 6f, harvestLevel = 1)).also { blockCache += it }
    val NITER_BLOCK = NTMBlock("niter_block", NTMProp(Material.ROCK, MaterialColor.LIGHT_GRAY, hardness = 4f, resistance = 6f, harvestLevel = 1)).also { blockCache += it }
    val COPPER_BLOCK = NTMBlock("copper_block", NTMProp(Material.IRON, MaterialColor.ORANGE_TERRACOTTA, hardnessAndResistance = 6f, harvestLevel = 1, soundType = SoundType.METAL)).also { blockCache += it }
    val RED_COPPER_BLOCK = NTMBlock("red_copper_block", NTMProp(Material.IRON, MaterialColor.RED, hardnessAndResistance = 8f, harvestLevel = 2, soundType = SoundType.METAL)).also { blockCache += it }
    val ADVANCED_ALLOY_BLOCK = NTMBlock("advanced_alloy_block", NTMProp(Material.IRON, MaterialColor.ORANGE_TERRACOTTA, hardnessAndResistance = 10f, harvestLevel = 2, soundType = SoundType.METAL)).also { blockCache += it }
    val TUNGSTEN_BLOCK = NTMBlock("tungsten_block", NTMProp(Material.IRON, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 2, soundType = SoundType.METAL)).also { blockCache += it }
    val ALUMINIUM_BLOCK = NTMBlock("aluminium_block", NTMProp(Material.IRON, MaterialColor.IRON, hardnessAndResistance = 6f, harvestLevel = 2, soundType = SoundType.METAL)).also { blockCache += it }
    val FLUORITE_BLOCK = NTMBlock("fluorite_block", NTMProp(Material.IRON, MaterialColor.IRON, hardness = 4f, resistance = 6f, harvestLevel = 2, soundType = SoundType.METAL)).also { blockCache += it }
    val BERYLLIUM_BLOCK = NTMBlock("beryllium_block", NTMProp(Material.IRON, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 2, soundType = SoundType.METAL)).also { blockCache += it }
    val STEEL_BLOCK = NTMBlock("steel_block", NTMProp(Material.IRON, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 2, soundType = SoundType.METAL)).also { blockCache += it }
    val LEAD_BLOCK = NTMBlock("lead_block", NTMProp(Material.IRON, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 2, soundType = SoundType.METAL)).also { blockCache += it }
    val YELLOWCAKE_BLOCK = NTMBlock("yellowcake_block", NTMProp(Material.SAND, MaterialColor.YELLOW, hardnessAndResistance = 3f, harvestLevel = 1, harvestTool = ToolType.SHOVEL, soundType = SoundType.SAND)).also { blockCache += it }
    val SCRAP_BLOCK = NTMBlock("scrap_block", NTMProp(Material.SAND, MaterialColor.LIGHT_GRAY, hardnessAndResistance = 1f, harvestTool = ToolType.SHOVEL, soundType = SoundType.SAND)).also { blockCache += it }
    val ELECTRICAL_SCRAP_BLOCK = NTMBlock("electrical_scrap_block", NTMProp(Material.ROCK, MaterialColor.GRAY, hardness = 4f, resistance = 6f, harvestLevel = 1)).also { blockCache += it }
    val TRINITITE_BLOCK = NTMBlock("trinitite_block", NTMProp(Material.ROCK, MaterialColor.GREEN, hardness = 3f, resistance = 5f, harvestLevel = 1)).also { blockCache += it }
    val NUCLEAR_WASTE_BLOCK = NTMBlock("nuclear_waste_block", NTMProp(Material.ROCK, MaterialColor.BLACK, hardnessAndResistance = 6f, harvestLevel = 2)).also { blockCache += it }
    val SCHRABIDIUM_BLOCK = NTMBlock("schrabidium_block", NTMProp(Material.IRON, MaterialColor.BLUE, hardness = 150f, resistance = 5000f, harvestLevel = 3, lightValue = 15, soundType = SoundType.METAL, rarity = Rarity.RARE)).also { blockCache += it }
    val MAGNETIZED_TUNGSTEN_BLOCK = NTMBlock("magnetized_tungsten_block", NTMProp(Material.IRON, MaterialColor.GRAY, hardness = 15f, resistance = 30f, harvestLevel = 2, lightValue = 5, soundType = SoundType.METAL)).also { blockCache += it }
    val COMBINE_STEEL_BLOCK = NTMBlock("combine_steel_block", NTMProp(Material.IRON, MaterialColor.BLUE, hardness = 30f, resistance = 60f, harvestLevel = 3, soundType = SoundType.METAL)).also { blockCache += it }
    val REINFORCED_DESH_BLOCK = NTMBlock("reinforced_desh_block", NTMProp(Material.IRON, MaterialColor.RED, hardness = 20f, resistance = 30f, harvestLevel = 2, soundType = SoundType.METAL)).also { blockCache += it }
    val STARMETAL_BLOCK = NTMBlock("starmetal_block", NTMProp(Material.IRON, MaterialColor.LIGHT_BLUE, hardnessAndResistance = 10f, harvestLevel = 2, soundType = SoundType.METAL)).also { blockCache += it }
    val AUSTRALIUM_BLOCK = NTMBlock("australium_block", NTMProp(Material.IRON, MaterialColor.YELLOW, hardnessAndResistance = 6f, harvestLevel = 2, soundType = SoundType.METAL, rarity = Rarity.UNCOMMON)).also { blockCache += it }
    val WEIDANIUM_BLOCK = NTMBlock("weidanium_block", NTMProp(Material.IRON, MaterialColor.ORANGE_TERRACOTTA, hardnessAndResistance = 6f, harvestLevel = 2, soundType = SoundType.METAL, rarity = Rarity.UNCOMMON)).also { blockCache += it }
    val REIIUM_BLOCK = NTMBlock("reiium_block", NTMProp(Material.IRON, MaterialColor.RED, hardnessAndResistance = 6f, harvestLevel = 2, soundType = SoundType.METAL, rarity = Rarity.UNCOMMON)).also { blockCache += it }
    val UNOBTAINIUM_BLOCK = NTMBlock("unobtainium_block", NTMProp(Material.IRON, MaterialColor.BLUE, hardnessAndResistance = 6f, harvestLevel = 2, soundType = SoundType.METAL, rarity = Rarity.UNCOMMON)).also { blockCache += it }
    val DAFFERGON_BLOCK = NTMBlock("daffergon_block", NTMProp(Material.IRON, MaterialColor.PURPLE, hardnessAndResistance = 6f, harvestLevel = 2, soundType = SoundType.METAL, rarity = Rarity.UNCOMMON)).also { blockCache += it }
    val VERTICIUM_BLOCK = NTMBlock("verticium_block", NTMProp(Material.IRON, MaterialColor.GREEN, hardnessAndResistance = 6f, harvestLevel = 2, soundType = SoundType.METAL, rarity = Rarity.UNCOMMON)).also { blockCache += it }

    // Deco Blocks

    val TITANIUM_DECO_BLOCK = NTMBlock("titanium_deco_block", NTMProp(Material.IRON, MaterialColor.IRON, hardnessAndResistance = 6f, harvestLevel = 2, soundType = SoundType.METAL)).also { blockCache += it }
    val RED_COPPER_DECO_BLOCK = NTMBlock("red_copper_deco_block", NTMProp(Material.IRON, MaterialColor.RED, hardnessAndResistance = 8f, harvestLevel = 2, soundType = SoundType.METAL)).also { blockCache += it }
    val TUNGSTEN_DECO_BLOCK = NTMBlock("tungsten_deco_block", NTMProp(Material.IRON, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 2, soundType = SoundType.METAL)).also { blockCache += it }
    val ALUMINIUM_DECO_BLOCK = NTMBlock("aluminium_deco_block", NTMProp(Material.IRON, MaterialColor.IRON, hardnessAndResistance = 6f, harvestLevel = 2, soundType = SoundType.METAL)).also { blockCache += it }
    val STEEL_DECO_BLOCK = NTMBlock("steel_deco_block", NTMProp(Material.IRON, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 2, soundType = SoundType.METAL)).also { blockCache += it }
    val LEAD_DECO_BLOCK = NTMBlock("lead_deco_block", NTMProp(Material.IRON, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 2, soundType = SoundType.METAL)).also { blockCache += it }
    val BERYLLIUM_DECO_BLOCK = NTMBlock("beryllium_deco_block", NTMProp(Material.IRON, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 2, soundType = SoundType.METAL)).also { blockCache += it }
    val HAZMAT_CLOTH_BLOCK = NTMBlock("hazmat_cloth_block", NTMProp(Material.WOOL, MaterialColor.YELLOW, hardnessAndResistance = 2f, soundType = SoundType.CLOTH)).also { blockCache += it }
    val CRUSHED_OBSIDIAN = CrushedObsidianBlock("crushed_obsidian", NTMProp(Material.SAND, MaterialColor.BLACK, hardness = 1f, resistance = 2f, harvestTool = ToolType.SHOVEL, soundType = SoundType.GROUND)).also { blockCache += it }
    val ASPHALT = NTMBlock("asphalt", NTMProp(Material.ROCK, MaterialColor.BLACK, hardness = 12f, resistance = 15f, harvestLevel = 2)).also { blockCache += it }

    // Reinforced Blocks

    val REINFORCED_STONE = NTMBlock("reinforced_stone", NTMProp(Material.ROCK, hardness = 30f, resistance = 210f, harvestLevel = 2)).also { blockCache += it }
    val REINFORCED_GLASS = ReinforcedGlassBlock("reinforced_glass", NTMProp(Material.GLASS, hardness = 10f, resistance = 70f, harvestLevel = 1, soundType = SoundType.GLASS)).also { blockCache += it }
    val REINFORCED_GLOWSTONE = NTMBlock("reinforced_glowstone", NTMProp(Material.GLASS, MaterialColor.SAND, hardness = 10f, resistance = 70f, harvestLevel = 1, lightValue = 15, soundType = SoundType.GLASS)).also { blockCache += it }
    val REINFORCED_SANDSTONE = NTMBlock("reinforced_sandstone", NTMProp(Material.ROCK, MaterialColor.SAND, hardness = 15f, resistance = 105f, harvestLevel = 2)).also { blockCache += it }
    val REINFORCED_REDSTONE_LAMP = ReinforcedRedstoneLampBlock("reinforced_redstone_lamp", NTMProp(Material.REDSTONE_LIGHT, hardness = 10f, resistance = 70f, harvestLevel = 1, lightValue = 15, soundType = SoundType.GLASS)).also { blockCache += it }
    // TODO add assets for blocks below here

    @SubscribeEvent
    @JvmStatic
    fun registerBlocks(event: RegistryEvent.Register<Block>) {
        NTM.logger.debug("Registering blocks...")

        event.registry.registerAll(*blockCache.toTypedArray())
        blockCache.removeAll { true } // Empty cache to free memory.

        NTM.logger.debug("Blocks registered")
    }
}
