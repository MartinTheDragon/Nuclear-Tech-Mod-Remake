package at.martinthedragon.nucleartech.datagen.recipes

import at.martinthedragon.nucleartech.mc
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.recipes.RecipeSerializers
import at.martinthedragon.nucleartech.recipes.StackedIngredient
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

class AssemblyRecipeBuilder(private val result: Item, private val count: Int, private val duration: Int) : RecipeBuilder {
    constructor(result: ItemStack, duration: Int) : this(result.item, result.count, duration)

    private val ingredients: NonNullList<StackedIngredient> = NonNullList.create()
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
    fun requires(requirements: Collection<StackedIngredient>): AssemblyRecipeBuilder {
        ingredients += requirements
        return this
    }

    override fun unlockedBy(criterionName: String, criterion: CriterionTriggerInstance): AssemblyRecipeBuilder {
        advancement.addCriterion(criterionName, criterion)
        return this
    }

    private fun has(item: ItemLike) = inventoryTrigger(ItemPredicate.Builder.item().of(item).build())
    private fun has(tag: TagKey<Item>) = inventoryTrigger(ItemPredicate.Builder.item().of(tag).build())
    private fun inventoryTrigger(vararg requirements: ItemPredicate) = InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, requirements)

    override fun group(group: String?) = this
    override fun getResult(): Item = result

    override fun save(consumer: Consumer<FinishedRecipe>) = save(consumer, ntm("${RecipeBuilder.getDefaultRecipeId(result).path}_from_assembling"))
    override fun save(consumer: Consumer<FinishedRecipe>, name: String) = save(consumer, ntm("${name}_from_assembling"))

    override fun save(consumer: Consumer<FinishedRecipe>, recipeName: ResourceLocation) {
        if (advancement.criteria.isEmpty()) throw IllegalStateException("No way of obtaining recipe $recipeName")
        advancement.parent(mc("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeName)).rewards(AdvancementRewards.Builder.recipe(recipeName)).requirements(RequirementsStrategy.OR)
        if (ingredients.isEmpty() || duration <= 0) throw IllegalStateException("Not enough data specified")
        consumer.accept(Result(recipeName, ingredients, result, count, duration, advancement, ResourceLocation(recipeName.namespace, "recipes/${getResult().itemCategory?.recipeFolderName}/${recipeName.path}")))
    }

    private class Result(
        private val id: ResourceLocation,
        private val ingredients: NonNullList<StackedIngredient>,
        private val result: Item,
        private val count: Int,
        private val duration: Int,
        private val advancement: Advancement.Builder,
        private val advancementID: ResourceLocation
    ) : FinishedRecipe {
        override fun serializeRecipeData(json: JsonObject) {
            json.add("ingredients", JsonArray().apply { for (ingredient in ingredients) add(ingredient.toJson()) })
            json.add("result", JsonObject().apply {
                addProperty("item", ForgeRegistries.ITEMS.getKey(result).toString())
                addProperty("count", count)
            })
            json.addProperty("duration", duration)
        }

        override fun getId() = id
        override fun getType() = RecipeSerializers.ASSEMBLY.get()
        override fun serializeAdvancement(): JsonObject? = advancement.serializeToJson()
        override fun getAdvancementId(): ResourceLocation = advancementID
    }

}
