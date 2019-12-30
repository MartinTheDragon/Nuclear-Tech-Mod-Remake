package at.martinthedragon.ntm.blocks

import at.martinthedragon.ntm.blocks.advancedblocks.CustomizedBlock
import at.martinthedragon.ntm.items.advanceditems.CustomizedBlockItem
import at.martinthedragon.ntm.items.advanceditems.CustomizedItem
import at.martinthedragon.ntm.lib.MODID
import at.martinthedragon.ntm.main.Main
import at.martinthedragon.ntm.worldgen.OreGenerationSettings
import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.item.BlockItem
import net.minecraft.item.Rarity
import net.minecraftforge.common.ToolType
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

typealias NTMBlock = CustomizedBlock
typealias NTMProp = CustomizedBlock.CustomizedProperties
typealias NTMBlockItem = CustomizedBlockItem
typealias NTMItemProp = CustomizedItem.CustomizedProperties

@Suppress("unused")
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object ModBlocks {
    val blockList = emptyList<CustomizedBlock>().toMutableList()
    val itemBlockList = emptyList<CustomizedBlockItem>().toMutableList()
    val oreList = emptyList<CustomizedBlock>().toMutableList() // used by Config and WorldGeneration

    // Ores

    val URANIUM_ORE = NTMBlock("uranium_ore", NTMProp(Material.ROCK, hardnessAndResistance = 3f, harvestLevel = 2, harvestTool = ToolType.PICKAXE, defaultOreGenerationSettings = OreGenerationSettings(listOf("minecraft:nether", "THEEND"), 5, 6, 0..25)))
    val THORIUM_ORE = NTMBlock("thorium_ore", NTMProp(Material.ROCK, hardnessAndResistance = 3f, harvestLevel = 2, harvestTool = ToolType.PICKAXE, defaultOreGenerationSettings = OreGenerationSettings(listOf("minecraft:nether", "THEEND"), 5, 7, 0..30)))
    val TITANIUM_ORE = NTMBlock("titanium_ore", NTMProp(Material.ROCK, hardnessAndResistance = 3f, harvestLevel = 2, harvestTool = ToolType.PICKAXE, defaultOreGenerationSettings = OreGenerationSettings(listOf("minecraft:nether", "THEEND"), 6, 8, 0..35)))
    val SULFUR_ORE = NTMBlock("sulfur_ore", NTMProp(Material.ROCK, hardness = 2f, resistance = 3f, harvestLevel = 1, harvestTool = ToolType.PICKAXE, defaultOreGenerationSettings = OreGenerationSettings(listOf("minecraft:nether", "THEEND"), 8, 5, 0..35), xpDrop = 1..3))
    val NITER_ORE = NTMBlock("niter_ore", NTMProp(Material.ROCK, hardness = 2f, resistance = 3f, harvestLevel = 1, harvestTool = ToolType.PICKAXE, defaultOreGenerationSettings = OreGenerationSettings(listOf("minecraft:nether", "THEEND"), 6, 6, 0..35), xpDrop = 2..4))
    val COPPER_ORE = NTMBlock("copper_ore", NTMProp(Material.ROCK, hardnessAndResistance = 3f, harvestLevel = 1, harvestTool = ToolType.PICKAXE, defaultOreGenerationSettings = OreGenerationSettings(listOf("minecraft:nether", "THEEND"), 6, 12, 0..50)))
    val TUNGSTEN_ORE = NTMBlock("tungsten_ore", NTMProp(Material.ROCK, hardnessAndResistance = 3f, harvestLevel = 2, harvestTool = ToolType.PICKAXE, defaultOreGenerationSettings = OreGenerationSettings(listOf("minecraft:nether", "THEEND"), 8, 10, 0..35)))
    val ALUMINIUM_ORE = NTMBlock("aluminium_ore", NTMProp(Material.ROCK, hardnessAndResistance = 3f, harvestLevel = 2, harvestTool = ToolType.PICKAXE, defaultOreGenerationSettings = OreGenerationSettings(listOf("minecraft:nether", "THEEND"), 6, 7, 0..45)))
    val FLUORITE_ORE = NTMBlock("fluorite_ore", NTMProp(Material.ROCK, hardness = 2f, resistance = 3f, harvestLevel = 1, harvestTool = ToolType.PICKAXE, defaultOreGenerationSettings = OreGenerationSettings(listOf("minecraft:nether", "THEEND"), 4, 6, 0..40), xpDrop = 2..5))
    val BERYLLIUM_ORE = NTMBlock("beryllium_ore", NTMProp(Material.ROCK, hardnessAndResistance = 3f, harvestLevel = 2, harvestTool = ToolType.PICKAXE, defaultOreGenerationSettings = OreGenerationSettings(listOf("minecraft:nether", "THEEND"), 4, 6, 0..35)))
    val LEAD_ORE = NTMBlock("lead_ore", NTMProp(Material.ROCK, hardnessAndResistance = 3f, harvestLevel = 2, harvestTool = ToolType.PICKAXE, defaultOreGenerationSettings = OreGenerationSettings(listOf("minecraft:nether", "THEEND"), 9, 6, 0..35)))
    val OIL_DEPOSIT = NTMBlock("oil_deposit", NTMProp(Material.ROCK, hardness = 1f, resistance = 2f, harvestLevel = 0, harvestTool = ToolType.PICKAXE)) // TODO implement oil generation
    val EMPTY_OIL_DEPOSIT = NTMBlock("empty_oil_deposit", NTMProp(Material.ROCK, hardness = 1f, resistance = 2f, harvestLevel = 0, harvestTool = ToolType.PICKAXE))
    val TAR_SAND = NTMBlock("tar_sand", NTMProp(Material.SAND, MaterialColor.LIGHT_GRAY, hardnessAndResistance = 1f, harvestLevel = -1, harvestTool = ToolType.SHOVEL, soundType = SoundType.SAND)) // TODO implement tar sand generation
    val LIGNITE_ORE = NTMBlock("lignite_ore", NTMProp(Material.ROCK, hardnessAndResistance = 3f, harvestLevel = 0, harvestTool = ToolType.PICKAXE, defaultOreGenerationSettings = OreGenerationSettings(listOf("minecraft:nether", "THEEND"), 24, 2, 35..60), xpDrop = 0..1))
    val SCHRABIDIUM_ORE = NTMBlock("schrabidium_ore", NTMProp(Material.ROCK, MaterialColor.CYAN, hardness = 20f, resistance = 50f, harvestLevel = 3, harvestTool = ToolType.PICKAXE, lightValue = 7, rarity = Rarity.RARE)) // TODO replace uranium ore with schrabidium ore on nuclear explosion
    val AUSTRALIAN_ORE = NTMBlock("australian_ore", NTMProp(Material.ROCK, MaterialColor.YELLOW, hardnessAndResistance = 6f, harvestLevel = 2, harvestTool = ToolType.PICKAXE, rarity = Rarity.UNCOMMON, defaultOreGenerationSettings = OreGenerationSettings(listOf("minecraft:nether", "THEEND"), 4, 2, 15..30))) // TODO find out how to generate special ores in a special way
    val WEIDITE = NTMBlock("weidite", NTMProp(Material.ROCK, MaterialColor.ORANGE_TERRACOTTA, hardnessAndResistance = 6f, harvestLevel = 2, harvestTool = ToolType.PICKAXE, rarity = Rarity.UNCOMMON, defaultOreGenerationSettings = OreGenerationSettings(listOf("minecraft:nether", "THEEND"), 4, 2, 0..25)))
    val REIITE = NTMBlock("reiite", NTMProp(Material.ROCK, MaterialColor.RED, hardnessAndResistance = 6f, harvestLevel = 2, harvestTool = ToolType.PICKAXE, rarity = Rarity.UNCOMMON, defaultOreGenerationSettings = OreGenerationSettings(listOf("minecraft:nether", "THEEND"), 4, 2, 0..35)))
    val BRIGHTBLENDE_ORE = NTMBlock("brightblende_ore", NTMProp(Material.ROCK, MaterialColor.BLUE, hardnessAndResistance = 6f, harvestLevel = 2, harvestTool = ToolType.PICKAXE, rarity = Rarity.UNCOMMON, defaultOreGenerationSettings = OreGenerationSettings(listOf("minecraft:nether", "THEEND"), 4, 2, 0..128)))
    val DELLITE = NTMBlock("dellite", NTMProp(Material.ROCK, MaterialColor.PURPLE, hardnessAndResistance = 6f, harvestLevel = 2, harvestTool = ToolType.PICKAXE, rarity = Rarity.UNCOMMON, defaultOreGenerationSettings = OreGenerationSettings(listOf("minecraft:nether", "THEEND"), 4, 2, 0..10)))
    val DOLLAR_GREEN_MINERAL = NTMBlock("dollar_green_mineral", NTMProp(Material.ROCK, MaterialColor.GREEN, hardnessAndResistance = 6f, harvestLevel = 2, harvestTool = ToolType.PICKAXE, rarity = Rarity.UNCOMMON, defaultOreGenerationSettings = OreGenerationSettings(listOf("minecraft:nether", "THEEND"), 4, 2, 25..50)))
    val RARE_EARTH_ORE = NTMBlock("rare_earth_ore", NTMProp(Material.ROCK, MaterialColor.NETHERRACK, hardness = 4f, resistance = 3f, harvestLevel = 2, harvestTool = ToolType.PICKAXE, rarity = Rarity.UNCOMMON, defaultOreGenerationSettings = OreGenerationSettings(listOf("minecraft:nether", "THEEND"), 5, 6, 0..25), xpDrop = 3..6))
    val NETHER_URANIUM_ORE = NTMBlock("nether_uranium_ore", NTMProp(Material.ROCK, MaterialColor.NETHERRACK, hardnessAndResistance = 3f, harvestLevel = 2, harvestTool = ToolType.PICKAXE, defaultOreGenerationSettings = OreGenerationSettings(listOf("!minecraft:nether"), 6, 8, 0..127)))
    val NETHER_PLUTONIUM_ORE = NTMBlock("nether_plutonium_ore", NTMProp(Material.ROCK, MaterialColor.NETHERRACK, hardnessAndResistance = 3f, harvestLevel = 2, harvestTool = ToolType.PICKAXE, defaultOreGenerationSettings = OreGenerationSettings(listOf("!minecraft:nether"), 4, 6, 0..127)))
    val NETHER_TUNGSTEN_ORE = NTMBlock("nether_tungsten_ore", NTMProp(Material.ROCK, MaterialColor.NETHERRACK, hardnessAndResistance = 3f, harvestLevel = 2, harvestTool = ToolType.PICKAXE, defaultOreGenerationSettings = OreGenerationSettings(listOf("!minecraft:nether"), 10, 10, 0..127)))
    val NETHER_SULFUR_ORE = NTMBlock("nether_sulfur_ore", NTMProp(Material.ROCK, MaterialColor.NETHERRACK, hardness = 2f, resistance = 3f, harvestLevel = 1, harvestTool = ToolType.PICKAXE, defaultOreGenerationSettings = OreGenerationSettings(listOf("!minecraft:nether"), 12, 26, 0..127), xpDrop = 1..2))
    val NETHER_FIRE_ORE = NTMBlock("nether_fire_ore", NTMProp(Material.ROCK, MaterialColor.NETHERRACK, hardness = 2f, resistance = 3f, harvestLevel = 1, harvestTool = ToolType.PICKAXE, defaultOreGenerationSettings = OreGenerationSettings(listOf("!minecraft:nether"), 3, 24, 0..127), xpDrop = 2..4))
    val NETHER_SCHRABIDIUM_ORE = NTMBlock("nether_schrabidium_ore", NTMProp(Material.ROCK, MaterialColor.CYAN, hardness = 20f, resistance = 50f, harvestLevel = 3, harvestTool = ToolType.PICKAXE, lightValue = 7, rarity = Rarity.RARE)) // TODO replace nether uranium ore with nether schrabidium ore on nuclear explosion
    val METEOR_URANIUM_ORE = NTMBlock("meteor_uranium_ore", NTMProp(Material.ROCK, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 3, harvestTool = ToolType.PICKAXE)) // TODO make meteors
    val METEOR_THORIUM_ORE = NTMBlock("meteor_thorium_ore", NTMProp(Material.ROCK, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 3, harvestTool = ToolType.PICKAXE))
    val METEOR_TITANIUM_ORE = NTMBlock("meteor_titanium_ore", NTMProp(Material.ROCK, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 3, harvestTool = ToolType.PICKAXE))
    val METEOR_SULFUR_ORE = NTMBlock("meteor_sulfur_ore", NTMProp(Material.ROCK, MaterialColor.GRAY, hardnessAndResistance = 5f, harvestLevel = 3, harvestTool = ToolType.PICKAXE, xpDrop = 5..8))
    val METEOR_COPPER_ORE = NTMBlock("meteor_copper_ore", NTMProp(Material.ROCK, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 3, harvestTool = ToolType.PICKAXE))
    val METEOR_TUNGSTEN_ORE = NTMBlock("meteor_tungsten_ore", NTMProp(Material.ROCK, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 3, harvestTool = ToolType.PICKAXE))
    val METEOR_ALUMINIUM_ORE = NTMBlock("meteor_aluminium_ore", NTMProp(Material.ROCK, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 3, harvestTool = ToolType.PICKAXE))
    val METEOR_LEAD_ORE = NTMBlock("meteor_lead_ore", NTMProp(Material.ROCK, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 3, harvestTool = ToolType.PICKAXE))
    val METEOR_LITHIUM_ORE = NTMBlock("meteor_lithium_ore", NTMProp(Material.ROCK, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 3, harvestTool = ToolType.PICKAXE))
    val STARMETAL_ORE = NTMBlock("starmetal_ore", NTMProp(Material.ROCK, MaterialColor.GRAY, hardnessAndResistance = 6f, harvestLevel = 3, harvestTool = ToolType.PICKAXE))
    val TRIXITE = NTMBlock("trixite", NTMProp(Material.ROCK, MaterialColor.SAND, hardness = 4f, resistance = 9f, harvestLevel = 2, harvestTool = ToolType.PICKAXE, defaultOreGenerationSettings = OreGenerationSettings(listOf("!THEEND"), 1, 100, 0..127)))

    @SubscribeEvent
    @JvmStatic
    fun registerBlocks(event: RegistryEvent.Register<Block>) {
        Main.LOGGER.debug("Registering blocks...")
        event.registry.registerAll(*blockList.toTypedArray())
        Main.LOGGER.debug("Blocks registered")
    }
}
