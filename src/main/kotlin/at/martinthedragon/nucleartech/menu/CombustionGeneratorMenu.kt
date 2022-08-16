package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.api.menu.slot.OutputSlot
import at.martinthedragon.nucleartech.block.entity.CombustionGeneratorBlockEntity
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.fluids.FluidUtil
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

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (index !in 0..3) {
                var successful = false
                when {
                    AbstractFurnaceBlockEntity.isFuel(itemStack) && moveItemStackTo(itemStack, 0, 1, false) -> successful = true
                    FluidUtil.getFluidContained(itemStack).let { if (it.isPresent) it.get().fluid == Fluids.WATER else false } && moveItemStackTo(itemStack, 1, 2, false) -> successful = true
                    itemStack.getCapability(CapabilityEnergy.ENERGY).isPresent && moveItemStackTo(itemStack, 3, 4, false) -> successful = true
                }
                if (!successful && !tryMoveInPlayerInventory(index, 4, itemStack)) return ItemStack.EMPTY
            } else if (!moveItemStackTo(itemStack, 4, slots.size, false)) return ItemStack.EMPTY

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()

            if (itemStack.count == returnStack.count) return ItemStack.EMPTY

            slot.onTake(player, itemStack)
        }
        return returnStack
    }

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            CombustionGeneratorMenu(windowId, playerInventory, getBlockEntityForContainer(buffer))
    }
}
