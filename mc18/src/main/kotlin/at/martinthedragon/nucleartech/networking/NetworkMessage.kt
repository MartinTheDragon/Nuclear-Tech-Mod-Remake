package at.martinthedragon.nucleartech.networking

import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

interface NetworkMessage<out T : NetworkMessage<T>> {
    fun encode(packetBuffer: FriendlyByteBuf)
    fun handle(context: Supplier<NetworkEvent.Context>)
}
