package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.api.menu.slot.OutputSlot
import at.martinthedragon.nucleartech.block.entity.AbstractOilWellBlockEntity
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class OilWellMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: AbstractOilWellBlockEntity
) : NTechContainerMenu<AbstractOilWellBlockEntity>(MenuTypes.oilWellMenu.get(), windowID, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

    init {
        addSlot(SlotItemHandler(inv, 0, 8, 53))
        for (i in 0 until 3) addSlot(SlotItemHandler(inv, 1 + i, 152, 17 + i * 18))
        addSlot(SlotItemHandler(inv, 4, 80, 17))
        addSlot(OutputSlot(inv, 5, 80, 53))
        addSlot(SlotItemHandler(inv, 6, 125, 17))
        addSlot(OutputSlot(inv, 7, 125, 53))
        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 8, intArrayOf(5, 7)) {
        0 check supportsEnergyCondition()
        1..3 check compatibleMachineUpgradeCondition(blockEntity)
        4 check canDrainTankCondition(blockEntity.oilTank)
        6 check canDrainTankCondition(blockEntity.gasTank)
        null
    }

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            OilWellMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
