package at.martinthedragon.nucleartech.networking

import at.martinthedragon.nucleartech.api.sound.SoundHandler
import at.martinthedragon.nucleartech.item.SirenTrackItem
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.sounds.SoundSource
import net.minecraft.world.item.ItemStack
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

class SirenMessage(val blockPos: BlockPos, val sirenTrack: ItemStack) : NetworkMessage<SirenMessage> {
    override fun encode(packetBuffer: FriendlyByteBuf) {
        packetBuffer.writeBlockPos(blockPos)
        packetBuffer.writeItem(sirenTrack)
    }

    override fun handle(context: Supplier<NetworkEvent.Context>) {
        if (context.get().direction.receptionSide.isClient) context.get().enqueueWork {
            if (sirenTrack.isEmpty) SoundHandler.stopBlockEntitySound(blockPos)
            val item = sirenTrack.item
            if (item !is SirenTrackItem) return@enqueueWork
            SoundHandler.stopBlockEntitySound(blockPos)
            SoundHandler.playBlockEntitySoundEvent(blockPos, item.soundSupplier.get(), SoundSource.BLOCKS, 1F, 1F, item.loop)
        }
        context.get().packetHandled = true
    }

    companion object {
        @JvmStatic
        fun decode(packetBuffer: FriendlyByteBuf) = SirenMessage(packetBuffer.readBlockPos(), packetBuffer.readItem())
    }
}
