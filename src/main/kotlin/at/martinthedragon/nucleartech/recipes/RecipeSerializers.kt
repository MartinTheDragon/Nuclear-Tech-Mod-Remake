package at.martinthedragon.nucleartech.recipes

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.RECIPE_SERIALIZERS
import net.minecraftforge.registries.RegistryObject

object RecipeSerializers {
    val PRESSING: RegistryObject<PressRecipe.Serializer> = RECIPE_SERIALIZERS.register("pressing", PressRecipe::Serializer)
    val BLASTING: RegistryObject<BlastingRecipe.Serializer> = RECIPE_SERIALIZERS.register("blasting", BlastingRecipe::Serializer)
    val SHREDDING: RegistryObject<ShreddingRecipe.Serializer> = RECIPE_SERIALIZERS.register("shredding", ShreddingRecipe::Serializer)
    val BATTERY: RegistryObject<BatteryRecipe.Serializer> = RECIPE_SERIALIZERS.register("battery", BatteryRecipe::Serializer)
}
