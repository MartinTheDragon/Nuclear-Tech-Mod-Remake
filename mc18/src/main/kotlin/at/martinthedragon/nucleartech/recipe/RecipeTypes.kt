package at.martinthedragon.nucleartech.recipe

import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.recipe.anvil.AnvilConstructingRecipe
import at.martinthedragon.nucleartech.recipe.anvil.AnvilSmithingRecipe
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType

object RecipeTypes {
    private val recipeTypes = mutableSetOf<RecipeType<*>>()

    // NOTE: all recipes need to have the isSpecial() = true override, so the recipe book does not issue a warning
    val ASSEMBLY = create<AssemblyRecipe>("assembly")
    val BLASTING = create<BlastingRecipe>("blasting")
    val CHEM = create<ChemRecipe>("chem")
    val CENTRIFUGE = create<CentrifugeRecipe>("centrifuge")
    val CONSTRUCTING = create<AnvilConstructingRecipe>("anvil_constructing")
    val PRESSING = create<PressingRecipe>("pressing")
    val SHREDDING = create<ShreddingRecipe>("shredding")
    val SMITHING = create<AnvilSmithingRecipe>("anvil_smithing")

    private fun <T : Recipe<*>> create(name: String): RecipeType<T> = object : RecipeType<T> {
        private val registryName = ntm(name)
        override fun toString() = registryName.toString()
    }.also { recipeTypes += it }

    fun getTypes() = recipeTypes.toSet()
}
