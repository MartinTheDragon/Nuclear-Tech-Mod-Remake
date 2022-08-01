package at.martinthedragon.nucleartech.datagen.tag

import at.martinthedragon.nucleartech.block.NTechBlocks
import at.martinthedragon.nucleartech.NTechTags
import at.martinthedragon.nucleartech.NuclearTech
import net.minecraft.data.DataGenerator
import net.minecraft.data.tags.BlockTagsProvider
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Blocks
import net.minecraftforge.common.Tags
import net.minecraftforge.common.data.ExistingFileHelper

class BlockTagProvider(dataGenerator: DataGenerator, existingFileHelper: ExistingFileHelper) : BlockTagsProvider(dataGenerator, NuclearTech.MODID, existingFileHelper) {
    override fun getName(): String = "Nuclear Tech Mod Block Tags"

    override fun addTags() {
        oreTags()
        storageBlockTags()
        miscTags()
    }

    private fun oreTags() = with(NTechTags.Blocks) {
        tag(Tags.Blocks.ORES)
            .addTags(ORES_ALUMINIUM, ORES_ASBESTOS, ORES_AUSTRALIUM, ORES_BERYLLIUM, ORES_COBALT, ORES_DAFFERGON, ORES_FLUORITE, ORES_LEAD, ORES_LIGNITE, ORES_NITER, ORES_OIL, ORES_PHOSPHORUS, ORES_PLUTONIUM, ORES_RARE_EARTH, ORES_REIIUM, ORES_SCHRABIDIUM, ORES_STARMETAL, ORES_SULFUR, ORES_THORIUM, ORES_TITANIUM, ORES_TRINITITE, ORES_TRIXITE, ORES_TUNGSTEN, ORES_UNOBTAINIUM, ORES_URANIUM, ORES_VERTICIUM, ORES_WEIDANIUM)
            .add(NTechBlocks.meteorUraniumOre, NTechBlocks.meteorThoriumOre, NTechBlocks.meteorTitaniumOre, NTechBlocks.meteorSulfurOre, NTechBlocks.meteorCopperOre, NTechBlocks.meteorTungstenOre, NTechBlocks.meteorAluminiumOre, NTechBlocks.meteorLeadOre, NTechBlocks.meteorLithiumOre)

        tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(NTechBlocks.aluminiumOre, NTechBlocks.asbestosOre, NTechBlocks.australianOre, NTechBlocks.berylliumOre, NTechBlocks.cobaltOre, NTechBlocks.dellite, NTechBlocks.fluoriteOre, NTechBlocks.leadOre, NTechBlocks.ligniteOre, NTechBlocks.niterOre, NTechBlocks.oilDeposit, NTechBlocks.rareEarthOre, NTechBlocks.reiite, NTechBlocks.schrabidiumOre, NTechBlocks.sulfurOre, NTechBlocks.thoriumOre, NTechBlocks.titaniumOre, NTechBlocks.tungstenOre, NTechBlocks.brightblendeOre, NTechBlocks.uraniumOre, NTechBlocks.scorchedUraniumOre, NTechBlocks.dollarGreenMineral, NTechBlocks.weidite)
        tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).add(NTechBlocks.deepslateAluminiumOre, NTechBlocks.deepslateAsbestosOre, NTechBlocks.deepslateAustralianOre, NTechBlocks.deepslateBerylliumOre, NTechBlocks.deepslateCobaltOre, NTechBlocks.deepslateFluoriteOre, NTechBlocks.deepslateLeadOre, NTechBlocks.deepslateNiterOre, NTechBlocks.deepslateOilDeposit, NTechBlocks.deepslateRareEarthOre, NTechBlocks.deepslateSchrabidiumOre, NTechBlocks.deepslateSulfurOre, NTechBlocks.deepslateThoriumOre, NTechBlocks.deepslateTitaniumOre, NTechBlocks.deepslateTungstenOre, NTechBlocks.deepslateUraniumOre, NTechBlocks.scorchedDeepslateUraniumOre)
        tag(Tags.Blocks.ORES_IN_GROUND_NETHERRACK).add(NTechBlocks.netherPhosphorusOre, NTechBlocks.netherPlutoniumOre, NTechBlocks.netherSchrabidiumOre, NTechBlocks.netherSulfurOre, NTechBlocks.netherTungstenOre, NTechBlocks.netherUraniumOre, NTechBlocks.scorchedNetherUraniumOre)
        tag(ORES_IN_GROUND_END_STONE).add(NTechBlocks.trixite)

        tag(ORES_ALUMINIUM).add(NTechBlocks.aluminiumOre, NTechBlocks.deepslateAluminiumOre)
        tag(ORES_ASBESTOS).add(NTechBlocks.asbestosOre, NTechBlocks.deepslateAsbestosOre)
        tag(ORES_AUSTRALIUM).add(NTechBlocks.australianOre, NTechBlocks.deepslateAustralianOre)
        tag(ORES_BERYLLIUM).add(NTechBlocks.berylliumOre, NTechBlocks.deepslateBerylliumOre)
        tag(ORES_COBALT).add(NTechBlocks.cobaltOre, NTechBlocks.deepslateCobaltOre)
        tag(ORES_DAFFERGON).add(NTechBlocks.dellite)
        tag(ORES_FLUORITE).add(NTechBlocks.fluoriteOre, NTechBlocks.deepslateFluoriteOre)
        tag(ORES_LEAD).add(NTechBlocks.leadOre, NTechBlocks.deepslateLeadOre)
        tag(ORES_LIGNITE).add(NTechBlocks.ligniteOre)
        tag(ORES_NITER).add(NTechBlocks.niterOre, NTechBlocks.deepslateNiterOre)
        tag(ORES_OIL).add(NTechBlocks.oilDeposit, NTechBlocks.deepslateOilDeposit)
        tag(ORES_PHOSPHORUS).add(NTechBlocks.netherPhosphorusOre)
        tag(ORES_PLUTONIUM).add(NTechBlocks.netherPlutoniumOre)
        tag(ORES_RARE_EARTH).add(NTechBlocks.rareEarthOre, NTechBlocks.deepslateRareEarthOre)
        tag(ORES_REIIUM).add(NTechBlocks.reiite)
        tag(ORES_SCHRABIDIUM).add(NTechBlocks.schrabidiumOre, NTechBlocks.deepslateSchrabidiumOre, NTechBlocks.netherSchrabidiumOre)
        tag(ORES_STARMETAL).add(NTechBlocks.starmetalOre)
        tag(ORES_SULFUR).add(NTechBlocks.sulfurOre, NTechBlocks.deepslateSulfurOre, NTechBlocks.netherSulfurOre)
        tag(ORES_THORIUM).add(NTechBlocks.thoriumOre, NTechBlocks.deepslateThoriumOre)
        tag(ORES_TITANIUM).add(NTechBlocks.titaniumOre, NTechBlocks.deepslateTitaniumOre)
        tag(ORES_TRINITITE).add(NTechBlocks.trinitite, NTechBlocks.redTrinitite)
        tag(ORES_TRIXITE).add(NTechBlocks.trixite)
        tag(ORES_TUNGSTEN).add(NTechBlocks.tungstenOre, NTechBlocks.deepslateTungstenOre, NTechBlocks.netherTungstenOre)
        tag(ORES_UNOBTAINIUM).add(NTechBlocks.brightblendeOre)
        tag(ORES_URANIUM).add(NTechBlocks.uraniumOre, NTechBlocks.scorchedUraniumOre, NTechBlocks.deepslateUraniumOre, NTechBlocks.scorchedDeepslateUraniumOre, NTechBlocks.netherUraniumOre, NTechBlocks.scorchedNetherUraniumOre)
        tag(ORES_VERTICIUM).add(NTechBlocks.dollarGreenMineral)
        tag(ORES_WEIDANIUM).add(NTechBlocks.weidite)
        return@with
    }

    private fun storageBlockTags() = with(NTechTags.Blocks) {
        tag(Tags.Blocks.STORAGE_BLOCKS)
            .addTags(STORAGE_BLOCKS_ALUMINIUM, STORAGE_BLOCKS_ASBESTOS, STORAGE_BLOCKS_AUSTRALIUM, STORAGE_BLOCKS_BERYLLIUM, STORAGE_BLOCKS_COBALT, STORAGE_BLOCKS_COMBINE_STEEL, STORAGE_BLOCKS_DAFFERGON, STORAGE_BLOCKS_DESH, STORAGE_BLOCKS_ELECTRICAL_SCRAP, STORAGE_BLOCKS_EUPHEMIUM, STORAGE_BLOCKS_FIBERGLASS, STORAGE_BLOCKS_FLUORITE, STORAGE_BLOCKS_HAZMAT, STORAGE_BLOCKS_INSULATOR, STORAGE_BLOCKS_LEAD, STORAGE_BLOCKS_LITHIUM, STORAGE_BLOCKS_MAGNETIZED_TUNGSTEN, STORAGE_BLOCKS_MOX, STORAGE_BLOCKS_NEPTUNIUM, STORAGE_BLOCKS_NITER, STORAGE_BLOCKS_NUCLEAR_WASTE, STORAGE_BLOCKS_PHOSPHORUS, STORAGE_BLOCKS_PLUTONIUM, STORAGE_BLOCKS_PLUTONIUM_FUEL, STORAGE_BLOCKS_RED_COPPER, STORAGE_BLOCKS_REIIUM, STORAGE_BLOCKS_SCHRABIDIUM, STORAGE_BLOCKS_SCHRABIDIUM_FUEL, STORAGE_BLOCKS_SCRAP, STORAGE_BLOCKS_SOLINIUM, STORAGE_BLOCKS_STARMETAL, STORAGE_BLOCKS_STEEL, STORAGE_BLOCKS_SULFUR, STORAGE_BLOCKS_THORIUM, STORAGE_BLOCKS_THORIUM_FUEL, STORAGE_BLOCKS_TITANIUM, STORAGE_BLOCKS_TRINITITE, STORAGE_BLOCKS_TUNGSTEN, STORAGE_BLOCKS_UNOBTAINIUM, STORAGE_BLOCKS_URANIUM, STORAGE_BLOCKS_URANIUM_FUEL, STORAGE_BLOCKS_VERTICIUM, STORAGE_BLOCKS_WEIDANIUM, STORAGE_BLOCKS_YELLOWCAKE)
            .add(NTechBlocks.advancedAlloyBlock)

        tag(STORAGE_BLOCKS_ALUMINIUM).add(NTechBlocks.aluminiumBlock)
        tag(STORAGE_BLOCKS_ASBESTOS).add(NTechBlocks.asbestosBlock)
        tag(STORAGE_BLOCKS_AUSTRALIUM).add(NTechBlocks.australiumBlock)
        tag(STORAGE_BLOCKS_BERYLLIUM).add(NTechBlocks.berylliumBlock)
        tag(STORAGE_BLOCKS_COBALT).add(NTechBlocks.cobaltBlock)
        tag(STORAGE_BLOCKS_COMBINE_STEEL).add(NTechBlocks.combineSteelBlock)
        tag(STORAGE_BLOCKS_DAFFERGON).add(NTechBlocks.daffergonBlock)
        tag(STORAGE_BLOCKS_DESH).add(NTechBlocks.deshReinforcedBlock)
        tag(STORAGE_BLOCKS_ELECTRICAL_SCRAP).add(NTechBlocks.electricalScrapBlock)
        tag(STORAGE_BLOCKS_EUPHEMIUM).add(NTechBlocks.euphemiumBlock)
        tag(STORAGE_BLOCKS_FIBERGLASS).add(NTechBlocks.fiberglassRoll)
        tag(STORAGE_BLOCKS_FLUORITE).add(NTechBlocks.fluoriteBlock)
        tag(STORAGE_BLOCKS_HAZMAT).add(NTechBlocks.hazmatBlock)
        tag(STORAGE_BLOCKS_INSULATOR).add(NTechBlocks.insulatorRoll)
        tag(STORAGE_BLOCKS_LEAD).add(NTechBlocks.leadBlock)
        tag(STORAGE_BLOCKS_LITHIUM).add(NTechBlocks.lithiumBlock)
        tag(STORAGE_BLOCKS_MAGNETIZED_TUNGSTEN).add(NTechBlocks.magnetizedTungstenBlock)
        tag(STORAGE_BLOCKS_MOX).add(NTechBlocks.moxFuelBlock)
        tag(STORAGE_BLOCKS_NEPTUNIUM).add(NTechBlocks.neptuniumBlock)
        tag(STORAGE_BLOCKS_NITER).add(NTechBlocks.niterBlock)
        tag(STORAGE_BLOCKS_NUCLEAR_WASTE).add(NTechBlocks.nuclearWasteBlock)
        tag(STORAGE_BLOCKS_PHOSPHORUS).add(NTechBlocks.whitePhosphorusBlock, NTechBlocks.redPhosphorusBlock)
        tag(STORAGE_BLOCKS_PLUTONIUM).add(NTechBlocks.plutoniumBlock)
        tag(STORAGE_BLOCKS_PLUTONIUM_FUEL).add(NTechBlocks.plutoniumFuelBlock)
        tag(STORAGE_BLOCKS_RED_COPPER).add(NTechBlocks.redCopperBlock)
        tag(STORAGE_BLOCKS_REIIUM).add(NTechBlocks.reiiumBlock)
        tag(STORAGE_BLOCKS_SCHRABIDIUM).add(NTechBlocks.schrabidiumBlock)
        tag(STORAGE_BLOCKS_SCHRABIDIUM_FUEL).add(NTechBlocks.schrabidiumFuelBlock)
        tag(STORAGE_BLOCKS_SCRAP).add(NTechBlocks.scrapBlock)
        tag(STORAGE_BLOCKS_SOLINIUM).add(NTechBlocks.soliniumBlock)
        tag(STORAGE_BLOCKS_STARMETAL).add(NTechBlocks.starmetalBlock)
        tag(STORAGE_BLOCKS_STEEL).add(NTechBlocks.steelBlock)
        tag(STORAGE_BLOCKS_SULFUR).add(NTechBlocks.sulfurBlock)
        tag(STORAGE_BLOCKS_THORIUM).add(NTechBlocks.thoriumBlock)
        tag(STORAGE_BLOCKS_THORIUM_FUEL).add(NTechBlocks.thoriumFuelBlock)
        tag(STORAGE_BLOCKS_TITANIUM).add(NTechBlocks.titaniumBlock)
        tag(STORAGE_BLOCKS_TRINITITE).add(NTechBlocks.trinititeBlock)
        tag(STORAGE_BLOCKS_TUNGSTEN).add(NTechBlocks.tungstenBlock)
        tag(STORAGE_BLOCKS_UNOBTAINIUM).add(NTechBlocks.unobtainiumBlock)
        tag(STORAGE_BLOCKS_URANIUM).add(NTechBlocks.uraniumBlock)
        tag(STORAGE_BLOCKS_URANIUM_FUEL).add(NTechBlocks.uraniumFuelBlock)
        tag(STORAGE_BLOCKS_VERTICIUM).add(NTechBlocks.verticiumBlock)
        tag(STORAGE_BLOCKS_WEIDANIUM).add(NTechBlocks.weidaniumBlock)
        tag(STORAGE_BLOCKS_YELLOWCAKE).add(NTechBlocks.yellowcakeBlock)
        return@with
    }

    private fun miscTags() {
        tag(Tags.Blocks.SAND).addTag(NTechTags.Blocks.SAND_OIL)
        tag(NTechTags.Blocks.SAND_OIL).add(NTechBlocks.oilSand)
        tag(NTechTags.Blocks.GLOWING_MUSHROOM_GROW_BLOCK).add(NTechBlocks.deadGrass, NTechBlocks.glowingMycelium)
        tag(NTechTags.Blocks.GLOWING_MYCELIUM_SPREADABLE).addTag(BlockTags.DIRT).add(NTechBlocks.deadGrass.get(), Blocks.DIRT_PATH)
        tag(NTechTags.Blocks.ANVIL).add(NTechBlocks.ironAnvil, NTechBlocks.leadAnvil, NTechBlocks.steelAnvil, NTechBlocks.meteoriteAnvil, NTechBlocks.starmetalAnvil, NTechBlocks.ferrouraniumAnvil, NTechBlocks.bismuthAnvil, NTechBlocks.schrabidateAnvil, NTechBlocks.dineutroniumAnvil, NTechBlocks.murkyAnvil)
    }
}
