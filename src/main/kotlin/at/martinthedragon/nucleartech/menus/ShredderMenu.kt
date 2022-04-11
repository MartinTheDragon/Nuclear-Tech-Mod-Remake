package at.martinthedragon.nucleartech.menus

import at.martinthedragon.nucleartech.blocks.entities.ShredderBlockEntity
import at.martinthedragon.nucleartech.items.ShredderBladeItem
import at.martinthedragon.nucleartech.menus.slots.ExperienceResultSlot
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

class ShredderMenu(
    windowID: Int,
    val playerInventory: Inventory,
    val tileEntity: ShredderBlockEntity,
    val data: ContainerData = SimpleContainerData(2)
) : AbstractContainerMenu(MenuTypes.shredderMenu.get(), windowID) {
    private val inv = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

    init {
        for (i in 0 until 3)
            for (j in 0 until 3)
                addSlot(SlotItemHandler(inv, j + i * 3, 44 + j * 18, 18 + i * 18))
        addSlot(SlotItemHandler(inv, 9, 8, 108))
        addSlot(SlotItemHandler(inv, 10, 44, 108))
        addSlot(SlotItemHandler(inv, 11, 80, 108))
        for (i in 0 until 6)
            for (j in 0 until 3)
                addSlot(ExperienceResultSlot(tileEntity, playerInventory.player, inv, j + i * 3 + 12, 116 + j * 18, 18 + i * 18))
        addPlayerInventory(this::addSlot, playerInventory, 8, 140)
        addDataSlots(data)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var returnStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasItem()) {
            val itemStack = slot.item
            returnStack = itemStack.copy()
            if (index in 12..29) {
                if (!moveItemStackTo(itemStack, 30, slots.size, true)) return ItemStack.EMPTY
                slot.onQuickCraft(itemStack, returnStack)
            } else if (index !in 0..11) {
                var successful = false
                when {
                    itemStack.getCapability(CapabilityEnergy.ENERGY).isPresent && moveItemStackTo(itemStack, 9, 10, false) -> successful = true
                    itemStack.item is ShredderBladeItem && moveItemStackTo(itemStack, 10, 12, false) -> successful = true
                    moveItemStackTo(itemStack, 0, 9, false) -> successful = true
                }
                if (!successful && !tryMoveInPlayerInventory(index, 30, itemStack)) return ItemStack.EMPTY
            } else if (!moveItemStackTo(itemStack, 30, slots.size, false)) return ItemStack.EMPTY

            if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
            else slot.setChanged()

            if (itemStack.count == returnStack.count) return ItemStack.EMPTY

            slot.onTake(player, itemStack)
        }
        return returnStack
    }

    override fun stillValid(player: Player) = playerInventory.stillValid(player)

    fun getShreddingProgress() = data[0]

    fun getEnergy() = data[1]

    fun getLeftBladeState(): Int = computeBladeState(10)

    fun getRightBladeState(): Int = computeBladeState(11)

    private fun computeBladeState(slotIndex: Int): Int {
        val shredderBlade = items[slotIndex]
        if (shredderBlade.item !is ShredderBladeItem) return 0
        return when {
            !shredderBlade.isDamageableItem -> 1
            shredderBlade.damageValue < shredderBlade.maxDamage / 2 -> 1
            shredderBlade.damageValue != shredderBlade.maxDamage -> 2
            else -> 3
        }
    }

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            ShredderMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
