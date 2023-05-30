package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.api.item.TargetDesignator
import at.martinthedragon.nucleartech.block.entity.LaunchPadBlockEntity
import at.martinthedragon.nucleartech.item.MissileItem
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class LaunchPadMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: LaunchPadBlockEntity,
) : NTechContainerMenu<LaunchPadBlockEntity>(MenuTypes.launchPadMenu.get(), windowID, playerInventory, blockEntity) {
    private val inv = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)

    init {
        addSlot(SlotItemHandler(inv, 0, 26, 17))
        addSlot(SlotItemHandler(inv, 1, 80, 17))
        addSlot(SlotItemHandler(inv, 2, 134, 17))
        addPlayerInventory(this::addSlot, playerInventory, 8, 84)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = quickMoveStackBoilerplate(player, index, 3, intArrayOf()) {
        0 check itemIsInstanceCondition<MissileItem<*>>()
        1 check itemIsInstanceCondition<TargetDesignator>()
        2 check supportsEnergyCondition()
        null
    }

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            LaunchPadMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
