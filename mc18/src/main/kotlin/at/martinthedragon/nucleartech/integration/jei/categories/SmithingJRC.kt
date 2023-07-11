package at.martinthedragon.nucleartech.integration.jei.categories

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.integration.jei.NuclearRecipeTypes
import at.martinthedragon.nucleartech.item.NTechBlockItems
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.recipe.anvil.AnvilSmithingRecipe
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
import net.minecraft.world.item.ItemStack

class SmithingJRC(guiHelper: IGuiHelper) : IRecipeCategory<AnvilSmithingRecipe> {
    private val texture = ntm("textures/gui/jei_smithing.png")
    private val background: IDrawable = guiHelper.drawableBuilder(texture, 0, 0, 90, 27).setTextureSize(128, 128).build()
    private val icon: IDrawable = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ItemStack(NTechBlockItems.ironAnvil.get()))

    override fun getUid() = ntm("smithing")
    override fun getRecipeClass() = AnvilSmithingRecipe::class.java
    override fun getRecipeType() = NuclearRecipeTypes.SMITHING
    override fun getTitle() = LangKeys.JEI_CATEGORY_SMITHING.get()
    override fun getBackground() = background
    override fun getIcon() = icon

    override fun setRecipe(builder: IRecipeLayoutBuilder, recipe: AnvilSmithingRecipe, focuses: IFocusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 10).addIngredients(recipe.ingredient1)
        builder.addSlot(RecipeIngredientRole.INPUT, 37, 10).addIngredients(recipe.ingredient2)
        builder.addSlot(RecipeIngredientRole.OUTPUT, 73, 10).addItemStack(recipe.resultItem)
    }

    override fun draw(recipe: AnvilSmithingRecipe, recipeSlotsView: IRecipeSlotsView, stack: PoseStack, mouseX: Double, mouseY: Double) {
        val tierString = LangKeys.JEI_TIER_MINIMUM.format(recipe.tier)
        val fontRenderer = Minecraft.getInstance().font
        val stringWidth = fontRenderer.width(tierString)
        fontRenderer.draw(stack, tierString, (background.width - stringWidth).toFloat(), 0F, -0x7F7F80)
    }

}
