package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.CreativeTabs
import at.martinthedragon.nucleartech.RegistriesAndLifecycle.ITEMS
import at.martinthedragon.nucleartech.block.*
import at.martinthedragon.nucleartech.block.entity.*
import at.martinthedragon.nucleartech.block.entity.rbmk.*
import at.martinthedragon.nucleartech.block.rbmk.RBMKConsoleBlock
import at.martinthedragon.nucleartech.registerK
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.crafting.RecipeType
import net.minecraftforge.registries.RegistryObject

@Suppress("unused")
object NTechBlockItems {
    val uraniumOre = ITEMS.registerK("uranium_ore") { BlockItem(NTechBlocks.uraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val scorchedUraniumOre = ITEMS.registerK("scorched_uranium_ore") { BlockItem(NTechBlocks.scorchedUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val thoriumOre = ITEMS.registerK("thorium_ore") { BlockItem(NTechBlocks.thoriumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val titaniumOre = ITEMS.registerK("titanium_ore") { BlockItem(NTechBlocks.titaniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val sulfurOre = ITEMS.registerK("sulfur_ore") { BlockItem(NTechBlocks.sulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val niterOre = ITEMS.registerK("niter_ore") { BlockItem(NTechBlocks.niterOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val tungstenOre = ITEMS.registerK("tungsten_ore") { BlockItem(NTechBlocks.tungstenOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val aluminiumOre = ITEMS.registerK("aluminium_ore") { BlockItem(NTechBlocks.aluminiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val fluoriteOre = ITEMS.registerK("fluorite_ore") { BlockItem(NTechBlocks.fluoriteOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val berylliumOre = ITEMS.registerK("beryllium_ore") { BlockItem(NTechBlocks.berylliumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val leadOre = ITEMS.registerK("lead_ore") { BlockItem(NTechBlocks.leadOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val oilDeposit = ITEMS.registerK("oil_deposit") { AutoTooltippedBlockItem(NTechBlocks.oilDeposit.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val emptyOilDeposit = ITEMS.registerK("empty_oil_deposit") { BlockItem(NTechBlocks.emptyOilDeposit.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val oilSand = ITEMS.registerK("oil_sand") { BlockItem(NTechBlocks.oilSand.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val ligniteOre = ITEMS.registerK("lignite_ore") { BlockItem(NTechBlocks.ligniteOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val asbestosOre = ITEMS.registerK("asbestos_ore") { BlockItem(NTechBlocks.asbestosOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val schrabidiumOre = ITEMS.registerK("schrabidium_ore") { BlockItem(NTechBlocks.schrabidiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.RARE)) }
    val australianOre = ITEMS.registerK("australian_ore") { AutoTooltippedBlockItem(NTechBlocks.australianOre.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val weidite = ITEMS.registerK("weidite") { AutoTooltippedBlockItem(NTechBlocks.weidite.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val reiite = ITEMS.registerK("reiite") { AutoTooltippedBlockItem(NTechBlocks.reiite.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val brightblendeOre = ITEMS.registerK("brightblende_ore") { AutoTooltippedBlockItem(NTechBlocks.brightblendeOre.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val dellite = ITEMS.registerK("dellite") { AutoTooltippedBlockItem(NTechBlocks.dellite.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val dollarGreenMineral = ITEMS.registerK("dollar_green_mineral") { AutoTooltippedBlockItem(NTechBlocks.dollarGreenMineral.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val rareEarthOre = ITEMS.registerK("rare_earth_ore") { BlockItem(NTechBlocks.rareEarthOre.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val cobaltOre = ITEMS.registerK("cobalt_ore") { BlockItem(NTechBlocks.cobaltOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateUraniumOre = ITEMS.registerK("deepslate_uranium_ore") { BlockItem(NTechBlocks.deepslateUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val scorchedDeepslateUraniumOre = ITEMS.registerK("scorched_deepslate_uranium_ore") { BlockItem(NTechBlocks.scorchedDeepslateUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateThoriumOre = ITEMS.registerK("deepslate_thorium_ore") { BlockItem(NTechBlocks.deepslateThoriumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateTitaniumOre = ITEMS.registerK("deepslate_titanium_ore") { BlockItem(NTechBlocks.deepslateTitaniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateSulfurOre = ITEMS.registerK("deepslate_sulfur_ore") { BlockItem(NTechBlocks.deepslateSulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateNiterOre = ITEMS.registerK("deepslate_niter_ore") { BlockItem(NTechBlocks.deepslateNiterOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateTungstenOre = ITEMS.registerK("deepslate_tungsten_ore") { BlockItem(NTechBlocks.deepslateTungstenOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateAluminiumOre = ITEMS.registerK("deepslate_aluminium_ore") { BlockItem(NTechBlocks.deepslateAluminiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateFluoriteOre = ITEMS.registerK("deepslate_fluorite_ore") { BlockItem(NTechBlocks.deepslateFluoriteOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateBerylliumOre = ITEMS.registerK("deepslate_beryllium_ore") { BlockItem(NTechBlocks.deepslateBerylliumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateLeadOre = ITEMS.registerK("deepslate_lead_ore") { BlockItem(NTechBlocks.deepslateLeadOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateOilDeposit = ITEMS.registerK("deepslate_oil_deposit") { AutoTooltippedBlockItem(NTechBlocks.deepslateOilDeposit.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val emptyDeepslateOilDeposit = ITEMS.registerK("empty_deepslate_oil_deposit") { BlockItem(NTechBlocks.emptyDeepslateOilDeposit.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateAsbestosOre = ITEMS.registerK("deepslate_asbestos_ore") { BlockItem(NTechBlocks.deepslateAsbestosOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateSchrabidiumOre = ITEMS.registerK("deepslate_schrabidium_ore") { BlockItem(NTechBlocks.deepslateSchrabidiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateAustralianOre = ITEMS.registerK("deepslate_australian_ore") { AutoTooltippedBlockItem(NTechBlocks.deepslateAustralianOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateRareEarthOre = ITEMS.registerK("deepslate_rare_earth_ore") { BlockItem(NTechBlocks.deepslateRareEarthOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateCobaltOre = ITEMS.registerK("deepslate_cobalt_ore") { BlockItem(NTechBlocks.deepslateCobaltOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherUraniumOre = ITEMS.registerK("nether_uranium_ore") { BlockItem(NTechBlocks.netherUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val scorchedNetherUraniumOre = ITEMS.registerK("scorched_nether_uranium_ore") { BlockItem(NTechBlocks.scorchedNetherUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherPlutoniumOre = ITEMS.registerK("nether_plutonium_ore") { BlockItem(NTechBlocks.netherPlutoniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherTungstenOre = ITEMS.registerK("nether_tungsten_ore") { BlockItem(NTechBlocks.netherTungstenOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherSulfurOre = ITEMS.registerK("nether_sulfur_ore") { BlockItem(NTechBlocks.netherSulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherPhosphorusOre = ITEMS.registerK("nether_phosphorus_ore") { BlockItem(NTechBlocks.netherPhosphorusOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherSchrabidiumOre = ITEMS.registerK("nether_schrabidium_ore") { BlockItem(NTechBlocks.netherSchrabidiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.RARE)) }
    val meteorUraniumOre = ITEMS.registerK("meteor_uranium_ore") { BlockItem(NTechBlocks.meteorUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorThoriumOre = ITEMS.registerK("meteor_thorium_ore") { BlockItem(NTechBlocks.meteorThoriumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorTitaniumOre = ITEMS.registerK("meteor_titanium_ore") { BlockItem(NTechBlocks.meteorTitaniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorSulfurOre = ITEMS.registerK("meteor_sulfur_ore") { BlockItem(NTechBlocks.meteorSulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorCopperOre = ITEMS.registerK("meteor_copper_ore") { BlockItem(NTechBlocks.meteorCopperOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorTungstenOre = ITEMS.registerK("meteor_tungsten_ore") { BlockItem(NTechBlocks.meteorTungstenOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorAluminiumOre = ITEMS.registerK("meteor_aluminium_ore") { BlockItem(NTechBlocks.meteorAluminiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorLeadOre = ITEMS.registerK("meteor_lead_ore") { BlockItem(NTechBlocks.meteorLeadOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorLithiumOre = ITEMS.registerK("meteor_lithium_ore") { BlockItem(NTechBlocks.meteorLithiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val starmetalOre = ITEMS.registerK("starmetal_ore") { BlockItem(NTechBlocks.starmetalOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val trixite = ITEMS.registerK("trixite") { BlockItem(NTechBlocks.trixite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val basaltSulfurOre = ITEMS.registerK("basalt_sulfur_ore") { BlockItem(NTechBlocks.basaltSulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val basaltFluoriteOre = ITEMS.registerK("basalt_fluorite_ore") { BlockItem(NTechBlocks.basaltFluoriteOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val basaltAsbestosOre = ITEMS.registerK("basalt_asbestos_ore") { BlockItem(NTechBlocks.basaltAsbestosOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val basaltVolcanicGemOre = ITEMS.registerK("basalt_volcanic_gem_ore") { BlockItem(NTechBlocks.basaltVolcanicGemOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }

    val uraniumBlock = ITEMS.registerK("uranium_block") { BlockItem(NTechBlocks.uraniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val u233Block = ITEMS.registerK("u233_block") { BlockItem(NTechBlocks.u233Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val u235Block = ITEMS.registerK("u235_block") { BlockItem(NTechBlocks.u235Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val u238Block = ITEMS.registerK("u238_block") { BlockItem(NTechBlocks.u238Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val uraniumFuelBlock = ITEMS.registerK("uranium_fuel_block") { BlockItem(NTechBlocks.uraniumFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val neptuniumBlock = ITEMS.registerK("neptunium_block") { BlockItem(NTechBlocks.neptuniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val moxFuelBlock = ITEMS.registerK("mox_fuel_block") { BlockItem(NTechBlocks.moxFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val plutoniumBlock = ITEMS.registerK("plutonium_block") { BlockItem(NTechBlocks.plutoniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val pu238Block = ITEMS.registerK("pu238_block") { BlockItem(NTechBlocks.pu238Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val pu239Block = ITEMS.registerK("pu239_block") { BlockItem(NTechBlocks.pu239Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val pu240Block = ITEMS.registerK("pu240_block") { BlockItem(NTechBlocks.pu240Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val plutoniumFuelBlock = ITEMS.registerK("plutonium_fuel_block") { BlockItem(NTechBlocks.plutoniumFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val thoriumBlock = ITEMS.registerK("thorium_block") { BlockItem(NTechBlocks.thoriumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val thoriumFuelBlock = ITEMS.registerK("thorium_fuel_block") { BlockItem(NTechBlocks.thoriumFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val titaniumBlock = ITEMS.registerK("titanium_block") { BlockItem(NTechBlocks.titaniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val sulfurBlock = ITEMS.registerK("sulfur_block") { BlockItem(NTechBlocks.sulfurBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val niterBlock = ITEMS.registerK("niter_block") { BlockItem(NTechBlocks.niterBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val redCopperBlock = ITEMS.registerK("red_copper_block") { BlockItem(NTechBlocks.redCopperBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val advancedAlloyBlock = ITEMS.registerK("advanced_alloy_block") { BlockItem(NTechBlocks.advancedAlloyBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val tungstenBlock = ITEMS.registerK("tungsten_block") { BlockItem(NTechBlocks.tungstenBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val aluminiumBlock = ITEMS.registerK("aluminium_block") { BlockItem(NTechBlocks.aluminiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val fluoriteBlock = ITEMS.registerK("fluorite_block") { BlockItem(NTechBlocks.fluoriteBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val berylliumBlock = ITEMS.registerK("beryllium_block") { BlockItem(NTechBlocks.berylliumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val cobaltBlock = ITEMS.registerK("cobalt_block") { BlockItem(NTechBlocks.cobaltBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val steelBlock = ITEMS.registerK("steel_block") { BlockItem(NTechBlocks.steelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val leadBlock = ITEMS.registerK("lead_block") { BlockItem(NTechBlocks.leadBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val lithiumBlock = ITEMS.registerK("lithium_block") { BlockItem(NTechBlocks.lithiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val whitePhosphorusBlock = ITEMS.registerK("white_phosphorus_block") { BlockItem(NTechBlocks.whitePhosphorusBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val redPhosphorusBlock = ITEMS.registerK("red_phosphorus_block") { BlockItem(NTechBlocks.redPhosphorusBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val yellowcakeBlock = ITEMS.registerK("yellowcake_block") { BlockItem(NTechBlocks.yellowcakeBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val scrapBlock: RegistryObject<BlockItem> = ITEMS.registerK("scrap_block") { object : BlockItem(NTechBlocks.scrapBlock.get(), Properties().tab(CreativeTabs.Blocks)) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 4000
    }}
    val electricalScrapBlock = ITEMS.registerK("electrical_scrap_block") { BlockItem(NTechBlocks.electricalScrapBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val insulatorRoll = ITEMS.registerK("insulator_roll") { BlockItem(NTechBlocks.insulatorRoll.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val fiberglassRoll = ITEMS.registerK("fiberglass_roll") { BlockItem(NTechBlocks.fiberglassRoll.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val asbestosBlock = ITEMS.registerK("asbestos_block") { BlockItem(NTechBlocks.asbestosBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val trinititeBlock = ITEMS.registerK("trinitite_block") { BlockItem(NTechBlocks.trinititeBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val nuclearWasteBlock = ITEMS.registerK("nuclear_waste_block") { BlockItem(NTechBlocks.nuclearWasteBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val schrabidiumBlock = ITEMS.registerK("schrabidium_block") { BlockItem(NTechBlocks.schrabidiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val soliniumBlock = ITEMS.registerK("solinium_block") { BlockItem(NTechBlocks.soliniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val schrabidiumFuelBlock = ITEMS.registerK("schrabidium_fuel_block") { BlockItem(NTechBlocks.schrabidiumFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val euphemiumBlock = ITEMS.registerK("euphemium_block") { BlockItem(NTechBlocks.euphemiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val schrabidiumCluster = ITEMS.registerK("schrabidium_cluster") { BlockItem(NTechBlocks.schrabidiumCluster.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val euphemiumEtchedSchrabidiumCluster = ITEMS.registerK("euphemium_etched_schrabidium_cluster") { BlockItem(
        NTechBlocks.euphemiumEtchedSchrabidiumCluster.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val magnetizedTungstenBlock = ITEMS.registerK("magnetized_tungsten_block") { BlockItem(NTechBlocks.magnetizedTungstenBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val combineSteelBlock = ITEMS.registerK("combine_steel_block") { BlockItem(NTechBlocks.combineSteelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deshReinforcedBlock = ITEMS.registerK("desh_reinforced_block") { BlockItem(NTechBlocks.deshReinforcedBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val starmetalBlock = ITEMS.registerK("starmetal_block") { BlockItem(NTechBlocks.starmetalBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val australiumBlock = ITEMS.registerK("australium_block") { BlockItem(NTechBlocks.australiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val weidaniumBlock = ITEMS.registerK("weidanium_block") { BlockItem(NTechBlocks.weidaniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val reiiumBlock = ITEMS.registerK("reiium_block") { BlockItem(NTechBlocks.reiiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val unobtainiumBlock = ITEMS.registerK("unobtainium_block") { BlockItem(NTechBlocks.unobtainiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val daffergonBlock = ITEMS.registerK("daffergon_block") { BlockItem(NTechBlocks.daffergonBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val verticiumBlock = ITEMS.registerK("verticium_block") { BlockItem(NTechBlocks.verticiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val titaniumDecoBlock = ITEMS.registerK("titanium_deco_block") { BlockItem(NTechBlocks.titaniumDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val redCopperDecoBlock = ITEMS.registerK("red_copper_deco_block") { BlockItem(NTechBlocks.redCopperDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val tungstenDecoBlock = ITEMS.registerK("tungsten_deco_block") { BlockItem(NTechBlocks.tungstenDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val aluminiumDecoBlock = ITEMS.registerK("aluminium_deco_block") { BlockItem(NTechBlocks.aluminiumDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val steelDecoBlock = ITEMS.registerK("steel_deco_block") { BlockItem(NTechBlocks.steelDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val leadDecoBlock = ITEMS.registerK("lead_deco_block") { BlockItem(NTechBlocks.leadDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val berylliumDecoBlock = ITEMS.registerK("beryllium_deco_block") { BlockItem(NTechBlocks.berylliumDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val asbestosRoof = ITEMS.registerK("asbestos_roof") { BlockItem(NTechBlocks.asbestosRoof.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val hazmatBlock = ITEMS.registerK("hazmat_block") { BlockItem(NTechBlocks.hazmatBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }

    val meteorite = ITEMS.registerK("meteorite") { BlockItem(NTechBlocks.meteorite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteoriteCobblestone = ITEMS.registerK("meteorite_cobblestone") { BlockItem(NTechBlocks.meteoriteCobblestone.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val brokenMeteorite = ITEMS.registerK("broken_meteorite") { BlockItem(NTechBlocks.brokenMeteorite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val hotMeteoriteCobblestone = ITEMS.registerK("hot_meteorite_cobblestone") { BlockItem(NTechBlocks.hotMeteoriteCobblestone.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteoriteTreasure = ITEMS.registerK("meteorite_treasure") { BlockItem(NTechBlocks.meteoriteTreasure.get(), Item.Properties().tab(CreativeTabs.Blocks)) }

    val decoRbmkBlock = ITEMS.registerK("deco_rbmk") { BlockItem(NTechBlocks.decoRbmkBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val decoRbmkSmoothBlock = ITEMS.registerK("deco_rbmk_smooth") { BlockItem(NTechBlocks.decoRbmkSmoothBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }

    val steelBeam = ITEMS.registerK("steel_beam") { BlockItem(NTechBlocks.steelBeam.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val steelScaffold = ITEMS.registerK("steel_scaffold") { BlockItem(NTechBlocks.steelScaffold.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val steelGrate = ITEMS.registerK("steel_grate") { BlockItem(NTechBlocks.steelGrate.get(), Item.Properties().tab(CreativeTabs.Blocks)) }

    val glowingMushroom = ITEMS.registerK("glowing_mushroom") { BlockItem(NTechBlocks.glowingMushroom.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val glowingMushroomBlock = ITEMS.registerK("glowing_mushroom_block") { BlockItem(NTechBlocks.glowingMushroomBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val glowingMushroomStem = ITEMS.registerK("glowing_mushroom_stem") { BlockItem(NTechBlocks.glowingMushroomStem.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deadGrass = ITEMS.registerK("dead_grass") { BlockItem(NTechBlocks.deadGrass.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val glowingMycelium = ITEMS.registerK("glowing_mycelium") { BlockItem(NTechBlocks.glowingMycelium.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val trinititeOre = ITEMS.registerK("trinitite_ore") { BlockItem(NTechBlocks.trinitite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val redTrinititeOre = ITEMS.registerK("red_trinitite_ore") { BlockItem(NTechBlocks.redTrinitite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val charredLog: RegistryObject<BlockItem> = ITEMS.registerK("charred_log") { object : BlockItem(NTechBlocks.charredLog.get(), Properties().tab(CreativeTabs.Blocks)) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 300
    }}
    val charredPlanks: RegistryObject<BlockItem> = ITEMS.registerK("charred_planks") { object : BlockItem(NTechBlocks.charredPlanks.get(), Properties().tab(CreativeTabs.Blocks)) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 300
    }}

    val slakedSellafite = ITEMS.registerK("slaked_sellafite") { BlockItem(NTechBlocks.slakedSellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val sellafite = ITEMS.registerK("sellafite") { BlockItem(NTechBlocks.sellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val hotSellafite = ITEMS.registerK("hot_sellafite") { BlockItem(NTechBlocks.hotSellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val boilingSellafite = ITEMS.registerK("boiling_sellafite") { BlockItem(NTechBlocks.boilingSellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val blazingSellafite = ITEMS.registerK("blazing_sellafite") { BlockItem(NTechBlocks.blazingSellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val infernalSellafite = ITEMS.registerK("infernal_sellafite") { BlockItem(NTechBlocks.infernalSellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val sellafiteCorium = ITEMS.registerK("sellafite_corium") { BlockItem(NTechBlocks.sellafiteCorium.get(), Item.Properties().tab(CreativeTabs.Blocks)) }

    val corium = ITEMS.registerK("corium") { BlockItem(NTechBlocks.corium.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val corebblestone = ITEMS.registerK("corebblestone") { BlockItem(NTechBlocks.corebblestone.get(), Item.Properties().tab(CreativeTabs.Blocks)) }

    val siren = ITEMS.registerK("siren") { BlockItem(NTechBlocks.siren.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val safe = ITEMS.registerK("safe") { BlockItem(NTechBlocks.safe.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val steamPress = ITEMS.registerK("steam_press") { BlockItem(NTechBlocks.steamPressBase.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val blastFurnace = ITEMS.registerK("blast_furnace") { BlockItem(NTechBlocks.blastFurnace.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val combustionGenerator = ITEMS.registerK("combustion_generator") { BlockItem(NTechBlocks.combustionGenerator.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val electricFurnace = ITEMS.registerK("electric_furnace") { BlockItem(NTechBlocks.electricFurnace.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val shredder = ITEMS.registerK("shredder") { BlockItem(NTechBlocks.shredder.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val assemblerPlacer = ITEMS.registerK("assembler") { SpecialModelMultiBlockPlacerItem(NTechBlocks.assembler.get(), ::AssemblerBlockEntity, AssemblerBlock::placeMultiBlock, Item.Properties().tab(CreativeTabs.Machines)) }
    val chemPlantPlacer = ITEMS.registerK("chem_plant") { SpecialModelMultiBlockPlacerItem(NTechBlocks.chemPlant.get(), ::ChemPlantBlockEntity, ChemPlantBlock::placeMultiBlock, Item.Properties().tab(CreativeTabs.Machines)) }
    val oilDerrickPlacer = ITEMS.registerK("oil_derrick") { SpecialModelMultiBlockPlacerItem(NTechBlocks.oilDerrick.get(), ::OilDerrickBlockEntity, OilDerrickBlock::placeMultiBlock, Item.Properties().tab(CreativeTabs.Machines)) }
    val pumpjackPlacer = ITEMS.registerK("pumpjack") { PumpjackPlacerItem(NTechBlocks.pumpjack.get(), ::PumpjackBlockEntity, PumpjackBlock::placeMultiBlock, Item.Properties().tab(CreativeTabs.Machines)) }
    val centrifugePlacer = ITEMS.registerK("centrifuge") { SpecialModelMultiBlockPlacerItem(NTechBlocks.centrifuge.get(), ::CentrifugeBlockEntity, CentrifugeBlock::placeMultiBlock, Item.Properties().tab(CreativeTabs.Machines)) }

    val ironAnvil = ITEMS.registerK("iron_anvil") { BlockItem(NTechBlocks.ironAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val leadAnvil = ITEMS.registerK("lead_anvil") { BlockItem(NTechBlocks.leadAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val steelAnvil = ITEMS.registerK("steel_anvil") { BlockItem(NTechBlocks.steelAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val meteoriteAnvil = ITEMS.registerK("meteorite_anvil") { BlockItem(NTechBlocks.meteoriteAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val starmetalAnvil = ITEMS.registerK("starmetal_anvil") { BlockItem(NTechBlocks.starmetalAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val ferrouraniumAnvil = ITEMS.registerK("ferrouranium_anvil") { BlockItem(NTechBlocks.ferrouraniumAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val bismuthAnvil = ITEMS.registerK("bismuth_anvil") { BlockItem(NTechBlocks.bismuthAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val schrabidateAnvil = ITEMS.registerK("schrabidate_anvil") { BlockItem(NTechBlocks.schrabidateAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val dineutroniumAnvil = ITEMS.registerK("dineutronium_anvil") { BlockItem(NTechBlocks.dineutroniumAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val murkyAnvil = ITEMS.registerK("murky_anvil") { BlockItem(NTechBlocks.murkyAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }

    val rbmkRod = ITEMS.registerK("rbmk_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkRod.get(), ::RBMKRodBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkModeratedRod = ITEMS.registerK("rbmk_moderated_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkModeratedRod.get(), ::RBMKModeratedRodBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkReaSimRod = ITEMS.registerK("rbmk_reasim_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkReaSimRod.get(), ::RBMKReaSimRodBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkModeratedReaSimRod = ITEMS.registerK("rbmk_moderated_reasim_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkModeratedReaSimRod.get(), ::RBMKModeratedReaSimRodBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkReflector = ITEMS.registerK("rbmk_reflector") { RBMKColumnBlockItem(NTechBlocks.rbmkReflector.get(), ::RBMKReflectorBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkModerator = ITEMS.registerK("rbmk_moderator") { RBMKColumnBlockItem(NTechBlocks.rbmkModerator.get(), ::RBMKModeratorBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkAbsorber = ITEMS.registerK("rbmk_absorber") { RBMKColumnBlockItem(NTechBlocks.rbmkAbsorber.get(), ::RBMKAbsorberBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkBlank = ITEMS.registerK("rbmk_blank") { RBMKColumnBlockItem(NTechBlocks.rbmkBlank.get(), ::RBMKBlankBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkBoiler = ITEMS.registerK("rbmk_boiler") { RBMKBoilerColumnBlockItem(NTechBlocks.rbmkBoiler.get(), ::RBMKBoilerBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkManualControlRod = ITEMS.registerK("rbmk_manual_control_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkManualControlRod.get(), ::RBMKManualControlBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkModeratedControlRod = ITEMS.registerK("rbmk_moderated_control_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkModeratedControlRod.get(), ::RBMKModeratedControlBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkAutoControlRod = ITEMS.registerK("rbmk_auto_control_rod") { RBMKColumnBlockItem(NTechBlocks.rbmkAutoControlRod.get(), ::RBMKAutoControlBlockEntity, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkSteamConnector = ITEMS.registerK("rbmk_steam_connector") { BlockItem(NTechBlocks.rbmkSteamConnector.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkInlet = ITEMS.registerK("rbmk_inlet") { BlockItem(NTechBlocks.rbmkInlet.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkOutlet = ITEMS.registerK("rbmk_outlet") { BlockItem(NTechBlocks.rbmkOutlet.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkConsole = ITEMS.registerK("rbmk_console") { SpecialModelMultiBlockPlacerItem(NTechBlocks.rbmkConsole.get(), ::RBMKConsoleBlockEntity, RBMKConsoleBlock::placeMultiBlock, Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkDebris = ITEMS.registerK("rbmk_debris") { BlockItem(NTechBlocks.rbmkDebris.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkBurningDebris = ITEMS.registerK("rbmk_burning_debris") { BlockItem(NTechBlocks.rbmkBurningDebris.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val rbmkRadioactiveDebris = ITEMS.registerK("rbmk_radioactive_debris") { BlockItem(NTechBlocks.rbmkRadioactiveDebris.get(), Item.Properties().tab(CreativeTabs.Machines)) }

    val coatedCable = ITEMS.registerK("coated_red_copper_cable") { BlockItem(NTechBlocks.coatedCable.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val coatedUniversalFluidDuct = ITEMS.registerK("coated_fluid_duct") { BlockItem(NTechBlocks.coatedUniversalFluidDuct.get(), Item.Properties().tab(CreativeTabs.Machines)) }

    val littleBoy = ITEMS.registerK("little_boy") { SpecialModelBlockItem(NTechBlocks.littleBoy.get(), ::LittleBoyBlockEntity, Item.Properties().tab(CreativeTabs.Bombs)) }
    val fatMan = ITEMS.registerK("fat_man") { SpecialModelBlockItem(NTechBlocks.fatMan.get(), ::FatManBlockEntity, Item.Properties().tab(CreativeTabs.Bombs)) }
    val volcanoCore = ITEMS.registerK("volcano_core") { BlockItem(NTechBlocks.volcanoCore.get(), Item.Properties().tab(CreativeTabs.Bombs)) }

    val launchPadPlacer = ITEMS.registerK("launch_pad") { SpecialModelMultiBlockPlacerItem(NTechBlocks.launchPad.get(), ::LaunchPadBlockEntity, LaunchPadBlock::placeMultiBlock, Item.Properties().tab(CreativeTabs.Rocketry)) }

    private fun Item.Properties.tab(tab: CreativeTabs): Item.Properties = tab(tab.tab)
}
