package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.ITEMS
import at.martinthedragon.nucleartech.blocks.AssemblerBlock
import at.martinthedragon.nucleartech.blocks.LaunchPadBlock
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
    val uraniumOre = ITEMS.registerK("uranium_ore") { BlockItem(ModBlocks.uraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val scorchedUraniumOre = ITEMS.registerK("scorched_uranium_ore") { BlockItem(ModBlocks.scorchedUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val thoriumOre = ITEMS.registerK("thorium_ore") { BlockItem(ModBlocks.thoriumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val titaniumOre = ITEMS.registerK("titanium_ore") { BlockItem(ModBlocks.titaniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val sulfurOre = ITEMS.registerK("sulfur_ore") { BlockItem(ModBlocks.sulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val niterOre = ITEMS.registerK("niter_ore") { BlockItem(ModBlocks.niterOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val tungstenOre = ITEMS.registerK("tungsten_ore") { BlockItem(ModBlocks.tungstenOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val aluminiumOre = ITEMS.registerK("aluminium_ore") { BlockItem(ModBlocks.aluminiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val fluoriteOre = ITEMS.registerK("fluorite_ore") { BlockItem(ModBlocks.fluoriteOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val berylliumOre = ITEMS.registerK("beryllium_ore") { BlockItem(ModBlocks.berylliumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val leadOre = ITEMS.registerK("lead_ore") { BlockItem(ModBlocks.leadOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val oilDeposit = ITEMS.registerK("oil_deposit") { AutoTooltippedBlockItem(ModBlocks.oilDeposit.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val emptyOilDeposit = ITEMS.registerK("empty_oil_deposit") { BlockItem(ModBlocks.emptyOilDeposit.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val oilSand = ITEMS.registerK("oil_sand") { BlockItem(ModBlocks.oilSand.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val ligniteOre = ITEMS.registerK("lignite_ore") { BlockItem(ModBlocks.ligniteOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val asbestosOre = ITEMS.registerK("asbestos_ore") { BlockItem(ModBlocks.asbestosOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val schrabidiumOre = ITEMS.registerK("schrabidium_ore") { BlockItem(ModBlocks.schrabidiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.RARE)) }
    val australianOre = ITEMS.registerK("australian_ore") { AutoTooltippedBlockItem(ModBlocks.australianOre.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val weidite = ITEMS.registerK("weidite") { AutoTooltippedBlockItem(ModBlocks.weidite.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val reiite = ITEMS.registerK("reiite") { AutoTooltippedBlockItem(ModBlocks.reiite.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val brightblendeOre = ITEMS.registerK("brightblende_ore") { AutoTooltippedBlockItem(ModBlocks.brightblendeOre.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val dellite = ITEMS.registerK("dellite") { AutoTooltippedBlockItem(ModBlocks.dellite.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val dollarGreenMineral = ITEMS.registerK("dollar_green_mineral") { AutoTooltippedBlockItem(ModBlocks.dollarGreenMineral.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val rareEarthOre = ITEMS.registerK("rare_earth_ore") { BlockItem(ModBlocks.rareEarthOre.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.UNCOMMON)) }
    val cobaltOre = ITEMS.registerK("cobalt_ore") { BlockItem(ModBlocks.cobaltOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateUraniumOre = ITEMS.registerK("deepslate_uranium_ore") { BlockItem(ModBlocks.deepslateUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val scorchedDeepslateUraniumOre = ITEMS.registerK("scorched_deepslate_uranium_ore") { BlockItem(ModBlocks.scorchedDeepslateUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateThoriumOre = ITEMS.registerK("deepslate_thorium_ore") { BlockItem(ModBlocks.deepslateThoriumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateTitaniumOre = ITEMS.registerK("deepslate_titanium_ore") { BlockItem(ModBlocks.deepslateTitaniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateSulfurOre = ITEMS.registerK("deepslate_sulfur_ore") { BlockItem(ModBlocks.deepslateSulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateNiterOre = ITEMS.registerK("deepslate_niter_ore") { BlockItem(ModBlocks.deepslateNiterOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateTungstenOre = ITEMS.registerK("deepslate_tungsten_ore") { BlockItem(ModBlocks.deepslateTungstenOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateAluminiumOre = ITEMS.registerK("deepslate_aluminium_ore") { BlockItem(ModBlocks.deepslateAluminiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateFluoriteOre = ITEMS.registerK("deepslate_fluorite_ore") { BlockItem(ModBlocks.deepslateFluoriteOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateBerylliumOre = ITEMS.registerK("deepslate_beryllium_ore") { BlockItem(ModBlocks.deepslateBerylliumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateLeadOre = ITEMS.registerK("deepslate_lead_ore") { BlockItem(ModBlocks.deepslateLeadOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateOilDeposit = ITEMS.registerK("deepslate_oil_deposit") { AutoTooltippedBlockItem(ModBlocks.deepslateOilDeposit.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val emptyDeepslateOilDeposit = ITEMS.registerK("empty_deepslate_oil_deposit") { BlockItem(ModBlocks.emptyDeepslateOilDeposit.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateAsbestosOre = ITEMS.registerK("deepslate_asbestos_ore") { BlockItem(ModBlocks.deepslateAsbestosOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateSchrabidiumOre = ITEMS.registerK("deepslate_schrabidium_ore") { BlockItem(ModBlocks.deepslateSchrabidiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateAustralianOre = ITEMS.registerK("deepslate_australian_ore") { AutoTooltippedBlockItem(ModBlocks.deepslateAustralianOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateRareEarthOre = ITEMS.registerK("deepslate_rare_earth_ore") { BlockItem(ModBlocks.deepslateRareEarthOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deepslateCobaltOre = ITEMS.registerK("deepslate_cobalt_ore") { BlockItem(ModBlocks.deepslateCobaltOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherUraniumOre = ITEMS.registerK("nether_uranium_ore") { BlockItem(ModBlocks.netherUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val scorchedNetherUraniumOre = ITEMS.registerK("scorched_nether_uranium_ore") { BlockItem(ModBlocks.scorchedNetherUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherPlutoniumOre = ITEMS.registerK("nether_plutonium_ore") { BlockItem(ModBlocks.netherPlutoniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherTungstenOre = ITEMS.registerK("nether_tungsten_ore") { BlockItem(ModBlocks.netherTungstenOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherSulfurOre = ITEMS.registerK("nether_sulfur_ore") { BlockItem(ModBlocks.netherSulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherPhosphorusOre = ITEMS.registerK("nether_phosphorus_ore") { BlockItem(ModBlocks.netherPhosphorusOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val netherSchrabidiumOre = ITEMS.registerK("nether_schrabidium_ore") { BlockItem(ModBlocks.netherSchrabidiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks).rarity(Rarity.RARE)) }
    val meteorUraniumOre = ITEMS.registerK("meteor_uranium_ore") { BlockItem(ModBlocks.meteorUraniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorThoriumOre = ITEMS.registerK("meteor_thorium_ore") { BlockItem(ModBlocks.meteorThoriumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorTitaniumOre = ITEMS.registerK("meteor_titanium_ore") { BlockItem(ModBlocks.meteorTitaniumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorSulfurOre = ITEMS.registerK("meteor_sulfur_ore") { BlockItem(ModBlocks.meteorSulfurOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorCopperOre = ITEMS.registerK("meteor_copper_ore") { BlockItem(ModBlocks.meteorCopperOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorTungstenOre = ITEMS.registerK("meteor_tungsten_ore") { BlockItem(ModBlocks.meteorTungstenOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorAluminiumOre = ITEMS.registerK("meteor_aluminium_ore") { BlockItem(ModBlocks.meteorAluminiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorLeadOre = ITEMS.registerK("meteor_lead_ore") { BlockItem(ModBlocks.meteorLeadOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val meteorLithiumOre = ITEMS.registerK("meteor_lithium_ore") { BlockItem(ModBlocks.meteorLithiumOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val starmetalOre = ITEMS.registerK("starmetal_ore") { BlockItem(ModBlocks.starmetalOre.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val trixite = ITEMS.registerK("trixite") { BlockItem(ModBlocks.trixite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }

    val uraniumBlock = ITEMS.registerK("uranium_block") { BlockItem(ModBlocks.uraniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val u233Block = ITEMS.registerK("u233_block") { BlockItem(ModBlocks.u233Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val u235Block = ITEMS.registerK("u235_block") { BlockItem(ModBlocks.u235Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val u238Block = ITEMS.registerK("u238_block") { BlockItem(ModBlocks.u238Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val uraniumFuelBlock = ITEMS.registerK("uranium_fuel_block") { BlockItem(ModBlocks.uraniumFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val neptuniumBlock = ITEMS.registerK("neptunium_block") { BlockItem(ModBlocks.neptuniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val moxFuelBlock = ITEMS.registerK("mox_fuel_block") { BlockItem(ModBlocks.moxFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val plutoniumBlock = ITEMS.registerK("plutonium_block") { BlockItem(ModBlocks.plutoniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val pu238Block = ITEMS.registerK("pu238_block") { BlockItem(ModBlocks.pu238Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val pu239Block = ITEMS.registerK("pu239_block") { BlockItem(ModBlocks.pu239Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val pu240Block = ITEMS.registerK("pu240_block") { BlockItem(ModBlocks.pu240Block.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val plutoniumFuelBlock = ITEMS.registerK("plutonium_fuel_block") { BlockItem(ModBlocks.plutoniumFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val thoriumBlock = ITEMS.registerK("thorium_block") { BlockItem(ModBlocks.thoriumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val thoriumFuelBlock = ITEMS.registerK("thorium_fuel_block") { BlockItem(ModBlocks.thoriumFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val titaniumBlock = ITEMS.registerK("titanium_block") { BlockItem(ModBlocks.titaniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val sulfurBlock = ITEMS.registerK("sulfur_block") { BlockItem(ModBlocks.sulfurBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val niterBlock = ITEMS.registerK("niter_block") { BlockItem(ModBlocks.niterBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val redCopperBlock = ITEMS.registerK("red_copper_block") { BlockItem(ModBlocks.redCopperBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val advancedAlloyBlock = ITEMS.registerK("advanced_alloy_block") { BlockItem(ModBlocks.advancedAlloyBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val tungstenBlock = ITEMS.registerK("tungsten_block") { BlockItem(ModBlocks.tungstenBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val aluminiumBlock = ITEMS.registerK("aluminium_block") { BlockItem(ModBlocks.aluminiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val fluoriteBlock = ITEMS.registerK("fluorite_block") { BlockItem(ModBlocks.fluoriteBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val berylliumBlock = ITEMS.registerK("beryllium_block") { BlockItem(ModBlocks.berylliumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val cobaltBlock = ITEMS.registerK("cobalt_block") { BlockItem(ModBlocks.cobaltBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val steelBlock = ITEMS.registerK("steel_block") { BlockItem(ModBlocks.steelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val leadBlock = ITEMS.registerK("lead_block") { BlockItem(ModBlocks.leadBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val lithiumBlock = ITEMS.registerK("lithium_block") { BlockItem(ModBlocks.lithiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val whitePhosphorusBlock = ITEMS.registerK("white_phosphorus_block") { BlockItem(ModBlocks.whitePhosphorusBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val redPhosphorusBlock = ITEMS.registerK("red_phosphorus_block") { BlockItem(ModBlocks.redPhosphorusBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val yellowcakeBlock = ITEMS.registerK("yellowcake_block") { BlockItem(ModBlocks.yellowcakeBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val scrapBlock: RegistryObject<BlockItem> = ITEMS.registerK("scrap_block") { object : BlockItem(ModBlocks.scrapBlock.get(), Properties().tab(CreativeTabs.Blocks)) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 4000
    }}
    val electricalScrapBlock = ITEMS.registerK("electrical_scrap_block") { BlockItem(ModBlocks.electricalScrapBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val insulatorRoll = ITEMS.registerK("insulator_roll") { BlockItem(ModBlocks.insulatorRoll.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val fiberglassRoll = ITEMS.registerK("fiberglass_roll") { BlockItem(ModBlocks.fiberglassRoll.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val asbestosBlock = ITEMS.registerK("asbestos_block") { BlockItem(ModBlocks.asbestosBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val trinititeBlock = ITEMS.registerK("trinitite_block") { BlockItem(ModBlocks.trinititeBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val nuclearWasteBlock = ITEMS.registerK("nuclear_waste_block") { BlockItem(ModBlocks.nuclearWasteBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val schrabidiumBlock = ITEMS.registerK("schrabidium_block") { BlockItem(ModBlocks.schrabidiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val soliniumBlock = ITEMS.registerK("solinium_block") { BlockItem(ModBlocks.soliniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val schrabidiumFuelBlock = ITEMS.registerK("schrabidium_fuel_block") { BlockItem(ModBlocks.schrabidiumFuelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val euphemiumBlock = ITEMS.registerK("euphemium_block") { BlockItem(ModBlocks.euphemiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val schrabidiumCluster = ITEMS.registerK("schrabidium_cluster") { BlockItem(ModBlocks.schrabidiumCluster.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val euphemiumEtchedSchrabidiumCluster = ITEMS.registerK("euphemium_etched_schrabidium_cluster") { BlockItem(ModBlocks.euphemiumEtchedSchrabidiumCluster.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val magnetizedTungstenBlock = ITEMS.registerK("magnetized_tungsten_block") { BlockItem(ModBlocks.magnetizedTungstenBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val combineSteelBlock = ITEMS.registerK("combine_steel_block") { BlockItem(ModBlocks.combineSteelBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deshReinforcedBlock = ITEMS.registerK("desh_reinforced_block") { BlockItem(ModBlocks.deshReinforcedBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val starmetalBlock = ITEMS.registerK("starmetal_block") { BlockItem(ModBlocks.starmetalBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val australiumBlock = ITEMS.registerK("australium_block") { BlockItem(ModBlocks.australiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val weidaniumBlock = ITEMS.registerK("weidanium_block") { BlockItem(ModBlocks.weidaniumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val reiiumBlock = ITEMS.registerK("reiium_block") { BlockItem(ModBlocks.reiiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val unobtainiumBlock = ITEMS.registerK("unobtainium_block") { BlockItem(ModBlocks.unobtainiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val daffergonBlock = ITEMS.registerK("daffergon_block") { BlockItem(ModBlocks.daffergonBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val verticiumBlock = ITEMS.registerK("verticium_block") { BlockItem(ModBlocks.verticiumBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val titaniumDecoBlock = ITEMS.registerK("titanium_deco_block") { BlockItem(ModBlocks.titaniumDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val redCopperDecoBlock = ITEMS.registerK("red_copper_deco_block") { BlockItem(ModBlocks.redCopperDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val tungstenDecoBlock = ITEMS.registerK("tungsten_deco_block") { BlockItem(ModBlocks.tungstenDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val aluminiumDecoBlock = ITEMS.registerK("aluminium_deco_block") { BlockItem(ModBlocks.aluminiumDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val steelDecoBlock = ITEMS.registerK("steel_deco_block") { BlockItem(ModBlocks.steelDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val leadDecoBlock = ITEMS.registerK("lead_deco_block") { BlockItem(ModBlocks.leadDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val berylliumDecoBlock = ITEMS.registerK("beryllium_deco_block") { BlockItem(ModBlocks.berylliumDecoBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val asbestosRoof = ITEMS.registerK("asbestos_roof") { BlockItem(ModBlocks.asbestosRoof.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val hazmatBlock = ITEMS.registerK("hazmat_block") { BlockItem(ModBlocks.hazmatBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }

    val glowingMushroom = ITEMS.registerK("glowing_mushroom") { BlockItem(ModBlocks.glowingMushroom.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val glowingMushroomBlock = ITEMS.registerK("glowing_mushroom_block") { BlockItem(ModBlocks.glowingMushroomBlock.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val glowingMushroomStem = ITEMS.registerK("glowing_mushroom_stem") { BlockItem(ModBlocks.glowingMushroomStem.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val deadGrass = ITEMS.registerK("dead_grass") { BlockItem(ModBlocks.deadGrass.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val glowingMycelium = ITEMS.registerK("glowing_mycelium") { BlockItem(ModBlocks.glowingMycelium.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val trinititeOre = ITEMS.registerK("trinitite_ore") { BlockItem(ModBlocks.trinitite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val redTrinititeOre = ITEMS.registerK("red_trinitite_ore") { BlockItem(ModBlocks.redTrinitite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val charredLog: RegistryObject<BlockItem> = ITEMS.registerK("charred_log") { object : BlockItem(ModBlocks.charredLog.get(), Properties().tab(CreativeTabs.Blocks)) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 300
    }}
    val charredPlanks: RegistryObject<BlockItem> = ITEMS.registerK("charred_planks") { object : BlockItem(ModBlocks.charredPlanks.get(), Properties().tab(CreativeTabs.Blocks)) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 300
    }}

    val slakedSellafite = ITEMS.registerK("slaked_sellafite") { BlockItem(ModBlocks.slakedSellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val sellafite = ITEMS.registerK("sellafite") { BlockItem(ModBlocks.sellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val hotSellafite = ITEMS.registerK("hot_sellafite") { BlockItem(ModBlocks.hotSellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val boilingSellafite = ITEMS.registerK("boiling_sellafite") { BlockItem(ModBlocks.boilingSellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val blazingSellafite = ITEMS.registerK("blazing_sellafite") { BlockItem(ModBlocks.blazingSellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val infernalSellafite = ITEMS.registerK("infernal_sellafite") { BlockItem(ModBlocks.infernalSellafite.get(), Item.Properties().tab(CreativeTabs.Blocks)) }
    val sellafiteCorium = ITEMS.registerK("sellafite_corium") { BlockItem(ModBlocks.sellafiteCorium.get(), Item.Properties().tab(CreativeTabs.Blocks)) }

    val siren = ITEMS.registerK("siren") { BlockItem(ModBlocks.siren.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val safe = ITEMS.registerK("safe") { BlockItem(ModBlocks.safe.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val steamPress = ITEMS.registerK("steam_press") { BlockItem(ModBlocks.steamPressBase.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val blastFurnace = ITEMS.registerK("blast_furnace") { BlockItem(ModBlocks.blastFurnace.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val combustionGenerator = ITEMS.registerK("combustion_generator") { BlockItem(ModBlocks.combustionGenerator.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val electricFurnace = ITEMS.registerK("electric_furnace") { BlockItem(ModBlocks.electricFurnace.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val shredder = ITEMS.registerK("shredder") { BlockItem(ModBlocks.shredder.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val assemblerPlacer = ITEMS.registerK("assembler") { MultiBlockPlacerItem(ModBlocks.assembler.get(), AssemblerBlock::placeMultiBlock, Item.Properties().tab(CreativeTabs.Machines)) }

    val ironAnvil = ITEMS.registerK("iron_anvil") { BlockItem(ModBlocks.ironAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val leadAnvil = ITEMS.registerK("lead_anvil") { BlockItem(ModBlocks.leadAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val steelAnvil = ITEMS.registerK("steel_anvil") { BlockItem(ModBlocks.steelAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val meteoriteAnvil = ITEMS.registerK("meteorite_anvil") { BlockItem(ModBlocks.meteoriteAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val starmetalAnvil = ITEMS.registerK("starmetal_anvil") { BlockItem(ModBlocks.starmetalAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val ferrouraniumAnvil = ITEMS.registerK("ferrouranium_anvil") { BlockItem(ModBlocks.ferrouraniumAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val bismuthAnvil = ITEMS.registerK("bismuth_anvil") { BlockItem(ModBlocks.bismuthAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val schrabidateAnvil = ITEMS.registerK("schrabidate_anvil") { BlockItem(ModBlocks.schrabidateAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val dineutroniumAnvil = ITEMS.registerK("dineutronium_anvil") { BlockItem(ModBlocks.dineutroniumAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }
    val murkyAnvil = ITEMS.registerK("murky_anvil") { BlockItem(ModBlocks.murkyAnvil.get(), Item.Properties().tab(CreativeTabs.Machines)) }

    val littleBoy = ITEMS.registerK("little_boy") { BlockItem(ModBlocks.littleBoy.get(), Item.Properties().tab(CreativeTabs.Bombs)) }
    val fatMan = ITEMS.registerK("fat_man") { BlockItem(ModBlocks.fatMan.get(), Item.Properties().tab(CreativeTabs.Bombs)) }

    val launchPadPlacer = ITEMS.registerK("launch_pad") { MultiBlockPlacerItem(ModBlocks.launchPad.get(), LaunchPadBlock::placeMultiBlock, Item.Properties().tab(CreativeTabs.Rocketry)) }

    private fun Item.Properties.tab(tab: CreativeTabs): Item.Properties = tab(tab.itemGroup)
}
