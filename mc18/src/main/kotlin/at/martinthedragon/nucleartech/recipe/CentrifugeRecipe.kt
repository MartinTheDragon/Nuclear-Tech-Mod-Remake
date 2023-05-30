package at.martinthedragon.nucleartech.recipe

import at.martinthedragon.nucleartech.block.entity.CentrifugeBlockEntity
import at.martinthedragon.nucleartech.extensions.toStupidMojangList
import at.martinthedragon.nucleartech.item.NTechBlockItems
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.core.NonNullList
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.ShapedRecipe
import net.minecraft.world.level.Level
import net.minecraftforge.registries.ForgeRegistryEntry

class CentrifugeRecipe(
    val recipeID: ResourceLocation,
    val ingredient: Ingredient,
    val resultsList: NonNullList<ItemStack>
) : Recipe<CentrifugeBlockEntity> {
    override fun matches(container: CentrifugeBlockEntity, level: Level) = container.containerSize > 1 && ingredient.test(container.getItem(3))
    override fun assemble(container: CentrifugeBlockEntity): ItemStack = resultItem.copy()
    override fun getId() = recipeID
    override fun getSerializer() = RecipeSerializers.CENTRIFUGE.get()
    override fun getType() = RecipeTypes.CENTRIFUGE
    override fun isSpecial() = true
    override fun canCraftInDimensions(x: Int, y: Int) = true
    override fun getToastSymbol(): ItemStack = NTechBlockItems.centrifugePlacer.get().defaultInstance
    override fun getIngredients(): NonNullList<Ingredient> = NonNullList.of(ingredient)
    override fun getResultItem(): ItemStack = resultsList.first()

    class Serializer : ForgeRegistryEntry<RecipeSerializer<*>>(), RecipeSerializer<CentrifugeRecipe> {
        override fun fromJson(id: ResourceLocation, json: JsonObject): CentrifugeRecipe {
            val ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"))
            val results = GsonHelper.getAsJsonArray(json, "results").map(JsonElement::getAsJsonObject).map(ShapedRecipe::itemStackFromJson)
            return CentrifugeRecipe(id, ingredient, results.toStupidMojangList())
        }

        override fun fromNetwork(id: ResourceLocation, buffer: FriendlyByteBuf): CentrifugeRecipe {
            val ingredient = Ingredient.fromNetwork(buffer)
            val results = buffer.readList(FriendlyByteBuf::readItem)
            return CentrifugeRecipe(id, ingredient, results.toStupidMojangList())
        }

        override fun toNetwork(buffer: FriendlyByteBuf, recipe: CentrifugeRecipe) {
            recipe.ingredient.toNetwork(buffer)
            buffer.writeCollection(recipe.resultsList, FriendlyByteBuf::writeItem)
        }
    }
}
