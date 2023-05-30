package at.martinthedragon.nucleartech.coremodules.forge.network

import at.martinthedragon.nucleartech.coremodules.InjectionStatic
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.chunk.LevelChunk
import java.util.function.Supplier

interface PacketDistributor<T> {
    interface Static {
        val trackingChunk: PacketDistributor<LevelChunk>
    }

    fun with(input: Supplier<T>): PacketTarget
    fun with(input: T) = with { input }

    interface PacketTarget {

    }

    companion object {
        val TRACKING_CHUNK: PacketDistributor<LevelChunk> get() = InjectionStatic.packetDistributor.trackingChunk
    }
}
