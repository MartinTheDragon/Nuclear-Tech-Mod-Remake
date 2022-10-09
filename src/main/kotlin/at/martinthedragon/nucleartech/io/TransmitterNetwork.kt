package at.martinthedragon.nucleartech.io

import at.martinthedragon.nucleartech.math.DirectionMask
import com.google.common.collect.HashBasedTable
import it.unimi.dsi.fastutil.objects.Object2ByteOpenHashMap
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import java.util.*
import kotlin.experimental.or

abstract class TransmitterNetwork<TRANSMITTER, NETWORK, MEMBER>(val uuid: UUID = UUID.randomUUID())
    where TRANSMITTER : AbstractTransmitter<TRANSMITTER, NETWORK, MEMBER>,
          NETWORK : TransmitterNetwork<TRANSMITTER, NETWORK, MEMBER>
{
    constructor(networks: Collection<NETWORK>, uuid: UUID = UUID.randomUUID()) : this(uuid) {
        createFromMergingNetworks(networks)
    }

    protected abstract fun self(): NETWORK

    var level: Level? = null
        private set

    protected val transmitters = mutableSetOf<TRANSMITTER>()
    protected val changedTransmitters = Object2ByteOpenHashMap<TRANSMITTER>() // Transmitter -> DirectionMask
    protected val networkMembers: HashBasedTable<BlockPos, Direction, MEMBER> = HashBasedTable.create(10, 6)

    abstract fun tick()

    private fun createFromMergingNetworks(networks: Collection<NETWORK>) {
        for (network in networks) {
            takeoverNetworkMembers(network)
            network.remove()
        }
        register()
    }

    private fun takeoverNetworkMembers(from: NETWORK) {
        for (otherTransmitter in from.transmitters) {
            transmitters += otherTransmitter
            otherTransmitter.setNetwork(self())
        }
        newTransmitters.addAll(from.newTransmitters)
        networkMembers.putAll(from.networkMembers)
        for ((transmitter, mask) in from.changedTransmitters) {
            changedTransmitters.mergeByte(transmitter, mask, Byte::or)
        }
    }

    abstract fun compatibleWith(other: NETWORK): Boolean

    private val newTransmitters = mutableSetOf<TRANSMITTER>()

    @Suppress("UNCHECKED_CAST")
    @JvmName("addTransmittersUnsafe")
    fun addTransmitters(transmitters: Collection<AbstractTransmitter<*, *, *>>) {
        addTransmitters(transmitters as Collection<TRANSMITTER>)
    }

    fun addTransmitters(transmitters: Collection<TRANSMITTER>) {
        newTransmitters.addAll(transmitters)
        if (level == null && transmitters.isNotEmpty())
            level = transmitters.first().level
    }

    fun handleChanges() {
        if (newTransmitters.isNotEmpty()) {
            for (transmitter in newTransmitters) {
                if (transmitter.valid) {
                    for (side in Direction.values()) {
                        handleChangedTransmitterOnSide(transmitter, side)
                    }
                    transmitter.setNetwork(self())
                    transmitters += transmitter
                }
            }
            newTransmitters.clear()
        }

        if (!changedTransmitters.isEmpty()) {
            for ((changed, byte) in changedTransmitters) {
                val directions = DirectionMask(byte)
                if (changed.valid) for (side in directions)
                    handleChangedTransmitterOnSide(changed, side)
            }
            changedTransmitters.clear()
        }
    }

    fun handleChangedTransmitterOnSide(transmitter: TRANSMITTER, side: Direction) {
        val cachedMember = transmitter.getCachedMember(side)
        val memberPos = transmitter.pos.relative(side)
        if (cachedMember != null)
            networkMembers.put(memberPos, side.opposite, cachedMember)
        else
            networkMembers.remove(memberPos, side.opposite)
    }

    fun transmitterChanged(transmitter: TRANSMITTER, side: Direction) {
        changedTransmitters.mergeByte(transmitter, DirectionMask.getBitForDirection(side), Byte::or)
        TransmitterNetworks.markNetworkDirty(this)
    }

    fun invalidate() {
        transmitters.removeIf { !it.valid } // remove any already invalid transmitters
        for (transmitter in transmitters) if (transmitter.valid) {
            transmitter.setNetwork(null)
            TransmitterNetworks.addOrphan(transmitter)
        }
        remove()
    }

    fun register() {
        TransmitterNetworks.addNetwork(this)
    }

    fun remove() {
        transmitters.clear()
        newTransmitters.clear()
        networkMembers.clear()
        changedTransmitters.clear()
        TransmitterNetworks.removeNetwork(this)
    }
}
