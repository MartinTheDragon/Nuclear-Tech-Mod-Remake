package at.martinthedragon.nucleartech.datagen.tags

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.NuclearTags
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

    private fun oreTags() = with(NuclearTags.Blocks) {
        tag(Tags.Blocks.ORES)
            .addTags(ORES_ALUMINIUM, ORES_ASBESTOS, ORES_AUSTRALIUM, ORES_BERYLLIUM, ORES_COBALT, ORES_DAFFERGON, ORES_FLUORITE, ORES_LEAD, ORES_LIGNITE, ORES_NITER, ORES_OIL, ORES_PHOSPHORUS, ORES_PLUTONIUM, ORES_RARE_EARTH, ORES_REIIUM, ORES_SCHRABIDIUM, ORES_STARMETAL, ORES_SULFUR, ORES_THORIUM, ORES_TITANIUM, ORES_TRINITITE, ORES_TRIXITE, ORES_TUNGSTEN, ORES_UNOBTAINIUM, ORES_URANIUM, ORES_VERTICIUM, ORES_WEIDANIUM)
            .add(ModBlocks.meteorUraniumOre, ModBlocks.meteorThoriumOre, ModBlocks.meteorTitaniumOre, ModBlocks.meteorSulfurOre, ModBlocks.meteorCopperOre, ModBlocks.meteorTungstenOre, ModBlocks.meteorAluminiumOre, ModBlocks.meteorLeadOre, ModBlocks.meteorLithiumOre)

        tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(ModBlocks.aluminiumOre, ModBlocks.asbestosOre, ModBlocks.australianOre, ModBlocks.berylliumOre, ModBlocks.cobaltOre, ModBlocks.dellite, ModBlocks.fluoriteOre, ModBlocks.leadOre, ModBlocks.ligniteOre, ModBlocks.niterOre, ModBlocks.oilDeposit, ModBlocks.rareEarthOre, ModBlocks.reiite, ModBlocks.schrabidiumOre, ModBlocks.sulfurOre, ModBlocks.thoriumOre, ModBlocks.titaniumOre, ModBlocks.tungstenOre, ModBlocks.brightblendeOre, ModBlocks.uraniumOre, ModBlocks.scorchedUraniumOre, ModBlocks.dollarGreenMineral, ModBlocks.weidite)
        tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).add(ModBlocks.deepslateAluminiumOre, ModBlocks.deepslateAsbestosOre, ModBlocks.deepslateAustralianOre, ModBlocks.deepslateBerylliumOre, ModBlocks.deepslateCobaltOre, ModBlocks.deepslateFluoriteOre, ModBlocks.deepslateLeadOre, ModBlocks.deepslateNiterOre, ModBlocks.deepslateOilDeposit, ModBlocks.deepslateRareEarthOre, ModBlocks.deepslateSchrabidiumOre, ModBlocks.deepslateSulfurOre, ModBlocks.deepslateThoriumOre, ModBlocks.deepslateTitaniumOre, ModBlocks.deepslateTungstenOre, ModBlocks.deepslateUraniumOre, ModBlocks.scorchedDeepslateUraniumOre)
        tag(Tags.Blocks.ORES_IN_GROUND_NETHERRACK).add(ModBlocks.netherPhosphorusOre, ModBlocks.netherPlutoniumOre, ModBlocks.netherSchrabidiumOre, ModBlocks.netherSulfurOre, ModBlocks.netherTungstenOre, ModBlocks.netherUraniumOre, ModBlocks.scorchedNetherUraniumOre)
        tag(ORES_IN_GROUND_END_STONE).add(ModBlocks.trixite)

        tag(ORES_ALUMINIUM).add(ModBlocks.aluminiumOre, ModBlocks.deepslateAluminiumOre)
        tag(ORES_ASBESTOS).add(ModBlocks.asbestosOre, ModBlocks.deepslateAsbestosOre)
        tag(ORES_AUSTRALIUM).add(ModBlocks.australianOre, ModBlocks.deepslateAustralianOre)
        tag(ORES_BERYLLIUM).add(ModBlocks.berylliumOre, ModBlocks.deepslateBerylliumOre)
        tag(ORES_COBALT).add(ModBlocks.cobaltOre, ModBlocks.deepslateCobaltOre)
        tag(ORES_DAFFERGON).add(ModBlocks.dellite)
        tag(ORES_FLUORITE).add(ModBlocks.fluoriteOre, ModBlocks.deepslateFluoriteOre)
        tag(ORES_LEAD).add(ModBlocks.leadOre, ModBlocks.deepslateLeadOre)
        tag(ORES_LIGNITE).add(ModBlocks.ligniteOre)
        tag(ORES_NITER).add(ModBlocks.niterOre, ModBlocks.deepslateNiterOre)
        tag(ORES_OIL).add(ModBlocks.oilDeposit, ModBlocks.deepslateOilDeposit)
        tag(ORES_PHOSPHORUS).add(ModBlocks.netherPhosphorusOre)
        tag(ORES_PLUTONIUM).add(ModBlocks.netherPlutoniumOre)
        tag(ORES_RARE_EARTH).add(ModBlocks.rareEarthOre, ModBlocks.deepslateRareEarthOre)
        tag(ORES_REIIUM).add(ModBlocks.reiite)
        tag(ORES_SCHRABIDIUM).add(ModBlocks.schrabidiumOre, ModBlocks.deepslateSchrabidiumOre, ModBlocks.netherSchrabidiumOre)
        tag(ORES_STARMETAL).add(ModBlocks.starmetalOre)
        tag(ORES_SULFUR).add(ModBlocks.sulfurOre, ModBlocks.deepslateSulfurOre, ModBlocks.netherSulfurOre)
        tag(ORES_THORIUM).add(ModBlocks.thoriumOre, ModBlocks.deepslateThoriumOre)
        tag(ORES_TITANIUM).add(ModBlocks.titaniumOre, ModBlocks.deepslateTitaniumOre)
        tag(ORES_TRINITITE).add(ModBlocks.trinitite, ModBlocks.redTrinitite)
        tag(ORES_TRIXITE).add(ModBlocks.trixite)
        tag(ORES_TUNGSTEN).add(ModBlocks.tungstenOre, ModBlocks.deepslateTungstenOre, ModBlocks.netherTungstenOre)
        tag(ORES_UNOBTAINIUM).add(ModBlocks.brightblendeOre)
        tag(ORES_URANIUM).add(ModBlocks.uraniumOre, ModBlocks.scorchedUraniumOre, ModBlocks.deepslateUraniumOre, ModBlocks.scorchedDeepslateUraniumOre, ModBlocks.netherUraniumOre, ModBlocks.scorchedNetherUraniumOre)
        tag(ORES_VERTICIUM).add(ModBlocks.dollarGreenMineral)
        tag(ORES_WEIDANIUM).add(ModBlocks.weidite)
        return@with
    }

    private fun storageBlockTags() = with(NuclearTags.Blocks) {
        tag(Tags.Blocks.STORAGE_BLOCKS)
            .addTags(STORAGE_BLOCKS_ALUMINIUM, STORAGE_BLOCKS_ASBESTOS, STORAGE_BLOCKS_AUSTRALIUM, STORAGE_BLOCKS_BERYLLIUM, STORAGE_BLOCKS_COBALT, STORAGE_BLOCKS_COMBINE_STEEL, STORAGE_BLOCKS_DAFFERGON, STORAGE_BLOCKS_DESH, STORAGE_BLOCKS_ELECTRICAL_SCRAP, STORAGE_BLOCKS_EUPHEMIUM, STORAGE_BLOCKS_FIBERGLASS, STORAGE_BLOCKS_FLUORITE, STORAGE_BLOCKS_HAZMAT, STORAGE_BLOCKS_INSULATOR, STORAGE_BLOCKS_LEAD, STORAGE_BLOCKS_LITHIUM, STORAGE_BLOCKS_MAGNETIZED_TUNGSTEN, STORAGE_BLOCKS_MOX, STORAGE_BLOCKS_NEPTUNIUM, STORAGE_BLOCKS_NITER, STORAGE_BLOCKS_NUCLEAR_WASTE, STORAGE_BLOCKS_PHOSPHORUS, STORAGE_BLOCKS_PLUTONIUM, STORAGE_BLOCKS_PLUTONIUM_FUEL, STORAGE_BLOCKS_RED_COPPER, STORAGE_BLOCKS_REIIUM, STORAGE_BLOCKS_SCHRABIDIUM, STORAGE_BLOCKS_SCHRABIDIUM_FUEL, STORAGE_BLOCKS_SCRAP, STORAGE_BLOCKS_SOLINIUM, STORAGE_BLOCKS_STARMETAL, STORAGE_BLOCKS_STEEL, STORAGE_BLOCKS_SULFUR, STORAGE_BLOCKS_THORIUM, STORAGE_BLOCKS_THORIUM_FUEL, STORAGE_BLOCKS_TITANIUM, STORAGE_BLOCKS_TRINITITE, STORAGE_BLOCKS_TUNGSTEN, STORAGE_BLOCKS_UNOBTAINIUM, STORAGE_BLOCKS_URANIUM, STORAGE_BLOCKS_URANIUM_FUEL, STORAGE_BLOCKS_VERTICIUM, STORAGE_BLOCKS_WEIDANIUM, STORAGE_BLOCKS_YELLOWCAKE)
            .add(ModBlocks.advancedAlloyBlock)

        tag(STORAGE_BLOCKS_ALUMINIUM).add(ModBlocks.aluminiumBlock)
        tag(STORAGE_BLOCKS_ASBESTOS).add(ModBlocks.asbestosBlock)
        tag(STORAGE_BLOCKS_AUSTRALIUM).add(ModBlocks.australiumBlock)
        tag(STORAGE_BLOCKS_BERYLLIUM).add(ModBlocks.berylliumBlock)
        tag(STORAGE_BLOCKS_COBALT).add(ModBlocks.cobaltBlock)
        tag(STORAGE_BLOCKS_COMBINE_STEEL).add(ModBlocks.combineSteelBlock)
        tag(STORAGE_BLOCKS_DAFFERGON).add(ModBlocks.daffergonBlock)
        tag(STORAGE_BLOCKS_DESH).add(ModBlocks.deshReinforcedBlock)
        tag(STORAGE_BLOCKS_ELECTRICAL_SCRAP).add(ModBlocks.electricalScrapBlock)
        tag(STORAGE_BLOCKS_EUPHEMIUM).add(ModBlocks.euphemiumBlock)
        tag(STORAGE_BLOCKS_FIBERGLASS).add(ModBlocks.fiberglassRoll)
        tag(STORAGE_BLOCKS_FLUORITE).add(ModBlocks.fluoriteBlock)
        tag(STORAGE_BLOCKS_HAZMAT).add(ModBlocks.hazmatBlock)
        tag(STORAGE_BLOCKS_INSULATOR).add(ModBlocks.insulatorRoll)
        tag(STORAGE_BLOCKS_LEAD).add(ModBlocks.leadBlock)
        tag(STORAGE_BLOCKS_LITHIUM).add(ModBlocks.lithiumBlock)
        tag(STORAGE_BLOCKS_MAGNETIZED_TUNGSTEN).add(ModBlocks.magnetizedTungstenBlock)
        tag(STORAGE_BLOCKS_MOX).add(ModBlocks.moxFuelBlock)
        tag(STORAGE_BLOCKS_NEPTUNIUM).add(ModBlocks.neptuniumBlock)
        tag(STORAGE_BLOCKS_NITER).add(ModBlocks.niterBlock)
        tag(STORAGE_BLOCKS_NUCLEAR_WASTE).add(ModBlocks.nuclearWasteBlock)
        tag(STORAGE_BLOCKS_PHOSPHORUS).add(ModBlocks.whitePhosphorusBlock, ModBlocks.redPhosphorusBlock)
        tag(STORAGE_BLOCKS_PLUTONIUM).add(ModBlocks.plutoniumBlock)
        tag(STORAGE_BLOCKS_PLUTONIUM_FUEL).add(ModBlocks.plutoniumFuelBlock)
        tag(STORAGE_BLOCKS_RED_COPPER).add(ModBlocks.redCopperBlock)
        tag(STORAGE_BLOCKS_REIIUM).add(ModBlocks.reiiumBlock)
        tag(STORAGE_BLOCKS_SCHRABIDIUM).add(ModBlocks.schrabidiumBlock)
        tag(STORAGE_BLOCKS_SCHRABIDIUM_FUEL).add(ModBlocks.schrabidiumFuelBlock)
        tag(STORAGE_BLOCKS_SCRAP).add(ModBlocks.scrapBlock)
        tag(STORAGE_BLOCKS_SOLINIUM).add(ModBlocks.soliniumBlock)
        tag(STORAGE_BLOCKS_STARMETAL).add(ModBlocks.starmetalBlock)
        tag(STORAGE_BLOCKS_STEEL).add(ModBlocks.steelBlock)
        tag(STORAGE_BLOCKS_SULFUR).add(ModBlocks.sulfurBlock)
        tag(STORAGE_BLOCKS_THORIUM).add(ModBlocks.thoriumBlock)
        tag(STORAGE_BLOCKS_THORIUM_FUEL).add(ModBlocks.thoriumFuelBlock)
        tag(STORAGE_BLOCKS_TITANIUM).add(ModBlocks.titaniumBlock)
        tag(STORAGE_BLOCKS_TRINITITE).add(ModBlocks.trinititeBlock)
        tag(STORAGE_BLOCKS_TUNGSTEN).add(ModBlocks.tungstenBlock)
        tag(STORAGE_BLOCKS_UNOBTAINIUM).add(ModBlocks.unobtainiumBlock)
        tag(STORAGE_BLOCKS_URANIUM).add(ModBlocks.uraniumBlock)
        tag(STORAGE_BLOCKS_URANIUM_FUEL).add(ModBlocks.uraniumFuelBlock)
        tag(STORAGE_BLOCKS_VERTICIUM).add(ModBlocks.verticiumBlock)
        tag(STORAGE_BLOCKS_WEIDANIUM).add(ModBlocks.weidaniumBlock)
        tag(STORAGE_BLOCKS_YELLOWCAKE).add(ModBlocks.yellowcakeBlock)
        return@with
    }

    private fun miscTags() {
        tag(Tags.Blocks.SAND).addTag(NuclearTags.Blocks.SAND_OIL)
        tag(NuclearTags.Blocks.SAND_OIL).add(ModBlocks.oilSand)
        tag(NuclearTags.Blocks.GLOWING_MUSHROOM_GROW_BLOCK).add(ModBlocks.deadGrass, ModBlocks.glowingMycelium)
        tag(NuclearTags.Blocks.GLOWING_MYCELIUM_SPREADABLE).addTag(BlockTags.DIRT).add(ModBlocks.deadGrass.get(), Blocks.DIRT_PATH)
        tag(NuclearTags.Blocks.ANVIL).add(ModBlocks.ironAnvil, ModBlocks.leadAnvil, ModBlocks.steelAnvil, ModBlocks.meteoriteAnvil, ModBlocks.starmetalAnvil, ModBlocks.ferrouraniumAnvil, ModBlocks.bismuthAnvil, ModBlocks.schrabidateAnvil, ModBlocks.dineutroniumAnvil, ModBlocks.murkyAnvil)
    }
}
