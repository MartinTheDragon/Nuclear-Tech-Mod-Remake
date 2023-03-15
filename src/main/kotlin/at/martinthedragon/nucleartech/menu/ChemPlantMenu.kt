package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.api.menu.slot.OutputSlot
import at.martinthedragon.nucleartech.api.menu.slot.ResultSlot
import at.martinthedragon.nucleartech.block.entity.ChemPlantBlockEntity
import at.martinthedragon.nucleartech.item.ChemPlantTemplateItem
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class ChemPlantMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: ChemPlantBlockEntity
) : NTechContainerMenu<ChemPlantBlockEntity>(MenuTypes.chemPlantMenu.get(), windowID, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

    init {
        val player = playerInventory.player

        addSlot(SlotItemHandler(inv, 0, 80, 18))
        for (i in 0 until 3) addSlot(SlotItemHandler(inv, 1 + i, 116, 18 + i * 18))
        addSlot(SlotItemHandler(inv, 4, 80, 54))

        // Output
        addSlot(ResultSlot(player, inv, 5, 134, 90))
        addSlot(ResultSlot(player, inv, 6, 152, 90))
        addSlot(ResultSlot(player, inv, 7, 134, 108))
        addSlot(ResultSlot(player, inv, 8, 152, 108))

        // Fluid Out
        addSlot(SlotItemHandler(inv, 9, 134, 54))
        addSlot(SlotItemHandler(inv, 10, 152, 54))
        addSlot(OutputSlot(inv, 11, 134, 72))
        addSlot(OutputSlot(inv, 12, 152, 72))

        // Input
        addSlot(SlotItemHandler(inv, 13, 8, 90))
        addSlot(SlotItemHandler(inv, 14, 26, 90))
        addSlot(SlotItemHandler(inv, 15, 8, 108))
        addSlot(SlotItemHandler(inv, 16, 26, 108))

        // Fluid In
        addSlot(SlotItemHandler(inv, 17, 8, 54))
        addSlot(SlotItemHandler(inv, 18, 26, 54))
        addSlot(OutputSlot(inv, 19, 8, 72))
        addSlot(OutputSlot(inv, 20, 26, 72))
        addPlayerInventory(this::addSlot, playerInventory, 8, 140)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 21, intArrayOf(5, 6, 7, 8, 11, 12, 19, 20)) {
        0 check supportsEnergyCondition()
        4 check itemIsInstanceCondition<ChemPlantTemplateItem>()
        1..3 check compatibleMachineUpgradeCondition(blockEntity)
        9 check canDrainTankCondition(blockEntity.outputTank1)
        10 check canDrainTankCondition(blockEntity.outputTank2)
        17 check canFillTankCondition(blockEntity.inputTank1)
        18 check canFillTankCondition(blockEntity.inputTank2)
        13..16
    }

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            ChemPlantMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
