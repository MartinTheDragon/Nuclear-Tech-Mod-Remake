package at.martinthedragon.nucleartech.integration.jei.categories

import at.martinthedragon.nucleartech.ModBlockItems
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.recipes.anvil.AnvilSmithingRecipe
import com.mojang.blaze3d.vertex.PoseStack
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.IRecipeLayout
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.recipe.category.IRecipeCategory
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

class SmithingJeiRecipeCategory(guiHelper: IGuiHelper) : IRecipeCategory<AnvilSmithingRecipe> {
    private val background: IDrawable = guiHelper.drawableBuilder(GUI_RESOURCE, 0, 0, 90, 27).setTextureSize(128, 128).build()
    private val icon: IDrawable = guiHelper.createDrawableIngredient(ItemStack(ModBlockItems.ironAnvil.get()))

    override fun getUid() = UID
    override fun getRecipeClass() = AnvilSmithingRecipe::class.java
    override fun getTitle() = TranslatableComponent("jei.${NuclearTech.MODID}.category.smithing")
    override fun getBackground() = background
    override fun getIcon() = icon

    override fun setIngredients(recipe: AnvilSmithingRecipe, ingredients: IIngredients) {
        ingredients.setInputIngredients(listOf(recipe.ingredient1, recipe.ingredient2))
        ingredients.setOutput(VanillaTypes.ITEM, recipe.result)
    }

    override fun setRecipe(recipeLayout: IRecipeLayout, recipe: AnvilSmithingRecipe, ingredients: IIngredients) {
        val itemStacks = recipeLayout.itemStacks
        itemStacks.init(0, true, 0, 9)
        itemStacks.init(1, true, 36, 9)
        itemStacks.init(2, false, 72, 9)
        itemStacks.set(ingredients)
    }

    override fun draw(recipe: AnvilSmithingRecipe, stack: PoseStack, mouseX: Double, mouseY: Double) {
        val tierString = TranslatableComponent("jei.${NuclearTech.MODID}.category.smithing.tier", recipe.tier)
        val fontRenderer = Minecraft.getInstance().font
        val stringWidth = fontRenderer.width(tierString)
        fontRenderer.draw(stack, tierString, (background.width - stringWidth).toFloat(), 0F, -0x7F7F80)
    }

    companion object {
        private val GUI_RESOURCE = ResourceLocation(NuclearTech.MODID, "textures/gui/jei_smithing.png")
        val UID = ResourceLocation(NuclearTech.MODID, "smithing")
    }
}
