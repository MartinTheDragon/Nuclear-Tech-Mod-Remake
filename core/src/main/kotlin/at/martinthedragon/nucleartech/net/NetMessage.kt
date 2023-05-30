package at.martinthedragon.nucleartech.net

import java.util.function.Supplier

interface NetMessage<out T : NetMessage<T>> {
    fun encode(buffer: ByteBuf)
    fun handle(context: NetContext)
    fun handle(context: Supplier<NetContext>) = handle(context.get())
}
