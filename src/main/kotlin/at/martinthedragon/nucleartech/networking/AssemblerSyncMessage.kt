package at.martinthedragon.nucleartech.networking

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.blocks.entities.AssemblerBlockEntity
import at.martinthedragon.nucleartech.ntm
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

class AssemblerSyncMessage(val pos: BlockPos, val recipeID: ResourceLocation?, val isProgressing: Boolean) : NetworkMessage<AssemblerSyncMessage> {
    override fun encode(packetBuffer: FriendlyByteBuf) {
        packetBuffer.writeBlockPos(pos)
        packetBuffer.writeResourceLocation(recipeID ?: ntm("noneb33fc0ffee"))
        packetBuffer.writeBoolean(isProgressing)
    }

    override fun handle(context: Supplier<NetworkEvent.Context>) {
        if (context.get().direction.receptionSide.isClient) context.get().enqueueWork {
            val level = Minecraft.getInstance().level
            if (level != null && Minecraft.getInstance().player != null && level.isLoaded(pos) && level.getBlockState(pos).`is`(ModBlocks.assembler.get())) {
                val blockEntity = level.getBlockEntity(pos)
                if (blockEntity !is AssemblerBlockEntity) return@enqueueWork
                blockEntity.recipeID = recipeID
                blockEntity.progress = if (isProgressing) 1 else 0
            }
        }
        context.get().packetHandled = true
    }

    companion object {
        @JvmStatic
        fun decode(packetBuffer: FriendlyByteBuf) = AssemblerSyncMessage(packetBuffer.readBlockPos(), packetBuffer.readResourceLocation().let { if (it == ntm("noneb33fc0ffee")) null else it }, packetBuffer.readBoolean())
    }
}
