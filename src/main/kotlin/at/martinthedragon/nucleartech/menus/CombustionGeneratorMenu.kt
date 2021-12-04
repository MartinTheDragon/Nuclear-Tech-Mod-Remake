package at.martinthedragon.nucleartech.menus

import at.martinthedragon.nucleartech.blocks.entities.CombustionGeneratorBlockEntity
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.fluids.FluidUtil
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class CombustionGeneratorMenu(
    windowId: Int,
    val playerInventory: Inventory,
    val tileEntity: CombustionGeneratorBlockEntity,
    val data: ContainerData = SimpleContainerData(4)
) : AbstractContainerMenu(MenuTypes.combustionGeneratorMenu.get(), windowId) {
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

    override fun stillValid(player: Player) = playerInventory.stillValid(player)

    fun getBurnTime() = data[0]

    fun getBurnProgress(): Int {
        val burnTime = data[0]
        val burnDuration = data[1]
        return if (burnTime != 0 && burnDuration != 0) burnTime * 14 / burnDuration else 0
    }

    fun getWaterLevel() = data[2]

    fun getEnergy() = data[3]

    companion object {
        fun fromNetwork(windowId: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            CombustionGeneratorMenu(windowId, playerInventory, getTileEntityForContainer(buffer))
    }
}
