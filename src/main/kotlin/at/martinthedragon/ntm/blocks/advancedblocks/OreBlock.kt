package at.martinthedragon.ntm.blocks.advancedblocks

import at.martinthedragon.ntm.blocks.ModBlocks
import at.martinthedragon.ntm.main.CreativeTabs
import at.martinthedragon.ntm.worldgen.OreGenerationSettings

class OreBlock(registryName: String, customProperties: CustomizedProperties, val oreGenerationSettings: OreGenerationSettings, autoGenerateBlockItem: Boolean = true, itemTab: CreativeTabs = CreativeTabs.BLOCKS_TAB) : CustomizedBlock(registryName, customProperties, autoGenerateBlockItem, itemTab = itemTab) {
    init {
        ModBlocks.ores += registryName
    }
}