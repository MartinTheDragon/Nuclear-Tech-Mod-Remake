package at.martinthedragon.nucleartech.networking

import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

class BlockEntityUpdateMessage(val pos: BlockPos, val tag: CompoundTag?) : NetworkMessage<BlockEntityUpdateMessage> {
    override fun encode(packetBuffer: FriendlyByteBuf) {
        packetBuffer.writeBlockPos(pos)
        packetBuffer.writeNbt(tag)
    }

    override fun handle(context: Supplier<NetworkEvent.Context>) {
        if (context.get().direction.receptionSide.isClient && tag != null) context.get().enqueueWork {
            val level = Minecraft.getInstance().level
            if (level != null && Minecraft.getInstance().player != null && level.isLoaded(pos)) {
                val blockEntity = level.getBlockEntity(pos)
                blockEntity?.handleUpdateTag(tag)
            }
        }
        context.get().packetHandled = true
    }

    companion object {
        @JvmStatic fun decode(packetBuffer: FriendlyByteBuf) = BlockEntityUpdateMessage(packetBuffer.readBlockPos(), packetBuffer.readNbt())
    }
}
