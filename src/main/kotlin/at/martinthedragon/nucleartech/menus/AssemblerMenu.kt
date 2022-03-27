package at.martinthedragon.nucleartech.menus

import at.martinthedragon.nucleartech.blocks.entities.AssemblerBlockEntity
import at.martinthedragon.nucleartech.items.AssemblyTemplateItem
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.item.ItemStack
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class AssemblerMenu(
    windowID: Int,
    val playerInventory: Inventory,
    val blockEntity: AssemblerBlockEntity,
    val data: ContainerData = SimpleContainerData(3)
) : AbstractContainerMenu(MenuTypes.assemblerMenu.get(), windowID) {
    private val inv = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

    init {
        addSlot(SlotItemHandler(inv, 0, 80, 18))
        for (i in 0 until 3) addSlot(SlotItemHandler(inv, 1 + i, 152, 18 + i * 18))
        addSlot(SlotItemHandler(inv, 4, 80, 54))
        for (i in 0 until 6) for (j in 0 until 2) addSlot(SlotItemHandler(inv, 5 + j + i * 2, 8 + j * 18, 18 + i * 18))
        addSlot(SlotItemHandler(inv, 17, 134, 90))
        addPlayerInventory(this::addSlot, playerInventory, 8, 140)
        addDataSlots(data)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (index == 17) {
                if (!moveItemStackTo(itemStack, 18, slots.size, true)) return ItemStack.EMPTY
                slot.onQuickCraft(itemStack, returnStack)
            } else if (index !in 0..16) {
                var successful = false
                when {
                    itemStack.getCapability(CapabilityEnergy.ENERGY).isPresent && moveItemStackTo(itemStack, 0, 1, false) -> successful = true
                    itemStack.item is AssemblyTemplateItem && moveItemStackTo(itemStack, 4, 5, false) -> successful = true
                    moveItemStackTo(itemStack, 5, 17, false) -> successful = true
                }
                if (!successful && !tryMoveInPlayerInventory(index, 18, itemStack)) return ItemStack.EMPTY
            } else if (!moveItemStackTo(itemStack, 18, slots.size, false)) return ItemStack.EMPTY

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()

            if (itemStack.count == returnStack.count) return ItemStack.EMPTY

            slot.onTake(player, itemStack)
        }
        return returnStack
    }

    override fun stillValid(player: Player) = playerInventory.stillValid(player)

    fun getEnergy(): Int = data[0]
    fun getProgress(): Int = data[1]
    fun getMaxProgress(): Int = data[2]

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            AssemblerMenu(windowID, playerInventory, getTileEntityForContainer(buffer))
    }
}
