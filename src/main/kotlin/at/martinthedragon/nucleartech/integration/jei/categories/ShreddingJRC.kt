package at.martinthedragon.nucleartech.integration.jei.categories

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.NTechTags
import at.martinthedragon.nucleartech.block.entity.ShredderBlockEntity
import at.martinthedragon.nucleartech.integration.jei.NuclearRecipeTypes
import at.martinthedragon.nucleartech.item.NTechBlockItems
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.recipe.ShreddingRecipe
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
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient

class ShreddingJRC(guiHelper: IGuiHelper) : IRecipeCategory<ShreddingRecipe> {
    private val texture = ntm("textures/gui/jei_shredder.png")
    private val background = guiHelper.createDrawable(texture, 0, 0, 111, 54)
    private val icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ItemStack(NTechBlockItems.shredder.get()))
    private val arrow = guiHelper.drawableBuilder(texture, 16, 90, 22, 16).buildAnimated(ShredderBlockEntity.SHREDDING_TIME, IDrawableAnimated.StartDirection.LEFT, false)
    private val energyBar = guiHelper.drawableBuilder(texture, 0, 54, 16, 52).buildAnimated(ShredderBlockEntity.MAX_ENERGY / ShredderBlockEntity.ENERGY_CONSUMPTION_RATE / 10, IDrawableAnimated.StartDirection.TOP, true)

    override fun getUid() = ntm("shredding")
    override fun getRecipeClass() = ShreddingRecipe::class.java
    override fun getRecipeType() = NuclearRecipeTypes.SHREDDING
    override fun getTitle() = LangKeys.JEI_CATEGORY_SHREDDING.get()
    override fun getBackground(): IDrawable = background

    override fun getIcon(): IDrawable = icon

    override fun setRecipe(builder: IRecipeLayoutBuilder, recipe: ShreddingRecipe, focuses: IFocusGroup) {
        val shredderBlades = Ingredient.of(NTechTags.Items.SHREDDER_BLADES)
        builder.addSlot(RecipeIngredientRole.INPUT, 24, 19).addIngredients(recipe.ingredient)
        builder.addSlot(RecipeIngredientRole.CATALYST, 49, 1).addIngredients(shredderBlades)
        builder.addSlot(RecipeIngredientRole.CATALYST, 49, 37).addIngredients(shredderBlades)
        builder.addSlot(RecipeIngredientRole.OUTPUT, 74, 20).addItemStack(recipe.resultItem)
    }

    override fun draw(recipe: ShreddingRecipe, recipeSlotsView: IRecipeSlotsView, matrixStack: PoseStack, mouseX: Double, mouseY: Double) {
        arrow.draw(matrixStack, 46, 20)
        energyBar.draw(matrixStack, 1, 1)
        drawExperience(recipe, matrixStack)
    }

    private fun drawExperience(recipe: ShreddingRecipe, matrixStack: PoseStack) {
        val experience = recipe.experience
        if (experience > 0) {
            val experienceString = LangKeys.JEI_EXPERIENCE.format(experience)
            val fontRenderer = Minecraft.getInstance().font
            val stringWidth = fontRenderer.width(experienceString)
            fontRenderer.draw(matrixStack, experienceString, (background.width - stringWidth).toFloat(), 0F, -0x7F7F80)
        }
    }
}
