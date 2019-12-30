package at.martinthedragon.ntm.blocks.advancedblocks

import at.martinthedragon.ntm.blocks.ModBlocks
import at.martinthedragon.ntm.items.advanceditems.CustomizedBlockItem
import at.martinthedragon.ntm.items.advanceditems.CustomizedItem
import at.martinthedragon.ntm.lib.MODID
import at.martinthedragon.ntm.main.CreativeTabs
import at.martinthedragon.ntm.worldgen.OreGenerationSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.entity.Entity
import net.minecraft.item.DyeColor
import net.minecraft.item.Rarity
import net.minecraft.tags.BlockTags
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorldReader
import net.minecraftforge.common.ToolType
import kotlin.random.Random

@Suppress("CanBeParameter", "MemberVisibilityCanBePrivate", "unused")
class CustomizedBlock(val registryName: String, val customProperties: CustomizedProperties, autoGenerateBlockItem: Boolean = true, blockItem: CustomizedBlockItem? = null, itemTab: CreativeTabs = CreativeTabs.BLOCKS_TAB) : Block(customProperties.property) {
    val blockItem: CustomizedBlockItem? = when {
        blockItem != null -> blockItem
        autoGenerateBlockItem -> CustomizedBlockItem(this, CustomizedItem.CustomizedProperties(group = itemTab, rarity = customProperties.rarity))
        else -> null
    }

    init {
        setRegistryName(MODID, registryName)
        ModBlocks.blockList.add(this)
        if (customProperties.defaultOreGenerationSettings != null) {
            ModBlocks.oreList.add(this)
        }
        if (this.blockItem != null) ModBlocks.itemBlockList.add(this.blockItem)
    }

    override fun getTranslationKey(): String = "tile.ntm.$registryName"
    override fun getExpDrop(state: BlockState?, world: IWorldReader?, pos: BlockPos?, fortune: Int, silktouch: Int): Int =
            if (silktouch == 0 && customProperties.xpDrop != null) customProperties.xpDrop.random() else 0

    class CustomizedProperties(
            val material: Material,
            mapColor: MaterialColor? = null,
            dyeColor: DyeColor? = null,
            val blocksMovement: Boolean = true,
            val soundType: SoundType = SoundType.STONE,
            val lightValue: Int = 0,
            resistance: Float = 0f,
            hardness: Float = 0f,
            hardnessAndResistance: Float = 0f,
            val ticksRandomly: Boolean = false,
            val slipperiness: Float = 0.6f,
            val variableOpacity: Boolean = false,
            val harvestLevel: Int = -1,
            val harvestTool: ToolType? = null,
            val defaultOreGenerationSettings: OreGenerationSettings? = null,
            val xpDrop: IntRange? = null,
            val rarity: Rarity = Rarity.COMMON
    ) {
        val property: Properties = when {
            mapColor != null -> Properties.create(material, mapColor)
            dyeColor != null -> Properties.create(material, dyeColor)
            else -> Properties.create(material)
        }
        val mapColor: MaterialColor = mapColor ?: material.color
        val resistance: Float = if (hardnessAndResistance != 0f) hardnessAndResistance else resistance
        val hardness: Float = if (hardnessAndResistance != 0f) hardnessAndResistance else hardness

        init {
            if (!blocksMovement) property.doesNotBlockMovement()
            if (soundType != SoundType.STONE) property.sound(soundType)
            if (lightValue != 0) property.lightValue(lightValue)
            if (hardnessAndResistance != 0f) property.hardnessAndResistance(hardnessAndResistance)
            else if (resistance != 0f || hardness != 0f) property.hardnessAndResistance(resistance, hardness)
            if (ticksRandomly) property.tickRandomly()
            if (slipperiness != 0.6f) property.slipperiness(slipperiness)
            if (variableOpacity) property.variableOpacity()
            if (harvestLevel != -1) property.harvestLevel(harvestLevel)
            if (harvestTool != null) property.harvestTool(harvestTool)
        }
    }
}
