package at.martinthedragon.nucleartech.integration.jei

import at.martinthedragon.nucleartech.ModBlockItems
import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.containers.BlastFurnaceContainer
import at.martinthedragon.nucleartech.containers.ElectricFurnaceContainer
import at.martinthedragon.nucleartech.containers.PressContainer
import at.martinthedragon.nucleartech.integration.jei.categories.*
import at.martinthedragon.nucleartech.integration.jei.transfers.PressingRecipeTransferInfo
import at.martinthedragon.nucleartech.recipes.BatteryRecipe
import at.martinthedragon.nucleartech.recipes.RecipeTypes
import at.martinthedragon.nucleartech.screens.BlastFurnaceScreen
import at.martinthedragon.nucleartech.screens.ElectricFurnaceScreen
import at.martinthedragon.nucleartech.screens.ShredderScreen
import at.martinthedragon.nucleartech.screens.SteamPressScreen
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.constants.VanillaRecipeCategoryUid
import mezz.jei.api.registration.*
import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient
import net.minecraft.tags.ItemTags
import net.minecraft.util.ResourceLocation

@JeiPlugin
@Suppress("unused")
class JeiIntegration : IModPlugin {
    init {
        NuclearTech.LOGGER.info("Enabling JEI integration")
    }

    override fun getPluginUid() = ResourceLocation(NuclearTech.MODID, NuclearTech.MODID)

    override fun registerCategories(registration: IRecipeCategoryRegistration) {
        registration.addRecipeCategories(TemplateFolderJeiRecipeCategory(registration.jeiHelpers.guiHelper))
        registration.addRecipeCategories(PressingJeiRecipeCategory(registration.jeiHelpers.guiHelper))
        registration.addRecipeCategories(BlastingJeiRecipeCategory(registration.jeiHelpers.guiHelper))
        registration.addRecipeCategories(ShreddingJeiRecipeCategory(registration.jeiHelpers.guiHelper))
    }

    override fun registerVanillaCategoryExtensions(registration: IVanillaCategoryExtensionRegistration) {
        val craftingCategory = registration.craftingCategory
        craftingCategory.addCategoryExtension(BatteryRecipe::class.java, ::BatteryCraftingJeiRecipeCategory)
    }

    override fun registerRecipes(registration: IRecipeRegistration) {
        val templateFolderOutputs = ItemTags.getAllTags().getTagOrEmpty(NuclearTags.Items.MACHINE_TEMPLATE_FOLDER_RESULTS.name).values
        if (templateFolderOutputs.isNotEmpty()) {
            val machineTemplateFolder = ItemStack(ModItems.machineTemplateFolder.get())
            // unnecessarily long automation logic follows, but seems to be working nonetheless
            @OptIn(ExperimentalStdlibApi::class)
            registration.addRecipes(buildList(TemplateFolderJeiRecipeCategory.TemplateFolderRecipe.TemplateType.values().size) {
                add(TemplateFolderJeiRecipeCategory.TemplateFolderRecipe(machineTemplateFolder, TemplateFolderJeiRecipeCategory.TemplateFolderRecipe.TemplateType.StoneStamp, Ingredient.of(*NuclearTags.Items.STONE_STAMPS.values.toMutableList().apply { remove(ModItems.stoneFlatStamp.get()) }.toTypedArray())))
                add(TemplateFolderJeiRecipeCategory.TemplateFolderRecipe(machineTemplateFolder, TemplateFolderJeiRecipeCategory.TemplateFolderRecipe.TemplateType.IronStamp, Ingredient.of(*NuclearTags.Items.IRON_STAMPS.values.toMutableList().apply { remove(ModItems.ironFlatStamp.get()) }.toTypedArray())))
                add(TemplateFolderJeiRecipeCategory.TemplateFolderRecipe(machineTemplateFolder, TemplateFolderJeiRecipeCategory.TemplateFolderRecipe.TemplateType.SteelStamp, Ingredient.of(*NuclearTags.Items.STEEL_STAMPS.values.toMutableList().apply { remove(ModItems.steelFlatStamp.get()) }.toTypedArray())))
                add(TemplateFolderJeiRecipeCategory.TemplateFolderRecipe(machineTemplateFolder, TemplateFolderJeiRecipeCategory.TemplateFolderRecipe.TemplateType.TitaniumStamp, Ingredient.of(*NuclearTags.Items.TITANIUM_STAMPS.values.toMutableList().apply { remove(ModItems.titaniumFlatStamp.get()) }.toTypedArray())))
                add(TemplateFolderJeiRecipeCategory.TemplateFolderRecipe(machineTemplateFolder, TemplateFolderJeiRecipeCategory.TemplateFolderRecipe.TemplateType.ObsidianStamp, Ingredient.of(*NuclearTags.Items.OBSIDIAN_STAMPS.values.toMutableList().apply { remove(ModItems.obsidianFlatStamp.get()) }.toTypedArray())))
                add(TemplateFolderJeiRecipeCategory.TemplateFolderRecipe(machineTemplateFolder, TemplateFolderJeiRecipeCategory.TemplateFolderRecipe.TemplateType.SchrabidiumStamp, Ingredient.of(*NuclearTags.Items.SCHRABIDIUM_STAMPS.values.toMutableList().apply { remove(ModItems.schrabidiumFlatStamp.get()) }.toTypedArray())))
                add(TemplateFolderJeiRecipeCategory.TemplateFolderRecipe(machineTemplateFolder, TemplateFolderJeiRecipeCategory.TemplateFolderRecipe.TemplateType.SirenTrack, Ingredient.of(NuclearTags.Items.SIREN_TRACKS)))
            }, TemplateFolderJeiRecipeCategory.UID)
        }

        val recipeMap = Minecraft.getInstance().level!!
            .recipeManager.recipes
            .filter { it.id.namespace == NuclearTech.MODID }
            .groupBy { it.type }
            .withDefault { emptyList() }
        registration.addRecipes(recipeMap.getValue(RecipeTypes.PRESSING), PressingJeiRecipeCategory.UID)
        registration.addRecipes(recipeMap.getValue(RecipeTypes.BLASTING), BlastingJeiRecipeCategory.UID)
        registration.addRecipes(recipeMap.getValue(RecipeTypes.SHREDDING), ShreddingJeiRecipeCategory.UID)
    }

    override fun registerRecipeCatalysts(registration: IRecipeCatalystRegistration) {
        registration.addRecipeCatalyst(ItemStack(ModItems.machineTemplateFolder.get()), TemplateFolderJeiRecipeCategory.UID)

        registration.addRecipeCatalyst(ItemStack(ModBlockItems.steamPress.get()), PressingJeiRecipeCategory.UID, VanillaRecipeCategoryUid.FUEL)
        registration.addRecipeCatalyst(ItemStack(ModBlockItems.blastFurnace.get()), BlastingJeiRecipeCategory.UID, VanillaRecipeCategoryUid.FUEL)
        registration.addRecipeCatalyst(ItemStack(ModBlockItems.combustionGenerator.get()), VanillaRecipeCategoryUid.FUEL)
        registration.addRecipeCatalyst(ItemStack(ModBlockItems.electricFurnace.get()), VanillaRecipeCategoryUid.FURNACE)
        registration.addRecipeCatalyst(ItemStack(ModBlockItems.shredder.get()), ShreddingJeiRecipeCategory.UID)
    }

    override fun registerRecipeTransferHandlers(registration: IRecipeTransferRegistration) {
        registration.addRecipeTransferHandler(PressingRecipeTransferInfo())
        registration.addRecipeTransferHandler(PressContainer::class.java, VanillaRecipeCategoryUid.FUEL, 2, 1, 4, 36)
        registration.addRecipeTransferHandler(BlastFurnaceContainer::class.java, BlastingJeiRecipeCategory.UID, 0, 2, 4, 36)
        registration.addRecipeTransferHandler(BlastFurnaceContainer::class.java, VanillaRecipeCategoryUid.FUEL, 2, 1, 4, 36)
        registration.addRecipeTransferHandler(ElectricFurnaceContainer::class.java, VanillaRecipeCategoryUid.FURNACE, 0, 1, 3, 36)
    }

    override fun registerGuiHandlers(registration: IGuiHandlerRegistration) {
        registration.addRecipeClickArea(SteamPressScreen::class.java, 103, 34, 24, 17, PressingJeiRecipeCategory.UID)
        registration.addRecipeClickArea(BlastFurnaceScreen::class.java, 101, 35, 24, 17, BlastingJeiRecipeCategory.UID)
        registration.addRecipeClickArea(ElectricFurnaceScreen::class.java, 79, 34, 24, 17, VanillaRecipeCategoryUid.FURNACE)
        registration.addRecipeClickArea(ShredderScreen::class.java, 43, 89, 54, 14, ShreddingJeiRecipeCategory.UID)
    }
}
