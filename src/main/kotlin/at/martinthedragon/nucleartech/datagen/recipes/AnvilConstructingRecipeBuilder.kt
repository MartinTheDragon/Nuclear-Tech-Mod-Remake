package at.martinthedragon.nucleartech.datagen.recipes

import at.martinthedragon.nucleartech.recipes.RecipeSerializers
import at.martinthedragon.nucleartech.recipes.StackedIngredient
import at.martinthedragon.nucleartech.recipes.anvil.AnvilConstructingRecipe
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.CriterionTriggerInstance
import net.minecraft.advancements.RequirementsStrategy
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger
import net.minecraft.core.NonNullList
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Consumer

class AnvilConstructingRecipeBuilder(val tierLower: Int = 1, val tierUpper: Int = -1) : RecipeBuilder {
    private lateinit var ingredients: NonNullList<StackedIngredient>
    private lateinit var output: Collection<AnvilConstructingRecipe.ConstructingResult>
    private var overlay: AnvilConstructingRecipe.OverlayType? = null
    private val advancement = Advancement.Builder.advancement()

    fun requires(vararg requirements: StackedIngredient) = requires(requirements.toList())
    fun requires(requirement: StackedIngredient) = requires(NonNullList.create<StackedIngredient>().apply { add(requirement) })
    fun requires(requirements: Collection<StackedIngredient>) = requires(NonNullList.createWithCapacity<StackedIngredient?>(requirements.size).apply { addAll(requirements) })
    fun requires(requirements: NonNullList<StackedIngredient>): AnvilConstructingRecipeBuilder {
        if (this::ingredients.isInitialized) throw IllegalStateException("Required ingredients already specified")
        ingredients = requirements
        return this
    }

    fun results(vararg result: AnvilConstructingRecipe.ConstructingResult) = results(result.toList())
    fun results(result: AnvilConstructingRecipe.ConstructingResult) = results(listOf(result))
    fun results(results: Collection<AnvilConstructingRecipe.ConstructingResult>): AnvilConstructingRecipeBuilder {
        if (this::output.isInitialized) throw IllegalStateException("Results already specified")
        output = results
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

    override fun group(group: String?) = this
    override fun getResult(): Item = output.first().stack.item

    override fun save(consumer: Consumer<FinishedRecipe>, recipeName: ResourceLocation) {
        if (advancement.criteria.isEmpty()) throw IllegalStateException("No way of obtaining recipe $recipeName")
        advancement.parent(ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeName)).rewards(AdvancementRewards.Builder.recipe(recipeName)).requirements(RequirementsStrategy.OR)
        if (!this::ingredients.isInitialized || !this::output.isInitialized) throw IllegalStateException("Not enough data specified")
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
