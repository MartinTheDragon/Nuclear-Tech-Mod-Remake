package at.martinthedragon.ntm.recipes

import at.martinthedragon.ntm.Main
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.IRecipeType
import net.minecraft.util.ResourceLocation
import net.minecraft.util.registry.Registry

object RecipeTypes {
    private val typesToRegister = mutableMapOf<ResourceLocation, IRecipeType<*>>()

    val PRESSING = register<PressRecipe>("pressing")

    private fun <T : IRecipe<*>> register(name: String): IRecipeType<T> {
        val recipeType = object : IRecipeType<T> {
            override fun toString() = name
        }
        typesToRegister[ResourceLocation(Main.MODID, name)] = recipeType
        return recipeType
    }

    fun registerTypes() {
        typesToRegister.forEach { (registryName, type) ->
            Registry.register(Registry.RECIPE_TYPE, registryName, type)
        }
    }
}
