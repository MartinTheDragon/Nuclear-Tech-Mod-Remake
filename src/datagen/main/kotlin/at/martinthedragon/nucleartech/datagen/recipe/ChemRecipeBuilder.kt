package at.martinthedragon.nucleartech.datagen.recipe

import at.martinthedragon.nucleartech.mc
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.recipe.RecipeSerializers
import at.martinthedragon.nucleartech.recipe.StackedIngredient
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.CriterionTriggerInstance
import net.minecraft.advancements.RequirementsStrategy
import net.minecraft.advancements.critereon.*
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Consumer

class ChemRecipeBuilder(private val duration: Int) : RecipeBuilder {
    private val ingredients = mutableListOf<StackedIngredient>()
    private val inputFluids = mutableListOf<FluidStack>()
    private val results = mutableListOf<ItemStack>()
    private val outputFluids = mutableListOf<FluidStack>()
    private var advancement: Advancement.Builder? = null
    private val advancementSpecified: Boolean get() = advancement?.criteria?.isEmpty() == false

    fun requires(item: ItemLike, amount: Int = 1): ChemRecipeBuilder {
        ingredients += StackedIngredient.of(amount, item)
        if (!advancementSpecified) unlockedBy("has_primary", has(item))
        return this
    }

    fun requires(item: ItemStack, amount: Int = 1): ChemRecipeBuilder {
        ingredients += StackedIngredient.of(amount, item)
        if (!advancementSpecified) unlockedBy("has_primary", has(item.item))
        return this
    }

    fun requires(itemTag: TagKey<Item>, amount: Int = 1): ChemRecipeBuilder {
        ingredients += StackedIngredient.of(amount, itemTag)
        if (!advancementSpecified) unlockedBy("has_primary", has(itemTag))
        return this
    }

    fun requires(fluidStack: FluidStack): ChemRecipeBuilder {
        inputFluids += fluidStack
        return this
    }

    fun requires(fluid: Fluid, amount: Int): ChemRecipeBuilder {
        inputFluids += FluidStack(fluid, amount)
        return this
    }

    fun results(item: ItemLike, amount: Int = 1): ChemRecipeBuilder {
        results += ItemStack(item, amount)
        return this
    }

    fun results(item: ItemStack): ChemRecipeBuilder {
        results += item
        return this
    }

    fun results(fluidStack: FluidStack): ChemRecipeBuilder {
        outputFluids += fluidStack
        return this
    }

    fun results(fluid: Fluid, amount: Int): ChemRecipeBuilder {
        outputFluids += FluidStack(fluid, amount)
        return this
    }

    override fun unlockedBy(critorionName: String, criterion: CriterionTriggerInstance): RecipeBuilder {
        if (advancement == null) advancement = Advancement.Builder.advancement()
        advancement?.addCriterion(critorionName, criterion)
        return this
    }

    private fun has(item: ItemLike) = inventoryTrigger(ItemPredicate.Builder.item().of(item).build())
    private fun has(tag: TagKey<Item>) = inventoryTrigger(ItemPredicate.Builder.item().of(tag).build())
    private fun inventoryTrigger(vararg requirements: ItemPredicate) = InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, requirements)

    override fun group(group: String?) = this
    override fun getResult(): Item = results.first().item

    override fun save(consumer: Consumer<FinishedRecipe>) {
        if (results.isNotEmpty()) {
            if (outputFluids.isNotEmpty()) save(consumer, ntm("${RecipeBuilder.getDefaultRecipeId(result).path}_and_${outputFluids.firstOrNull()?.fluid?.registryName?.path}_from_chemistry"))
            else save(consumer, ntm("${RecipeBuilder.getDefaultRecipeId(result).path}_from_chemistry"))
        } else save(consumer, ntm("${outputFluids.firstOrNull()?.fluid?.registryName?.path}_from_chemistry"))
    }

    override fun save(consumer: Consumer<FinishedRecipe>, recipeName: String) = save(consumer, ntm("${recipeName}_from_chemistry"))

    override fun save(consumer: Consumer<FinishedRecipe>, recipeName: ResourceLocation) {
        if (advancement?.criteria?.isEmpty() == true) throw IllegalStateException("No way of obtaining recipe $recipeName")
        advancement?.parent(mc("recipes/root"))?.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeName))?.rewards(AdvancementRewards.Builder.recipe(recipeName))?.requirements(RequirementsStrategy.OR)
        if (duration <= 0) throw IllegalStateException("Duration can't be zero or below")
        if (ingredients.isEmpty() && inputFluids.isEmpty()) throw IllegalStateException("No inputs for recipe $recipeName")
        if (results.isEmpty() && outputFluids.isEmpty()) throw IllegalStateException("No outputs for recipe $recipeName")
        if (ingredients.size > 4) throw IllegalStateException("More than four ingredients specified for recipe $recipeName")
        if (inputFluids.size > 2) throw IllegalStateException("More than two input fluids specified for recipe $recipeName")
        if (results.size > 4) throw IllegalStateException("More than four results specified for recipe $recipeName")
        if (outputFluids.size > 2) throw IllegalStateException("More than four results specified for recipe $recipeName")
        val inputFluid1 = inputFluids.getOrNull(0) ?: FluidStack.EMPTY
        val inputFluid2 = inputFluids.getOrNull(1) ?: FluidStack.EMPTY
        val outputFluid1 = outputFluids.getOrNull(0) ?: FluidStack.EMPTY
        val outputFluid2 = outputFluids.getOrNull(1) ?: FluidStack.EMPTY
        consumer.accept(Result(recipeName, ingredients, inputFluid1 to inputFluid2, results, outputFluid1 to outputFluid2, duration, advancement, ResourceLocation(recipeName.namespace, "recipes/${result.itemCategory?.recipeFolderName}/${recipeName.path}")))
    }

    private class Result(
        private val id: ResourceLocation,
        private val ingredients: List<StackedIngredient>,
        private val inputFluids: Pair<FluidStack, FluidStack>,
        private val results: List<ItemStack>,
        private val outputFluids: Pair<FluidStack, FluidStack>,
        private val duration: Int,
        private val advancement: Advancement.Builder?,
        private val advancementID: ResourceLocation?
    ) : FinishedRecipe {
        override fun serializeRecipeData(json: JsonObject) {
            json.add("ingredients", JsonArray().apply { for (ingredient in ingredients) add(ingredient.toJson()) })
            val (inputFluid1, inputFluid2) = inputFluids
            json.addProperty("inputFluid1", inputFluid1.writeToNBT(CompoundTag()).toString())
            json.addProperty("inputFluid2", inputFluid2.writeToNBT(CompoundTag()).toString())
            json.add("results", JsonArray().apply {
                for (result in results) {
                    val resultJson = JsonObject()
                    resultJson.addProperty("item", ForgeRegistries.ITEMS.getKey(result.item).toString())
                    if (result.count > 1) resultJson.addProperty("count", result.count)
                    if (result.hasTag()) resultJson.addProperty("nbt", result.tag.toString())
                    add(resultJson)
                }
            })
            val (outputFluid1, outputFluid2) = outputFluids
            json.addProperty("outputFluid1", outputFluid1.writeToNBT(CompoundTag()).toString())
            json.addProperty("outputFluid2", outputFluid2.writeToNBT(CompoundTag()).toString())
            json.addProperty("duration", duration)
        }

        override fun getId() = id
        override fun getType() = RecipeSerializers.CHEM.get()
        override fun serializeAdvancement(): JsonObject? = advancement?.serializeToJson()
        override fun getAdvancementId() = advancementID
    }
}
