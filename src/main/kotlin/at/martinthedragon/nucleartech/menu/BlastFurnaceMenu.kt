package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.api.menu.slot.ExperienceResultSlot
import at.martinthedragon.nucleartech.block.entity.BlastFurnaceBlockEntity
import at.martinthedragon.nucleartech.recipe.RecipeTypes
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class BlastFurnaceMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: BlastFurnaceBlockEntity,
) : NTechContainerMenu<BlastFurnaceBlockEntity>(MenuTypes.blastFurnaceMenu.get(), windowID, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)
    private val level = playerInventory.player.level

    init {
        addSlot(SlotItemHandler(inv, 0, 80, 18))
        addSlot(SlotItemHandler(inv, 1, 80, 54))
        addSlot(SlotItemHandler(inv, 2, 8, 36))
        addSlot(ExperienceResultSlot(blockEntity, playerInventory.player, inv, 3, 134, 36))
        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 4, intArrayOf(3)) {
        0..1 check this@BlastFurnaceMenu::canBlast
        2 check AbstractFurnaceBlockEntity::isFuel
        null
    }

    private fun canBlast(itemStack: ItemStack) =
        level.recipeManager.getRecipeFor(RecipeTypes.BLASTING, SimpleContainer(itemStack), level).isPresent

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            BlastFurnaceMenu(windowId, playerInventory, getBlockEntityForContainer(buffer))
    }
}
