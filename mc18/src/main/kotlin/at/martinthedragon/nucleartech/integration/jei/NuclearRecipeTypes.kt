package at.martinthedragon.nucleartech.integration.jei

import at.martinthedragon.nucleartech.integration.jei.categories.TemplateFolderJRC
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.recipe.*
import at.martinthedragon.nucleartech.recipe.anvil.AnvilConstructingRecipe
import at.martinthedragon.nucleartech.recipe.anvil.AnvilSmithingRecipe
import mezz.jei.api.recipe.RecipeType

object NuclearRecipeTypes {
    val ASSEMBLING = RecipeType(ntm("assembling"), AssemblyRecipe::class.java)
    val BLASTING = RecipeType(ntm("blasting"), BlastingRecipe::class.java)
    val CHEMISTRY = RecipeType(ntm("chemistry"), ChemRecipe::class.java)
    val CONSTRUCTING = RecipeType(ntm("constructing"), AnvilConstructingRecipe::class.java)
    val PRESSING = RecipeType(ntm("pressing"), PressingRecipe::class.java)
    val SHREDDING = RecipeType(ntm("shredding"), ShreddingRecipe::class.java)
    val SMITHING = RecipeType(ntm("smithing"), AnvilSmithingRecipe::class.java)
    val FOLDER_RESULTS = RecipeType(ntm("template_folder_results"), TemplateFolderJRC.TemplateFolderRecipe::class.java)
}
