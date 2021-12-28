package at.martinthedragon.nucleartech.integration.jei.categories

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.recipes.anvil.AnvilConstructingRecipe
import com.mojang.blaze3d.vertex.PoseStack
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.IRecipeLayout
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.recipe.category.IRecipeCategory
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent

class ConstructingJeiRecipeCategory(guiHelper: IGuiHelper) : IRecipeCategory<AnvilConstructingRecipe> {
    private val background: IDrawable = guiHelper.drawableBuilder(GUI_RESOURCE, 0, 0, 180, 64).build()
    private val arrow: IDrawable = guiHelper.drawableBuilder(GUI_RESOURCE, 180, 0, 16, 16).build()
    private val wrench: IDrawable = guiHelper.drawableBuilder(GUI_RESOURCE, 180, 16, 16, 16).build()
    private val recycle: IDrawable = guiHelper.drawableBuilder(GUI_RESOURCE, 180, 32, 16, 16).build()
    private val hammer: IDrawable = guiHelper.drawableBuilder(GUI_RESOURCE, 180, 48, 16, 16).build()
    private val hideLeft: IDrawable = guiHelper.drawableBuilder(GUI_RESOURCE, 0, 64, 72, 54).build()
    private val hideRight: IDrawable = guiHelper.drawableBuilder(GUI_RESOURCE, 108, 64, 72, 54).build()

    override fun getUid() = UID
    override fun getRecipeClass() = AnvilConstructingRecipe::class.java
    override fun getTitle() = TranslatableComponent("jei.${NuclearTech.MODID}.category.constructing")
    override fun getBackground() = background
    override fun getIcon() = hammer

    override fun setIngredients(recipe: AnvilConstructingRecipe, ingredients: IIngredients) {
        ingredients.setInputIngredients(recipe.ingredients)
        ingredients.setOutputs(VanillaTypes.ITEM, recipe.getResultsChanceCollapsed())
    }

    override fun setRecipe(recipeLayout: IRecipeLayout, recipe: AnvilConstructingRecipe, ingredients: IIngredients) {
        val itemStacks = recipeLayout.itemStacks
        when (recipe.overlay) {
            AnvilConstructingRecipe.OverlayType.NONE, AnvilConstructingRecipe.OverlayType.CONSTRUCTING -> for (y in 0 until 3) for (x in 0 until 4) itemStacks.init(y * 4 + x, true, x * 18, 10 + y * 18)
            AnvilConstructingRecipe.OverlayType.RECYCLING, AnvilConstructingRecipe.OverlayType.SMITHING -> itemStacks.init(0, true, 54, 28)
        }
        when (recipe.overlay) {
            AnvilConstructingRecipe.OverlayType.NONE, AnvilConstructingRecipe.OverlayType.RECYCLING -> for (y in 0 until 3) for (x in 0 until 4) itemStacks.init(12 + y * 4 + x, false, 108 + x * 18, 10 + y * 18)
            AnvilConstructingRecipe.OverlayType.CONSTRUCTING, AnvilConstructingRecipe.OverlayType.SMITHING -> itemStacks.init(12, false, 108, 28)
        }
        itemStacks.set(ingredients)
        itemStacks.addTooltipCallback { slotIndex, input, _, tooltip ->
            if (input) return@addTooltipCallback
            val chances = recipe.getTooltipChancesForOutputAt(slotIndex - 12)
            if (chances.isEmpty()) return@addTooltipCallback
            if (tooltip.isNotEmpty()) tooltip.add(TextComponent.EMPTY)
            tooltip.add(TranslatableComponent("jei.${NuclearTech.MODID}.category.constructing.chance").withStyle(ChatFormatting.GOLD))
            tooltip.addAll(chances)
        }
    }

    override fun draw(recipe: AnvilConstructingRecipe, stack: PoseStack, mouseX: Double, mouseY: Double) {
        val tierString =
            if (recipe.tierUpper == -1 || recipe.tierLower == recipe.tierUpper) TranslatableComponent("jei.${NuclearTech.MODID}.category.constructing.tier", recipe.tierLower)
            else TranslatableComponent("jei.${NuclearTech.MODID}.category.constructing.tier_range", recipe.tierLower, recipe.tierUpper)
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

    companion object {
        private val GUI_RESOURCE = ntm("textures/gui/jei_constructing.png")
        val UID = ntm("constructing")
    }
}
