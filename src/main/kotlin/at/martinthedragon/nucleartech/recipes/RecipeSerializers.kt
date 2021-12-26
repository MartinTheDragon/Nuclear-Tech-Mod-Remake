package at.martinthedragon.nucleartech.recipes

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.RECIPE_SERIALIZERS
import at.martinthedragon.nucleartech.recipes.anvil.AnvilConstructingRecipe
import at.martinthedragon.nucleartech.recipes.anvil.AnvilSmithingRecipe
import net.minecraftforge.registries.RegistryObject

object RecipeSerializers {
    val SMITHING: RegistryObject<AnvilSmithingRecipe.Serializer> = RECIPE_SERIALIZERS.register("anvil_smithing", AnvilSmithingRecipe::Serializer)
    val CONSTRUCTING: RegistryObject<AnvilConstructingRecipe.Serializer> = RECIPE_SERIALIZERS.register("anvil_constructing", AnvilConstructingRecipe::Serializer)
    val PRESSING: RegistryObject<PressRecipe.Serializer> = RECIPE_SERIALIZERS.register("pressing", PressRecipe::Serializer)
    val BLASTING: RegistryObject<BlastingRecipe.Serializer> = RECIPE_SERIALIZERS.register("blasting", BlastingRecipe::Serializer)
    val SHREDDING: RegistryObject<ShreddingRecipe.Serializer> = RECIPE_SERIALIZERS.register("shredding", ShreddingRecipe::Serializer)
    val BATTERY: RegistryObject<BatteryRecipe.Serializer> = RECIPE_SERIALIZERS.register("battery", BatteryRecipe::Serializer)
}
