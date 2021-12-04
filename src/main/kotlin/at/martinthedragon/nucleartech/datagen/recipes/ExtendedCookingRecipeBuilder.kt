package at.martinthedragon.nucleartech.datagen.recipes

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
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.SimpleCookingSerializer
import net.minecraft.world.level.ItemLike
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Consumer

// Groups can actually be used, and the result of a furnace recipe can be multiple random
class ExtendedCookingRecipeBuilder(
    private val ingredient: Ingredient,
    private val experience: Float,
    private val cookingTime: Int,
    private val result: Item,
    private val resultCount: Int = 1,
    private val serializer: SimpleCookingSerializer<*> = RecipeSerializer.SMELTING_RECIPE
) : RecipeBuilder {
    constructor(
        ingredient: Ingredient,
        experience: Float,
        cookingTime: Int,
        result: ItemLike,
        resultCount: Int = 1,
        serializer: SimpleCookingSerializer<*> = RecipeSerializer.SMELTING_RECIPE
    ) : this(ingredient, experience, cookingTime, result.asItem(), resultCount, serializer)

    private val advancement = Advancement.Builder.advancement()
    private var group = ""

    override fun unlockedBy(criterionName: String, criterionInstance: CriterionTriggerInstance): ExtendedCookingRecipeBuilder {
        advancement.addCriterion(criterionName, criterionInstance)
        return this
    }

    override fun getResult() = result

    override fun group(groupName: String?): ExtendedCookingRecipeBuilder {
        group = groupName ?: ""
        return this
    }

    override fun save(consumer: Consumer<FinishedRecipe>, recipeName: ResourceLocation) {
        if (advancement.criteria.isEmpty()) throw IllegalStateException("No way of obtaining recipe $recipeName")

        advancement.parent(ResourceLocation("recipes/root"))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeName))
            .rewards(AdvancementRewards.Builder.recipe(recipeName))
            .requirements(RequirementsStrategy.OR)

        consumer.accept(
            Result(
                recipeName, group, ingredient, result, resultCount, experience, cookingTime, advancement,
                ResourceLocation(recipeName.namespace, "recipes/${result.itemCategory?.recipeFolderName}/${recipeName.path}"),
                serializer
            )
        )
    }

    class Result(
        private val id: ResourceLocation,
        private val group: String,
        private val ingredient: Ingredient,
        private val result: Item,
        private val resultCount: Int,
        private val experience: Float,
        private val cookingTime: Int,
        private val advancement: Advancement.Builder,
        private val advancementID: ResourceLocation,
        private val serializer: SimpleCookingSerializer<*>
    ) : FinishedRecipe {
        override fun serializeRecipeData(rootObject: JsonObject) {
            if (group.isNotBlank())
                rootObject.addProperty("group", group)

            rootObject.add("ingredient", ingredient.toJson())
            if (resultCount <= 1) {
                rootObject.addProperty("result", ForgeRegistries.ITEMS.getKey(result).toString())
            } else {
                val resultObject = JsonObject()
                resultObject.addProperty("item", ForgeRegistries.ITEMS.getKey(result).toString())
                resultObject.addProperty("count", resultCount)
                rootObject.add("result", resultObject)
            }
            rootObject.addProperty("experience", experience)
            rootObject.addProperty("cookingtime", cookingTime)
        }

        override fun getId(): ResourceLocation = id

        override fun getType(): RecipeSerializer<*> = serializer

        override fun serializeAdvancement(): JsonObject = advancement.serializeToJson()

        override fun getAdvancementId(): ResourceLocation = advancementID

    }
}
