package at.martinthedragon.nucleartech.integration.jei.categories

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.integration.jei.NuclearRecipeTypes
import at.martinthedragon.nucleartech.item.NTechItems
import at.martinthedragon.nucleartech.ntm
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.recipe.IFocusGroup
import mezz.jei.api.recipe.RecipeIngredientRole
import mezz.jei.api.recipe.category.IRecipeCategory
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient

class TemplateFolderJRC(guiHelper: IGuiHelper) : IRecipeCategory<TemplateFolderJRC.TemplateFolderRecipe> {
    private val texture = ntm("textures/gui/jei_template_folder.png")
    private val background = guiHelper.createDrawable(texture, 0, 0, 112, 38)
    private val icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ItemStack(NTechItems.machineTemplateFolder.get()))

    override fun getUid() = ntm("template_folder_results")
    override fun getRecipeClass() = TemplateFolderRecipe::class.java
    override fun getRecipeType() = NuclearRecipeTypes.FOLDER_RESULTS
    override fun getTitle() = LangKeys.JEI_CATEGORY_TEMPLATE_FOLDER.get()
    override fun getBackground(): IDrawable = background
    override fun getIcon(): IDrawable = icon

    override fun setRecipe(builder: IRecipeLayoutBuilder, recipe: TemplateFolderRecipe, focuses: IFocusGroup) {
        builder.addSlot(RecipeIngredientRole.CATALYST, 66, 1).addItemStack(recipe.folderItem)

        val firstInput = builder.addSlot(RecipeIngredientRole.INPUT, 1, 21).addIngredients(recipe.firstIngredient)
        builder.addSlot(RecipeIngredientRole.INPUT, 37, 21).addIngredients(recipe.secondIngredient)

        val output = builder.addSlot(RecipeIngredientRole.OUTPUT, 95, 21).addItemStacks(recipe.result)

        if (recipe.templateType == TemplateFolderRecipe.TemplateType.PressStamp && recipe.firstIngredient.items.size == recipe.result.size) {
            builder.createFocusLink(firstInput, output)
        }
    }

    data class TemplateFolderRecipe(val folderItem: ItemStack, val templateType: TemplateType, val firstIngredient: Ingredient, val secondIngredient: Ingredient, val result: List<ItemStack>) {
        enum class TemplateType { PressStamp, SirenTrack, FluidIdentifier, AssemblyTemplate, ChemistryTemplate }
    }
}
