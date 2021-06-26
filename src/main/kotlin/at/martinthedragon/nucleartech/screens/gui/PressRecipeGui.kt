package at.martinthedragon.nucleartech.screens.gui

import net.minecraft.client.gui.recipebook.RecipeBookGui
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent

class PressRecipeGui : RecipeBookGui() {
    override fun getRecipeFilterName(): ITextComponent = FILTER_NAME

    companion object {
        val FILTER_NAME = TranslationTextComponent("gui.nucleartech.recipebook.toggleRecipes.pressable")
    }
}
