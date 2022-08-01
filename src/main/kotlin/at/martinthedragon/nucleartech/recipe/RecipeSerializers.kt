package at.martinthedragon.nucleartech.recipe

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.RECIPE_SERIALIZERS
import at.martinthedragon.nucleartech.recipe.anvil.AnvilConstructingRecipe
import at.martinthedragon.nucleartech.recipe.anvil.AnvilRenameRecipe
import at.martinthedragon.nucleartech.recipe.anvil.AnvilSmithingRecipe
import at.martinthedragon.nucleartech.registerK
import net.minecraft.world.item.crafting.SimpleRecipeSerializer

object RecipeSerializers {
    val SMITHING = RECIPE_SERIALIZERS.registerK("anvil_smithing", AnvilSmithingRecipe::Serializer)
    val SMITHING_RENAMING = RECIPE_SERIALIZERS.registerK("anvil_smithing_special_renaming") { SimpleRecipeSerializer(::AnvilRenameRecipe) }
    val CONSTRUCTING = RECIPE_SERIALIZERS.registerK("anvil_constructing", AnvilConstructingRecipe::Serializer)
    val PRESSING = RECIPE_SERIALIZERS.registerK("pressing", PressingRecipe::Serializer)
    val BLASTING = RECIPE_SERIALIZERS.registerK("blasting", BlastingRecipe::Serializer)
    val SHREDDING = RECIPE_SERIALIZERS.registerK("shredding", ShreddingRecipe::Serializer)
    val ASSEMBLY = RECIPE_SERIALIZERS.registerK("assembly", AssemblyRecipe::Serializer)
    val CHEM = RECIPE_SERIALIZERS.registerK("chem", ChemRecipe::Serializer)
    val BATTERY = RECIPE_SERIALIZERS.registerK("battery", BatteryRecipe::Serializer)
}
