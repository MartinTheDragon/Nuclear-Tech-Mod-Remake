package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.ITEMS
import at.martinthedragon.nucleartech.items.AutoTooltippedBlockItem
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.Rarity
import net.minecraftforge.fml.RegistryObject

@Suppress("unused")
object ModBlockItems {
    val uraniumOre: RegistryObject<Item> = ITEMS.register("uranium_ore") { BlockItem(ModBlocks.uraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val thoriumOre: RegistryObject<Item> = ITEMS.register("thorium_ore") { BlockItem(ModBlocks.thoriumOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val titaniumOre: RegistryObject<Item> = ITEMS.register("titanium_ore") { BlockItem(ModBlocks.titaniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val sulfurOre: RegistryObject<Item> = ITEMS.register("sulfur_ore") { BlockItem(ModBlocks.sulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val niterOre: RegistryObject<Item> = ITEMS.register("niter_ore") { BlockItem(ModBlocks.niterOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val copperOre: RegistryObject<Item> = ITEMS.register("copper_ore") { BlockItem(ModBlocks.copperOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val tungstenOre: RegistryObject<Item> = ITEMS.register("tungsten_ore") { BlockItem(ModBlocks.tungstenOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val aluminiumOre: RegistryObject<Item> = ITEMS.register("aluminium_ore") { BlockItem(ModBlocks.aluminiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val fluoriteOre: RegistryObject<Item> = ITEMS.register("fluorite_ore") { BlockItem(ModBlocks.fluoriteOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val berylliumOre: RegistryObject<Item> = ITEMS.register("beryllium_ore") { BlockItem(ModBlocks.berylliumOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val leadOre: RegistryObject<Item> = ITEMS.register("lead_ore") { BlockItem(ModBlocks.leadOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val oilDeposit: RegistryObject<Item> = ITEMS.register("oil_deposit") { AutoTooltippedBlockItem(ModBlocks.oilDeposit.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val emptyOilDeposit: RegistryObject<Item> = ITEMS.register("empty_oil_deposit") { BlockItem(ModBlocks.emptyOilDeposit.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val oilSand: RegistryObject<Item> = ITEMS.register("oil_sand") { BlockItem(ModBlocks.oilSand.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val ligniteOre: RegistryObject<Item> = ITEMS.register("lignite_ore") { BlockItem(ModBlocks.ligniteOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val asbestosOre: RegistryObject<Item> = ITEMS.register("asbestos_ore") { BlockItem(ModBlocks.asbestosOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val schrabidiumOre: RegistryObject<Item> = ITEMS.register("schrabidium_ore") { BlockItem(ModBlocks.schrabidiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup).rarity(Rarity.RARE)) }
    val australianOre: RegistryObject<Item> = ITEMS.register("australian_ore") { AutoTooltippedBlockItem(ModBlocks.australianOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup).rarity(Rarity.UNCOMMON)) }
    val weidite: RegistryObject<Item> = ITEMS.register("weidite") { AutoTooltippedBlockItem(ModBlocks.weidite.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup).rarity(Rarity.UNCOMMON)) }
    val reiite: RegistryObject<Item> = ITEMS.register("reiite") { AutoTooltippedBlockItem(ModBlocks.reiite.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup).rarity(Rarity.UNCOMMON)) }
    val brightblendeOre: RegistryObject<Item> = ITEMS.register("brightblende_ore") { AutoTooltippedBlockItem(ModBlocks.brightblendeOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup).rarity(Rarity.UNCOMMON)) }
    val dellite: RegistryObject<Item> = ITEMS.register("dellite") { AutoTooltippedBlockItem(ModBlocks.dellite.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup).rarity(Rarity.UNCOMMON)) }
    val dollarGreenMineral: RegistryObject<Item> = ITEMS.register("dollar_green_mineral") { AutoTooltippedBlockItem(ModBlocks.dollarGreenMineral.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup).rarity(Rarity.UNCOMMON)) }
    val rareEarthOre: RegistryObject<Item> = ITEMS.register("rare_earth_ore") { BlockItem(ModBlocks.rareEarthOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup).rarity(Rarity.UNCOMMON)) }
    val netherUraniumOre: RegistryObject<Item> = ITEMS.register("nether_uranium_ore") { BlockItem(ModBlocks.netherUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val netherPlutoniumOre: RegistryObject<Item> = ITEMS.register("nether_plutonium_ore") { BlockItem(ModBlocks.netherPlutoniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val netherTungstenOre: RegistryObject<Item> = ITEMS.register("nether_tungsten_ore") { BlockItem(ModBlocks.netherTungstenOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val netherSulfurOre: RegistryObject<Item> = ITEMS.register("nether_sulfur_ore") { BlockItem(ModBlocks.netherSulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val netherPhosphorusOre: RegistryObject<Item> = ITEMS.register("nether_phosphorus_ore") { BlockItem(ModBlocks.netherPhosphorusOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val netherSchrabidiumOre: RegistryObject<Item> = ITEMS.register("nether_schrabidium_ore") { BlockItem(ModBlocks.netherSchrabidiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup).rarity(Rarity.RARE)) }
    val meteorUraniumOre: RegistryObject<Item> = ITEMS.register("meteor_uranium_ore") { BlockItem(ModBlocks.meteorUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val meteorThoriumOre: RegistryObject<Item> = ITEMS.register("meteor_thorium_ore") { BlockItem(ModBlocks.meteorThoriumOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val meteorTitaniumOre: RegistryObject<Item> = ITEMS.register("meteor_titanium_ore") { BlockItem(ModBlocks.meteorTitaniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val meteorSulfurOre: RegistryObject<Item> = ITEMS.register("meteor_sulfur_ore") { BlockItem(ModBlocks.meteorSulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val meteorCopperOre: RegistryObject<Item> = ITEMS.register("meteor_copper_ore") { BlockItem(ModBlocks.meteorCopperOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val meteorTungstenOre: RegistryObject<Item> = ITEMS.register("meteor_tungsten_ore") { BlockItem(ModBlocks.meteorTungstenOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val meteorAluminiumOre: RegistryObject<Item> = ITEMS.register("meteor_aluminium_ore") { BlockItem(ModBlocks.meteorAluminiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val meteorLeadOre: RegistryObject<Item> = ITEMS.register("meteor_lead_ore") { BlockItem(ModBlocks.meteorLeadOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val meteorLithiumOre: RegistryObject<Item> = ITEMS.register("meteor_lithium_ore") { BlockItem(ModBlocks.meteorLithiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val starmetalOre: RegistryObject<Item> = ITEMS.register("starmetal_ore") { BlockItem(ModBlocks.starmetalOre.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val trixite: RegistryObject<Item> = ITEMS.register("trixite") { BlockItem(ModBlocks.trixite.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }

    val uraniumBlock: RegistryObject<Item> = ITEMS.register("uranium_block") { BlockItem(ModBlocks.uraniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val u233Block: RegistryObject<Item> = ITEMS.register("u233_block") { BlockItem(ModBlocks.u233Block.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val u235Block: RegistryObject<Item> = ITEMS.register("u235_block") { BlockItem(ModBlocks.u235Block.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val u238Block: RegistryObject<Item> = ITEMS.register("u238_block") { BlockItem(ModBlocks.u238Block.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val uraniumFuelBlock: RegistryObject<Item> = ITEMS.register("uranium_fuel_block") { BlockItem(ModBlocks.uraniumFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val neptuniumBlock: RegistryObject<Item> = ITEMS.register("neptunium_block") { BlockItem(ModBlocks.neptuniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val moxFuelBlock: RegistryObject<Item> = ITEMS.register("mox_fuel_block") { BlockItem(ModBlocks.moxFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val plutoniumBlock: RegistryObject<Item> = ITEMS.register("plutonium_block") { BlockItem(ModBlocks.plutoniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val pu238Block: RegistryObject<Item> = ITEMS.register("pu238_block") { BlockItem(ModBlocks.pu238Block.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val pu239Block: RegistryObject<Item> = ITEMS.register("pu239_block") { BlockItem(ModBlocks.pu239Block.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val pu240Block: RegistryObject<Item> = ITEMS.register("pu240_block") { BlockItem(ModBlocks.pu240Block.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val plutoniumFuelBlock: RegistryObject<Item> = ITEMS.register("plutonium_fuel_block") { BlockItem(ModBlocks.plutoniumFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val thoriumBlock: RegistryObject<Item> = ITEMS.register("thorium_block") { BlockItem(ModBlocks.thoriumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val thoriumFuelBlock: RegistryObject<Item> = ITEMS.register("thorium_fuel_block") { BlockItem(ModBlocks.thoriumFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val titaniumBlock: RegistryObject<Item> = ITEMS.register("titanium_block") { BlockItem(ModBlocks.titaniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val sulfurBlock: RegistryObject<Item> = ITEMS.register("sulfur_block") { BlockItem(ModBlocks.sulfurBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val niterBlock: RegistryObject<Item> = ITEMS.register("niter_block") { BlockItem(ModBlocks.niterBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val copperBlock: RegistryObject<Item> = ITEMS.register("copper_block") { BlockItem(ModBlocks.copperBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val redCopperBlock: RegistryObject<Item> = ITEMS.register("red_copper_block") { BlockItem(ModBlocks.redCopperBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val advancedAlloyBlock: RegistryObject<Item> = ITEMS.register("advanced_alloy_block") { BlockItem(ModBlocks.advancedAlloyBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val tungstenBlock: RegistryObject<Item> = ITEMS.register("tungsten_block") { BlockItem(ModBlocks.tungstenBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val aluminiumBlock: RegistryObject<Item> = ITEMS.register("aluminium_block") { BlockItem(ModBlocks.aluminiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val fluoriteBlock: RegistryObject<Item> = ITEMS.register("fluorite_block") { BlockItem(ModBlocks.fluoriteBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val berylliumBlock: RegistryObject<Item> = ITEMS.register("beryllium_block") { BlockItem(ModBlocks.berylliumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val cobaltBlock: RegistryObject<Item> = ITEMS.register("cobalt_block") { BlockItem(ModBlocks.cobaltBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val steelBlock: RegistryObject<Item> = ITEMS.register("steel_block") { BlockItem(ModBlocks.steelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val leadBlock: RegistryObject<Item> = ITEMS.register("lead_block") { BlockItem(ModBlocks.leadBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val lithiumBlock: RegistryObject<Item> = ITEMS.register("lithium_block") { BlockItem(ModBlocks.lithiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val whitePhosphorusBlock: RegistryObject<Item> = ITEMS.register("white_phosphorus_block") { BlockItem(ModBlocks.whitePhosphorusBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val redPhosphorusBlock: RegistryObject<Item> = ITEMS.register("red_phosphorus_block") { BlockItem(ModBlocks.redPhosphorusBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val yellowcakeBlock: RegistryObject<Item> = ITEMS.register("yellowcake_block") { BlockItem(ModBlocks.yellowcakeBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val scrapBlock: RegistryObject<Item> = ITEMS.register("scrap_block") { BlockItem(ModBlocks.scrapBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val electricalScrapBlock: RegistryObject<Item> = ITEMS.register("electrical_scrap_block") { BlockItem(ModBlocks.electricalScrapBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val insulatorRoll: RegistryObject<Item> = ITEMS.register("insulator_roll") { BlockItem(ModBlocks.insulatorRoll.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val fiberglassRoll: RegistryObject<Item> = ITEMS.register("fiberglass_roll") { BlockItem(ModBlocks.fiberglassRoll.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val asbestosBlock: RegistryObject<Item> = ITEMS.register("asbestos_block") { BlockItem(ModBlocks.asbestosBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val trinititeBlock: RegistryObject<Item> = ITEMS.register("trinitite_block") { BlockItem(ModBlocks.trinititeBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val nuclearWasteBlock: RegistryObject<Item> = ITEMS.register("nuclear_waste_block") { BlockItem(ModBlocks.nuclearWasteBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val schrabidiumBlock: RegistryObject<Item> = ITEMS.register("schrabidium_block") { BlockItem(ModBlocks.schrabidiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val soliniumBlock: RegistryObject<Item> = ITEMS.register("solinium_block") { BlockItem(ModBlocks.soliniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val schrabidiumFuelBlock: RegistryObject<Item> = ITEMS.register("schrabidium_fuel_block") { BlockItem(ModBlocks.schrabidiumFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val euphemiumBlock: RegistryObject<Item> = ITEMS.register("euphemium_block") { BlockItem(ModBlocks.euphemiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val schrabidiumCluster: RegistryObject<Item> = ITEMS.register("schrabidium_cluster") { BlockItem(ModBlocks.schrabidiumCluster.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val euphemiumEtchedSchrabidiumCluster: RegistryObject<Item> = ITEMS.register("euphemium_etched_schrabidium_cluster") { BlockItem(ModBlocks.euphemiumEtchedSchrabidiumCluster.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val magnetizedTungstenBlock: RegistryObject<Item> = ITEMS.register("magnetized_tungsten_block") { BlockItem(ModBlocks.magnetizedTungstenBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val combineSteelBlock: RegistryObject<Item> = ITEMS.register("combine_steel_block") { BlockItem(ModBlocks.combineSteelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val deshReinforcedBlock: RegistryObject<Item> = ITEMS.register("desh_reinforced_block") { BlockItem(ModBlocks.deshReinforcedBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val starmetalBlock: RegistryObject<Item> = ITEMS.register("starmetal_block") { BlockItem(ModBlocks.starmetalBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val australiumBlock: RegistryObject<Item> = ITEMS.register("australium_block") { BlockItem(ModBlocks.australiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val weidaniumBlock: RegistryObject<Item> = ITEMS.register("weidanium_block") { BlockItem(ModBlocks.weidaniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val reiiumBlock: RegistryObject<Item> = ITEMS.register("reiium_block") { BlockItem(ModBlocks.reiiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val unobtainiumBlock: RegistryObject<Item> = ITEMS.register("unobtainium_block") { BlockItem(ModBlocks.unobtainiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val daffergonBlock: RegistryObject<Item> = ITEMS.register("daffergon_block") { BlockItem(ModBlocks.daffergonBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val verticiumBlock: RegistryObject<Item> = ITEMS.register("verticium_block") { BlockItem(ModBlocks.verticiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val titaniumDecoBlock: RegistryObject<Item> = ITEMS.register("titanium_deco_block") { BlockItem(ModBlocks.titaniumDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val redCopperDecoBlock: RegistryObject<Item> = ITEMS.register("red_copper_deco_block") { BlockItem(ModBlocks.redCopperDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val tungstenDecoBlock: RegistryObject<Item> = ITEMS.register("tungsten_deco_block") { BlockItem(ModBlocks.tungstenDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val aluminiumDecoBlock: RegistryObject<Item> = ITEMS.register("aluminium_deco_block") { BlockItem(ModBlocks.aluminiumDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val steelDecoBlock: RegistryObject<Item> = ITEMS.register("steel_deco_block") { BlockItem(ModBlocks.steelDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val leadDecoBlock: RegistryObject<Item> = ITEMS.register("lead_deco_block") { BlockItem(ModBlocks.leadDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val berylliumDecoBlock: RegistryObject<Item> = ITEMS.register("beryllium_deco_block") { BlockItem(ModBlocks.berylliumDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val asbestosRoof: RegistryObject<Item> = ITEMS.register("asbestos_roof") { BlockItem(ModBlocks.asbestosRoof.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }
    val hazmatBlock: RegistryObject<Item> = ITEMS.register("hazmat_block") { BlockItem(ModBlocks.hazmatBlock.get(), Item.Properties().tab(CreativeTabs.Blocks.itemGroup)) }

    val siren: RegistryObject<Item> = ITEMS.register("siren") { BlockItem(ModBlocks.siren.get(), Item.Properties().tab(CreativeTabs.Machines.itemGroup)) }
    val safe: RegistryObject<Item> = ITEMS.register("safe") { BlockItem(ModBlocks.safe.get(), Item.Properties().tab(CreativeTabs.Machines.itemGroup)) }
    val steamPress: RegistryObject<Item> = ITEMS.register("steam_press") { BlockItem(ModBlocks.steamPressBase.get(), Item.Properties().tab(CreativeTabs.Machines.itemGroup)) }
}
