//package at.martinthedragon.nucleartech.integration.jei.transfers
//
//import at.martinthedragon.nucleartech.integration.jei.categories.PressingJeiRecipeCategory
//import at.martinthedragon.nucleartech.menus.PressMenu
//import at.martinthedragon.nucleartech.recipes.PressRecipe
//import mezz.jei.api.recipe.transfer.IRecipeTransferInfo
//import net.minecraft.resources.ResourceLocation
//import net.minecraft.world.inventory.Slot
//
//class PressingRecipeTransferInfo : IRecipeTransferInfo<PressMenu, PressRecipe> {
//    override fun getContainerClass(): Class<PressMenu> = PressMenu::class.java
//    override fun getRecipeClass(): Class<PressRecipe> = PressRecipe::class.java
//    override fun getRecipeCategoryUid(): ResourceLocation = PressingJeiRecipeCategory.UID
//    override fun canHandle(container: PressMenu, recipe: PressRecipe) = true
//    override fun getRecipeSlots(container: PressMenu, recipe: PressRecipe): MutableList<Slot> = MutableList(2) { container.slots[it] }
//    override fun getInventorySlots(container: PressMenu, recipe: PressRecipe): MutableList<Slot> = MutableList(36) { container.slots[it + 4] }
//    override fun requireCompleteSets(container: PressMenu, recipe: PressRecipe) = false
//}
