package at.martinthedragon.nucleartech.recipes.anvil

import at.martinthedragon.nucleartech.blocks.Anvil
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.recipes.RecipeSerializers
import at.martinthedragon.nucleartech.recipes.RecipeTypes
import at.martinthedragon.nucleartech.recipes.StackedIngredient
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
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

open class AnvilSmithingRecipe(
    val recipeID: ResourceLocation,
    val ingredient1: StackedIngredient,
    val ingredient2: StackedIngredient,
    val result: ItemStack,
    tier: Int,
    val shapeless: Boolean = false
) : Recipe<Container> {
    val tier = if (NuclearConfig.general.enableBabyMode.get()) 1 else tier

    fun getAmountConsumed(index: Int, mirrored: Boolean) = when (index) {
        0 -> if (mirrored) ingredient2.requiredAmount else ingredient1.requiredAmount
        1 -> if (mirrored) ingredient1.requiredAmount else ingredient2.requiredAmount
        else -> 0
    }

    override fun matches(container: Container, level: Level): Boolean = matchesInt(container) != -1

    fun matchesInt(container: Container): Int =
        if (container.containerSize < 2) -1
        else if (ingredient1.testWithStackSize(container.getItem(0)) && ingredient2.testWithStackSize(container.getItem(1))) 0
        else if (shapeless) if (ingredient1.testWithStackSize(container.getItem(1)) && ingredient2.testWithStackSize(container.getItem(0))) 1 else -1
        else -1

    fun isMissingIngredient(stack: ItemStack, firstSlotHasItem: Boolean): Boolean =
        if (firstSlotHasItem) ingredient2.test(stack)
        else if (shapeless) ingredient2.test(stack)
        else ingredient1.test(stack)

    override fun assemble(container: Container): ItemStack = result.copy()

    override fun getId() = recipeID
    override fun getSerializer(): Serializer = RecipeSerializers.SMITHING.get()
    override fun getType() = RecipeTypes.SMITHING
    override fun isSpecial() = true
    override fun canCraftInDimensions(p_43999_: Int, p_44000_: Int) = true
    override fun getToastSymbol() = ItemStack(Anvil.getAnvilByTier(tier))
    override fun getIngredients(): NonNullList<Ingredient> = NonNullList.of(Ingredient.EMPTY, ingredient1, ingredient2)
    override fun getResultItem() = result

    open class Serializer : ForgeRegistryEntry<RecipeSerializer<*>>(), RecipeSerializer<AnvilSmithingRecipe> {
        override fun fromJson(id: ResourceLocation, json: JsonObject): AnvilSmithingRecipe {
            val ingredient1 = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "left")) as? StackedIngredient ?: throw JsonParseException("Couldn't parse 'left' as StackedIngredient")
            val ingredient2 = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "right")) as? StackedIngredient ?: throw JsonParseException("Couldn't parse 'right' as StackedIngredient")
            val result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"))
            val tier = GsonHelper.getAsInt(json, "tier")
            return AnvilSmithingRecipe(id, ingredient1, ingredient2, result, tier)
        }

        override fun fromNetwork(id: ResourceLocation, packet: FriendlyByteBuf): AnvilSmithingRecipe {
            val ingredient1 = Ingredient.fromNetwork(packet) as? StackedIngredient ?: throw IllegalStateException("Received non-StackedIngredient over network")
            val ingredient2 = Ingredient.fromNetwork(packet) as? StackedIngredient ?: throw IllegalStateException("Received non-StackedIngredient over network")
            val result = packet.readItem()
            val tier = packet.readInt()
            return AnvilSmithingRecipe(id, ingredient1, ingredient2, result, tier)
        }

        override fun toNetwork(packet: FriendlyByteBuf, recipe: AnvilSmithingRecipe) {
            recipe.ingredient1.toNetwork(packet)
            recipe.ingredient2.toNetwork(packet)
            packet.writeItem(recipe.resultItem)
            packet.writeInt(recipe.tier)
        }
    }
}
