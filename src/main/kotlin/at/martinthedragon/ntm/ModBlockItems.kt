package at.martinthedragon.ntm

import at.martinthedragon.ntm.RegistriesAndLifecycle.register
import at.martinthedragon.ntm.items.AutoTooltippedBlockItem
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Rarity
import net.minecraft.util.text.*
import net.minecraft.world.World
import net.minecraftforge.fml.common.thread.SidedThreadGroups

@Suppress("unused")
object ModBlockItems {
    val uraniumOre: BlockItem = BlockItem(B.uraniumOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("uranium_ore").register() as BlockItem
    val thoriumOre: BlockItem = BlockItem(B.thoriumOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("thorium_ore").register() as BlockItem
    val titaniumOre: BlockItem = BlockItem(B.titaniumOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("titanium_ore").register() as BlockItem
    val sulfurOre: BlockItem = BlockItem(B.sulfurOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("sulfur_ore").register() as BlockItem
    val niterOre: BlockItem = BlockItem(B.niterOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("niter_ore").register() as BlockItem
    val copperOre: BlockItem = BlockItem(B.copperOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("copper_ore").register() as BlockItem
    val tungstenOre: BlockItem = BlockItem(B.tungstenOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("tungsten_ore").register() as BlockItem
    val aluminiumOre: BlockItem = BlockItem(B.aluminiumOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("aluminium_ore").register() as BlockItem
    val fluoriteOre: BlockItem = BlockItem(B.fluoriteOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("fluorite_ore").register() as BlockItem
    val berylliumOre: BlockItem = BlockItem(B.berylliumOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("beryllium_ore").register() as BlockItem
    val leadOre: BlockItem = BlockItem(B.leadOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("lead_ore").register() as BlockItem
    val oilDeposit: BlockItem = AutoTooltippedBlockItem(B.oilDeposit, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("oil_deposit").register() as BlockItem
    val emptyOilDeposit: BlockItem = BlockItem(B.emptyOilDeposit, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("empty_oil_deposit").register() as BlockItem
    val oilSand: BlockItem = BlockItem(B.oilSand, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("oil_sand").register() as BlockItem
    val ligniteOre: BlockItem = BlockItem(B.ligniteOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("lignite_ore").register() as BlockItem
    val asbestosOre: BlockItem = BlockItem(B.asbestosOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("asbestos_ore").register() as BlockItem
    val schrabidiumOre: BlockItem = BlockItem(B.schrabidiumOre, Item.Properties().tab(CT.Blocks.itemGroup).rarity(Rarity.RARE)).setRegistryName("schrabidium_ore").register() as BlockItem
    val australianOre: BlockItem = AutoTooltippedBlockItem(B.australianOre, Item.Properties().tab(CT.Blocks.itemGroup).rarity(Rarity.UNCOMMON)).setRegistryName("australian_ore").register() as BlockItem
    val weidite: BlockItem = AutoTooltippedBlockItem(B.weidite, Item.Properties().tab(CT.Blocks.itemGroup).rarity(Rarity.UNCOMMON)).setRegistryName("weidite").register() as BlockItem
    val reiite: BlockItem = AutoTooltippedBlockItem(B.reiite, Item.Properties().tab(CT.Blocks.itemGroup).rarity(Rarity.UNCOMMON)).setRegistryName("reiite").register() as BlockItem
    val brightblendeOre: BlockItem = AutoTooltippedBlockItem(B.brightblendeOre, Item.Properties().tab(CT.Blocks.itemGroup).rarity(Rarity.UNCOMMON)).setRegistryName("brightblende_ore").register() as BlockItem
    val dellite: BlockItem = AutoTooltippedBlockItem(B.dellite, Item.Properties().tab(CT.Blocks.itemGroup).rarity(Rarity.UNCOMMON)).setRegistryName("dellite").register() as BlockItem
    val dollarGreenMineral: BlockItem = AutoTooltippedBlockItem(B.dollarGreenMineral, Item.Properties().tab(CT.Blocks.itemGroup).rarity(Rarity.UNCOMMON)).setRegistryName("dollar_green_mineral").register() as BlockItem
    val rareEarthOre: BlockItem = BlockItem(B.rareEarthOre, Item.Properties().tab(CT.Blocks.itemGroup).rarity(Rarity.UNCOMMON)).setRegistryName("rare_earth_ore").register() as BlockItem
    val netherUraniumOre: BlockItem = BlockItem(B.netherUraniumOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("nether_uranium_ore").register() as BlockItem
    val netherPlutoniumOre: BlockItem = BlockItem(B.netherPlutoniumOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("nether_plutonium_ore").register() as BlockItem
    val netherTungstenOre: BlockItem = BlockItem(B.netherTungstenOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("nether_tungsten_ore").register() as BlockItem
    val netherSulfurOre: BlockItem = BlockItem(B.netherSulfurOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("nether_sulfur_ore").register() as BlockItem
    val netherPhosphorusOre: BlockItem = BlockItem(B.netherPhosphorusOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("nether_phosphorus_ore").register() as BlockItem
    val netherSchrabidiumOre: BlockItem = BlockItem(B.netherSchrabidiumOre, Item.Properties().tab(CT.Blocks.itemGroup).rarity(Rarity.RARE)).setRegistryName("nether_schrabidium_ore").register() as BlockItem
    val meteorUraniumOre: BlockItem = BlockItem(B.meteorUraniumOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("meteor_uranium_ore").register() as BlockItem
    val meteorThoriumOre: BlockItem = BlockItem(B.meteorThoriumOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("meteor_thorium_ore").register() as BlockItem
    val meteorTitaniumOre: BlockItem = BlockItem(B.meteorTitaniumOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("meteor_titanium_ore").register() as BlockItem
    val meteorSulfurOre: BlockItem = BlockItem(B.meteorSulfurOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("meteor_sulfur_ore").register() as BlockItem
    val meteorCopperOre: BlockItem = BlockItem(B.meteorCopperOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("meteor_copper_ore").register() as BlockItem
    val meteorTungstenOre: BlockItem = BlockItem(B.meteorTungstenOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("meteor_tungsten_ore").register() as BlockItem
    val meteorAluminiumOre: BlockItem = BlockItem(B.meteorAluminiumOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("meteor_aluminium_ore").register() as BlockItem
    val meteorLeadOre: BlockItem = BlockItem(B.meteorLeadOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("meteor_lead_ore").register() as BlockItem
    val meteorLithiumOre: BlockItem = BlockItem(B.meteorLithiumOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("meteor_lithium_ore").register() as BlockItem
    val starmetalOre: BlockItem = BlockItem(B.starmetalOre, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("starmetal_ore").register() as BlockItem
    val trixite: BlockItem = BlockItem(B.trixite, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("trixite").register() as BlockItem

    val uraniumBlock: BlockItem = BlockItem(B.uraniumBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("uranium_block").register() as BlockItem
    val u233Block: BlockItem = BlockItem(B.u233Block, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("u233_block").register() as BlockItem
    val u235Block: BlockItem = BlockItem(B.u235Block, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("u235_block").register() as BlockItem
    val u238Block: BlockItem = BlockItem(B.u238Block, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("u238_block").register() as BlockItem
    val uraniumFuelBlock: BlockItem = BlockItem(B.uraniumFuelBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("uranium_fuel_block").register() as BlockItem
    val neptuniumBlock: BlockItem = BlockItem(B.neptuniumBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("neptunium_block").register() as BlockItem
    val moxFuelBlock: BlockItem = BlockItem(B.moxFuelBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("mox_fuel_block").register() as BlockItem
    val plutoniumBlock: BlockItem = BlockItem(B.plutoniumBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("plutonium_block").register() as BlockItem
    val pu238Block: BlockItem = BlockItem(B.pu238Block, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("pu238_block").register() as BlockItem
    val pu239Block: BlockItem = BlockItem(B.pu239Block, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("pu239_block").register() as BlockItem
    val pu240Block: BlockItem = BlockItem(B.pu240Block, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("pu240_block").register() as BlockItem
    val plutoniumFuelBlock: BlockItem = BlockItem(B.plutoniumFuelBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("plutonium_fuel_block").register() as BlockItem
    val thoriumBlock: BlockItem = BlockItem(B.thoriumBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("thorium_block").register() as BlockItem
    val thoriumFuelBlock: BlockItem = BlockItem(B.thoriumFuelBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("thorium_fuel_block").register() as BlockItem
    val titaniumBlock: BlockItem = BlockItem(B.titaniumBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("titanium_block").register() as BlockItem
    val sulfurBlock: BlockItem = BlockItem(B.sulfurBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("sulfur_block").register() as BlockItem
    val niterBlock: BlockItem = BlockItem(B.niterBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("niter_block").register() as BlockItem
    val copperBlock: BlockItem = BlockItem(B.copperBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("copper_block").register() as BlockItem
    val redCopperBlock: BlockItem = BlockItem(B.redCopperBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("red_copper_block").register() as BlockItem
    val advancedAlloyBlock: BlockItem = BlockItem(B.advancedAlloyBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("advanced_alloy_block").register() as BlockItem
    val tungstenBlock: BlockItem = BlockItem(B.tungstenBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("tungsten_block").register() as BlockItem
    val aluminiumBlock: BlockItem = BlockItem(B.aluminiumBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("aluminium_block").register() as BlockItem
    val fluoriteBlock: BlockItem = BlockItem(B.fluoriteBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("fluorite_block").register() as BlockItem
    val berylliumBlock: BlockItem = BlockItem(B.berylliumBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("beryllium_block").register() as BlockItem
    val cobaltBlock: BlockItem = BlockItem(B.cobaltBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("cobalt_block").register() as BlockItem
    val steelBlock: BlockItem = BlockItem(B.steelBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("steel_block").register() as BlockItem
    val leadBlock: BlockItem = BlockItem(B.leadBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("lead_block").register() as BlockItem
    val lithiumBlock: BlockItem = BlockItem(B.lithiumBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("lithium_block").register() as BlockItem
    val whitePhosphorusBlock: BlockItem = BlockItem(B.whitePhosphorusBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("white_phosphorus_block").register() as BlockItem
    val redPhosphorusBlock: BlockItem = BlockItem(B.redPhosphorusBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("red_phosphorus_block").register() as BlockItem
    val yellowcakeBlock: BlockItem = BlockItem(B.yellowcakeBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("yellowcake_block").register() as BlockItem
    val scrapBlock: BlockItem = BlockItem(B.scrapBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("scrap_block").register() as BlockItem
    val electricalScrapBlock: BlockItem = BlockItem(B.electricalScrapBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("electrical_scrap_block").register() as BlockItem
    val insulatorRoll: BlockItem = BlockItem(B.insulatorRoll, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("insulator_roll").register() as BlockItem
    val fiberglassRoll: BlockItem = BlockItem(B.fiberglassRoll, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("fiberglass_roll").register() as BlockItem
    val asbestosBlock: BlockItem = BlockItem(B.asbestosBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("asbestos_block").register() as BlockItem
    val trinititeBlock: BlockItem = BlockItem(B.trinititeBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("trinitite_block").register() as BlockItem
    val nuclearWasteBlock: BlockItem = BlockItem(B.nuclearWasteBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("nuclear_waste_block").register() as BlockItem
    val schrabidiumBlock: BlockItem = BlockItem(B.schrabidiumBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("schrabidium_block").register() as BlockItem
    val soliniumBlock: BlockItem = BlockItem(B.soliniumBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("solinium_block").register() as BlockItem
    val schrabidiumFuelBlock: BlockItem = BlockItem(B.schrabidiumFuelBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("schrabidium_fuel_block").register() as BlockItem
    val euphemiumBlock: BlockItem = BlockItem(B.euphemiumBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("euphemium_block").register() as BlockItem
    val schrabidiumCluster: BlockItem = BlockItem(B.schrabidiumCluster, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("schrabidium_cluster").register() as BlockItem
    val euphemiumEtchedSchrabidiumCluster: BlockItem = BlockItem(B.euphemiumEtchedSchrabidiumCluster, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("euphemium_etched_schrabidium_cluster").register() as BlockItem
    val magnetizedTungstenBlock: BlockItem = BlockItem(B.magnetizedTungstenBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("magnetized_tungsten_block").register() as BlockItem
    val combineSteelBlock: BlockItem = BlockItem(B.combineSteelBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("combine_steel_block").register() as BlockItem
    val deshReinforcedBlock: BlockItem = BlockItem(B.deshReinforcedBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("desh_reinforced_block").register() as BlockItem
    val starmetalBlock: BlockItem = BlockItem(B.starmetalBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("starmetal_block").register() as BlockItem
    val australiumBlock: BlockItem = BlockItem(B.australiumBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("australium_block").register() as BlockItem
    val weidaniumBlock: BlockItem = BlockItem(B.weidaniumBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("weidanium_block").register() as BlockItem
    val reiiumBlock: BlockItem = BlockItem(B.reiiumBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("reiium_block").register() as BlockItem
    val unobtainiumBlock: BlockItem = BlockItem(B.unobtainiumBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("unobtainium_block").register() as BlockItem
    val daffergonBlock: BlockItem = BlockItem(B.daffergonBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("daffergon_block").register() as BlockItem
    val verticiumBlock: BlockItem = BlockItem(B.verticiumBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("verticium_block").register() as BlockItem
    val titaniumDecoBlock: BlockItem = BlockItem(B.titaniumDecoBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("titanium_deco_block").register() as BlockItem
    val redCopperDecoBlock: BlockItem = BlockItem(B.redCopperDecoBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("red_copper_deco_block").register() as BlockItem
    val tungstenDecoBlock: BlockItem = BlockItem(B.tungstenDecoBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("tungsten_deco_block").register() as BlockItem
    val aluminiumDecoBlock: BlockItem = BlockItem(B.aluminiumDecoBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("aluminium_deco_block").register() as BlockItem
    val steelDecoBlock: BlockItem = BlockItem(B.steelDecoBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("steel_deco_block").register() as BlockItem
    val leadDecoBlock: BlockItem = BlockItem(B.leadDecoBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("lead_deco_block").register() as BlockItem
    val berylliumDecoBlock: BlockItem = BlockItem(B.berylliumDecoBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("beryllium_deco_block").register() as BlockItem
    val asbestosRoof: BlockItem = BlockItem(B.asbestosRoof, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("asbestos_roof").register() as BlockItem
    val hazmatBlock: BlockItem = BlockItem(B.hazmatBlock, Item.Properties().tab(CT.Blocks.itemGroup)).setRegistryName("hazmat_block").register() as BlockItem

    val siren: BlockItem = BlockItem(B.siren, Item.Properties().tab(CT.Machines.itemGroup)).setRegistryName("siren").register() as BlockItem
    val safe: BlockItem = BlockItem(B.safe, Item.Properties().tab(CT.Machines.itemGroup)).setRegistryName("safe").register() as BlockItem
    val steamPress: BlockItem = BlockItem(B.steamPressBase, Item.Properties().tab(CT.Machines.itemGroup)).setRegistryName("steam_press").register() as BlockItem
}
