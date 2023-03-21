package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.block.entity.SafeBlockEntity
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class SafeMenu(windowId: Int, playerInventory: Inventory, blockEntity: SafeBlockEntity) : NTechContainerMenu<SafeBlockEntity>(MenuTypes.safeMenu.get(), windowId, playerInventory, blockEntity) {
    init {
        val inv = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

        for (i in 0 until 3)
            for (j in 0 until 5) {
                addSlot(SlotItemHandler(inv, j + i * 5, 44 + j * 18, 18 + i * 18))
            }

        addPlayerInventory(this::addSlot, playerInventory, 8, 86)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 15, intArrayOf()) {
        0..14
    }

    override fun removed(player: Player) {
        super.removed(player)
        blockEntity.stopOpen(player)
    }

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            SafeMenu(windowId, playerInventory, getBlockEntityForContainer(buffer))
    }
}
