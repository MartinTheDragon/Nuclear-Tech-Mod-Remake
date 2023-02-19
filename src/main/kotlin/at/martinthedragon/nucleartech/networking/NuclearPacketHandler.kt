package at.martinthedragon.nucleartech.networking

import at.martinthedragon.nucleartech.ntm
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkDirection
import net.minecraftforge.network.NetworkRegistry
import net.minecraftforge.network.simple.SimpleChannel
import java.util.*
import kotlin.reflect.KClass

object NuclearPacketHandler {
    const val PROTOCOL_VERSION = "1"

    @JvmField
    val INSTANCE: SimpleChannel = NetworkRegistry.newSimpleChannel(
        ntm("main"),
        this::PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals, // deny login if versions don't match and if channel is not present on endpoint
        PROTOCOL_VERSION::equals
    )

    private var currentPacketID = 0

    internal fun initialize() {
        registerMessage(AnvilConstructMessage::decode, NetworkDirection.PLAY_TO_SERVER)
        registerMessage(CraftMachineTemplateMessage::decode, NetworkDirection.PLAY_TO_SERVER)
        registerMessage(SetRBMKAutoControlRodValuesMessage::decode, NetworkDirection.PLAY_TO_SERVER)
        registerMessage(SetRBMKConsoleControlRodLevelMessage::decode, NetworkDirection.PLAY_TO_SERVER)
        registerMessage(SetRBMKConsoleScreenAssignedColumnsMessage::decode, NetworkDirection.PLAY_TO_SERVER)
        registerMessage(SpawnNuclearExplosionMessage::decode, NetworkDirection.PLAY_TO_SERVER)

        registerMessage(ContainerMenuUpdateMessage::decode, NetworkDirection.PLAY_TO_CLIENT)

        registerMessage(ContaminationValuesUpdateMessage::decode, NetworkDirection.PLAY_TO_CLIENT)
        registerMessage(HurtAnimationMessage::decode, NetworkDirection.PLAY_TO_CLIENT)
        registerMessage(SirenMessage::decode, NetworkDirection.PLAY_TO_CLIENT)
        registerMessage(BlockEntityUpdateMessage::decode, NetworkDirection.PLAY_TO_CLIENT) // TODO replace others with this
        registerMessage(ExplosionVNTMessage::decode, NetworkDirection.PLAY_TO_CLIENT)
    }

    private fun <T : NetworkMessage<T>> registerMessage(message: KClass<T>, decoder: (FriendlyByteBuf) -> T, networkDirection: NetworkDirection? = null): NuclearPacketHandler {
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

    private inline fun <reified T : NetworkMessage<T>> registerMessage(noinline decoder: (FriendlyByteBuf) -> T, networkDirection: NetworkDirection? = null) =
        registerMessage(T::class, decoder, networkDirection)

}
