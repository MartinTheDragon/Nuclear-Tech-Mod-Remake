package at.martinthedragon.nucleartech.datagen

import at.martinthedragon.nucleartech.*
import at.martinthedragon.nucleartech.datagen.recipes.*
import at.martinthedragon.nucleartech.fluid.NTechFluids
import at.martinthedragon.nucleartech.recipes.PressingRecipe
import at.martinthedragon.nucleartech.recipes.RecipeSerializers
import at.martinthedragon.nucleartech.recipes.StackedIngredient
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
import at.martinthedragon.nucleartech.datagen.recipes.AnvilConstructingRecipeBuilder as AnvilRecipeBuilder
import at.martinthedragon.nucleartech.recipes.anvil.AnvilConstructingRecipe.OverlayType as AnvilRecipeType

@Suppress("SameParameterValue")
class NuclearRecipeProvider(generator: DataGenerator) : RecipeProvider(generator) {
    override fun getName(): String = "Nuclear Tech Mod Recipes"

    override fun buildCraftingRecipes(consumer: Consumer<FinishedRecipe>) {
        setExperienceYields()

        SpecialRecipeBuilder.special(RecipeSerializers.SMITHING_RENAMING.get()).save(consumer, ntm("anvil_smithing_renaming").toString())

        ShapelessRecipeBuilder.shapeless(ModItems.schrabidiumFuelIngot.get()).requires(ModItems.schrabidiumNugget.get(), 3).requires(NuclearTags.Items.NUGGETS_NEPTUNIUM, 3).requires(NuclearTags.Items.NUGGETS_BERYLLIUM, 3).group("schrabidium_fuel_ingot").unlockedBy("has_schrabidium_nugget", has(ModItems.schrabidiumNugget.get())).save(consumer, ntm("schrabidium_fuel_ingot_from_isotope_nuggets"))
        ShapelessRecipeBuilder.shapeless(ModItems.highEnrichedSchrabidiumFuelIngot.get()).requires(ModItems.schrabidiumNugget.get(), 5).requires(NuclearTags.Items.NUGGETS_NEPTUNIUM, 2).requires(NuclearTags.Items.NUGGETS_BERYLLIUM, 2).group("high_enriched_schrabidium_fuel_ingot").unlockedBy("has_schrabidium_nugget", has(ModItems.schrabidiumNugget.get())).save(consumer, ntm("high_enriched_schrabidium_fuel_ingot_from_isotope_nuggets"))
        ShapelessRecipeBuilder.shapeless(ModItems.lowEnrichedSchrabidiumFuelIngot.get()).requires(ModItems.schrabidiumNugget.get()).requires(NuclearTags.Items.NUGGETS_NEPTUNIUM, 4).requires(NuclearTags.Items.NUGGETS_BERYLLIUM, 4).group("low_enriched_schrabidium_fuel_ingot").unlockedBy("has_schrabidium_nugget", has(ModItems.schrabidiumNugget.get())).save(consumer, ntm("low_enriched_schrabidium_fuel_ingot_from_isotope_nuggets"))
        ShapelessRecipeBuilder.shapeless(ModItems.moxFuelIngot.get()).requires(ModItems.u235Nugget.get(), 3).requires(ModItems.pu238Nugget.get()).requires(ModItems.pu239Nugget.get(), 3).requires(ModItems.u238Nugget.get(), 2).group("mox_fuel_ingot").unlockedBy("has_plutonium_nugget", has(NuclearTags.Items.NUGGETS_PLUTONIUM)).save(consumer, ntm("mox_fuel_ingot_from_isotope_nuggets"))
        ShapelessRecipeBuilder.shapeless(ModItems.plutoniumFuelIngot.get()).requires(ModItems.pu238Nugget.get()).requires(ModItems.pu239Nugget.get(), 5).requires(ModItems.pu240Nugget.get(), 3).group("plutonium_fuel_ingot").unlockedBy("has_plutonium_nugget", has(NuclearTags.Items.NUGGETS_PLUTONIUM)).save(consumer, ntm("plutonium_fuel_ingot_from_isotope_nuggets"))
        ShapelessRecipeBuilder.shapeless(ModItems.thoriumFuelIngot.get()).requires(ModItems.u233Nugget.get(), 3).requires(NuclearTags.Items.NUGGETS_THORIUM, 6).group("thorium_fuel_ingot").unlockedBy("has_thorium_nugget", has(NuclearTags.Items.NUGGETS_THORIUM)).save(consumer, ntm("thorium_fuel_ingot_from_isotope_nuggets"))
        ShapelessRecipeBuilder.shapeless(ModItems.uraniumFuelIngot.get()).requires(ModItems.u238Nugget.get(), 6).requires(ModItems.u233Nugget.get(), 3).group("uranium_fuel_ingot").unlockedBy("has_u238_nugget", has(ModItems.u238Nugget.get())).save(consumer, ntm("uranium_fuel_ingot_from_isotope_nuggets_u233"))
        ShapelessRecipeBuilder.shapeless(ModItems.uraniumFuelIngot.get()).requires(ModItems.u238Nugget.get(), 6).requires(ModItems.u235Nugget.get(), 3).group("uranium_fuel_ingot").unlockedBy("has_u238_nugget", has(ModItems.u238Nugget.get())).save(consumer, ntm("uranium_fuel_ingot_from_isotope_nuggets_u235"))

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
        ingotFromMeteorOre(ModBlockItems.meteorUraniumOre.get(), ModItems.uraniumIngot.get(), 3F, consumer)
        ingotFromMeteorOre(ModBlockItems.meteorThoriumOre.get(), ModItems.th232Ingot.get(), 6f, consumer)
        ingotFromMeteorOre(ModBlockItems.meteorTitaniumOre.get(), ModItems.titaniumIngot.get(), 2.4F, consumer)
        ingotFromMeteorOre(ModBlockItems.meteorSulfurOre.get(), ModItems.sulfur.get(), 1F, consumer)
        ingotFromMeteorOre(ModBlockItems.meteorCopperOre.get(), Items.COPPER_INGOT, 1.5F, consumer)
        ingotFromMeteorOre(ModBlockItems.meteorTungstenOre.get(), ModItems.tungstenIngot.get(), 2.25F, consumer)
        ingotFromMeteorOre(ModBlockItems.meteorAluminiumOre.get(), ModItems.aluminiumIngot.get(), 1.8F, consumer)
        ingotFromMeteorOre(ModBlockItems.meteorLeadOre.get(), ModItems.leadIngot.get(), 1.8F, consumer)
        ingotFromMeteorOre(ModBlockItems.meteorLithiumOre.get(), ModItems.lithiumCube.get(), 1.8F, consumer)

        // crystals to ingots
        ingotFromCrystals(ModItems.trixiteCrystals.get(), ModItems.plutoniumIngot.get(), 3F, consumer, 4)

        ExtendedCookingRecipeBuilder(Ingredient.of(ModItems.combineScrapMetal.get()), .5F, 200, ModItems.combineSteelIngot.get()).group("combine_steel_ingot").unlockedBy("has_combine_steel_scrap_metal", has(ModItems.combineScrapMetal.get())).save(consumer, ntm("combine_steel_ingot_from_combine_steel_scrap_metal"))

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

        flatPressRecipe(NuclearTags.Items.BIOMASS, ModItems.compressedBiomass.get(), "biomass", .1F, consumer)
        flatPressRecipe(NuclearTags.Items.DUSTS_COAL, Items.COAL, "coal_powder", .2F, consumer)
        flatPressRecipe(NuclearTags.Items.DUSTS_LAPIS, Items.LAPIS_LAZULI, "lapis_powder", .2F, consumer)
        flatPressRecipe(NuclearTags.Items.DUSTS_DIAMOND, Items.DIAMOND, "diamond_powder", .2F, consumer)
        flatPressRecipe(NuclearTags.Items.DUSTS_EMERALD, Items.EMERALD, "emerald_powder", .2F, consumer)
        flatPressRecipe(NuclearTags.Items.DUSTS_QUARTZ, Items.QUARTZ, "quartz_powder", .2F, consumer)
        flatPressRecipe(NuclearTags.Items.DUSTS_LIGNITE, ModItems.ligniteBriquette.get(), "lignite_powder", .1F, consumer)
        flatPressRecipe(ModItems.denseCoalCluster.get(), Items.DIAMOND, 2F, consumer)

        pressRecipe(ModItems.basicCircuitAssembly.get(), PressingRecipe.StampType.CIRCUIT, ModItems.basicCircuit.get(), 1, .75F, consumer)
    }

    private fun blastFurnaceRecipes(consumer: Consumer<FinishedRecipe>) {
        blastingRecipe(Tags.Items.INGOTS_IRON, Items.COAL, ModItems.steelIngot.get(), .25F, 2, "iron_ingot", consumer)
        blastingRecipe(Tags.Items.INGOTS_COPPER, Tags.Items.DUSTS_REDSTONE, ModItems.redCopperIngot.get(), .25F, 2, "copper_ingot", "redstone", consumer)
        blastingRecipe(NuclearTags.Items.INGOTS_RED_COPPER, NuclearTags.Items.INGOTS_STEEL, ModItems.advancedAlloyIngot.get(), .5F, 2, "red_copper_ingot", "steel_ingot",consumer)
        blastingRecipe(NuclearTags.Items.INGOTS_TUNGSTEN, Items.COAL, ModItems.neutronReflector.get(), .5F, 2, "tungsten_ingot", consumer)
        blastingRecipe(NuclearTags.Items.INGOTS_LEAD, Tags.Items.INGOTS_COPPER, ModItems.neutronReflector.get(), .25F, 4, "lead_ingot", "copper_ingot", consumer)
        blastingRecipe(NuclearTags.Items.PLATES_LEAD, NuclearTags.Items.PLATES_COPPER, ModItems.neutronReflector.get(), .5F, 1, "lead_plate", "copper_plate", consumer)
        blastingRecipe(NuclearTags.Items.INGOTS_STEEL, NuclearTags.Items.INGOTS_TUNGSTEN, ModItems.highSpeedSteelIngot.get(), .5F, 2, "steel_ingot", "tungsten_ingot", consumer)
        blastingRecipe(NuclearTags.Items.INGOTS_STEEL, NuclearTags.Items.INGOTS_COBALT, ModItems.highSpeedSteelIngot.get(), 1F, 2, "steel_ingot", "cobalt_ingot", consumer)
        blastingRecipe(ModItems.mixedPlate.get(), NuclearTags.Items.PLATES_GOLD, ModItems.paAAlloyPlate.get(), 2F, 2, "gold_plate", consumer)
        blastingRecipe(NuclearTags.Items.INGOTS_TUNGSTEN, NuclearTags.Items.NUGGETS_SCHRABIDIUM, ModItems.magnetizedTungstenIngot.get(), 2F, 1, "tungsten_ingot", "schrabidium_nugget", consumer)
        blastingRecipe(NuclearTags.Items.INGOTS_SATURNITE, NuclearTags.Items.DUSTS_METEORITE, ModItems.starmetalIngot.get(), 2F, 2, "saturnite_ingot", "meteorite_dust", consumer)
    }

    private fun shreddingRecipes(consumer: Consumer<FinishedRecipe>) {
        shreddingRecipe(Tags.Items.DUSTS, ModItems.dust.get(), .1F, 1, consumer, "any_dust")
        shreddingRecipe(Tags.Items.SAND, ModItems.dust.get(), .1F, 2, consumer, "sand")
        shreddingRecipe(NuclearTags.Items.SCRAP, ModItems.dust.get(), .25F, 1, consumer, "scrap")

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
        shreddingRecipe(Tags.Items.ORES_DIAMOND, ModItems.diamondPowder.get(), .5F, 2, consumer, "diamond_ore") // TODO diamond gravel
        shreddingRecipe(ModBlockItems.rareEarthOre.get(), ModItems.deshMix.get(), 1.5F, 1, consumer)
        shreddingRecipe(NuclearTags.Items.ORES_STARMETAL, ModItems.highSpeedSteelPowder.get(), 2F, 4, consumer, "starmetal_ore")
        shreddingRecipe(ModBlockItems.trixite.get(), ModItems.plutoniumPowder.get(), 1.5F, 4, consumer)
        shreddingRecipe(ModBlockItems.meteorLithiumOre.get(), ModItems.lithiumPowder.get(), 1F, 2, consumer)

        // crystals to powder
        shreddingRecipe(ModItems.rareEarthCrystals.get(), ModItems.deshMix.get(), 1F, 2, consumer)
        shreddingRecipe(NuclearTags.Items.CRYSTALS_STARMETAL, ModItems.highSpeedSteelPowder.get(), 3F, 9, consumer, "starmetal_crystals")
        shreddingRecipe(ModItems.trixiteCrystals.get(), ModItems.plutoniumPowder.get(), 1.5F, 9, consumer)

        // blocks to powder
        shreddingRecipe(NuclearTags.Items.STORAGE_BLOCKS_STARMETAL, ModItems.highSpeedSteelPowder.get(), 20F, 36, consumer, "starmetal_block")
        shreddingRecipe(Items.GLOWSTONE, Items.GLOWSTONE_DUST, 0F, 4, consumer)

        // ingots to powder
        shreddingRecipe(NuclearTags.Items.INGOTS_STARMETAL, ModItems.highSpeedSteelPowder.get(), 1F, 1, consumer, "starmetal_ingot")

        // fragments to powder
        shreddingRecipe(NuclearTags.Items.GEMS_NEODYMIUM, ModItems.tinyNeodymiumPowder.get(), 0F, 1, consumer, "neodymium_fragment")
        shreddingRecipe(NuclearTags.Items.GEMS_COBALT, ModItems.tinyCobaltPowder.get(), 0F, 1, consumer, "cobalt_fragment")
        shreddingRecipe(NuclearTags.Items.GEMS_NIOBIUM, ModItems.tinyNiobiumPowder.get(), 0F, 1, consumer, "niobium_fragment")
        shreddingRecipe(NuclearTags.Items.GEMS_CERIUM, ModItems.tinyCeriumPowder.get(), 0F, 1, consumer, "cerium_fragment")
        shreddingRecipe(NuclearTags.Items.GEMS_LANTHANUM, ModItems.tinyLanthanumPowder.get(), 0F, 1, consumer, "lanthanum_fragment")
        shreddingRecipe(NuclearTags.Items.GEMS_ACTINIUM, ModItems.tinyActiniumPowder.get(), 0F, 1, consumer, "actinium_fragment")
        shreddingRecipe(ModItems.meteoriteFragment.get(), ModItems.tinyMeteoritePowder.get(), 0F, 1, consumer)

        // coils to powder
        shreddingRecipe(ModItems.copperCoil.get(), ModItems.redCopperPowder.get(), .1F, 1, consumer)
        shreddingRecipe(ModItems.superConductingCoil.get(), ModItems.advancedAlloyPowder.get(), .1F, 1, consumer)
        shreddingRecipe(ModItems.goldCoil.get(), ModItems.goldPowder.get(), .1F, 1, consumer)
        shreddingRecipe(ModItems.ringCoil.get(), ModItems.redCopperPowder.get(), .1F, 2, consumer)
        shreddingRecipe(ModItems.superConductingRingCoil.get(), ModItems.advancedAlloyPowder.get(), .1F, 2, consumer)
        shreddingRecipe(ModItems.goldRingCoil.get(), ModItems.goldPowder.get(), .1F, 2, consumer)
        shreddingRecipe(ModItems.heatingCoil.get(), ModItems.tungstenPowder.get(), .1F, 1, consumer)

        // quartz to powder
        shreddingRecipe(Tags.Items.STORAGE_BLOCKS_QUARTZ, ModItems.quartzPowder.get(), 0F, 4, consumer, "quartz_block")
        shreddingRecipe(Items.QUARTZ_BRICKS, ModItems.quartzPowder.get(), 0F, 4, consumer)
        shreddingRecipe(Items.QUARTZ_PILLAR, ModItems.quartzPowder.get(), 0F, 4, consumer)
        shreddingRecipe(Items.QUARTZ_STAIRS, ModItems.quartzPowder.get(), 0F, 4, consumer)
        shreddingRecipe(Items.CHISELED_QUARTZ_BLOCK, ModItems.quartzPowder.get(), 0F, 4, consumer)
        shreddingRecipe(Items.QUARTZ_SLAB, ModItems.quartzPowder.get(), 0F, 2, consumer)
        shreddingRecipe(Items.SMOOTH_QUARTZ, ModItems.quartzPowder.get(), 0F, 4, consumer)
        shreddingRecipe(Items.SMOOTH_QUARTZ_STAIRS, ModItems.quartzPowder.get(), 0F, 3, consumer)
        shreddingRecipe(Items.SMOOTH_QUARTZ_SLAB, ModItems.quartzPowder.get(), 0F, 2, consumer)
        shreddingRecipe(Tags.Items.ORES_QUARTZ, ModItems.quartzPowder.get(), 0F, 2, consumer, "quartz_ore")
        shreddingRecipe(Tags.Items.GEMS_QUARTZ, ModItems.quartzPowder.get(), 0F, 1, consumer, "quartz")

        shreddingRecipe(ModBlockItems.emptyOilDeposit.get(), Items.GRAVEL, .05F, 1, consumer)
        shreddingRecipe(Tags.Items.STONE, Items.GRAVEL, .0F, 1, consumer, "stone")
        shreddingRecipe(Tags.Items.COBBLESTONE, Items.COBBLESTONE, 0F, 1, consumer, "cobblestone")
        shreddingRecipe(ItemTags.STONE_BRICKS, Items.GRAVEL, .1F, 1, consumer, "stone_bricks")
        shreddingRecipe(Tags.Items.GRAVEL, Items.SAND, 0F, 1, consumer, "gravel")
        shreddingRecipe(Items.ANVIL, ModItems.ironPowder.get(), 0F, 31, consumer)
        shreddingRecipe(Items.CLAY, Items.CLAY_BALL, 0F, 4, consumer)
        shreddingRecipe(Items.BRICK, Items.CLAY_BALL, 0F, 1, consumer)
        shreddingRecipe(Items.BRICKS, Items.CLAY_BALL, 0F, 4, consumer)
        shreddingRecipe(Items.BRICK_STAIRS, Items.CLAY_BALL, 0F, 3, consumer)
        shreddingRecipe(Items.BRICK_SLAB, Items.CLAY_BALL, 0F, 2, consumer)
        shreddingRecipe(ItemTags.WOOL, Items.STRING, 0F, 4, consumer, "wool")
        shreddingRecipe(Items.FLOWER_POT, Items.CLAY_BALL, 0F, 3, consumer)
        shreddingRecipe(Items.ENCHANTED_BOOK, ModItems.enchantmentPowder.get(), 2F, 1, consumer)
        shreddingRecipe(Items.PACKED_ICE, ModItems.cryoPowder.get(), .1F, 1, consumer)
        shreddingRecipe(Items.BLUE_ICE, ModItems.cryoPowder.get(), .1F, 2, consumer)
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
        ingotFromPowder(NuclearTags.Items.DUSTS_COAL, ModItems.coke.get(), "coal_powder", consumer)
        ExtendedCookingRecipeBuilder(Ingredient.of(ModItems.ligniteBriquette.get()), .1F, 200, ModItems.coke.get()).group(ModItems.coke.id.path).unlockedBy("has_${ModItems.ligniteBriquette.id.path}", has(ModItems.ligniteBriquette.get())).save(consumer, ModItems.coke.id)
        ShapedRecipeBuilder.shaped(ModItems.copperPanel.get()).define('C', NuclearTags.Items.PLATES_COPPER).pattern("CCC").pattern("CCC").group(ModItems.copperPanel.id.path).unlockedBy("has${ModItems.copperPlate.id.path}", has(NuclearTags.Items.PLATES_COPPER)).save(consumer, ModItems.copperPanel.id)
        //region Circuits
        ShapedRecipeBuilder.shaped(ModItems.basicCircuitAssembly.get()).define('W', NuclearTags.Items.WIRES_ALUMINIUM).define('R', Tags.Items.DUSTS_REDSTONE).define('P', NuclearTags.Items.PLATES_STEEL).pattern("W").pattern("R").pattern("P").unlockedBy(getHasName(Items.REDSTONE), has(Tags.Items.DUSTS_REDSTONE)).save(consumer)
        AssemblyRecipeBuilder(ModItems.enhancedCircuit.get(), 1, 100).requires(ModItems.basicCircuit.get()).requires(4 to NuclearTags.Items.WIRES_COPPER, 1 to NuclearTags.Items.DUSTS_QUARTZ, 1 to NuclearTags.Items.PLATES_COPPER).save(consumer)
        AssemblyRecipeBuilder(ModItems.advancedCircuit.get(), 1, 150).requires(ModItems.enhancedCircuit.get()).requires(4 to NuclearTags.Items.WIRES_RED_COPPER, 1 to NuclearTags.Items.DUSTS_GOLD, 1 to NuclearTags.Items.PLATES_INSULATOR).save(consumer)
        ExtendedCookingRecipeBuilder(Ingredient.of(ModItems.enhancedCircuit.get()), 0F, 200, ModItems.basicCircuit.get()).unlockedBy("has_circuit", has(ModItems.enhancedCircuit.get())).save(consumer, ntm("${getItemName(ModItems.basicCircuit.get())}_from_smelting_${getItemName(ModItems.enhancedCircuit.get())}"))
        ExtendedCookingRecipeBuilder(Ingredient.of(ModItems.advancedCircuit.get()), 0F, 200, ModItems.enhancedCircuit.get()).unlockedBy("has_circuit", has(ModItems.advancedCircuit.get())).save(consumer, ntm("${getItemName(ModItems.enhancedCircuit.get())}_from_smelting_${getItemName(ModItems.advancedCircuit.get())}"))
        ExtendedCookingRecipeBuilder(Ingredient.of(ModItems.overclockedCircuit.get()), 0F, 200, ModItems.advancedCircuit.get()).unlockedBy("has_circuit", has(ModItems.overclockedCircuit.get())).save(consumer, ntm("${getItemName(ModItems.advancedCircuit.get())}_from_smelting_${getItemName(ModItems.overclockedCircuit.get())}"))
        ExtendedCookingRecipeBuilder(Ingredient.of(ModItems.highPerformanceCircuit.get()), 0F, 200, ModItems.overclockedCircuit.get()).unlockedBy("has_circuit", has(ModItems.highPerformanceCircuit.get())).save(consumer, ntm("${getItemName(ModItems.overclockedCircuit.get())}_from_smelting_${getItemName(ModItems.highPerformanceCircuit.get())}"))
        ShapedRecipeBuilder.shaped(ModItems.militaryGradeCircuitBoardTier1.get()).define('C', ModItems.basicCircuit.get()).define('D', Tags.Items.DUSTS_REDSTONE).pattern("CDC").unlockedBy("has_circuit", has(ModItems.basicCircuit.get())).save(consumer)
        ShapedRecipeBuilder.shaped(ModItems.militaryGradeCircuitBoardTier2.get()).define('C', ModItems.enhancedCircuit.get()).define('D', NuclearTags.Items.DUSTS_QUARTZ).pattern("CDC").unlockedBy("has_circuit", has(ModItems.enhancedCircuit.get())).save(consumer)
        ShapedRecipeBuilder.shaped(ModItems.militaryGradeCircuitBoardTier3.get()).define('C', ModItems.advancedCircuit.get()).define('D', NuclearTags.Items.DUSTS_GOLD).pattern("CDC").unlockedBy("has_circuit", has(ModItems.advancedCircuit.get())).save(consumer)
        ShapedRecipeBuilder.shaped(ModItems.militaryGradeCircuitBoardTier4.get()).define('C', ModItems.overclockedCircuit.get()).define('D', NuclearTags.Items.DUSTS_LAPIS).pattern("CDC").unlockedBy("has_circuit", has(ModItems.overclockedCircuit.get())).save(consumer)
        ShapedRecipeBuilder.shaped(ModItems.militaryGradeCircuitBoardTier5.get()).define('C', ModItems.highPerformanceCircuit.get()).define('D', NuclearTags.Items.DUSTS_DIAMOND).pattern("CDC").unlockedBy("has_circuit", has(ModItems.highPerformanceCircuit.get())).save(consumer)
        ShapelessRecipeBuilder.shapeless(ModItems.basicCircuit.get(), 2).requires(ModItems.militaryGradeCircuitBoardTier1.get()).unlockedBy("has_military_circuit", has(ModItems.militaryGradeCircuitBoardTier1.get())).save(consumer, ntm("${getItemName(ModItems.basicCircuit.get())}_from_military_grade"))
        ShapelessRecipeBuilder.shapeless(ModItems.enhancedCircuit.get(), 2).requires(ModItems.militaryGradeCircuitBoardTier2.get()).unlockedBy("has_military_circuit", has(ModItems.militaryGradeCircuitBoardTier2.get())).save(consumer, ntm("${getItemName(ModItems.enhancedCircuit.get())}_from_military_grade"))
        ShapelessRecipeBuilder.shapeless(ModItems.advancedCircuit.get(), 2).requires(ModItems.militaryGradeCircuitBoardTier3.get()).unlockedBy("has_military_circuit", has(ModItems.militaryGradeCircuitBoardTier3.get())).save(consumer, ntm("${getItemName(ModItems.advancedCircuit.get())}_from_military_grade"))
        ShapelessRecipeBuilder.shapeless(ModItems.overclockedCircuit.get(), 2).requires(ModItems.militaryGradeCircuitBoardTier4.get()).unlockedBy("has_military_circuit", has(ModItems.militaryGradeCircuitBoardTier4.get())).save(consumer, ntm("${getItemName(ModItems.overclockedCircuit.get())}_from_military_grade"))
        ShapelessRecipeBuilder.shapeless(ModItems.highPerformanceCircuit.get(), 2).requires(ModItems.militaryGradeCircuitBoardTier5.get()).unlockedBy("has_military_circuit", has(ModItems.militaryGradeCircuitBoardTier5.get())).save(consumer, ntm("${getItemName(ModItems.highPerformanceCircuit.get())}_from_military_grade"))
        //endregion
        ShapedRecipeBuilder.shaped(ModItems.deshCompoundPlate.get()).define('P', NuclearTags.Items.DUSTS_POLYMER).define('D', ModItems.deshIngot.get()).define('S', ModItems.highSpeedSteelIngot.get()).pattern("PDP").pattern("DSD").pattern("PDP").group(ModItems.deshCompoundPlate.id.path).unlockedBy("has_${ModItems.deshIngot.id.path}", has(ModItems.deshIngot.get())).save(consumer, ModItems.deshCompoundPlate.id)
        ShapedRecipeBuilder.shaped(ModItems.heatingCoil.get()).define('T', NuclearTags.Items.WIRES_TUNGSTEN).define('I', Tags.Items.INGOTS_IRON).pattern("TTT").pattern("TIT").pattern("TTT").group(ModItems.heatingCoil.id.path).unlockedBy("has_${ModItems.tungstenWire.get()}", has(NuclearTags.Items.WIRES_TUNGSTEN)).save(consumer, ModItems.heatingCoil.id)
        //region Insulator
        ShapedRecipeBuilder.shaped(ModItems.insulator.get(), 4).define('S', Tags.Items.STRING).define('W', ItemTags.WOOL).pattern("SWS").group(getItemName(ModItems.insulator.get())).unlockedBy("has_wool", has(ItemTags.WOOL)).save(consumer, ntm("insulator_from_wool"))
        ShapelessRecipeBuilder.shapeless(ModItems.insulator.get(), 4).requires(Tags.Items.INGOTS_BRICK, 2).group(getItemName(ModItems.insulator.get())).unlockedBy("has_brick", has(Tags.Items.INGOTS_BRICK)).save(consumer, ntm("insulator_from_bricks"))
        ShapelessRecipeBuilder.shapeless(ModItems.insulator.get(), 8).requires(NuclearTags.Items.INGOTS_POLYMER, 2).group(getItemName(ModItems.insulator.get())).unlockedBy("has_polymer", has(NuclearTags.Items.INGOTS_POLYMER)).save(consumer, ntm("insulator_from_polymer"))
        ShapelessRecipeBuilder.shapeless(ModItems.insulator.get(), 16).requires(NuclearTags.Items.INGOTS_ASBESTOS, 2).group(getItemName(ModItems.insulator.get())).unlockedBy("has_yummy", has(NuclearTags.Items.INGOTS_ASBESTOS)).save(consumer, ntm("insulator_from_asbestos"))
        ShapelessRecipeBuilder.shapeless(ModItems.insulator.get(), 16).requires(NuclearTags.Items.INGOTS_FIBERGLASS, 2).group(getItemName(ModItems.insulator.get())).unlockedBy("has_fiberglass", has(NuclearTags.Items.INGOTS_FIBERGLASS)).save(consumer, ntm("insulator_from_fiberglass"))
        //endregion
        ShapedRecipeBuilder.shaped(ModItems.steelTank.get(), 2).define('S', NuclearTags.Items.PLATES_STEEL).define('T', NuclearTags.Items.PLATES_TITANIUM).pattern("STS").pattern("S S").pattern("STS").group(ModItems.steelTank.id.path).unlockedBy("has_${ModItems.steelPlate.id.path}", has(NuclearTags.Items.PLATES_STEEL)).save(consumer, ModItems.steelTank.id)
    }

    private fun machineItems(consumer: Consumer<FinishedRecipe>) {
        with (NuclearTags.Items) {
            with(BatteryRecipeBuilder) {
                battery(consumer, ModItems.battery.get(), WIRES_ALUMINIUM, PLATES_ALUMINIUM, Tags.Items.DUSTS_REDSTONE, extra = arrayOf(ModItems.redstonePowerCell.get() to arrayOf("WBW", "PBP", "WBW"), ModItems.sixfoldRedstonePowerCell.get() to arrayOf("BBB", "WPW", "BBB"), ModItems.twentyFourFoldRedstonePowerCell.get() to arrayOf("BWB", "WPW", "BWB")))
                battery(consumer, ModItems.advancedBattery.get(), WIRES_RED_COPPER, PLATES_COPPER, DUSTS_SULFUR, DUSTS_LEAD, extra = arrayOf(ModItems.advancedPowerCell.get() to arrayOf("WBW", "PBP", "WBW"), ModItems.quadrupleAdvancedPowerCell.get() to arrayOf("BWB", "WPW", "BWB"), ModItems.twelveFoldAdvancedPowerCell.get() to arrayOf("WPW", "BBB", "WPW")))
                battery(consumer, ModItems.lithiumBattery.get(), WIRES_GOLD, PLATES_TITANIUM, DUSTS_LITHIUM, DUSTS_COBALT, arrayOf("W W", "PXP", "PYP"), extra = arrayOf(ModItems.lithiumPowerCell.get() to arrayOf("WBW", "PBP", "WBW"), ModItems.tripleLithiumPowerCell.get() to arrayOf("WPW", "BBB", "WPW"), ModItems.sixfoldLithiumPowerCell.get() to arrayOf("WPW", "BWB", "WPW")))
                battery(consumer, ModItems.schrabidiumBattery.get(), WIRES_SCHRABIDIUM, PLATES_SCHRABIDIUM, DUSTS_NEPTUNIUM, DUSTS_SCHRABIDIUM, extra = arrayOf(ModItems.schrabidiumPowerCell.get() to arrayOf("WBW", "PBP", "WBW"), ModItems.doubleSchrabidiumPowerCell.get() to arrayOf("WPW", "BWB", "WPW"), ModItems.quadrupleSchrabidiumPowerCell.get() to arrayOf("WPW", "BWB", "WPW")))
                battery(consumer, ModItems.sparkBattery.get(), Ingredient.of(WIRES_MAGNETIZED_TUNGSTEN), Ingredient.of(ModItems.dineutroniumCompoundPlate.get()), Ingredient.of(ModItems.sparkMix.get()), criterion = has(ModItems.sparkMix.get()), extra = arrayOf(ModItems.sparkPowerCell.get() to arrayOf("BBW", "BBP", "BBW"), ModItems.sparkArcaneCarBattery.get() to arrayOf(" WW", "PBB", "BBB")))
            }
        }

        pressStamp(Tags.Items.STONE, ModItems.stoneFlatStamp.get(), consumer)
        pressStamp(Tags.Items.INGOTS_IRON, ModItems.ironFlatStamp.get(), consumer)
        pressStamp(NuclearTags.Items.INGOTS_STEEL, ModItems.steelFlatStamp.get(), consumer)
        pressStamp(NuclearTags.Items.INGOTS_TITANIUM, ModItems.titaniumFlatStamp.get(), consumer)
        pressStamp(Tags.Items.OBSIDIAN, ModItems.obsidianFlatStamp.get(), consumer)
        pressStamp(NuclearTags.Items.INGOTS_SCHRABIDIUM, ModItems.schrabidiumFlatStamp.get(), consumer)
        shredderBlade(NuclearTags.Items.INGOTS_ALUMINIUM, NuclearTags.Items.PLATES_ALUMINIUM, ModItems.aluminiumShredderBlade.get(), consumer)
        shredderBlade(Tags.Items.INGOTS_GOLD, NuclearTags.Items.PLATES_GOLD, ModItems.goldShredderBlade.get(), consumer)
        shredderBlade(Tags.Items.INGOTS_IRON, NuclearTags.Items.PLATES_IRON, ModItems.ironShredderBlade.get(), consumer)
        shredderBlade(NuclearTags.Items.INGOTS_STEEL, NuclearTags.Items.PLATES_STEEL, ModItems.steelShredderBlade.get(), consumer)
        shredderBlade(NuclearTags.Items.INGOTS_TITANIUM, NuclearTags.Items.PLATES_TITANIUM, ModItems.titaniumShredderBlade.get(), consumer)
        shredderBlade(ModItems.advancedAlloyIngot.get(), ModItems.advancedAlloyPlate.get(), ModItems.advancedAlloyShredderBlade.get(), consumer)
        shredderBlade(NuclearTags.Items.INGOTS_COMBINE_STEEL, NuclearTags.Items.PLATES_COMBINE_STEEL, ModItems.combineSteelShredderBlade.get(), consumer)
        shredderBlade(NuclearTags.Items.INGOTS_SCHRABIDIUM, NuclearTags.Items.PLATES_SCHRABIDIUM, ModItems.schrabidiumShredderBlade.get(), consumer)
        ShapedRecipeBuilder.shaped(ModItems.deshShredderBlade.get()).define('B', ModItems.combineSteelShredderBlade.get()).define('D', ModItems.deshCompoundPlate.get()).define('S', NuclearTags.Items.NUGGETS_SCHRABIDIUM).pattern("SDS").pattern("DBD").pattern("SDS").group(ModItems.deshShredderBlade.id.path).unlockedBy("has_${ModItems.deshIngot.id.path}", has(ModItems.deshIngot.get())).save(consumer, ModItems.deshShredderBlade.id)
    }

    private fun templates(consumer: Consumer<FinishedRecipe>) {
        ShapedRecipeBuilder.shaped(ModItems.machineTemplateFolder.get()).define('B', Tags.Items.DYES_BLUE).define('P', Items.PAPER).define('W', Tags.Items.DYES_WHITE).pattern("BPB").pattern("WPW").pattern("BPB").group(ModItems.machineTemplateFolder.id.path).unlockedBy("has_${Items.PAPER.registryName!!.path}", has(Items.PAPER)).save(consumer, ModItems.machineTemplateFolder.id)
    }

    private fun blocks(consumer: Consumer<FinishedRecipe>) {
        ShapedRecipeBuilder.shaped(ModBlockItems.scrapBlock.get()).define('#', NuclearTags.Items.SCRAP).pattern("##").pattern("##").group(ModBlockItems.scrapBlock.id.path).unlockedBy("has_scrap", has(NuclearTags.Items.SCRAP)).save(consumer, ntm("${ModBlockItems.scrapBlock.id.path}_from_scrap"))
        ShapelessRecipeBuilder.shapeless(ModBlockItems.trinititeOre.get()).requires(Tags.Items.SAND_COLORLESS).requires(ModItems.trinitite.get()).group(ModBlockItems.trinititeOre.id.path).unlockedBy("has_trinitite", has(ModItems.trinitite.get())).save(consumer, ModBlockItems.trinititeOre.id)
        ShapelessRecipeBuilder.shapeless(ModBlockItems.redTrinititeOre.get()).requires(Tags.Items.SAND_RED).requires(ModItems.trinitite.get()).group(ModBlockItems.trinititeOre.id.path).unlockedBy("has_trinitite", has(ModItems.trinitite.get())).save(consumer, ModBlockItems.redTrinititeOre.id)
    }

    private fun machines(consumer: Consumer<FinishedRecipe>) {
        ShapedRecipeBuilder.shaped(ModBlockItems.siren.get()).define('S', NuclearTags.Items.PLATES_STEEL).define('I', NuclearTags.Items.PLATES_INSULATOR).define('C', ModItems.enhancedCircuit.get()).define('R', Tags.Items.DUSTS_REDSTONE).pattern("SIS").pattern("ICI").pattern("SRS").group(ModBlockItems.siren.id.path).unlockedBy("has_${ModItems.enhancedCircuit.id.path}", has(ModItems.enhancedCircuit.get())).save(consumer, ModBlockItems.siren.id)
        ShapedRecipeBuilder.shaped(ModBlockItems.steamPress.get()).define('I', Tags.Items.INGOTS_IRON).define('F', Items.FURNACE).define('P', Items.PISTON).define('B', Tags.Items.STORAGE_BLOCKS_IRON).pattern("IFI").pattern("IPI").pattern("IBI").group(ModBlockItems.steamPress.id.path).unlockedBy("has_${Items.PISTON.registryName!!.path}", has(Items.PISTON)).save(consumer, ModBlockItems.steamPress.id)
//        ShapedRecipeBuilder.shaped(ModBlockItems.blastFurnace.get()).define('T', NuclearTags.Items.INGOTS_TUNGSTEN).define('C', ModItems.copperPanel.get()).define('H', Items.HOPPER).define('F', Items.FURNACE).pattern("T T").pattern("CHC").pattern("TFT").group(ModBlockItems.blastFurnace.id.path).unlockedBy("has_${ModItems.tungstenIngot.get()}", has(NuclearTags.Items.INGOTS_TUNGSTEN)).save(consumer, ModBlockItems.blastFurnace.id)
        ShapedRecipeBuilder.shaped(ModBlockItems.combustionGenerator.get()).define('S', NuclearTags.Items.INGOTS_STEEL).define('T', ModItems.steelTank.get()).define('C', ModItems.redCopperIngot.get()).define('F', Items.FURNACE).pattern("STS").pattern("SCS").pattern("SFS").group(ModBlockItems.combustionGenerator.id.path).unlockedBy("has_${ModItems.redCopperIngot.id.path}", has(ModItems.redCopperIngot.get())).save(consumer, ModBlockItems.combustionGenerator.id)
        ShapedRecipeBuilder.shaped(ModBlockItems.electricFurnace.get()).define('B', NuclearTags.Items.INGOTS_BERYLLIUM).define('C', ModItems.copperPanel.get()).define('F', Items.FURNACE).define('H', ModItems.heatingCoil.get()).pattern("BBB").pattern("CFC").pattern("HHH").group(ModBlockItems.electricFurnace.id.path).unlockedBy("has_${ModItems.berylliumIngot.id.path}", has(NuclearTags.Items.INGOTS_BERYLLIUM)).save(consumer, ModBlockItems.electricFurnace.id)
    }

    private fun anvilSmithing(consumer: Consumer<FinishedRecipe>) {
        ShapedRecipeBuilder.shaped(ModBlockItems.ironAnvil.get()).define('I', Tags.Items.INGOTS_IRON).define('B', Tags.Items.STORAGE_BLOCKS_IRON).pattern("III").pattern(" B ").pattern("III").group(ModBlockItems.ironAnvil.id.path).unlockedBy("has_iron_block", has(Tags.Items.STORAGE_BLOCKS_IRON)).save(consumer)
        ShapedRecipeBuilder.shaped(ModBlockItems.leadAnvil.get()).define('I', NuclearTags.Items.INGOTS_LEAD).define('B', NuclearTags.Items.STORAGE_BLOCKS_LEAD).pattern("III").pattern(" B ").pattern("III").group(ModBlockItems.leadAnvil.id.path).unlockedBy("has_lead_block", has(NuclearTags.Items.STORAGE_BLOCKS_LEAD)).save(consumer)

        val baseAnvils = StackedIngredient.of(1, ModBlockItems.ironAnvil.get(), ModBlockItems.leadAnvil.get())
        AnvilSmithingRecipeBuilder(1, ModBlockItems.steelAnvil.get()).requiresFirst(baseAnvils).requiresSecond(StackedIngredient.of(10, NuclearTags.Items.INGOTS_STEEL)).unlockedBy("has_steel", has(NuclearTags.Items.INGOTS_STEEL)).save(consumer)
        AnvilSmithingRecipeBuilder(1, ModBlockItems.starmetalAnvil.get()).requiresFirst(baseAnvils).requiresSecond(StackedIngredient.of(10, NuclearTags.Items.INGOTS_STARMETAL)).unlockedBy("has_starmetal", has(NuclearTags.Items.INGOTS_STARMETAL)).save(consumer)
        AnvilSmithingRecipeBuilder(1, ModBlockItems.ferrouraniumAnvil.get()).requiresFirst(baseAnvils).requiresSecond(StackedIngredient.of(10, ModItems.u238Ingot.get())).unlockedBy("has_ferrouranium", has(ModItems.u238Ingot.get())).save(consumer)
        AnvilSmithingRecipeBuilder(1, ModBlockItems.bismuthAnvil.get()).requiresFirst(baseAnvils).requiresSecond(StackedIngredient.of(10, NuclearTags.Items.INGOTS_BISMUTH)).unlockedBy("has_bismuth", has(NuclearTags.Items.INGOTS_BISMUTH)).save(consumer)
        AnvilSmithingRecipeBuilder(1, ModBlockItems.schrabidateAnvil.get()).requiresFirst(baseAnvils).requiresSecond(StackedIngredient.of(10, NuclearTags.Items.INGOTS_SCHRABIDATE)).unlockedBy("has_schrabidate", has(NuclearTags.Items.INGOTS_SCHRABIDATE)).save(consumer)
        AnvilSmithingRecipeBuilder(1, ModBlockItems.dineutroniumAnvil.get()).requiresFirst(baseAnvils).requiresSecond(StackedIngredient.of(10, NuclearTags.Items.INGOTS_DINEUTRONIUM)).unlockedBy("has_dineutronium", has(NuclearTags.Items.INGOTS_DINEUTRONIUM)).save(consumer)
    }

    private fun anvilConstructing(consumer: Consumer<FinishedRecipe>) {
        AnvilRecipeBuilder(3).requires(Tags.Items.INGOTS_IRON).results(ModItems.ironPlate.get()).save(consumer)
        AnvilRecipeBuilder(3).requires(Tags.Items.INGOTS_GOLD).results(ModItems.goldPlate.get()).save(consumer)
        AnvilRecipeBuilder(3).requires(NuclearTags.Items.INGOTS_TITANIUM).results(ModItems.titaniumPlate.get()).save(consumer)
        AnvilRecipeBuilder(3).requires(NuclearTags.Items.INGOTS_ALUMINIUM).results(ModItems.aluminiumPlate.get()).save(consumer)
        AnvilRecipeBuilder(3).requires(NuclearTags.Items.INGOTS_STEEL).results(ModItems.steelPlate.get()).save(consumer)
        AnvilRecipeBuilder(3).requires(NuclearTags.Items.INGOTS_LEAD).results(ModItems.leadPlate.get()).save(consumer)
        AnvilRecipeBuilder(3).requires(Tags.Items.INGOTS_COPPER).results(ModItems.copperPlate.get()).save(consumer)
        AnvilRecipeBuilder(3).requires(ModItems.advancedAlloyIngot.get()).results(ModItems.advancedAlloyPlate.get()).save(consumer)
        AnvilRecipeBuilder(3).requires(ModItems.schrabidiumIngot.get()).results(ModItems.schrabidiumPlate.get()).save(consumer)
        AnvilRecipeBuilder(3).requires(ModItems.combineSteelIngot.get()).results(ModItems.combineSteelPlate.get()).save(consumer)
        AnvilRecipeBuilder(3).requires(ModItems.saturniteIngot.get()).results(ModItems.saturnitePlate.get()).save(consumer)

        AnvilRecipeBuilder(4).requires(NuclearTags.Items.INGOTS_ALUMINIUM).results(ItemStack(ModItems.aluminiumWire.get(), 8)).save(consumer)
        AnvilRecipeBuilder(4).requires(Tags.Items.INGOTS_COPPER).results(ItemStack(ModItems.copperWire.get(), 8)).save(consumer)
        AnvilRecipeBuilder(4).requires(NuclearTags.Items.INGOTS_TUNGSTEN).results(ItemStack(ModItems.tungstenWire.get(), 8)).save(consumer)
        AnvilRecipeBuilder(4).requires(ModItems.redCopperIngot.get()).results(ItemStack(ModItems.redCopperWire.get(), 8)).save(consumer)
        AnvilRecipeBuilder(4).requires(Tags.Items.INGOTS_GOLD).results(ItemStack(ModItems.goldWire.get(), 8)).save(consumer)
        AnvilRecipeBuilder(4).requires(ModItems.schrabidiumIngot.get()).results(ItemStack(ModItems.schrabidiumWire.get(), 8)).save(consumer)
        AnvilRecipeBuilder(4).requires(ModItems.magnetizedTungstenIngot.get()).results(ItemStack(ModItems.highTemperatureSuperConductor.get(), 8)).save(consumer)

        AnvilRecipeBuilder().requires(NuclearTags.Items.INGOTS_ALUMINIUM).results(ModBlockItems.aluminiumDecoBlock.get()).setOverlay(AnvilRecipeType.CONSTRUCTING).save(consumer)
        AnvilRecipeBuilder().requires(NuclearTags.Items.INGOTS_BERYLLIUM).results(ModBlockItems.berylliumDecoBlock.get()).setOverlay(AnvilRecipeType.CONSTRUCTING).save(consumer)
        AnvilRecipeBuilder().requires(NuclearTags.Items.INGOTS_LEAD).results(ModBlockItems.leadDecoBlock.get()).setOverlay(AnvilRecipeType.CONSTRUCTING).save(consumer)
        AnvilRecipeBuilder().requires(ModItems.redCopperIngot.get()).results(ModBlockItems.redCopperDecoBlock.get()).setOverlay(AnvilRecipeType.CONSTRUCTING).save(consumer)
        AnvilRecipeBuilder().requires(NuclearTags.Items.INGOTS_STEEL).results(ModBlockItems.steelDecoBlock.get()).setOverlay(AnvilRecipeType.CONSTRUCTING).save(consumer)
        AnvilRecipeBuilder().requires(NuclearTags.Items.INGOTS_TITANIUM).results(ModBlockItems.titaniumDecoBlock.get()).setOverlay(AnvilRecipeType.CONSTRUCTING).save(consumer)
        AnvilRecipeBuilder().requires(NuclearTags.Items.INGOTS_TUNGSTEN).results(ModBlockItems.tungstenDecoBlock.get()).setOverlay(AnvilRecipeType.CONSTRUCTING).save(consumer)

        AnvilRecipeBuilder().requires(4 to NuclearTags.Items.PLATES_COPPER).results(ModItems.copperPanel.get()).save(consumer)
        AnvilRecipeBuilder().requires(2 to NuclearTags.Items.PLATES_STEEL).results(ModItems.smallSteelShell.get()).save(consumer) // TODO ?
        AnvilRecipeBuilder().requires(2 to ModItems.copperCoil.get()).results(ModItems.ringCoil.get()).setOverlay(AnvilRecipeType.CONSTRUCTING).save(consumer)
        AnvilRecipeBuilder().requires(2 to ModItems.superConductingCoil.get()).results(ModItems.superConductingRingCoil.get()).setOverlay(AnvilRecipeType.CONSTRUCTING).save(consumer)
        AnvilRecipeBuilder().requires(2 to ModItems.goldCoil.get()).results(ModItems.goldRingCoil.get()).setOverlay(AnvilRecipeType.CONSTRUCTING).save(consumer)

        AnvilRecipeBuilder().requires(ModItems.copperCoil.get(), ModItems.ringCoil.get()).requires(2 to NuclearTags.Items.PLATES_IRON).results(ItemStack(ModItems.motor.get(), 2)).save(consumer)

        AnvilRecipeBuilder().requires(4 to NuclearTags.Items.INGOTS_TUNGSTEN, 4 to ItemTags.STONE_BRICKS, 2 to Tags.Items.INGOTS_IRON).requires(2 to ModItems.copperPanel.get()).results(ModBlockItems.blastFurnace.get()).save(consumer)
        AnvilRecipeBuilder(2).requires(4 to Tags.Items.GLASS_COLORLESS, 8 to NuclearTags.Items.INGOTS_STEEL, 8 to Tags.Items.INGOTS_COPPER).requires(2 to ModItems.motor.get(), 1 to ModItems.basicCircuit.get()).results(ModBlockItems.assemblerPlacer.get()).save(consumer)

        AnvilRecipeBuilder(3).requires(2 to ModItems.advancedAlloyPlate.get(), 1 to ModItems.saturnitePlate.get()).requires(NuclearTags.Items.PLATES_NEUTRON_REFLECTOR).results(ItemStack(ModItems.mixedPlate.get(), 4)).save(consumer)
        AnvilRecipeBuilder(3).requires(4 to ModItems.deshIngot.get()).requires(2 to NuclearTags.Items.DUSTS_POLYMER).requires(ModItems.highSpeedSteelIngot.get()).results(ItemStack(ModItems.deshCompoundPlate.get(), 4)).save(consumer)
        AnvilRecipeBuilder(7).requires(4 to ModItems.dineutroniumIngot.get(), 2 to ModItems.sparkMix.get(), 1 to ModItems.deshIngot.get()).results(ItemStack(ModItems.dineutroniumCompoundPlate.get(), 4)).save(consumer)

        AnvilRecipeBuilder().requires(NuclearTags.Items.PLATES_COPPER).results(ModItems.point357MagnumCasing.get()).save(consumer)
        AnvilRecipeBuilder().requires(NuclearTags.Items.PLATES_COPPER).results(ModItems.point44MagnumCasing.get()).save(consumer)
        AnvilRecipeBuilder().requires(NuclearTags.Items.PLATES_COPPER).results(ModItems.smallCaliberCasing.get()).save(consumer)
        AnvilRecipeBuilder().requires(NuclearTags.Items.PLATES_COPPER).results(ModItems.largeCaliberCasing.get()).save(consumer)
        AnvilRecipeBuilder().requires(NuclearTags.Items.PLATES_COPPER).results(ModItems.buckshotCasing.get()).save(consumer)
        AnvilRecipeBuilder().requires(NuclearTags.Items.PLATES_IRON, Tags.Items.DUSTS_REDSTONE).results(ModItems.point357MagnumPrimer.get()).save(consumer)
        AnvilRecipeBuilder().requires(NuclearTags.Items.PLATES_IRON, Tags.Items.DUSTS_REDSTONE).results(ModItems.point44MagnumPrimer.get()).save(consumer)
        AnvilRecipeBuilder().requires(NuclearTags.Items.PLATES_IRON, Tags.Items.DUSTS_REDSTONE).results(ModItems.smallCaliberPrimer.get()).save(consumer)
        AnvilRecipeBuilder().requires(NuclearTags.Items.PLATES_IRON, Tags.Items.DUSTS_REDSTONE).results(ModItems.largeCaliberPrimer.get()).save(consumer)
        AnvilRecipeBuilder().requires(NuclearTags.Items.PLATES_IRON, Tags.Items.DUSTS_REDSTONE).results(ModItems.buckshotPrimer.get()).save(consumer)

        AnvilRecipeBuilder().requires(ModItems.basicCircuitAssembly.get()).results(ModItems.steelPlate.get(), ModItems.aluminiumWire.get(), Items.REDSTONE).save(consumer)
        AnvilRecipeBuilder().requires(ModItems.basicCircuit.get()).results(ModItems.steelPlate.get() to 1F, ModItems.aluminiumWire.get() to .5F, Items.REDSTONE to .25F).save(consumer)
        AnvilRecipeBuilder(2).requires(ModItems.enhancedCircuit.get()).results(ModItems.basicCircuit.get()).results(ItemStack(ModItems.copperWire.get(), 2)).results(ModItems.copperWire.get() to .5F, ModItems.copperWire.get() to .25F, ModItems.quartzPowder.get() to .25F, ModItems.copperPlate.get() to .5F).save(consumer)
        AnvilRecipeBuilder(3).requires(ModItems.advancedCircuit.get()).results(ModItems.enhancedCircuit.get()).results(ItemStack(ModItems.redCopperWire.get(), 2)).results(ModItems.redCopperWire.get() to .5F, ModItems.redCopperWire.get() to .25F, ModItems.goldPowder.get() to .25F, ModItems.insulator.get() to .5F).save(consumer)
        AnvilRecipeBuilder(4).requires(ModItems.overclockedCircuit.get()).results(ModItems.advancedCircuit.get()).results(ItemStack(ModItems.goldWire.get(), 2)).results(ModItems.goldWire.get() to .5F, ModItems.goldWire.get() to .25F, ModItems.lapisLazuliPowder.get() to .25F, ModItems.polymerIngot.get() to .5F).save(consumer)
        AnvilRecipeBuilder(6).requires(ModItems.highPerformanceCircuit.get()).results(ModItems.overclockedCircuit.get()).results(ItemStack(ModItems.schrabidiumWire.get(), 2)).results(ModItems.schrabidiumWire.get() to .5F, ModItems.schrabidiumWire.get() to .25F, ModItems.diamondPowder.get() to .25F, ModItems.deshIngot.get() to .5F).save(consumer)
    }

    private fun assembly(consumer: Consumer<FinishedRecipe>) {
        AssemblyRecipeBuilder(ModItems.ironPlate.get(), 2, 30).requires(3 to Tags.Items.INGOTS_IRON).save(consumer)
        AssemblyRecipeBuilder(ModItems.goldPlate.get(), 2, 30).requires(3 to Tags.Items.INGOTS_GOLD).save(consumer)
        AssemblyRecipeBuilder(ModItems.titaniumPlate.get(), 2, 30).requires(3 to NuclearTags.Items.INGOTS_TITANIUM).save(consumer)
        AssemblyRecipeBuilder(ModItems.aluminiumPlate.get(), 2, 30).requires(3 to NuclearTags.Items.INGOTS_ALUMINIUM).save(consumer)
        AssemblyRecipeBuilder(ModItems.steelPlate.get(), 2, 30).requires(3 to NuclearTags.Items.INGOTS_STEEL).save(consumer)
        AssemblyRecipeBuilder(ModItems.leadPlate.get(), 2, 30).requires(3 to NuclearTags.Items.INGOTS_LEAD).save(consumer)
        AssemblyRecipeBuilder(ModItems.copperPlate.get(), 2, 30).requires(3 to Tags.Items.INGOTS_COPPER).save(consumer)
        AssemblyRecipeBuilder(ModItems.advancedAlloyPlate.get(), 2, 30).requires(3 to ModItems.advancedAlloyIngot.get()).save(consumer)
        AssemblyRecipeBuilder(ModItems.schrabidiumPlate.get(), 2, 30).requires(3 to ModItems.schrabidiumIngot.get()).save(consumer)
        AssemblyRecipeBuilder(ModItems.combineSteelPlate.get(), 2, 30).requires(3 to ModItems.combineSteelIngot.get()).save(consumer)
        AssemblyRecipeBuilder(ModItems.saturnitePlate.get(), 2, 30).requires(3 to ModItems.saturniteIngot.get()).save(consumer)
        AssemblyRecipeBuilder(ModItems.mixedPlate.get(), 4, 50).requires(2 to ModItems.advancedAlloyPlate.get()).requires(NuclearTags.Items.PLATES_NEUTRON_REFLECTOR).requires(ModItems.saturnitePlate.get()).save(consumer)
        AssemblyRecipeBuilder(ModItems.aluminiumWire.get(), 6, 20).requires(NuclearTags.Items.INGOTS_ALUMINIUM).save(consumer)
        AssemblyRecipeBuilder(ModItems.copperWire.get(), 6, 20).requires(Tags.Items.INGOTS_COPPER).save(consumer)
        AssemblyRecipeBuilder(ModItems.tungstenWire.get(), 6, 20).requires(NuclearTags.Items.INGOTS_TUNGSTEN).save(consumer)
        AssemblyRecipeBuilder(ModItems.redCopperWire.get(), 6, 20).requires(ModItems.redCopperIngot.get()).save(consumer)
        AssemblyRecipeBuilder(ModItems.superConductor.get(), 6, 20).requires(ModItems.advancedAlloyIngot.get()).save(consumer)
        AssemblyRecipeBuilder(ModItems.goldWire.get(), 6, 20).requires(Tags.Items.INGOTS_GOLD).save(consumer)
        AssemblyRecipeBuilder(ModItems.schrabidiumWire.get(), 6, 20).requires(ModItems.schrabidiumIngot.get()).save(consumer)
        AssemblyRecipeBuilder(ModItems.highTemperatureSuperConductor.get(), 6, 20).requires(ModItems.magnetizedTungstenIngot.get()).save(consumer)
        AssemblyRecipeBuilder(ModItems.hazmatCloth.get(), 4, 50).requires(4 to NuclearTags.Items.DUSTS_LEAD, 8 to Tags.Items.STRING).save(consumer)
        AssemblyRecipeBuilder(ModBlockItems.fatMan.get(), 1, 300).requires(1 to ModItems.steelSphere.get(), 2 to ModItems.bigSteelShell.get(), 1 to ModItems.bigSteelGridFins.get(), 2 to ModItems.militaryGradeCircuitBoardTier2.get()).requires(6 to NuclearTags.Items.WIRES_COPPER, 6 to Tags.Items.DYES_YELLOW).save(consumer)
        AssemblyRecipeBuilder(ModBlockItems.chemPlantPlacer.get(), 1, 200).requires(8 to NuclearTags.Items.INGOTS_STEEL, 6 to NuclearTags.Items.PLATES_COPPER).requires(4 to ModItems.steelTank.get()).requires(ModItems.bigSteelShell.get()).requires(3 to NuclearTags.Items.COILS_TUNGSTEN).requires(2 to ModItems.enhancedCircuit.get(), 1 to ModItems.advancedCircuit.get()).requires(8 to NuclearTags.Items.INGOTS_POLYMER).save(consumer)
    }

    private fun chemistry(consumer: Consumer<FinishedRecipe>) {
        ChemRecipeBuilder(100).requires(NuclearTags.Items.YELLOWCAKE_URANIUM).requires(NuclearTags.Items.DUSTS_FLUORITE, 4).requires(FluidStack(Fluids.WATER, 1000)).results(ModItems.sulfur.get(), 2).results(FluidStack(NTechFluids.uraniumHexafluoride.source.get(), 1200)).save(consumer)
    }

    private fun consumables(consumer: Consumer<FinishedRecipe>) {
        ShapedRecipeBuilder.shaped(ModItems.oilDetector.get()).define('G', NuclearTags.Items.WIRES_GOLD).define('S', NuclearTags.Items.PLATES_STEEL).define('C', Tags.Items.INGOTS_COPPER).define('A', ModItems.advancedCircuit.get()).pattern("G C").pattern("GAC").pattern("SSS").group(ModItems.oilDetector.id.path).unlockedBy("has_${ModItems.advancedCircuit.id.path}", has(ModItems.advancedCircuit.get())).save(consumer, ModItems.oilDetector.id)
        ShapedRecipeBuilder.shaped(ModItems.ivBag.get(), 4).define('I', NuclearTags.Items.PLATES_INSULATOR).define('F', NuclearTags.Items.PLATES_IRON).pattern("I").pattern("F").pattern("I").unlockedBy("has_insulator", has(NuclearTags.Items.PLATES_INSULATOR)).save(consumer)
        ShapelessRecipeBuilder.shapeless(ModItems.emptyExperienceBag.get()).requires(ModItems.ivBag.get()).requires(ModItems.enchantmentPowder.get()).unlockedBy("has_powder", has(ModItems.enchantmentPowder.get())).save(consumer)
        ShapelessRecipeBuilder.shapeless(ModItems.radAway.get()).requires(ModItems.bloodBag.get()).requires(NuclearTags.Items.DUSTS_COAL).requires(Tags.Items.SEEDS_PUMPKIN).unlockedBy("has_blood_bag", has(ModItems.bloodBag.get())).save(consumer)
        ShapelessRecipeBuilder.shapeless(ModItems.strongRadAway.get()).requires(ModItems.radAway.get()).requires(ModBlockItems.glowingMushroom.get()).unlockedBy("has_glowing_mushroom", has(ModBlockItems.glowingMushroom.get())).save(consumer)
        // TODO elite radaway iodine
        ShapedRecipeBuilder.shaped(ModItems.polaroid.get()).define('C', NuclearTags.Items.DUSTS_COAL).define('A', ModItems.advancedAlloyPowder.get()).define('G', NuclearTags.Items.DUSTS_GOLD).define('L', NuclearTags.Items.DUSTS_LAPIS).define('P', Items.PAPER).pattern(" C ").pattern("APG").pattern(" L ").unlockedBy("has_advanced_alloy", has(ModItems.advancedAlloyPowder.get())).save(consumer)
    }

    private fun misc(consumer: Consumer<FinishedRecipe>) {
        ShapedRecipeBuilder.shaped(Items.TORCH, 8).define('C', NuclearTags.Items.COKE).define('S', Tags.Items.RODS_WOODEN).pattern("C").pattern("S").group(Items.TORCH.registryName!!.path).unlockedBy("has_coke", has(NuclearTags.Items.COKE)).save(consumer, ntm("torch_from_coke"))
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
