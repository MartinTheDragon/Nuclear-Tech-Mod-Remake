package at.martinthedragon.nucleartech.integration.jei

import at.martinthedragon.nucleartech.integration.jei.categories.TemplateFolderJeiRecipeCategory
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.recipes.BlastingRecipe
import at.martinthedragon.nucleartech.recipes.PressingRecipe
import at.martinthedragon.nucleartech.recipes.ShreddingRecipe
import at.martinthedragon.nucleartech.recipes.anvil.AnvilConstructingRecipe
import at.martinthedragon.nucleartech.recipes.anvil.AnvilSmithingRecipe
import mezz.jei.api.recipe.RecipeType

object NuclearRecipeTypes {
    val BLASTING = RecipeType(ntm("blasting"), BlastingRecipe::class.java)
    val CONSTRUCTING = RecipeType(ntm("constructing"), AnvilConstructingRecipe::class.java)
    val PRESSING = RecipeType(ntm("pressing"), PressingRecipe::class.java)
    val SHREDDING = RecipeType(ntm("shredding"), ShreddingRecipe::class.java)
    val SMITHING = RecipeType(ntm("smithing"), AnvilSmithingRecipe::class.java)
    val FOLDER_RESULTS = RecipeType(ntm("template_folder_results"), TemplateFolderJeiRecipeCategory.TemplateFolderRecipe::class.java)
}
