package at.martinthedragon.nucleartech.recipe

import at.martinthedragon.nucleartech.item.BatteryItem
import com.google.gson.JsonObject
import net.minecraft.core.NonNullList
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.inventory.CraftingContainer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.*
import net.minecraftforge.common.crafting.IShapedRecipe
import net.minecraftforge.registries.ForgeRegistryEntry

class BatteryRecipe(private val delegate: ShapedRecipe) : CraftingRecipe, IShapedRecipe<CraftingContainer> by delegate { // i got frustrated on having to use access transformers and everything. then i remembered kotlin has delegation...
    override fun assemble(inventory: CraftingContainer): ItemStack {
        if (resultItem.item !is BatteryItem) return resultItem.copy()
        @OptIn(ExperimentalStdlibApi::class)
        val batteries = buildList<ItemStack> {
            for (i in 0 until inventory.containerSize) {
                val item = inventory.getItem(i)
                if (item.item is BatteryItem) add(item)
            }
        }

        val resultEnergy = batteries.fold(0L) { acc, itemStack -> acc + itemStack.orCreateTag.getLong("Energy") }
        return resultItem.copy().apply {
            orCreateTag.putLong("Energy", resultEnergy)
            damageValue = (((resultItem.item as BatteryItem).capacity - resultEnergy).toDouble() / (resultItem.item as BatteryItem).energyPerDamage.toDouble()).toInt()
        }
    }

    override fun getSerializer() = RecipeSerializers.BATTERY.get()
    override fun getType(): RecipeType<*> = delegate.type
    override fun isSpecial() = true
    override fun getIngredients(): NonNullList<Ingredient> = delegate.ingredients

    class Serializer : ForgeRegistryEntry<RecipeSerializer<*>>(), RecipeSerializer<BatteryRecipe> {
        override fun fromJson(id: ResourceLocation, json: JsonObject): BatteryRecipe =
            BatteryRecipe(RecipeSerializer.SHAPED_RECIPE.fromJson(id, json))

        override fun fromNetwork(id: ResourceLocation, packet: FriendlyByteBuf): BatteryRecipe? {
            return BatteryRecipe(RecipeSerializer.SHAPED_RECIPE.fromNetwork(id, packet) ?: return null)
        }

        override fun toNetwork(packet: FriendlyByteBuf, recipe: BatteryRecipe) =
            RecipeSerializer.SHAPED_RECIPE.toNetwork(packet, recipe.delegate)
    }
}
