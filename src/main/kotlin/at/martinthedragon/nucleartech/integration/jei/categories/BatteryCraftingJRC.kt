package at.martinthedragon.nucleartech.integration.jei.categories

import at.martinthedragon.nucleartech.recipes.BatteryRecipe
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder
import mezz.jei.api.gui.ingredient.ICraftingGridHelper
import mezz.jei.api.recipe.IFocusGroup
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension
import net.minecraft.resources.ResourceLocation

// TODO show off different energy levels to clarify that the resulting item retains the energy
class BatteryCraftingJRC(val recipe: BatteryRecipe) : ICraftingCategoryExtension {
    override fun setRecipe(builder: IRecipeLayoutBuilder, craftingGridHelper: ICraftingGridHelper, focuses: IFocusGroup) {
        val inputs = recipe.ingredients.map { it.items.toList() }
        craftingGridHelper.setInputs(builder, VanillaTypes.ITEM_STACK, inputs, width, height)
        craftingGridHelper.setOutputs(builder, VanillaTypes.ITEM_STACK, listOf(recipe.resultItem))
    }

    override fun getRegistryName(): ResourceLocation = recipe.id
    override fun getWidth() = recipe.recipeWidth
    override fun getHeight() = recipe.recipeHeight
}
