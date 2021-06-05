package at.martinthedragon.ntm

import at.martinthedragon.ntm.RegistriesAndLifecycle.ITEMS
import at.martinthedragon.ntm.items.AutoTooltippedBlockItem
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.Rarity
import net.minecraftforge.fml.RegistryObject

@Suppress("unused")
object ModBlockItems {
    val uraniumOre: RegistryObject<Item> = ITEMS.register("uranium_ore") { BlockItem(B.uraniumOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val thoriumOre: RegistryObject<Item> = ITEMS.register("thorium_ore") { BlockItem(B.thoriumOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val titaniumOre: RegistryObject<Item> = ITEMS.register("titanium_ore") { BlockItem(B.titaniumOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val sulfurOre: RegistryObject<Item> = ITEMS.register("sulfur_ore") { BlockItem(B.sulfurOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val niterOre: RegistryObject<Item> = ITEMS.register("niter_ore") { BlockItem(B.niterOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val copperOre: RegistryObject<Item> = ITEMS.register("copper_ore") { BlockItem(B.copperOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val tungstenOre: RegistryObject<Item> = ITEMS.register("tungsten_ore") { BlockItem(B.tungstenOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val aluminiumOre: RegistryObject<Item> = ITEMS.register("aluminium_ore") { BlockItem(B.aluminiumOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val fluoriteOre: RegistryObject<Item> = ITEMS.register("fluorite_ore") { BlockItem(B.fluoriteOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val berylliumOre: RegistryObject<Item> = ITEMS.register("beryllium_ore") { BlockItem(B.berylliumOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val leadOre: RegistryObject<Item> = ITEMS.register("lead_ore") { BlockItem(B.leadOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val oilDeposit: RegistryObject<Item> = ITEMS.register("oil_deposit") { AutoTooltippedBlockItem(B.oilDeposit.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val emptyOilDeposit: RegistryObject<Item> = ITEMS.register("empty_oil_deposit") { BlockItem(B.emptyOilDeposit.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val oilSand: RegistryObject<Item> = ITEMS.register("oil_sand") { BlockItem(B.oilSand.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val ligniteOre: RegistryObject<Item> = ITEMS.register("lignite_ore") { BlockItem(B.ligniteOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val asbestosOre: RegistryObject<Item> = ITEMS.register("asbestos_ore") { BlockItem(B.asbestosOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val schrabidiumOre: RegistryObject<Item> = ITEMS.register("schrabidium_ore") { BlockItem(B.schrabidiumOre.get(), Item.Properties().tab(CT.Blocks.itemGroup).rarity(Rarity.RARE)) }
    val australianOre: RegistryObject<Item> = ITEMS.register("australian_ore") { AutoTooltippedBlockItem(B.australianOre.get(), Item.Properties().tab(CT.Blocks.itemGroup).rarity(Rarity.UNCOMMON)) }
    val weidite: RegistryObject<Item> = ITEMS.register("weidite") { AutoTooltippedBlockItem(B.weidite.get(), Item.Properties().tab(CT.Blocks.itemGroup).rarity(Rarity.UNCOMMON)) }
    val reiite: RegistryObject<Item> = ITEMS.register("reiite") { AutoTooltippedBlockItem(B.reiite.get(), Item.Properties().tab(CT.Blocks.itemGroup).rarity(Rarity.UNCOMMON)) }
    val brightblendeOre: RegistryObject<Item> = ITEMS.register("brightblende_ore") { AutoTooltippedBlockItem(B.brightblendeOre.get(), Item.Properties().tab(CT.Blocks.itemGroup).rarity(Rarity.UNCOMMON)) }
    val dellite: RegistryObject<Item> = ITEMS.register("dellite") { AutoTooltippedBlockItem(B.dellite.get(), Item.Properties().tab(CT.Blocks.itemGroup).rarity(Rarity.UNCOMMON)) }
    val dollarGreenMineral: RegistryObject<Item> = ITEMS.register("dollar_green_mineral") { AutoTooltippedBlockItem(B.dollarGreenMineral.get(), Item.Properties().tab(CT.Blocks.itemGroup).rarity(Rarity.UNCOMMON)) }
    val rareEarthOre: RegistryObject<Item> = ITEMS.register("rare_earth_ore") { BlockItem(B.rareEarthOre.get(), Item.Properties().tab(CT.Blocks.itemGroup).rarity(Rarity.UNCOMMON)) }
    val netherUraniumOre: RegistryObject<Item> = ITEMS.register("nether_uranium_ore") { BlockItem(B.netherUraniumOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val netherPlutoniumOre: RegistryObject<Item> = ITEMS.register("nether_plutonium_ore") { BlockItem(B.netherPlutoniumOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val netherTungstenOre: RegistryObject<Item> = ITEMS.register("nether_tungsten_ore") { BlockItem(B.netherTungstenOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val netherSulfurOre: RegistryObject<Item> = ITEMS.register("nether_sulfur_ore") { BlockItem(B.netherSulfurOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val netherPhosphorusOre: RegistryObject<Item> = ITEMS.register("nether_phosphorus_ore") { BlockItem(B.netherPhosphorusOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val netherSchrabidiumOre: RegistryObject<Item> = ITEMS.register("nether_schrabidium_ore") { BlockItem(B.netherSchrabidiumOre.get(), Item.Properties().tab(CT.Blocks.itemGroup).rarity(Rarity.RARE)) }
    val meteorUraniumOre: RegistryObject<Item> = ITEMS.register("meteor_uranium_ore") { BlockItem(B.meteorUraniumOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val meteorThoriumOre: RegistryObject<Item> = ITEMS.register("meteor_thorium_ore") { BlockItem(B.meteorThoriumOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val meteorTitaniumOre: RegistryObject<Item> = ITEMS.register("meteor_titanium_ore") { BlockItem(B.meteorTitaniumOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val meteorSulfurOre: RegistryObject<Item> = ITEMS.register("meteor_sulfur_ore") { BlockItem(B.meteorSulfurOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val meteorCopperOre: RegistryObject<Item> = ITEMS.register("meteor_copper_ore") { BlockItem(B.meteorCopperOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val meteorTungstenOre: RegistryObject<Item> = ITEMS.register("meteor_tungsten_ore") { BlockItem(B.meteorTungstenOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val meteorAluminiumOre: RegistryObject<Item> = ITEMS.register("meteor_aluminium_ore") { BlockItem(B.meteorAluminiumOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val meteorLeadOre: RegistryObject<Item> = ITEMS.register("meteor_lead_ore") { BlockItem(B.meteorLeadOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val meteorLithiumOre: RegistryObject<Item> = ITEMS.register("meteor_lithium_ore") { BlockItem(B.meteorLithiumOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val starmetalOre: RegistryObject<Item> = ITEMS.register("starmetal_ore") { BlockItem(B.starmetalOre.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val trixite: RegistryObject<Item> = ITEMS.register("trixite") { BlockItem(B.trixite.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }

    val uraniumBlock: RegistryObject<Item> = ITEMS.register("uranium_block") { BlockItem(B.uraniumBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val u233Block: RegistryObject<Item> = ITEMS.register("u233_block") { BlockItem(B.u233Block.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val u235Block: RegistryObject<Item> = ITEMS.register("u235_block") { BlockItem(B.u235Block.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val u238Block: RegistryObject<Item> = ITEMS.register("u238_block") { BlockItem(B.u238Block.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val uraniumFuelBlock: RegistryObject<Item> = ITEMS.register("uranium_fuel_block") { BlockItem(B.uraniumFuelBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val neptuniumBlock: RegistryObject<Item> = ITEMS.register("neptunium_block") { BlockItem(B.neptuniumBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val moxFuelBlock: RegistryObject<Item> = ITEMS.register("mox_fuel_block") { BlockItem(B.moxFuelBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val plutoniumBlock: RegistryObject<Item> = ITEMS.register("plutonium_block") { BlockItem(B.plutoniumBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val pu238Block: RegistryObject<Item> = ITEMS.register("pu238_block") { BlockItem(B.pu238Block.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val pu239Block: RegistryObject<Item> = ITEMS.register("pu239_block") { BlockItem(B.pu239Block.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val pu240Block: RegistryObject<Item> = ITEMS.register("pu240_block") { BlockItem(B.pu240Block.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val plutoniumFuelBlock: RegistryObject<Item> = ITEMS.register("plutonium_fuel_block") { BlockItem(B.plutoniumFuelBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val thoriumBlock: RegistryObject<Item> = ITEMS.register("thorium_block") { BlockItem(B.thoriumBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val thoriumFuelBlock: RegistryObject<Item> = ITEMS.register("thorium_fuel_block") { BlockItem(B.thoriumFuelBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val titaniumBlock: RegistryObject<Item> = ITEMS.register("titanium_block") { BlockItem(B.titaniumBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val sulfurBlock: RegistryObject<Item> = ITEMS.register("sulfur_block") { BlockItem(B.sulfurBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val niterBlock: RegistryObject<Item> = ITEMS.register("niter_block") { BlockItem(B.niterBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val copperBlock: RegistryObject<Item> = ITEMS.register("copper_block") { BlockItem(B.copperBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val redCopperBlock: RegistryObject<Item> = ITEMS.register("red_copper_block") { BlockItem(B.redCopperBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val advancedAlloyBlock: RegistryObject<Item> = ITEMS.register("advanced_alloy_block") { BlockItem(B.advancedAlloyBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val tungstenBlock: RegistryObject<Item> = ITEMS.register("tungsten_block") { BlockItem(B.tungstenBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val aluminiumBlock: RegistryObject<Item> = ITEMS.register("aluminium_block") { BlockItem(B.aluminiumBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val fluoriteBlock: RegistryObject<Item> = ITEMS.register("fluorite_block") { BlockItem(B.fluoriteBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val berylliumBlock: RegistryObject<Item> = ITEMS.register("beryllium_block") { BlockItem(B.berylliumBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val cobaltBlock: RegistryObject<Item> = ITEMS.register("cobalt_block") { BlockItem(B.cobaltBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val steelBlock: RegistryObject<Item> = ITEMS.register("steel_block") { BlockItem(B.steelBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val leadBlock: RegistryObject<Item> = ITEMS.register("lead_block") { BlockItem(B.leadBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val lithiumBlock: RegistryObject<Item> = ITEMS.register("lithium_block") { BlockItem(B.lithiumBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val whitePhosphorusBlock: RegistryObject<Item> = ITEMS.register("white_phosphorus_block") { BlockItem(B.whitePhosphorusBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val redPhosphorusBlock: RegistryObject<Item> = ITEMS.register("red_phosphorus_block") { BlockItem(B.redPhosphorusBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val yellowcakeBlock: RegistryObject<Item> = ITEMS.register("yellowcake_block") { BlockItem(B.yellowcakeBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val scrapBlock: RegistryObject<Item> = ITEMS.register("scrap_block") { BlockItem(B.scrapBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val electricalScrapBlock: RegistryObject<Item> = ITEMS.register("electrical_scrap_block") { BlockItem(B.electricalScrapBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val insulatorRoll: RegistryObject<Item> = ITEMS.register("insulator_roll") { BlockItem(B.insulatorRoll.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val fiberglassRoll: RegistryObject<Item> = ITEMS.register("fiberglass_roll") { BlockItem(B.fiberglassRoll.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val asbestosBlock: RegistryObject<Item> = ITEMS.register("asbestos_block") { BlockItem(B.asbestosBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val trinititeBlock: RegistryObject<Item> = ITEMS.register("trinitite_block") { BlockItem(B.trinititeBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val nuclearWasteBlock: RegistryObject<Item> = ITEMS.register("nuclear_waste_block") { BlockItem(B.nuclearWasteBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val schrabidiumBlock: RegistryObject<Item> = ITEMS.register("schrabidium_block") { BlockItem(B.schrabidiumBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val soliniumBlock: RegistryObject<Item> = ITEMS.register("solinium_block") { BlockItem(B.soliniumBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val schrabidiumFuelBlock: RegistryObject<Item> = ITEMS.register("schrabidium_fuel_block") { BlockItem(B.schrabidiumFuelBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val euphemiumBlock: RegistryObject<Item> = ITEMS.register("euphemium_block") { BlockItem(B.euphemiumBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val schrabidiumCluster: RegistryObject<Item> = ITEMS.register("schrabidium_cluster") { BlockItem(B.schrabidiumCluster.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val euphemiumEtchedSchrabidiumCluster: RegistryObject<Item> = ITEMS.register("euphemium_etched_schrabidium_cluster") { BlockItem(B.euphemiumEtchedSchrabidiumCluster.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val magnetizedTungstenBlock: RegistryObject<Item> = ITEMS.register("magnetized_tungsten_block") { BlockItem(B.magnetizedTungstenBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val combineSteelBlock: RegistryObject<Item> = ITEMS.register("combine_steel_block") { BlockItem(B.combineSteelBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val deshReinforcedBlock: RegistryObject<Item> = ITEMS.register("desh_reinforced_block") { BlockItem(B.deshReinforcedBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val starmetalBlock: RegistryObject<Item> = ITEMS.register("starmetal_block") { BlockItem(B.starmetalBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val australiumBlock: RegistryObject<Item> = ITEMS.register("australium_block") { BlockItem(B.australiumBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val weidaniumBlock: RegistryObject<Item> = ITEMS.register("weidanium_block") { BlockItem(B.weidaniumBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val reiiumBlock: RegistryObject<Item> = ITEMS.register("reiium_block") { BlockItem(B.reiiumBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val unobtainiumBlock: RegistryObject<Item> = ITEMS.register("unobtainium_block") { BlockItem(B.unobtainiumBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val daffergonBlock: RegistryObject<Item> = ITEMS.register("daffergon_block") { BlockItem(B.daffergonBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val verticiumBlock: RegistryObject<Item> = ITEMS.register("verticium_block") { BlockItem(B.verticiumBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val titaniumDecoBlock: RegistryObject<Item> = ITEMS.register("titanium_deco_block") { BlockItem(B.titaniumDecoBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val redCopperDecoBlock: RegistryObject<Item> = ITEMS.register("red_copper_deco_block") { BlockItem(B.redCopperDecoBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val tungstenDecoBlock: RegistryObject<Item> = ITEMS.register("tungsten_deco_block") { BlockItem(B.tungstenDecoBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val aluminiumDecoBlock: RegistryObject<Item> = ITEMS.register("aluminium_deco_block") { BlockItem(B.aluminiumDecoBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val steelDecoBlock: RegistryObject<Item> = ITEMS.register("steel_deco_block") { BlockItem(B.steelDecoBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val leadDecoBlock: RegistryObject<Item> = ITEMS.register("lead_deco_block") { BlockItem(B.leadDecoBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val berylliumDecoBlock: RegistryObject<Item> = ITEMS.register("beryllium_deco_block") { BlockItem(B.berylliumDecoBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val asbestosRoof: RegistryObject<Item> = ITEMS.register("asbestos_roof") { BlockItem(B.asbestosRoof.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }
    val hazmatBlock: RegistryObject<Item> = ITEMS.register("hazmat_block") { BlockItem(B.hazmatBlock.get(), Item.Properties().tab(CT.Blocks.itemGroup)) }

    val siren: RegistryObject<Item> = ITEMS.register("siren") { BlockItem(B.siren.get(), Item.Properties().tab(CT.Machines.itemGroup)) }
    val safe: RegistryObject<Item> = ITEMS.register("safe") { BlockItem(B.safe.get(), Item.Properties().tab(CT.Machines.itemGroup)) }
    val steamPress: RegistryObject<Item> = ITEMS.register("steam_press") { BlockItem(B.steamPressBase.get(), Item.Properties().tab(CT.Machines.itemGroup)) }
}
