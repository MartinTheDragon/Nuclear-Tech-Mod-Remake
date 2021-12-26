package at.martinthedragon.nucleartech.recipes

import at.martinthedragon.nucleartech.ModBlocks
import com.google.gson.JsonObject
import net.minecraft.core.NonNullList
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.ShapedRecipe
import net.minecraft.world.level.Level
import net.minecraftforge.registries.ForgeRegistryEntry

class ShreddingRecipe(
    val recipeID: ResourceLocation,
    val ingredient: Ingredient,
    val result: ItemStack,
    val experience: Float
) : Recipe<Container> {
    override fun matches(inventory: Container, level: Level): Boolean =
        (0..8).map { inventory.getItem(it) }.any { ingredient.test(it) }

    override fun assemble(inventory: Container): ItemStack = resultItem.copy()

    override fun getId() = recipeID
    override fun getSerializer(): Serializer = RecipeSerializers.SHREDDING.get()
    override fun getType() = RecipeTypes.SHREDDING
    override fun isSpecial() = true
    override fun canCraftInDimensions(p_194133_1_: Int, p_194133_2_: Int) = true
    override fun getToastSymbol() = ItemStack(ModBlocks.shredder.get())
    override fun getIngredients(): NonNullList<Ingredient> = NonNullList.of(Ingredient.EMPTY, ingredient)
    override fun getResultItem() = result

    class Serializer : ForgeRegistryEntry<RecipeSerializer<*>>(), RecipeSerializer<ShreddingRecipe> {
        override fun fromJson(id: ResourceLocation, json: JsonObject): ShreddingRecipe {
            val ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"))
            val result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"))
            val experience = GsonHelper.getAsFloat(json, "experience")
            return ShreddingRecipe(id, ingredient, result, experience)
        }

        override fun fromNetwork(id: ResourceLocation, packetBuffer: FriendlyByteBuf): ShreddingRecipe {
            val ingredient = Ingredient.fromNetwork(packetBuffer)
            val result = packetBuffer.readItem()
            val experience = packetBuffer.readFloat()
            return ShreddingRecipe(id, ingredient, result, experience)
        }

        override fun toNetwork(packetBuffer: FriendlyByteBuf, recipe: ShreddingRecipe) {
            recipe.ingredient.toNetwork(packetBuffer)
            packetBuffer.writeItem(recipe.resultItem)
            packetBuffer.writeFloat(recipe.experience)
        }
    }
}
