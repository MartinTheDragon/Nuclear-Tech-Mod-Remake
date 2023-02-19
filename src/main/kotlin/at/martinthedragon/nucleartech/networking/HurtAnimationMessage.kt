package at.martinthedragon.nucleartech.networking

import net.minecraft.client.Minecraft
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

class HurtAnimationMessage(val amount: Int) : NetworkMessage<HurtAnimationMessage> {
    override fun encode(packetBuffer: FriendlyByteBuf) {
        packetBuffer.writeInt(amount)
    }

    override fun handle(context: Supplier<NetworkEvent.Context>) {
        if (context.get().direction.receptionSide.isClient) context.get().enqueueWork {
            val player = Minecraft.getInstance().player ?: return@enqueueWork
            player.hurtTime = amount
            player.hurtDuration = amount
            player.hurtDir = 0F
        }
        context.get().packetHandled = true
    }

    companion object {
        @JvmStatic
        fun decode(packetBuffer: FriendlyByteBuf) = HurtAnimationMessage(packetBuffer.readInt())
    }
}
