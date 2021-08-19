package at.martinthedragon.nucleartech.recipes

import at.martinthedragon.nucleartech.NuclearTags
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import net.minecraft.inventory.IInventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.item.crafting.Ingredient
import net.minecraft.item.crafting.ShapedRecipe
import net.minecraft.network.PacketBuffer
import net.minecraft.tags.ITag
import net.minecraft.tags.ItemTags
import net.minecraft.util.JSONUtils
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.registries.ForgeRegistryEntry

class PressRecipe(
    val recipeID: ResourceLocation,
    val stampType: StampType,
    val ingredient: Ingredient,
    val result: ItemStack,
    val experience: Float
) : IRecipe<IInventory> {
    enum class StampType(val tag: ITag.INamedTag<Item>) {
        FLAT(NuclearTags.Items.FLAT_STAMPS),
        PLATE(NuclearTags.Items.PLATE_STAMPS),
        WIRE(NuclearTags.Items.WIRE_STAMPS),
        CIRCUIT(NuclearTags.Items.CIRCUIT_STAMPS),

        ANY(NuclearTags.Items.STAMPS);

        fun matches(item: Item): Boolean = item in ItemTags.getAllTags().getTagOrEmpty(tag.name)
    }

    // containerSize == 1 used for quickMoveStack
    override fun matches(inventory: IInventory, world: World): Boolean =
        ingredient.test(inventory.getItem(0)) && (inventory.containerSize == 1 || stampType.matches(inventory.getItem(1).item))

    override fun assemble(inventory: IInventory): ItemStack = resultItem.copy()

    override fun canCraftInDimensions(p_194133_1_: Int, p_194133_2_: Int) = true

    override fun getResultItem() = result

    override fun getId() = recipeID

    override fun getSerializer() = RecipeSerializers.PRESSING.get()

    override fun getType() = RecipeTypes.PRESSING

    override fun isSpecial() = true

    class Serializer : ForgeRegistryEntry<IRecipeSerializer<*>>(), IRecipeSerializer<PressRecipe> {
        override fun fromJson(id: ResourceLocation, jsonObject: JsonObject): PressRecipe {
            val stampType = try {
                StampType.valueOf(JSONUtils.getAsString(jsonObject, "stamp_type").uppercase())
            } catch (e: IllegalArgumentException) {
                throw JsonSyntaxException("Unexpected value for stamp type", e)
            }
            val ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(jsonObject, "ingredient"))
            val result = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(jsonObject, "result"))
            val experience = JSONUtils.getAsFloat(jsonObject, "experience", 0F)
            return PressRecipe(id, stampType, ingredient, result, experience)
        }

        override fun fromNetwork(id: ResourceLocation, packetBuffer: PacketBuffer): PressRecipe {
            val stampType = packetBuffer.readEnum(StampType::class.java)
            val ingredient = Ingredient.fromNetwork(packetBuffer)
            val result = packetBuffer.readItem()
            val experience = packetBuffer.readFloat()
            return PressRecipe(id, stampType, ingredient, result, experience)
        }

        override fun toNetwork(packetBuffer: PacketBuffer, recipe: PressRecipe) {
            packetBuffer.writeEnum(recipe.stampType)
            recipe.ingredient.toNetwork(packetBuffer)
            packetBuffer.writeItem(recipe.resultItem)
            packetBuffer.writeFloat(recipe.experience)
        }
    }
}
