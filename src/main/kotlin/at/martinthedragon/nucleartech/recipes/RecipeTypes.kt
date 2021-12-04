package at.martinthedragon.nucleartech.recipes

import at.martinthedragon.nucleartech.NuclearTech
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType

object RecipeTypes {
    // NOTE: all recipes need to have the isSpecial() = true override, so the recipe book does not issue a warning

    val PRESSING = create<PressRecipe>("pressing")
    val BLASTING = create<BlastingRecipe>("blasting")
    val SHREDDING = create<ShreddingRecipe>("shredding")

    private fun <T : Recipe<*>> create(name: String): RecipeType<T> = object : RecipeType<T> {
        private val registryName = ResourceLocation(NuclearTech.MODID, name)
        override fun toString() = registryName.toString()
    }

    fun getTypes(): Set<RecipeType<*>> = setOf(PRESSING, BLASTING, SHREDDING)
}
