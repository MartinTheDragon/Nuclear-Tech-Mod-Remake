package at.martinthedragon.nucleartech.datagen.recipes

import at.martinthedragon.nucleartech.recipes.RecipeSerializers
import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.google.common.collect.Sets
import com.google.gson.JsonArray
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

/*
* slightly modified copy of ShapedRecipeBuilder
* for the BatteryRecipe, which sums up the energy of the previous batteries
*/
class BatteryRecipeBuilder(p_i48261_1_: IItemProvider, p_i48261_2_: Int) {
    private val result: Item
    private val count: Int
    private val rows: MutableList<String> = Lists.newArrayList()
    private val key: MutableMap<Char, Ingredient> = Maps.newLinkedHashMap()
    private val advancement = Advancement.Builder.advancement()
    private var group: String = ""

    init {
        result = p_i48261_1_.asItem()
        count = p_i48261_2_
    }

    fun define(p_200469_1_: Char, p_200469_2_: ITag<Item>): BatteryRecipeBuilder {
        return this.define(p_200469_1_, Ingredient.of(p_200469_2_))
    }

    fun define(p_200462_1_: Char, p_200462_2_: IItemProvider): BatteryRecipeBuilder {
        return this.define(p_200462_1_, Ingredient.of(p_200462_2_))
    }

    fun define(p_200471_1_: Char, p_200471_2_: Ingredient): BatteryRecipeBuilder {
        return if (key.containsKey(p_200471_1_)) {
            throw IllegalArgumentException("Symbol '$p_200471_1_' is already defined!")
        } else if (p_200471_1_ == ' ') {
            throw IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined")
        } else {
            key[p_200471_1_] = p_200471_2_
            this
        }
    }

    fun pattern(p_200472_1_: String): BatteryRecipeBuilder {
        return if (!rows.isEmpty() && p_200472_1_.length != rows[0].length) {
            throw IllegalArgumentException("Pattern must be the same width on every line!")
        } else {
            rows.add(p_200472_1_)
            this
        }
    }

    fun unlockedBy(p_200465_1_: String, p_200465_2_: ICriterionInstance): BatteryRecipeBuilder {
        advancement.addCriterion(p_200465_1_, p_200465_2_)
        return this
    }

    fun group(p_200473_1_: String): BatteryRecipeBuilder {
        group = p_200473_1_
        return this
    }

    fun save(p_200464_1_: Consumer<IFinishedRecipe>) {
        this.save(
            p_200464_1_,
            ForgeRegistries.ITEMS.getKey(result)
                ?: throw IllegalStateException("No item ${result.registryName} registered")
        )
    }

    fun save(p_200466_1_: Consumer<IFinishedRecipe>, p_200466_2_: String) {
        val resourcelocation = ForgeRegistries.ITEMS.getKey(result)
            ?: throw IllegalStateException("No item ${result.registryName} registered")
        check(ResourceLocation(p_200466_2_) != resourcelocation) { "Shaped Recipe $p_200466_2_ should remove its 'save' argument" }
        this.save(p_200466_1_, ResourceLocation(p_200466_2_))
    }

    fun save(p_200467_1_: Consumer<IFinishedRecipe>, p_200467_2_: ResourceLocation) {
        ensureValid(p_200467_2_)
        advancement.parent(ResourceLocation("recipes/root"))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_200467_2_))
            .rewards(AdvancementRewards.Builder.recipe(p_200467_2_)).requirements(IRequirementsStrategy.OR)
        p_200467_1_.accept(
            Result(
                p_200467_2_,
                result,
                count,
                group,
                rows,
                key,
                advancement,
                ResourceLocation(
                    p_200467_2_.namespace,
                    "recipes/" + result.itemCategory!!.recipeFolderName + "/" + p_200467_2_.path
                )
            )
        )
    }

    private fun ensureValid(p_200463_1_: ResourceLocation) {
        check(rows.isNotEmpty()) { "No pattern is defined for shaped recipe $p_200463_1_!" }
        val set: MutableSet<Char> = Sets.newHashSet(key.keys)
        set.remove(' ')
        for (s in rows) {
            for (element in s) {
                check(!(!key.containsKey(element) && element != ' ')) { "Pattern in recipe $p_200463_1_ uses undefined symbol '$element'" }
                set.remove(element)
            }
        }
        check(set.isEmpty()) { "Ingredients are defined but not used in pattern for recipe $p_200463_1_" }
        check(!(rows.size == 1 && rows[0].length == 1)) { "Shaped recipe $p_200463_1_ only takes in a single item - should it be a shapeless recipe instead?" }
        check(advancement.criteria.isNotEmpty()) { "No way of obtaining recipe $p_200463_1_" }
    }

    class Result(
        private val id: ResourceLocation,
        private val result: Item,
        private val count: Int,
        private val group: String,
        private val pattern: List<String>,
        private val key: Map<Char, Ingredient>,
        private val advancement: Advancement.Builder,
        private val advancementId: ResourceLocation
    ) : IFinishedRecipe {
        override fun serializeRecipeData(p_218610_1_: JsonObject) {
            if (this.group.isNotEmpty()) {
                p_218610_1_.addProperty("group", this.group)
            }
            val jsonarray = JsonArray()
            for (s in pattern) {
                jsonarray.add(s)
            }
            p_218610_1_.add("pattern", jsonarray)
            val jsonobject = JsonObject()
            for ((key1, value) in this.key) {
                jsonobject.add(key1.toString(), value.toJson())
            }
            p_218610_1_.add("key", jsonobject)
            val jsonobject1 = JsonObject()
            jsonobject1.addProperty(
                "item",
                (ForgeRegistries.ITEMS.getKey(result)
                    ?: throw IllegalStateException("No item ${result.registryName} registered")).toString()
            )
            if (this.count > 1) {
                jsonobject1.addProperty("count", this.count)
            }
            p_218610_1_.add("result", jsonobject1)
        }

        override fun getType() = RecipeSerializers.BATTERY.get()

        override fun getId() = id

        override fun serializeAdvancement(): JsonObject? = this.advancement.serializeToJson()
        override fun getAdvancementId() = advancementId
    }

    companion object {

        fun battery(p_200470_0_: IItemProvider): BatteryRecipeBuilder {
            return battery(p_200470_0_, 1)
        }

        fun battery(p_200468_0_: IItemProvider, p_200468_1_: Int): BatteryRecipeBuilder {
            return BatteryRecipeBuilder(p_200468_0_, p_200468_1_)
        }
    }
}
