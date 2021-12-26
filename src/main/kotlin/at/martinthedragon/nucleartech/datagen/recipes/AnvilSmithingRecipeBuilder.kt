package at.martinthedragon.nucleartech.datagen.recipes

import at.martinthedragon.nucleartech.recipes.RecipeSerializers
import at.martinthedragon.nucleartech.recipes.StackedIngredient
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
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Consumer

class AnvilSmithingRecipeBuilder(private val tier: Int, private val result: ItemStack) : RecipeBuilder {
    constructor(tier: Int, result: ItemLike) : this(tier, ItemStack(result))
    constructor(tier: Int, result: Item) : this(tier, ItemStack(result))

    private lateinit var left: StackedIngredient
    private lateinit var right: StackedIngredient
    private val advancement = Advancement.Builder.advancement()

    fun requiresFirst(tag: Tag<Item>, amount: Int): AnvilSmithingRecipeBuilder = requiresFirst(StackedIngredient.of(amount, tag))
    fun requiresFirst(itemLike: ItemLike, amount: Int): AnvilSmithingRecipeBuilder = requiresFirst(StackedIngredient.of(amount, itemLike))
    fun requiresFirst(ingredient: StackedIngredient): AnvilSmithingRecipeBuilder {
        left = ingredient
        return this
    }

    fun requiresSecond(tag: Tag<Item>, amount: Int): AnvilSmithingRecipeBuilder = requiresSecond(StackedIngredient.of(amount, tag))
    fun requiresSecond(itemLike: ItemLike, amount: Int): AnvilSmithingRecipeBuilder = requiresSecond(StackedIngredient.of(amount, itemLike))
    fun requiresSecond(ingredient: StackedIngredient): AnvilSmithingRecipeBuilder {
        right = ingredient
        return this
    }

    override fun unlockedBy(criterionName: String, criterion: CriterionTriggerInstance): RecipeBuilder {
        advancement.addCriterion(criterionName, criterion)
        return this
    }

    override fun group(group: String?) = this
    override fun getResult(): Item = result.item

    override fun save(consumer: Consumer<FinishedRecipe>, recipeName: ResourceLocation) {
        if (advancement.criteria.isEmpty()) throw IllegalStateException("No way of obtaining recipe $recipeName")
        advancement.parent(ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeName)).rewards(AdvancementRewards.Builder.recipe(recipeName)).requirements(RequirementsStrategy.OR)
        if (!this::left.isInitialized || !this::right.isInitialized) throw IllegalStateException("Not enough ingredients specified for recipe $recipeName")
        consumer.accept(Result(recipeName, tier, result, left, right, advancement, ResourceLocation(recipeName.namespace, "recipes/${result.item.itemCategory?.recipeFolderName}/${recipeName.path}")))
    }

    private class Result(
        private val id: ResourceLocation,
        private val tier: Int,
        private val result: ItemStack,
        private val left: Ingredient,
        private val right: Ingredient,
        private val advancement: Advancement.Builder,
        private val advancementID: ResourceLocation
    ) : FinishedRecipe {
        override fun serializeRecipeData(json: JsonObject) {
            json.addProperty("tier", tier)
            json.add("left", left.toJson())
            json.add("right", right.toJson())
            json.add("result", JsonObject().apply {
                addProperty("item", ForgeRegistries.ITEMS.getKey(result.item).toString())
                addProperty("count", result.count)
                if (result.tag != null) addProperty("nbt", result.tag!!.toString())
            })
        }

        override fun getId() = id
        override fun getType() = RecipeSerializers.SMITHING.get()
        override fun serializeAdvancement(): JsonObject? = advancement.serializeToJson()
        override fun getAdvancementId() = advancementID
    }
}
