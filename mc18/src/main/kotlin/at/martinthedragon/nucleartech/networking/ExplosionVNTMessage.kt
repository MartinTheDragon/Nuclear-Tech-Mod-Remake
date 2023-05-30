package at.martinthedragon.nucleartech.networking

import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.level.Explosion
import net.minecraft.world.phys.Vec3
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

class ExplosionVNTMessage(private val pos: Vec3, private val power: Float, private val toBlow: List<BlockPos>, private val knockBack: Vec3) : NetworkMessage<ExplosionVNTMessage> {
    override fun encode(packetBuffer: FriendlyByteBuf) {
        packetBuffer.writeDouble(pos.x)
        packetBuffer.writeDouble(pos.y)
        packetBuffer.writeDouble(pos.z)
        packetBuffer.writeFloat(power)

        val posXFloored = pos.x.toInt()
        val posYFloored = pos.y.toInt()
        val posZFloored = pos.z.toInt()
        packetBuffer.writeCollection(toBlow) { buf, blockPos ->
            buf.writeByte(blockPos.x - posXFloored)
            buf.writeByte(blockPos.y - posYFloored)
            buf.writeByte(blockPos.z - posZFloored)
        }
        packetBuffer.writeDouble(knockBack.x)
        packetBuffer.writeDouble(knockBack.y)
        packetBuffer.writeDouble(knockBack.z)
    }

    override fun handle(context: Supplier<NetworkEvent.Context>) {
        context.get().enqueueWork {
            val level = Minecraft.getInstance().level ?: return@enqueueWork
            val player = Minecraft.getInstance().player ?: return@enqueueWork
            val explosion = Explosion(level, null, pos.x, pos.y, pos.z, power, toBlow)
            explosion.finalizeExplosion(true)
            player.deltaMovement = player.deltaMovement.add(knockBack)
        }
        context.get().packetHandled = true
    }

    companion object {
        fun decode(packetBuffer: FriendlyByteBuf): ExplosionVNTMessage {
            val pos = Vec3(packetBuffer.readDouble(), packetBuffer.readDouble(), packetBuffer.readDouble())
            val posXFloored = pos.x.toInt()
            val posYFloored = pos.y.toInt()
            val posZFloored = pos.z.toInt()
            return ExplosionVNTMessage(
                pos,
                packetBuffer.readFloat(),
                packetBuffer.readList {
                    BlockPos(it.readByte() + posXFloored, it.readByte() + posYFloored, it.readByte() + posZFloored)
                },
                Vec3(packetBuffer.readDouble(), packetBuffer.readDouble(), packetBuffer.readDouble())
            )
        }
    }
}
