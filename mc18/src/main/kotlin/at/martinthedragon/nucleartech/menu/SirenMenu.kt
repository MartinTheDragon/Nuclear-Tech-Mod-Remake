package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.api.menu.slot.SlotItemHandlerUnglitched
import at.martinthedragon.nucleartech.block.entity.SirenBlockEntity
import at.martinthedragon.nucleartech.item.SirenTrackItem
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.CapabilityItemHandler

class SirenMenu(windowId: Int, playerInventory: Inventory, blockEntity: SirenBlockEntity) : NTechContainerMenu<SirenBlockEntity>(MenuTypes.sirenMenu.get(), windowId, playerInventory, blockEntity) {
    init {
        val inv = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

        addSlot(SlotItemHandlerUnglitched(inv, 0, 8, 35))

        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 1, intArrayOf()) {
        0 check itemIsInstanceCondition<SirenTrackItem>()
        null
    }

    override fun removed(player: Player) {
        super.removed(player)
        blockEntity.stopOpen(player)
    }

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            SirenMenu(windowId, playerInventory, getBlockEntityForContainer(buffer))
    }
}
