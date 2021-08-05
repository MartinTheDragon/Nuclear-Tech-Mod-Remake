package at.martinthedragon.nucleartech.datagen

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.NuclearTags
import net.minecraft.block.Blocks
import net.minecraft.data.BlockTagsProvider
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.Tags
import net.minecraftforge.common.data.ExistingFileHelper

class BlockTagProvider(dataGenerator: DataGenerator, existingFileHelper: ExistingFileHelper) : BlockTagsProvider(dataGenerator, NuclearTech.MODID, existingFileHelper) {
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
            .addTag(NuclearTags.Blocks.ORES_PLUTONIUM)
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
                ModBlocks.schrabidiumOre.get(), ModBlocks.australianOre.get(),
                ModBlocks.weidite.get(), ModBlocks.reiite.get(),
                ModBlocks.brightblendeOre.get(), ModBlocks.dellite.get(),
                ModBlocks.dollarGreenMineral.get(), ModBlocks.netherUraniumOre.get(),
                ModBlocks.netherTungstenOre.get(), ModBlocks.netherSulfurOre.get(), ModBlocks.netherPhosphorusOre.get(),
                ModBlocks.netherSchrabidiumOre.get(), ModBlocks.meteorUraniumOre.get(),
                ModBlocks.meteorThoriumOre.get(), ModBlocks.meteorTitaniumOre.get(),
                ModBlocks.meteorSulfurOre.get(), ModBlocks.meteorCopperOre.get(),
                ModBlocks.meteorTungstenOre.get(), ModBlocks.meteorAluminiumOre.get(),
                ModBlocks.meteorLeadOre.get(), ModBlocks.meteorLithiumOre.get(),
                ModBlocks.starmetalOre.get(), ModBlocks.trixite.get()
            )

        tag(NuclearTags.Blocks.ORES_URANIUM).add(ModBlocks.uraniumOre.get())
        tag(NuclearTags.Blocks.ORES_THORIUM).add(ModBlocks.thoriumOre.get())
        tag(NuclearTags.Blocks.ORES_PLUTONIUM).add(ModBlocks.netherPlutoniumOre.get())
        tag(NuclearTags.Blocks.ORES_TITANIUM).add(ModBlocks.titaniumOre.get())
        tag(NuclearTags.Blocks.ORES_SULFUR).add(ModBlocks.sulfurOre.get())
        tag(NuclearTags.Blocks.ORES_NITER).add(ModBlocks.niterOre.get())
        tag(NuclearTags.Blocks.ORES_COPPER).add(ModBlocks.copperOre.get())
        tag(NuclearTags.Blocks.ORES_TUNGSTEN).add(ModBlocks.tungstenOre.get())
        tag(NuclearTags.Blocks.ORES_ALUMINIUM).add(ModBlocks.aluminiumOre.get())
        tag(NuclearTags.Blocks.ORES_FLUORITE).add(ModBlocks.fluoriteOre.get())
        tag(NuclearTags.Blocks.ORES_BERYLLIUM).add(ModBlocks.berylliumOre.get())
        tag(NuclearTags.Blocks.ORES_LEAD).add(ModBlocks.leadOre.get())
        tag(NuclearTags.Blocks.ORES_OIL).add(ModBlocks.oilDeposit.get())
        tag(NuclearTags.Blocks.ORES_LIGNITE).add(ModBlocks.ligniteOre.get())
        tag(NuclearTags.Blocks.ORES_ASBESTOS).add(ModBlocks.asbestosOre.get())
        tag(NuclearTags.Blocks.ORES_RARE_EARTH).add(ModBlocks.rareEarthOre.get())
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
                ModBlocks.redCopperBlock.get(), ModBlocks.advancedAlloyBlock.get(),
                ModBlocks.trinititeBlock.get(), ModBlocks.schrabidiumBlock.get(),
                ModBlocks.soliniumBlock.get(), ModBlocks.schrabidiumFuelBlock.get(),
                ModBlocks.euphemiumBlock.get(), ModBlocks.magnetizedTungstenBlock.get(),
                ModBlocks.combineSteelBlock.get(), ModBlocks.starmetalBlock.get(),
                ModBlocks.australiumBlock.get(), ModBlocks.weidaniumBlock.get(),
                ModBlocks.reiiumBlock.get(), ModBlocks.unobtainiumBlock.get(),
                ModBlocks.daffergonBlock.get(), ModBlocks.verticiumBlock.get(),
                ModBlocks.hazmatBlock.get()
            )

        tag(NuclearTags.Blocks.STORAGE_BLOCKS_URANIUM).add(
            ModBlocks.uraniumBlock.get(), ModBlocks.u233Block.get(),
            ModBlocks.u235Block.get(), ModBlocks.u238Block.get(),
            ModBlocks.uraniumFuelBlock.get()
        )
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_NEPTUNIUM).add(ModBlocks.neptuniumBlock.get())
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_MOX).add(ModBlocks.moxFuelBlock.get())
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_PLUTONIUM).add(
            ModBlocks.plutoniumBlock.get(), ModBlocks.pu238Block.get(),
            ModBlocks.pu239Block.get(), ModBlocks.pu240Block.get(),
            ModBlocks.plutoniumFuelBlock.get()
        )
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_THORIUM).add(ModBlocks.thoriumBlock.get())
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_TITANIUM).add(ModBlocks.titaniumBlock.get())
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_SULFUR).add(ModBlocks.sulfurBlock.get())
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_NITER).add(ModBlocks.niterBlock.get())
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_COPPER).add(ModBlocks.copperBlock.get())
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_TUNGSTEN).add(ModBlocks.tungstenBlock.get())
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_ALUMINIUM).add(ModBlocks.aluminiumBlock.get())
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_FLUORITE).add(ModBlocks.fluoriteBlock.get())
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_BERYLLIUM).add(ModBlocks.berylliumBlock.get())
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_COBALT).add(ModBlocks.cobaltBlock.get())
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_STEEL).add(ModBlocks.steelBlock.get())
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_LEAD).add(ModBlocks.leadBlock.get())
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_LITHIUM).add(ModBlocks.lithiumBlock.get())
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_PHOSPHORUS).add(ModBlocks.whitePhosphorusBlock.get(), ModBlocks.redPhosphorusBlock.get())
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_YELLOWCAKE).add(ModBlocks.yellowcakeBlock.get())
        tag(NuclearTags.Blocks.STORAGE_BLOCKS_SCRAP).add(ModBlocks.scrapBlock.get(), ModBlocks.electricalScrapBlock.get())
        tag(NuclearTags.Blocks.STORAGE_BLOCK_ASBESTOS).add(ModBlocks.asbestosBlock.get())
        tag(NuclearTags.Blocks.STORAGE_BLOCK_NUCLEAR_WASTE).add(ModBlocks.nuclearWasteBlock.get())
    }

    private fun miscTags() {
        tag(Tags.Blocks.SAND).addTag(NuclearTags.Blocks.SAND_OIL)
        tag(NuclearTags.Blocks.SAND_OIL).add(ModBlocks.oilSand.get())
        tag(NuclearTags.Blocks.GLOWING_MUSHROOM_GROW_BLOCK).add(ModBlocks.deadGrass.get(), ModBlocks.glowingMycelium.get())
        tag(NuclearTags.Blocks.GLOWING_MYCELIUM_SPREADABLE).addTag(Tags.Blocks.DIRT).add(ModBlocks.deadGrass.get(), Blocks.GRASS_PATH)
    }
}
