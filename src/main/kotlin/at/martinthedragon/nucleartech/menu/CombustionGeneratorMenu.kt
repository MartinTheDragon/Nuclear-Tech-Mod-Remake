package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.api.menu.slot.OutputSlot
import at.martinthedragon.nucleartech.block.entity.CombustionGeneratorBlockEntity
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class CombustionGeneratorMenu(
    windowId: Int,
    playerInventory: Inventory,
    blockEntity: CombustionGeneratorBlockEntity,
) : NTechContainerMenu<CombustionGeneratorBlockEntity>(MenuTypes.combustionGeneratorMenu.get(), windowId, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

    init {
        addSlot(SlotItemHandler(inv, 0, 80, 53))
        addSlot(SlotItemHandler(inv, 1, 44, 17))
        addSlot(OutputSlot(inv, 2, 44, 53))
        addSlot(SlotItemHandler(inv, 3, 116, 53))
        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 4, intArrayOf(2)) {
        0 check AbstractFurnaceBlockEntity::isFuel
        1 check containsFluidCondition(Fluids.WATER)
        3 check supportsEnergyCondition()
        null
    }

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            CombustionGeneratorMenu(windowId, playerInventory, getBlockEntityForContainer(buffer))
    }
}
