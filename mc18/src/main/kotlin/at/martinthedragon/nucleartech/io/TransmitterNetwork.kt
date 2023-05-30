package at.martinthedragon.nucleartech.io

import at.martinthedragon.nucleartech.math.DirectionMask
import com.google.common.collect.HashBasedTable
import it.unimi.dsi.fastutil.ints.IntIntPair
import it.unimi.dsi.fastutil.objects.Object2ByteOpenHashMap
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import java.util.*
import kotlin.experimental.or
import kotlin.math.max

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

    protected abstract val networkEmitter: NetworkEmissionTicker

    open fun tick() {
        networkEmitter.tick()
    }

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
        onMemberChanged(memberPos, side.opposite, cachedMember)
    }

    protected open fun onMemberChanged(pos: BlockPos, side: Direction, changedMember: MEMBER?) {
        networkEmitter.membersChanged()
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
        networkEmitter.finish()
        transmitters.clear()
        newTransmitters.clear()
        networkMembers.clear()
        changedTransmitters.clear()
        TransmitterNetworks.removeNetwork(this)
    }

    abstract inner class NetworkEmissionTicker {
        abstract fun isEmitter(member: MEMBER): Boolean
        abstract fun isReceiver(member: MEMBER): Boolean

        abstract fun getPossibleAmountToDistribute(emitters: List<MEMBER>, receivers: List<MEMBER>): Int

        /**
         * Transfers [amount] of stuff from the [emitter] to the [receiver], while extracting [additional] stuff from the [emitter], and giving [extra] stuff to the [receiver].
         *
         * @return The amount of stuff the [receiver] couldn't take from the [emitter] minus accepted [extra] on the left side, and the amount of stuff the [emitter] couldn't provide minus transferred [additional] on the right side.
         */
        abstract fun transfer(emitter: MEMBER?, receiver: MEMBER, amount: Int, additional: Int, extra: Int, simulate: Boolean): IntIntPair

        private val emitters = mutableListOf<MEMBER>()
        private val receivers = mutableListOf<MEMBER>()

        private var nextEmitter = 0
        private var nextReceiver = 0

        private var lastEmitterRemaining = 0
        private var lastReceiverRemaining = 0

        fun tick() {
            val freshEmitters = networkMembers.values().filter(this::isEmitter)
            val freshReceivers = networkMembers.values().filter(this::isReceiver)
            if (!emitters.containsAll(freshEmitters) || !freshEmitters.containsAll(emitters) || !receivers.containsAll(freshReceivers) || !freshReceivers.containsAll(receivers)) {
                reset()
                emitters.addAll(freshEmitters)
                receivers.addAll(freshReceivers)
            }

            if (lastEmitterRemaining <= 0 && (emitters.isEmpty() || receivers.isEmpty()) || receivers.isEmpty()) return

            val emitterCount = emitters.size
            val receiverCount = receivers.size

            val distributions = max(emitterCount, receiverCount)

            val possibleAmountToDistribute = getPossibleAmountToDistribute(emitters, receivers)
            val transferEach = possibleAmountToDistribute / distributions

            var additionalRuns = 0
            var i = 0

            while (i < distributions + additionalRuns) {
                i++
                val emitter = emitters.getOrNull(nextEmitter)
                val receiver = receivers[nextReceiver]
                val toTransfer = if (lastReceiverRemaining > 0) 0 else transferEach
                val overload = transfer(emitter, receiver, toTransfer, 0, 0, true).let { max(it.leftInt(), it.rightInt()).coerceAtLeast(0) }
                val remaining = transfer(emitter, receiver, toTransfer - overload, lastReceiverRemaining, lastEmitterRemaining, false)
                lastEmitterRemaining += remaining.leftInt()
                lastReceiverRemaining += remaining.rightInt()
                if (lastReceiverRemaining <= 0) nextReceiver++ else additionalRuns++
                nextEmitter++
                if (nextEmitter >= emitterCount) nextEmitter = 0
                if (nextReceiver >= receiverCount) nextReceiver = 0

                if (additionalRuns > 1000) {
                    TransmitterNetworks.LOGGER.warn("Something isn't distributing correctly in network with UUID '$uuid'!")
                    lastReceiverRemaining = 0
                    break
                }
            }
        }

        fun finish() {
            if (lastEmitterRemaining > 0 && receivers.isNotEmpty()) for (receiver in receivers) {
                lastEmitterRemaining += transfer(null, receiver, 0, 0, lastEmitterRemaining, false).leftInt()
            }
        }

        fun reset() {
            emitters.clear()
            receivers.clear()
            nextEmitter = 0
            nextReceiver = 0
        }

        fun membersChanged() {
            reset()
            for (member in networkMembers.values()) {
                if (isEmitter(member)) emitters += member
                if (isReceiver(member)) receivers += member
            }
        }
    }
}
