package at.martinthedragon.nucleartech.integration.jei.categories

import at.martinthedragon.nucleartech.ModBlockItems
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.recipes.BlastingRecipe
import com.mojang.blaze3d.vertex.PoseStack
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.IRecipeLayout
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.gui.drawable.IDrawableAnimated
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.recipe.category.IRecipeCategory
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

class BlastingJeiRecipeCategory(guiHelper: IGuiHelper) : IRecipeCategory<BlastingRecipe> {
    private val background = guiHelper.createDrawable(GUI_RESOURCE, 0, 0, 72, 54)
    private val icon = guiHelper.createDrawableIngredient(ItemStack(ModBlockItems.blastFurnace.get()))
    private val blastArrow = guiHelper.drawableBuilder(GUI_RESOURCE, 0, 54, 22, 16).buildAnimated(400, IDrawableAnimated.StartDirection.LEFT, false)

    override fun getUid() = UID

    override fun getRecipeClass(): Class<out BlastingRecipe> = BlastingRecipe::class.java

    override fun getTitle() = TranslatableComponent("jei.${NuclearTech.MODID}.category.blasting")

    override fun getBackground(): IDrawable = background

    override fun getIcon(): IDrawable = icon

    override fun setIngredients(p0: BlastingRecipe, p1: IIngredients) {
        p1.setInputIngredients(listOf(p0.ingredient1, p0.ingredient2))
        p1.setOutput(VanillaTypes.ITEM, p0.result)
    }

    override fun setRecipe(p0: IRecipeLayout, p1: BlastingRecipe, p2: IIngredients) {
        val guiItemStacks = p0.itemStacks
        guiItemStacks.init(0, true, 0, 0)
        guiItemStacks.init(1, true, 0, 36)
        guiItemStacks.init(3, false, 54, 18)
        guiItemStacks.set(p2)
    }

    override fun draw(recipe: BlastingRecipe, matrixStack: PoseStack, mouseX: Double, mouseY: Double) {
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

    companion object {
        val GUI_RESOURCE = ResourceLocation(NuclearTech.MODID, "textures/gui/jei_blast_furnace.png")
        val UID = ResourceLocation(NuclearTech.MODID, "blasting")
    }
}
