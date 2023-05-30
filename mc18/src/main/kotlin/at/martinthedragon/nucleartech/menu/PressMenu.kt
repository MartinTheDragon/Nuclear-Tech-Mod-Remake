package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.NTechTags
import at.martinthedragon.nucleartech.api.menu.slot.ExperienceResultSlot
import at.martinthedragon.nucleartech.block.entity.SteamPressBlockEntity
import at.martinthedragon.nucleartech.recipe.RecipeTypes
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class PressMenu(
    windowId: Int,
    playerInventory: Inventory,
    blockEntity: SteamPressBlockEntity,
) : NTechContainerMenu<SteamPressBlockEntity>(MenuTypes.steamPressMenu.get(), windowId, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)
    private val level = playerInventory.player.level

    init {
        addSlot(SlotItemHandler(inv, 0, 80, 53))
        addSlot(SlotItemHandler(inv, 1, 80, 17))
        addSlot(SlotItemHandler(inv, 2, 26, 53))
        addSlot(ExperienceResultSlot(blockEntity, playerInventory.player, inv, 3, 140, 35))
        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 4, intArrayOf(3)) {
        0 check this@PressMenu::canPress
        1 check itemTagCondition(NTechTags.Items.STAMPS)
        2 check AbstractFurnaceBlockEntity::isFuel
        null
    }

    private fun canPress(itemStack: ItemStack) =
        level.recipeManager.getRecipeFor(RecipeTypes.PRESSING, SimpleContainer(itemStack), level).isPresent

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            PressMenu(windowId, playerInventory, getBlockEntityForContainer(buffer))
    }
}
