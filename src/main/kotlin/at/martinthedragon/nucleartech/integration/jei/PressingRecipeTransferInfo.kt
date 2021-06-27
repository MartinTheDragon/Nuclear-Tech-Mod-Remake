package at.martinthedragon.nucleartech.integration.jei

import at.martinthedragon.nucleartech.containers.PressContainer
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo
import net.minecraft.inventory.container.Slot
import net.minecraft.util.ResourceLocation

class PressingRecipeTransferInfo : IRecipeTransferInfo<PressContainer> {
    override fun getContainerClass(): Class<PressContainer> = PressContainer::class.java

    override fun getRecipeCategoryUid(): ResourceLocation = PressingJeiRecipeCategory.UID

    override fun canHandle(p0: PressContainer) = true

    override fun getRecipeSlots(p0: PressContainer): MutableList<Slot> = MutableList(2) { p0.slots[it] }

    override fun getInventorySlots(p0: PressContainer): MutableList<Slot> = MutableList(36) { p0.slots[it + 4] }

    override fun requireCompleteSets() = false
}
