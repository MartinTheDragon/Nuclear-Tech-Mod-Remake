package at.martinthedragon.nucleartech.recipes

import com.google.gson.JsonObject
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

class BlastingRecipe(
    val recipeID: ResourceLocation,
    val ingredient1: Ingredient,
    val ingredient2: Ingredient,
    val result: ItemStack,
    val experience: Float
) : Recipe<Container> {
    override fun matches(tileEntity: Container, world: Level): Boolean = if (tileEntity.containerSize == 1)
        ingredient1.test(tileEntity.getItem(0)) || ingredient2.test(tileEntity.getItem(1)) ||
                ingredient1.test(tileEntity.getItem(1)) || ingredient2.test(tileEntity.getItem(0))
    else ingredient1.test(tileEntity.getItem(0)) && ingredient2.test(tileEntity.getItem(1)) ||
            ingredient1.test(tileEntity.getItem(1)) && ingredient2.test(tileEntity.getItem(0))

    override fun assemble(tileEntity: Container): ItemStack = resultItem.copy()

    override fun canCraftInDimensions(p_194133_1_: Int, p_194133_2_: Int) = true

    override fun getResultItem() = result

    override fun getId() = recipeID

    override fun getSerializer() = RecipeSerializers.BLASTING.get()

    override fun getType() = RecipeTypes.BLASTING

    override fun isSpecial() = true

    class Serializer : ForgeRegistryEntry<RecipeSerializer<*>>(), RecipeSerializer<BlastingRecipe> {
        override fun fromJson(id: ResourceLocation, jsonObject: JsonObject): BlastingRecipe {
            val ingredient1 = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "first_ingredient"))
            val ingredient2 = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "second_ingredient"))
            val result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"))
            val experience = GsonHelper.getAsFloat(jsonObject, "experience", 0F)
            return BlastingRecipe(id, ingredient1, ingredient2, result, experience)
        }

        override fun fromNetwork(id: ResourceLocation, packetBuffer: FriendlyByteBuf): BlastingRecipe {
            val ingredient1 = Ingredient.fromNetwork(packetBuffer)
            val ingredient2 = Ingredient.fromNetwork(packetBuffer)
            val result = packetBuffer.readItem()
            val experience = packetBuffer.readFloat()
            return BlastingRecipe(id, ingredient1, ingredient2, result, experience)
        }

        override fun toNetwork(packetBuffer: FriendlyByteBuf, recipe: BlastingRecipe) {
            recipe.ingredient1.toNetwork(packetBuffer)
            recipe.ingredient2.toNetwork(packetBuffer)
            packetBuffer.writeItem(recipe.resultItem)
            packetBuffer.writeFloat(recipe.experience)
        }
    }
}
