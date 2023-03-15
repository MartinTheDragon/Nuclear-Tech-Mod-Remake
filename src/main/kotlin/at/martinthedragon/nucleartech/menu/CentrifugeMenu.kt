package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.api.menu.slot.ResultSlot
import at.martinthedragon.nucleartech.block.entity.CentrifugeBlockEntity
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class CentrifugeMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: CentrifugeBlockEntity
) : NTechContainerMenu<CentrifugeBlockEntity>(MenuTypes.centrifugeMenu.get(), windowID, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

    init {
        addSlot(SlotItemHandler(inv, 0, 9, 50))
        addSlot(SlotItemHandler(inv, 1, 149, 22))
        addSlot(SlotItemHandler(inv, 2, 149, 40))
        addSlot(SlotItemHandler(inv, 3, 36, 50))
        for (i in 0 until 4) addSlot(ResultSlot(playerInventory.player, inv, 4 + i, 63 + i * 20, 50))
        addPlayerInventory(this::addSlot, playerInventory, 8, 104)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 8, intArrayOf(4, 5, 6, 7)) {
        0 check supportsEnergyCondition()
        1..2 check compatibleMachineUpgradeCondition(blockEntity)
        3..3
    }

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            CentrifugeMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
