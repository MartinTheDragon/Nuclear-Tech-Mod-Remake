package at.martinthedragon.nucleartech.networking

import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

interface NetworkMessage<out T : NetworkMessage<T>> {
    fun encode(packetBuffer: PacketBuffer)
    fun handle(context: Supplier<NetworkEvent.Context>)
}
