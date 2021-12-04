//package at.martinthedragon.nucleartech.integration.jei.categories
//
//import at.martinthedragon.nucleartech.ModBlockItems
//import at.martinthedragon.nucleartech.NuclearTech
//import at.martinthedragon.nucleartech.recipes.PressRecipe
//import com.mojang.blaze3d.vertex.PoseStack
//import mezz.jei.api.constants.VanillaTypes
//import mezz.jei.api.gui.IRecipeLayout
//import mezz.jei.api.gui.drawable.IDrawable
//import mezz.jei.api.gui.drawable.IDrawableAnimated
//import mezz.jei.api.helpers.IGuiHelper
//import mezz.jei.api.ingredients.IIngredients
//import mezz.jei.api.recipe.category.IRecipeCategory
//import net.minecraft.client.Minecraft
//import net.minecraft.network.chat.TranslatableComponent
//import net.minecraft.resources.ResourceLocation
//import net.minecraft.world.item.ItemStack
//import net.minecraft.world.item.crafting.Ingredient
//
//class PressingJeiRecipeCategory(guiHelper: IGuiHelper) : IRecipeCategory<PressRecipe> {
//    private val background = guiHelper.createDrawable(GUI_RESOURCE, 0, 0, 81, 54)
//    private val icon = guiHelper.createDrawableIngredient(ItemStack(ModBlockItems.steamPress.get()))
//    private val pressArrow = guiHelper.drawableBuilder(GUI_RESOURCE, 0, 54, 18, 16).buildAnimated(20, IDrawableAnimated.StartDirection.TOP, false)
//
//    override fun getUid() = UID
//
//    override fun getRecipeClass(): Class<out PressRecipe> = PressRecipe::class.java
//
//    override fun getTitle() = TranslatableComponent("jei.${NuclearTech.MODID}.category.pressing")
//
//    override fun getBackground(): IDrawable = background
//
//    override fun getIcon(): IDrawable = icon
//
//    override fun setIngredients(p0: PressRecipe, p1: IIngredients) {
//        p1.setInputIngredients(mutableListOf(p0.ingredient, Ingredient.of(p0.stampType.tag)))
//        p1.setOutput(VanillaTypes.ITEM, p0.result)
//    }
//
//    override fun setRecipe(p0: IRecipeLayout, p1: PressRecipe, p2: IIngredients) {
//        val guiItemStacks = p0.itemStacks
//        guiItemStacks.init(0, true, 0, 36)
//        guiItemStacks.init(1, true, 0, 0)
//        guiItemStacks.init(3, false, 63, 18)
//        guiItemStacks.set(p2)
//    }
//
//    override fun draw(recipe: PressRecipe, matrixStack: PoseStack, mouseX: Double, mouseY: Double) {
//        pressArrow.draw(matrixStack, 0, 19)
//        drawExperience(recipe, matrixStack)
//    }
//
//    private fun drawExperience(recipe: PressRecipe, matrixStack: PoseStack) {
//        val experience = recipe.experience
//        if (experience > 0) {
//            val experienceString = TranslatableComponent("jei.${NuclearTech.MODID}.category.pressing.experience", experience)
//            val fontRenderer = Minecraft.getInstance().font
//            val stringWidth = fontRenderer.width(experienceString)
//            fontRenderer.draw(matrixStack, experienceString, (background.width - stringWidth).toFloat(), 0F, -0x7F7F80)
//        }
//    }
//
//    companion object {
//        val GUI_RESOURCE = ResourceLocation(NuclearTech.MODID, "textures/gui/jei_press.png")
//        val UID = ResourceLocation(NuclearTech.MODID, "pressing")
//    }
//}
