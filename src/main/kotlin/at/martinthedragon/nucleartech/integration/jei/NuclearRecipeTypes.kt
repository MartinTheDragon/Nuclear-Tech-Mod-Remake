package at.martinthedragon.nucleartech.integration.jei

import at.martinthedragon.nucleartech.integration.jei.categories.*
import at.martinthedragon.nucleartech.recipes.BlastingRecipe
import at.martinthedragon.nucleartech.recipes.PressingRecipe
import at.martinthedragon.nucleartech.recipes.ShreddingRecipe
import at.martinthedragon.nucleartech.recipes.anvil.AnvilConstructingRecipe
import at.martinthedragon.nucleartech.recipes.anvil.AnvilSmithingRecipe
import mezz.jei.api.recipe.RecipeType

object NuclearRecipeTypes {
    val BLASTING = RecipeType(BlastingJeiRecipeCategory.UID, BlastingRecipe::class.java)
    val CONSTRUCTING = RecipeType(ConstructingJeiRecipeCategory.UID, AnvilConstructingRecipe::class.java)
    val PRESSING = RecipeType(PressingJeiRecipeCategory.UID, PressingRecipe::class.java)
    val SHREDDING = RecipeType(ShreddingJeiRecipeCategory.UID, ShreddingRecipe::class.java)
    val SMITHING = RecipeType(SmithingJeiRecipeCategory.UID, AnvilSmithingRecipe::class.java)
    val FOLDER_RESULTS = RecipeType(TemplateFolderJeiRecipeCategory.UID, TemplateFolderJeiRecipeCategory.TemplateFolderRecipe::class.java)
}
