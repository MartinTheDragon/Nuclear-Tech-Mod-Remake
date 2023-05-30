package at.martinthedragon.nucleartech.net

import at.martinthedragon.nucleartech.coremodules.InjectionStatic
import at.martinthedragon.nucleartech.net.msg.BlockEntityUpdateMessage
import kotlin.reflect.KClass

interface NTechNetMessages {
    fun entryPoint() = initialize()
    val channel: NetChannel
    fun <T : NetMessage<T>> registerMessage(message: KClass<T>, decoder: (ByteBuf) -> T, direction: NetDirection? = null)

    companion object {
        val channel: NetChannel get() = InjectionStatic.networkMessages.channel
        fun <T : NetMessage<T>> registerMessage(message: KClass<T>, decoder: (ByteBuf) -> T, direction: NetDirection? = null) = InjectionStatic.networkMessages.registerMessage(message, decoder, direction)
    }
}

inline fun <reified T : NetMessage<T>> NTechNetMessages.registerMessage(noinline decoder: (ByteBuf) -> T, direction: NetDirection? = null) =
    registerMessage(T::class, decoder, direction)

internal fun NTechNetMessages.initialize() {
    registerMessage(BlockEntityUpdateMessage::decode, NetDirection.PLAY_TO_CLIENT)
}
