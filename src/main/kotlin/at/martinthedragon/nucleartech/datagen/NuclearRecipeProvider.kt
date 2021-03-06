package at.martinthedragon.nucleartech.datagen

import at.martinthedragon.nucleartech.ModBlockItems
import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.datagen.recipes.BlastingRecipeBuilder
import at.martinthedragon.nucleartech.datagen.recipes.ExtendedCookingRecipeBuilder
import at.martinthedragon.nucleartech.datagen.recipes.PressRecipeBuilder
import at.martinthedragon.nucleartech.recipes.PressRecipe
import net.minecraft.data.*
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.item.crafting.Ingredient
import net.minecraft.tags.ITag
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.Tags
import java.util.function.Consumer

@Suppress("SameParameterValue")
class NuclearRecipeProvider(generator: DataGenerator) : RecipeProvider(generator) {
    override fun getName(): String = "Nuclear Tech Mod Recipes"

    override fun buildShapelessRecipes(consumer: Consumer<IFinishedRecipe>) {
        ShapelessRecipeBuilder.shapeless(ModItems.schrabidiumFuelIngot.get()).requires(ModItems.schrabidiumNugget.get(), 3).requires(NuclearTags.Items.NUGGETS_NEPTUNIUM, 3).requires(NuclearTags.Items.NUGGETS_BERYLLIUM, 3).group("schrabidium_fuel_ingot").unlockedBy("has_schrabidium_nugget", has(ModItems.schrabidiumNugget.get())).save(consumer, ResourceLocation(NuclearTech.MODID, "schrabidium_fuel_ingot_from_isotope_nuggets"))
        ShapelessRecipeBuilder.shapeless(ModItems.highEnrichedSchrabidiumFuelIngot.get()).requires(ModItems.schrabidiumNugget.get(), 5).requires(NuclearTags.Items.NUGGETS_NEPTUNIUM, 2).requires(NuclearTags.Items.NUGGETS_BERYLLIUM, 2).group("high_enriched_schrabidium_fuel_ingot").unlockedBy("has_schrabidium_nugget", has(ModItems.schrabidiumNugget.get())).save(consumer, ResourceLocation(NuclearTech.MODID, "high_enriched_schrabidium_fuel_ingot_from_isotope_nuggets"))
        ShapelessRecipeBuilder.shapeless(ModItems.lowEnrichedSchrabidiumFuelIngot.get()).requires(ModItems.schrabidiumNugget.get()).requires(NuclearTags.Items.NUGGETS_NEPTUNIUM, 4).requires(NuclearTags.Items.NUGGETS_BERYLLIUM, 4).group("low_enriched_schrabidium_fuel_ingot").unlockedBy("has_schrabidium_nugget", has(ModItems.schrabidiumNugget.get())).save(consumer, ResourceLocation(NuclearTech.MODID, "low_enriched_schrabidium_fuel_ingot_from_isotope_nuggets"))
        ShapelessRecipeBuilder.shapeless(ModItems.moxFuelIngot.get()).requires(ModItems.u235Nugget.get(), 3).requires(ModItems.pu238Nugget.get()).requires(ModItems.pu239Nugget.get(), 3).requires(ModItems.u238Nugget.get(), 2).group("mox_fuel_ingot").unlockedBy("has_plutonium_nugget", has(NuclearTags.Items.NUGGETS_PLUTONIUM)).save(consumer, ResourceLocation(NuclearTech.MODID, "mox_fuel_ingot_from_isotope_nuggets"))
        ShapelessRecipeBuilder.shapeless(ModItems.plutoniumFuelIngot.get()).requires(ModItems.pu238Nugget.get()).requires(ModItems.pu239Nugget.get(), 5).requires(ModItems.pu240Nugget.get(), 3).group("plutonium_fuel_ingot").unlockedBy("has_plutonium_nugget", has(NuclearTags.Items.NUGGETS_PLUTONIUM)).save(consumer, ResourceLocation(NuclearTech.MODID, "plutonium_fuel_ingot_from_isotope_nuggets"))
        ShapelessRecipeBuilder.shapeless(ModItems.thoriumFuelIngot.get()).requires(ModItems.u233Nugget.get(), 3).requires(NuclearTags.Items.NUGGETS_THORIUM, 6).group("thorium_fuel_ingot").unlockedBy("has_thorium_nugget", has(NuclearTags.Items.NUGGETS_THORIUM)).save(consumer, ResourceLocation(NuclearTech.MODID, "thorium_fuel_ingot_from_isotope_nuggets"))
        ShapelessRecipeBuilder.shapeless(ModItems.uraniumFuelIngot.get()).requires(ModItems.u238Nugget.get(), 6).requires(ModItems.u233Nugget.get(), 3).group("uranium_fuel_ingot").unlockedBy("has_u238_nugget", has(ModItems.u238Nugget.get())).save(consumer, ResourceLocation(NuclearTech.MODID, "uranium_fuel_ingot_from_isotope_nuggets_u233"))
        ShapelessRecipeBuilder.shapeless(ModItems.uraniumFuelIngot.get()).requires(ModItems.u238Nugget.get(), 6).requires(ModItems.u235Nugget.get(), 3).group("uranium_fuel_ingot").unlockedBy("has_u238_nugget", has(ModItems.u238Nugget.get())).save(consumer, ResourceLocation(NuclearTech.MODID, "uranium_fuel_ingot_from_isotope_nuggets_u235"))

        // blocks to ingots
        ingotFromBlock(ModItems.uraniumIngot.get(), ModBlockItems.uraniumBlock.get(), consumer)
        ingotFromBlock(ModItems.u233Ingot.get(), ModBlockItems.u233Block.get(), consumer)
        ingotFromBlock(ModItems.u235Ingot.get(), ModBlockItems.u235Block.get(), consumer)
        ingotFromBlock(ModItems.u238Ingot.get(), ModBlockItems.u238Block.get(), consumer)
        ingotFromBlock(ModItems.uraniumFuelIngot.get(), ModBlockItems.uraniumFuelBlock.get(), consumer)
        ingotFromBlock(ModItems.neptuniumIngot.get(), NuclearTags.Items.STORAGE_BLOCKS_NEPTUNIUM, "neptunium_block", consumer)
        ingotFromBlock(ModItems.moxFuelIngot.get(), NuclearTags.Items.STORAGE_BLOCKS_MOX, "mox_fuel_block", consumer)
        ingotFromBlock(ModItems.plutoniumIngot.get(), ModBlockItems.plutoniumBlock.get(), consumer)
        ingotFromBlock(ModItems.pu238Ingot.get(), ModBlockItems.pu238Block.get(), consumer)
        ingotFromBlock(ModItems.pu239Ingot.get(), ModBlockItems.pu239Block.get(), consumer)
        ingotFromBlock(ModItems.pu240Ingot.get(), ModBlockItems.pu240Block.get(), consumer)
        ingotFromBlock(ModItems.plutoniumFuelIngot.get(), ModBlockItems.plutoniumFuelBlock.get(), consumer)
        ingotFromBlock(ModItems.th232Ingot.get(), NuclearTags.Items.STORAGE_BLOCKS_THORIUM, "thorium_block", consumer)
        ingotFromBlock(ModItems.thoriumFuelIngot.get(), ModBlockItems.thoriumFuelBlock.get(), consumer)
        ingotFromBlock(ModItems.titaniumIngot.get(), NuclearTags.Items.STORAGE_BLOCKS_TITANIUM, "titanium_block", consumer)
        ingotFromBlock(ModItems.sulfur.get(), NuclearTags.Items.STORAGE_BLOCKS_SULFUR, "sulfur_block", consumer)
        ingotFromBlock(ModItems.niter.get(), NuclearTags.Items.STORAGE_BLOCKS_NITER, "niter_block", consumer)
        ingotFromBlock(ModItems.copperIngot.get(), NuclearTags.Items.STORAGE_BLOCKS_COPPER, "copper_block", consumer)
        ingotFromBlock(ModItems.redCopperIngot.get(), ModBlockItems.redCopperBlock.get(), consumer)
        ingotFromBlock(ModItems.advancedAlloyIngot.get(), ModBlockItems.advancedAlloyBlock.get(), consumer)
        ingotFromBlock(ModItems.tungstenIngot.get(), NuclearTags.Items.STORAGE_BLOCKS_TUNGSTEN, "tungsten_block", consumer)
        ingotFromBlock(ModItems.aluminiumIngot.get(), NuclearTags.Items.STORAGE_BLOCKS_ALUMINIUM, "aluminium_block", consumer)
        ingotFromBlock(ModItems.fluorite.get(), NuclearTags.Items.STORAGE_BLOCKS_FLUORITE, "fluorite_block", consumer)
        ingotFromBlock(ModItems.berylliumIngot.get(), NuclearTags.Items.STORAGE_BLOCKS_BERYLLIUM, "beryllium_block", consumer)
        ingotFromBlock(ModItems.cobaltIngot.get(), NuclearTags.Items.STORAGE_BLOCKS_COBALT, "cobalt_block", consumer)
        ingotFromBlock(ModItems.steelIngot.get(), NuclearTags.Items.STORAGE_BLOCKS_STEEL, "steel_block", consumer)
        ingotFromBlock(ModItems.leadIngot.get(), NuclearTags.Items.STORAGE_BLOCKS_LEAD, "lead_block", consumer)
        ingotFromBlock(ModItems.lithiumCube.get(), NuclearTags.Items.STORAGE_BLOCKS_LITHIUM, "lithium_block", consumer)
        ingotFromBlock(ModItems.whitePhosphorusIngot.get(), ModBlockItems.whitePhosphorusBlock.get(), consumer)
        ingotFromBlock(ModItems.redPhosphorus.get(), ModBlockItems.redPhosphorusBlock.get(), consumer)
        ingotFromBlock(ModItems.yellowcake.get(), NuclearTags.Items.STORAGE_BLOCKS_YELLOWCAKE, "yellowcake_block", consumer)
        ingotFromBlock(ModItems.insulator.get(), ModBlockItems.insulatorRoll.get(), consumer)
        ingotFromBlock(ModItems.fiberglassSheet.get(), ModBlockItems.fiberglassRoll.get(), consumer)
        ingotFromBlock(ModItems.asbestosSheet.get(), NuclearTags.Items.STORAGE_BLOCK_ASBESTOS, "asbestos_block", consumer)
        ingotFromBlock(ModItems.trinitite.get(), ModBlockItems.trinititeBlock.get(), consumer)
        ingotFromBlock(ModItems.nuclearWaste.get(), NuclearTags.Items.STORAGE_BLOCK_NUCLEAR_WASTE, "nuclear_waste_block", consumer)
        ingotFromBlock(ModItems.schrabidiumIngot.get(), ModBlockItems.schrabidiumBlock.get(), consumer)
        ingotFromBlock(ModItems.soliniumIngot.get(), ModBlockItems.soliniumBlock.get(), consumer)
        ingotFromBlock(ModItems.schrabidiumFuelIngot.get(), ModBlockItems.schrabidiumFuelBlock.get(), consumer)
        ingotFromBlock(ModItems.euphemiumIngot.get(), ModBlockItems.euphemiumBlock.get(), consumer)
        ingotFromBlock(ModItems.magnetizedTungstenIngot.get(), ModBlockItems.magnetizedTungstenBlock.get(), consumer)
        ingotFromBlock(ModItems.combineSteelIngot.get(), ModBlockItems.combineSteelBlock.get(), consumer)
        ingotFromBlock(ModItems.deshIngot.get(), ModBlockItems.deshReinforcedBlock.get(), consumer)
        ingotFromBlock(ModItems.starmetalIngot.get(), ModBlockItems.starmetalBlock.get(), consumer)
        ingotFromBlock(ModItems.australiumIngot.get(), ModBlockItems.australiumBlock.get(), consumer)
        ingotFromBlock(ModItems.weidaniumIngot.get(), ModBlockItems.weidaniumBlock.get(), consumer)
        ingotFromBlock(ModItems.reiiumIngot.get(), ModBlockItems.reiiumBlock.get(), consumer)
        ingotFromBlock(ModItems.unobtainiumIngot.get(), ModBlockItems.unobtainiumBlock.get(), consumer)
        ingotFromBlock(ModItems.daffergonIngot.get(), ModBlockItems.daffergonBlock.get(), consumer)
        ingotFromBlock(ModItems.verticiumIngot.get(), ModBlockItems.verticiumBlock.get(), consumer)

        //nuggets to ingots
        ingotFromNuggets(ModItems.uraniumIngot.get(), ModItems.uraniumNugget.get(), consumer)
        ingotFromNuggets(ModItems.berylliumIngot.get(), NuclearTags.Items.NUGGETS_BERYLLIUM, "beryllium_nugget", consumer)
        ingotFromNuggets(ModItems.schrabidiumIngot.get(), ModItems.schrabidiumFuelNugget.get(), consumer)
        ingotFromNuggets(ModItems.schrabidiumFuelIngot.get(), ModItems.schrabidiumFuelNugget.get(), consumer)
        ingotFromNuggets(ModItems.highEnrichedSchrabidiumFuelIngot.get(), ModItems.highEnrichedSchrabidiumFuelNugget.get(), consumer)
        ingotFromNuggets(ModItems.lowEnrichedSchrabidiumFuelIngot.get(), ModItems.lowEnrichedSchrabidiumFuelNugget.get(), consumer)
        ingotFromNuggets(ModItems.soliniumIngot.get(), ModItems.soliniumNugget.get(), consumer)
        ingotFromNuggets(ModItems.leadIngot.get(), NuclearTags.Items.NUGGETS_LEAD, "lead_nugget", consumer)
        ingotFromNuggets(ModItems.moxFuelIngot.get(), ModItems.moxFuelNugget.get(), consumer)
        ingotFromNuggets(ModItems.neptuniumIngot.get(), NuclearTags.Items.NUGGETS_NEPTUNIUM, "neptunium_nugget", consumer)
        ingotFromNuggets(ModItems.plutoniumIngot.get(), ModItems.plutoniumNugget.get(), consumer)
        ingotFromNuggets(ModItems.plutoniumFuelIngot.get(), ModItems.plutoniumFuelNugget.get(), consumer)
        ingotFromNuggets(ModItems.poloniumIngot.get(), ModItems.poloniumNugget.get(), consumer)
        ingotFromNuggets(ModItems.pu238Ingot.get(), ModItems.pu238Nugget.get(), consumer)
        ingotFromNuggets(ModItems.pu239Ingot.get(), ModItems.pu239Nugget.get(), consumer)
        ingotFromNuggets(ModItems.pu240Ingot.get(), ModItems.pu240Nugget.get(), consumer)
        ingotFromNuggets(ModItems.th232Ingot.get(), NuclearTags.Items.NUGGETS_THORIUM, "thorium_nugget", consumer)
        ingotFromNuggets(ModItems.thoriumFuelIngot.get(), ModItems.thoriumFuelNugget.get(), consumer)
        ingotFromNuggets(ModItems.u233Ingot.get(), ModItems.u233Nugget.get(), consumer)
        ingotFromNuggets(ModItems.u235Ingot.get(), ModItems.u235Nugget.get(), consumer)
        ingotFromNuggets(ModItems.u238Ingot.get(), ModItems.u238Nugget.get(), consumer)
        ingotFromNuggets(ModItems.uraniumFuelIngot.get(), ModItems.uraniumFuelNugget.get(), consumer)

        // ores to ingots
        ingotFromOre(NuclearTags.Items.ORES_URANIUM, ModItems.uraniumIngot.get(), 1F, "uranium_ore", consumer)
        ingotFromOre(NuclearTags.Items.ORES_THORIUM, ModItems.th232Ingot.get(), 2F, "thorium_ore", consumer)
        ingotFromOre(NuclearTags.Items.ORES_TITANIUM, ModItems.titaniumIngot.get(), .8F, "titanium_ore", consumer)
        ingotFromOre(NuclearTags.Items.ORES_SULFUR, ModItems.sulfur.get(), .2F, "sulfur_ore", consumer)
        ingotFromOre(NuclearTags.Items.ORES_NITER, ModItems.niter.get(), .2F, "niter_ore", consumer)
        ingotFromOre(NuclearTags.Items.ORES_COPPER, ModItems.copperIngot.get(), .5F, "copper_ore", consumer)
        ingotFromOre(NuclearTags.Items.ORES_TUNGSTEN, ModItems.tungstenIngot.get(), .75F, "tungsten_ore", consumer)
        ingotFromOre(NuclearTags.Items.ORES_ALUMINIUM, ModItems.aluminiumIngot.get(), .6F, "aluminium_ore", consumer)
        ingotFromOre(NuclearTags.Items.ORES_FLUORITE, ModItems.fluorite.get(), .2F, "fluorite_ore", consumer)
        ingotFromOre(NuclearTags.Items.ORES_BERYLLIUM, ModItems.berylliumIngot.get(), .75F, "beryllium_ore", consumer)
        ingotFromOre(NuclearTags.Items.ORES_LEAD, ModItems.leadIngot.get(), .6F, "lead_ore", consumer)
        ingotFromOre(NuclearTags.Items.ORES_LIGNITE, ModItems.lignite.get(), .05F, "lignite_ore", consumer)
        ingotFromOre(ModBlockItems.schrabidiumOre.get(), ModItems.schrabidiumIngot.get(), 50F, consumer)
        ingotFromOre(ModBlockItems.australianOre.get(), ModItems.australiumIngot.get(), 2.5F, consumer)
        ingotFromOre(ModBlockItems.weidite.get(), ModItems.weidaniumIngot.get(), 2.5F, consumer)
        ingotFromOre(ModBlockItems.reiite.get(), ModItems.reiiumIngot.get(), 2.5F, consumer)
        ingotFromOre(ModBlockItems.brightblendeOre.get(), ModItems.unobtainiumIngot.get(), 2.5F, consumer)
        ingotFromOre(ModBlockItems.dellite.get(), ModItems.daffergonIngot.get(), 2.5F, consumer)
        ingotFromOre(ModBlockItems.dollarGreenMineral.get(), ModItems.verticiumIngot.get(), 2.5F, consumer)
        ingotFromOre(ModBlockItems.rareEarthOre.get(), ModItems.deshMix.get(), 3F, consumer)
        ingotFromOre(ModBlockItems.netherUraniumOre.get(), ModItems.uraniumIngot.get(), 2F, consumer)
        ingotFromOre(ModBlockItems.netherPlutoniumOre.get(), ModItems.plutoniumIngot.get(), 3F, consumer)
        ingotFromOre(ModBlockItems.netherTungstenOre.get(), ModItems.tungstenIngot.get(), 1.5F, consumer)
        ingotFromOre(ModBlockItems.netherSulfurOre.get(), ModItems.sulfur.get(), .4F, consumer)
        ingotFromOre(ModBlockItems.netherPhosphorusOre.get(), ModItems.redPhosphorus.get(), 1F, consumer)
        ingotFromOre(ModBlockItems.netherSchrabidiumOre.get(), ModItems.schrabidiumIngot.get(), 100F, consumer)
        ingotFromMeteorOre(ModBlockItems.meteorUraniumOre.get(), ModItems.uraniumIngot.get(), 3F, consumer)
        ingotFromMeteorOre(ModBlockItems.meteorThoriumOre.get(), ModItems.th232Ingot.get(), 6f, consumer)
        ingotFromMeteorOre(ModBlockItems.meteorTitaniumOre.get(), ModItems.titaniumIngot.get(), 2.4F, consumer)
        ingotFromMeteorOre(ModBlockItems.meteorSulfurOre.get(), ModItems.sulfur.get(), 1F, consumer)
        ingotFromMeteorOre(ModBlockItems.meteorCopperOre.get(), ModItems.copperIngot.get(), 1.5F, consumer)
        ingotFromMeteorOre(ModBlockItems.meteorTungstenOre.get(), ModItems.tungstenIngot.get(), 2.25F, consumer)
        ingotFromMeteorOre(ModBlockItems.meteorAluminiumOre.get(), ModItems.aluminiumIngot.get(), 1.8F, consumer)
        ingotFromMeteorOre(ModBlockItems.meteorLeadOre.get(), ModItems.leadIngot.get(), 1.8F, consumer)
        ingotFromOre(ModBlockItems.meteorLithiumOre.get(), ModItems.lithiumCube.get(), 5F, consumer)
        ingotFromOre(ModBlockItems.starmetalOre.get(), ModItems.starmetalIngot.get(), 10F, consumer)

        // powder to ingots
        ingotFromPowder(NuclearTags.Items.DUSTS_URANIUM, ModItems.uraniumIngot.get(), "uranium_powder", consumer)
        ingotFromPowder(ModItems.advancedAlloyPowder.get(), ModItems.advancedAlloyIngot.get(), consumer)
        ingotFromPowder(NuclearTags.Items.DUSTS_ALUMINIUM, ModItems.aluminiumIngot.get(), "aluminium_powder", consumer)
        ingotFromPowder(NuclearTags.Items.DUSTS_BERYLLIUM, ModItems.berylliumIngot.get(), "beryllium_powder", consumer)
        ingotFromPowder(ModItems.combineSteelPowder.get(), ModItems.combineSteelIngot.get(), consumer)
        ingotFromPowder(NuclearTags.Items.DUSTS_COPPER, ModItems.copperIngot.get(), "copper_powder", consumer)
        ingotFromPowder(ModItems.highSpeedSteelPowder.get(), ModItems.highSpeedSteelIngot.get(), consumer)
        ingotFromPowder(NuclearTags.Items.DUSTS_LEAD, ModItems.leadIngot.get(), "lead_powder", consumer)
        ingotFromPowder(ModItems.magnetizedTungstenPowder.get(), ModItems.magnetizedTungstenIngot.get(), consumer)
        ingotFromPowder(NuclearTags.Items.DUSTS_NEPTUNIUM, ModItems.neptuniumIngot.get(), "neptunium_powder", consumer)
        ingotFromPowder(NuclearTags.Items.DUSTS_PLUTONIUM, ModItems.plutoniumIngot.get(), "plutonium_powder", consumer)
        ingotFromPowder(NuclearTags.Items.DUSTS_POLONIUM, ModItems.poloniumIngot.get(), "polonium_powder", consumer)
        ingotFromPowder(NuclearTags.Items.DUSTS_POLYMER, ModItems.polymerIngot.get(), "polymer_powder", consumer)
        ingotFromPowder(ModItems.redCopperPowder.get(), ModItems.redCopperIngot.get(), consumer)
        ingotFromPowder(ModItems.schrabidiumPowder.get(), ModItems.schrabidiumIngot.get(), consumer)
        ingotFromPowder(NuclearTags.Items.DUSTS_STEEL, ModItems.steelIngot.get(), "steel_powder", consumer)
        ingotFromPowder(NuclearTags.Items.DUSTS_THORIUM, ModItems.th232Ingot.get(), "thorium_powder", consumer)
        ingotFromPowder(NuclearTags.Items.DUSTS_TITANIUM, ModItems.titaniumIngot.get(), "titanium_powder", consumer)
        ingotFromPowder(NuclearTags.Items.DUSTS_TUNGSTEN, ModItems.tungstenIngot.get(), "tungsten_powder", consumer)

        // crystals to ingots
        ingotFromCrystals(ModItems.ironCrystals.get(), Items.IRON_INGOT, .7F, consumer)
        ingotFromCrystals(ModItems.goldCrystals.get(), Items.GOLD_INGOT, 1F, consumer)
        ingotFromCrystals(ModItems.redstoneCrystals.get(), Items.REDSTONE, .7F, consumer, 6)
        ingotFromCrystals(ModItems.diamondCrystals.get(), Items.DIAMOND, 1F, consumer)
        ingotFromCrystals(ModItems.uraniumCrystals.get(), ModItems.uraniumIngot.get(), 1F, consumer)
        ingotFromCrystals(ModItems.thoriumCrystals.get(), ModItems.th232Ingot.get(), 2F, consumer)
        ingotFromCrystals(ModItems.plutoniumCrystals.get(), ModItems.plutoniumIngot.get(), 1.5F, consumer)
        ingotFromCrystals(ModItems.titaniumCrystals.get(), ModItems.titaniumIngot.get(), .8F, consumer)
        ingotFromCrystals(ModItems.sulfurCrystals.get(), ModItems.sulfur.get(), .2F, consumer, 6)
        ingotFromCrystals(ModItems.niterCrystals.get(), ModItems.niter.get(), .2F, consumer, 6)
        ingotFromCrystals(ModItems.copperCrystals.get(), ModItems.copperIngot.get(), .5F, consumer)
        ingotFromCrystals(ModItems.tungstenCrystals.get(), ModItems.tungstenIngot.get(), .75F, consumer)
        ingotFromCrystals(ModItems.aluminiumCrystals.get(), ModItems.aluminiumIngot.get(), .6F, consumer)
        ingotFromCrystals(ModItems.fluoriteCrystals.get(), ModItems.fluorite.get(), .2F, consumer, 6)
        ingotFromCrystals(ModItems.berylliumCrystals.get(), ModItems.berylliumIngot.get(), .75F, consumer)
        ingotFromCrystals(ModItems.leadCrystals.get(), ModItems.leadIngot.get(), .6F, consumer)
        ingotFromCrystals(ModItems.schraraniumCrystals.get(), ModItems.schrabidiumNugget.get(), 5F, consumer)
        ingotFromCrystals(ModItems.schrabidiumCrystals.get(), ModItems.schrabidiumIngot.get(), 50F, consumer)
        ingotFromCrystals(ModItems.rareEarthCrystals.get(), ModItems.deshMix.get(), 3F, consumer)
        ingotFromCrystals(ModItems.redPhosphorusCrystals.get(), ModItems.redPhosphorus.get(), 1F, consumer, 6)
        ingotFromCrystals(ModItems.lithiumCrystals.get(), ModItems.lithiumCube.get(), 2F, consumer)
        ingotFromCrystals(ModItems.starmetalCrystals.get(), ModItems.starmetalIngot.get(), 10F, consumer)
        ingotFromCrystals(ModItems.trixiteCrystals.get(), ModItems.plutoniumIngot.get(), 3F, consumer, 4)

        // ingots to blocks
        blockFromIngots(ModBlockItems.uraniumBlock.get(), ModItems.uraniumIngot.get(), consumer)
        blockFromIngots(ModBlockItems.u233Block.get(), ModItems.u233Ingot.get(), consumer)
        blockFromIngots(ModBlockItems.u235Block.get(), ModItems.u235Ingot.get(), consumer)
        blockFromIngots(ModBlockItems.u238Block.get(), ModItems.u238Ingot.get(), consumer)
        blockFromIngots(ModBlockItems.uraniumFuelBlock.get(), ModItems.uraniumFuelIngot.get(), consumer)
        blockFromIngots(ModBlockItems.neptuniumBlock.get(), NuclearTags.Items.INGOTS_NEPTUNIUM, "neptunium_ingot", consumer)
        blockFromIngots(ModBlockItems.moxFuelBlock.get(), ModItems.moxFuelIngot.get(), consumer)
        blockFromIngots(ModBlockItems.plutoniumBlock.get(), ModItems.plutoniumIngot.get(), consumer)
        blockFromIngots(ModBlockItems.pu238Block.get(), ModItems.pu238Ingot.get(), consumer)
        blockFromIngots(ModBlockItems.pu239Block.get(), ModItems.pu239Ingot.get(), consumer)
        blockFromIngots(ModBlockItems.pu240Block.get(), ModItems.pu240Ingot.get(), consumer)
        blockFromIngots(ModBlockItems.plutoniumFuelBlock.get(), ModItems.plutoniumFuelIngot.get(), consumer)
        blockFromIngots(ModBlockItems.thoriumBlock.get(), NuclearTags.Items.INGOTS_THORIUM, "thorium_ingot", consumer)
        blockFromIngots(ModBlockItems.thoriumFuelBlock.get(), ModItems.thoriumFuelIngot.get(), consumer)
        blockFromIngots(ModBlockItems.titaniumBlock.get(), NuclearTags.Items.INGOTS_TITANIUM, "titanium_ingot", consumer)
        blockFromIngots(ModBlockItems.sulfurBlock.get(), NuclearTags.Items.DUSTS_SULFUR, "sulfur", consumer)
        blockFromIngots(ModBlockItems.niterBlock.get(), NuclearTags.Items.DUSTS_NITER, "niter", consumer)
        blockFromIngots(ModBlockItems.copperBlock.get(), NuclearTags.Items.INGOTS_COPPER, "copper_ingot", consumer)
        blockFromIngots(ModBlockItems.redCopperBlock.get(), ModItems.redCopperIngot.get(), consumer)
        blockFromIngots(ModBlockItems.advancedAlloyBlock.get(), ModItems.advancedAlloyIngot.get(), consumer)
        blockFromIngots(ModBlockItems.tungstenBlock.get(), NuclearTags.Items.INGOTS_TUNGSTEN, "tungsten_ingot", consumer)
        blockFromIngots(ModBlockItems.aluminiumBlock.get(), NuclearTags.Items.INGOTS_ALUMINIUM, "aluminium_ingot", consumer)
        blockFromIngots(ModBlockItems.fluoriteBlock.get(), NuclearTags.Items.DUSTS_FLUORITE, "fluorite", consumer)
        blockFromIngots(ModBlockItems.berylliumBlock.get(), NuclearTags.Items.INGOTS_BERYLLIUM, "beryllium_ingot", consumer)
        blockFromIngots(ModBlockItems.cobaltBlock.get(), NuclearTags.Items.INGOTS_COBALT, "cobalt_ingot", consumer)
        blockFromIngots(ModBlockItems.steelBlock.get(), NuclearTags.Items.INGOTS_STEEL, "steel_ingot", consumer)
        blockFromIngots(ModBlockItems.leadBlock.get(), NuclearTags.Items.INGOTS_LEAD, "lead_ingot", consumer)
        blockFromIngots(ModBlockItems.lithiumBlock.get(), NuclearTags.Items.INGOTS_LITHIUM, "lithium_cube", consumer)
        blockFromIngots(ModBlockItems.whitePhosphorusBlock.get(), ModItems.whitePhosphorusIngot.get(), consumer)
        blockFromIngots(ModBlockItems.redPhosphorusBlock.get(), ModItems.redPhosphorus.get(), consumer)
        blockFromIngots(ModBlockItems.yellowcakeBlock.get(), ModItems.yellowcake.get(), consumer)
        blockFromIngots(ModBlockItems.scrapBlock.get(), NuclearTags.Items.DUSTS_DUST, "dust", consumer)
        blockFromIngots(ModBlockItems.trinititeBlock.get(), ModItems.trinitite.get(), consumer)
        blockFromIngots(ModBlockItems.nuclearWasteBlock.get(), NuclearTags.Items.COLD_WASTES, "nuclear_wastes_any", consumer)
        blockFromIngots(ModBlockItems.nuclearWasteBlock.get(), ModItems.nuclearWaste.get(), consumer)
        blockFromIngots(ModBlockItems.schrabidiumBlock.get(), ModItems.schrabidiumIngot.get(), consumer)
        blockFromIngots(ModBlockItems.soliniumBlock.get(), ModItems.soliniumIngot.get(), consumer)
        blockFromIngots(ModBlockItems.schrabidiumFuelBlock.get(), ModItems.schrabidiumFuelIngot.get(), consumer)
        blockFromIngots(ModBlockItems.euphemiumBlock.get(), ModItems.euphemiumIngot.get(), consumer)
        blockFromIngots(ModBlockItems.magnetizedTungstenBlock.get(), ModItems.magnetizedTungstenIngot.get(), consumer)
        blockFromIngots(ModBlockItems.combineSteelBlock.get(), ModItems.combineSteelIngot.get(), consumer)
        blockFromIngots(ModBlockItems.deshReinforcedBlock.get(), ModItems.deshIngot.get(), consumer)
        blockFromIngots(ModBlockItems.starmetalBlock.get(), ModItems.starmetalIngot.get(), consumer)
        blockFromIngots(ModBlockItems.australiumBlock.get(), ModItems.australiumIngot.get(), consumer)
        blockFromIngots(ModBlockItems.weidaniumBlock.get(), ModItems.weidaniumIngot.get(), consumer)
        blockFromIngots(ModBlockItems.reiiumBlock.get(), ModItems.reiiumIngot.get(), consumer)
        blockFromIngots(ModBlockItems.unobtainiumBlock.get(), ModItems.unobtainiumIngot.get(), consumer)
        blockFromIngots(ModBlockItems.daffergonBlock.get(), ModItems.daffergonIngot.get(), consumer)
        blockFromIngots(ModBlockItems.verticiumBlock.get(), ModItems.verticiumIngot.get(), consumer)

        ExtendedCookingRecipeBuilder(Ingredient.of(ModItems.combineScrapMetal.get()), .5F, 200, ModItems.combineSteelIngot.get()).group("combine_steel_ingot").unlockedBy("has_combine_steel_scrap_metal", has(ModItems.combineScrapMetal.get())).save(consumer, ResourceLocation(NuclearTech.MODID, "combine_steel_ingot_from_combine_steel_scrap_metal"))

        pressRecipes(consumer)
        blastFurnaceRecipes(consumer)
        parts(consumer)
        machineItems(consumer)
        templates(consumer)
        machines(consumer)
        consumables(consumer)
    }

    private fun pressRecipes(consumer: Consumer<IFinishedRecipe>) {
        flatPressRecipe(NuclearTags.Items.BIOMASS, ModItems.compressedBiomass.get(), "biomass", .1F, consumer)
        flatPressRecipe(NuclearTags.Items.DUSTS_COAL, Items.COAL, "coal_powder", .2F, consumer)
        flatPressRecipe(NuclearTags.Items.DUSTS_LAPIS, Items.LAPIS_LAZULI, "lapis_powder", .2F, consumer)
        flatPressRecipe(NuclearTags.Items.DUSTS_DIAMOND, Items.DIAMOND, "diamond_powder", .2F, consumer)
        flatPressRecipe(NuclearTags.Items.DUSTS_EMERALD, Items.EMERALD, "emerald_powder", .2F, consumer)
        flatPressRecipe(NuclearTags.Items.DUSTS_QUARTZ, Items.QUARTZ, "quartz_powder", .2F, consumer)
        flatPressRecipe(ModItems.lignitePowder.get(), ModItems.ligniteBriquette.get(), .1F, consumer)
        flatPressRecipe(ModItems.denseCoalCluster.get(), Items.DIAMOND, 2F, consumer)

        platePressRecipe(Tags.Items.INGOTS_IRON, ModItems.ironPlate.get(), "iron_ingot", .1F, consumer)
        platePressRecipe(Tags.Items.INGOTS_GOLD, ModItems.goldPlate.get(), "gold_ingot", .1F, consumer)
        platePressRecipe(NuclearTags.Items.INGOTS_STEEL, ModItems.steelPlate.get(), "steel_ingot", .1F, consumer)
        platePressRecipe(NuclearTags.Items.INGOTS_COPPER, ModItems.copperPlate.get(), "copper_ingot", .1F, consumer)
        platePressRecipe(NuclearTags.Items.INGOTS_TITANIUM, ModItems.titaniumPlate.get(), "titanium_ingot", .1F, consumer)
        platePressRecipe(NuclearTags.Items.INGOTS_ALUMINIUM, ModItems.aluminiumPlate.get(), "aluminium_ingot", .1F, consumer)
        platePressRecipe(NuclearTags.Items.INGOTS_LEAD, ModItems.leadPlate.get(), "lead_ingot", .1F, consumer)
        platePressRecipe(ModItems.advancedAlloyIngot.get(), ModItems.advancedAlloyPlate.get(), .1F, consumer)
        platePressRecipe(ModItems.combineSteelIngot.get(), ModItems.combineSteelPlate.get(), .1F, consumer)
        platePressRecipe(ModItems.saturniteIngot.get(), ModItems.saturnitePlate.get(), .2F, consumer)
        platePressRecipe(ModItems.schrabidiumIngot.get(), ModItems.schrabidiumPlate.get(), .5F, consumer)

        wirePressRecipe(Tags.Items.INGOTS_GOLD, ModItems.goldWire.get(), "gold_ingot", .1F, consumer)
        wirePressRecipe(NuclearTags.Items.INGOTS_COPPER, ModItems.copperWire.get(), "copper_ingot", .1F, consumer)
        wirePressRecipe(ModItems.redCopperIngot.get(), ModItems.redCopperWire.get(), .1F, consumer)
        wirePressRecipe(ModItems.advancedAlloyIngot.get(), ModItems.superConductor.get(), .1F, consumer)
        wirePressRecipe(NuclearTags.Items.INGOTS_ALUMINIUM, ModItems.aluminiumWire.get(), "aluminium_ingot", .1F, consumer)
        wirePressRecipe(NuclearTags.Items.INGOTS_TUNGSTEN, ModItems.tungstenWire.get(), "tungsten_ingot", .1F, consumer)
        wirePressRecipe(ModItems.magnetizedTungstenIngot.get(), ModItems.highTemperatureSuperConductor.get(), .1F, consumer)
        wirePressRecipe(ModItems.schrabidiumIngot.get(), ModItems.schrabidiumWire.get(), .5F, consumer)

        pressRecipe(ModItems.basicCircuitAssembly.get(), PressRecipe.StampType.CIRCUIT, ModItems.basicCircuit.get(), 1, .75F, consumer)
    }

    private fun blastFurnaceRecipes(consumer: Consumer<IFinishedRecipe>) {
        blastingRecipe(Tags.Items.INGOTS_IRON, Items.COAL, ModItems.steelIngot.get(), .25F, 2, "iron_ingot", consumer)
        blastingRecipe(NuclearTags.Items.INGOTS_COPPER, Tags.Items.DUSTS_REDSTONE, ModItems.redCopperIngot.get(), .25F, 2, "copper_ingot", "redstone", consumer)
        blastingRecipe(ModItems.redCopperIngot.get(), NuclearTags.Items.INGOTS_STEEL, ModItems.advancedAlloyIngot.get(), .5F, 2, "steel_ingot", consumer)
        blastingRecipe(NuclearTags.Items.INGOTS_TUNGSTEN, Items.COAL, ModItems.neutronReflector.get(), .5F, 2, "tungsten_ingot", consumer)
        blastingRecipe(NuclearTags.Items.INGOTS_LEAD, NuclearTags.Items.INGOTS_COPPER, ModItems.neutronReflector.get(), .25F, 4, "lead_ingot", "copper_ingot", consumer)
        blastingRecipe(NuclearTags.Items.PLATES_LEAD, NuclearTags.Items.PLATES_COPPER, ModItems.neutronReflector.get(), .5F, 1, "lead_plate", "copper_plate", consumer)
        blastingRecipe(NuclearTags.Items.INGOTS_STEEL, NuclearTags.Items.INGOTS_TUNGSTEN, ModItems.highSpeedSteelIngot.get(), .5F, 2, "steel_ingot", "tungsten_ingot", consumer)
        blastingRecipe(NuclearTags.Items.INGOTS_STEEL, NuclearTags.Items.INGOTS_COBALT, ModItems.highSpeedSteelIngot.get(), 1F, 2, "steel_ingot", "cobalt_ingot", consumer)
        blastingRecipe(ModItems.mixedPlate.get(), NuclearTags.Items.PLATES_GOLD, ModItems.paAAlloyPlate.get(), 2F, 2, "gold_plate", consumer)
        blastingRecipe(NuclearTags.Items.INGOTS_TUNGSTEN, ModItems.schrabidiumNugget.get(), ModItems.magnetizedTungstenIngot.get(), 2F, 1, "tungsten_ingot", consumer)
        blastingRecipe(ModItems.saturniteIngot.get(), NuclearTags.Items.DUSTS_METEORITE, ModItems.starmetalIngot.get(), 2F, 2, "meteorite_dust", consumer)
    }

    private fun parts(consumer: Consumer<IFinishedRecipe>) {
        ShapedRecipeBuilder.shaped(ModItems.copperPanel.get()).define('C', NuclearTags.Items.PLATES_COPPER).pattern("CCC").pattern("CCC").group(ModItems.copperPanel.id.path).unlockedBy("has${ModItems.copperPlate.id.path}", has(NuclearTags.Items.PLATES_COPPER)).save(consumer, ModItems.copperPanel.id)
        ShapedRecipeBuilder.shaped(ModItems.heatingCoil.get()).define('T', NuclearTags.Items.WIRES_TUNGSTEN).define('I', Tags.Items.INGOTS_IRON).pattern("TTT").pattern("TIT").pattern("TTT").group(ModItems.heatingCoil.id.path).unlockedBy("has_${ModItems.tungstenWire.get()}", has(NuclearTags.Items.WIRES_TUNGSTEN)).save(consumer, ModItems.heatingCoil.id)
        ShapedRecipeBuilder.shaped(ModItems.steelTank.get(), 2).define('S', NuclearTags.Items.PLATES_STEEL).define('T', NuclearTags.Items.PLATES_TITANIUM).pattern("STS").pattern("S S").pattern("STS").group(ModItems.steelTank.id.path).unlockedBy("has_${ModItems.steelPlate.id.path}", has(NuclearTags.Items.PLATES_STEEL)).save(consumer, ModItems.steelTank.id)
    }

    private fun machineItems(consumer: Consumer<IFinishedRecipe>) {
        pressStamp(Tags.Items.STONE, ModItems.stoneFlatStamp.get(), consumer)
        pressStamp(Tags.Items.INGOTS_IRON, ModItems.ironFlatStamp.get(), consumer)
        pressStamp(NuclearTags.Items.INGOTS_STEEL, ModItems.steelFlatStamp.get(), consumer)
        pressStamp(NuclearTags.Items.INGOTS_TITANIUM, ModItems.titaniumFlatStamp.get(), consumer)
        pressStamp(Tags.Items.OBSIDIAN, ModItems.obsidianFlatStamp.get(), consumer)
        pressStamp(ModItems.schrabidiumIngot.get(), ModItems.schrabidiumFlatStamp.get(), consumer)
    }

    private fun templates(consumer: Consumer<IFinishedRecipe>) {
        ShapedRecipeBuilder.shaped(ModItems.machineTemplateFolder.get()).define('B', Tags.Items.DYES_BLUE).define('P', Items.PAPER).define('W', Tags.Items.DYES_WHITE).pattern("BPB").pattern("WPW").pattern("BPB").group(ModItems.machineTemplateFolder.id.path).unlockedBy("has_${Items.PAPER.registryName!!.path}", has(Items.PAPER)).save(consumer, ModItems.machineTemplateFolder.id)
    }

    private fun machines(consumer: Consumer<IFinishedRecipe>) {
        ShapedRecipeBuilder.shaped(ModBlockItems.siren.get()).define('S', NuclearTags.Items.PLATES_STEEL).define('I', NuclearTags.Items.PLATES_INSULATOR).define('C', ModItems.enhancedCircuit.get()).define('R', Tags.Items.DUSTS_REDSTONE).pattern("SIS").pattern("ICI").pattern("SRS").group(ModBlockItems.siren.id.path).unlockedBy("has_${ModItems.enhancedCircuit.id.path}", has(ModItems.enhancedCircuit.get())).save(consumer, ModBlockItems.siren.id)
        ShapedRecipeBuilder.shaped(ModBlockItems.steamPress.get()).define('I', Tags.Items.INGOTS_IRON).define('F', Items.FURNACE).define('P', Items.PISTON).define('B', Tags.Items.STORAGE_BLOCKS_IRON).pattern("IFI").pattern("IPI").pattern("IBI").group(ModBlockItems.steamPress.id.path).unlockedBy("has_${Items.PISTON.registryName!!.path}", has(Items.PISTON)).save(consumer, ModBlockItems.steamPress.id)
        ShapedRecipeBuilder.shaped(ModBlockItems.blastFurnace.get()).define('T', NuclearTags.Items.INGOTS_TUNGSTEN).define('C', ModItems.copperPanel.get()).define('H', Items.HOPPER).define('F', Items.FURNACE).pattern("T T").pattern("CHC").pattern("TFT").group(ModBlockItems.blastFurnace.id.path).unlockedBy("has_${ModItems.tungstenIngot.get()}", has(NuclearTags.Items.INGOTS_TUNGSTEN)).save(consumer, ModBlockItems.blastFurnace.id)
        ShapedRecipeBuilder.shaped(ModBlockItems.combustionGenerator.get()).define('S', NuclearTags.Items.INGOTS_STEEL).define('T', ModItems.steelTank.get()).define('C', ModItems.redCopperIngot.get()).define('F', Items.FURNACE).pattern("STS").pattern("SCS").pattern("SFS").group(ModBlockItems.combustionGenerator.id.path).unlockedBy("has_${ModItems.redCopperIngot.id.path}", has(ModItems.redCopperIngot.get())).save(consumer, ModBlockItems.combustionGenerator.id)
        ShapedRecipeBuilder.shaped(ModBlockItems.electricFurnace.get()).define('B', NuclearTags.Items.INGOTS_BERYLLIUM).define('C', ModItems.copperPanel.get()).define('F', Items.FURNACE).define('H', ModItems.heatingCoil.get()).pattern("BBB").pattern("CFC").pattern("HHH").group(ModBlockItems.electricFurnace.id.path).unlockedBy("has_${ModItems.berylliumIngot.id.path}", has(NuclearTags.Items.INGOTS_BERYLLIUM)).save(consumer, ModBlockItems.electricFurnace.id)
    }

    private fun consumables(consumer: Consumer<IFinishedRecipe>) {
        ShapedRecipeBuilder.shaped(ModItems.oilDetector.get()).define('G', NuclearTags.Items.WIRES_GOLD).define('S', NuclearTags.Items.PLATES_STEEL).define('C', NuclearTags.Items.INGOTS_COPPER).define('A', ModItems.advancedCircuit.get()).pattern("G C").pattern("GAC").pattern("SSS").group(ModItems.oilDetector.id.path).unlockedBy("has_${ModItems.advancedCircuit.id.path}", has(ModItems.advancedCircuit.get())).save(consumer, ModItems.oilDetector.id)
    }

    // so we can also use tags when declaring a shapeless recipe requiring multiple items of one type
    private fun ShapelessRecipeBuilder.requires(itemTag: ITag<Item>, count: Int): ShapelessRecipeBuilder {
        for (i in 0 until count) requires(itemTag)
        return this
    }

    private fun ingotFromBlock(result: IItemProvider, ingredient: IItemProvider, consumer: Consumer<IFinishedRecipe>) {
        ShapelessRecipeBuilder.shapeless(result, 9).requires(ingredient).group(result.asItem().registryName!!.path).unlockedBy("has_${ingredient.asItem().registryName!!.path}", has(ingredient)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_${ingredient.asItem().registryName!!.path}"))
    }

    // we need the ingredient name because tags haven't been bound yet at this point
    private fun ingotFromBlock(result: IItemProvider, ingredient: ITag<Item>, ingredientName: String, consumer: Consumer<IFinishedRecipe>) {
        ShapelessRecipeBuilder.shapeless(result, 9).requires(ingredient).group(result.asItem().registryName!!.path).unlockedBy("has_$ingredientName", has(ingredient)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_$ingredientName"))
    }

    private fun ingotFromNuggets(result: IItemProvider, ingredient: IItemProvider, consumer: Consumer<IFinishedRecipe>) {
        ShapedRecipeBuilder.shaped(result).define('#', ingredient).pattern("###").pattern("###").pattern("###").group(result.asItem().registryName!!.path).unlockedBy("has_${ingredient.asItem().registryName!!.path}", has(ingredient)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_nuggets"))
    }

    private fun ingotFromNuggets(result: IItemProvider, ingredient: ITag<Item>, ingredientName: String, consumer: Consumer<IFinishedRecipe>) {
        ShapedRecipeBuilder.shaped(result).define('#', ingredient).pattern("###").pattern("###").pattern("###").group(result.asItem().registryName!!.path).unlockedBy("has_${ingredientName}", has(ingredient)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_nuggets"))
    }

    private fun ingotFromOre(ingredient: IItemProvider, result: IItemProvider, experience: Float, consumer: Consumer<IFinishedRecipe>) {
        ExtendedCookingRecipeBuilder(Ingredient.of(ingredient), experience, 200, result).group(result.asItem().registryName!!.path).unlockedBy("has_${ingredient.asItem().registryName!!.path}", has(ingredient)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_${ingredient.asItem().registryName!!.path}"))
        ExtendedCookingRecipeBuilder(Ingredient.of(ingredient), experience / 2, 100, result, serializer = IRecipeSerializer.BLASTING_RECIPE).group(result.asItem().registryName!!.path).unlockedBy("has_${ingredient.asItem().registryName!!.path}", has(ingredient)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_blasting_${ingredient.asItem().registryName!!.path}"))
    }

    private fun ingotFromOre(ingredient: ITag<Item>, result: IItemProvider, experience: Float, ingredientName: String, consumer: Consumer<IFinishedRecipe>) {
        ExtendedCookingRecipeBuilder(Ingredient.of(ingredient), experience, 200, result).group(result.asItem().registryName!!.path).unlockedBy("has_$ingredientName", has(ingredient)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_$ingredientName"))
        ExtendedCookingRecipeBuilder(Ingredient.of(ingredient), experience / 2, 100, result, serializer = IRecipeSerializer.BLASTING_RECIPE).group(result.asItem().registryName!!.path).unlockedBy("has_$ingredientName", has(ingredient)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_blasting_$ingredientName"))
    }

    private fun ingotFromMeteorOre(ingredient: IItemProvider, result: IItemProvider, experience: Float, consumer: Consumer<IFinishedRecipe>) {
        ExtendedCookingRecipeBuilder(Ingredient.of(ingredient), experience, 200, result).group(result.asItem().registryName!!.path).unlockedBy("has_${ingredient.asItem().registryName!!.path}", has(ingredient)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_${ingredient.asItem().registryName!!.path}"))
        ExtendedCookingRecipeBuilder(Ingredient.of(ingredient), experience / 2, 100, result, serializer = IRecipeSerializer.BLASTING_RECIPE).group(result.asItem().registryName!!.path).unlockedBy("has_${ingredient.asItem().registryName!!.path}", has(ingredient)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_blasting_${ingredient.asItem().registryName!!.path}"))
    }

    private fun ingotFromPowder(ingredient: IItemProvider, result: IItemProvider, consumer: Consumer<IFinishedRecipe>) {
        ExtendedCookingRecipeBuilder(Ingredient.of(ingredient), .1F, 200, result).group(result.asItem().registryName!!.path).unlockedBy("has_${ingredient.asItem().registryName!!.path}", has(ingredient)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_powder"))
    }

    private fun ingotFromPowder(ingredient: ITag<Item>, result: IItemProvider, ingredientName: String, consumer: Consumer<IFinishedRecipe>) {
        ExtendedCookingRecipeBuilder(Ingredient.of(ingredient), .1F, 200, result).group(result.asItem().registryName!!.path).unlockedBy("has_$ingredientName", has(ingredient)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_powder"))
    }

    private fun ingotFromCrystals(ingredient: IItemProvider, result: IItemProvider, experience: Float, consumer: Consumer<IFinishedRecipe>, resultCount: Int = 2) {
        ExtendedCookingRecipeBuilder(Ingredient.of(ingredient), experience, 200, result, resultCount).group(result.asItem().registryName!!.path).unlockedBy("has_${ingredient.asItem().registryName!!.path}", has(ingredient)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_${ingredient.asItem().registryName!!.path}"))
    }

    private fun blockFromIngots(result: IItemProvider, ingredient: IItemProvider, consumer: Consumer<IFinishedRecipe>) {
        ShapedRecipeBuilder.shaped(result).define('#', ingredient).pattern("###").pattern("###").pattern("###").group(result.asItem().registryName!!.path).unlockedBy("has_${ingredient.asItem().registryName!!.path}", has(ingredient)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_${ingredient.asItem().registryName!!.path}"))
    }

    private fun blockFromIngots(result: IItemProvider, ingredient: ITag<Item>, ingredientName: String, consumer: Consumer<IFinishedRecipe>) {
        ShapedRecipeBuilder.shaped(result).define('#', ingredient).pattern("###").pattern("###").pattern("###").group(result.asItem().registryName!!.path).unlockedBy("has_${ingredientName}", has(ingredient)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_$ingredientName"))
    }

    private fun pressRecipe(ingredient: IItemProvider, stampType: PressRecipe.StampType, result: IItemProvider, count: Int, experience: Float, consumer: Consumer<IFinishedRecipe>) {
        PressRecipeBuilder(result, stampType, experience, count).requires(ingredient).group(result.asItem().registryName!!.path).unlockedBy("has_${ingredient.asItem().registryName!!.path}", has(ingredient)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_pressing_${ingredient.asItem().registryName!!.path}"))
    }

    private fun pressRecipe(ingredient: ITag<Item>, stampType: PressRecipe.StampType, result: IItemProvider, count: Int, ingredientName: String, experience: Float, consumer: Consumer<IFinishedRecipe>) {
        PressRecipeBuilder(result, stampType, experience, count).requires(ingredient).group(result.asItem().registryName!!.path).unlockedBy("has_$ingredientName", has(ingredient)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_pressing_$ingredientName"))
    }

    private fun flatPressRecipe(ingredient: IItemProvider, result: IItemProvider, experience: Float, consumer: Consumer<IFinishedRecipe>) {
        pressRecipe(ingredient, PressRecipe.StampType.FLAT, result, 1, experience, consumer)
    }

    private fun flatPressRecipe(ingredient: ITag<Item>, result: IItemProvider, ingredientName: String, experience: Float, consumer: Consumer<IFinishedRecipe>) {
        pressRecipe(ingredient, PressRecipe.StampType.FLAT, result, 1, ingredientName, experience, consumer)
    }

    private fun platePressRecipe(ingredient: IItemProvider, result: IItemProvider, experience: Float, consumer: Consumer<IFinishedRecipe>) {
        pressRecipe(ingredient, PressRecipe.StampType.PLATE, result, 1, experience, consumer)
    }

    private fun platePressRecipe(ingredient: ITag<Item>, result: IItemProvider, ingredientName: String, experience: Float, consumer: Consumer<IFinishedRecipe>) {
        pressRecipe(ingredient, PressRecipe.StampType.PLATE, result, 1, ingredientName, experience, consumer)
    }

    private fun wirePressRecipe(ingredient: IItemProvider, result: IItemProvider, experience: Float, consumer: Consumer<IFinishedRecipe>) {
        pressRecipe(ingredient, PressRecipe.StampType.WIRE, result, 8, experience, consumer)
    }

    private fun wirePressRecipe(ingredient: ITag<Item>, result: IItemProvider, ingredientName: String, experience: Float, consumer: Consumer<IFinishedRecipe>) {
        pressRecipe(ingredient, PressRecipe.StampType.WIRE, result, 8, ingredientName, experience, consumer)
    }

    private fun pressStamp(material: IItemProvider, result: IItemProvider, consumer: Consumer<IFinishedRecipe>) {
        ShapedRecipeBuilder.shaped(result).define('R', Tags.Items.DUSTS_REDSTONE).define('B', Tags.Items.INGOTS_BRICK).define('M', material).pattern(" R ").pattern("BBB").pattern("MMM").group("press_stamp_blanks").unlockedBy("has_${material.asItem().registryName!!.path}", has(material)).save(consumer, result.asItem().registryName!!)
    }

    private fun pressStamp(material: ITag<Item>, result: IItemProvider, consumer: Consumer<IFinishedRecipe>) {
        ShapedRecipeBuilder.shaped(result).define('R', Tags.Items.DUSTS_REDSTONE).define('B', Tags.Items.INGOTS_BRICK).define('M', material).pattern(" R ").pattern("BBB").pattern("MMM").group("press_stamp_blanks").unlockedBy("has_material_tag", has(material)).save(consumer, result.asItem().registryName!!)
    }

    private fun blastingRecipe(ingredient1: IItemProvider, ingredient2: IItemProvider, result: IItemProvider, experience: Float, count: Int, consumer: Consumer<IFinishedRecipe>) {
        BlastingRecipeBuilder(result, experience, count).requiresFirst(ingredient1).requiresSecond(ingredient2).unlockedBy("has_${ingredient1.asItem().registryName!!.path}", has(ingredient1)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_${ingredient1.asItem().registryName!!.path}_and_${ingredient2.asItem().registryName!!.path}"))
    }

    private fun blastingRecipe(ingredient1: ITag<Item>, ingredient2: ITag<Item>, result: IItemProvider, experience: Float, count: Int, ingredientName1: String, ingredientName2: String, consumer: Consumer<IFinishedRecipe>) {
        BlastingRecipeBuilder(result, experience, count).requiresFirst(ingredient1).requiresSecond(ingredient2).unlockedBy("has_$ingredientName1", has(ingredient1)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_${ingredientName1}_and_${ingredientName2}"))
    }

    private fun blastingRecipe(ingredient1: ITag<Item>, ingredient2: IItemProvider, result: IItemProvider, experience: Float, count: Int, ingredientName1: String, consumer: Consumer<IFinishedRecipe>) {
        BlastingRecipeBuilder(result, experience, count).requiresFirst(ingredient1).requiresSecond(ingredient2).unlockedBy("has_$ingredientName1", has(ingredient1)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_${ingredientName1}_and_${ingredient2.asItem().registryName!!.path}"))
    }

    private fun blastingRecipe(ingredient1: IItemProvider, ingredient2: ITag<Item>, result: IItemProvider, experience: Float, count: Int, ingredientName2: String, consumer: Consumer<IFinishedRecipe>) {
        BlastingRecipeBuilder(result, experience, count).requiresFirst(ingredient1).requiresSecond(ingredient2).unlockedBy("has_${ingredient1.asItem().registryName!!.path}", has(ingredient1)).save(consumer, ResourceLocation(NuclearTech.MODID, "${result.asItem().registryName!!.path}_from_${ingredient1.asItem().registryName!!.path}_and_${ingredientName2}"))
    }
}
