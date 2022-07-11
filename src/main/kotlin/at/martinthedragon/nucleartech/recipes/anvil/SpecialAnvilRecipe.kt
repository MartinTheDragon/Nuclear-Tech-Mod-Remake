package at.martinthedragon.nucleartech.recipes.anvil

import at.martinthedragon.nucleartech.recipes.StackedIngredient
import net.minecraft.core.NonNullList
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.Level

abstract class SpecialAnvilRecipe(recipeID: ResourceLocation, tier: Int) : AnvilSmithingRecipe(recipeID, StackedIngredient.EMPTY, StackedIngredient.EMPTY, ItemStack.EMPTY, tier) {
    override fun getIngredients(): NonNullList<Ingredient> = NonNullList.create()
    override fun getResultItem(): ItemStack = ItemStack.EMPTY
    override fun getAmountConsumed(index: Int, mirrored: Boolean) = 1
    final override fun matches(container: Container, level: Level): Boolean = matches(container)
    abstract fun matches(container: Container): Boolean
    override fun matchesInt(container: Container): Int = if (matches(container)) 0 else -1
    abstract override fun assemble(container: Container): ItemStack
    abstract override fun getSerializer(): RecipeSerializer<*>
}
