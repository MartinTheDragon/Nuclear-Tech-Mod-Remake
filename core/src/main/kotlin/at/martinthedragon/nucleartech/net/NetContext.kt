package at.martinthedragon.nucleartech.net

import at.martinthedragon.nucleartech.coremodules.minecraft.server.level.ServerPlayer

interface NetContext {
    val direction: NetDirection
    var packetHandled: Boolean
    val sender: ServerPlayer?

    fun enqueueWork(runnable: Runnable)
}
