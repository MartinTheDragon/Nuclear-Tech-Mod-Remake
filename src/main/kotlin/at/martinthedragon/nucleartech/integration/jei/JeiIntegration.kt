package at.martinthedragon.nucleartech.integration.jei

import at.martinthedragon.nucleartech.ModBlockItems
import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.containers.PressContainer
import at.martinthedragon.nucleartech.recipes.RecipeTypes
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.constants.VanillaRecipeCategoryUid
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.registration.IRecipeCatalystRegistration
import mezz.jei.api.registration.IRecipeCategoryRegistration
import mezz.jei.api.registration.IRecipeRegistration
import mezz.jei.api.registration.IRecipeTransferRegistration
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
    }

    override fun registerRecipes(registration: IRecipeRegistration) {
        registration.addRecipes(
            Minecraft.getInstance().level!!
                .recipeManager.recipes
                .filter { it.id.namespace == NuclearTech.MODID }
                .filter { it.type == RecipeTypes.PRESSING },
            PressingJeiRecipeCategory.UID
        )
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
    }

    override fun registerRecipeTransferHandlers(registration: IRecipeTransferRegistration) {
        registration.addRecipeTransferHandler(PressingRecipeTransferInfo())
        registration.addRecipeTransferHandler(PressContainer::class.java, VanillaRecipeCategoryUid.FUEL, 2, 1, 4, 36)
    }
}
