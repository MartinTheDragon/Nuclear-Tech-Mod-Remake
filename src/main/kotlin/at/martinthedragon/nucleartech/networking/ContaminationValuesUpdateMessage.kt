package at.martinthedragon.nucleartech.networking

import at.martinthedragon.nucleartech.capabilites.contamination.CapabilityContaminationHandler
import at.martinthedragon.nucleartech.capabilites.contamination.EntityContaminationHandler
import net.minecraft.client.Minecraft
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent
import java.io.IOException
import java.util.function.Supplier

class ContaminationValuesUpdateMessage(val data: CompoundNBT?) : NetworkMessage<ContaminationValuesUpdateMessage> {
    override fun encode(packetBuffer: PacketBuffer) {
        packetBuffer.writeNbt(data)
    }

    override fun handle(context: Supplier<NetworkEvent.Context>) {
        if (context.get().direction.receptionSide.isClient)
            context.get().enqueueWork {
                if (data != null && Minecraft.getInstance().level != null && Minecraft.getInstance().player != null) try {
                    val capability = CapabilityContaminationHandler.getCapability(Minecraft.getInstance().player!!)
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
        fun decode(packetBuffer: PacketBuffer) = ContaminationValuesUpdateMessage(packetBuffer.readNbt())
    }
}
