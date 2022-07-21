package at.martinthedragon.nucleartech.networking

import at.martinthedragon.nucleartech.menus.NTechContainerMenu
import at.martinthedragon.nucleartech.menus.slots.data.NTechDataSlot
import net.minecraft.client.Minecraft
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

class ContainerMenuUpdateMessage(val windowID: Int, val data: List<NTechDataSlot.Data>) : NetworkMessage<ContainerMenuUpdateMessage> {
    override fun encode(packetBuffer: FriendlyByteBuf) {
        packetBuffer.writeShort(windowID)
        packetBuffer.writeVarInt(data.size)
        for (data in data) data.writeToBuffer(packetBuffer)
    }

    override fun handle(context: Supplier<NetworkEvent.Context>) {
        if (context.get().direction.receptionSide.isClient) context.get().enqueueWork {
            val player = Minecraft.getInstance().player ?: return@enqueueWork
            val containerMenu = player.containerMenu
            if (containerMenu !is NTechContainerMenu<*> || containerMenu.containerId != windowID) return@enqueueWork
            for (data in data) data.handleDataUpdate(containerMenu)
        }
        context.get().packetHandled = true
    }

    companion object {
        @JvmStatic fun decode(packetBuffer: FriendlyByteBuf): ContainerMenuUpdateMessage = with(packetBuffer) {
            val windowID = readShort()
            val size = readVarInt()
            val data = buildList(size) { for (i in 0 until size) add(NTechDataSlot.Data.readFromBuffer(packetBuffer)) }
            ContainerMenuUpdateMessage(windowID.toInt(), data)
        }
    }
}
