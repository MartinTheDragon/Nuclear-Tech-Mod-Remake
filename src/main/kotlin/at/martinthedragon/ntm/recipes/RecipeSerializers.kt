package at.martinthedragon.ntm.recipes

import at.martinthedragon.ntm.RegistriesAndLifecycle.RECIPE_SERIALIZERS
import net.minecraftforge.fml.RegistryObject

object RecipeSerializers {
    val PRESSING: RegistryObject<PressRecipe.Serializer> = RECIPE_SERIALIZERS.register("pressing") { PressRecipe.Serializer() }
}
