package at.martinthedragon.nucleartech.menus

import at.martinthedragon.nucleartech.api.menus.slots.OutputSlot
import at.martinthedragon.nucleartech.api.menus.slots.ResultSlot
import at.martinthedragon.nucleartech.blocks.entities.ChemPlantBlockEntity
import at.martinthedragon.nucleartech.extensions.ifPresentInline
import at.martinthedragon.nucleartech.items.ChemPlantTemplateItem
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

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (index in 5..8 || index == 10 || index == 12 || index == 18 || index == 20) {
                if (!moveItemStackTo(itemStack, 21, slots.size, true)) return ItemStack.EMPTY
                slot.onQuickCraft(itemStack, returnStack)
            } else if (index !in 0..19) {
                var successful = false
                when {
                    itemStack.getCapability(CapabilityEnergy.ENERGY).isPresent && moveItemStackTo(itemStack, 0, 1, false) -> successful = true
                    itemStack.item is ChemPlantTemplateItem && moveItemStackTo(itemStack, 4, 5, false) -> successful = true
                    itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent -> itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresentInline {
                        when { // TODO maybe extract?
                            !it.drain(FluidStack(blockEntity.inputTank1.fluid.rawFluid, 1), IFluidHandler.FluidAction.SIMULATE).isEmpty && moveItemStackTo(itemStack, 17, 18, false) -> successful = true
                            !it.drain(FluidStack(blockEntity.inputTank2.fluid.rawFluid, 1), IFluidHandler.FluidAction.SIMULATE).isEmpty && moveItemStackTo(itemStack, 18, 19, false) -> successful = true
                            it.fill(FluidStack(blockEntity.outputTank1.fluid.rawFluid, 1), IFluidHandler.FluidAction.SIMULATE) > 0 && moveItemStackTo(itemStack, 9, 10, false) -> successful = true
                            it.fill(FluidStack(blockEntity.outputTank2.fluid.rawFluid, 1), IFluidHandler.FluidAction.SIMULATE) > 0 && moveItemStackTo(itemStack, 10, 11, false) -> successful = true
                            !it.drain(FluidStack(blockEntity.inputTank1.fluid.rawFluid, blockEntity.inputTank1.capacity), IFluidHandler.FluidAction.SIMULATE).isEmpty && moveItemStackTo(itemStack, 17, 18, false) -> successful = true
                            !it.drain(FluidStack(blockEntity.inputTank2.fluid.rawFluid, blockEntity.inputTank2.capacity), IFluidHandler.FluidAction.SIMULATE).isEmpty && moveItemStackTo(itemStack, 18, 19, false) -> successful = true
                            it.fill(FluidStack(blockEntity.outputTank1.fluid.rawFluid, blockEntity.outputTank1.capacity), IFluidHandler.FluidAction.SIMULATE) > 0 && moveItemStackTo(itemStack, 9, 10, false) -> successful = true
                            it.fill(FluidStack(blockEntity.outputTank2.fluid.rawFluid, blockEntity.outputTank2.capacity), IFluidHandler.FluidAction.SIMULATE) > 0 && moveItemStackTo(itemStack, 10, 11, false) -> successful = true
                        }
                    }
                    moveItemStackTo(itemStack, 13, 17, false) -> successful = true
                }
                if (!successful && !tryMoveInPlayerInventory(index, 21, itemStack)) return ItemStack.EMPTY
            } else if (!moveItemStackTo(itemStack, 21, slots.size, false)) return ItemStack.EMPTY

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()

            if (itemStack.count == returnStack.count) return ItemStack.EMPTY

            slot.onTake(player, itemStack)
        }
        return returnStack
    }

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            ChemPlantMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
