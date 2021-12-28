package at.martinthedragon.nucleartech.datagen.recipes

import at.martinthedragon.nucleartech.mc
import at.martinthedragon.nucleartech.recipes.PressRecipe
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

class PressRecipeBuilder(private val result: Item, val stampType: PressRecipe.StampType, val experience: Float, val count: Int = 1) : RecipeBuilder {
    constructor(result: ItemLike, stampType: PressRecipe.StampType, experience: Float, count: Int = 1) : this(result.asItem(), stampType, experience, count)

    private lateinit var ingredient: Ingredient
    private val advancement = Advancement.Builder.advancement()

    fun requires(tag: Tag<Item>): PressRecipeBuilder = requires(Ingredient.of(tag))

    fun requires(item: ItemLike): PressRecipeBuilder = requires(Ingredient.of(item))

    fun requires(ingredient: Ingredient): PressRecipeBuilder {
        this.ingredient = ingredient
        return this
    }

    override fun unlockedBy(criterionName: String, criterion: CriterionTriggerInstance): PressRecipeBuilder {
        advancement.addCriterion(criterionName, criterion)
        return this
    }

    override fun group(group: String?) = this

    override fun getResult() = result

    override fun save(consumer: Consumer<FinishedRecipe>, recipeName: ResourceLocation) {
        if (advancement.criteria.isEmpty()) throw IllegalStateException("No way of obtaining recipe $recipeName")
        advancement.parent(mc("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeName)).rewards(AdvancementRewards.Builder.recipe(recipeName)).requirements(RequirementsStrategy.OR)
        if (!this::ingredient.isInitialized) throw IllegalStateException("No ingredient for recipe $recipeName specified")
        consumer.accept(Result(recipeName, result, count, experience, stampType, ingredient, advancement, ResourceLocation(recipeName.namespace, "recipes/${result.itemCategory?.recipeFolderName}/${recipeName.path}")))
    }

    class Result(
        private val id: ResourceLocation,
        private val result: Item,
        private val count: Int,
        private val experience: Float,
        private val stampType: PressRecipe.StampType,
        private val ingredient: Ingredient,
        private val advancement: Advancement.Builder,
        private val advancementID: ResourceLocation
    ) : FinishedRecipe {
        override fun serializeRecipeData(rootObject: JsonObject) {
            rootObject.add("ingredient", ingredient.toJson())
            rootObject.addProperty("stamp_type", stampType.name)
            val resultObject = JsonObject()
            resultObject.addProperty("item", ForgeRegistries.ITEMS.getKey(result).toString())
            resultObject.addProperty("count", count)
            rootObject.add("result", resultObject)
            rootObject.addProperty("experience", experience)
        }

        override fun getType() = RecipeSerializers.PRESSING.get()

        override fun getId() = id

        override fun serializeAdvancement(): JsonObject? = advancement.serializeToJson()

        override fun getAdvancementId(): ResourceLocation = advancementID
    }
}
