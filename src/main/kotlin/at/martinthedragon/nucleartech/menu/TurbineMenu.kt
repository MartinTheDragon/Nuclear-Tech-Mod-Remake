package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.api.menu.slot.OutputSlot
import at.martinthedragon.nucleartech.block.entity.TurbineBlockEntity
import at.martinthedragon.nucleartech.fluid.trait.CoolableFluidTrait
import at.martinthedragon.nucleartech.item.FluidIdentifierItem
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class TurbineMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: TurbineBlockEntity
) : NTechContainerMenu<TurbineBlockEntity>(MenuTypes.turbineMenu.get(), windowID, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

    init {
        addSlot(SlotItemHandler(inv, 0, 8, 17)) // Fluid ID
        addSlot(OutputSlot(inv, 1, 8, 53))
        addSlot(SlotItemHandler(inv, 2, 44, 17)) // Input
        addSlot(OutputSlot(inv, 3, 44, 53))
        addSlot(SlotItemHandler(inv, 4, 152, 17)) // Output
        addSlot(OutputSlot(inv, 5, 152, 53))
        addSlot(SlotItemHandler(inv, 6, 98, 53))
        addPlayerInventory(this::addSlot, playerInventory, 8, 84) // Battery
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 7, intArrayOf(1, 3, 5)) {
        0 check (itemIsInstanceCondition<FluidIdentifierItem>() and fluidTraitCondition<CoolableFluidTrait>(FluidIdentifierItem::getFluid) { it.trait.getEfficiency(it, CoolableFluidTrait.CoolingType.Turbine) > 0F })
        2 check canFillTankCondition(blockEntity.inputTank)
        4 check canDrainTankCondition(blockEntity.outputTank)
        6 check supportsEnergyCondition()
        null
    }

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            TurbineMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
