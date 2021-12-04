//package at.martinthedragon.nucleartech.integration.jei.categories
//
//import at.martinthedragon.nucleartech.recipes.BatteryRecipe
//import mezz.jei.api.constants.VanillaTypes
//import mezz.jei.api.ingredients.IIngredients
//import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension
//import net.minecraft.resources.ResourceLocation
//import net.minecraftforge.common.util.Size2i
//
//// TODO show off different energy levels to clarify that the resulting item retains the energy
//class BatteryCraftingJeiRecipeCategory(val recipe: BatteryRecipe) : ICraftingCategoryExtension {
//    override fun setIngredients(ingredients: IIngredients) {
//        ingredients.setInputIngredients(recipe.ingredients)
//        ingredients.setOutput(VanillaTypes.ITEM, recipe.resultItem)
//    }
//
//    override fun getRegistryName(): ResourceLocation = recipe.id
//
//    override fun getSize() = Size2i(recipe.recipeWidth, recipe.recipeHeight)
//}
