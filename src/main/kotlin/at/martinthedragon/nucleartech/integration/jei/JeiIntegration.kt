package at.martinthedragon.nucleartech.integration.jei

import at.martinthedragon.nucleartech.ModBlockItems
import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.containers.BlastFurnaceContainer
import at.martinthedragon.nucleartech.containers.PressContainer
import at.martinthedragon.nucleartech.integration.jei.categories.BlastingJeiRecipeCategory
import at.martinthedragon.nucleartech.integration.jei.categories.PressingJeiRecipeCategory
import at.martinthedragon.nucleartech.integration.jei.transfers.PressingRecipeTransferInfo
import at.martinthedragon.nucleartech.recipes.RecipeTypes
import at.martinthedragon.nucleartech.screens.BlastFurnaceScreen
import at.martinthedragon.nucleartech.screens.SteamPressScreen
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.constants.VanillaRecipeCategoryUid
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.registration.*
import net.minecraft.client.Minecraft
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tags.ItemTags
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TranslationTextComponent

@JeiPlugin
@Suppress("unused")
class JeiIntegration : IModPlugin {
    init {
        NuclearTech.LOGGER.info("Enabling JEI integration")
    }

    override fun getPluginUid() = ResourceLocation(NuclearTech.MODID, NuclearTech.MODID)

    override fun registerCategories(registration: IRecipeCategoryRegistration) {
        registration.addRecipeCategories(PressingJeiRecipeCategory(registration.jeiHelpers.guiHelper))
        registration.addRecipeCategories(BlastingJeiRecipeCategory(registration.jeiHelpers.guiHelper))
    }

    override fun registerRecipes(registration: IRecipeRegistration) {
        val recipeMap = Minecraft.getInstance().level!!
            .recipeManager.recipes
            .filter { it.id.namespace == NuclearTech.MODID }
            .groupBy { it.type }
            .withDefault { emptyList() }
        registration.addRecipes(recipeMap.getValue(RecipeTypes.PRESSING), PressingJeiRecipeCategory.UID)
        registration.addRecipes(recipeMap.getValue(RecipeTypes.BLASTING), BlastingJeiRecipeCategory.UID)

        val templateFolderResults = ItemTags.getAllTags().getTagOrEmpty(NuclearTags.Items.MACHINE_TEMPLATE_FOLDER_RESULTS.name).values.map(Item::getDefaultInstance)
        if (templateFolderResults.isNotEmpty()) // empty list throws exception and disables plugin
            registration.addIngredientInfo(
                templateFolderResults,
                VanillaTypes.ITEM,
                TranslationTextComponent("jei.${NuclearTech.MODID}.info.items.machine_template_folder_results")
            )
    }

    override fun registerRecipeCatalysts(registration: IRecipeCatalystRegistration) {
        registration.addRecipeCatalyst(ItemStack(ModBlockItems.steamPress.get()),
            PressingJeiRecipeCategory.UID,
            VanillaRecipeCategoryUid.FUEL
        )
        registration.addRecipeCatalyst(ItemStack(ModBlockItems.blastFurnace.get()),
            BlastingJeiRecipeCategory.UID,
            VanillaRecipeCategoryUid.FUEL
        )
    }

    override fun registerRecipeTransferHandlers(registration: IRecipeTransferRegistration) {
        registration.addRecipeTransferHandler(PressingRecipeTransferInfo())
        registration.addRecipeTransferHandler(PressContainer::class.java, VanillaRecipeCategoryUid.FUEL, 2, 1, 4, 36)
        registration.addRecipeTransferHandler(BlastFurnaceContainer::class.java, BlastingJeiRecipeCategory.UID, 0, 2, 4, 36)
        registration.addRecipeTransferHandler(BlastFurnaceContainer::class.java, VanillaRecipeCategoryUid.FUEL, 2, 1, 4, 36)
    }

    override fun registerGuiHandlers(registration: IGuiHandlerRegistration) {
        registration.addRecipeClickArea(SteamPressScreen::class.java, 103, 34, 24, 17, PressingJeiRecipeCategory.UID)
        registration.addRecipeClickArea(BlastFurnaceScreen::class.java, 101, 35, 24, 17, BlastingJeiRecipeCategory.UID)
    }
}
