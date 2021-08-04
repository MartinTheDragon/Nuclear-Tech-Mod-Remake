package at.martinthedragon.nucleartech.recipes

import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.IRecipeType

object RecipeTypes {
    val PRESSING = create<PressRecipe>("pressing")
    val BLASTING = create<BlastingRecipe>("blasting")
    val SHREDDING = create<ShreddingRecipe>("shredding")

    private fun <T : IRecipe<*>> create(name: String): IRecipeType<T> = object : IRecipeType<T> {
        override fun toString() = name
    }
}
