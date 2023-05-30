package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.api.menu.slot.ResultSlot
import at.martinthedragon.nucleartech.block.entity.AssemblerBlockEntity
import at.martinthedragon.nucleartech.item.AssemblyTemplateItem
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class AssemblerMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: AssemblerBlockEntity,
) : NTechContainerMenu<AssemblerBlockEntity>(MenuTypes.assemblerMenu.get(), windowID, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

    init {
        addSlot(SlotItemHandler(inv, 0, 80, 18))
        for (i in 0 until 3) addSlot(SlotItemHandler(inv, 1 + i, 152, 18 + i * 18))
        addSlot(SlotItemHandler(inv, 4, 80, 54))
        for (i in 0 until 6) for (j in 0 until 2) addSlot(SlotItemHandler(inv, 5 + j + i * 2, 8 + j * 18, 18 + i * 18))
        addSlot(ResultSlot(playerInventory.player, inv, 17, 134, 90))
        addPlayerInventory(this::addSlot, playerInventory, 8, 140)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 18, intArrayOf(17)) {
        0 check supportsEnergyCondition()
        4 check itemIsInstanceCondition<AssemblyTemplateItem>()
        1..3 check compatibleMachineUpgradeCondition(blockEntity)
        5..16
    }

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            AssemblerMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
