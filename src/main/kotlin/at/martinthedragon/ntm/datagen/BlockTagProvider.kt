package at.martinthedragon.ntm.datagen

import at.martinthedragon.ntm.Main
import at.martinthedragon.ntm.ModBlocks
import at.martinthedragon.ntm.NuclearTags
import net.minecraft.data.BlockTagsProvider
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.Tags
import net.minecraftforge.common.data.ExistingFileHelper

class BlockTagProvider(dataGenerator: DataGenerator, existingFileHelper: ExistingFileHelper) : BlockTagsProvider(dataGenerator, Main.MODID, existingFileHelper) {
    override fun getName(): String = "Nuclear Tech Mod Block Tags"

    override fun addTags() {
        oreTags()
        storageBlockTags()
        miscTags()
    }

    private fun oreTags() {
        tag(Tags.Blocks.ORES)
            .addTag(NuclearTags.Blocks.ORES_URANIUM)
            .addTag(NuclearTags.Blocks.ORES_THORIUM)
            .addTag(NuclearTags.Blocks.ORES_TITANIUM)
            .addTag(NuclearTags.Blocks.ORES_SULFUR)
            .addTag(NuclearTags.Blocks.ORES_NITER)
            .addTag(NuclearTags.Blocks.ORES_COPPER)
            .addTag(NuclearTags.Blocks.ORES_TUNGSTEN)
            .addTag(NuclearTags.Blocks.ORES_ALUMINIUM)
            .addTag(NuclearTags.Blocks.ORES_FLUORITE)
            .addTag(NuclearTags.Blocks.ORES_BERYLLIUM)
            .addTag(NuclearTags.Blocks.ORES_LEAD)
            .addTag(NuclearTags.Blocks.ORES_OIL)
            .addTag(NuclearTags.Blocks.ORES_LIGNITE)
            .addTag(NuclearTags.Blocks.ORES_ASBESTOS)
            .addTag(NuclearTags.Blocks.ORES_RARE_EARTH)
            .add(
                ModBlocks.schrabidiumOre, ModBlocks.australianOre,
                ModBlocks.weidite, ModBlocks.reiite,
                ModBlocks.brightblendeOre, ModBlocks.dellite,
                ModBlocks.dollarGreenMineral, ModBlocks.netherUraniumOre,
                ModBlocks.netherPlutoniumOre, ModBlocks.netherTungstenOre,
                ModBlocks.netherSulfurOre, ModBlocks.netherPhosphorusOre,
                ModBlocks.netherSchrabidiumOre, ModBlocks.meteorUraniumOre,
                ModBlocks.meteorThoriumOre, ModBlocks.meteorTitaniumOre,
                ModBlocks.meteorSulfurOre, ModBlocks.meteorCopperOre,
                ModBlocks.meteorTungstenOre, ModBlocks.meteorAluminiumOre,
                ModBlocks.meteorLeadOre, ModBlocks.meteorLithiumOre,
                ModBlocks.starmetalOre, ModBlocks.trixite
            )

        tag(NuclearTags.Blocks.ORES_URANIUM).add(ModBlocks.uraniumOre)
        tag(NuclearTags.Blocks.ORES_THORIUM).add(ModBlocks.thoriumOre)
        tag(NuclearTags.Blocks.ORES_TITANIUM).add(ModBlocks.titaniumOre)
        tag(NuclearTags.Blocks.ORES_SULFUR).add(ModBlocks.sulfurOre)
        tag(NuclearTags.Blocks.ORES_NITER).add(ModBlocks.niterOre)
        tag(NuclearTags.Blocks.ORES_COPPER).add(ModBlocks.copperOre)
        tag(NuclearTags.Blocks.ORES_TUNGSTEN).add(ModBlocks.tungstenOre)
        tag(NuclearTags.Blocks.ORES_ALUMINIUM).add(ModBlocks.aluminiumOre)
        tag(NuclearTags.Blocks.ORES_FLUORITE).add(ModBlocks.fluoriteOre)
        tag(NuclearTags.Blocks.ORES_BERYLLIUM).add(ModBlocks.berylliumOre)
        tag(NuclearTags.Blocks.ORES_LEAD).add(ModBlocks.leadOre)
        tag(NuclearTags.Blocks.ORES_OIL).add(ModBlocks.oilDeposit)
        tag(NuclearTags.Blocks.ORES_LIGNITE).add(ModBlocks.ligniteOre)
        tag(NuclearTags.Blocks.ORES_ASBESTOS).add(ModBlocks.asbestosOre)
        tag(NuclearTags.Blocks.ORES_RARE_EARTH).add(ModBlocks.rareEarthOre)
    }

    private fun storageBlockTags() {
        tag(Tags.Blocks.STORAGE_BLOCKS)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCKS_URANIUM)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCKS_NEPTUNIUM)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCKS_MOX)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCKS_PLUTONIUM)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCKS_THORIUM)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCKS_TITANIUM)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCKS_SULFUR)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCKS_NITER)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCKS_COPPER)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCKS_TUNGSTEN)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCKS_ALUMINIUM)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCKS_FLUORITE)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCKS_BERYLLIUM)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCKS_COBALT)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCKS_STEEL)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCKS_LEAD)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCKS_LITHIUM)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCKS_PHOSPHORUS)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCKS_YELLOWCAKE)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCKS_SCRAP)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCK_ASBESTOS)
            .addTag(NuclearTags.Blocks.STORAGE_BLOCK_NUCLEAR_WASTE)
            .add(
                ModBlocks.redCopperBlock, ModBlocks.advancedAlloyBlock,
                ModBlocks.trinititeBlock, ModBlocks.schrabidiumBlock,
                ModBlocks.soliniumBlock, ModBlocks.schrabidiumFuelBlock,
                ModBlocks.euphemiumBlock, ModBlocks.magnetizedTungstenBlock,
                ModBlocks.combineSteelBlock, ModBlocks.starmetalBlock,
                ModBlocks.australiumBlock, ModBlocks.weidaniumBlock,
                ModBlocks.reiiumBlock, ModBlocks.unobtainiumBlock,
                ModBlocks.daffergonBlock, ModBlocks.verticiumBlock,
                ModBlocks.hazmatBlock
            )

        tag(NuclearTags.Blocks.STORAGE_BLOCKS_URANIUM).add(
            ModBlocks.uraniumBlock, ModBlocks.u233Block,
            ModBlocks.u235Block, ModBlocks.u238Block,
            ModBlocks.uraniumFuelBlock
        )
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_NEPTUNIUM).add(ModBlocks.neptuniumBlock)
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_MOX).add(ModBlocks.moxFuelBlock)
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_PLUTONIUM).add(
            ModBlocks.plutoniumBlock, ModBlocks.pu238Block,
            ModBlocks.pu239Block, ModBlocks.pu240Block,
            ModBlocks.plutoniumFuelBlock
        )
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_THORIUM).add(ModBlocks.thoriumBlock)
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_TITANIUM).add(ModBlocks.titaniumBlock)
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_SULFUR).add(ModBlocks.sulfurBlock)
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_NITER).add(ModBlocks.niterBlock)
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_COPPER).add(ModBlocks.copperBlock)
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_TUNGSTEN).add(ModBlocks.tungstenBlock)
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_ALUMINIUM).add(ModBlocks.aluminiumBlock)
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_FLUORITE).add(ModBlocks.fluoriteBlock)
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_BERYLLIUM).add(ModBlocks.berylliumBlock)
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_COBALT).add(ModBlocks.cobaltBlock)
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_STEEL).add(ModBlocks.steelBlock)
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_LEAD).add(ModBlocks.leadBlock)
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_LITHIUM).add(ModBlocks.lithiumBlock)
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_PHOSPHORUS).add(ModBlocks.whitePhosphorusBlock, ModBlocks.redPhosphorusBlock)
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_YELLOWCAKE).add(ModBlocks.yellowcakeBlock)
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_SCRAP).add(ModBlocks.scrapBlock, ModBlocks.electricalScrapBlock)
        tag(NuclearTags.Blocks.STORAGE_BLOCK_ASBESTOS).add(ModBlocks.asbestosBlock)
        tag(NuclearTags.Blocks.STORAGE_BLOCK_NUCLEAR_WASTE).add(ModBlocks.nuclearWasteBlock)
    }

    private fun miscTags() {
        tag(Tags.Blocks.SAND).addTag(NuclearTags.Blocks.SAND_OIL)
        tag(NuclearTags.Blocks.SAND_OIL).add(ModBlocks.oilSand)
    }
}
