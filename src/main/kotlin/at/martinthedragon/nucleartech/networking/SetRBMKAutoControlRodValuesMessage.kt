package at.martinthedragon.nucleartech.networking

import at.martinthedragon.nucleartech.menu.rbmk.RBMKAutoControlMenu
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

class SetRBMKAutoControlRodValuesMessage(private val levelUpper: Double, private val levelLower: Double, private val heatUpper: Double, private val heatLower: Double) : NetworkMessage<SetRBMKAutoControlRodValuesMessage> {
    override fun encode(packetBuffer: FriendlyByteBuf) {
        packetBuffer.writeDouble(levelUpper)
        packetBuffer.writeDouble(levelLower)
        packetBuffer.writeDouble(heatUpper)
        packetBuffer.writeDouble(heatLower)
    }

    override fun handle(context: Supplier<NetworkEvent.Context>) {
        if (context.get().direction.receptionSide.isServer)
            context.get().enqueueWork {
                val sender = context.get().sender ?: return@enqueueWork
                val menu = sender.containerMenu
                if (menu is RBMKAutoControlMenu) {
                    val controlRod = menu.blockEntity
                    controlRod.levelUpper = levelUpper.coerceIn(0.0, 100.0)
                    controlRod.levelLower = levelLower.coerceIn(0.0, 100.0)
                    controlRod.heatUpper = heatUpper.coerceIn(0.0, 9999.0)
                    controlRod.heatLower = heatLower.coerceIn(0.0, 9999.0)
                    controlRod.markDirty()
                }
            }
        context.get().packetHandled = true
    }

    companion object {
        @JvmStatic
        fun decode(packetBuffer: FriendlyByteBuf) = SetRBMKAutoControlRodValuesMessage(packetBuffer.readDouble(), packetBuffer.readDouble(), packetBuffer.readDouble(), packetBuffer.readDouble())
    }
}
