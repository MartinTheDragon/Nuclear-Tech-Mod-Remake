package at.martinthedragon.nucleartech.recipes

import at.martinthedragon.nucleartech.ModBlockItems
import at.martinthedragon.nucleartech.extensions.containsFluids
import at.martinthedragon.nucleartech.extensions.toStupidMojangList
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import net.minecraft.core.NonNullList
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.ShapedRecipe
import net.minecraft.world.level.Level
import net.minecraftforge.common.crafting.CraftingHelper
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.registries.ForgeRegistryEntry

class ChemRecipe(
    val recipeID: ResourceLocation,
    val ingredientsList: NonNullList<StackedIngredient>,
    val inputFluid1: FluidStack,
    val inputFluid2: FluidStack,
    val resultsList: List<ItemStack>,
    val outputFluid1: FluidStack,
    val outputFluid2: FluidStack,
    val duration: Int,
) : Recipe<Container> {
    override fun matches(container: Container, level: Level) =
        container.containerSize >= ingredientsList.size
            && container is IFluidHandler
            && ingredientsList.containerSatisfiesRequirements(container)
            && container.containsFluids(inputFluid1, inputFluid2)

    override fun assemble(container: Container): ItemStack = resultItem.copy()
    override fun getId() = recipeID
    override fun getSerializer() = RecipeSerializers.CHEM.get()
    override fun getType() = RecipeTypes.CHEM
    override fun isSpecial() = true
    override fun canCraftInDimensions(x: Int, y: Int) = true
    override fun getToastSymbol() = ItemStack(ModBlockItems.chemPlantPlacer.get())
    @Suppress("UNCHECKED_CAST")
    override fun getIngredients(): NonNullList<Ingredient> = ingredientsList as NonNullList<Ingredient>
    override fun getResultItem() = resultsList.first()

    class Serializer : ForgeRegistryEntry<RecipeSerializer<*>>(), RecipeSerializer<ChemRecipe> {
        override fun fromJson(id: ResourceLocation, json: JsonObject): ChemRecipe {
            val ingredients = GsonHelper.getAsJsonArray(json, "ingredients").map(Ingredient::fromJson).map { it as? StackedIngredient ?: throw JsonParseException("Couldn't parse ingredient as StackedIngredient") }
            val inputFluid1 = FluidStack.loadFluidStackFromNBT(CraftingHelper.getNBT(json.get("inputFluid1")))
            val inputFluid2 = FluidStack.loadFluidStackFromNBT(CraftingHelper.getNBT(json.get("inputFluid2")))
            val results = GsonHelper.getAsJsonArray(json, "results").map(JsonElement::getAsJsonObject).map(ShapedRecipe::itemStackFromJson)
            val outputFluid1 = FluidStack.loadFluidStackFromNBT(CraftingHelper.getNBT(json.get("outputFluid1")))
            val outputFluid2 = FluidStack.loadFluidStackFromNBT(CraftingHelper.getNBT(json.get("outputFluid2")))
            val duration = GsonHelper.getAsInt(json, "duration")
            return ChemRecipe(id, ingredients.toStupidMojangList(), inputFluid1, inputFluid2, results, outputFluid1, outputFluid2, duration)
        }

        override fun fromNetwork(id: ResourceLocation, buffer: FriendlyByteBuf): ChemRecipe {
            val ingredients = buffer.readList(Ingredient::fromNetwork).map { it as? StackedIngredient ?: throw IllegalStateException("Received non-StackedIngredient over network") }
            val inputFluid1 = buffer.readFluidStack()
            val inputFluid2 = buffer.readFluidStack()
            val results = buffer.readList(FriendlyByteBuf::readItem)
            val outputFluid1 = buffer.readFluidStack()
            val outputFluid2 = buffer.readFluidStack()
            val duration = buffer.readInt()
            return ChemRecipe(id, ingredients.toStupidMojangList(), inputFluid1, inputFluid2, results, outputFluid1, outputFluid2, duration)
        }

        override fun toNetwork(buffer: FriendlyByteBuf, recipe: ChemRecipe) {
            buffer.writeCollection(recipe.ingredientsList) { packet, ingredient -> ingredient.toNetwork(packet) }
            buffer.writeFluidStack(recipe.inputFluid1)
            buffer.writeFluidStack(recipe.inputFluid2)
            buffer.writeCollection(recipe.resultsList, FriendlyByteBuf::writeItem)
            buffer.writeFluidStack(recipe.outputFluid1)
            buffer.writeFluidStack(recipe.outputFluid2)
            buffer.writeInt(recipe.duration)
        }
    }
}
