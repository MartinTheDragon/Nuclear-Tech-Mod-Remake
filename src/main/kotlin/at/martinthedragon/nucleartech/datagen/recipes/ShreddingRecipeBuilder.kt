package at.martinthedragon.nucleartech.datagen.recipes

import at.martinthedragon.nucleartech.recipes.RecipeSerializers
import com.google.gson.JsonObject
import net.minecraft.data.IFinishedRecipe
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Consumer

class ShreddingRecipeBuilder(val result: Item, val experience: Float, val count: Int, val ingredient: Ingredient) {
    constructor(result: IItemProvider, experience: Float, count: Int, ingredient: Ingredient) : this(result.asItem(), experience, count, ingredient)

    fun save(consumer: Consumer<IFinishedRecipe>, recipeName: String) = save(consumer, ResourceLocation(recipeName))

    fun save(consumer: Consumer<IFinishedRecipe>, recipeName: ResourceLocation) {
        consumer.accept(Result(recipeName, result, count, experience, ingredient))
    }

    class Result(
        private val id: ResourceLocation,
        private val result: Item,
        private val count: Int,
        private val experience: Float,
        private val ingredient: Ingredient,
    ) : IFinishedRecipe {
        override fun serializeRecipeData(json: JsonObject) {
            json.add("ingredient", ingredient.toJson())
            val resultJson = JsonObject()
            resultJson.addProperty("item", ForgeRegistries.ITEMS.getKey(result).toString())
            resultJson.addProperty("count", count)
            json.add("result", resultJson)
            json.addProperty("experience", experience)
        }

        override fun getId() = id

        override fun getType() = RecipeSerializers.SHREDDING.get()

        override fun getAdvancementId(): ResourceLocation? = null

        override fun serializeAdvancement(): JsonObject? = null
    }
}
