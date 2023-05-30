package at.martinthedragon.nucleartech.datagen.recipe

import at.martinthedragon.nucleartech.mc
import at.martinthedragon.nucleartech.recipe.RecipeSerializers
import com.google.gson.JsonObject
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.CriterionTriggerInstance
import net.minecraft.advancements.RequirementsStrategy
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Consumer

class ShreddingRecipeBuilder(private val result: Item, val experience: Float, val count: Int, val ingredient: Ingredient) : RecipeBuilder {
    constructor(result: ItemLike, experience: Float, count: Int, ingredient: Ingredient) : this(result.asItem(), experience, count, ingredient)

    private val advancement = Advancement.Builder.advancement()

    override fun unlockedBy(criterionName: String, criterion: CriterionTriggerInstance): RecipeBuilder {
        advancement.addCriterion(criterionName, criterion)
        return this
    }

    override fun group(group: String?) = this

    override fun getResult() = result

    override fun save(consumer: Consumer<FinishedRecipe>, recipeName: ResourceLocation) {
        if (advancement.criteria.isEmpty()) throw throw IllegalStateException("No way of obtaining recipe $recipeName")
        advancement.parent(mc("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeName)).rewards(AdvancementRewards.Builder.recipe(recipeName)).requirements(RequirementsStrategy.OR)
        consumer.accept(Result(recipeName, result, count, experience, ingredient, advancement, ResourceLocation(recipeName.namespace, "recipes/${result.itemCategory?.recipeFolderName}/${recipeName.path}")))
    }

    class Result(
        private val id: ResourceLocation,
        private val result: Item,
        private val count: Int,
        private val experience: Float,
        private val ingredient: Ingredient,
        private val advancement: Advancement.Builder,
        private val advancementID: ResourceLocation
    ) : FinishedRecipe {
        override fun serializeRecipeData(json: JsonObject) {
            json.add("ingredient", ingredient.toJson())
            val resultJson = JsonObject()
            resultJson.addProperty("item", ForgeRegistries.ITEMS.getKey(result).toString())
            resultJson.addProperty("count", count)
            json.add("result", resultJson)
            json.addProperty("experience", experience)
        }

        override fun getId() = id

        override fun getType() = RecipeSerializers.SHREDDING.get()

        override fun getAdvancementId() = advancementID

        override fun serializeAdvancement(): JsonObject = advancement.serializeToJson()
    }
}
