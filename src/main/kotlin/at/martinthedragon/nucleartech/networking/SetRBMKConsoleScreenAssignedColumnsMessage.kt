package at.martinthedragon.nucleartech.networking

import at.martinthedragon.nucleartech.menu.rbmk.RBMKConsoleMenu
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

class SetRBMKConsoleScreenAssignedColumnsMessage(val screen: Int, val columns: ByteArray) : NetworkMessage<SetRBMKConsoleScreenAssignedColumnsMessage> {
    override fun encode(packetBuffer: FriendlyByteBuf) {
        packetBuffer.writeByte(screen)
        packetBuffer.writeByteArray(columns)
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    override fun handle(context: Supplier<NetworkEvent.Context>) {
        if (context.get().direction.receptionSide.isServer)
            context.get().enqueueWork {
                val sender = context.get().sender ?: return@enqueueWork
                val menu = sender.containerMenu
                if (menu is RBMKConsoleMenu) {
                    menu.blockEntity.assignColumnsToScreen(screen, columns.toUByteArray())
                    menu.blockEntity.markDirty()
                }
            }
        context.get().packetHandled = true
    }

    companion object {
        @JvmStatic
        fun decode(packetBuffer: FriendlyByteBuf) = SetRBMKConsoleScreenAssignedColumnsMessage(packetBuffer.readByte().toInt(), packetBuffer.readByteArray())
    }
}
