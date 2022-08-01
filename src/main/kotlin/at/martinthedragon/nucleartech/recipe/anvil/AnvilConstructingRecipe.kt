package at.martinthedragon.nucleartech.recipe.anvil

import at.martinthedragon.nucleartech.block.AnvilBlock
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.recipe.RecipeSerializers
import at.martinthedragon.nucleartech.recipe.RecipeTypes
import at.martinthedragon.nucleartech.recipe.StackedIngredient
import at.martinthedragon.nucleartech.recipe.containerSatisfiesRequirements
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import net.minecraft.core.NonNullList
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
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

class AnvilConstructingRecipe(
    val recipeID: ResourceLocation,
    val ingredientsList: NonNullList<StackedIngredient>,
    val results: List<ConstructingResult>,
    tierLower: Int = 1,
    tierUpper: Int = -1,
    val overlay: OverlayType = OverlayType.NONE
) : Recipe<Container> {
    constructor(recipeID: ResourceLocation, ingredient: StackedIngredient, result: ConstructingResult, tierLower: Int, tierUpper: Int, overlay: OverlayType = OverlayType.SMITHING) : this(recipeID, NonNullList.of(StackedIngredient.EMPTY, ingredient), listOf(result), tierLower, tierUpper, overlay)
    constructor(recipeID: ResourceLocation, ingredients: NonNullList<StackedIngredient>, result: ConstructingResult, tierLower: Int, tierUpper: Int, overlay: OverlayType = OverlayType.CONSTRUCTING) : this(recipeID, ingredients, listOf(result), tierLower, tierUpper, overlay)
    constructor(recipeID: ResourceLocation, ingredient: StackedIngredient, results: List<ConstructingResult>, tierLower: Int, tierUpper: Int, overlay: OverlayType = OverlayType.RECYCLING) : this(recipeID, NonNullList.of(StackedIngredient.EMPTY, ingredient), results, tierLower, tierUpper, overlay)

    val tierLower = if (NuclearConfig.general.enableBabyMode.get()) 1 else tierLower
    val tierUpper = if (NuclearConfig.general.enableBabyMode.get() && tierUpper != -1) 1 else tierUpper

    fun isTierWithinBounds(tier: Int): Boolean =
        if (tierUpper == -1) tier >= tierLower
        else tier in tierLower..tierUpper

    fun getDisplay(): ItemStack = when (overlay) {
        OverlayType.NONE, OverlayType.CONSTRUCTING, OverlayType.SMITHING -> results[0].stack.copy()
        OverlayType.RECYCLING -> ingredientsList[0].items[0].copy()
    }

    fun getResultsChanceCollapsed(): List<ItemStack> = results
        .map(ConstructingResult::stack)
        .map(ItemStack::copy)
        .run { if (isEmpty()) emptyList() else fold(mutableListOf(first())) { list, stack -> list.apply { if (!ItemStack.isSameItemSameTags(list.last(), stack)) add(stack) }}}

    fun getTooltipChancesForOutputAt(index: Int): Collection<Component> {
        val collapsedResults = getResultsChanceCollapsed()
        if (!collapsedResults.indices.contains(index)) return emptyList()
        val stack = collapsedResults[index]
        val matching = results.filter { ItemStack.isSameItemSameTags(it.stack, stack) }
        if (matching.all { it.chance == 1F }) return emptyList()
        return matching.map { TextComponent("${it.stack.count}x ${it.chance * 100}%") }
    }

    data class ConstructingResult(val stack: ItemStack, val chance: Float = 1F) {
        fun toNetwork(buffer: FriendlyByteBuf) {
            buffer.writeItem(stack)
            buffer.writeFloat(chance)
        }

        companion object {
            fun fromJson(json: JsonObject) = ConstructingResult(ShapedRecipe.itemStackFromJson(
                GsonHelper.getAsJsonObject(json, "item")),
                if (json.has("chance")) GsonHelper.getAsFloat(json, "chance") else 1F
            )

            fun fromNetwork(buffer: FriendlyByteBuf) = ConstructingResult(buffer.readItem(), buffer.readFloat())
        }
    }

    enum class OverlayType { NONE, CONSTRUCTING, RECYCLING, SMITHING }

    override fun matches(container: Container, level: Level) = container.containerSize > ingredientsList.size && ingredientsList.containerSatisfiesRequirements(container)
    override fun assemble(container: Container): ItemStack = resultItem.copy()
    override fun getId() = recipeID
    override fun getSerializer(): Serializer = RecipeSerializers.CONSTRUCTING.get()
    override fun getType() = RecipeTypes.CONSTRUCTING
    override fun isSpecial() = true
    override fun canCraftInDimensions(p_43999_: Int, p_44000_: Int) = true
    override fun getToastSymbol() = ItemStack(AnvilBlock.getAnvilByTier(tierLower))
    @Suppress("UNCHECKED_CAST")
    override fun getIngredients(): NonNullList<Ingredient> = ingredientsList as NonNullList<Ingredient>
    override fun getResultItem(): ItemStack = results.first().stack

    class Serializer : ForgeRegistryEntry<RecipeSerializer<*>>(), RecipeSerializer<AnvilConstructingRecipe> {
        override fun fromJson(id: ResourceLocation, json: JsonObject): AnvilConstructingRecipe {
            val ingredients = GsonHelper.getAsJsonArray(json, "ingredients").map(Ingredient::fromJson).map { it as? StackedIngredient ?: throw JsonParseException("Couldn't parse ingredient as StackedIngredient") }
            val ingredientsStupidMojangList = NonNullList.createWithCapacity<StackedIngredient>(ingredients.size)
            for (ingredient in ingredients) ingredientsStupidMojangList.add(ingredient)
            val results = GsonHelper.getAsJsonArray(json, "results").map { ConstructingResult.fromJson(it.asJsonObject) }
            val tierLower = GsonHelper.getAsInt(json, "tierLower")
            val tierUpper = GsonHelper.getAsInt(json, "tierUpper")
            return if (json.has("overlay")) {
                val overlay = try {
                    OverlayType.valueOf(GsonHelper.getAsString(json, "overlay").uppercase())
                } catch (e: IllegalArgumentException) {
                    throw JsonSyntaxException("Unexpected value for overlay type", e)
                }
                AnvilConstructingRecipe(id, ingredientsStupidMojangList, results, tierLower, tierUpper, overlay)
            } else when {
                ingredients.size == 1 && results.size == 1 -> AnvilConstructingRecipe(id, ingredientsStupidMojangList.first(), results.first(), tierLower, tierUpper)
                ingredients.size == 1 -> AnvilConstructingRecipe(id, ingredientsStupidMojangList.first(), results, tierLower, tierUpper)
                results.size == 1 -> AnvilConstructingRecipe(id, ingredientsStupidMojangList, results.first(), tierLower, tierUpper)
                else -> AnvilConstructingRecipe(id, ingredientsStupidMojangList, results, tierLower, tierUpper)
            }
        }

        override fun fromNetwork(id: ResourceLocation, buffer: FriendlyByteBuf): AnvilConstructingRecipe {
            val ingredients = buffer.readList { Ingredient.fromNetwork(it) }.map { it as? StackedIngredient ?: throw IllegalStateException("Received non-StackedIngredient over network") }
            val ingredientsStupidMojangList = NonNullList.createWithCapacity<StackedIngredient>(ingredients.size)
            for (ingredient in ingredients) ingredientsStupidMojangList.add(ingredient)
            val results = buffer.readList { ConstructingResult.fromNetwork(it) }
            val tierLower = buffer.readInt()
            val tierUpper = buffer.readInt()
            val overlay = buffer.readEnum(OverlayType::class.java)
            return AnvilConstructingRecipe(id, ingredientsStupidMojangList, results, tierLower, tierUpper, overlay)
        }

        override fun toNetwork(buffer: FriendlyByteBuf, recipe: AnvilConstructingRecipe) {
            buffer.writeCollection(recipe.ingredientsList) { packet, ingredient -> ingredient.toNetwork(packet) }
            buffer.writeCollection(recipe.results) { packet, result -> result.toNetwork(packet) }
            buffer.writeInt(recipe.tierLower)
            buffer.writeInt(recipe.tierUpper)
            buffer.writeEnum(recipe.overlay)
        }
    }
}
