package at.martinthedragon.nucleartech.integration.jei

import at.martinthedragon.nucleartech.ModBlockItems
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.recipes.PressRecipe
import com.mojang.blaze3d.matrix.MatrixStack
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.IRecipeLayout
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.gui.drawable.IDrawableAnimated
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.recipe.category.IRecipeCategory
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TranslationTextComponent

class PressingJeiRecipeCategory(guiHelper: IGuiHelper) : IRecipeCategory<PressRecipe> {
    private val background = guiHelper.createDrawable(GUI_RESOURCE, 0, 0, 81, 54)
    private val icon = guiHelper.createDrawableIngredient(ItemStack(ModBlockItems.steamPress.get()))
    private val pressArrow = guiHelper.drawableBuilder(GUI_RESOURCE, 0, 54, 18, 16).buildAnimated(20, IDrawableAnimated.StartDirection.TOP, false)

    override fun getUid() = UID

    override fun getRecipeClass(): Class<out PressRecipe> = PressRecipe::class.java

    override fun getTitle() = "Pressing"

    override fun getTitleAsTextComponent() = TranslationTextComponent("jei.${NuclearTech.MODID}.category.pressing")

    override fun getBackground(): IDrawable = background

    override fun getIcon(): IDrawable = icon

    override fun setIngredients(p0: PressRecipe, p1: IIngredients) {
        p1.setInputIngredients(mutableListOf(p0.ingredient, Ingredient.of(p0.stampType.tag)))
        p1.setOutput(VanillaTypes.ITEM, p0.result)
    }

    override fun setRecipe(p0: IRecipeLayout, p1: PressRecipe, p2: IIngredients) {
        val guiItemStacks = p0.itemStacks
        guiItemStacks.init(0, true, 0, 36)
        guiItemStacks.init(1, true, 0, 0)
        guiItemStacks.init(3, false, 63, 18)
        guiItemStacks.set(p2)
    }

    override fun draw(recipe: PressRecipe, matrixStack: MatrixStack, mouseX: Double, mouseY: Double) {
        pressArrow.draw(matrixStack, 0, 19)
    }

    companion object {
        val GUI_RESOURCE = ResourceLocation(NuclearTech.MODID, "textures/gui/jei_press.png")
        val UID = ResourceLocation(NuclearTech.MODID, "pressing")
    }
}
