package at.martinthedragon.ntm.datagen

import com.google.gson.JsonObject
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.IRequirementsStrategy
import net.minecraft.advancements.criterion.CriterionInstance
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger
import net.minecraft.data.IFinishedRecipe
import net.minecraft.item.Item
import net.minecraft.item.crafting.CookingRecipeSerializer
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.IItemProvider
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Consumer

// Groups can actually be used, and the result of a furnace recipe can be multiple items
class ExtendedCookingRecipeBuilder(
    private val ingredient: Ingredient,
    private val experience: Float,
    private val cookingTime: Int,
    private val result: Item,
    private val resultCount: Int = 1,
    private val serializer: CookingRecipeSerializer<*> = IRecipeSerializer.SMELTING_RECIPE
) {
    constructor(
        ingredient: Ingredient,
        experience: Float,
        cookingTime: Int,
        result: IItemProvider,
        resultCount: Int = 1,
        serializer: CookingRecipeSerializer<*> = IRecipeSerializer.SMELTING_RECIPE
    ) : this(ingredient, experience, cookingTime, result.asItem(), resultCount, serializer)

    private val advancement = Advancement.Builder.advancement()
    private var group = ""

    fun unlockedBy(criterionName: String, criterionInstance: CriterionInstance): ExtendedCookingRecipeBuilder {
        advancement.addCriterion(criterionName, criterionInstance)
        return this
    }

    fun group(groupName: String): ExtendedCookingRecipeBuilder {
        group = groupName
        return this
    }

    fun save(consumer: Consumer<IFinishedRecipe>) =
        save(consumer, ForgeRegistries.ITEMS.getKey(result) ?: throw IllegalStateException("Item '${result.registryName}' hasn't been registered yet"))

    fun save(consumer: Consumer<IFinishedRecipe>, recipeName: String) =
        save(consumer, ResourceLocation(recipeName))

    fun save(consumer: Consumer<IFinishedRecipe>, recipeName: ResourceLocation) {
        if (advancement.criteria.isEmpty()) throw IllegalStateException("No way of obtaining recipe $recipeName")

        advancement.parent(ResourceLocation("recipes/root"))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeName))
            .rewards(AdvancementRewards.Builder.recipe(recipeName))
            .requirements(IRequirementsStrategy.OR)

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
        private val serializer: CookingRecipeSerializer<*>
    ) : IFinishedRecipe {


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

        override fun getType(): IRecipeSerializer<*> = serializer

        override fun serializeAdvancement(): JsonObject = advancement.serializeToJson()

        override fun getAdvancementId(): ResourceLocation = advancementID

    }
}
