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

class BlastingRecipe(
    val recipeID: ResourceLocation,
    val ingredient1: Ingredient,
    val ingredient2: Ingredient,
    val result: ItemStack,
    val experience: Float
) : IRecipe<IInventory> {
    override fun matches(tileEntity: IInventory, world: World): Boolean = if (tileEntity.containerSize == 1)
        ingredient1.test(tileEntity.getItem(0)) || ingredient2.test(tileEntity.getItem(1)) ||
                ingredient1.test(tileEntity.getItem(1)) || ingredient2.test(tileEntity.getItem(0))
    else ingredient1.test(tileEntity.getItem(0)) && ingredient2.test(tileEntity.getItem(1)) ||
            ingredient1.test(tileEntity.getItem(1)) && ingredient2.test(tileEntity.getItem(0))

    override fun assemble(tileEntity: IInventory): ItemStack = resultItem.copy()

    override fun canCraftInDimensions(p_194133_1_: Int, p_194133_2_: Int) = true

    override fun getResultItem() = result

    override fun getId() = recipeID

    override fun getSerializer() = RecipeSerializers.BLASTING.get()

    override fun getType() = RecipeTypes.BLASTING

    override fun isSpecial() = true

    class Serializer : ForgeRegistryEntry<IRecipeSerializer<*>>(), IRecipeSerializer<BlastingRecipe> {
        override fun fromJson(id: ResourceLocation, jsonObject: JsonObject): BlastingRecipe {
            val ingredient1 = Ingredient.fromJson(JSONUtils.getAsJsonObject(jsonObject, "first_ingredient"))
            val ingredient2 = Ingredient.fromJson(JSONUtils.getAsJsonObject(jsonObject, "second_ingredient"))
            val result = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(jsonObject, "result"))
            val experience = JSONUtils.getAsFloat(jsonObject, "experience", 0F)
            return BlastingRecipe(id, ingredient1, ingredient2, result, experience)
        }

        override fun fromNetwork(id: ResourceLocation, packetBuffer: PacketBuffer): BlastingRecipe {
            val ingredient1 = Ingredient.fromNetwork(packetBuffer)
            val ingredient2 = Ingredient.fromNetwork(packetBuffer)
            val result = packetBuffer.readItem()
            val experience = packetBuffer.readFloat()
            return BlastingRecipe(id, ingredient1, ingredient2, result, experience)
        }

        override fun toNetwork(packetBuffer: PacketBuffer, recipe: BlastingRecipe) {
            recipe.ingredient1.toNetwork(packetBuffer)
            recipe.ingredient2.toNetwork(packetBuffer)
            packetBuffer.writeItem(recipe.resultItem)
            packetBuffer.writeFloat(recipe.experience)
        }
    }
}
