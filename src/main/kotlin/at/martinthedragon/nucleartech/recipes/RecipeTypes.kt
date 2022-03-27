package at.martinthedragon.nucleartech.recipes

import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.recipes.anvil.AnvilConstructingRecipe
import at.martinthedragon.nucleartech.recipes.anvil.AnvilSmithingRecipe
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType

object RecipeTypes {
    private val recipeTypes = mutableSetOf<RecipeType<*>>()

    // NOTE: all recipes need to have the isSpecial() = true override, so the recipe book does not issue a warning
    val SMITHING = create<AnvilSmithingRecipe>("anvil_smithing")
    val CONSTRUCTING = create<AnvilConstructingRecipe>("anvil_constructing")
    val PRESSING = create<PressingRecipe>("pressing")
    val BLASTING = create<BlastingRecipe>("blasting")
    val SHREDDING = create<ShreddingRecipe>("shredding")
    val ASSEMBLY = create<AssemblyRecipe>("assembly")

    private fun <T : Recipe<*>> create(name: String): RecipeType<T> = object : RecipeType<T> {
        private val registryName = ntm(name)
        override fun toString() = registryName.toString()
    }.also { recipeTypes += it }

    fun getTypes() = recipeTypes.toSet()
}
