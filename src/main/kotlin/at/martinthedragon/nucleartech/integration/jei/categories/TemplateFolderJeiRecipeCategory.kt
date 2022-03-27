package at.martinthedragon.nucleartech.integration.jei.categories

import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.ntm
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.recipe.IFocusGroup
import mezz.jei.api.recipe.RecipeIngredientRole
import mezz.jei.api.recipe.category.IRecipeCategory
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient

class TemplateFolderJeiRecipeCategory(guiHelper: IGuiHelper) : IRecipeCategory<TemplateFolderJeiRecipeCategory.TemplateFolderRecipe> {
    private val background = guiHelper.createDrawable(GUI_RESOURCE, 0, 0, 112, 38)
    private val icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, ItemStack(ModItems.machineTemplateFolder.get()))

    override fun getUid() = UID

    override fun getRecipeClass() = TemplateFolderRecipe::class.java

    override fun getTitle() = TranslatableComponent("jei.${NuclearTech.MODID}.category.template_folder_results")

    override fun getBackground(): IDrawable = background

    override fun getIcon(): IDrawable = icon

    override fun setRecipe(builder: IRecipeLayoutBuilder, recipe: TemplateFolderRecipe, focuses: IFocusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 66, 1).addItemStack(recipe.folderItem)
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 21).addIngredients(recipe.templateType.firstIngredient)
        builder.addSlot(RecipeIngredientRole.INPUT, 37, 21).addIngredients(recipe.templateType.secondIngredient)
        builder.addSlot(RecipeIngredientRole.OUTPUT, 95, 21).addItemStacks(recipe.result.items.toList())
    }

    data class TemplateFolderRecipe(val folderItem: ItemStack, val templateType: TemplateType, val result: Ingredient) {
        enum class TemplateType(val firstIngredient: Ingredient, val secondIngredient: Ingredient) {
            StoneStamp(Ingredient.of(ModItems.stoneFlatStamp.get()), Ingredient.EMPTY),
            IronStamp(Ingredient.of(ModItems.ironFlatStamp.get()), Ingredient.EMPTY),
            SteelStamp(Ingredient.of(ModItems.steelFlatStamp.get()), Ingredient.EMPTY),
            TitaniumStamp(Ingredient.of(ModItems.titaniumFlatStamp.get()), Ingredient.EMPTY),
            ObsidianStamp(Ingredient.of(ModItems.obsidianFlatStamp.get()), Ingredient.EMPTY),
            SchrabidiumStamp(Ingredient.of(ModItems.schrabidiumFlatStamp.get()), Ingredient.EMPTY),
            SirenTrack(Ingredient.of(NuclearTags.Items.PLATES_INSULATOR), Ingredient.of(NuclearTags.Items.PLATES_STEEL)),
        }
    }

    companion object {
        val GUI_RESOURCE = ntm("textures/gui/jei_template_folder.png")
        val UID = ntm("template_folder_results")
    }
}
