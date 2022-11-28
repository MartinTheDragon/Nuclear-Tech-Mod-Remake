package at.martinthedragon.nucleartech.menu.rbmk

import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKConsoleBlockEntity
import at.martinthedragon.nucleartech.menu.MenuTypes
import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import at.martinthedragon.nucleartech.menu.getBlockEntityForContainer
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

class RBMKConsoleMenu(
    windowID: Int,
    playerInventory: Inventory,
    blockEntity: RBMKConsoleBlockEntity
) : NTechContainerMenu<RBMKConsoleBlockEntity>(MenuTypes.rbmkConsoleMenu.get(), windowID, playerInventory, blockEntity) {
    override fun clickMenuButton(player: Player, button: Int): Boolean {
        if (button in 0..5) {
            val screen = blockEntity.screens[button]
            val nextType = screen.type.ordinal + 1
            screen.type = RBMKConsoleBlockEntity.Screen.Type.values()[nextType % RBMKConsoleBlockEntity.Screen.Type.values().size]
            blockEntity.markDirty()
            return true
        }

        return super.clickMenuButton(player, button)
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack = ItemStack.EMPTY

    companion object {
        fun fromNetwork(windowID: Int, playerInventory: Inventory, buffer: FriendlyByteBuf) =
            RBMKConsoleMenu(windowID, playerInventory, getBlockEntityForContainer(buffer))
    }
}
