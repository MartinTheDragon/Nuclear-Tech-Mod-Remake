package at.martinthedragon.nucleartech.net

import at.martinthedragon.nucleartech.coremodules.forge.network.PacketDistributor

interface NetChannel {
    fun <MSG> send(target: PacketDistributor.PacketTarget, message: MSG)
}
