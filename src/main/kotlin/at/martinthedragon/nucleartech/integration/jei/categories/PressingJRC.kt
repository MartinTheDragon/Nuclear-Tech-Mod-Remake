package at.martinthedragon.nucleartech.integration.jei.categories

import at.martinthedragon.nucleartech.item.NTechBlockItems
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.integration.jei.NuclearRecipeTypes
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.recipe.PressingRecipe
import com.mojang.blaze3d.vertex.PoseStack
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.gui.drawable.IDrawableAnimated
import mezz.jei.api.gui.ingredient.IRecipeSlotsView
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.recipe.IFocusGroup
import mezz.jei.api.recipe.RecipeIngredientRole
import mezz.jei.api.recipe.category.IRecipeCategory
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient

class PressingJRC(guiHelper: IGuiHelper) : IRecipeCategory<PressingRecipe> {
    private val texture = ntm("textures/gui/jei_press.png")
    private val background = guiHelper.createDrawable(texture, 0, 0, 81, 54)
    private val icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ItemStack(NTechBlockItems.steamPress.get()))
    private val pressArrow = guiHelper.drawableBuilder(texture, 0, 54, 18, 16).buildAnimated(20, IDrawableAnimated.StartDirection.TOP, false)

    override fun getUid() = ntm("pressing")
    override fun getRecipeClass(): Class<out PressingRecipe> = PressingRecipe::class.java
    override fun getRecipeType() = NuclearRecipeTypes.PRESSING
    override fun getTitle() = TranslatableComponent("jei.${NuclearTech.MODID}.category.pressing")
    override fun getBackground(): IDrawable = background
    override fun getIcon(): IDrawable = icon

    override fun setRecipe(builder: IRecipeLayoutBuilder, recipe: PressingRecipe, focuses: IFocusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 37).addIngredients(recipe.ingredient)
        builder.addSlot(RecipeIngredientRole.CATALYST, 1, 1).addIngredients(Ingredient.of(recipe.stampType.tag))
        builder.addSlot(RecipeIngredientRole.OUTPUT, 64, 19).addItemStack(recipe.resultItem)
    }

    override fun draw(recipe: PressingRecipe, recipeSlotsView: IRecipeSlotsView, matrixStack: PoseStack, mouseX: Double, mouseY: Double) {
        pressArrow.draw(matrixStack, 0, 19)
        drawExperience(recipe, matrixStack)
    }

    private fun drawExperience(recipe: PressingRecipe, matrixStack: PoseStack) {
        val experience = recipe.experience
        if (experience > 0) {
            val experienceString = TranslatableComponent("jei.${NuclearTech.MODID}.category.pressing.experience", experience)
            val fontRenderer = Minecraft.getInstance().font
            val stringWidth = fontRenderer.width(experienceString)
            fontRenderer.draw(matrixStack, experienceString, (background.width - stringWidth).toFloat(), 0F, -0x7F7F80)
        }
    }
}
