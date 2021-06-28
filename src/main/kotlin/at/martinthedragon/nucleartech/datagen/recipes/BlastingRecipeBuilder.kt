package at.martinthedragon.nucleartech.datagen.recipes

import at.martinthedragon.nucleartech.recipes.RecipeSerializers
import com.google.gson.JsonObject
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.ICriterionInstance
import net.minecraft.advancements.IRequirementsStrategy
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger
import net.minecraft.data.IFinishedRecipe
import net.minecraft.item.Item
import net.minecraft.item.crafting.Ingredient
import net.minecraft.tags.ITag
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Consumer

class BlastingRecipeBuilder(val result: Item, val experience: Float, val count: Int = 1) {
    constructor(result: IItemProvider, experience: Float, count: Int = 1) : this(result.asItem(), experience, count)

    private lateinit var ingredient1: Ingredient
    private lateinit var ingredient2: Ingredient
    private val advancement = Advancement.Builder.advancement()

    fun requiresFirst(tag: ITag<Item>): BlastingRecipeBuilder = requiresFirst(Ingredient.of(tag))

    fun requiresFirst(item: IItemProvider): BlastingRecipeBuilder = requiresFirst(Ingredient.of(item))

    fun requiresFirst(ingredient: Ingredient): BlastingRecipeBuilder {
        this.ingredient1 = ingredient
        return this
    }

    fun requiresSecond(tag: ITag<Item>): BlastingRecipeBuilder = requiresSecond(Ingredient.of(tag))

    fun requiresSecond(item: IItemProvider): BlastingRecipeBuilder = requiresSecond(Ingredient.of(item))

    fun requiresSecond(ingredient: Ingredient): BlastingRecipeBuilder {
        this.ingredient2 = ingredient
        return this
    }

    fun unlockedBy(criterionName: String, criterion: ICriterionInstance): BlastingRecipeBuilder {
        advancement.addCriterion(criterionName, criterion)
        return this
    }

    fun save(consumer: Consumer<IFinishedRecipe>, recipeName: String) {
        save(consumer, ResourceLocation(recipeName))
    }

    fun save(consumer: Consumer<IFinishedRecipe>, recipeName: ResourceLocation = ForgeRegistries.ITEMS.getKey(result) ?: throw IllegalArgumentException("Result item '${result.registryName}' was not registered")) {
        if (advancement.criteria.isEmpty()) throw IllegalStateException("No way of obtaining recipe $recipeName")
        advancement.parent(ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeName)).rewards(
            AdvancementRewards.Builder.recipe(recipeName)).requirements(IRequirementsStrategy.OR)
        if (!this::ingredient1.isInitialized || !this::ingredient2.isInitialized) throw IllegalStateException("Not enough ingredients specified for recipe $recipeName specified")
        consumer.accept(Result(recipeName, result, count, experience, ingredient1, ingredient2, advancement, ResourceLocation(recipeName.namespace, "recipes/${result.itemCategory?.recipeFolderName}/${recipeName.path}")))
    }

    class Result(
        private val id: ResourceLocation,
        private val result: Item,
        private val count: Int,
        private val experience: Float,
        private val ingredient1: Ingredient,
        private val ingredient2: Ingredient,
        private val advancement: Advancement.Builder,
        private val advancementID: ResourceLocation
    ) : IFinishedRecipe {
        override fun serializeRecipeData(jsonObject: JsonObject) {
            jsonObject.add("first_ingredient", ingredient1.toJson())
            jsonObject.add("second_ingredient", ingredient2.toJson())
            val resultObject = JsonObject()
            resultObject.addProperty("item", ForgeRegistries.ITEMS.getKey(result).toString())
            resultObject.addProperty("count", count)
            jsonObject.add("result", resultObject)
            jsonObject.addProperty("experience", experience)
        }

        override fun getId() = id

        override fun getType() = RecipeSerializers.BLASTING.get()

        override fun serializeAdvancement(): JsonObject? = advancement.serializeToJson()

        override fun getAdvancementId() = advancementID
    }
}
