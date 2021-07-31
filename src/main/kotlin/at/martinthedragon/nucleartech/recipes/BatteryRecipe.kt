package at.martinthedragon.nucleartech.recipes

import at.martinthedragon.nucleartech.items.BatteryItem
import com.google.gson.JsonObject
import net.minecraft.inventory.CraftingInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.*
import net.minecraft.network.PacketBuffer
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.crafting.IShapedRecipe
import net.minecraftforge.registries.ForgeRegistryEntry

class BatteryRecipe(private val delegate: ShapedRecipe) : ICraftingRecipe, IShapedRecipe<CraftingInventory> by delegate { // i got frustrated on having to use access transformers and everything. then i remembered kotlin has delegation...
    override fun assemble(inventory: CraftingInventory): ItemStack {
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
    override fun getType(): IRecipeType<*> = delegate.type
    override fun isSpecial() = true
    override fun getIngredients(): NonNullList<Ingredient> = delegate.ingredients

    class Serializer : ForgeRegistryEntry<IRecipeSerializer<*>>(), IRecipeSerializer<BatteryRecipe> {
        override fun fromJson(id: ResourceLocation, json: JsonObject): BatteryRecipe =
            BatteryRecipe(IRecipeSerializer.SHAPED_RECIPE.fromJson(id, json))

        override fun fromNetwork(id: ResourceLocation, packet: PacketBuffer): BatteryRecipe? {
            return BatteryRecipe(IRecipeSerializer.SHAPED_RECIPE.fromNetwork(id, packet) ?: return null)
        }

        override fun toNetwork(packet: PacketBuffer, recipe: BatteryRecipe) =
            IRecipeSerializer.SHAPED_RECIPE.toNetwork(packet, recipe.delegate)
    }
}
