package at.martinthedragon.nucleartech.integration.jei.categories

import at.martinthedragon.nucleartech.ModBlockItems
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.integration.jei.NuclearRecipeTypes
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.recipes.BlastingRecipe
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

class BlastingJRC(guiHelper: IGuiHelper) : IRecipeCategory<BlastingRecipe> {
    private val texture = ntm("textures/gui/jei_blast_furnace.png")
    private val background = guiHelper.createDrawable(texture, 0, 0, 72, 54)
    private val icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ItemStack(ModBlockItems.blastFurnace.get()))
    private val blastArrow = guiHelper.drawableBuilder(texture, 0, 54, 22, 16).buildAnimated(400, IDrawableAnimated.StartDirection.LEFT, false)

    override fun getUid() = ntm("blasting")
    override fun getRecipeClass(): Class<out BlastingRecipe> = BlastingRecipe::class.java
    override fun getRecipeType() = NuclearRecipeTypes.BLASTING
    override fun getTitle() = TranslatableComponent("jei.${NuclearTech.MODID}.category.blasting")
    override fun getBackground(): IDrawable = background

    override fun getIcon(): IDrawable = icon

    override fun setRecipe(builder: IRecipeLayoutBuilder, recipe: BlastingRecipe, focuses: IFocusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(recipe.ingredient1)
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 37).addIngredients(recipe.ingredient2)
        builder.addSlot(RecipeIngredientRole.OUTPUT, 55, 19).addItemStack(recipe.resultItem)
    }

    override fun draw(recipe: BlastingRecipe, recipeSlotsView: IRecipeSlotsView, matrixStack: PoseStack, mouseX: Double, mouseY: Double) {
        blastArrow.draw(matrixStack, 23, 19)
        drawExperience(recipe, matrixStack)
    }

    private fun drawExperience(recipe: BlastingRecipe, matrixStack: PoseStack) {
        val experience = recipe.experience
        if (experience > 0) {
            val experienceString = TranslatableComponent("jei.${NuclearTech.MODID}.category.blasting.experience", experience)
            val fontRenderer = Minecraft.getInstance().font
            val stringWidth = fontRenderer.width(experienceString)
            fontRenderer.draw(matrixStack, experienceString, (background.width - stringWidth).toFloat(), 0F, -0x7F7F80)
        }
    }
}
