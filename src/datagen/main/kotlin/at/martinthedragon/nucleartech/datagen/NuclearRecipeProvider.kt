package at.martinthedragon.nucleartech.datagen

import at.martinthedragon.nucleartech.*
import at.martinthedragon.nucleartech.datagen.recipe.*
import at.martinthedragon.nucleartech.fluid.NTechFluids
import at.martinthedragon.nucleartech.item.NTechBlockItems
import at.martinthedragon.nucleartech.item.NTechItems
import at.martinthedragon.nucleartech.recipe.PressingRecipe
import at.martinthedragon.nucleartech.recipe.RecipeSerializers
import at.martinthedragon.nucleartech.recipe.StackedIngredient
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap
import net.minecraft.advancements.critereon.InventoryChangeTrigger
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.data.DataGenerator
import net.minecraft.data.recipes.*
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.common.Tags
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Consumer
import at.martinthedragon.nucleartech.datagen.recipe.AnvilConstructingRecipeBuilder as AnvilRecipeBuilder
import at.martinthedragon.nucleartech.recipe.anvil.AnvilConstructingRecipe.OverlayType as AnvilRecipeType

@Suppress("SameParameterValue")
class NuclearRecipeProvider(generator: DataGenerator) : RecipeProvider(generator) {
    override fun getName(): String = "Nuclear Tech Mod Recipes"

    override fun buildCraftingRecipes(consumer: Consumer<FinishedRecipe>) {
        setExperienceYields()

        SpecialRecipeBuilder.special(RecipeSerializers.SMITHING_RENAMING.get()).save(consumer, ntm("anvil_smithing_renaming").toString())

        ShapelessRecipeBuilder.shapeless(NTechItems.schrabidiumFuelIngot.get()).requires(NTechItems.schrabidiumNugget.get(), 3).requires(NTechTags.Items.NUGGETS_NEPTUNIUM, 3).requires(NTechTags.Items.NUGGETS_BERYLLIUM, 3).group("schrabidium_fuel_ingot").unlockedBy("has_schrabidium_nugget", has(
            NTechItems.schrabidiumNugget.get())).save(consumer, ntm("schrabidium_fuel_ingot_from_isotope_nuggets"))
        ShapelessRecipeBuilder.shapeless(NTechItems.highEnrichedSchrabidiumFuelIngot.get()).requires(NTechItems.schrabidiumNugget.get(), 5).requires(NTechTags.Items.NUGGETS_NEPTUNIUM, 2).requires(NTechTags.Items.NUGGETS_BERYLLIUM, 2).group("high_enriched_schrabidium_fuel_ingot").unlockedBy("has_schrabidium_nugget", has(
            NTechItems.schrabidiumNugget.get())).save(consumer, ntm("high_enriched_schrabidium_fuel_ingot_from_isotope_nuggets"))
        ShapelessRecipeBuilder.shapeless(NTechItems.lowEnrichedSchrabidiumFuelIngot.get()).requires(NTechItems.schrabidiumNugget.get()).requires(NTechTags.Items.NUGGETS_NEPTUNIUM, 4).requires(NTechTags.Items.NUGGETS_BERYLLIUM, 4).group("low_enriched_schrabidium_fuel_ingot").unlockedBy("has_schrabidium_nugget", has(
            NTechItems.schrabidiumNugget.get())).save(consumer, ntm("low_enriched_schrabidium_fuel_ingot_from_isotope_nuggets"))
        ShapelessRecipeBuilder.shapeless(NTechItems.moxFuelIngot.get()).requires(NTechItems.u235Nugget.get(), 3).requires(
            NTechItems.pu238Nugget.get()).requires(NTechItems.pu239Nugget.get(), 3).requires(NTechItems.u238Nugget.get(), 2).group("mox_fuel_ingot").unlockedBy("has_plutonium_nugget", has(NTechTags.Items.NUGGETS_PLUTONIUM)).save(consumer, ntm("mox_fuel_ingot_from_isotope_nuggets"))
        ShapelessRecipeBuilder.shapeless(NTechItems.plutoniumFuelIngot.get()).requires(NTechItems.pu238Nugget.get()).requires(
            NTechItems.pu239Nugget.get(), 5).requires(NTechItems.pu240Nugget.get(), 3).group("plutonium_fuel_ingot").unlockedBy("has_plutonium_nugget", has(NTechTags.Items.NUGGETS_PLUTONIUM)).save(consumer, ntm("plutonium_fuel_ingot_from_isotope_nuggets"))
        ShapelessRecipeBuilder.shapeless(NTechItems.thoriumFuelIngot.get()).requires(NTechItems.u233Nugget.get(), 3).requires(NTechTags.Items.NUGGETS_THORIUM, 6).group("thorium_fuel_ingot").unlockedBy("has_thorium_nugget", has(NTechTags.Items.NUGGETS_THORIUM)).save(consumer, ntm("thorium_fuel_ingot_from_isotope_nuggets"))
        ShapelessRecipeBuilder.shapeless(NTechItems.uraniumFuelIngot.get()).requires(NTechItems.u238Nugget.get(), 6).requires(
            NTechItems.u233Nugget.get(), 3).group("uranium_fuel_ingot").unlockedBy("has_u238_nugget", has(NTechItems.u238Nugget.get())).save(consumer, ntm("uranium_fuel_ingot_from_isotope_nuggets_u233"))
        ShapelessRecipeBuilder.shapeless(NTechItems.uraniumFuelIngot.get()).requires(NTechItems.u238Nugget.get(), 6).requires(
            NTechItems.u235Nugget.get(), 3).group("uranium_fuel_ingot").unlockedBy("has_u238_nugget", has(NTechItems.u238Nugget.get())).save(consumer, ntm("uranium_fuel_ingot_from_isotope_nuggets_u235"))

        // TODO a lot of repeating stuff here. maybe this could be simplified?
        RuleBasedRecipeBuilder.create {
            forModTagsAndMaterials()
            addRule { tagGroup -> // blocks to ingots
                val ingot = tagGroup.materialGroup.ingot() ?: return@addRule null
                val (blockTag, blockItem) = tagGroup.blockTagAndItem()
                val (ingredient, condition) = ingredientAndConditionOf(blockTag, blockItem) ?: return@addRule null
                ShapelessRecipeBuilder.shapeless(ingot, 9).requires(ingredient).group(getItemName(ingot)).unlockedBy(getHasName(blockTag, blockItem), condition) to ntm(getConversionRecipeName(ingot, blockTag, blockItem))
            }
            addRule { tagGroup -> // ingots to blocks
                val block = tagGroup.materialGroup.block() ?: return@addRule null
                val (ingotTag, ingotItem) = tagGroup.ingotTagAndItem()
                val (ingredient, condition) = ingredientAndConditionOf(ingotTag, ingotItem) ?: return@addRule null
                ShapedRecipeBuilder.shaped(block).define('#', ingredient).pattern("###").pattern("###").pattern("###").group(getItemName(block)).unlockedBy(getHasName(ingotTag, ingotItem), condition) to ntm(getItemName(block))
            }
            addRule { tagGroup -> // nuggets to ingots
                val ingot = tagGroup.materialGroup.ingot() ?: return@addRule null
                val (nuggetTag, nuggetItem) = tagGroup.nuggetTagAndItem()
                val (ingredient, condition) = ingredientAndConditionOf(nuggetTag, nuggetItem) ?: return@addRule null
                ShapedRecipeBuilder.shaped(ingot).define('#', ingredient).pattern("###").pattern("###").pattern("###").group(getItemName(ingot)).unlockedBy(getHasName(nuggetTag, nuggetItem), condition) to ntm("${getItemName(ingot)}_from_nuggets")
            }
            addRule { tagGroup -> // ingots to nuggets
                val nugget = tagGroup.materialGroup.nugget() ?: return@addRule null
                val (ingotTag, ingotItem) = tagGroup.ingotTagAndItem()
                val (ingredient, condition) = ingredientAndConditionOf(ingotTag, ingotItem) ?: return@addRule null
                ShapelessRecipeBuilder.shapeless(nugget, 9).requires(ingredient).group(getItemName(nugget)).unlockedBy(getHasName(ingotTag, ingotItem), condition)to ntm(getItemName(nugget))
            }
            addRule { tagGroup -> // smelting ores to ingots
                val ingot = tagGroup.materialGroup.ingot() ?: return@addRule null
                val (oreTag, oreItem) = tagGroup.oreTagAndItem()
                val (ingredient, condition) = ingredientAndConditionOf(oreTag, oreItem) ?: return@addRule null
                val experience = experienceLookupTable.getFloat(tagGroup)
                ExtendedCookingRecipeBuilder(ingredient, experience, 200, ingot).group(getItemName(ingot)).unlockedBy(getHasName(oreTag, oreItem), condition) to ntm("${getItemName(ingot)}_from_smelting_${getItemName(oreTag, oreItem)}")
            }
            addRule { tagGroup -> // blasting ores to ingots
                val ingot = tagGroup.materialGroup.ingot() ?: return@addRule null
                val (oreTag, oreItem) = tagGroup.oreTagAndItem()
                val (ingredient, condition) = ingredientAndConditionOf(oreTag, oreItem) ?: return@addRule null
                val experience = experienceLookupTable.getFloat(tagGroup)
                ExtendedCookingRecipeBuilder(ingredient, experience / 2F, 100, ingot, serializer = RecipeSerializer.BLASTING_RECIPE).group(getItemName(ingot)).unlockedBy(getHasName(oreTag, oreItem), condition) to ntm("${getItemName(ingot)}_from_blasting_${getItemName(oreTag, oreItem)}")
            }
            addRule { tagGroup -> // smelting raw ores to ingots
                val ingot = tagGroup.materialGroup.ingot() ?: return@addRule null
                val (rawTag, rawItem) = tagGroup.rawTagAndItem()
                val (ingredient, condition) = ingredientAndConditionOf(rawTag, rawItem) ?: return@addRule null
                val experience = experienceLookupTable.getFloat(tagGroup)
                ExtendedCookingRecipeBuilder(ingredient, experience, 200, ingot).group(getItemName(ingot)).unlockedBy(getHasName(rawTag, rawItem), condition) to ntm("${getItemName(ingot)}_from_smelting_${getItemName(rawTag, rawItem)}")
            }
            addRule { tagGroup -> // blasting raw ores to ingots
                val ingot = tagGroup.materialGroup.ingot() ?: return@addRule null
                val (rawTag, rawItem) = tagGroup.rawTagAndItem()
                val (ingredient, condition) = ingredientAndConditionOf(rawTag, rawItem) ?: return@addRule null
                val experience = experienceLookupTable.getFloat(tagGroup)
                ExtendedCookingRecipeBuilder(ingredient, experience / 2F, 100, ingot, serializer = RecipeSerializer.BLASTING_RECIPE).group(getItemName(ingot)).unlockedBy(getHasName(rawTag, rawItem), condition) to ntm("${getItemName(ingot)}_from_blasting_${getItemName(rawTag, rawItem)}")
            }
        }.build(consumer)

        RuleBasedRecipeBuilder.create {
            forAllTagsAndMaterials().excluding(TagGroups.coal) // coal powder turns to coke
            addRule { tagGroup -> // powders to ingots
                val ingot = tagGroup.materialGroup.ingot() ?: return@addRule null
                val (powderTag, powderItem) = tagGroup.powderTagAndItem()
                val (ingredient, condition) = ingredientAndConditionOf(powderTag, powderItem) ?: return@addRule null
                ExtendedCookingRecipeBuilder(ingredient, .1F, 200, ingot).group(getItemName(ingot)).unlockedBy(getHasName(powderTag, powderItem), condition) to ntm("${getItemName(ingot)}_from_smelting_${getItemName(powderTag, powderItem)}")
            }
        }.build(consumer)

        RuleBasedRecipeBuilder.create {
            forAllTagsAndMaterials()
            addRule { tagGroup -> // crystals to ingots
                val ingot = tagGroup.materialGroup.ingot() ?: return@addRule null
                val (crystalsTag, crystalsItem) = tagGroup.crystalsTagAndItem()
                val (ingredient, condition) = ingredientAndConditionOf(crystalsTag, crystalsItem) ?: return@addRule null
                val experience = experienceLookupTable.getFloat(tagGroup)
                val count = if (tagGroup.materialGroup.ingotIsPowder) 6 else 2
                ExtendedCookingRecipeBuilder(ingredient, experience, 200, ingot, count).group(getItemName(ingot)).unlockedBy(getHasName(crystalsTag, crystalsItem), condition) to ntm("${getItemName(ingot)}_from_smelting_${getItemName(crystalsTag, crystalsItem)}")
            }
        }.build(consumer)

        // meteor ores to ingots
        ingotFromMeteorOre(NTechBlockItems.meteorUraniumOre.get(), NTechItems.uraniumIngot.get(), 3F, consumer)
        ingotFromMeteorOre(NTechBlockItems.meteorThoriumOre.get(), NTechItems.th232Ingot.get(), 6f, consumer)
        ingotFromMeteorOre(NTechBlockItems.meteorTitaniumOre.get(), NTechItems.titaniumIngot.get(), 2.4F, consumer)
        ingotFromMeteorOre(NTechBlockItems.meteorSulfurOre.get(), NTechItems.sulfur.get(), 1F, consumer)
        ingotFromMeteorOre(NTechBlockItems.meteorCopperOre.get(), Items.COPPER_INGOT, 1.5F, consumer)
        ingotFromMeteorOre(NTechBlockItems.meteorTungstenOre.get(), NTechItems.tungstenIngot.get(), 2.25F, consumer)
        ingotFromMeteorOre(NTechBlockItems.meteorAluminiumOre.get(), NTechItems.aluminiumIngot.get(), 1.8F, consumer)
        ingotFromMeteorOre(NTechBlockItems.meteorLeadOre.get(), NTechItems.leadIngot.get(), 1.8F, consumer)
        ingotFromMeteorOre(NTechBlockItems.meteorLithiumOre.get(), NTechItems.lithiumCube.get(), 1.8F, consumer)

        // crystals to ingots
        ingotFromCrystals(NTechItems.trixiteCrystals.get(), NTechItems.plutoniumIngot.get(), 3F, consumer, 4)

        ExtendedCookingRecipeBuilder(Ingredient.of(NTechItems.combineScrapMetal.get()), .5F, 200, NTechItems.combineSteelIngot.get()).group("combine_steel_ingot").unlockedBy("has_combine_steel_scrap_metal", has(
            NTechItems.combineScrapMetal.get())).save(consumer, ntm("combine_steel_ingot_from_combine_steel_scrap_metal"))

        pressRecipes(consumer)
        blastFurnaceRecipes(consumer)
        shreddingRecipes(consumer)

        parts(consumer)
        machineItems(consumer)
        templates(consumer)
        blocks(consumer)
        machines(consumer)
        anvilSmithing(consumer)
        anvilConstructing(consumer)
        assembly(consumer)
        chemistry(consumer)
        consumables(consumer)
        misc(consumer)
    }

    private fun pressRecipes(consumer: Consumer<FinishedRecipe>) {
        RuleBasedRecipeBuilder.create {
            forAllTagsAndMaterials()
            addRule { tagGroup ->
                val plate = tagGroup.materialGroup.plate() ?: return@addRule null
                val (ingotTag, ingotItem) = tagGroup.ingotTagAndItem()
                val (ingredient, condition) = ingredientAndConditionOf(ingotTag, ingotItem) ?: return@addRule null
                PressRecipeBuilder(plate, PressingRecipe.StampType.PLATE, .2F).requires(ingredient).group(getItemName(plate)).unlockedBy(getHasName(ingotTag, ingotItem), condition) to ntm("${getItemName(plate)}_from_pressing_${getItemName(ingotTag, ingotItem)}")
            }
            addRule { tagGroup ->
                val wire = tagGroup.materialGroup.wire() ?: return@addRule null
                val (ingotTag, ingotItem) = tagGroup.ingotTagAndItem()
                val (ingredient, condition) = ingredientAndConditionOf(ingotTag, ingotItem) ?: return@addRule null
                PressRecipeBuilder(wire, PressingRecipe.StampType.WIRE, .2F, 8).requires(ingredient).group(getItemName(wire)).unlockedBy(getHasName(ingotTag, ingotItem), condition) to ntm("${getItemName(wire)}_from_pressing_${getItemName(ingotTag, ingotItem)}")
            }
        }.build(consumer)

        flatPressRecipe(NTechTags.Items.BIOMASS, NTechItems.compressedBiomass.get(), "biomass", .1F, consumer)
        flatPressRecipe(NTechTags.Items.DUSTS_COAL, Items.COAL, "coal_powder", .2F, consumer)
        flatPressRecipe(NTechTags.Items.DUSTS_LAPIS, Items.LAPIS_LAZULI, "lapis_powder", .2F, consumer)
        flatPressRecipe(NTechTags.Items.DUSTS_DIAMOND, Items.DIAMOND, "diamond_powder", .2F, consumer)
        flatPressRecipe(NTechTags.Items.DUSTS_EMERALD, Items.EMERALD, "emerald_powder", .2F, consumer)
        flatPressRecipe(NTechTags.Items.DUSTS_QUARTZ, Items.QUARTZ, "quartz_powder", .2F, consumer)
        flatPressRecipe(NTechTags.Items.DUSTS_LIGNITE, NTechItems.ligniteBriquette.get(), "lignite_powder", .1F, consumer)
        flatPressRecipe(NTechItems.denseCoalCluster.get(), Items.DIAMOND, 2F, consumer)

        pressRecipe(NTechItems.basicCircuitAssembly.get(), PressingRecipe.StampType.CIRCUIT, NTechItems.basicCircuit.get(), 1, .75F, consumer)
    }

    private fun blastFurnaceRecipes(consumer: Consumer<FinishedRecipe>) {
        blastingRecipe(Tags.Items.INGOTS_IRON, Items.COAL, NTechItems.steelIngot.get(), .25F, 2, "iron_ingot", consumer)
        blastingRecipe(Tags.Items.INGOTS_COPPER, Tags.Items.DUSTS_REDSTONE, NTechItems.redCopperIngot.get(), .25F, 2, "copper_ingot", "redstone", consumer)
        blastingRecipe(NTechTags.Items.INGOTS_RED_COPPER, NTechTags.Items.INGOTS_STEEL, NTechItems.advancedAlloyIngot.get(), .5F, 2, "red_copper_ingot", "steel_ingot",consumer)
        blastingRecipe(NTechTags.Items.INGOTS_TUNGSTEN, Items.COAL, NTechItems.neutronReflector.get(), .5F, 2, "tungsten_ingot", consumer)
        blastingRecipe(NTechTags.Items.INGOTS_LEAD, Tags.Items.INGOTS_COPPER, NTechItems.neutronReflector.get(), .25F, 4, "lead_ingot", "copper_ingot", consumer)
        blastingRecipe(NTechTags.Items.PLATES_LEAD, NTechTags.Items.PLATES_COPPER, NTechItems.neutronReflector.get(), .5F, 1, "lead_plate", "copper_plate", consumer)
        blastingRecipe(NTechTags.Items.INGOTS_STEEL, NTechTags.Items.INGOTS_TUNGSTEN, NTechItems.highSpeedSteelIngot.get(), .5F, 2, "steel_ingot", "tungsten_ingot", consumer)
        blastingRecipe(NTechTags.Items.INGOTS_STEEL, NTechTags.Items.INGOTS_COBALT, NTechItems.highSpeedSteelIngot.get(), 1F, 2, "steel_ingot", "cobalt_ingot", consumer)
        blastingRecipe(NTechItems.mixedPlate.get(), NTechTags.Items.PLATES_GOLD, NTechItems.paAAlloyPlate.get(), 2F, 2, "gold_plate", consumer)
        blastingRecipe(NTechTags.Items.INGOTS_TUNGSTEN, NTechTags.Items.NUGGETS_SCHRABIDIUM, NTechItems.magnetizedTungstenIngot.get(), 2F, 1, "tungsten_ingot", "schrabidium_nugget", consumer)
        blastingRecipe(NTechTags.Items.INGOTS_SATURNITE, NTechTags.Items.DUSTS_METEORITE, NTechItems.starmetalIngot.get(), 2F, 2, "saturnite_ingot", "meteorite_dust", consumer)
    }

    private fun shreddingRecipes(consumer: Consumer<FinishedRecipe>) {
        shreddingRecipe(Tags.Items.DUSTS, NTechItems.dust.get(), .1F, 1, consumer, "any_dust")
        shreddingRecipe(Tags.Items.SAND, NTechItems.dust.get(), .1F, 2, consumer, "sand")
        shreddingRecipe(NTechTags.Items.SCRAP, NTechItems.dust.get(), .25F, 1, consumer, "scrap")

        RuleBasedRecipeBuilder.create {
            forAllTagsAndMaterials().excluding(TagGroups.diamond, TagGroups.starmetal)
            addRule { tagGroup ->
                val powder = tagGroup.materialGroup.powder() ?: return@addRule null
                val (oreTag, oreItem) = tagGroup.oreTagAndItem()
                val (ingredient, condition) = ingredientAndConditionOf(oreTag, oreItem) ?: return@addRule null
                val experience = experienceLookupTable.getFloat(tagGroup) / 2F
                val count = if (tagGroup.materialGroup.ingotIsPowder) 6 else 2
                ShreddingRecipeBuilder(powder, experience, count, ingredient).group(getItemName(powder)).unlockedBy(getHasName(oreTag, oreItem), condition) to ntm("${getItemName(powder)}_from_shredding_${getItemName(oreTag, oreItem)}")
            }
        }.build(consumer)

        RuleBasedRecipeBuilder.create {
            forAllTagsAndMaterials().excluding(TagGroups.starmetal)
            addRule { tagGroup ->
                val powder = tagGroup.materialGroup.powder() ?: return@addRule null
                val (crystalsTag, crystalsItem) = tagGroup.crystalsTagAndItem()
                val (ingredient, condition) = ingredientAndConditionOf(crystalsTag, crystalsItem) ?: return@addRule null
                val experience = experienceLookupTable.getFloat(tagGroup) / 2F
                val count = if (tagGroup.materialGroup.ingotIsPowder) 9 else 3
                ShreddingRecipeBuilder(powder, experience, count, ingredient).group(getItemName(powder)).unlockedBy(getHasName(crystalsTag, crystalsItem), condition) to ntm("${getItemName(powder)}_from_shredding_${getItemName(crystalsTag, crystalsItem)}")
            }
            addRule { tagGroup ->
                val powder = tagGroup.materialGroup.powder() ?: return@addRule null
                val (blockTag, blockItem) = tagGroup.blockTagAndItem()
                val (ingredient, condition) = ingredientAndConditionOf(blockTag, blockItem) ?: return@addRule null
                ShreddingRecipeBuilder(powder, 0F, 9, ingredient).group(getItemName(powder)).unlockedBy(getHasName(blockTag, blockItem), condition) to ntm("${getItemName(powder)}_from_shredding_${getItemName(blockTag, blockItem)}")
            }
            addRule { tagGroup ->
                if (tagGroup.materialGroup.ingotIsPowder) return@addRule null
                val powder = tagGroup.materialGroup.powder() ?: return@addRule null
                val (ingotTag, ingotItem) = tagGroup.ingotTagAndItem()
                val (ingredient, condition) = ingredientAndConditionOf(ingotTag, ingotItem) ?: return@addRule null
                ShreddingRecipeBuilder(powder, 0F, 1, ingredient).group(getItemName(powder)).unlockedBy(getHasName(ingotTag, ingotItem), condition) to ntm("${getItemName(powder)}_from_shredding_${getItemName(ingotTag, ingotItem)}")
            }
        }.build(consumer)

        // ores to powder
        shreddingRecipe(Tags.Items.ORES_DIAMOND, NTechItems.diamondPowder.get(), .5F, 2, consumer, "diamond_ore") // TODO diamond gravel
        shreddingRecipe(NTechBlockItems.rareEarthOre.get(), NTechItems.deshMix.get(), 1.5F, 1, consumer)
        shreddingRecipe(NTechTags.Items.ORES_STARMETAL, NTechItems.highSpeedSteelPowder.get(), 2F, 4, consumer, "starmetal_ore")
        shreddingRecipe(NTechBlockItems.trixite.get(), NTechItems.plutoniumPowder.get(), 1.5F, 4, consumer)
        shreddingRecipe(NTechBlockItems.meteorLithiumOre.get(), NTechItems.lithiumPowder.get(), 1F, 2, consumer)

        // crystals to powder
        shreddingRecipe(NTechItems.rareEarthCrystals.get(), NTechItems.deshMix.get(), 1F, 2, consumer)
        shreddingRecipe(NTechTags.Items.CRYSTALS_STARMETAL, NTechItems.highSpeedSteelPowder.get(), 3F, 9, consumer, "starmetal_crystals")
        shreddingRecipe(NTechItems.trixiteCrystals.get(), NTechItems.plutoniumPowder.get(), 1.5F, 9, consumer)

        // blocks to powder
        shreddingRecipe(NTechTags.Items.STORAGE_BLOCKS_STARMETAL, NTechItems.highSpeedSteelPowder.get(), 20F, 36, consumer, "starmetal_block")
        shreddingRecipe(Items.GLOWSTONE, Items.GLOWSTONE_DUST, 0F, 4, consumer)

        // ingots to powder
        shreddingRecipe(NTechTags.Items.INGOTS_STARMETAL, NTechItems.highSpeedSteelPowder.get(), 1F, 1, consumer, "starmetal_ingot")

        // fragments to powder
        shreddingRecipe(NTechTags.Items.GEMS_NEODYMIUM, NTechItems.tinyNeodymiumPowder.get(), 0F, 1, consumer, "neodymium_fragment")
        shreddingRecipe(NTechTags.Items.GEMS_COBALT, NTechItems.tinyCobaltPowder.get(), 0F, 1, consumer, "cobalt_fragment")
        shreddingRecipe(NTechTags.Items.GEMS_NIOBIUM, NTechItems.tinyNiobiumPowder.get(), 0F, 1, consumer, "niobium_fragment")
        shreddingRecipe(NTechTags.Items.GEMS_CERIUM, NTechItems.tinyCeriumPowder.get(), 0F, 1, consumer, "cerium_fragment")
        shreddingRecipe(NTechTags.Items.GEMS_LANTHANUM, NTechItems.tinyLanthanumPowder.get(), 0F, 1, consumer, "lanthanum_fragment")
        shreddingRecipe(NTechTags.Items.GEMS_ACTINIUM, NTechItems.tinyActiniumPowder.get(), 0F, 1, consumer, "actinium_fragment")
        shreddingRecipe(NTechItems.meteoriteFragment.get(), NTechItems.tinyMeteoritePowder.get(), 0F, 1, consumer)

        // coils to powder
        shreddingRecipe(NTechItems.copperCoil.get(), NTechItems.redCopperPowder.get(), .1F, 1, consumer)
        shreddingRecipe(NTechItems.superConductingCoil.get(), NTechItems.advancedAlloyPowder.get(), .1F, 1, consumer)
        shreddingRecipe(NTechItems.goldCoil.get(), NTechItems.goldPowder.get(), .1F, 1, consumer)
        shreddingRecipe(NTechItems.ringCoil.get(), NTechItems.redCopperPowder.get(), .1F, 2, consumer)
        shreddingRecipe(NTechItems.superConductingRingCoil.get(), NTechItems.advancedAlloyPowder.get(), .1F, 2, consumer)
        shreddingRecipe(NTechItems.goldRingCoil.get(), NTechItems.goldPowder.get(), .1F, 2, consumer)
        shreddingRecipe(NTechItems.heatingCoil.get(), NTechItems.tungstenPowder.get(), .1F, 1, consumer)

        // quartz to powder
        shreddingRecipe(Tags.Items.STORAGE_BLOCKS_QUARTZ, NTechItems.quartzPowder.get(), 0F, 4, consumer, "quartz_block")
        shreddingRecipe(Items.QUARTZ_BRICKS, NTechItems.quartzPowder.get(), 0F, 4, consumer)
        shreddingRecipe(Items.QUARTZ_PILLAR, NTechItems.quartzPowder.get(), 0F, 4, consumer)
        shreddingRecipe(Items.QUARTZ_STAIRS, NTechItems.quartzPowder.get(), 0F, 4, consumer)
        shreddingRecipe(Items.CHISELED_QUARTZ_BLOCK, NTechItems.quartzPowder.get(), 0F, 4, consumer)
        shreddingRecipe(Items.QUARTZ_SLAB, NTechItems.quartzPowder.get(), 0F, 2, consumer)
        shreddingRecipe(Items.SMOOTH_QUARTZ, NTechItems.quartzPowder.get(), 0F, 4, consumer)
        shreddingRecipe(Items.SMOOTH_QUARTZ_STAIRS, NTechItems.quartzPowder.get(), 0F, 3, consumer)
        shreddingRecipe(Items.SMOOTH_QUARTZ_SLAB, NTechItems.quartzPowder.get(), 0F, 2, consumer)
        shreddingRecipe(Tags.Items.ORES_QUARTZ, NTechItems.quartzPowder.get(), 0F, 2, consumer, "quartz_ore")
        shreddingRecipe(Tags.Items.GEMS_QUARTZ, NTechItems.quartzPowder.get(), 0F, 1, consumer, "quartz")

        shreddingRecipe(NTechBlockItems.emptyOilDeposit.get(), Items.GRAVEL, .05F, 1, consumer)
        shreddingRecipe(Tags.Items.STONE, Items.GRAVEL, .0F, 1, consumer, "stone")
        shreddingRecipe(Tags.Items.COBBLESTONE, Items.COBBLESTONE, 0F, 1, consumer, "cobblestone")
        shreddingRecipe(ItemTags.STONE_BRICKS, Items.GRAVEL, .1F, 1, consumer, "stone_bricks")
        shreddingRecipe(Tags.Items.GRAVEL, Items.SAND, 0F, 1, consumer, "gravel")
        shreddingRecipe(Items.ANVIL, NTechItems.ironPowder.get(), 0F, 31, consumer)
        shreddingRecipe(Items.CLAY, Items.CLAY_BALL, 0F, 4, consumer)
        shreddingRecipe(Items.BRICK, Items.CLAY_BALL, 0F, 1, consumer)
        shreddingRecipe(Items.BRICKS, Items.CLAY_BALL, 0F, 4, consumer)
        shreddingRecipe(Items.BRICK_STAIRS, Items.CLAY_BALL, 0F, 3, consumer)
        shreddingRecipe(Items.BRICK_SLAB, Items.CLAY_BALL, 0F, 2, consumer)
        shreddingRecipe(ItemTags.WOOL, Items.STRING, 0F, 4, consumer, "wool")
        shreddingRecipe(Items.FLOWER_POT, Items.CLAY_BALL, 0F, 3, consumer)
        shreddingRecipe(Items.ENCHANTED_BOOK, NTechItems.enchantmentPowder.get(), 2F, 1, consumer)
        shreddingRecipe(Items.PACKED_ICE, NTechItems.cryoPowder.get(), .1F, 1, consumer)
        shreddingRecipe(Items.BLUE_ICE, NTechItems.cryoPowder.get(), .1F, 2, consumer)
        shreddingRecipe(Items.TNT, Items.GUNPOWDER, .1F, 5, consumer)
        shreddingRecipe(Items.SANDSTONE, Items.SAND, 0F, 4, consumer)
        shreddingRecipe(Items.SANDSTONE_STAIRS, Items.SAND, 0F, 6, consumer)
        shreddingRecipe(Items.SANDSTONE_SLAB, Items.SAND, 0F, 2, consumer)
        shreddingRecipe(Items.RED_SANDSTONE, Items.RED_SAND, 0F, 4, consumer)
        shreddingRecipe(Items.RED_SANDSTONE_STAIRS, Items.RED_SAND, 0F, 3, consumer)
        shreddingRecipe(Items.RED_SANDSTONE_SLAB, Items.RED_SAND, 0F, 2, consumer)
        shreddingRecipe(Items.SUGAR_CANE, Items.PAPER, .05F, 3, consumer)

        // TODO consider doing biomass here
    }

    private fun parts(consumer: Consumer<FinishedRecipe>) {
        ingotFromPowder(NTechTags.Items.DUSTS_COAL, NTechItems.coke.get(), "coal_powder", consumer)
        ExtendedCookingRecipeBuilder(Ingredient.of(NTechItems.ligniteBriquette.get()), .1F, 200, NTechItems.coke.get()).group(
            NTechItems.coke.id.path).unlockedBy("has_${NTechItems.ligniteBriquette.id.path}", has(NTechItems.ligniteBriquette.get())).save(consumer, NTechItems.coke.id)
        ShapedRecipeBuilder.shaped(NTechItems.copperPanel.get()).define('C', NTechTags.Items.PLATES_COPPER).pattern("CCC").pattern("CCC").group(
            NTechItems.copperPanel.id.path).unlockedBy("has${NTechItems.copperPlate.id.path}", has(NTechTags.Items.PLATES_COPPER)).save(consumer, NTechItems.copperPanel.id)
        //region Circuits
        ShapedRecipeBuilder.shaped(NTechItems.basicCircuitAssembly.get()).define('W', NTechTags.Items.WIRES_ALUMINIUM).define('R', Tags.Items.DUSTS_REDSTONE).define('P', NTechTags.Items.PLATES_STEEL).pattern("W").pattern("R").pattern("P").unlockedBy(getHasName(Items.REDSTONE), has(Tags.Items.DUSTS_REDSTONE)).save(consumer)
        AssemblyRecipeBuilder(NTechItems.enhancedCircuit.get(), 1, 100).requires(NTechItems.basicCircuit.get()).requires(4 to NTechTags.Items.WIRES_COPPER, 1 to NTechTags.Items.DUSTS_QUARTZ, 1 to NTechTags.Items.PLATES_COPPER).save(consumer)
        AssemblyRecipeBuilder(NTechItems.advancedCircuit.get(), 1, 150).requires(NTechItems.enhancedCircuit.get()).requires(4 to NTechTags.Items.WIRES_RED_COPPER, 1 to NTechTags.Items.DUSTS_GOLD, 1 to NTechTags.Items.PLATES_INSULATOR).save(consumer)
        ExtendedCookingRecipeBuilder(Ingredient.of(NTechItems.enhancedCircuit.get()), 0F, 200, NTechItems.basicCircuit.get()).unlockedBy("has_circuit", has(
            NTechItems.enhancedCircuit.get())).save(consumer, ntm("${getItemName(NTechItems.basicCircuit.get())}_from_smelting_${getItemName(
            NTechItems.enhancedCircuit.get())}"))
        ExtendedCookingRecipeBuilder(Ingredient.of(NTechItems.advancedCircuit.get()), 0F, 200, NTechItems.enhancedCircuit.get()).unlockedBy("has_circuit", has(
            NTechItems.advancedCircuit.get())).save(consumer, ntm("${getItemName(NTechItems.enhancedCircuit.get())}_from_smelting_${getItemName(
            NTechItems.advancedCircuit.get())}"))
        ExtendedCookingRecipeBuilder(Ingredient.of(NTechItems.overclockedCircuit.get()), 0F, 200, NTechItems.advancedCircuit.get()).unlockedBy("has_circuit", has(
            NTechItems.overclockedCircuit.get())).save(consumer, ntm("${getItemName(NTechItems.advancedCircuit.get())}_from_smelting_${getItemName(
            NTechItems.overclockedCircuit.get())}"))
        ExtendedCookingRecipeBuilder(Ingredient.of(NTechItems.highPerformanceCircuit.get()), 0F, 200, NTechItems.overclockedCircuit.get()).unlockedBy("has_circuit", has(
            NTechItems.highPerformanceCircuit.get())).save(consumer, ntm("${getItemName(NTechItems.overclockedCircuit.get())}_from_smelting_${getItemName(
            NTechItems.highPerformanceCircuit.get())}"))
        ShapedRecipeBuilder.shaped(NTechItems.militaryGradeCircuitBoardTier1.get()).define('C', NTechItems.basicCircuit.get()).define('D', Tags.Items.DUSTS_REDSTONE).pattern("CDC").unlockedBy("has_circuit", has(
            NTechItems.basicCircuit.get())).save(consumer)
        ShapedRecipeBuilder.shaped(NTechItems.militaryGradeCircuitBoardTier2.get()).define('C', NTechItems.enhancedCircuit.get()).define('D', NTechTags.Items.DUSTS_QUARTZ).pattern("CDC").unlockedBy("has_circuit", has(
            NTechItems.enhancedCircuit.get())).save(consumer)
        ShapedRecipeBuilder.shaped(NTechItems.militaryGradeCircuitBoardTier3.get()).define('C', NTechItems.advancedCircuit.get()).define('D', NTechTags.Items.DUSTS_GOLD).pattern("CDC").unlockedBy("has_circuit", has(
            NTechItems.advancedCircuit.get())).save(consumer)
        ShapedRecipeBuilder.shaped(NTechItems.militaryGradeCircuitBoardTier4.get()).define('C', NTechItems.overclockedCircuit.get()).define('D', NTechTags.Items.DUSTS_LAPIS).pattern("CDC").unlockedBy("has_circuit", has(
            NTechItems.overclockedCircuit.get())).save(consumer)
        ShapedRecipeBuilder.shaped(NTechItems.militaryGradeCircuitBoardTier5.get()).define('C', NTechItems.highPerformanceCircuit.get()).define('D', NTechTags.Items.DUSTS_DIAMOND).pattern("CDC").unlockedBy("has_circuit", has(
            NTechItems.highPerformanceCircuit.get())).save(consumer)
        ShapelessRecipeBuilder.shapeless(NTechItems.basicCircuit.get(), 2).requires(NTechItems.militaryGradeCircuitBoardTier1.get()).unlockedBy("has_military_circuit", has(
            NTechItems.militaryGradeCircuitBoardTier1.get())).save(consumer, ntm("${getItemName(NTechItems.basicCircuit.get())}_from_military_grade"))
        ShapelessRecipeBuilder.shapeless(NTechItems.enhancedCircuit.get(), 2).requires(NTechItems.militaryGradeCircuitBoardTier2.get()).unlockedBy("has_military_circuit", has(
            NTechItems.militaryGradeCircuitBoardTier2.get())).save(consumer, ntm("${getItemName(NTechItems.enhancedCircuit.get())}_from_military_grade"))
        ShapelessRecipeBuilder.shapeless(NTechItems.advancedCircuit.get(), 2).requires(NTechItems.militaryGradeCircuitBoardTier3.get()).unlockedBy("has_military_circuit", has(
            NTechItems.militaryGradeCircuitBoardTier3.get())).save(consumer, ntm("${getItemName(NTechItems.advancedCircuit.get())}_from_military_grade"))
        ShapelessRecipeBuilder.shapeless(NTechItems.overclockedCircuit.get(), 2).requires(NTechItems.militaryGradeCircuitBoardTier4.get()).unlockedBy("has_military_circuit", has(
            NTechItems.militaryGradeCircuitBoardTier4.get())).save(consumer, ntm("${getItemName(NTechItems.overclockedCircuit.get())}_from_military_grade"))
        ShapelessRecipeBuilder.shapeless(NTechItems.highPerformanceCircuit.get(), 2).requires(NTechItems.militaryGradeCircuitBoardTier5.get()).unlockedBy("has_military_circuit", has(
            NTechItems.militaryGradeCircuitBoardTier5.get())).save(consumer, ntm("${getItemName(NTechItems.highPerformanceCircuit.get())}_from_military_grade"))
        //endregion
        ShapedRecipeBuilder.shaped(NTechItems.deshCompoundPlate.get()).define('P', NTechTags.Items.DUSTS_POLYMER).define('D', NTechItems.deshIngot.get()).define('S', NTechItems.highSpeedSteelIngot.get()).pattern("PDP").pattern("DSD").pattern("PDP").group(
            NTechItems.deshCompoundPlate.id.path).unlockedBy("has_${NTechItems.deshIngot.id.path}", has(NTechItems.deshIngot.get())).save(consumer, NTechItems.deshCompoundPlate.id)
        ShapedRecipeBuilder.shaped(NTechItems.heatingCoil.get()).define('T', NTechTags.Items.WIRES_TUNGSTEN).define('I', Tags.Items.INGOTS_IRON).pattern("TTT").pattern("TIT").pattern("TTT").group(
            NTechItems.heatingCoil.id.path).unlockedBy("has_${NTechItems.tungstenWire.get()}", has(NTechTags.Items.WIRES_TUNGSTEN)).save(consumer, NTechItems.heatingCoil.id)
        //region Insulator
        ShapedRecipeBuilder.shaped(NTechItems.insulator.get(), 4).define('S', Tags.Items.STRING).define('W', ItemTags.WOOL).pattern("SWS").group(getItemName(
            NTechItems.insulator.get())).unlockedBy("has_wool", has(ItemTags.WOOL)).save(consumer, ntm("insulator_from_wool"))
        ShapelessRecipeBuilder.shapeless(NTechItems.insulator.get(), 4).requires(Tags.Items.INGOTS_BRICK, 2).group(getItemName(
            NTechItems.insulator.get())).unlockedBy("has_brick", has(Tags.Items.INGOTS_BRICK)).save(consumer, ntm("insulator_from_bricks"))
        ShapelessRecipeBuilder.shapeless(NTechItems.insulator.get(), 8).requires(NTechTags.Items.INGOTS_POLYMER, 2).group(getItemName(
            NTechItems.insulator.get())).unlockedBy("has_polymer", has(NTechTags.Items.INGOTS_POLYMER)).save(consumer, ntm("insulator_from_polymer"))
        ShapelessRecipeBuilder.shapeless(NTechItems.insulator.get(), 16).requires(NTechTags.Items.INGOTS_ASBESTOS, 2).group(getItemName(
            NTechItems.insulator.get())).unlockedBy("has_yummy", has(NTechTags.Items.INGOTS_ASBESTOS)).save(consumer, ntm("insulator_from_asbestos"))
        ShapelessRecipeBuilder.shapeless(NTechItems.insulator.get(), 16).requires(NTechTags.Items.INGOTS_FIBERGLASS, 2).group(getItemName(
            NTechItems.insulator.get())).unlockedBy("has_fiberglass", has(NTechTags.Items.INGOTS_FIBERGLASS)).save(consumer, ntm("insulator_from_fiberglass"))
        //endregion
        ShapedRecipeBuilder.shaped(NTechItems.steelTank.get(), 2).define('S', NTechTags.Items.PLATES_STEEL).define('T', NTechTags.Items.PLATES_TITANIUM).pattern("STS").pattern("S S").pattern("STS").group(
            NTechItems.steelTank.id.path).unlockedBy("has_${NTechItems.steelPlate.id.path}", has(NTechTags.Items.PLATES_STEEL)).save(consumer, NTechItems.steelTank.id)
    }

    private fun machineItems(consumer: Consumer<FinishedRecipe>) {
        with (NTechTags.Items) {
            with(BatteryRecipeBuilder) {
                battery(consumer, NTechItems.battery.get(), WIRES_ALUMINIUM, PLATES_ALUMINIUM, Tags.Items.DUSTS_REDSTONE, extra = arrayOf(
                    NTechItems.redstonePowerCell.get() to arrayOf("WBW", "PBP", "WBW"), NTechItems.sixfoldRedstonePowerCell.get() to arrayOf("BBB", "WPW", "BBB"), NTechItems.twentyFourFoldRedstonePowerCell.get() to arrayOf("BWB", "WPW", "BWB")))
                battery(consumer, NTechItems.advancedBattery.get(), WIRES_RED_COPPER, PLATES_COPPER, DUSTS_SULFUR, DUSTS_LEAD, extra = arrayOf(
                    NTechItems.advancedPowerCell.get() to arrayOf("WBW", "PBP", "WBW"), NTechItems.quadrupleAdvancedPowerCell.get() to arrayOf("BWB", "WPW", "BWB"), NTechItems.twelveFoldAdvancedPowerCell.get() to arrayOf("WPW", "BBB", "WPW")))
                battery(consumer, NTechItems.lithiumBattery.get(), WIRES_GOLD, PLATES_TITANIUM, DUSTS_LITHIUM, DUSTS_COBALT, arrayOf("W W", "PXP", "PYP"), extra = arrayOf(
                    NTechItems.lithiumPowerCell.get() to arrayOf("WBW", "PBP", "WBW"), NTechItems.tripleLithiumPowerCell.get() to arrayOf("WPW", "BBB", "WPW"), NTechItems.sixfoldLithiumPowerCell.get() to arrayOf("WPW", "BWB", "WPW")))
                battery(consumer, NTechItems.schrabidiumBattery.get(), WIRES_SCHRABIDIUM, PLATES_SCHRABIDIUM, DUSTS_NEPTUNIUM, DUSTS_SCHRABIDIUM, extra = arrayOf(
                    NTechItems.schrabidiumPowerCell.get() to arrayOf("WBW", "PBP", "WBW"), NTechItems.doubleSchrabidiumPowerCell.get() to arrayOf("WPW", "BWB", "WPW"), NTechItems.quadrupleSchrabidiumPowerCell.get() to arrayOf("WPW", "BWB", "WPW")))
                battery(consumer, NTechItems.sparkBattery.get(), Ingredient.of(WIRES_MAGNETIZED_TUNGSTEN), Ingredient.of(
                    NTechItems.dineutroniumCompoundPlate.get()), Ingredient.of(NTechItems.sparkMix.get()), criterion = has(
                    NTechItems.sparkMix.get()), extra = arrayOf(NTechItems.sparkPowerCell.get() to arrayOf("BBW", "BBP", "BBW"), NTechItems.sparkArcaneCarBattery.get() to arrayOf(" WW", "PBB", "BBB")))
            }
        }

        pressStamp(Tags.Items.STONE, NTechItems.stoneFlatStamp.get(), consumer)
        pressStamp(Tags.Items.INGOTS_IRON, NTechItems.ironFlatStamp.get(), consumer)
        pressStamp(NTechTags.Items.INGOTS_STEEL, NTechItems.steelFlatStamp.get(), consumer)
        pressStamp(NTechTags.Items.INGOTS_TITANIUM, NTechItems.titaniumFlatStamp.get(), consumer)
        pressStamp(Tags.Items.OBSIDIAN, NTechItems.obsidianFlatStamp.get(), consumer)
        pressStamp(NTechTags.Items.INGOTS_SCHRABIDIUM, NTechItems.schrabidiumFlatStamp.get(), consumer)
        shredderBlade(NTechTags.Items.INGOTS_ALUMINIUM, NTechTags.Items.PLATES_ALUMINIUM, NTechItems.aluminiumShredderBlade.get(), consumer)
        shredderBlade(Tags.Items.INGOTS_GOLD, NTechTags.Items.PLATES_GOLD, NTechItems.goldShredderBlade.get(), consumer)
        shredderBlade(Tags.Items.INGOTS_IRON, NTechTags.Items.PLATES_IRON, NTechItems.ironShredderBlade.get(), consumer)
        shredderBlade(NTechTags.Items.INGOTS_STEEL, NTechTags.Items.PLATES_STEEL, NTechItems.steelShredderBlade.get(), consumer)
        shredderBlade(NTechTags.Items.INGOTS_TITANIUM, NTechTags.Items.PLATES_TITANIUM, NTechItems.titaniumShredderBlade.get(), consumer)
        shredderBlade(NTechItems.advancedAlloyIngot.get(), NTechItems.advancedAlloyPlate.get(), NTechItems.advancedAlloyShredderBlade.get(), consumer)
        shredderBlade(NTechTags.Items.INGOTS_COMBINE_STEEL, NTechTags.Items.PLATES_COMBINE_STEEL, NTechItems.combineSteelShredderBlade.get(), consumer)
        shredderBlade(NTechTags.Items.INGOTS_SCHRABIDIUM, NTechTags.Items.PLATES_SCHRABIDIUM, NTechItems.schrabidiumShredderBlade.get(), consumer)
        ShapedRecipeBuilder.shaped(NTechItems.deshShredderBlade.get()).define('B', NTechItems.combineSteelShredderBlade.get()).define('D', NTechItems.deshCompoundPlate.get()).define('S', NTechTags.Items.NUGGETS_SCHRABIDIUM).pattern("SDS").pattern("DBD").pattern("SDS").group(
            NTechItems.deshShredderBlade.id.path).unlockedBy("has_${NTechItems.deshIngot.id.path}", has(NTechItems.deshIngot.get())).save(consumer, NTechItems.deshShredderBlade.id)
    }

    private fun templates(consumer: Consumer<FinishedRecipe>) {
        ShapedRecipeBuilder.shaped(NTechItems.machineTemplateFolder.get()).define('B', Tags.Items.DYES_BLUE).define('P', Items.PAPER).define('W', Tags.Items.DYES_WHITE).pattern("BPB").pattern("WPW").pattern("BPB").group(
            NTechItems.machineTemplateFolder.id.path).unlockedBy("has_${Items.PAPER.registryName!!.path}", has(Items.PAPER)).save(consumer, NTechItems.machineTemplateFolder.id)
    }

    private fun blocks(consumer: Consumer<FinishedRecipe>) {
        ShapedRecipeBuilder.shaped(NTechBlockItems.scrapBlock.get()).define('#', NTechTags.Items.SCRAP).pattern("##").pattern("##").group(NTechBlockItems.scrapBlock.id.path).unlockedBy("has_scrap", has(NTechTags.Items.SCRAP)).save(consumer, ntm("${NTechBlockItems.scrapBlock.id.path}_from_scrap"))
        ShapelessRecipeBuilder.shapeless(NTechBlockItems.trinititeOre.get()).requires(Tags.Items.SAND_COLORLESS).requires(
            NTechItems.trinitite.get()).group(NTechBlockItems.trinititeOre.id.path).unlockedBy("has_trinitite", has(NTechItems.trinitite.get())).save(consumer, NTechBlockItems.trinititeOre.id)
        ShapelessRecipeBuilder.shapeless(NTechBlockItems.redTrinititeOre.get()).requires(Tags.Items.SAND_RED).requires(
            NTechItems.trinitite.get()).group(NTechBlockItems.trinititeOre.id.path).unlockedBy("has_trinitite", has(NTechItems.trinitite.get())).save(consumer, NTechBlockItems.redTrinititeOre.id)
    }

    private fun machines(consumer: Consumer<FinishedRecipe>) {
        ShapedRecipeBuilder.shaped(NTechBlockItems.siren.get()).define('S', NTechTags.Items.PLATES_STEEL).define('I', NTechTags.Items.PLATES_INSULATOR).define('C', NTechItems.enhancedCircuit.get()).define('R', Tags.Items.DUSTS_REDSTONE).pattern("SIS").pattern("ICI").pattern("SRS").group(NTechBlockItems.siren.id.path).unlockedBy("has_${NTechItems.enhancedCircuit.id.path}", has(
            NTechItems.enhancedCircuit.get())).save(consumer, NTechBlockItems.siren.id)
        ShapedRecipeBuilder.shaped(NTechBlockItems.steamPress.get()).define('I', Tags.Items.INGOTS_IRON).define('F', Items.FURNACE).define('P', Items.PISTON).define('B', Tags.Items.STORAGE_BLOCKS_IRON).pattern("IFI").pattern("IPI").pattern("IBI").group(NTechBlockItems.steamPress.id.path).unlockedBy("has_${Items.PISTON.registryName!!.path}", has(Items.PISTON)).save(consumer, NTechBlockItems.steamPress.id)
//        ShapedRecipeBuilder.shaped(NTechBlockItems.blastFurnace.get()).define('T', NTechTags.Items.INGOTS_TUNGSTEN).define('C', NTechItems.copperPanel.get()).define('H', Items.HOPPER).define('F', Items.FURNACE).pattern("T T").pattern("CHC").pattern("TFT").group(NTechBlockItems.blastFurnace.id.path).unlockedBy("has_${NTechItems.tungstenIngot.get()}", has(NTechTags.Items.INGOTS_TUNGSTEN)).save(consumer, NTechBlockItems.blastFurnace.id)
        ShapedRecipeBuilder.shaped(NTechBlockItems.combustionGenerator.get()).define('S', NTechTags.Items.INGOTS_STEEL).define('T', NTechItems.steelTank.get()).define('C', NTechItems.redCopperIngot.get()).define('F', Items.FURNACE).pattern("STS").pattern("SCS").pattern("SFS").group(NTechBlockItems.combustionGenerator.id.path).unlockedBy("has_${NTechItems.redCopperIngot.id.path}", has(
            NTechItems.redCopperIngot.get())).save(consumer, NTechBlockItems.combustionGenerator.id)
        ShapedRecipeBuilder.shaped(NTechBlockItems.electricFurnace.get()).define('B', NTechTags.Items.INGOTS_BERYLLIUM).define('C', NTechItems.copperPanel.get()).define('F', Items.FURNACE).define('H', NTechItems.heatingCoil.get()).pattern("BBB").pattern("CFC").pattern("HHH").group(NTechBlockItems.electricFurnace.id.path).unlockedBy("has_${NTechItems.berylliumIngot.id.path}", has(NTechTags.Items.INGOTS_BERYLLIUM)).save(consumer, NTechBlockItems.electricFurnace.id)
    }

    private fun anvilSmithing(consumer: Consumer<FinishedRecipe>) {
        ShapedRecipeBuilder.shaped(NTechBlockItems.ironAnvil.get()).define('I', Tags.Items.INGOTS_IRON).define('B', Tags.Items.STORAGE_BLOCKS_IRON).pattern("III").pattern(" B ").pattern("III").group(NTechBlockItems.ironAnvil.id.path).unlockedBy("has_iron_block", has(Tags.Items.STORAGE_BLOCKS_IRON)).save(consumer)
        ShapedRecipeBuilder.shaped(NTechBlockItems.leadAnvil.get()).define('I', NTechTags.Items.INGOTS_LEAD).define('B', NTechTags.Items.STORAGE_BLOCKS_LEAD).pattern("III").pattern(" B ").pattern("III").group(NTechBlockItems.leadAnvil.id.path).unlockedBy("has_lead_block", has(NTechTags.Items.STORAGE_BLOCKS_LEAD)).save(consumer)

        val baseAnvils = StackedIngredient.of(1, NTechBlockItems.ironAnvil.get(), NTechBlockItems.leadAnvil.get())
        AnvilSmithingRecipeBuilder(1, NTechBlockItems.steelAnvil.get()).requiresFirst(baseAnvils).requiresSecond(StackedIngredient.of(10, NTechTags.Items.INGOTS_STEEL)).unlockedBy("has_steel", has(NTechTags.Items.INGOTS_STEEL)).save(consumer)
        AnvilSmithingRecipeBuilder(1, NTechBlockItems.starmetalAnvil.get()).requiresFirst(baseAnvils).requiresSecond(StackedIngredient.of(10, NTechTags.Items.INGOTS_STARMETAL)).unlockedBy("has_starmetal", has(NTechTags.Items.INGOTS_STARMETAL)).save(consumer)
        AnvilSmithingRecipeBuilder(1, NTechBlockItems.ferrouraniumAnvil.get()).requiresFirst(baseAnvils).requiresSecond(StackedIngredient.of(10, NTechItems.u238Ingot.get())).unlockedBy("has_ferrouranium", has(
            NTechItems.u238Ingot.get())).save(consumer)
        AnvilSmithingRecipeBuilder(1, NTechBlockItems.bismuthAnvil.get()).requiresFirst(baseAnvils).requiresSecond(StackedIngredient.of(10, NTechTags.Items.INGOTS_BISMUTH)).unlockedBy("has_bismuth", has(NTechTags.Items.INGOTS_BISMUTH)).save(consumer)
        AnvilSmithingRecipeBuilder(1, NTechBlockItems.schrabidateAnvil.get()).requiresFirst(baseAnvils).requiresSecond(StackedIngredient.of(10, NTechTags.Items.INGOTS_SCHRABIDATE)).unlockedBy("has_schrabidate", has(NTechTags.Items.INGOTS_SCHRABIDATE)).save(consumer)
        AnvilSmithingRecipeBuilder(1, NTechBlockItems.dineutroniumAnvil.get()).requiresFirst(baseAnvils).requiresSecond(StackedIngredient.of(10, NTechTags.Items.INGOTS_DINEUTRONIUM)).unlockedBy("has_dineutronium", has(NTechTags.Items.INGOTS_DINEUTRONIUM)).save(consumer)
    }

    private fun anvilConstructing(consumer: Consumer<FinishedRecipe>) {
        AnvilRecipeBuilder(3).requires(Tags.Items.INGOTS_IRON).results(NTechItems.ironPlate.get()).save(consumer)
        AnvilRecipeBuilder(3).requires(Tags.Items.INGOTS_GOLD).results(NTechItems.goldPlate.get()).save(consumer)
        AnvilRecipeBuilder(3).requires(NTechTags.Items.INGOTS_TITANIUM).results(NTechItems.titaniumPlate.get()).save(consumer)
        AnvilRecipeBuilder(3).requires(NTechTags.Items.INGOTS_ALUMINIUM).results(NTechItems.aluminiumPlate.get()).save(consumer)
        AnvilRecipeBuilder(3).requires(NTechTags.Items.INGOTS_STEEL).results(NTechItems.steelPlate.get()).save(consumer)
        AnvilRecipeBuilder(3).requires(NTechTags.Items.INGOTS_LEAD).results(NTechItems.leadPlate.get()).save(consumer)
        AnvilRecipeBuilder(3).requires(Tags.Items.INGOTS_COPPER).results(NTechItems.copperPlate.get()).save(consumer)
        AnvilRecipeBuilder(3).requires(NTechItems.advancedAlloyIngot.get()).results(NTechItems.advancedAlloyPlate.get()).save(consumer)
        AnvilRecipeBuilder(3).requires(NTechItems.schrabidiumIngot.get()).results(NTechItems.schrabidiumPlate.get()).save(consumer)
        AnvilRecipeBuilder(3).requires(NTechItems.combineSteelIngot.get()).results(NTechItems.combineSteelPlate.get()).save(consumer)
        AnvilRecipeBuilder(3).requires(NTechItems.saturniteIngot.get()).results(NTechItems.saturnitePlate.get()).save(consumer)

        AnvilRecipeBuilder(4).requires(NTechTags.Items.INGOTS_ALUMINIUM).results(ItemStack(NTechItems.aluminiumWire.get(), 8)).save(consumer)
        AnvilRecipeBuilder(4).requires(Tags.Items.INGOTS_COPPER).results(ItemStack(NTechItems.copperWire.get(), 8)).save(consumer)
        AnvilRecipeBuilder(4).requires(NTechTags.Items.INGOTS_TUNGSTEN).results(ItemStack(NTechItems.tungstenWire.get(), 8)).save(consumer)
        AnvilRecipeBuilder(4).requires(NTechItems.redCopperIngot.get()).results(ItemStack(NTechItems.redCopperWire.get(), 8)).save(consumer)
        AnvilRecipeBuilder(4).requires(Tags.Items.INGOTS_GOLD).results(ItemStack(NTechItems.goldWire.get(), 8)).save(consumer)
        AnvilRecipeBuilder(4).requires(NTechItems.schrabidiumIngot.get()).results(ItemStack(NTechItems.schrabidiumWire.get(), 8)).save(consumer)
        AnvilRecipeBuilder(4).requires(NTechItems.magnetizedTungstenIngot.get()).results(ItemStack(NTechItems.highTemperatureSuperConductor.get(), 8)).save(consumer)

        AnvilRecipeBuilder().requires(NTechTags.Items.INGOTS_ALUMINIUM).results(NTechBlockItems.aluminiumDecoBlock.get()).setOverlay(AnvilRecipeType.CONSTRUCTING).save(consumer)
        AnvilRecipeBuilder().requires(NTechTags.Items.INGOTS_BERYLLIUM).results(NTechBlockItems.berylliumDecoBlock.get()).setOverlay(AnvilRecipeType.CONSTRUCTING).save(consumer)
        AnvilRecipeBuilder().requires(NTechTags.Items.INGOTS_LEAD).results(NTechBlockItems.leadDecoBlock.get()).setOverlay(AnvilRecipeType.CONSTRUCTING).save(consumer)
        AnvilRecipeBuilder().requires(NTechItems.redCopperIngot.get()).results(NTechBlockItems.redCopperDecoBlock.get()).setOverlay(AnvilRecipeType.CONSTRUCTING).save(consumer)
        AnvilRecipeBuilder().requires(NTechTags.Items.INGOTS_STEEL).results(NTechBlockItems.steelDecoBlock.get()).setOverlay(AnvilRecipeType.CONSTRUCTING).save(consumer)
        AnvilRecipeBuilder().requires(NTechTags.Items.INGOTS_TITANIUM).results(NTechBlockItems.titaniumDecoBlock.get()).setOverlay(AnvilRecipeType.CONSTRUCTING).save(consumer)
        AnvilRecipeBuilder().requires(NTechTags.Items.INGOTS_TUNGSTEN).results(NTechBlockItems.tungstenDecoBlock.get()).setOverlay(AnvilRecipeType.CONSTRUCTING).save(consumer)

        AnvilRecipeBuilder().requires(4 to NTechTags.Items.PLATES_COPPER).results(NTechItems.copperPanel.get()).save(consumer)
        AnvilRecipeBuilder().requires(2 to NTechTags.Items.PLATES_STEEL).results(NTechItems.smallSteelShell.get()).save(consumer) // TODO ?
        AnvilRecipeBuilder().requires(2 to NTechItems.copperCoil.get()).results(NTechItems.ringCoil.get()).setOverlay(AnvilRecipeType.CONSTRUCTING).save(consumer)
        AnvilRecipeBuilder().requires(2 to NTechItems.superConductingCoil.get()).results(NTechItems.superConductingRingCoil.get()).setOverlay(AnvilRecipeType.CONSTRUCTING).save(consumer)
        AnvilRecipeBuilder().requires(2 to NTechItems.goldCoil.get()).results(NTechItems.goldRingCoil.get()).setOverlay(AnvilRecipeType.CONSTRUCTING).save(consumer)

        AnvilRecipeBuilder().requires(NTechItems.copperCoil.get(), NTechItems.ringCoil.get()).requires(2 to NTechTags.Items.PLATES_IRON).results(ItemStack(
            NTechItems.motor.get(), 2)).save(consumer)

        AnvilRecipeBuilder().requires(4 to NTechTags.Items.INGOTS_TUNGSTEN, 4 to ItemTags.STONE_BRICKS, 2 to Tags.Items.INGOTS_IRON).requires(2 to NTechItems.copperPanel.get()).results(NTechBlockItems.blastFurnace.get()).save(consumer)
        AnvilRecipeBuilder(2).requires(4 to Tags.Items.GLASS_COLORLESS, 8 to NTechTags.Items.INGOTS_STEEL, 8 to Tags.Items.INGOTS_COPPER).requires(2 to NTechItems.motor.get(), 1 to NTechItems.basicCircuit.get()).results(NTechBlockItems.assemblerPlacer.get()).save(consumer)

        AnvilRecipeBuilder(3).requires(2 to NTechItems.advancedAlloyPlate.get(), 1 to NTechItems.saturnitePlate.get()).requires(NTechTags.Items.PLATES_NEUTRON_REFLECTOR).results(ItemStack(
            NTechItems.mixedPlate.get(), 4)).save(consumer)
        AnvilRecipeBuilder(3).requires(4 to NTechItems.deshIngot.get()).requires(2 to NTechTags.Items.DUSTS_POLYMER).requires(
            NTechItems.highSpeedSteelIngot.get()).results(ItemStack(NTechItems.deshCompoundPlate.get(), 4)).save(consumer)
        AnvilRecipeBuilder(7).requires(4 to NTechItems.dineutroniumIngot.get(), 2 to NTechItems.sparkMix.get(), 1 to NTechItems.deshIngot.get()).results(ItemStack(
            NTechItems.dineutroniumCompoundPlate.get(), 4)).save(consumer)

        AnvilRecipeBuilder().requires(NTechTags.Items.PLATES_COPPER).results(NTechItems.point357MagnumCasing.get()).save(consumer)
        AnvilRecipeBuilder().requires(NTechTags.Items.PLATES_COPPER).results(NTechItems.point44MagnumCasing.get()).save(consumer)
        AnvilRecipeBuilder().requires(NTechTags.Items.PLATES_COPPER).results(NTechItems.smallCaliberCasing.get()).save(consumer)
        AnvilRecipeBuilder().requires(NTechTags.Items.PLATES_COPPER).results(NTechItems.largeCaliberCasing.get()).save(consumer)
        AnvilRecipeBuilder().requires(NTechTags.Items.PLATES_COPPER).results(NTechItems.buckshotCasing.get()).save(consumer)
        AnvilRecipeBuilder().requires(NTechTags.Items.PLATES_IRON, Tags.Items.DUSTS_REDSTONE).results(NTechItems.point357MagnumPrimer.get()).save(consumer)
        AnvilRecipeBuilder().requires(NTechTags.Items.PLATES_IRON, Tags.Items.DUSTS_REDSTONE).results(NTechItems.point44MagnumPrimer.get()).save(consumer)
        AnvilRecipeBuilder().requires(NTechTags.Items.PLATES_IRON, Tags.Items.DUSTS_REDSTONE).results(NTechItems.smallCaliberPrimer.get()).save(consumer)
        AnvilRecipeBuilder().requires(NTechTags.Items.PLATES_IRON, Tags.Items.DUSTS_REDSTONE).results(NTechItems.largeCaliberPrimer.get()).save(consumer)
        AnvilRecipeBuilder().requires(NTechTags.Items.PLATES_IRON, Tags.Items.DUSTS_REDSTONE).results(NTechItems.buckshotPrimer.get()).save(consumer)

        AnvilRecipeBuilder().requires(NTechItems.basicCircuitAssembly.get()).results(NTechItems.steelPlate.get(), NTechItems.aluminiumWire.get(), Items.REDSTONE).save(consumer)
        AnvilRecipeBuilder().requires(NTechItems.basicCircuit.get()).results(NTechItems.steelPlate.get() to 1F, NTechItems.aluminiumWire.get() to .5F, Items.REDSTONE to .25F).save(consumer)
        AnvilRecipeBuilder(2).requires(NTechItems.enhancedCircuit.get()).results(NTechItems.basicCircuit.get()).results(ItemStack(
            NTechItems.copperWire.get(), 2)).results(NTechItems.copperWire.get() to .5F, NTechItems.copperWire.get() to .25F, NTechItems.quartzPowder.get() to .25F, NTechItems.copperPlate.get() to .5F).save(consumer)
        AnvilRecipeBuilder(3).requires(NTechItems.advancedCircuit.get()).results(NTechItems.enhancedCircuit.get()).results(ItemStack(
            NTechItems.redCopperWire.get(), 2)).results(NTechItems.redCopperWire.get() to .5F, NTechItems.redCopperWire.get() to .25F, NTechItems.goldPowder.get() to .25F, NTechItems.insulator.get() to .5F).save(consumer)
        AnvilRecipeBuilder(4).requires(NTechItems.overclockedCircuit.get()).results(NTechItems.advancedCircuit.get()).results(ItemStack(
            NTechItems.goldWire.get(), 2)).results(NTechItems.goldWire.get() to .5F, NTechItems.goldWire.get() to .25F, NTechItems.lapisLazuliPowder.get() to .25F, NTechItems.polymerIngot.get() to .5F).save(consumer)
        AnvilRecipeBuilder(6).requires(NTechItems.highPerformanceCircuit.get()).results(NTechItems.overclockedCircuit.get()).results(ItemStack(
            NTechItems.schrabidiumWire.get(), 2)).results(NTechItems.schrabidiumWire.get() to .5F, NTechItems.schrabidiumWire.get() to .25F, NTechItems.diamondPowder.get() to .25F, NTechItems.deshIngot.get() to .5F).save(consumer)
    }

    private fun assembly(consumer: Consumer<FinishedRecipe>) {
        AssemblyRecipeBuilder(NTechItems.ironPlate.get(), 2, 30).requires(3 to Tags.Items.INGOTS_IRON).save(consumer)
        AssemblyRecipeBuilder(NTechItems.goldPlate.get(), 2, 30).requires(3 to Tags.Items.INGOTS_GOLD).save(consumer)
        AssemblyRecipeBuilder(NTechItems.titaniumPlate.get(), 2, 30).requires(3 to NTechTags.Items.INGOTS_TITANIUM).save(consumer)
        AssemblyRecipeBuilder(NTechItems.aluminiumPlate.get(), 2, 30).requires(3 to NTechTags.Items.INGOTS_ALUMINIUM).save(consumer)
        AssemblyRecipeBuilder(NTechItems.steelPlate.get(), 2, 30).requires(3 to NTechTags.Items.INGOTS_STEEL).save(consumer)
        AssemblyRecipeBuilder(NTechItems.leadPlate.get(), 2, 30).requires(3 to NTechTags.Items.INGOTS_LEAD).save(consumer)
        AssemblyRecipeBuilder(NTechItems.copperPlate.get(), 2, 30).requires(3 to Tags.Items.INGOTS_COPPER).save(consumer)
        AssemblyRecipeBuilder(NTechItems.advancedAlloyPlate.get(), 2, 30).requires(3 to NTechItems.advancedAlloyIngot.get()).save(consumer)
        AssemblyRecipeBuilder(NTechItems.schrabidiumPlate.get(), 2, 30).requires(3 to NTechItems.schrabidiumIngot.get()).save(consumer)
        AssemblyRecipeBuilder(NTechItems.combineSteelPlate.get(), 2, 30).requires(3 to NTechItems.combineSteelIngot.get()).save(consumer)
        AssemblyRecipeBuilder(NTechItems.saturnitePlate.get(), 2, 30).requires(3 to NTechItems.saturniteIngot.get()).save(consumer)
        AssemblyRecipeBuilder(NTechItems.mixedPlate.get(), 4, 50).requires(2 to NTechItems.advancedAlloyPlate.get()).requires(NTechTags.Items.PLATES_NEUTRON_REFLECTOR).requires(
            NTechItems.saturnitePlate.get()).save(consumer)
        AssemblyRecipeBuilder(NTechItems.aluminiumWire.get(), 6, 20).requires(NTechTags.Items.INGOTS_ALUMINIUM).save(consumer)
        AssemblyRecipeBuilder(NTechItems.copperWire.get(), 6, 20).requires(Tags.Items.INGOTS_COPPER).save(consumer)
        AssemblyRecipeBuilder(NTechItems.tungstenWire.get(), 6, 20).requires(NTechTags.Items.INGOTS_TUNGSTEN).save(consumer)
        AssemblyRecipeBuilder(NTechItems.redCopperWire.get(), 6, 20).requires(NTechItems.redCopperIngot.get()).save(consumer)
        AssemblyRecipeBuilder(NTechItems.superConductor.get(), 6, 20).requires(NTechItems.advancedAlloyIngot.get()).save(consumer)
        AssemblyRecipeBuilder(NTechItems.goldWire.get(), 6, 20).requires(Tags.Items.INGOTS_GOLD).save(consumer)
        AssemblyRecipeBuilder(NTechItems.schrabidiumWire.get(), 6, 20).requires(NTechItems.schrabidiumIngot.get()).save(consumer)
        AssemblyRecipeBuilder(NTechItems.highTemperatureSuperConductor.get(), 6, 20).requires(NTechItems.magnetizedTungstenIngot.get()).save(consumer)
        AssemblyRecipeBuilder(NTechItems.hazmatCloth.get(), 4, 50).requires(4 to NTechTags.Items.DUSTS_LEAD, 8 to Tags.Items.STRING).save(consumer)
        AssemblyRecipeBuilder(NTechBlockItems.fatMan.get(), 1, 300).requires(1 to NTechItems.steelSphere.get(), 2 to NTechItems.bigSteelShell.get(), 1 to NTechItems.bigSteelGridFins.get(), 2 to NTechItems.militaryGradeCircuitBoardTier2.get()).requires(6 to NTechTags.Items.WIRES_COPPER, 6 to Tags.Items.DYES_YELLOW).save(consumer)
        AssemblyRecipeBuilder(NTechBlockItems.chemPlantPlacer.get(), 1, 200).requires(8 to NTechTags.Items.INGOTS_STEEL, 6 to NTechTags.Items.PLATES_COPPER).requires(4 to NTechItems.steelTank.get()).requires(
            NTechItems.bigSteelShell.get()).requires(3 to NTechTags.Items.COILS_TUNGSTEN).requires(2 to NTechItems.enhancedCircuit.get(), 1 to NTechItems.advancedCircuit.get()).requires(8 to NTechTags.Items.INGOTS_POLYMER).save(consumer)
    }

    private fun chemistry(consumer: Consumer<FinishedRecipe>) {
        ChemRecipeBuilder(100).requires(NTechTags.Items.YELLOWCAKE_URANIUM).requires(NTechTags.Items.DUSTS_FLUORITE, 4).requires(FluidStack(Fluids.WATER, 1000)).results(
            NTechItems.sulfur.get(), 2).results(FluidStack(NTechFluids.uraniumHexafluoride.source.get(), 1200)).save(consumer)
    }

    private fun consumables(consumer: Consumer<FinishedRecipe>) {
        ShapedRecipeBuilder.shaped(NTechItems.oilDetector.get()).define('G', NTechTags.Items.WIRES_GOLD).define('S', NTechTags.Items.PLATES_STEEL).define('C', Tags.Items.INGOTS_COPPER).define('A', NTechItems.advancedCircuit.get()).pattern("G C").pattern("GAC").pattern("SSS").group(
            NTechItems.oilDetector.id.path).unlockedBy("has_${NTechItems.advancedCircuit.id.path}", has(NTechItems.advancedCircuit.get())).save(consumer, NTechItems.oilDetector.id)
        ShapedRecipeBuilder.shaped(NTechItems.ivBag.get(), 4).define('I', NTechTags.Items.PLATES_INSULATOR).define('F', NTechTags.Items.PLATES_IRON).pattern("I").pattern("F").pattern("I").unlockedBy("has_insulator", has(NTechTags.Items.PLATES_INSULATOR)).save(consumer)
        ShapelessRecipeBuilder.shapeless(NTechItems.emptyExperienceBag.get()).requires(NTechItems.ivBag.get()).requires(
            NTechItems.enchantmentPowder.get()).unlockedBy("has_powder", has(NTechItems.enchantmentPowder.get())).save(consumer)
        ShapelessRecipeBuilder.shapeless(NTechItems.radAway.get()).requires(NTechItems.bloodBag.get()).requires(NTechTags.Items.DUSTS_COAL).requires(Tags.Items.SEEDS_PUMPKIN).unlockedBy("has_blood_bag", has(
            NTechItems.bloodBag.get())).save(consumer)
        ShapelessRecipeBuilder.shapeless(NTechItems.strongRadAway.get()).requires(NTechItems.radAway.get()).requires(NTechBlockItems.glowingMushroom.get()).unlockedBy("has_glowing_mushroom", has(NTechBlockItems.glowingMushroom.get())).save(consumer)
        // TODO elite radaway iodine
        ShapedRecipeBuilder.shaped(NTechItems.polaroid.get()).define('C', NTechTags.Items.DUSTS_COAL).define('A', NTechItems.advancedAlloyPowder.get()).define('G', NTechTags.Items.DUSTS_GOLD).define('L', NTechTags.Items.DUSTS_LAPIS).define('P', Items.PAPER).pattern(" C ").pattern("APG").pattern(" L ").unlockedBy("has_advanced_alloy", has(
            NTechItems.advancedAlloyPowder.get())).save(consumer)
    }

    private fun misc(consumer: Consumer<FinishedRecipe>) {
        ShapedRecipeBuilder.shaped(Items.TORCH, 8).define('C', NTechTags.Items.COKE).define('S', Tags.Items.RODS_WOODEN).pattern("C").pattern("S").group(Items.TORCH.registryName!!.path).unlockedBy("has_coke", has(NTechTags.Items.COKE)).save(consumer, ntm("torch_from_coke"))
    }

    // so we can also use tags when declaring a shapeless recipe with multiple required items of the same type
    private fun ShapelessRecipeBuilder.requires(itemTag: TagKey<Item>, count: Int): ShapelessRecipeBuilder {
        for (i in 0 until count) requires(itemTag)
        return this
    }

    private fun ingotFromMeteorOre(ingredient: ItemLike, result: ItemLike, experience: Float, consumer: Consumer<FinishedRecipe>) {
        ExtendedCookingRecipeBuilder(Ingredient.of(ingredient), experience, 200, result, 3).group(getItemName(result)).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, ntm("${getItemName(result)}_from_smelting_${getItemName(ingredient)}"))
        ExtendedCookingRecipeBuilder(Ingredient.of(ingredient), experience / 2, 100, result, 3, serializer = RecipeSerializer.BLASTING_RECIPE).group(getItemName(result)).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, ntm("${getItemName(result)}_from_blasting_${getItemName(ingredient)}"))
    }

    private fun ingotFromPowder(ingredient: TagKey<Item>, result: ItemLike, ingredientName: String, consumer: Consumer<FinishedRecipe>) {
        ExtendedCookingRecipeBuilder(Ingredient.of(ingredient), .1F, 200, result).group(result.asItem().registryName!!.path).unlockedBy("has_$ingredientName", has(ingredient)).save(consumer, ntm("${result.asItem().registryName!!.path}_from_powder"))
    }

    private fun ingotFromCrystals(ingredient: ItemLike, result: ItemLike, experience: Float, consumer: Consumer<FinishedRecipe>, resultCount: Int = 2) {
        ExtendedCookingRecipeBuilder(Ingredient.of(ingredient), experience, 200, result, resultCount).group(result.asItem().registryName!!.path).unlockedBy("has_${ingredient.asItem().registryName!!.path}", has(ingredient)).save(consumer, ntm("${result.asItem().registryName!!.path}_from_${ingredient.asItem().registryName!!.path}"))
    }

    private fun pressRecipe(ingredient: ItemLike, stampType: PressingRecipe.StampType, result: ItemLike, count: Int, experience: Float, consumer: Consumer<FinishedRecipe>) {
        PressRecipeBuilder(result, stampType, experience, count).requires(ingredient).unlockedBy("has_${ingredient.asItem().registryName!!.path}", has(ingredient)).save(consumer, ntm("${result.asItem().registryName!!.path}_from_pressing_${ingredient.asItem().registryName!!.path}"))
    }

    private fun pressRecipe(ingredient: TagKey<Item>, stampType: PressingRecipe.StampType, result: ItemLike, count: Int, ingredientName: String, experience: Float, consumer: Consumer<FinishedRecipe>) {
        PressRecipeBuilder(result, stampType, experience, count).requires(ingredient).unlockedBy("has_$ingredientName", has(ingredient)).save(consumer, ntm("${result.asItem().registryName!!.path}_from_pressing_$ingredientName"))
    }

    private fun flatPressRecipe(ingredient: ItemLike, result: ItemLike, experience: Float, consumer: Consumer<FinishedRecipe>) {
        pressRecipe(ingredient, PressingRecipe.StampType.FLAT, result, 1, experience, consumer)
    }

    private fun flatPressRecipe(ingredient: TagKey<Item>, result: ItemLike, ingredientName: String, experience: Float, consumer: Consumer<FinishedRecipe>) {
        pressRecipe(ingredient, PressingRecipe.StampType.FLAT, result, 1, ingredientName, experience, consumer)
    }

    private fun pressStamp(material: ItemLike, result: ItemLike, consumer: Consumer<FinishedRecipe>) {
        ShapedRecipeBuilder.shaped(result).define('R', Tags.Items.DUSTS_REDSTONE).define('B', Tags.Items.INGOTS_BRICK).define('M', material).pattern(" R ").pattern("BBB").pattern("MMM").group("press_stamp_blanks").unlockedBy("has_${material.asItem().registryName!!.path}", has(material)).save(consumer, result.asItem().registryName!!)
    }

    private fun pressStamp(material: TagKey<Item>, result: ItemLike, consumer: Consumer<FinishedRecipe>) {
        ShapedRecipeBuilder.shaped(result).define('R', Tags.Items.DUSTS_REDSTONE).define('B', Tags.Items.INGOTS_BRICK).define('M', material).pattern(" R ").pattern("BBB").pattern("MMM").group("press_stamp_blanks").unlockedBy("has_material_tag", has(material)).save(consumer, result.asItem().registryName!!)
    }

    private fun shredderBlade(primaryMaterial: ItemLike, secondaryMaterial: ItemLike, result: ItemLike, consumer: Consumer<FinishedRecipe>) {
        ShapedRecipeBuilder.shaped(result).define('#', primaryMaterial).define('+', secondaryMaterial).pattern(" + ").pattern("+#+").pattern(" + ").group(result.asItem().registryName!!.path).unlockedBy("has_${primaryMaterial.asItem().registryName!!.path}", has(primaryMaterial)).save(consumer, result.asItem().registryName!!)
        ShapedRecipeBuilder.shaped(result).define('M', secondaryMaterial).define('B', result).pattern("MBM").group(result.asItem().registryName!!.path).unlockedBy("has_${result.asItem().registryName!!.path}", has(result)).save(consumer, ntm("repairing_${result.asItem().registryName!!.path}"))
    }

    private fun shredderBlade(primaryMaterial: TagKey<Item>, secondaryMaterial: TagKey<Item>, result: ItemLike, consumer: Consumer<FinishedRecipe>) {
        ShapedRecipeBuilder.shaped(result).define('#', primaryMaterial).define('+', secondaryMaterial).pattern(" + ").pattern("+#+").pattern(" + ").group(result.asItem().registryName!!.path).unlockedBy("has_material", has(primaryMaterial)).save(consumer, result.asItem().registryName!!)
        ShapedRecipeBuilder.shaped(result).define('M', secondaryMaterial).define('B', result).pattern("MBM").group(result.asItem().registryName!!.path).unlockedBy("has_${result.asItem().registryName!!.path}", has(result)).save(consumer, ntm("repairing_${result.asItem().registryName!!.path}"))
    }

    private fun blastingRecipe(ingredient1: ItemLike, ingredient2: ItemLike, result: ItemLike, experience: Float, count: Int, consumer: Consumer<FinishedRecipe>) {
        BlastingRecipeBuilder(result, experience, count).requiresFirst(ingredient1).requiresSecond(ingredient2).unlockedBy("has_${ingredient1.asItem().registryName!!.path}", has(ingredient1)).save(consumer, ntm("${result.asItem().registryName!!.path}_from_${ingredient1.asItem().registryName!!.path}_and_${ingredient2.asItem().registryName!!.path}"))
    }

    private fun blastingRecipe(ingredient1: TagKey<Item>, ingredient2: TagKey<Item>, result: ItemLike, experience: Float, count: Int, ingredientName1: String, ingredientName2: String, consumer: Consumer<FinishedRecipe>) {
        BlastingRecipeBuilder(result, experience, count).requiresFirst(ingredient1).requiresSecond(ingredient2).unlockedBy("has_$ingredientName1", has(ingredient1)).save(consumer, ntm("${result.asItem().registryName!!.path}_from_${ingredientName1}_and_${ingredientName2}"))
    }

    private fun blastingRecipe(ingredient1: TagKey<Item>, ingredient2: ItemLike, result: ItemLike, experience: Float, count: Int, ingredientName1: String, consumer: Consumer<FinishedRecipe>) {
        BlastingRecipeBuilder(result, experience, count).requiresFirst(ingredient1).requiresSecond(ingredient2).unlockedBy("has_$ingredientName1", has(ingredient1)).save(consumer, ntm("${result.asItem().registryName!!.path}_from_${ingredientName1}_and_${ingredient2.asItem().registryName!!.path}"))
    }

    private fun blastingRecipe(ingredient1: ItemLike, ingredient2: TagKey<Item>, result: ItemLike, experience: Float, count: Int, ingredientName2: String, consumer: Consumer<FinishedRecipe>) {
        BlastingRecipeBuilder(result, experience, count).requiresFirst(ingredient1).requiresSecond(ingredient2).unlockedBy("has_${ingredient1.asItem().registryName!!.path}", has(ingredient1)).save(consumer, ntm("${result.asItem().registryName!!.path}_from_${ingredient1.asItem().registryName!!.path}_and_${ingredientName2}"))
    }

    private fun shreddingRecipe(ingredient: ItemLike, result: ItemLike, experience: Float, count: Int, consumer: Consumer<FinishedRecipe>) {
        ShreddingRecipeBuilder(result, experience, count, Ingredient.of(ingredient)).unlockedBy("has_${ingredient.asItem().registryName!!.path}", has(ingredient)).save(consumer, ntm("${result.asItem().registryName!!.path}_from_shredding_${ingredient.asItem().registryName!!.path}"))
    }

    private fun shreddingRecipe(ingredient: TagKey<Item>, result: ItemLike, experience: Float, count: Int, consumer: Consumer<FinishedRecipe>, ingredientName: String) {
        ShreddingRecipeBuilder(result, experience, count, Ingredient.of(ingredient)).unlockedBy("has_${ingredientName}", has(ingredient)).save(consumer, ntm("${result.asItem().registryName!!.path}_from_shredding_$ingredientName"))
    }

    private val experienceLookupTable = Object2FloatOpenHashMap<TagGroup>().apply { defaultReturnValue(0F) }

    private fun setExperienceYields() {
        with(TagGroups) {
            experienceLookupTable.putAll(arrayOf(
                coal to 0.1F,
                iron to 0.7F,
                gold to 1F,
                copper to 0.7F,
                redstone to 0.7F,
                emerald to 1F,
                lazuli to 0.2F,
                diamond to 1F,
                aluminium to 0.6F,
                asbestos to 0.8F,
                australium to 2.5F,
                beryllium to 0.75F,
                cobalt to 0.9F,
                daffergon to 2.5F,
                fluorite to 0.2F,
                lead to 0.6F,
                lignite to 0.1F,
                lithium to 5F,
                naturalPlutonium to 3F,
                naturalUranium to 1F,
                niter to 0.2F,
                redPhosphorus to 1F,
                reiium to 2.5F,
                schrabidium to 50F,
                starmetal to 10F,
                sulfur to 0.2F,
                thorium to 2F,
                titanium to 0.8F,
                trinitite to 0.3F,
                tungsten to 0.75F,
                unobtainium to 2.5F,
                verticium to 2.5F,
                weidanium to 2.5F,
            ))
        }

        // TODO this structure is also present inside the language providers. maybe extract it?
        val groupsThatShouldBePresent = TagGroups.getAllTagGroups().filter { it.ore != null || it.materialGroup.ore() != null }
        if (!experienceLookupTable.keys.containsAll(groupsThatShouldBePresent)) {
            val missingTagGroups = groupsThatShouldBePresent subtract experienceLookupTable.keys
            val warningMessage = StringBuilder().appendLine("Missing experience yields for following tag groups containing ores:")
            missingTagGroups.forEach(warningMessage::appendLine)
            NuclearTech.LOGGER.warn(warningMessage.toString())
        }
        if (!groupsThatShouldBePresent.containsAll(experienceLookupTable.keys)) {
            val extraTagGroups = experienceLookupTable.keys subtract groupsThatShouldBePresent.toSet()
            val warningMessage = StringBuilder().appendLine("Extra experience yields for tag groups that don't contain ores:")
            extraTagGroups.forEach(warningMessage::appendLine)
            NuclearTech.LOGGER.warn(warningMessage.toString())
        }
    }

    private fun getHasName(tag: TagKey<Item>?, item: Item?) = "has_${getItemName(tag, item)}"

    private fun getConversionRecipeName(result: Item, inputTag: TagKey<Item>?, inputItem: Item?) = "${getItemName(result)}_from_${getItemName(inputTag, inputItem)}"

    // FIXME this is kinda unnecessary since the tags aren't bound anyway
    private fun getItemName(tag: TagKey<Item>?, item: Item?) =
        tag?.let {
            val tagManager = ForgeRegistries.ITEMS.getTagManager()
            if (!tagManager.isKnownTagName(it)) return@let null
            tagManager.getTag(it).firstOrNull()?.registryName?.path
        } ?: item?.registryName?.path ?: throw IllegalStateException("Could not find an applicable name for tag '$tag' or item '$item'")

    private fun ingredientAndConditionOf(tag: TagKey<Item>?, item: Item?) =
        tag?.let { Ingredient.of(it) to InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(it).build()) } ?:
        item?.let { Ingredient.of(it) to InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(it).build()) }
}
