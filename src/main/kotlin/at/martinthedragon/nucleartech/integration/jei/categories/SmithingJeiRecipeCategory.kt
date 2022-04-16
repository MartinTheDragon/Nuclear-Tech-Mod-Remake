package at.martinthedragon.nucleartech.integration.jei.categories

import at.martinthedragon.nucleartech.ModBlockItems
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.integration.jei.NuclearRecipeTypes
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.recipes.anvil.AnvilSmithingRecipe
import com.mojang.blaze3d.vertex.PoseStack
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.gui.ingredient.IRecipeSlotsView
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.recipe.IFocusGroup
import mezz.jei.api.recipe.RecipeIngredientRole
import mezz.jei.api.recipe.category.IRecipeCategory
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.item.ItemStack

class SmithingJeiRecipeCategory(guiHelper: IGuiHelper) : IRecipeCategory<AnvilSmithingRecipe> {
    private val background: IDrawable = guiHelper.drawableBuilder(GUI_RESOURCE, 0, 0, 90, 27).setTextureSize(128, 128).build()
    private val icon: IDrawable = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ItemStack(ModBlockItems.ironAnvil.get()))

    override fun getUid() = UID
    override fun getRecipeClass() = AnvilSmithingRecipe::class.java
    override fun getRecipeType() = NuclearRecipeTypes.SMITHING
    override fun getTitle() = TranslatableComponent("jei.${NuclearTech.MODID}.category.smithing")
    override fun getBackground() = background
    override fun getIcon() = icon

    override fun setRecipe(builder: IRecipeLayoutBuilder, recipe: AnvilSmithingRecipe, focuses: IFocusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 10).addIngredients(recipe.ingredient1)
        builder.addSlot(RecipeIngredientRole.INPUT, 37, 10).addIngredients(recipe.ingredient2)
        builder.addSlot(RecipeIngredientRole.OUTPUT, 73, 10).addItemStack(recipe.resultItem)
    }

    override fun draw(recipe: AnvilSmithingRecipe, recipeSlotsView: IRecipeSlotsView, stack: PoseStack, mouseX: Double, mouseY: Double) {
        val tierString = TranslatableComponent("jei.${NuclearTech.MODID}.category.smithing.tier", recipe.tier)
        val fontRenderer = Minecraft.getInstance().font
        val stringWidth = fontRenderer.width(tierString)
        fontRenderer.draw(stack, tierString, (background.width - stringWidth).toFloat(), 0F, -0x7F7F80)
    }

    companion object {
        private val GUI_RESOURCE = ntm("textures/gui/jei_smithing.png")
        val UID = ntm("smithing")
    }
}
