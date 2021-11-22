package at.martinthedragon.nucleartech.networking

import at.martinthedragon.nucleartech.entities.NukeExplosionEntity
import at.martinthedragon.nucleartech.screens.UseCreativeNuclearExplosionSpawnerScreen
import net.minecraft.network.PacketBuffer
import net.minecraft.util.Util
import net.minecraft.util.math.vector.Vector3d
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class SpawnNuclearExplosionMessage(
    private val strength: Int,
    private val muted: Boolean,
    private val hasFallout: Boolean,
    private val extraFallout: Int,
    private val position: Vector3d
) : NetworkMessage<SpawnNuclearExplosionMessage> {
    override fun encode(packetBuffer: PacketBuffer) {
        packetBuffer.writeInt(strength)
        packetBuffer.writeBoolean(muted)
        packetBuffer.writeBoolean(hasFallout)
        packetBuffer.writeInt(extraFallout)
        packetBuffer.writeDouble(position.x)
        packetBuffer.writeDouble(position.y)
        packetBuffer.writeDouble(position.z)
    }

    override fun handle(context: Supplier<NetworkEvent.Context>) {
        if (context.get().direction.receptionSide.isServer)
            context.get().enqueueWork {
                val sender = context.get().sender ?: return@enqueueWork
                if (!sender.canUseGameMasterBlocks()) {
                    sender.sendMessage(UseCreativeNuclearExplosionSpawnerScreen.ERR_INSUFFICIENT_PERMISSION, Util.NIL_UUID)
                    return@enqueueWork
                }
                NukeExplosionEntity.create(sender.getLevel(), position, strength, hasFallout, extraFallout, muted)
            }
        context.get().packetHandled = true
    }

    companion object {
        @JvmStatic
        fun decode(packetBuffer: PacketBuffer) = SpawnNuclearExplosionMessage(
            packetBuffer.readInt(),
            packetBuffer.readBoolean(),
            packetBuffer.readBoolean(),
            packetBuffer.readInt(),
            Vector3d(packetBuffer.readDouble(), packetBuffer.readDouble(), packetBuffer.readDouble())
        )
    }
}
