package at.martinthedragon.nucleartech.datagen

import at.martinthedragon.nucleartech.block.NTechBlocks
import at.martinthedragon.nucleartech.NuclearTech
import net.minecraft.data.DataGenerator
import net.minecraft.data.tags.BlockTagsProvider
import net.minecraft.tags.BlockTags
import net.minecraftforge.common.Tags
import net.minecraftforge.common.data.ExistingFileHelper

class HarvestabilityProvider(dataGenerator: DataGenerator, existingFileHelper: ExistingFileHelper) : BlockTagsProvider(dataGenerator, NuclearTech.MODID, existingFileHelper) {
    override fun getName() = "Nuclear Tech Mod Block Harvestability"

    override fun addTags() {
        toolTypes()
        materialLevel()
    }

    private fun toolTypes() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
            NTechBlocks.uraniumOre.get(), NTechBlocks.deepslateUraniumOre.get(), NTechBlocks.netherUraniumOre.get(), NTechBlocks.scorchedNetherUraniumOre.get(), NTechBlocks.meteorUraniumOre.get(),  NTechBlocks.uraniumBlock.get(), NTechBlocks.u233Block.get(), NTechBlocks.u235Block.get(), NTechBlocks.u238Block.get(), NTechBlocks.uraniumFuelBlock.get(),
            NTechBlocks.scorchedUraniumOre.get(), NTechBlocks.scorchedDeepslateUraniumOre.get(),
            NTechBlocks.netherPlutoniumOre.get(), NTechBlocks.plutoniumBlock.get(), NTechBlocks.pu238Block.get(), NTechBlocks.pu239Block.get(), NTechBlocks.pu240Block.get(), NTechBlocks.plutoniumFuelBlock.get(),
            NTechBlocks.thoriumOre.get(), NTechBlocks.deepslateThoriumOre.get(), NTechBlocks.meteorThoriumOre.get(), NTechBlocks.thoriumBlock.get(), NTechBlocks.thoriumFuelBlock.get(),
            NTechBlocks.titaniumOre.get(), NTechBlocks.deepslateTitaniumOre.get(), NTechBlocks.meteorTitaniumOre.get(), NTechBlocks.titaniumBlock.get(),
            NTechBlocks.sulfurOre.get(), NTechBlocks.deepslateSulfurOre.get(), NTechBlocks.netherSulfurOre.get(), NTechBlocks.meteorSulfurOre.get(), NTechBlocks.sulfurBlock.get(),
            NTechBlocks.niterOre.get(), NTechBlocks.deepslateNiterOre.get(), NTechBlocks.niterBlock.get(),
            NTechBlocks.tungstenOre.get(), NTechBlocks.deepslateTungstenOre.get(), NTechBlocks.netherTungstenOre.get(), NTechBlocks.meteorTungstenOre.get(), NTechBlocks.tungstenBlock.get(),
            NTechBlocks.aluminiumOre.get(), NTechBlocks.deepslateAluminiumOre.get(), NTechBlocks.meteorAluminiumOre.get(), NTechBlocks.aluminiumBlock.get(),
            NTechBlocks.fluoriteOre.get(), NTechBlocks.deepslateFluoriteOre.get(), NTechBlocks.fluoriteBlock.get(),
            NTechBlocks.berylliumOre.get(), NTechBlocks.deepslateBerylliumOre.get(), NTechBlocks.berylliumBlock.get(),
            NTechBlocks.leadOre.get(), NTechBlocks.deepslateLeadOre.get(), NTechBlocks.meteorLeadOre.get(), NTechBlocks.leadBlock.get(),
            NTechBlocks.oilDeposit.get(), NTechBlocks.emptyOilDeposit.get(), NTechBlocks.deepslateOilDeposit.get(), NTechBlocks.emptyDeepslateOilDeposit.get(),
            NTechBlocks.ligniteOre.get(),
            NTechBlocks.asbestosOre.get(), NTechBlocks.deepslateAsbestosOre.get(), NTechBlocks.asbestosBlock.get(),
            NTechBlocks.schrabidiumOre.get(), NTechBlocks.deepslateSchrabidiumOre.get(), NTechBlocks.netherSchrabidiumOre.get(),  NTechBlocks.schrabidiumBlock.get(), NTechBlocks.soliniumBlock.get(), NTechBlocks.schrabidiumFuelBlock.get(),
            NTechBlocks.australianOre.get(), NTechBlocks.deepslateAustralianOre.get(), NTechBlocks.australiumBlock.get(),
            NTechBlocks.weidite.get(), NTechBlocks.weidaniumBlock.get(),
            NTechBlocks.reiite.get(), NTechBlocks.reiiumBlock.get(),
            NTechBlocks.brightblendeOre.get(), NTechBlocks.unobtainiumBlock.get(),
            NTechBlocks.dellite.get(), NTechBlocks.daffergonBlock.get(),
            NTechBlocks.dollarGreenMineral.get(), NTechBlocks.verticiumBlock.get(),
            NTechBlocks.rareEarthOre.get(), NTechBlocks.deepslateRareEarthOre.get(),
            NTechBlocks.cobaltOre.get(), NTechBlocks.deepslateCobaltOre.get(),
            NTechBlocks.netherPhosphorusOre.get(),
            NTechBlocks.meteorCopperOre.get(),
            NTechBlocks.meteorLithiumOre.get(), NTechBlocks.lithiumBlock.get(),
            NTechBlocks.starmetalOre.get(), NTechBlocks.starmetalBlock.get(),
            NTechBlocks.trixite.get(),
            NTechBlocks.neptuniumBlock.get(),
            NTechBlocks.moxFuelBlock.get(),
            NTechBlocks.redCopperBlock.get(),
            NTechBlocks.advancedAlloyBlock.get(),
            NTechBlocks.cobaltBlock.get(),
            NTechBlocks.steelBlock.get(),
            NTechBlocks.electricalScrapBlock.get(),
            NTechBlocks.trinititeBlock.get(),
            NTechBlocks.nuclearWasteBlock.get(),
            NTechBlocks.euphemiumBlock.get(),
            NTechBlocks.schrabidiumCluster.get(), NTechBlocks.euphemiumEtchedSchrabidiumCluster.get(),
            NTechBlocks.magnetizedTungstenBlock.get(),
            NTechBlocks.combineSteelBlock.get(),
            NTechBlocks.deshReinforcedBlock.get(),
            NTechBlocks.titaniumDecoBlock.get(), NTechBlocks.redCopperDecoBlock.get(), NTechBlocks.tungstenDecoBlock.get(), NTechBlocks.aluminiumDecoBlock.get(), NTechBlocks.steelDecoBlock.get(), NTechBlocks.leadDecoBlock.get(), NTechBlocks.berylliumDecoBlock.get(),
            NTechBlocks.asbestosRoof.get(),
            NTechBlocks.slakedSellafite.get(), NTechBlocks.sellafite.get(), NTechBlocks.hotSellafite.get(), NTechBlocks.boilingSellafite.get(), NTechBlocks.blazingSellafite.get(), NTechBlocks.infernalSellafite.get(), NTechBlocks.sellafiteCorium.get(),
            NTechBlocks.siren.get(),
            NTechBlocks.safe.get(),
            NTechBlocks.steamPressBase.get(), NTechBlocks.steamPressFrame.get(), NTechBlocks.steamPressFrame.get(), NTechBlocks.blastFurnace.get(),
            NTechBlocks.combustionGenerator.get(),
            NTechBlocks.electricFurnace.get(),
            NTechBlocks.shredder.get(),
            NTechBlocks.ironAnvil.get(), NTechBlocks.leadAnvil.get(), NTechBlocks.steelAnvil.get(), NTechBlocks.meteoriteAnvil.get(), NTechBlocks.starmetalAnvil.get(), NTechBlocks.ferrouraniumAnvil.get(), NTechBlocks.bismuthAnvil.get(), NTechBlocks.schrabidateAnvil.get(), NTechBlocks.dineutroniumAnvil.get(), NTechBlocks.murkyAnvil.get(),
            NTechBlocks.assembler.get(),
            NTechBlocks.littleBoy.get(), NTechBlocks.fatMan.get(),
            NTechBlocks.genericMultiBlockPart.get(), NTechBlocks.genericMultiBlockPort.get(),
        )
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
            NTechBlocks.oilSand.get(),
            NTechBlocks.whitePhosphorusBlock.get(),
            NTechBlocks.redPhosphorusBlock.get(),
            NTechBlocks.yellowcakeBlock.get(),
            NTechBlocks.scrapBlock.get(),
            NTechBlocks.deadGrass.get(),
            NTechBlocks.glowingMycelium.get(),
            NTechBlocks.trinitite.get(),
            NTechBlocks.redTrinitite.get(),
        )
        tag(BlockTags.MINEABLE_WITH_AXE).add(
            NTechBlocks.insulatorRoll.get(),
            NTechBlocks.fiberglassRoll.get(),
            NTechBlocks.hazmatBlock.get(),
            NTechBlocks.glowingMushroomBlock.get(),
            NTechBlocks.glowingMushroomStem.get(),
            NTechBlocks.charredLog.get(),
            NTechBlocks.charredPlanks.get(),
        )
    }

    private fun materialLevel() {
        tag(Tags.Blocks.NEEDS_WOOD_TOOL).add(
            NTechBlocks.ligniteOre.get(),
            NTechBlocks.whitePhosphorusBlock.get(),
            NTechBlocks.redPhosphorusBlock.get(),
        )
        tag(BlockTags.NEEDS_STONE_TOOL).add(
            NTechBlocks.sulfurOre.get(), NTechBlocks.deepslateSulfurOre.get(), NTechBlocks.netherSulfurOre.get(), NTechBlocks.sulfurBlock.get(),
            NTechBlocks.niterOre.get(), NTechBlocks.deepslateNiterOre.get(), NTechBlocks.niterBlock.get(),
            NTechBlocks.aluminiumOre.get(), NTechBlocks.deepslateAluminiumOre.get(), NTechBlocks.aluminiumBlock.get(),
            NTechBlocks.fluoriteOre.get(), NTechBlocks.deepslateFluoriteOre.get(), NTechBlocks.fluoriteBlock.get(),
            NTechBlocks.netherPhosphorusOre.get(),
            NTechBlocks.oilDeposit.get(), NTechBlocks.emptyOilDeposit.get(), NTechBlocks.deepslateOilDeposit.get(), NTechBlocks.emptyDeepslateOilDeposit.get(),
            NTechBlocks.redCopperBlock.get(),
            NTechBlocks.lithiumBlock.get(),
            NTechBlocks.electricalScrapBlock.get(),
            NTechBlocks.trinititeBlock.get(),
            NTechBlocks.nuclearWasteBlock.get(),
            NTechBlocks.titaniumDecoBlock.get(), NTechBlocks.redCopperDecoBlock.get(), NTechBlocks.tungstenDecoBlock.get(), NTechBlocks.aluminiumDecoBlock.get(), NTechBlocks.steelDecoBlock.get(), NTechBlocks.leadDecoBlock.get(), NTechBlocks.berylliumDecoBlock.get(),
            NTechBlocks.asbestosRoof.get(),
            NTechBlocks.slakedSellafite.get(), NTechBlocks.sellafite.get(), NTechBlocks.hotSellafite.get(), NTechBlocks.boilingSellafite.get(), NTechBlocks.blazingSellafite.get(), NTechBlocks.infernalSellafite.get(), NTechBlocks.sellafiteCorium.get(),
            NTechBlocks.siren.get(),
            NTechBlocks.safe.get(),
            NTechBlocks.steamPressBase.get(), NTechBlocks.steamPressFrame.get(), NTechBlocks.steamPressTop.get(),
            NTechBlocks.blastFurnace.get(),
            NTechBlocks.combustionGenerator.get(),
            NTechBlocks.electricFurnace.get(),
            NTechBlocks.shredder.get(),
            NTechBlocks.ironAnvil.get(),
            NTechBlocks.leadAnvil.get(),
            NTechBlocks.assembler.get(),
            NTechBlocks.genericMultiBlockPart.get(), NTechBlocks.genericMultiBlockPort.get()
        )
        tag(BlockTags.NEEDS_IRON_TOOL).add(
            NTechBlocks.uraniumOre.get(), NTechBlocks.deepslateUraniumOre.get(), NTechBlocks.uraniumBlock.get(), NTechBlocks.u233Block.get(), NTechBlocks.u235Block.get(), NTechBlocks.u238Block.get(), NTechBlocks.uraniumFuelBlock.get(),
            NTechBlocks.scorchedUraniumOre.get(), NTechBlocks.scorchedDeepslateUraniumOre.get(),
            NTechBlocks.netherPlutoniumOre.get(), NTechBlocks.plutoniumBlock.get(), NTechBlocks.pu238Block.get(), NTechBlocks.pu239Block.get(), NTechBlocks.pu240Block.get(), NTechBlocks.plutoniumFuelBlock.get(),
            NTechBlocks.thoriumOre.get(), NTechBlocks.deepslateThoriumOre.get(), NTechBlocks.thoriumBlock.get(), NTechBlocks.thoriumFuelBlock.get(),
            NTechBlocks.titaniumOre.get(), NTechBlocks.deepslateTitaniumOre.get(), NTechBlocks.titaniumBlock.get(),
            NTechBlocks.tungstenOre.get(), NTechBlocks.deepslateTungstenOre.get(), NTechBlocks.netherTungstenOre.get(), NTechBlocks.tungstenBlock.get(),
            NTechBlocks.berylliumOre.get(), NTechBlocks.deepslateBerylliumOre.get(), NTechBlocks.berylliumBlock.get(),
            NTechBlocks.cobaltBlock.get(),
            NTechBlocks.leadOre.get(), NTechBlocks.deepslateLeadOre.get(),
            NTechBlocks.asbestosOre.get(), NTechBlocks.deepslateAsbestosOre.get(),
            NTechBlocks.rareEarthOre.get(), NTechBlocks.deepslateRareEarthOre.get(),
            NTechBlocks.cobaltOre.get(), NTechBlocks.deepslateCobaltOre.get(),
            NTechBlocks.trixite.get(),
            NTechBlocks.neptuniumBlock.get(),
            NTechBlocks.moxFuelBlock.get(),
            NTechBlocks.advancedAlloyBlock.get(),
            NTechBlocks.steelBlock.get(),
            NTechBlocks.asbestosBlock.get(),
            NTechBlocks.steelAnvil.get(),
        )
        tag(BlockTags.NEEDS_DIAMOND_TOOL).add(
            NTechBlocks.australianOre.get(), NTechBlocks.deepslateAustralianOre.get(), NTechBlocks.australiumBlock.get(),
            NTechBlocks.weidite.get(), NTechBlocks.weidaniumBlock.get(),
            NTechBlocks.reiite.get(), NTechBlocks.reiiumBlock.get(),
            NTechBlocks.brightblendeOre.get(), NTechBlocks.unobtainiumBlock.get(),
            NTechBlocks.dellite.get(), NTechBlocks.daffergonBlock.get(),
            NTechBlocks.dollarGreenMineral.get(), NTechBlocks.verticiumBlock.get(),
            NTechBlocks.meteorUraniumOre.get(),
            NTechBlocks.meteorThoriumOre.get(),
            NTechBlocks.meteorTitaniumOre.get(),
            NTechBlocks.meteorSulfurOre.get(),
            NTechBlocks.meteorCopperOre.get(),
            NTechBlocks.meteorTungstenOre.get(),
            NTechBlocks.meteorAluminiumOre.get(),
            NTechBlocks.meteorLeadOre.get(),
            NTechBlocks.meteorLithiumOre.get(),
            NTechBlocks.starmetalOre.get(), NTechBlocks.starmetalBlock.get(),
            NTechBlocks.magnetizedTungstenBlock.get(),
            NTechBlocks.combineSteelBlock.get(),
            NTechBlocks.deshReinforcedBlock.get(),
            NTechBlocks.meteoriteAnvil.get(),
            NTechBlocks.starmetalAnvil.get(),
            NTechBlocks.ferrouraniumAnvil.get(),
            NTechBlocks.bismuthAnvil.get(),
        )
        tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(
            NTechBlocks.schrabidiumOre.get(), NTechBlocks.deepslateSchrabidiumOre.get(), NTechBlocks.netherSchrabidiumOre.get(), NTechBlocks.schrabidiumBlock.get(),
            NTechBlocks.euphemiumBlock.get(),
            NTechBlocks.schrabidiumCluster.get(), NTechBlocks.euphemiumEtchedSchrabidiumCluster.get(),
            NTechBlocks.schrabidateAnvil.get(),
            NTechBlocks.dineutroniumAnvil.get(),
            NTechBlocks.murkyAnvil.get(),
        )
    }
}
