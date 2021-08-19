package at.martinthedragon.nucleartech.recipes

import at.martinthedragon.nucleartech.NuclearTech
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.IRecipeType
import net.minecraft.util.ResourceLocation

object RecipeTypes {
    // NOTE: all recipes need to have the isSpecial() = true override, so the recipe book does not issue a warning

    val PRESSING = create<PressRecipe>("pressing")
    val BLASTING = create<BlastingRecipe>("blasting")
    val SHREDDING = create<ShreddingRecipe>("shredding")

    private fun <T : IRecipe<*>> create(name: String): IRecipeType<T> = object : IRecipeType<T> {
        private val registryName = ResourceLocation(NuclearTech.MODID, name)
        override fun toString() = registryName.toString()
    }

    fun getTypes(): Set<IRecipeType<*>> = setOf(PRESSING, BLASTING, SHREDDING)
}
