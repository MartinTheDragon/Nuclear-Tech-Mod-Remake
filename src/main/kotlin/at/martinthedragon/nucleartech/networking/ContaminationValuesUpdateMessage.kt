package at.martinthedragon.nucleartech.networking

import at.martinthedragon.nucleartech.capabilites.Capabilities
import at.martinthedragon.nucleartech.capabilites.contamination.EntityContaminationHandler
import net.minecraft.client.Minecraft
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkEvent
import java.io.IOException
import java.util.function.Supplier

class ContaminationValuesUpdateMessage(val data: CompoundTag?) : NetworkMessage<ContaminationValuesUpdateMessage> {
    override fun encode(packetBuffer: FriendlyByteBuf) {
        packetBuffer.writeNbt(data)
    }

    override fun handle(context: Supplier<NetworkEvent.Context>) {
        if (context.get().direction.receptionSide.isClient)
            context.get().enqueueWork {
                if (data != null && Minecraft.getInstance().level != null && Minecraft.getInstance().player != null) try {
                    val capability = Capabilities.getContamination(Minecraft.getInstance().player!!)
                    if (capability !is EntityContaminationHandler) throw RuntimeException("Custom contamination handlers aren't supported yet")
                    capability.deserializeNBT(data)
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
            }
        context.get().packetHandled = true
    }

    companion object {
        @JvmStatic
        fun decode(packetBuffer: FriendlyByteBuf) = ContaminationValuesUpdateMessage(packetBuffer.readNbt())
    }
}
