package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.api.fluid.trait.getTraitForFluidStack
import at.martinthedragon.nucleartech.api.menu.slot.OutputSlot
import at.martinthedragon.nucleartech.block.entity.TurbineBlockEntity
import at.martinthedragon.nucleartech.extensions.ifPresentInline
import at.martinthedragon.nucleartech.fluid.trait.CoolableFluidTrait
import at.martinthedragon.nucleartech.fluid.trait.FluidTraitManager
import at.martinthedragon.nucleartech.item.FluidIdentifierItem
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

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (index == 1 || index == 3 || index == 5) {
                if (!moveItemStackTo(itemStack, 7, slots.size, true)) return ItemStack.EMPTY
                slot.onQuickCraft(itemStack, returnStack)
            } else if (index !in 0..6) {
                var successful = false
                when {
                    FluidTraitManager.getTraitForFluidStack<CoolableFluidTrait>(FluidStack(FluidIdentifierItem.getFluid(itemStack), 1000))?.let { it.trait.getEfficiency(it, CoolableFluidTrait.CoolingType.Turbine) > 0F } == true && moveItemStackTo(itemStack, 0, 1, false) -> successful = true
                    itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent -> itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresentInline {
                        when {
                            !it.drain(FluidStack(blockEntity.inputTank.fluid.rawFluid, 1), IFluidHandler.FluidAction.SIMULATE).isEmpty && moveItemStackTo(itemStack, 2, 3, false) -> successful = true
                            it.fill(FluidStack(blockEntity.outputTank.fluid.rawFluid, 1), IFluidHandler.FluidAction.SIMULATE) > 0 && moveItemStackTo(itemStack, 4, 5, false) -> successful = true
                        }
                    }
                    itemStack.getCapability(CapabilityEnergy.ENERGY).isPresent && moveItemStackTo(itemStack, 6, 7, false) -> successful = true
                }
                if (!successful && !tryMoveInPlayerInventory(index, 7, itemStack)) return ItemStack.EMPTY
            } else if (!moveItemStackTo(itemStack, 7, slots.size, false)) return ItemStack.EMPTY

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()

            if (itemStack.count == returnStack.count) return ItemStack.EMPTY

            slot.onTake(player, itemStack)
        }
        return returnStack
    }

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            TurbineMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
