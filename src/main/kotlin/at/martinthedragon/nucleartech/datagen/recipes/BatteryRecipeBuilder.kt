package at.martinthedragon.nucleartech.datagen.recipes

import at.martinthedragon.nucleartech.mc
import at.martinthedragon.nucleartech.recipes.RecipeSerializers
import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.google.common.collect.Sets
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
import net.minecraft.tags.Tag
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Consumer

/*
* slightly modified copy of ShapedRecipeBuilder
* for the BatteryRecipe, which sums up the energy of the previous batteries
*/
class BatteryRecipeBuilder(private val result: Item, val count: Int) : RecipeBuilder {
    constructor(result: ItemLike, count: Int) : this(result.asItem(), count)

    private val rows: MutableList<String> = Lists.newArrayList()
    private val key: MutableMap<Char, Ingredient> = Maps.newLinkedHashMap()
    private val advancement = Advancement.Builder.advancement()
    private var group: String = ""

    fun define(char: Char, tag: Tag<Item>): BatteryRecipeBuilder {
        return this.define(char, Ingredient.of(tag))
    }

    fun define(char: Char, item: ItemLike): BatteryRecipeBuilder {
        return this.define(char, Ingredient.of(item))
    }

    fun define(char: Char, ingredient: Ingredient): BatteryRecipeBuilder {
        return if (key.containsKey(char)) {
            throw IllegalArgumentException("Symbol '$char' is already defined!")
        } else if (char == ' ') {
            throw IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined")
        } else {
            key[char] = ingredient
            this
        }
    }

    fun pattern(p_200472_1_: String): BatteryRecipeBuilder {
        return if (rows.isNotEmpty() && p_200472_1_.length != rows[0].length) {
            throw IllegalArgumentException("Pattern must be the same width on every line!")
        } else {
            rows.add(p_200472_1_)
            this
        }
    }

    override fun unlockedBy(p_200465_1_: String, p_200465_2_: CriterionTriggerInstance): BatteryRecipeBuilder {
        advancement.addCriterion(p_200465_1_, p_200465_2_)
        return this
    }

    override fun getResult() = result

    override fun group(group: String?): BatteryRecipeBuilder {
        this.group = group ?: ""
        return this
    }

    override fun save(consumer: Consumer<FinishedRecipe>, name: ResourceLocation) {
        ensureValid(name)
        advancement.parent(mc("recipes/root"))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(name))
            .rewards(AdvancementRewards.Builder.recipe(name)).requirements(RequirementsStrategy.OR)
        consumer.accept(Result(name, result, count, group, rows, key, advancement, ResourceLocation(name.namespace, "recipes/" + result.itemCategory!!.recipeFolderName + "/" + name.path)))
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
    ) : FinishedRecipe {
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

        fun battery(item: ItemLike): BatteryRecipeBuilder {
            return battery(item, 1)
        }

        fun battery(item: ItemLike, count: Int): BatteryRecipeBuilder {
            return BatteryRecipeBuilder(item, count)
        }
    }
}
