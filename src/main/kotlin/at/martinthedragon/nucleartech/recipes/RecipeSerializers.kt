package at.martinthedragon.nucleartech.recipes

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.RECIPE_SERIALIZERS
import net.minecraftforge.fml.RegistryObject

object RecipeSerializers {
    val PRESSING: RegistryObject<PressRecipe.Serializer> = RECIPE_SERIALIZERS.register("pressing") { PressRecipe.Serializer() }
}
