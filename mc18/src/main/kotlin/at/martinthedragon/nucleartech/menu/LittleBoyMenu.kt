package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.block.entity.LittleBoyBlockEntity
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class LittleBoyMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: LittleBoyBlockEntity,
    val data: ContainerData = SimpleContainerData(1)
) : NTechContainerMenu<LittleBoyBlockEntity>(MenuTypes.littleBoyMenu.get(), windowID, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

    init {
        for (i in 0..4) addSlot(SlotItemHandler(inv, i, 26 + 18 * i, 36))
        addPlayerInventory(this::addSlot, playerInventory, 8, 140)
        addDataSlots(data)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 5, intArrayOf()) {
        0..4
    }

    fun getBombCompletion() = data[0]

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            LittleBoyMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
