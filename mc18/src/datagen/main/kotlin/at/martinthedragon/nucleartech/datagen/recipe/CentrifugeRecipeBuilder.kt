package at.martinthedragon.nucleartech.datagen.recipe

import at.martinthedragon.nucleartech.extensions.toJson
import at.martinthedragon.nucleartech.mc
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.recipe.RecipeSerializers
import com.google.gson.JsonArray
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
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import java.util.function.Consumer

class CentrifugeRecipeBuilder(private val input: Ingredient, private vararg val outputs: ItemStack) : RecipeBuilder {
    constructor(input: Ingredient, vararg outputs: Item) : this(input, *outputs.map(Item::getDefaultInstance).toTypedArray())

    private val advancement = Advancement.Builder.advancement()

    override fun unlockedBy(criterionName: String, criterion: CriterionTriggerInstance): CentrifugeRecipeBuilder {
        advancement.addCriterion(criterionName, criterion)
        return this
    }

    override fun group(group: String?) = this
    override fun getResult(): Item = outputs.first().item

    override fun save(consumer: Consumer<FinishedRecipe>) = save(consumer, ntm("${RecipeBuilder.getDefaultRecipeId(result).path}_from_centrifuge"))
    override fun save(consumer: Consumer<FinishedRecipe>, name: String) = save(consumer, ntm("${name}_from_centrifuge"))

    override fun save(consumer: Consumer<FinishedRecipe>, recipeName: ResourceLocation) {
        if (advancement.criteria.isEmpty()) throw IllegalStateException("No way of obtaining recipe $recipeName")
        advancement.parent(mc("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeName)).rewards(AdvancementRewards.Builder.recipe(recipeName)).requirements(RequirementsStrategy.OR)
        if (outputs.isEmpty()) throw IllegalStateException("Not enough data specified")
        if (outputs.size > 4) throw IllegalStateException("Too many outputs")
        consumer.accept(Result(recipeName, input, outputs.toList(), advancement, ResourceLocation(recipeName.namespace, "recipes/${result.itemCategory?.recipeFolderName}/${recipeName.path}")))
    }

    private class Result(
        private val id: ResourceLocation,
        private val input: Ingredient,
        private val outputs: List<ItemStack>,
        private val advancement: Advancement.Builder,
        private val advancementID: ResourceLocation
    ) : FinishedRecipe {
        override fun serializeRecipeData(json: JsonObject) {
            json.add("ingredient", input.toJson())
            json.add("results", JsonArray().apply {
                for (output in outputs) add(output.toJson())
            })
        }

        override fun getId() = id
        override fun getType() = RecipeSerializers.CENTRIFUGE.get()
        override fun serializeAdvancement(): JsonObject? = advancement.serializeToJson()
        override fun getAdvancementId(): ResourceLocation = advancementID
    }
}
