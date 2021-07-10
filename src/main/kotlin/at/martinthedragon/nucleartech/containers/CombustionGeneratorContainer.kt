package at.martinthedragon.nucleartech.containers

import at.martinthedragon.nucleartech.tileentities.CombustionGeneratorTileEntity
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.fluid.Fluids
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.Slot
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketBuffer
import net.minecraft.tileentity.AbstractFurnaceTileEntity
import net.minecraft.util.IIntArray
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidUtil
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler
import net.minecraft.util.IntArray as MinecraftIntArray

class CombustionGeneratorContainer(
    windowId: Int,
    val playerInventory: PlayerInventory,
    val tileEntity: CombustionGeneratorTileEntity,
    val data: IIntArray = MinecraftIntArray(4)
) : Container(ContainerTypes.combustionGeneratorContainer.get(), windowId) {
    private val inv = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

    init {
        addSlot(SlotItemHandler(inv, 0, 80, 53))
        addSlot(SlotItemHandler(inv, 1, 44, 17))
        addSlot(object : SlotItemHandler(inv, 2, 44, 53) {
            override fun mayPlace(stack: ItemStack) = false
        })
        addSlot(SlotItemHandler(inv, 3, 116, 53))
        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
        addDataSlots(data)
    }

    private fun addPlayerInventory(
        addSlot: (Slot) -> Slot,
        playerInventory: IInventory,
        xStart: Int,
        yStart: Int,
        slotCreator: (inventory: IInventory, index: Int, x: Int, y: Int) -> Slot = ::Slot
    ) {
        val slotSize = 18
        val rows = 3
        val columns = 9

        for (i in 0 until rows)
            for (j in 0 until columns) {
                addSlot(slotCreator(playerInventory, j + i * 9 + 9, xStart + j * slotSize, yStart + i * slotSize))
            }
        val newYStart = yStart + slotSize * rows + 4

        for (i in 0 until columns) {
            addSlot(slotCreator(playerInventory, i, xStart + i * slotSize, newYStart))
        }
    }

    override fun quickMoveStack(player: PlayerEntity, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot != null && slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (index !in 0..3) {
                if (AbstractFurnaceTileEntity.isFuel(itemStack)) {
                    if (!moveItemStackTo(itemStack, 0, 1, false))
                        return ItemStack.EMPTY
                } else if (FluidUtil.getFluidContained(itemStack).let { if (it.isPresent) it.get().fluid == Fluids.WATER else false }) {
                    if (!moveItemStackTo(itemStack, 1, 2, false))
                        return ItemStack.EMPTY
                } else if (itemStack.getCapability(CapabilityEnergy.ENERGY).isPresent) {
                    if (!moveItemStackTo(itemStack, 3, 4, false))
                        return ItemStack.EMPTY
                } else if (index >= 4 && index < (slots.size - 9).coerceAtLeast(5)) {
                    if (!moveItemStackTo(itemStack, (slots.size - 9).coerceAtLeast(4), slots.size, false))
                        return ItemStack.EMPTY
                } else if (index >= (slots.size - 9) && index < slots.size && !moveItemStackTo(itemStack, 4, (slots.size - 9), false))
                    return ItemStack.EMPTY
            } else if (!moveItemStackTo(itemStack, 4, slots.size, false))
                return ItemStack.EMPTY

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()

            if (itemStack.count == returnStack.count) return ItemStack.EMPTY

            slot.onTake(player, itemStack)
        }
        return returnStack
    }

    override fun stillValid(player: PlayerEntity) = playerInventory.stillValid(player)

    fun getBurnTime() = data[0]

    fun getBurnProgress(): Int {
        val burnTime = data[0]
        val burnDuration = data[1]
        return if (burnTime != 0 && burnDuration != 0) burnTime * 14 / burnDuration else 0
    }

    fun getWaterLevel() = data[2]

    fun getEnergy() = data[3]

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: PlayerInventory, buffer: PacketBuffer) =
            CombustionGeneratorContainer(windowId, playerInventory, DistExecutor.safeRunForDist({
                DistExecutor.SafeSupplier { Minecraft.getInstance().level?.getBlockEntity(buffer.readBlockPos()) }
            }) {
                throw IllegalAccessException("Cannot call function on server")
            } as CombustionGeneratorTileEntity)
    }
}
