package at.martinthedragon.nucleartech.integration.jei.categories

import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.ntm
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.IRecipeLayout
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.recipe.category.IRecipeCategory
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient

class TemplateFolderJeiRecipeCategory(guiHelper: IGuiHelper) : IRecipeCategory<TemplateFolderJeiRecipeCategory.TemplateFolderRecipe> {
    private val background = guiHelper.createDrawable(GUI_RESOURCE, 0, 0, 112, 38)
    private val icon = guiHelper.createDrawableIngredient(ItemStack(ModItems.machineTemplateFolder.get()))

    override fun getUid() = UID

    override fun getRecipeClass() = TemplateFolderRecipe::class.java

    override fun getTitle() = TranslatableComponent("jei.${NuclearTech.MODID}.category.template_folder_results")

    override fun getBackground(): IDrawable = background

    override fun getIcon(): IDrawable = icon

    override fun setIngredients(recipe: TemplateFolderRecipe, ingredients: IIngredients) {
        ingredients.setInputIngredients(mutableListOf(Ingredient.of(recipe.folderItem), recipe.templateType.firstIngredient, recipe.templateType.secondIngredient))
        ingredients.setOutputLists(VanillaTypes.ITEM, listOf(recipe.result.items.toList()))
    }

    override fun setRecipe(recipeLayout: IRecipeLayout, recipe: TemplateFolderRecipe, ingredients: IIngredients) {
        val guiItemStacks = recipeLayout.itemStacks
        guiItemStacks.init(0, true, 65, 0)
        guiItemStacks.init(1, true, 0, 20)
        guiItemStacks.init(2, true, 36, 20)
        guiItemStacks.init(3, false, 94, 20)
        guiItemStacks.set(ingredients)
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
