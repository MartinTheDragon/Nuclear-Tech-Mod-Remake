package at.martinthedragon.nucleartech.datagen.recipe

import at.martinthedragon.nucleartech.mc
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.recipe.RecipeSerializers
import at.martinthedragon.nucleartech.recipe.StackedIngredient
import at.martinthedragon.nucleartech.recipe.anvil.AnvilConstructingRecipe
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.CriterionTriggerInstance
import net.minecraft.advancements.RequirementsStrategy
import net.minecraft.advancements.critereon.*
import net.minecraft.core.NonNullList
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Consumer

class AnvilConstructingRecipeBuilder(val tierLower: Int = 1, val tierUpper: Int = -1) : RecipeBuilder {
    private val ingredients: NonNullList<StackedIngredient> = NonNullList.create()
    private val output = mutableListOf<AnvilConstructingRecipe.ConstructingResult>()
    private var overlay: AnvilConstructingRecipe.OverlayType? = null
    private val advancement = Advancement.Builder.advancement()

    fun requires(vararg item: ItemLike) = requires(item.map { StackedIngredient.of(1, it) }).also { if (advancement.criteria.isEmpty()) unlockedBy("has_${item.first().asItem().registryName!!.path}", has(item.first())) }
    fun requires(vararg tag: TagKey<Item>) = requires(tag.map { StackedIngredient.of(1, it) }).also { if (advancement.criteria.isEmpty()) unlockedBy("has_primary_passed_tag", has(tag.first())) }
    @JvmName("requiresItemPair")
    fun requires(vararg requirements: Pair<Int, ItemLike>) = requires(requirements.map { (amount, item) -> StackedIngredient.of(amount, item) }).also { if (advancement.criteria.isEmpty()) unlockedBy("has_${requirements.first().second.asItem().registryName!!.path}", has(requirements.first().second)) }
    @JvmName("requiresStackPair")
    fun requires(vararg requirements: Pair<Int, ItemStack>) = requires(requirements.map { (amount, stack) -> StackedIngredient.of(amount, stack) }).also { if (advancement.criteria.isEmpty()) unlockedBy("has_${requirements.first().second.item.registryName!!.path}", has(requirements.first().second.item)) }
    @JvmName("requiresTagPair")
    fun requires(vararg requirements: Pair<Int, TagKey<Item>>) = requires(requirements.map { (amount, tag) -> StackedIngredient.of(amount, tag) }).also { if (advancement.criteria.isEmpty()) unlockedBy("has_primary_passed_tag", has(requirements.first().second)) }
    fun requires(vararg requirements: StackedIngredient) = requires(requirements.toList())
    fun requires(requirements: Collection<StackedIngredient>): AnvilConstructingRecipeBuilder {
        ingredients += requirements
        return this
    }

    fun results(vararg result: AnvilConstructingRecipe.ConstructingResult) = results(result.toList())
    fun results(result: AnvilConstructingRecipe.ConstructingResult) = results(listOf(result))
    fun results(vararg result: ItemLike) = results(*result.map(::ItemStack).toTypedArray())
    @JvmName("resultsItemPair")
    fun results(vararg result: Pair<ItemLike, Float>): AnvilConstructingRecipeBuilder = results(*result.map { (item, chance) -> ItemStack(item) to chance }.toTypedArray())
    fun results(vararg result: ItemStack) = results(result.map { AnvilConstructingRecipe.ConstructingResult(it, 1F) })
    @JvmName("resultsStackPair")
    fun results(vararg result: Pair<ItemStack, Float>) = results(result.map { (stack, chance) -> AnvilConstructingRecipe.ConstructingResult(stack, chance) })
    fun results(results: Collection<AnvilConstructingRecipe.ConstructingResult>): AnvilConstructingRecipeBuilder {
        output += results
        return this
    }

    fun setOverlay(overlay: AnvilConstructingRecipe.OverlayType): AnvilConstructingRecipeBuilder {
        this.overlay = overlay
        return this
    }

    override fun unlockedBy(criterionName: String, criterion: CriterionTriggerInstance): RecipeBuilder {
        advancement.addCriterion(criterionName, criterion)
        return this
    }

    private fun has(item: ItemLike) = inventoryTrigger(ItemPredicate.Builder.item().of(item).build())
    private fun has(tag: TagKey<Item>) = inventoryTrigger(ItemPredicate.Builder.item().of(tag).build())
    private fun inventoryTrigger(vararg requirements: ItemPredicate) = InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, requirements)

    override fun group(group: String?) = this
    override fun getResult(): Item = output.first().stack.item

    override fun save(consumer: Consumer<FinishedRecipe>) = save(consumer, ntm(if ((output.size > 1 && ingredients.size == 1) || overlay == AnvilConstructingRecipe.OverlayType.RECYCLING) "${RecipeBuilder.getDefaultRecipeId(ingredients.first().items.first().item).path}_anvil_recycling" else "${RecipeBuilder.getDefaultRecipeId(result).path}_from_anvil_constructing"))
    override fun save(consumer: Consumer<FinishedRecipe>, name: String) = save(consumer, ntm("${name}_from_anvil_constructing"))

    override fun save(consumer: Consumer<FinishedRecipe>, recipeName: ResourceLocation) {
        if (advancement.criteria.isEmpty()) throw IllegalStateException("No way of obtaining recipe $recipeName")
        advancement.parent(mc("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeName)).rewards(AdvancementRewards.Builder.recipe(recipeName)).requirements(RequirementsStrategy.OR)
        if (ingredients.isEmpty() || output.isEmpty()) throw IllegalStateException("Not enough data specified")
        consumer.accept(Result(recipeName, tierLower, tierUpper, ingredients, output, overlay, advancement, ResourceLocation(recipeName.namespace, "recipes/${result.itemCategory?.recipeFolderName}/${recipeName.path}")))
    }

    private class Result(
        private val id: ResourceLocation,
        private val tierLower: Int,
        private val tierUpper: Int,
        private val ingredients: NonNullList<StackedIngredient>,
        private val results: Collection<AnvilConstructingRecipe.ConstructingResult>,
        private val overlay: AnvilConstructingRecipe.OverlayType?,
        private val advancement: Advancement.Builder,
        private val advancementID: ResourceLocation,
    ) : FinishedRecipe {
        override fun serializeRecipeData(json: JsonObject) {
            json.add("ingredients", JsonArray().apply { for (ingredient in ingredients) add(ingredient.toJson()) })
            json.add("results", JsonArray().apply { for (result in results) add(JsonObject().apply {
                add("item", JsonObject().apply {
                    addProperty("item", ForgeRegistries.ITEMS.getKey(result.stack.item).toString())
                    addProperty("count", result.stack.count)
                    if (result.stack.tag != null) addProperty("nbt", result.stack.tag!!.toString())
                })
                addProperty("chance", result.chance)
            })})
            json.addProperty("tierLower", tierLower)
            json.addProperty("tierUpper", tierUpper)
            if (overlay != null) json.addProperty("overlay", overlay.name)
        }

        override fun getId() = id
        override fun getType() = RecipeSerializers.CONSTRUCTING.get()
        override fun serializeAdvancement(): JsonObject? = advancement.serializeToJson()
        override fun getAdvancementId(): ResourceLocation = advancementID
    }
}
