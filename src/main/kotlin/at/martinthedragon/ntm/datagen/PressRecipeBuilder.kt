package at.martinthedragon.ntm.datagen

import at.martinthedragon.ntm.recipes.PressRecipe
import at.martinthedragon.ntm.recipes.RecipeSerializers
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

class PressRecipeBuilder(val result: Item, val stampType: PressRecipe.StampType, val experience: Float) {
    constructor(result: IItemProvider, stampType: PressRecipe.StampType, experience: Float) : this(result.asItem(), stampType, experience)

    private lateinit var ingredient: Ingredient
    private val advancement = Advancement.Builder.advancement()
    private var group: String = ""

    fun requires(tag: ITag<Item>): PressRecipeBuilder = requires(Ingredient.of(tag))

    fun requires(item: IItemProvider): PressRecipeBuilder = requires(Ingredient.of(item))

    fun requires(ingredient: Ingredient): PressRecipeBuilder {
        this.ingredient = ingredient
        return this
    }

    fun unlockedBy(criterionName: String, criterion: ICriterionInstance): PressRecipeBuilder {
        advancement.addCriterion(criterionName, criterion)
        return this
    }

    fun group(groupName: String): PressRecipeBuilder {
        group = groupName
        return this
    }

    fun save(consumer: Consumer<IFinishedRecipe>, recipeName: String) {
        save(consumer, ResourceLocation(recipeName))
    }

    fun save(consumer: Consumer<IFinishedRecipe>, recipeName: ResourceLocation = ForgeRegistries.ITEMS.getKey(result) ?: throw IllegalArgumentException("Result item '${result.registryName}' was not registered")) {
        if (advancement.criteria.isEmpty()) throw IllegalStateException("No way of obtaining recipe $recipeName")
        advancement.parent(ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeName)).rewards(AdvancementRewards.Builder.recipe(recipeName)).requirements(IRequirementsStrategy.OR)
        consumer.accept(Result(recipeName, result, experience, stampType, group, ingredient, advancement, ResourceLocation(recipeName.namespace, "recipes/${result.itemCategory?.recipeFolderName}")))
    }

    class Result(
        private val id: ResourceLocation,
        private val result: Item,
        private val experience: Float,
        private val stampType: PressRecipe.StampType,
        private val group: String,
        private val ingredient: Ingredient,
        private val advancement: Advancement.Builder,
        private val advancementID: ResourceLocation
    ) : IFinishedRecipe {
        override fun serializeRecipeData(rootObject: JsonObject) {
            if (group.isNotBlank()) {
                rootObject.addProperty("group", group)
            }

            rootObject.add("ingredient", ingredient.toJson())
            rootObject.addProperty("stamp_type", stampType.name)
            val resultObject = JsonObject()
            resultObject.addProperty("item", ForgeRegistries.ITEMS.getKey(result).toString())
            rootObject.add("result", resultObject)
            rootObject.addProperty("experience", experience)
        }

        override fun getType() = RecipeSerializers.PRESSING.get()

        override fun getId() = id

        override fun serializeAdvancement(): JsonObject? = advancement.serializeToJson()

        override fun getAdvancementId(): ResourceLocation = advancementID
    }
}
