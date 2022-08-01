package at.martinthedragon.nucleartech.networking

import at.martinthedragon.nucleartech.block.entity.LaunchPadBlockEntity
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.item.ItemStack
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

class LaunchPadMissileMessage(val blockPos: BlockPos, val missileItem: ItemStack) : NetworkMessage<LaunchPadMissileMessage> {
    override fun encode(packetBuffer: FriendlyByteBuf) {
        packetBuffer.writeBlockPos(blockPos)
        packetBuffer.writeItem(missileItem)
    }

    override fun handle(context: Supplier<NetworkEvent.Context>) {
        if (context.get().direction.receptionSide.isClient) context.get().enqueueWork {
            val level = Minecraft.getInstance().level ?: return@enqueueWork
            if (!level.isLoaded(blockPos)) return@enqueueWork
            val launchPad = level.getBlockEntity(blockPos) as? LaunchPadBlockEntity ?: return@enqueueWork
            launchPad.missileItem = missileItem
        }
        context.get().packetHandled = true
    }

    companion object {
        fun decode(packetBuffer: FriendlyByteBuf) = LaunchPadMissileMessage(packetBuffer.readBlockPos(), packetBuffer.readItem())
    }
}
