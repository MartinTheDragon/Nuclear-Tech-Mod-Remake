package at.martinthedragon.nucleartech.io

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.block.entity.transmitters.AbstractTransmitterBlockEntity
import com.mojang.logging.LogUtils
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.GlobalPos
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.TickEvent.ServerTickEvent
import net.minecraftforge.event.server.ServerStoppedEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import java.util.*

@EventBusSubscriber(modid = NuclearTech.MODID, bus = EventBusSubscriber.Bus.FORGE)
object TransmitterNetworks {
    private val LOGGER = LogUtils.getLogger()

    private val networks = mutableSetOf<TransmitterNetwork<*, *, *>>()
    private val dirtyNetworks = mutableSetOf<TransmitterNetwork<*, *, *>>()

    @SubscribeEvent @JvmStatic
    fun tickServer(event: ServerTickEvent) {
        if (event.phase == TickEvent.Phase.END) {
            handleInvalidTransmitters()
            handleOrphans()
            handleDirtyNetworks()
            for (network in networks) network.tick()
        }
    }

    @SubscribeEvent @JvmStatic
    fun onServerStopped(event: ServerStoppedEvent) {
        networks.clear()
        dirtyNetworks.clear()
        invalidTransmitters.clear()
        orphans.clear()
    }

    fun addNetwork(network: TransmitterNetwork<*, *, *>) {
        networks += network
    }

    fun removeNetwork(network: TransmitterNetwork<*, *, *>) {
        networks -= network
        dirtyNetworks -= network
    }

    fun markNetworkDirty(network: TransmitterNetwork<*, *, *>) {
        dirtyNetworks += network
    }

    private val invalidTransmitters = mutableSetOf<AbstractTransmitter<*, *, *>>()

    fun invalidateTransmitter(transmitter: AbstractTransmitter<*, *, *>) {
        invalidTransmitters += transmitter
    }

    private fun handleInvalidTransmitters() {
        if (invalidTransmitters.isEmpty()) return
        val copy = invalidTransmitters.toSet()
        invalidTransmitters.clear()
        for (transmitter in copy) {
            if (transmitter.orphan && transmitter.valid) continue
            val network = transmitter.network ?: continue
            network.invalidate()
            transmitter.setNetwork(null)
        }
    }

    private val orphans = mutableMapOf<GlobalPos, AbstractTransmitter<*, *, *>>()

    fun addOrphan(orphan: AbstractTransmitter<*, *, *>) {
        orphans[orphan.globalPos] = orphan
    }

    private fun handleOrphans() {
        if (orphans.isEmpty()) return
        val copy = orphans.toMap()
        orphans.clear()
        for (orphan in copy.values) if (orphan.valid && orphan.orphan) {
            markNetworkDirty(combineOrphansToNetwork(orphan, copy))
        }
    }

    private fun combineOrphansToNetwork(start: AbstractTransmitter<*, *, *>, orphans: Map<GlobalPos, AbstractTransmitter<*, *, *>>): TransmitterNetwork<*, *, *> {
        val level = start.level
        val queue = LinkedList<BlockPos>()
        val done = mutableSetOf<BlockPos>()
        val connectedTransmitters = mutableSetOf<AbstractTransmitter<*, *, *>>()
        val otherNetworks = mutableSetOf<TransmitterNetwork<*, *, *>>()
        queue.push(start.pos)
        // find all connected orphan transmitters and networks
        while (queue.peek() != null) {
            val next = queue.removeFirst()
            if (!done.add(next)) continue // check if we already got to that transmitter
            val orphan = orphans[GlobalPos.of(level.dimension(), next)]
            if (orphan != null && orphan.valid && orphan.orphan) { // check if the next transmitter is an orphan
                connectedTransmitters += orphan
                orphan.orphan = false
                for (side in Direction.values()) { // discover more transmitters
                    val relativePos = next.relative(side)
                    if (relativePos in done) continue
                    val blockEntity = level.getBlockEntity(relativePos) // TODO so many chunk lookups are expensive, cache?
                    if (blockEntity != null && start.isValidTransmitterNeighbour(blockEntity, null)) queue.addLast(relativePos)
                }
            } else { // if the transmitter isn't an orphan, we discovered another network (or nothing at all)
                val blockEntity = level.getBlockEntity(next)
                if (blockEntity as? AbstractTransmitterBlockEntity != null && start.isValidTransmitterNeighbour(blockEntity, null)) { // check if joining the networks is possible
                    val network = blockEntity.transmitter.network
                    if (network != null) otherNetworks += network
                }
            }
        }
        val newNetwork = if (otherNetworks.size == 1) otherNetworks.first() else start.createNetworkByMerging(otherNetworks)
        newNetwork.addTransmitters(connectedTransmitters)

        if (otherNetworks.size == 1) {
            LOGGER.debug("Adding ${connectedTransmitters.size} transmitter(s) to existing network with UUID '${newNetwork.uuid}'")
        } else if (otherNetworks.isEmpty()) {
            LOGGER.debug("Creating new network with UUID '${newNetwork.uuid}' and ${connectedTransmitters.size} transmitter(s)")
        } else {
            LOGGER.debug("Merging ${otherNetworks.size} networks and ${connectedTransmitters.size} transmitter(s) into new network with UUID '${newNetwork.uuid}'")
        }

        return newNetwork
    }

    private fun handleDirtyNetworks() {
        if (dirtyNetworks.isEmpty()) return
        val copy = dirtyNetworks.toSet()
        dirtyNetworks.clear()
        for (network in copy) network.handleChanges()
    }
}
