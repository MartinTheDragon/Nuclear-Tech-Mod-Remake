package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.api.menu.slot.OutputSlot
import at.martinthedragon.nucleartech.block.entity.AbstractOilWellBlockEntity
import at.martinthedragon.nucleartech.extensions.ifPresentInline
import at.martinthedragon.nucleartech.item.upgrades.MachineUpgradeItem
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler
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

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (index == 5 || index == 7) {
                if (!moveItemStackTo(itemStack, 8, slots.size, true)) return ItemStack.EMPTY
            } else if (index !in 0..7) {
                var successful = false
                when {
                    itemStack.getCapability(CapabilityEnergy.ENERGY).isPresent && moveItemStackTo(itemStack, 0, 1, false) -> successful = true
                    MachineUpgradeItem.isValidFor(blockEntity, itemStack) && moveItemStackTo(itemStack, 1, 4, false) -> successful = true
                    itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent -> itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresentInline {
                        when {
                            it.fill(FluidStack(blockEntity.oilTank.fluid.rawFluid, 1), IFluidHandler.FluidAction.SIMULATE) > 0 && moveItemStackTo(itemStack, 4, 5, false) -> successful = true
                            it.fill(FluidStack(blockEntity.gasTank.fluid.rawFluid, 1), IFluidHandler.FluidAction.SIMULATE) > 0 && moveItemStackTo(itemStack, 6, 7, false) -> successful = true
                        }
                    }
                }
                if (!successful && !tryMoveInPlayerInventory(index, 8, itemStack)) return ItemStack.EMPTY
            } else if (!moveItemStackTo(itemStack, 8, slots.size, false)) return ItemStack.EMPTY

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()

            if (itemStack.count == returnStack.count) return ItemStack.EMPTY

            slot.onTake(player, itemStack)
        }
        return returnStack
    }

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            OilWellMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
