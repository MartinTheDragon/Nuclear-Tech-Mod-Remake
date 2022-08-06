package at.martinthedragon.nucleartech.integration.jei.categories

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.integration.jei.NuclearRecipeTypes
import at.martinthedragon.nucleartech.item.ChemPlantTemplateItem
import at.martinthedragon.nucleartech.item.NTechBlockItems
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.recipe.ChemRecipe
import com.mojang.blaze3d.vertex.PoseStack
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.forge.ForgeTypes
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.gui.drawable.IDrawableAnimated
import mezz.jei.api.gui.ingredient.IRecipeSlotsView
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.recipe.IFocusGroup
import mezz.jei.api.recipe.RecipeIngredientRole
import mezz.jei.api.recipe.category.IRecipeCategory
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient

class ChemistryJRC(guiHelper: IGuiHelper) : IRecipeCategory<ChemRecipe> {
    private val texture = ntm("textures/gui/jei_chemistry.png")
    private val background: IDrawable = guiHelper.createDrawable(texture, 0, 0, 153, 54)
    private val icon: IDrawable = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ItemStack(NTechBlockItems.chemPlantPlacer.get()))
    private val arrow: IDrawable = guiHelper.drawableBuilder(texture, 16, 54, 50, 17).buildAnimated(40, IDrawableAnimated.StartDirection.LEFT, false)
    private val energyBar: IDrawable = guiHelper.drawableBuilder(texture, 0, 54, 16, 52).buildAnimated(200, IDrawableAnimated.StartDirection.TOP, true)

    override fun getUid() = ntm("chemistry")
    override fun getRecipeClass() = ChemRecipe::class.java
    override fun getRecipeType() = NuclearRecipeTypes.CHEMISTRY
    override fun getTitle() = LangKeys.JEI_CATEGORY_CHEMISTRY.get()
    override fun getBackground() = background
    override fun getIcon() = icon

    override fun setRecipe(builder: IRecipeLayoutBuilder, recipe: ChemRecipe, focuses: IFocusGroup) {
        for (y in 0 until 2) for (x in 0 until 2) {
            builder.addSlot(RecipeIngredientRole.INPUT, 28 + x * 18, 19 + y * 18).addIngredients(recipe.ingredients.getOrElse(x + y * 2) { Ingredient.EMPTY })
            builder.addSlot(RecipeIngredientRole.OUTPUT, 118 + x * 18, 19 + y * 18).addItemStack(recipe.resultsList.getOrElse(x + y * 2) { ItemStack.EMPTY })
        }
        builder.addSlot(RecipeIngredientRole.CATALYST, 82, 1).addItemStack(ChemPlantTemplateItem.createWithID(recipe.id))
        builder.addSlot(RecipeIngredientRole.INPUT, 28, 1).addIngredient(ForgeTypes.FLUID_STACK, recipe.inputFluid1)
        builder.addSlot(RecipeIngredientRole.INPUT, 46, 1).addIngredient(ForgeTypes.FLUID_STACK, recipe.inputFluid2)
        builder.addSlot(RecipeIngredientRole.OUTPUT, 118, 1).addIngredient(ForgeTypes.FLUID_STACK, recipe.outputFluid1)
        builder.addSlot(RecipeIngredientRole.OUTPUT, 136, 1).addIngredient(ForgeTypes.FLUID_STACK, recipe.outputFluid2)
    }

    override fun draw(recipe: ChemRecipe, recipeSlotsView: IRecipeSlotsView, stack: PoseStack, mouseX: Double, mouseY: Double) {
        arrow.draw(stack, 65, 19)
        energyBar.draw(stack, 1, 1)
    }
}
