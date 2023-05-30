package at.martinthedragon.nucleartech.recipe

import at.martinthedragon.nucleartech.ntm
import com.google.gson.*
import it.unimi.dsi.fastutil.ints.IntArrayList
import it.unimi.dsi.fastutil.ints.IntComparators
import it.unimi.dsi.fastutil.ints.IntList
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.tags.TagKey
import net.minecraft.util.GsonHelper
import net.minecraft.world.Container
import net.minecraft.world.entity.player.StackedContents
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraftforge.common.crafting.CraftingHelper
import net.minecraftforge.common.crafting.IIngredientSerializer
import java.util.stream.Stream
import java.util.stream.StreamSupport
import kotlin.math.min

class StackedIngredient private constructor(private val values: Sequence<Value>, val requiredAmount: Int) : Ingredient(Stream.empty()) {
    private val itemStacks: Array<ItemStack> by lazy { values.flatMap { it.items }.map(ItemStack::copy).onEach { it.count = requiredAmount }.toList().toTypedArray() }
    override fun getItems() = itemStacks

    override fun test(stack: ItemStack?): Boolean =
        if (stack == null) false
        else if (items.isEmpty()) stack.isEmpty
        else items.any { it.`is`(stack.item) && stack.count >= requiredAmount }

    fun testWithoutStackSize(stack: ItemStack?): Boolean =
        if (stack == null) false
        else if (items.isEmpty()) stack.isEmpty
        else items.any { it.`is`(stack.item) }

    private val stackingIdList: IntList by lazy {
        IntArrayList(items.size).apply {
            items.forEach { item -> add(StackedContents.getStackingIndex(item)) }
            sort(IntComparators.NATURAL_COMPARATOR)
        }
    }
    override fun getStackingIds() = stackingIdList

    override fun isEmpty(): Boolean = super.isEmpty() && (items.isEmpty() || items.all { it.isEmpty })

    override fun toJson(): JsonElement {
        val json = JsonObject()
        json.addProperty("type", CraftingHelper.getID(Serializer).toString())
        if (values.count() == 1) json.add("item", values.first().serialize())
        else json.add("items", JsonArray().apply { for (value in values) add(value.serialize()) })
        json.addProperty("amount", requiredAmount)
        return json
    }

    override fun getSerializer() = Serializer

    object Serializer : IIngredientSerializer<StackedIngredient> {
        override fun parse(buffer: FriendlyByteBuf) = StackedIngredient(buildList {
            for (i in 0 until buffer.readVarInt()) add(ItemValue(buffer.readItem()))
        }.asSequence(), buffer.readVarInt())

        override fun parse(json: JsonObject): StackedIngredient {
            if (json.has("item") && json.has("items")) throw JsonParseException("A StackedIngredient entry needs either an 'item' or an 'items' entry, not both")
            if (!json.has("amount")) throw JsonParseException("A StackedIngredient entry needs an 'amount' entry")
            val amount = GsonHelper.getAsInt(json, "amount")
            return if (json.has("item")) {
                fromValues(sequenceOf(valueFromJson(GsonHelper.getAsJsonObject(json, "item"))), amount)
            } else {
                val items = GsonHelper.getAsJsonArray(json, "items")
                if (items.isEmpty) throw JsonSyntaxException("Item array cannot be empty, at least one item must be defined")
                fromValues(StreamSupport.stream(items.spliterator(), false).map { valueFromJson(GsonHelper.convertToJsonObject(it, "item")) }.toList().asSequence(), amount)
            }
        }

        override fun write(buffer: FriendlyByteBuf, ingredient: StackedIngredient) {
            buffer.writeCollection(ingredient.items.toList(), FriendlyByteBuf::writeItem)
            buffer.writeVarInt(ingredient.requiredAmount)
        }
    }

    companion object {
        val NAME = ntm("stacked")
        val EMPTY = StackedIngredient(emptySequence(), 0)

        fun fromValues(values: Sequence<Value>, amount: Int): StackedIngredient {
            val ingredient = StackedIngredient(values, amount)
            return if (ingredient.values.none()) EMPTY else ingredient
        }

        fun of() = EMPTY
        fun of(amount: Int, stacks: Sequence<ItemStack>) = fromValues(stacks.filter { !it.isEmpty }.map(::ItemValue), amount)
        fun of(amount: Int, stacks: Collection<ItemStack>) = of(amount, stacks.asSequence())
        fun of(amount: Int, vararg itemLike: ItemLike) = of(amount, itemLike.map(::ItemStack))
        fun of(amount: Int, vararg itemAmountPairs: Pair<ItemLike, Int>) = of(amount, itemAmountPairs.map { (item, amount) -> ItemStack(item, amount) })
        fun of(amount: Int, vararg stacks: ItemStack) = of(amount, stacks.asSequence())
        fun of(amount: Int, tag: TagKey<Item>) = fromValues(sequenceOf(TagValue(tag)), amount)
    }
}

fun Container.getItems(copy: Boolean = true): List<ItemStack> = buildList {
    if (copy) for (i in 0 until containerSize) add(getItem(i).copy())
    else for (i in 0 until containerSize) add(getItem(i))
}

fun Collection<Ingredient>.containerSatisfiesRequirements(container: Container, actuallyRemove: Boolean = false): Boolean {
    if (isEmpty()) return true

    val containerItems = container.getItems(!actuallyRemove)

    for (ingredient in this) {
        if (ingredient is StackedIngredient) { // TODO kinda dirty, that's true. maybe move to API?
            var amountLeft = ingredient.requiredAmount
            for (stack in containerItems) {
                if (ingredient.test(stack)) {
                    val removeCount = min(stack.count, amountLeft)
                    stack.count -= removeCount
                    amountLeft -= removeCount
                }
            }
            if (amountLeft > 0) return false
        } else for (stack in containerItems) {
            if (ingredient.test(stack)) {
                stack.shrink(1)
                break
            }
        }
    }

    return containerItems.all { it.count >= 0 }
}
