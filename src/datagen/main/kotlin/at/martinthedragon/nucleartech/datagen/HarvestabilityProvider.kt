package at.martinthedragon.nucleartech.datagen

import at.martinthedragon.nucleartech.ModBlocks
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
            ModBlocks.uraniumOre.get(), ModBlocks.deepslateUraniumOre.get(), ModBlocks.netherUraniumOre.get(), ModBlocks.scorchedNetherUraniumOre.get(), ModBlocks.meteorUraniumOre.get(),  ModBlocks.uraniumBlock.get(), ModBlocks.u233Block.get(), ModBlocks.u235Block.get(), ModBlocks.u238Block.get(), ModBlocks.uraniumFuelBlock.get(),
            ModBlocks.scorchedUraniumOre.get(), ModBlocks.scorchedDeepslateUraniumOre.get(),
            ModBlocks.netherPlutoniumOre.get(), ModBlocks.plutoniumBlock.get(), ModBlocks.pu238Block.get(), ModBlocks.pu239Block.get(), ModBlocks.pu240Block.get(), ModBlocks.plutoniumFuelBlock.get(),
            ModBlocks.thoriumOre.get(), ModBlocks.deepslateThoriumOre.get(), ModBlocks.meteorThoriumOre.get(), ModBlocks.thoriumBlock.get(), ModBlocks.thoriumFuelBlock.get(),
            ModBlocks.titaniumOre.get(), ModBlocks.deepslateTitaniumOre.get(), ModBlocks.meteorTitaniumOre.get(), ModBlocks.titaniumBlock.get(),
            ModBlocks.sulfurOre.get(), ModBlocks.deepslateSulfurOre.get(), ModBlocks.netherSulfurOre.get(), ModBlocks.meteorSulfurOre.get(), ModBlocks.sulfurBlock.get(),
            ModBlocks.niterOre.get(), ModBlocks.deepslateNiterOre.get(), ModBlocks.niterBlock.get(),
            ModBlocks.tungstenOre.get(), ModBlocks.deepslateTungstenOre.get(), ModBlocks.netherTungstenOre.get(), ModBlocks.meteorTungstenOre.get(), ModBlocks.tungstenBlock.get(),
            ModBlocks.aluminiumOre.get(), ModBlocks.deepslateAluminiumOre.get(), ModBlocks.meteorAluminiumOre.get(), ModBlocks.aluminiumBlock.get(),
            ModBlocks.fluoriteOre.get(), ModBlocks.deepslateFluoriteOre.get(), ModBlocks.fluoriteBlock.get(),
            ModBlocks.berylliumOre.get(), ModBlocks.deepslateBerylliumOre.get(), ModBlocks.berylliumBlock.get(),
            ModBlocks.leadOre.get(), ModBlocks.deepslateLeadOre.get(), ModBlocks.meteorLeadOre.get(), ModBlocks.leadBlock.get(),
            ModBlocks.oilDeposit.get(), ModBlocks.emptyOilDeposit.get(), ModBlocks.deepslateOilDeposit.get(), ModBlocks.emptyDeepslateOilDeposit.get(),
            ModBlocks.ligniteOre.get(),
            ModBlocks.asbestosOre.get(), ModBlocks.deepslateAsbestosOre.get(), ModBlocks.asbestosBlock.get(),
            ModBlocks.schrabidiumOre.get(), ModBlocks.deepslateSchrabidiumOre.get(), ModBlocks.netherSchrabidiumOre.get(),  ModBlocks.schrabidiumBlock.get(), ModBlocks.soliniumBlock.get(), ModBlocks.schrabidiumFuelBlock.get(),
            ModBlocks.australianOre.get(), ModBlocks.deepslateAustralianOre.get(), ModBlocks.australiumBlock.get(),
            ModBlocks.weidite.get(), ModBlocks.weidaniumBlock.get(),
            ModBlocks.reiite.get(), ModBlocks.reiiumBlock.get(),
            ModBlocks.brightblendeOre.get(), ModBlocks.unobtainiumBlock.get(),
            ModBlocks.dellite.get(), ModBlocks.daffergonBlock.get(),
            ModBlocks.dollarGreenMineral.get(), ModBlocks.verticiumBlock.get(),
            ModBlocks.rareEarthOre.get(), ModBlocks.deepslateRareEarthOre.get(),
            ModBlocks.cobaltOre.get(), ModBlocks.deepslateCobaltOre.get(),
            ModBlocks.netherPhosphorusOre.get(),
            ModBlocks.meteorCopperOre.get(), ModBlocks.copperBlock.get(),
            ModBlocks.meteorLithiumOre.get(), ModBlocks.lithiumBlock.get(),
            ModBlocks.starmetalOre.get(), ModBlocks.starmetalBlock.get(),
            ModBlocks.trixite.get(),
            ModBlocks.neptuniumBlock.get(),
            ModBlocks.moxFuelBlock.get(),
            ModBlocks.redCopperBlock.get(),
            ModBlocks.advancedAlloyBlock.get(),
            ModBlocks.cobaltBlock.get(),
            ModBlocks.steelBlock.get(),
            ModBlocks.electricalScrapBlock.get(),
            ModBlocks.trinititeBlock.get(),
            ModBlocks.nuclearWasteBlock.get(),
            ModBlocks.euphemiumBlock.get(),
            ModBlocks.schrabidiumCluster.get(), ModBlocks.euphemiumEtchedSchrabidiumCluster.get(),
            ModBlocks.magnetizedTungstenBlock.get(),
            ModBlocks.combineSteelBlock.get(),
            ModBlocks.deshReinforcedBlock.get(),
            ModBlocks.titaniumDecoBlock.get(), ModBlocks.redCopperDecoBlock.get(), ModBlocks.tungstenDecoBlock.get(), ModBlocks.aluminiumDecoBlock.get(), ModBlocks.steelDecoBlock.get(), ModBlocks.leadDecoBlock.get(), ModBlocks.berylliumDecoBlock.get(),
            ModBlocks.asbestosRoof.get(),
            ModBlocks.slakedSellafite.get(), ModBlocks.sellafite.get(), ModBlocks.hotSellafite.get(), ModBlocks.boilingSellafite.get(), ModBlocks.blazingSellafite.get(), ModBlocks.infernalSellafite.get(), ModBlocks.sellafiteCorium.get(),
            ModBlocks.siren.get(),
            ModBlocks.safe.get(),
            ModBlocks.steamPressBase.get(), ModBlocks.steamPressFrame.get(), ModBlocks.steamPressFrame.get(), ModBlocks.blastFurnace.get(),
            ModBlocks.combustionGenerator.get(),
            ModBlocks.electricFurnace.get(),
            ModBlocks.shredder.get(),
            ModBlocks.ironAnvil.get(), ModBlocks.leadAnvil.get(), ModBlocks.steelAnvil.get(), ModBlocks.meteoriteAnvil.get(), ModBlocks.starmetalAnvil.get(), ModBlocks.ferrouraniumAnvil.get(), ModBlocks.bismuthAnvil.get(), ModBlocks.schrabidateAnvil.get(), ModBlocks.dineutroniumAnvil.get(), ModBlocks.murkyAnvil.get(),
            ModBlocks.assembler.get(), ModBlocks.assemblerPart.get(),
            ModBlocks.littleBoy.get(), ModBlocks.fatMan.get()
        )
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
            ModBlocks.oilSand.get(),
            ModBlocks.whitePhosphorusBlock.get(),
            ModBlocks.redPhosphorusBlock.get(),
            ModBlocks.yellowcakeBlock.get(),
            ModBlocks.scrapBlock.get(),
            ModBlocks.deadGrass.get(),
            ModBlocks.glowingMycelium.get(),
            ModBlocks.trinitite.get(),
            ModBlocks.redTrinitite.get(),
        )
        tag(BlockTags.MINEABLE_WITH_AXE).add(
            ModBlocks.insulatorRoll.get(),
            ModBlocks.fiberglassRoll.get(),
            ModBlocks.hazmatBlock.get(),
            ModBlocks.glowingMushroomBlock.get(),
            ModBlocks.glowingMushroomStem.get(),
            ModBlocks.charredLog.get(),
            ModBlocks.charredPlanks.get(),
        )
    }

    private fun materialLevel() {
        tag(Tags.Blocks.NEEDS_WOOD_TOOL).add(
            ModBlocks.ligniteOre.get(),
            ModBlocks.whitePhosphorusBlock.get(),
            ModBlocks.redPhosphorusBlock.get(),
        )
        tag(BlockTags.NEEDS_STONE_TOOL).add(
            ModBlocks.sulfurOre.get(), ModBlocks.deepslateSulfurOre.get(), ModBlocks.netherSulfurOre.get(), ModBlocks.sulfurBlock.get(),
            ModBlocks.niterOre.get(), ModBlocks.deepslateNiterOre.get(), ModBlocks.niterBlock.get(),
            ModBlocks.aluminiumOre.get(), ModBlocks.deepslateAluminiumOre.get(), ModBlocks.aluminiumBlock.get(),
            ModBlocks.fluoriteOre.get(), ModBlocks.deepslateFluoriteOre.get(), ModBlocks.fluoriteBlock.get(),
            ModBlocks.netherPhosphorusOre.get(),
            ModBlocks.oilDeposit.get(), ModBlocks.emptyOilDeposit.get(), ModBlocks.deepslateOilDeposit.get(), ModBlocks.emptyDeepslateOilDeposit.get(),
            ModBlocks.copperBlock.get(),
            ModBlocks.redCopperBlock.get(),
            ModBlocks.lithiumBlock.get(),
            ModBlocks.electricalScrapBlock.get(),
            ModBlocks.trinititeBlock.get(),
            ModBlocks.nuclearWasteBlock.get(),
            ModBlocks.titaniumDecoBlock.get(), ModBlocks.redCopperDecoBlock.get(), ModBlocks.tungstenDecoBlock.get(), ModBlocks.aluminiumDecoBlock.get(), ModBlocks.steelDecoBlock.get(), ModBlocks.leadDecoBlock.get(), ModBlocks.berylliumDecoBlock.get(),
            ModBlocks.asbestosRoof.get(),
            ModBlocks.slakedSellafite.get(), ModBlocks.sellafite.get(), ModBlocks.hotSellafite.get(), ModBlocks.boilingSellafite.get(), ModBlocks.blazingSellafite.get(), ModBlocks.infernalSellafite.get(), ModBlocks.sellafiteCorium.get(),
            ModBlocks.siren.get(),
            ModBlocks.safe.get(),
            ModBlocks.steamPressBase.get(), ModBlocks.steamPressFrame.get(), ModBlocks.steamPressTop.get(),
            ModBlocks.blastFurnace.get(),
            ModBlocks.combustionGenerator.get(),
            ModBlocks.electricFurnace.get(),
            ModBlocks.shredder.get(),
            ModBlocks.ironAnvil.get(),
            ModBlocks.leadAnvil.get(),
            ModBlocks.assembler.get(), ModBlocks.assemblerPart.get(),
        )
        tag(BlockTags.NEEDS_IRON_TOOL).add(
            ModBlocks.uraniumOre.get(), ModBlocks.deepslateUraniumOre.get(), ModBlocks.uraniumBlock.get(), ModBlocks.u233Block.get(), ModBlocks.u235Block.get(), ModBlocks.u238Block.get(), ModBlocks.uraniumFuelBlock.get(),
            ModBlocks.scorchedUraniumOre.get(), ModBlocks.scorchedDeepslateUraniumOre.get(),
            ModBlocks.netherPlutoniumOre.get(), ModBlocks.plutoniumBlock.get(), ModBlocks.pu238Block.get(), ModBlocks.pu239Block.get(), ModBlocks.pu240Block.get(), ModBlocks.plutoniumFuelBlock.get(),
            ModBlocks.thoriumOre.get(), ModBlocks.deepslateThoriumOre.get(), ModBlocks.thoriumBlock.get(), ModBlocks.thoriumFuelBlock.get(),
            ModBlocks.titaniumOre.get(), ModBlocks.deepslateTitaniumOre.get(), ModBlocks.titaniumBlock.get(),
            ModBlocks.tungstenOre.get(), ModBlocks.deepslateTungstenOre.get(), ModBlocks.netherTungstenOre.get(), ModBlocks.tungstenBlock.get(),
            ModBlocks.berylliumOre.get(), ModBlocks.deepslateBerylliumOre.get(), ModBlocks.berylliumBlock.get(),
            ModBlocks.cobaltBlock.get(),
            ModBlocks.leadOre.get(), ModBlocks.deepslateLeadOre.get(),
            ModBlocks.asbestosOre.get(), ModBlocks.deepslateAsbestosOre.get(),
            ModBlocks.rareEarthOre.get(), ModBlocks.deepslateRareEarthOre.get(),
            ModBlocks.cobaltOre.get(), ModBlocks.deepslateCobaltOre.get(),
            ModBlocks.trixite.get(),
            ModBlocks.neptuniumBlock.get(),
            ModBlocks.moxFuelBlock.get(),
            ModBlocks.advancedAlloyBlock.get(),
            ModBlocks.steelBlock.get(),
            ModBlocks.asbestosBlock.get(),
            ModBlocks.steelAnvil.get(),
        )
        tag(BlockTags.NEEDS_DIAMOND_TOOL).add(
            ModBlocks.australianOre.get(), ModBlocks.deepslateAustralianOre.get(), ModBlocks.australiumBlock.get(),
            ModBlocks.weidite.get(), ModBlocks.weidaniumBlock.get(),
            ModBlocks.reiite.get(), ModBlocks.reiiumBlock.get(),
            ModBlocks.brightblendeOre.get(), ModBlocks.unobtainiumBlock.get(),
            ModBlocks.dellite.get(), ModBlocks.daffergonBlock.get(),
            ModBlocks.dollarGreenMineral.get(), ModBlocks.verticiumBlock.get(),
            ModBlocks.meteorUraniumOre.get(),
            ModBlocks.meteorThoriumOre.get(),
            ModBlocks.meteorTitaniumOre.get(),
            ModBlocks.meteorSulfurOre.get(),
            ModBlocks.meteorCopperOre.get(),
            ModBlocks.meteorTungstenOre.get(),
            ModBlocks.meteorAluminiumOre.get(),
            ModBlocks.meteorLeadOre.get(),
            ModBlocks.meteorLithiumOre.get(),
            ModBlocks.starmetalOre.get(), ModBlocks.starmetalBlock.get(),
            ModBlocks.magnetizedTungstenBlock.get(),
            ModBlocks.combineSteelBlock.get(),
            ModBlocks.deshReinforcedBlock.get(),
            ModBlocks.meteoriteAnvil.get(),
            ModBlocks.starmetalAnvil.get(),
            ModBlocks.ferrouraniumAnvil.get(),
            ModBlocks.bismuthAnvil.get(),
        )
        tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(
            ModBlocks.schrabidiumOre.get(), ModBlocks.deepslateSchrabidiumOre.get(), ModBlocks.netherSchrabidiumOre.get(), ModBlocks.schrabidiumBlock.get(),
            ModBlocks.euphemiumBlock.get(),
            ModBlocks.schrabidiumCluster.get(), ModBlocks.euphemiumEtchedSchrabidiumCluster.get(),
            ModBlocks.schrabidateAnvil.get(),
            ModBlocks.dineutroniumAnvil.get(),
            ModBlocks.murkyAnvil.get(),
        )
    }
}
