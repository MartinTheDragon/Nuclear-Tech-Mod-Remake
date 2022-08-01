package at.martinthedragon.nucleartech.integration.jei

import at.martinthedragon.nucleartech.*
import at.martinthedragon.nucleartech.integration.jei.categories.*
import at.martinthedragon.nucleartech.integration.jei.categories.TemplateFolderJRC.TemplateFolderRecipe
import at.martinthedragon.nucleartech.integration.jei.categories.TemplateFolderJRC.TemplateFolderRecipe.TemplateType
import at.martinthedragon.nucleartech.integration.jei.transfers.PressingJRTI
import at.martinthedragon.nucleartech.item.AssemblyTemplateItem
import at.martinthedragon.nucleartech.item.ChemPlantTemplateItem
import at.martinthedragon.nucleartech.item.NTechBlockItems
import at.martinthedragon.nucleartech.item.NTechItems
import at.martinthedragon.nucleartech.menu.*
import at.martinthedragon.nucleartech.recipe.*
import at.martinthedragon.nucleartech.recipe.anvil.AnvilConstructingRecipe
import at.martinthedragon.nucleartech.recipe.anvil.AnvilSmithingRecipe
import at.martinthedragon.nucleartech.screen.*
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.registration.*
import net.minecraft.client.Minecraft
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraftforge.common.Tags
import net.minecraftforge.registries.ForgeRegistries
import mezz.jei.api.constants.RecipeTypes as JeiRecipeTypes

/*
JRC = JeiRecipeCategory
JRTI = JeiRecipeTransferInfo
 */

@JeiPlugin
@Suppress("unused")
class JeiIntegration : IModPlugin {
    init {
        NuclearTech.LOGGER.info("Enabling JEI integration")
    }

    override fun getPluginUid() = ntm(NuclearTech.MODID)

    override fun registerCategories(registration: IRecipeCategoryRegistration) {
        val guiHelper = registration.jeiHelpers.guiHelper
        registration.addRecipeCategories(
            AssemblingJRC(guiHelper),
            BlastingJRC(guiHelper),
            ChemistryJRC(guiHelper),
            ConstructingJRC(guiHelper),
            PressingJRC(guiHelper),
            ShreddingJRC(guiHelper),
            SmithingJRC(guiHelper),
            TemplateFolderJRC(guiHelper),
        )
    }

    override fun registerVanillaCategoryExtensions(registration: IVanillaCategoryExtensionRegistration) {
        val craftingCategory = registration.craftingCategory
        craftingCategory.addCategoryExtension(BatteryRecipe::class.java, ::BatteryCraftingJRC)
    }

    override fun registerRecipes(registration: IRecipeRegistration) {
        val recipeManager = Minecraft.getInstance().level!!.recipeManager
        val tagManager = ForgeRegistries.ITEMS.tags() ?: throw IllegalStateException("No tag manager bound to items")
        val templateFolderOutputs = tagManager.getTag(NTechTags.Items.MACHINE_TEMPLATE_FOLDER_RESULTS)
        if (!templateFolderOutputs.isEmpty) {
            val machineTemplateFolder = ItemStack(NTechItems.machineTemplateFolder.get())
            registration.addRecipes(NuclearRecipeTypes.FOLDER_RESULTS, buildList {
                add(TemplateFolderRecipe(machineTemplateFolder, TemplateType.PressStamp, Ingredient.of(NTechTags.Items.FLAT_STAMPS), Ingredient.EMPTY, tagManager.getTag(NTechTags.Items.PLATE_STAMPS).map(::ItemStack)))
                add(TemplateFolderRecipe(machineTemplateFolder, TemplateType.PressStamp, Ingredient.of(NTechTags.Items.FLAT_STAMPS), Ingredient.EMPTY, tagManager.getTag(NTechTags.Items.WIRE_STAMPS).map(::ItemStack)))
                add(TemplateFolderRecipe(machineTemplateFolder, TemplateType.PressStamp, Ingredient.of(NTechTags.Items.FLAT_STAMPS), Ingredient.EMPTY, tagManager.getTag(NTechTags.Items.CIRCUIT_STAMPS).map(::ItemStack)))
                add(TemplateFolderRecipe(machineTemplateFolder, TemplateType.SirenTrack, Ingredient.of(NTechTags.Items.PLATES_INSULATOR), Ingredient.of(NTechTags.Items.PLATES_STEEL), tagManager.getTag(NTechTags.Items.SIREN_TRACKS).map(::ItemStack)))
                add(TemplateFolderRecipe(machineTemplateFolder, TemplateType.AssemblyTemplate, Ingredient.of(Items.PAPER), Ingredient.of(Tags.Items.DYES), AssemblyTemplateItem.getAllTemplates(recipeManager)))
                add(TemplateFolderRecipe(machineTemplateFolder, TemplateType.ChemistryTemplate, Ingredient.of(Items.PAPER), Ingredient.of(Tags.Items.DYES), ChemPlantTemplateItem.getAllChemTemplates(recipeManager)))
            })
        }

        val recipeMap = recipeManager.recipes
            .filter { it.id.namespace == NuclearTech.MODID }
            .groupBy { it.type }
            .withDefault { emptyList() }
        registration.addRecipes(NuclearRecipeTypes.ASSEMBLING, recipeMap.getValue(RecipeTypes.ASSEMBLY).filterIsInstance<AssemblyRecipe>())
        registration.addRecipes(NuclearRecipeTypes.BLASTING, recipeMap.getValue(RecipeTypes.BLASTING).filterIsInstance<BlastingRecipe>())
        registration.addRecipes(NuclearRecipeTypes.CHEMISTRY, recipeMap.getValue(RecipeTypes.CHEM).filterIsInstance<ChemRecipe>())
        registration.addRecipes(NuclearRecipeTypes.CONSTRUCTING, recipeMap.getValue(RecipeTypes.CONSTRUCTING).filterIsInstance<AnvilConstructingRecipe>())
        registration.addRecipes(NuclearRecipeTypes.PRESSING, recipeMap.getValue(RecipeTypes.PRESSING).filterIsInstance<PressingRecipe>())
        registration.addRecipes(NuclearRecipeTypes.SHREDDING, recipeMap.getValue(RecipeTypes.SHREDDING).filterIsInstance<ShreddingRecipe>())
        registration.addRecipes(NuclearRecipeTypes.SMITHING, recipeMap.getValue(RecipeTypes.SMITHING).filterIsInstance<AnvilSmithingRecipe>())
    }

    override fun registerRecipeCatalysts(registration: IRecipeCatalystRegistration) {
        registration.addRecipeCatalyst(ItemStack(NTechItems.machineTemplateFolder.get()), NuclearRecipeTypes.FOLDER_RESULTS)

        listOf(
            NTechBlockItems.ironAnvil.get(), NTechBlockItems.leadAnvil.get(), NTechBlockItems.steelAnvil.get(),
            NTechBlockItems.meteoriteAnvil.get(), NTechBlockItems.starmetalAnvil.get(), NTechBlockItems.ferrouraniumAnvil.get(),
            NTechBlockItems.bismuthAnvil.get(), NTechBlockItems.schrabidateAnvil.get(), NTechBlockItems.dineutroniumAnvil.get(),
            NTechBlockItems.murkyAnvil.get()
        ).forEach { registration.addRecipeCatalyst(ItemStack(it), NuclearRecipeTypes.SMITHING, NuclearRecipeTypes.CONSTRUCTING) }

        registration.addRecipeCatalyst(ItemStack(NTechBlockItems.assemblerPlacer.get()), NuclearRecipeTypes.ASSEMBLING)
        registration.addRecipeCatalyst(ItemStack(NTechBlockItems.blastFurnace.get()), NuclearRecipeTypes.BLASTING, JeiRecipeTypes.FUELING)
        registration.addRecipeCatalyst(ItemStack(NTechBlockItems.chemPlantPlacer.get()), NuclearRecipeTypes.CHEMISTRY)
        registration.addRecipeCatalyst(ItemStack(NTechBlockItems.steamPress.get()), NuclearRecipeTypes.PRESSING, JeiRecipeTypes.FUELING)
        registration.addRecipeCatalyst(ItemStack(NTechBlockItems.shredder.get()), NuclearRecipeTypes.SHREDDING)
        registration.addRecipeCatalyst(ItemStack(NTechBlockItems.combustionGenerator.get()), JeiRecipeTypes.FUELING)
        registration.addRecipeCatalyst(ItemStack(NTechBlockItems.electricFurnace.get()), JeiRecipeTypes.SMELTING)
    }

    override fun registerRecipeTransferHandlers(registration: IRecipeTransferRegistration) {
        registration.addRecipeTransferHandler(AnvilMenu::class.java, NuclearRecipeTypes.SMITHING, 0, 2, 3, 36)
        registration.addRecipeTransferHandler(AssemblerMenu::class.java, NuclearRecipeTypes.ASSEMBLING, 5, 12, 18, 36)
        registration.addRecipeTransferHandler(BlastFurnaceMenu::class.java, NuclearRecipeTypes.BLASTING, 0, 2, 4, 36)
        registration.addRecipeTransferHandler(BlastFurnaceMenu::class.java, JeiRecipeTypes.FUELING, 2, 1, 4, 36)
        registration.addRecipeTransferHandler(PressingJRTI())
        registration.addRecipeTransferHandler(PressMenu::class.java, JeiRecipeTypes.FUELING, 2, 1, 4, 36)
        registration.addRecipeTransferHandler(ElectricFurnaceMenu::class.java, JeiRecipeTypes.SMELTING, 0, 1, 3, 36)
    }

    override fun registerGuiHandlers(registration: IGuiHandlerRegistration) {
        registration.addRecipeClickArea(AnvilScreen::class.java, 72, 28, 14, 14, NuclearRecipeTypes.SMITHING)
        registration.addRecipeClickArea(AnvilScreen::class.java, 17, 61, 33, 9, NuclearRecipeTypes.CONSTRUCTING)
        registration.addRecipeClickArea(AnvilScreen::class.java, 72, 61, 33, 9, NuclearRecipeTypes.CONSTRUCTING)
        registration.addRecipeClickArea(AssemblerScreen::class.java, 45, 83, 82, 30, NuclearRecipeTypes.ASSEMBLING)
        registration.addRecipeClickArea(BlastFurnaceScreen::class.java, 101, 35, 24, 17, NuclearRecipeTypes.BLASTING)
        registration.addRecipeClickArea(ChemPlantScreen::class.java, 44, 88, 88, 20, NuclearRecipeTypes.CHEMISTRY)
        registration.addRecipeClickArea(ElectricFurnaceScreen::class.java, 79, 34, 24, 17, JeiRecipeTypes.SMELTING)
        registration.addRecipeClickArea(ShredderScreen::class.java, 43, 89, 54, 14, NuclearRecipeTypes.SHREDDING)
        registration.addRecipeClickArea(SteamPressScreen::class.java, 103, 34, 24, 17, NuclearRecipeTypes.PRESSING)
    }

    override fun registerItemSubtypes(registration: ISubtypeRegistration) {
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, NTechItems.assemblyTemplate.get()) { ingredient, _ -> if (ingredient.hasTag()) ingredient.tag!!.getString("recipe") else "" }
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, NTechItems.chemTemplate.get()) { ingredient, _ -> if (ingredient.hasTag()) ingredient.tag!!.getString("recipe") else "" }
    }
}
