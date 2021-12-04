package at.martinthedragon.nucleartech.datagen.recipes

import at.martinthedragon.nucleartech.recipes.RecipeSerializers
import com.google.gson.JsonObject
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.CriterionTriggerInstance
import net.minecraft.advancements.RequirementsStrategy
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.Tag
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Consumer

class BlastingRecipeBuilder(private val result: Item, val experience: Float, val count: Int = 1) : RecipeBuilder {
    constructor(result: ItemLike, experience: Float, count: Int = 1) : this(result.asItem(), experience, count)

    private lateinit var ingredient1: Ingredient
    private lateinit var ingredient2: Ingredient
    private val advancement = Advancement.Builder.advancement()

    fun requiresFirst(tag: Tag<Item>): BlastingRecipeBuilder = requiresFirst(Ingredient.of(tag))

    fun requiresFirst(item: ItemLike): BlastingRecipeBuilder = requiresFirst(Ingredient.of(item))

    fun requiresFirst(ingredient: Ingredient): BlastingRecipeBuilder {
        this.ingredient1 = ingredient
        return this
    }

    fun requiresSecond(tag: Tag<Item>): BlastingRecipeBuilder = requiresSecond(Ingredient.of(tag))

    fun requiresSecond(item: ItemLike): BlastingRecipeBuilder = requiresSecond(Ingredient.of(item))

    fun requiresSecond(ingredient: Ingredient): BlastingRecipeBuilder {
        this.ingredient2 = ingredient
        return this
    }

    override fun unlockedBy(criterionName: String, criterion: CriterionTriggerInstance): BlastingRecipeBuilder {
        advancement.addCriterion(criterionName, criterion)
        return this
    }

    override fun group(group: String?) = this

    override fun getResult() = result

    override fun save(consumer: Consumer<FinishedRecipe>, recipeName: ResourceLocation) {
        if (advancement.criteria.isEmpty()) throw IllegalStateException("No way of obtaining recipe $recipeName")
        advancement.parent(ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeName)).rewards(AdvancementRewards.Builder.recipe(recipeName)).requirements(RequirementsStrategy.OR)
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
    ) : FinishedRecipe {
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
