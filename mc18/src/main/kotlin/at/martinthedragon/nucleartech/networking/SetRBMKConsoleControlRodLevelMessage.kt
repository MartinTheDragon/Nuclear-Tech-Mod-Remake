package at.martinthedragon.nucleartech.networking

import at.martinthedragon.nucleartech.menu.rbmk.RBMKConsoleMenu
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

class SetRBMKConsoleControlRodLevelMessage(val level: Double, val rods: ByteArray) : NetworkMessage<SetRBMKConsoleControlRodLevelMessage> {
    override fun encode(packetBuffer: FriendlyByteBuf) {
        packetBuffer.writeDouble(level)
        packetBuffer.writeByteArray(rods)
    }

    override fun handle(context: Supplier<NetworkEvent.Context>) {
        if (context.get().direction.receptionSide.isServer)
            context.get().enqueueWork {
                val sender = context.get().sender ?: return@enqueueWork
                val menu = sender.containerMenu
                if (menu is RBMKConsoleMenu)
                    @OptIn(ExperimentalUnsignedTypes::class)
                    menu.blockEntity.setControlRodLevelForSelection(level, rods.toUByteArray())
            }
        context.get().packetHandled = true
    }

    companion object {
        @JvmStatic
        fun decode(packetBuffer: FriendlyByteBuf) = SetRBMKConsoleControlRodLevelMessage(packetBuffer.readDouble(), packetBuffer.readByteArray())
    }
}
