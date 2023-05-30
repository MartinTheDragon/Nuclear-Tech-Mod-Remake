package at.martinthedragon.nucleartech.net.msg

import at.martinthedragon.nucleartech.block.entity.SyncedBlockEntity
import at.martinthedragon.nucleartech.coremodules.minecraft.client.Minecraft
import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPos
import at.martinthedragon.nucleartech.coremodules.minecraft.nbt.CompoundTag
import at.martinthedragon.nucleartech.net.ByteBuf
import at.martinthedragon.nucleartech.net.NetContext
import at.martinthedragon.nucleartech.net.NetMessage

class BlockEntityUpdateMessage(val pos: BlockPos, val tag: CompoundTag?) : NetMessage<BlockEntityUpdateMessage> {
    override fun encode(buffer: ByteBuf) = with(buffer) {
        writeBlockPos(pos)
        writeNbt(tag)
    }

    override fun handle(context: NetContext) {
        if (context.direction.receptionSide.isClient && tag != null) context.enqueueWork {
            val level = Minecraft.level
            if (level != null && Minecraft.player != null && level.isLoaded(pos)) {
                val blockEntity = level.getBlockEntity(pos)
                if (blockEntity is SyncedBlockEntity) blockEntity.handleContinuousUpdatePacket(tag)
                else blockEntity?.handleUpdateTag(tag)
            }
        }
        context.packetHandled = true
    }

    companion object {
        @JvmStatic fun decode(buffer: ByteBuf) = BlockEntityUpdateMessage(buffer.readBlockPos(), buffer.readNbt())
    }
}
