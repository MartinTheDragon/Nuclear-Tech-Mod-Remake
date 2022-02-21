package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.ITEMS
import at.martinthedragon.nucleartech.blocks.Assembler
import at.martinthedragon.nucleartech.items.AutoTooltippedBlockItem
import at.martinthedragon.nucleartech.items.MultiBlockPlacerItem
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.crafting.RecipeType
import net.minecraftforge.registries.RegistryObject

@Suppress("unused")
object ModBlockItems {
    val uraniumOre: RegistryObject<Item> = ITEMS.register("uranium_ore") { BlockItem(ModBlocks.uraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val scorchedUraniumOre: RegistryObject<Item> = ITEMS.register("scorched_uranium_ore") { BlockItem(ModBlocks.scorchedUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val thoriumOre: RegistryObject<Item> = ITEMS.register("thorium_ore") { BlockItem(ModBlocks.thoriumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val titaniumOre: RegistryObject<Item> = ITEMS.register("titanium_ore") { BlockItem(ModBlocks.titaniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val sulfurOre: RegistryObject<Item> = ITEMS.register("sulfur_ore") { BlockItem(ModBlocks.sulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val niterOre: RegistryObject<Item> = ITEMS.register("niter_ore") { BlockItem(ModBlocks.niterOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val tungstenOre: RegistryObject<Item> = ITEMS.register("tungsten_ore") { BlockItem(ModBlocks.tungstenOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val aluminiumOre: RegistryObject<Item> = ITEMS.register("aluminium_ore") { BlockItem(ModBlocks.aluminiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val fluoriteOre: RegistryObject<Item> = ITEMS.register("fluorite_ore") { BlockItem(ModBlocks.fluoriteOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val berylliumOre: RegistryObject<Item> = ITEMS.register("beryllium_ore") { BlockItem(ModBlocks.berylliumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val leadOre: RegistryObject<Item> = ITEMS.register("lead_ore") { BlockItem(ModBlocks.leadOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val oilDeposit: RegistryObject<Item> = ITEMS.register("oil_deposit") { AutoTooltippedBlockItem(ModBlocks.oilDeposit.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val emptyOilDeposit: RegistryObject<Item> = ITEMS.register("empty_oil_deposit") { BlockItem(ModBlocks.emptyOilDeposit.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val oilSand: RegistryObject<Item> = ITEMS.register("oil_sand") { BlockItem(ModBlocks.oilSand.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val ligniteOre: RegistryObject<Item> = ITEMS.register("lignite_ore") { BlockItem(ModBlocks.ligniteOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val asbestosOre: RegistryObject<Item> = ITEMS.register("asbestos_ore") { BlockItem(ModBlocks.asbestosOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val schrabidiumOre: RegistryObject<Item> = ITEMS.register("schrabidium_ore") { BlockItem(ModBlocks.schrabidiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.RARE)) }
    val australianOre: RegistryObject<Item> = ITEMS.register("australian_ore") { AutoTooltippedBlockItem(ModBlocks.australianOre.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val weidite: RegistryObject<Item> = ITEMS.register("weidite") { AutoTooltippedBlockItem(ModBlocks.weidite.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val reiite: RegistryObject<Item> = ITEMS.register("reiite") { AutoTooltippedBlockItem(ModBlocks.reiite.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val brightblendeOre: RegistryObject<Item> = ITEMS.register("brightblende_ore") { AutoTooltippedBlockItem(ModBlocks.brightblendeOre.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val dellite: RegistryObject<Item> = ITEMS.register("dellite") { AutoTooltippedBlockItem(ModBlocks.dellite.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val dollarGreenMineral: RegistryObject<Item> = ITEMS.register("dollar_green_mineral") { AutoTooltippedBlockItem(ModBlocks.dollarGreenMineral.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val rareEarthOre: RegistryObject<Item> = ITEMS.register("rare_earth_ore") { BlockItem(ModBlocks.rareEarthOre.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val cobaltOre: RegistryObject<Item> = ITEMS.register("cobalt_ore") { BlockItem(ModBlocks.cobaltOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateUraniumOre: RegistryObject<Item> = ITEMS.register("deepslate_uranium_ore") { BlockItem(ModBlocks.deepslateUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val scorchedDeepslateUraniumOre: RegistryObject<Item> = ITEMS.register("scorched_deepslate_uranium_ore") { BlockItem(ModBlocks.scorchedDeepslateUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateThoriumOre: RegistryObject<Item> = ITEMS.register("deepslate_thorium_ore") { BlockItem(ModBlocks.deepslateThoriumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateTitaniumOre: RegistryObject<Item> = ITEMS.register("deepslate_titanium_ore") { BlockItem(ModBlocks.deepslateTitaniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateSulfurOre: RegistryObject<Item> = ITEMS.register("deepslate_sulfur_ore") { BlockItem(ModBlocks.deepslateSulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateNiterOre: RegistryObject<Item> = ITEMS.register("deepslate_niter_ore") { BlockItem(ModBlocks.deepslateNiterOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateTungstenOre: RegistryObject<Item> = ITEMS.register("deepslate_tungsten_ore") { BlockItem(ModBlocks.deepslateTungstenOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateAluminiumOre: RegistryObject<Item> = ITEMS.register("deepslate_aluminium_ore") { BlockItem(ModBlocks.deepslateAluminiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateFluoriteOre: RegistryObject<Item> = ITEMS.register("deepslate_fluorite_ore") { BlockItem(ModBlocks.deepslateFluoriteOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateBerylliumOre: RegistryObject<Item> = ITEMS.register("deepslate_beryllium_ore") { BlockItem(ModBlocks.deepslateBerylliumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateLeadOre: RegistryObject<Item> = ITEMS.register("deepslate_lead_ore") { BlockItem(ModBlocks.deepslateLeadOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateOilDeposit: RegistryObject<Item> = ITEMS.register("deepslate_oil_deposit") { AutoTooltippedBlockItem(ModBlocks.deepslateOilDeposit.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val emptyDeepslateOilDeposit: RegistryObject<Item> = ITEMS.register("empty_deepslate_oil_deposit") { BlockItem(ModBlocks.emptyDeepslateOilDeposit.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateAsbestosOre: RegistryObject<Item> = ITEMS.register("deepslate_asbestos_ore") { BlockItem(ModBlocks.deepslateAsbestosOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateSchrabidiumOre: RegistryObject<Item> = ITEMS.register("deepslate_schrabidium_ore") { BlockItem(ModBlocks.deepslateSchrabidiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateAustralianOre: RegistryObject<Item> = ITEMS.register("deepslate_australian_ore") { AutoTooltippedBlockItem(ModBlocks.deepslateAustralianOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateRareEarthOre: RegistryObject<Item> = ITEMS.register("deepslate_rare_earth_ore") { BlockItem(ModBlocks.deepslateRareEarthOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateCobaltOre: RegistryObject<Item> = ITEMS.register("deepslate_cobalt_ore") { BlockItem(ModBlocks.deepslateCobaltOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherUraniumOre: RegistryObject<Item> = ITEMS.register("nether_uranium_ore") { BlockItem(ModBlocks.netherUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val scorchedNetherUraniumOre: RegistryObject<Item> = ITEMS.register("scorched_nether_uranium_ore") { BlockItem(ModBlocks.scorchedNetherUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherPlutoniumOre: RegistryObject<Item> = ITEMS.register("nether_plutonium_ore") { BlockItem(ModBlocks.netherPlutoniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherTungstenOre: RegistryObject<Item> = ITEMS.register("nether_tungsten_ore") { BlockItem(ModBlocks.netherTungstenOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherSulfurOre: RegistryObject<Item> = ITEMS.register("nether_sulfur_ore") { BlockItem(ModBlocks.netherSulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherPhosphorusOre: RegistryObject<Item> = ITEMS.register("nether_phosphorus_ore") { BlockItem(ModBlocks.netherPhosphorusOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherSchrabidiumOre: RegistryObject<Item> = ITEMS.register("nether_schrabidium_ore") { BlockItem(ModBlocks.netherSchrabidiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.RARE)) }
    val meteorUraniumOre: RegistryObject<Item> = ITEMS.register("meteor_uranium_ore") { BlockItem(ModBlocks.meteorUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorThoriumOre: RegistryObject<Item> = ITEMS.register("meteor_thorium_ore") { BlockItem(ModBlocks.meteorThoriumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorTitaniumOre: RegistryObject<Item> = ITEMS.register("meteor_titanium_ore") { BlockItem(ModBlocks.meteorTitaniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorSulfurOre: RegistryObject<Item> = ITEMS.register("meteor_sulfur_ore") { BlockItem(ModBlocks.meteorSulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorCopperOre: RegistryObject<Item> = ITEMS.register("meteor_copper_ore") { BlockItem(ModBlocks.meteorCopperOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorTungstenOre: RegistryObject<Item> = ITEMS.register("meteor_tungsten_ore") { BlockItem(ModBlocks.meteorTungstenOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorAluminiumOre: RegistryObject<Item> = ITEMS.register("meteor_aluminium_ore") { BlockItem(ModBlocks.meteorAluminiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorLeadOre: RegistryObject<Item> = ITEMS.register("meteor_lead_ore") { BlockItem(ModBlocks.meteorLeadOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorLithiumOre: RegistryObject<Item> = ITEMS.register("meteor_lithium_ore") { BlockItem(ModBlocks.meteorLithiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val starmetalOre: RegistryObject<Item> = ITEMS.register("starmetal_ore") { BlockItem(ModBlocks.starmetalOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val trixite: RegistryObject<Item> = ITEMS.register("trixite") { BlockItem(ModBlocks.trixite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }

    val uraniumBlock: RegistryObject<Item> = ITEMS.register("uranium_block") { BlockItem(ModBlocks.uraniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val u233Block: RegistryObject<Item> = ITEMS.register("u233_block") { BlockItem(ModBlocks.u233Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val u235Block: RegistryObject<Item> = ITEMS.register("u235_block") { BlockItem(ModBlocks.u235Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val u238Block: RegistryObject<Item> = ITEMS.register("u238_block") { BlockItem(ModBlocks.u238Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val uraniumFuelBlock: RegistryObject<Item> = ITEMS.register("uranium_fuel_block") { BlockItem(ModBlocks.uraniumFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val neptuniumBlock: RegistryObject<Item> = ITEMS.register("neptunium_block") { BlockItem(ModBlocks.neptuniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val moxFuelBlock: RegistryObject<Item> = ITEMS.register("mox_fuel_block") { BlockItem(ModBlocks.moxFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val plutoniumBlock: RegistryObject<Item> = ITEMS.register("plutonium_block") { BlockItem(ModBlocks.plutoniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val pu238Block: RegistryObject<Item> = ITEMS.register("pu238_block") { BlockItem(ModBlocks.pu238Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val pu239Block: RegistryObject<Item> = ITEMS.register("pu239_block") { BlockItem(ModBlocks.pu239Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val pu240Block: RegistryObject<Item> = ITEMS.register("pu240_block") { BlockItem(ModBlocks.pu240Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val plutoniumFuelBlock: RegistryObject<Item> = ITEMS.register("plutonium_fuel_block") { BlockItem(ModBlocks.plutoniumFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val thoriumBlock: RegistryObject<Item> = ITEMS.register("thorium_block") { BlockItem(ModBlocks.thoriumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val thoriumFuelBlock: RegistryObject<Item> = ITEMS.register("thorium_fuel_block") { BlockItem(ModBlocks.thoriumFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val titaniumBlock: RegistryObject<Item> = ITEMS.register("titanium_block") { BlockItem(ModBlocks.titaniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val sulfurBlock: RegistryObject<Item> = ITEMS.register("sulfur_block") { BlockItem(ModBlocks.sulfurBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val niterBlock: RegistryObject<Item> = ITEMS.register("niter_block") { BlockItem(ModBlocks.niterBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val copperBlock: RegistryObject<Item> = ITEMS.register("copper_block") { BlockItem(ModBlocks.copperBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val redCopperBlock: RegistryObject<Item> = ITEMS.register("red_copper_block") { BlockItem(ModBlocks.redCopperBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val advancedAlloyBlock: RegistryObject<Item> = ITEMS.register("advanced_alloy_block") { BlockItem(ModBlocks.advancedAlloyBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val tungstenBlock: RegistryObject<Item> = ITEMS.register("tungsten_block") { BlockItem(ModBlocks.tungstenBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val aluminiumBlock: RegistryObject<Item> = ITEMS.register("aluminium_block") { BlockItem(ModBlocks.aluminiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val fluoriteBlock: RegistryObject<Item> = ITEMS.register("fluorite_block") { BlockItem(ModBlocks.fluoriteBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val berylliumBlock: RegistryObject<Item> = ITEMS.register("beryllium_block") { BlockItem(ModBlocks.berylliumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val cobaltBlock: RegistryObject<Item> = ITEMS.register("cobalt_block") { BlockItem(ModBlocks.cobaltBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val steelBlock: RegistryObject<Item> = ITEMS.register("steel_block") { BlockItem(ModBlocks.steelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val leadBlock: RegistryObject<Item> = ITEMS.register("lead_block") { BlockItem(ModBlocks.leadBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val lithiumBlock: RegistryObject<Item> = ITEMS.register("lithium_block") { BlockItem(ModBlocks.lithiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val whitePhosphorusBlock: RegistryObject<Item> = ITEMS.register("white_phosphorus_block") { BlockItem(ModBlocks.whitePhosphorusBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val redPhosphorusBlock: RegistryObject<Item> = ITEMS.register("red_phosphorus_block") { BlockItem(ModBlocks.redPhosphorusBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val yellowcakeBlock: RegistryObject<Item> = ITEMS.register("yellowcake_block") { BlockItem(ModBlocks.yellowcakeBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val scrapBlock: RegistryObject<Item> = ITEMS.register("scrap_block") { object : BlockItem(ModBlocks.scrapBlock.get(), Properties().tab(CreativeTabs.Blocks)) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 4000
    }}
    val electricalScrapBlock: RegistryObject<Item> = ITEMS.register("electrical_scrap_block") { BlockItem(ModBlocks.electricalScrapBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val insulatorRoll: RegistryObject<Item> = ITEMS.register("insulator_roll") { BlockItem(ModBlocks.insulatorRoll.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val fiberglassRoll: RegistryObject<Item> = ITEMS.register("fiberglass_roll") { BlockItem(ModBlocks.fiberglassRoll.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val asbestosBlock: RegistryObject<Item> = ITEMS.register("asbestos_block") { BlockItem(ModBlocks.asbestosBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val trinititeBlock: RegistryObject<Item> = ITEMS.register("trinitite_block") { BlockItem(ModBlocks.trinititeBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val nuclearWasteBlock: RegistryObject<Item> = ITEMS.register("nuclear_waste_block") { BlockItem(ModBlocks.nuclearWasteBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val schrabidiumBlock: RegistryObject<Item> = ITEMS.register("schrabidium_block") { BlockItem(ModBlocks.schrabidiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val soliniumBlock: RegistryObject<Item> = ITEMS.register("solinium_block") { BlockItem(ModBlocks.soliniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val schrabidiumFuelBlock: RegistryObject<Item> = ITEMS.register("schrabidium_fuel_block") { BlockItem(ModBlocks.schrabidiumFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val euphemiumBlock: RegistryObject<Item> = ITEMS.register("euphemium_block") { BlockItem(ModBlocks.euphemiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val schrabidiumCluster: RegistryObject<Item> = ITEMS.register("schrabidium_cluster") { BlockItem(ModBlocks.schrabidiumCluster.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val euphemiumEtchedSchrabidiumCluster: RegistryObject<Item> = ITEMS.register("euphemium_etched_schrabidium_cluster") { BlockItem(ModBlocks.euphemiumEtchedSchrabidiumCluster.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val magnetizedTungstenBlock: RegistryObject<Item> = ITEMS.register("magnetized_tungsten_block") { BlockItem(ModBlocks.magnetizedTungstenBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val combineSteelBlock: RegistryObject<Item> = ITEMS.register("combine_steel_block") { BlockItem(ModBlocks.combineSteelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deshReinforcedBlock: RegistryObject<Item> = ITEMS.register("desh_reinforced_block") { BlockItem(ModBlocks.deshReinforcedBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val starmetalBlock: RegistryObject<Item> = ITEMS.register("starmetal_block") { BlockItem(ModBlocks.starmetalBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val australiumBlock: RegistryObject<Item> = ITEMS.register("australium_block") { BlockItem(ModBlocks.australiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val weidaniumBlock: RegistryObject<Item> = ITEMS.register("weidanium_block") { BlockItem(ModBlocks.weidaniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val reiiumBlock: RegistryObject<Item> = ITEMS.register("reiium_block") { BlockItem(ModBlocks.reiiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val unobtainiumBlock: RegistryObject<Item> = ITEMS.register("unobtainium_block") { BlockItem(ModBlocks.unobtainiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val daffergonBlock: RegistryObject<Item> = ITEMS.register("daffergon_block") { BlockItem(ModBlocks.daffergonBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val verticiumBlock: RegistryObject<Item> = ITEMS.register("verticium_block") { BlockItem(ModBlocks.verticiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val titaniumDecoBlock: RegistryObject<Item> = ITEMS.register("titanium_deco_block") { BlockItem(ModBlocks.titaniumDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val redCopperDecoBlock: RegistryObject<Item> = ITEMS.register("red_copper_deco_block") { BlockItem(ModBlocks.redCopperDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val tungstenDecoBlock: RegistryObject<Item> = ITEMS.register("tungsten_deco_block") { BlockItem(ModBlocks.tungstenDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val aluminiumDecoBlock: RegistryObject<Item> = ITEMS.register("aluminium_deco_block") { BlockItem(ModBlocks.aluminiumDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val steelDecoBlock: RegistryObject<Item> = ITEMS.register("steel_deco_block") { BlockItem(ModBlocks.steelDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val leadDecoBlock: RegistryObject<Item> = ITEMS.register("lead_deco_block") { BlockItem(ModBlocks.leadDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val berylliumDecoBlock: RegistryObject<Item> = ITEMS.register("beryllium_deco_block") { BlockItem(ModBlocks.berylliumDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val asbestosRoof: RegistryObject<Item> = ITEMS.register("asbestos_roof") { BlockItem(ModBlocks.asbestosRoof.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val hazmatBlock: RegistryObject<Item> = ITEMS.register("hazmat_block") { BlockItem(ModBlocks.hazmatBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }

    val glowingMushroom: RegistryObject<Item> = ITEMS.register("glowing_mushroom") { BlockItem(ModBlocks.glowingMushroom.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val glowingMushroomBlock: RegistryObject<Item> = ITEMS.register("glowing_mushroom_block") { BlockItem(ModBlocks.glowingMushroomBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val glowingMushroomStem: RegistryObject<Item> = ITEMS.register("glowing_mushroom_stem") { BlockItem(ModBlocks.glowingMushroomStem.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deadGrass: RegistryObject<Item> = ITEMS.register("dead_grass") { BlockItem(ModBlocks.deadGrass.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val glowingMycelium: RegistryObject<Item> = ITEMS.register("glowing_mycelium") { BlockItem(ModBlocks.glowingMycelium.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val trinititeOre: RegistryObject<Item> = ITEMS.register("trinitite_ore") { BlockItem(ModBlocks.trinitite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val redTrinititeOre: RegistryObject<Item> = ITEMS.register("red_trinitite_ore") { BlockItem(ModBlocks.redTrinitite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val charredLog: RegistryObject<Item> = ITEMS.register("charred_log") { object : BlockItem(ModBlocks.charredLog.get(), Properties().tab(CreativeTabs.Blocks)) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 300
    }}
    val charredPlanks: RegistryObject<Item> = ITEMS.register("charred_planks") { object : BlockItem(ModBlocks.charredPlanks.get(), Properties().tab(CreativeTabs.Blocks)) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 300
    }}

    val slakedSellafite: RegistryObject<Item> = ITEMS.register("slaked_sellafite") { BlockItem(ModBlocks.slakedSellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val sellafite: RegistryObject<Item> = ITEMS.register("sellafite") { BlockItem(ModBlocks.sellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val hotSellafite: RegistryObject<Item> = ITEMS.register("hot_sellafite") { BlockItem(ModBlocks.hotSellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val boilingSellafite: RegistryObject<Item> = ITEMS.register("boiling_sellafite") { BlockItem(ModBlocks.boilingSellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val blazingSellafite: RegistryObject<Item> = ITEMS.register("blazing_sellafite") { BlockItem(ModBlocks.blazingSellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val infernalSellafite: RegistryObject<Item> = ITEMS.register("infernal_sellafite") { BlockItem(ModBlocks.infernalSellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val sellafiteCorium: RegistryObject<Item> = ITEMS.register("sellafite_corium") { BlockItem(ModBlocks.sellafiteCorium.get(), Item.Properties().tab(CreativeTabs.Blocks)) }

    val siren: RegistryObject<Item> = ITEMS.register("siren") { BlockItem(ModBlocks.siren.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val safe: RegistryObject<Item> = ITEMS.register("safe") { BlockItem(ModBlocks.safe.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val steamPress: RegistryObject<Item> = ITEMS.register("steam_press") { BlockItem(ModBlocks.steamPressBase.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val blastFurnace: RegistryObject<Item> = ITEMS.register("blast_furnace") { BlockItem(ModBlocks.blastFurnace.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val combustionGenerator: RegistryObject<Item> = ITEMS.register("combustion_generator") { BlockItem(ModBlocks.combustionGenerator.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val electricFurnace: RegistryObject<Item> = ITEMS.register("electric_furnace") { BlockItem(ModBlocks.electricFurnace.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val shredder: RegistryObject<Item> = ITEMS.register("shredder") { BlockItem(ModBlocks.shredder.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val assemblerPlacer: RegistryObject<MultiBlockPlacerItem> = ITEMS.register("assembler") { MultiBlockPlacerItem(ModBlocks.assembler.get(), Assembler::placeMultiBlock, Item.Properties().tab(CreativeTabs.Machines)) }

    val ironAnvil: RegistryObject<Item> = ITEMS.register("iron_anvil") { BlockItem(ModBlocks.ironAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val leadAnvil: RegistryObject<Item> = ITEMS.register("lead_anvil") { BlockItem(ModBlocks.leadAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val steelAnvil: RegistryObject<Item> = ITEMS.register("steel_anvil") { BlockItem(ModBlocks.steelAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val meteoriteAnvil: RegistryObject<Item> = ITEMS.register("meteorite_anvil") { BlockItem(ModBlocks.meteoriteAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val starmetalAnvil: RegistryObject<Item> = ITEMS.register("starmetal_anvil") { BlockItem(ModBlocks.starmetalAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val ferrouraniumAnvil: RegistryObject<Item> = ITEMS.register("ferrouranium_anvil") { BlockItem(ModBlocks.ferrouraniumAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val bismuthAnvil: RegistryObject<Item> = ITEMS.register("bismuth_anvil") { BlockItem(ModBlocks.bismuthAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val schrabidateAnvil: RegistryObject<Item> = ITEMS.register("schrabidate_anvil") { BlockItem(ModBlocks.schrabidateAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val dineutroniumAnvil: RegistryObject<Item> = ITEMS.register("dineutronium_anvil") { BlockItem(ModBlocks.dineutroniumAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val murkyAnvil: RegistryObject<Item> = ITEMS.register("murky_anvil") { BlockItem(ModBlocks.murkyAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }

    val littleBoy: RegistryObject<Item> = ITEMS.register("little_boy") { BlockItem(ModBlocks.littleBoy.get(), Item.Properties().tab(CreativeTabs.Bombs)) }
    val fatMan: RegistryObject<Item> = ITEMS.register("fat_man") { BlockItem(ModBlocks.fatMan.get(), Item.Properties().tab(CreativeTabs.Bombs)) }

    private fun Item.Properties.tab(tab: CreativeTabs): Item.Properties = tab(tab.itemGroup)
}
