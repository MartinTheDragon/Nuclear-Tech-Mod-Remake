package at.martinthedragon.nucleartech.integration.jei.categories

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.extensions.gold
import at.martinthedragon.nucleartech.integration.jei.NuclearRecipeTypes
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.recipe.anvil.AnvilConstructingRecipe
import com.mojang.blaze3d.vertex.PoseStack
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.gui.ingredient.IRecipeSlotsView
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.recipe.IFocusGroup
import mezz.jei.api.recipe.RecipeIngredientRole
import mezz.jei.api.recipe.category.IRecipeCategory
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.TextComponent

class ConstructingJRC(guiHelper: IGuiHelper) : IRecipeCategory<AnvilConstructingRecipe> {
    private val texture = ntm("textures/gui/jei_constructing.png")
    private val background: IDrawable = guiHelper.drawableBuilder(texture, 0, 0, 180, 64).build()
    private val arrow: IDrawable = guiHelper.drawableBuilder(texture, 180, 0, 16, 16).build()
    private val wrench: IDrawable = guiHelper.drawableBuilder(texture, 180, 16, 16, 16).build()
    private val recycle: IDrawable = guiHelper.drawableBuilder(texture, 180, 32, 16, 16).build()
    private val hammer: IDrawable = guiHelper.drawableBuilder(texture, 180, 48, 16, 16).build()
    private val hideLeft: IDrawable = guiHelper.drawableBuilder(texture, 0, 64, 72, 54).build()
    private val hideRight: IDrawable = guiHelper.drawableBuilder(texture, 108, 64, 72, 54).build()

    override fun getUid() = ntm("constructing")
    override fun getRecipeClass() = AnvilConstructingRecipe::class.java
    override fun getRecipeType() = NuclearRecipeTypes.CONSTRUCTING
    override fun getTitle() = LangKeys.JEI_CATEGORY_CONSTRUCTING.get()
    override fun getBackground() = background
    override fun getIcon() = hammer

    override fun setRecipe(builder: IRecipeLayoutBuilder, recipe: AnvilConstructingRecipe, focuses: IFocusGroup) {
        when (recipe.overlay) {
            AnvilConstructingRecipe.OverlayType.NONE, AnvilConstructingRecipe.OverlayType.CONSTRUCTING -> {
                for (y in 0 until 3) for (x in 0 until 4) builder.addSlot(RecipeIngredientRole.INPUT, 1 + x * 18, 11 + y * 18).apply {
                    val index = y * 4 + x
                    if (index <= recipe.ingredientsList.lastIndex) addIngredients(recipe.ingredientsList[index])
                }
            }
            AnvilConstructingRecipe.OverlayType.RECYCLING, AnvilConstructingRecipe.OverlayType.SMITHING -> builder.addSlot(RecipeIngredientRole.INPUT, 55, 29).addIngredients(recipe.ingredientsList[0])
        }
        when (recipe.overlay) {
            AnvilConstructingRecipe.OverlayType.NONE, AnvilConstructingRecipe.OverlayType.RECYCLING -> {
                for (y in 0 until 3) for (x in 0 until 4) builder.addSlot(RecipeIngredientRole.OUTPUT, 109 + x * 18, 11 + y * 18).apply {
                    val index = y * 4 + x
                    val results = recipe.getResultsChanceCollapsed()
                    if (index <= results.lastIndex) addItemStack(results[index])
                }.addTooltipCallback { _, tooltip ->
                    val chances = recipe.getTooltipChancesForOutputAt(y * 4 + x)
                    if (chances.isEmpty()) return@addTooltipCallback
                    if (tooltip.isNotEmpty()) tooltip.add(TextComponent.EMPTY)
                    tooltip.add(LangKeys.JEI_OUTPUT_CHANCE.gold())
                    tooltip.addAll(chances)
                }
            }
            AnvilConstructingRecipe.OverlayType.CONSTRUCTING, AnvilConstructingRecipe.OverlayType.SMITHING -> builder.addSlot(RecipeIngredientRole.OUTPUT, 109, 29).addItemStacks(recipe.getResultsChanceCollapsed())
        }
    }

    override fun draw(recipe: AnvilConstructingRecipe, recipeSlotsView: IRecipeSlotsView, stack: PoseStack, mouseX: Double, mouseY: Double) {
        val tierString =
            if (recipe.tierUpper == -1 || recipe.tierLower == recipe.tierUpper) LangKeys.JEI_TIER_MINIMUM.format(recipe.tierLower)
            else LangKeys.JEI_TIER_RANGE.format(recipe.tierLower, recipe.tierUpper)
        val fontRenderer = Minecraft.getInstance().font
        val stringWidth = fontRenderer.width(tierString)
        fontRenderer.draw(stack, tierString, (background.width - stringWidth.toFloat()), 0F, -0x7F7F80)

        val overlay = recipe.overlay

        when (overlay) {
            AnvilConstructingRecipe.OverlayType.NONE -> arrow.draw(stack, 82, 20)
            AnvilConstructingRecipe.OverlayType.CONSTRUCTING -> wrench.draw(stack, 82, 20)
            AnvilConstructingRecipe.OverlayType.RECYCLING -> recycle.draw(stack, 82, 20)
            AnvilConstructingRecipe.OverlayType.SMITHING -> hammer.draw(stack, 82, 20)
        }

        if (overlay == AnvilConstructingRecipe.OverlayType.RECYCLING || overlay == AnvilConstructingRecipe.OverlayType.SMITHING) hideLeft.draw(stack, 0, 10)
        if (overlay == AnvilConstructingRecipe.OverlayType.CONSTRUCTING || overlay == AnvilConstructingRecipe.OverlayType.SMITHING) hideRight.draw(stack, 108, 10)
    }
}
