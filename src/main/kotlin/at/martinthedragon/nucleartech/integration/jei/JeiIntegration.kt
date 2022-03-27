package at.martinthedragon.nucleartech.integration.jei

import at.martinthedragon.nucleartech.*
import at.martinthedragon.nucleartech.integration.jei.categories.*
import at.martinthedragon.nucleartech.integration.jei.transfers.PressingRecipeTransferInfo
import at.martinthedragon.nucleartech.menus.AnvilMenu
import at.martinthedragon.nucleartech.menus.BlastFurnaceMenu
import at.martinthedragon.nucleartech.menus.ElectricFurnaceMenu
import at.martinthedragon.nucleartech.menus.PressMenu
import at.martinthedragon.nucleartech.recipes.*
import at.martinthedragon.nucleartech.recipes.anvil.AnvilConstructingRecipe
import at.martinthedragon.nucleartech.recipes.anvil.AnvilSmithingRecipe
import at.martinthedragon.nucleartech.screens.*
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.registration.*
import net.minecraft.client.Minecraft
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraftforge.registries.ForgeRegistries
import mezz.jei.api.constants.RecipeTypes as JeiRecipeTypes

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
            TemplateFolderJeiRecipeCategory(guiHelper),
            SmithingJeiRecipeCategory(guiHelper),
            ConstructingJeiRecipeCategory(guiHelper),
            PressingJeiRecipeCategory(guiHelper),
            BlastingJeiRecipeCategory(guiHelper),
            ShreddingJeiRecipeCategory(guiHelper),
        )
    }

    override fun registerVanillaCategoryExtensions(registration: IVanillaCategoryExtensionRegistration) {
        val craftingCategory = registration.craftingCategory
        craftingCategory.addCategoryExtension(BatteryRecipe::class.java, ::BatteryCraftingJeiRecipeCategory)
    }

    override fun registerRecipes(registration: IRecipeRegistration) {
        val tagManager = ForgeRegistries.ITEMS.tags() ?: throw IllegalStateException("No tag manager bound to items")
        val templateFolderOutputs = tagManager.getTag(NuclearTags.Items.MACHINE_TEMPLATE_FOLDER_RESULTS)
        if (!templateFolderOutputs.isEmpty) {
            val machineTemplateFolder = ItemStack(ModItems.machineTemplateFolder.get())
            // unnecessarily long automation logic follows, but seems to be working nonetheless
            @OptIn(ExperimentalStdlibApi::class)
            registration.addRecipes(NuclearRecipeTypes.FOLDER_RESULTS, buildList(TemplateFolderJeiRecipeCategory.TemplateFolderRecipe.TemplateType.values().size) {
                add(TemplateFolderJeiRecipeCategory.TemplateFolderRecipe(machineTemplateFolder, TemplateFolderJeiRecipeCategory.TemplateFolderRecipe.TemplateType.StoneStamp, Ingredient.of(*tagManager.getTag(NuclearTags.Items.STONE_STAMPS).toMutableList().apply { remove(ModItems.stoneFlatStamp.get()) }.toTypedArray())))
                add(TemplateFolderJeiRecipeCategory.TemplateFolderRecipe(machineTemplateFolder, TemplateFolderJeiRecipeCategory.TemplateFolderRecipe.TemplateType.IronStamp, Ingredient.of(*tagManager.getTag(NuclearTags.Items.IRON_STAMPS).toMutableList().apply { remove(ModItems.ironFlatStamp.get()) }.toTypedArray())))
                add(TemplateFolderJeiRecipeCategory.TemplateFolderRecipe(machineTemplateFolder, TemplateFolderJeiRecipeCategory.TemplateFolderRecipe.TemplateType.SteelStamp, Ingredient.of(*tagManager.getTag(NuclearTags.Items.STEEL_STAMPS).toMutableList().apply { remove(ModItems.steelFlatStamp.get()) }.toTypedArray())))
                add(TemplateFolderJeiRecipeCategory.TemplateFolderRecipe(machineTemplateFolder, TemplateFolderJeiRecipeCategory.TemplateFolderRecipe.TemplateType.TitaniumStamp, Ingredient.of(*tagManager.getTag(NuclearTags.Items.TITANIUM_STAMPS).toMutableList().apply { remove(ModItems.titaniumFlatStamp.get()) }.toTypedArray())))
                add(TemplateFolderJeiRecipeCategory.TemplateFolderRecipe(machineTemplateFolder, TemplateFolderJeiRecipeCategory.TemplateFolderRecipe.TemplateType.ObsidianStamp, Ingredient.of(*tagManager.getTag(NuclearTags.Items.OBSIDIAN_STAMPS).toMutableList().apply { remove(ModItems.obsidianFlatStamp.get()) }.toTypedArray())))
                add(TemplateFolderJeiRecipeCategory.TemplateFolderRecipe(machineTemplateFolder, TemplateFolderJeiRecipeCategory.TemplateFolderRecipe.TemplateType.SchrabidiumStamp, Ingredient.of(*tagManager.getTag(NuclearTags.Items.SCHRABIDIUM_STAMPS).toMutableList().apply { remove(ModItems.schrabidiumFlatStamp.get()) }.toTypedArray())))
                add(TemplateFolderJeiRecipeCategory.TemplateFolderRecipe(machineTemplateFolder, TemplateFolderJeiRecipeCategory.TemplateFolderRecipe.TemplateType.SirenTrack, Ingredient.of(NuclearTags.Items.SIREN_TRACKS)))
            })
        }

        val recipeMap = Minecraft.getInstance().level!!
            .recipeManager.recipes
            .filter { it.id.namespace == NuclearTech.MODID }
            .groupBy { it.type }
            .withDefault { emptyList() }
        registration.addRecipes(NuclearRecipeTypes.SMITHING, recipeMap.getValue(RecipeTypes.SMITHING).filterIsInstance<AnvilSmithingRecipe>())
        registration.addRecipes(NuclearRecipeTypes.CONSTRUCTING, recipeMap.getValue(RecipeTypes.CONSTRUCTING).filterIsInstance<AnvilConstructingRecipe>())
        registration.addRecipes(NuclearRecipeTypes.PRESSING, recipeMap.getValue(RecipeTypes.PRESSING).filterIsInstance<PressingRecipe>())
        registration.addRecipes(NuclearRecipeTypes.BLASTING, recipeMap.getValue(RecipeTypes.BLASTING).filterIsInstance<BlastingRecipe>())
        registration.addRecipes(NuclearRecipeTypes.SHREDDING, recipeMap.getValue(RecipeTypes.SHREDDING).filterIsInstance<ShreddingRecipe>())
    }

    override fun registerRecipeCatalysts(registration: IRecipeCatalystRegistration) {
        registration.addRecipeCatalyst(ItemStack(ModItems.machineTemplateFolder.get()), NuclearRecipeTypes.FOLDER_RESULTS)

        listOf(
            ModBlockItems.ironAnvil.get(), ModBlockItems.leadAnvil.get(), ModBlockItems.steelAnvil.get(),
            ModBlockItems.meteoriteAnvil.get(), ModBlockItems.starmetalAnvil.get(), ModBlockItems.ferrouraniumAnvil.get(),
            ModBlockItems.bismuthAnvil.get(), ModBlockItems.schrabidateAnvil.get(), ModBlockItems.dineutroniumAnvil.get(),
            ModBlockItems.murkyAnvil.get()
        ).forEach { registration.addRecipeCatalyst(ItemStack(it), NuclearRecipeTypes.SMITHING, NuclearRecipeTypes.CONSTRUCTING) }

        registration.addRecipeCatalyst(ItemStack(ModBlockItems.steamPress.get()), NuclearRecipeTypes.PRESSING, JeiRecipeTypes.FUELING)
        registration.addRecipeCatalyst(ItemStack(ModBlockItems.blastFurnace.get()), NuclearRecipeTypes.BLASTING, JeiRecipeTypes.FUELING)
        registration.addRecipeCatalyst(ItemStack(ModBlockItems.combustionGenerator.get()), JeiRecipeTypes.FUELING)
        registration.addRecipeCatalyst(ItemStack(ModBlockItems.electricFurnace.get()), JeiRecipeTypes.SMELTING)
        registration.addRecipeCatalyst(ItemStack(ModBlockItems.shredder.get()), NuclearRecipeTypes.SHREDDING)
    }

    override fun registerRecipeTransferHandlers(registration: IRecipeTransferRegistration) {
        registration.addRecipeTransferHandler(AnvilMenu::class.java, NuclearRecipeTypes.SMITHING, 0, 2, 3, 36)
        registration.addRecipeTransferHandler(PressingRecipeTransferInfo())
        registration.addRecipeTransferHandler(PressMenu::class.java, JeiRecipeTypes.FUELING, 2, 1, 4, 36)
        registration.addRecipeTransferHandler(BlastFurnaceMenu::class.java, NuclearRecipeTypes.BLASTING, 0, 2, 4, 36)
        registration.addRecipeTransferHandler(BlastFurnaceMenu::class.java, JeiRecipeTypes.FUELING, 2, 1, 4, 36)
        registration.addRecipeTransferHandler(ElectricFurnaceMenu::class.java, JeiRecipeTypes.SMELTING, 0, 1, 3, 36)
    }

    override fun registerGuiHandlers(registration: IGuiHandlerRegistration) {
        registration.addRecipeClickArea(AnvilScreen::class.java, 72, 28, 14, 14, NuclearRecipeTypes.SMITHING)
        registration.addRecipeClickArea(AnvilScreen::class.java, 17, 61, 33, 9, NuclearRecipeTypes.CONSTRUCTING)
        registration.addRecipeClickArea(AnvilScreen::class.java, 72, 61, 33, 9, NuclearRecipeTypes.CONSTRUCTING)
        registration.addRecipeClickArea(SteamPressScreen::class.java, 103, 34, 24, 17, NuclearRecipeTypes.PRESSING)
        registration.addRecipeClickArea(BlastFurnaceScreen::class.java, 101, 35, 24, 17, NuclearRecipeTypes.BLASTING)
        registration.addRecipeClickArea(ElectricFurnaceScreen::class.java, 79, 34, 24, 17, JeiRecipeTypes.SMELTING)
        registration.addRecipeClickArea(ShredderScreen::class.java, 43, 89, 54, 14, NuclearRecipeTypes.SHREDDING)
    }
}
