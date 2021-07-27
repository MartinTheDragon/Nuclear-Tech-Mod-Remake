package at.martinthedragon.nucleartech.recipes

import com.google.gson.JsonObject
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.item.crafting.Ingredient
import net.minecraft.item.crafting.ShapedRecipe
import net.minecraft.network.PacketBuffer
import net.minecraft.util.JSONUtils
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.registries.ForgeRegistryEntry

class ShreddingRecipe(
    val recipeID: ResourceLocation,
    val ingredient: Ingredient,
    val result: ItemStack,
    val experience: Float
) : IRecipe<IInventory> {
    override fun matches(inventory: IInventory, world: World): Boolean =
        (0..8).map { inventory.getItem(it) }.any { ingredient.test(it) }

    override fun assemble(inventory: IInventory): ItemStack = resultItem.copy()

    override fun canCraftInDimensions(p_194133_1_: Int, p_194133_2_: Int) = true

    override fun getResultItem() = result

    override fun getId() = recipeID

    override fun getSerializer() = RecipeSerializers.SHREDDING.get()

    override fun getType() = RecipeTypes.SHREDDING

    class Serializer : ForgeRegistryEntry<IRecipeSerializer<*>>(), IRecipeSerializer<ShreddingRecipe> {
        override fun fromJson(id: ResourceLocation, json: JsonObject): ShreddingRecipe {
            val ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "ingredient"))
            val result = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"))
            val experience = JSONUtils.getAsFloat(json, "experience")
            return ShreddingRecipe(id, ingredient, result, experience)
        }

        override fun fromNetwork(id: ResourceLocation, packetBuffer: PacketBuffer): ShreddingRecipe {
            val ingredient = Ingredient.fromNetwork(packetBuffer)
            val result = packetBuffer.readItem()
            val experience = packetBuffer.readFloat()
            return ShreddingRecipe(id, ingredient, result, experience)
        }

        override fun toNetwork(packetBuffer: PacketBuffer, recipe: ShreddingRecipe) {
            recipe.ingredient.toNetwork(packetBuffer)
            packetBuffer.writeItem(recipe.resultItem)
            packetBuffer.writeFloat(recipe.experience)
        }
    }
}
