package at.martinthedragon.nucleartech.networking

import at.martinthedragon.nucleartech.NuclearTech
import net.minecraft.network.PacketBuffer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkRegistry
import net.minecraftforge.fml.network.simple.SimpleChannel
import java.util.*
import kotlin.reflect.KClass

object NuclearPacketHandler {
    private const val PROTOCOL_VERSION = "1"
    val INSTANCE: SimpleChannel = NetworkRegistry.newSimpleChannel(
        ResourceLocation(NuclearTech.MODID, "main"),
        this::PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals, // deny login if versions don't match and if channel is not present on endpoint
        PROTOCOL_VERSION::equals
    )

    private var currentPacketID = 0

    fun initialize() {
        registerMessage(CraftMachineTemplateMessage.Companion::decode, NetworkDirection.PLAY_TO_SERVER)
    }

    fun <T : NetworkMessage<T>> registerMessage(message: KClass<T>, decoder: (PacketBuffer) -> T, networkDirection: NetworkDirection? = null): NuclearPacketHandler {
        @Suppress("INACCESSIBLE_TYPE")
        INSTANCE.registerMessage(
            currentPacketID++,
            message.java,
            NetworkMessage<T>::encode,
            decoder,
            NetworkMessage<T>::handle,
            Optional.ofNullable(networkDirection)
        )
        return this
    }

    inline fun <reified T : NetworkMessage<T>> registerMessage(noinline decoder: (PacketBuffer) -> T, networkDirection: NetworkDirection? = null) =
        registerMessage(T::class, decoder, networkDirection)

}
