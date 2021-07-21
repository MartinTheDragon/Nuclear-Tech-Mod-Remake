package at.martinthedragon.nucleartech.containers

import at.martinthedragon.nucleartech.containers.slots.ExperienceResultSlot
import at.martinthedragon.nucleartech.tileentities.ElectricFurnaceTileEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.container.Container
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipeType
import net.minecraft.network.PacketBuffer
import net.minecraft.util.IIntArray
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler
import net.minecraft.util.IntArray as MinecraftIntArray

class ElectricFurnaceContainer(
    windowId: Int,
    val playerInventory: PlayerInventory,
    val tileEntity: ElectricFurnaceTileEntity,
    val data: IIntArray = MinecraftIntArray(2)
) : Container(ContainerTypes.electricFurnaceContainer.get(), windowId) {
    private val inv = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)
    private val level = playerInventory.player.level

    init {
        addSlot(SlotItemHandler(inv, 0, 56, 17))
        addSlot(SlotItemHandler(inv, 1, 56, 53))
        addSlot(ExperienceResultSlot(tileEntity, playerInventory.player, inv, 2, 116, 35))
        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
        addDataSlots(data)
    }

    override fun quickMoveStack(player: PlayerEntity, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot != null && slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (index == 2) {
                if (!moveItemStackTo(itemStack, 3, slots.size, true)) return ItemStack.EMPTY
                slot.onQuickCraft(itemStack, returnStack)
            } else if (index != 0 && index != 1) {
                var successful = false
                when {
                    canCook(itemStack) && moveItemStackTo(itemStack, 0, 1, false) -> successful = true
                    itemStack.getCapability(CapabilityEnergy.ENERGY).isPresent && moveItemStackTo(itemStack, 1, 2, false) -> successful = true
                }
                if (!successful && !tryMoveInPlayerInventory(index, 3, itemStack)) return ItemStack.EMPTY
            } else if (!moveItemStackTo(itemStack, 3, slots.size, false)) return ItemStack.EMPTY

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()

            if (itemStack.count == returnStack.count) return ItemStack.EMPTY

            slot.onTake(player, itemStack)
        }
        return returnStack
    }

    private fun canCook(itemStack: ItemStack) =
        level.recipeManager.getRecipeFor(IRecipeType.SMELTING, Inventory(itemStack), level).isPresent

    override fun stillValid(player: PlayerEntity) = playerInventory.stillValid(player)

    fun getCookingProgress() = data[0]

    fun getEnergy() = data[1]

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: PlayerInventory, buffer: PacketBuffer) =
            ElectricFurnaceContainer(windowId, playerInventory, getTileEntityForContainer(buffer))
    }
}
