package at.martinthedragon.nucleartech.io.energy

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.block.entity.CableBlockEntity
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
object EnergyNetworks {
    private val networks = mutableSetOf<EnergyTransmissionNetwork>()
    private val dirtyNetworks = mutableSetOf<EnergyTransmissionNetwork>()

    @SubscribeEvent @JvmStatic
    fun tickServer(event: ServerTickEvent) {
        if (event.phase == TickEvent.Phase.END) {
            handleInvalidCables()
            handleOrphans()
            handleDirtyNetworks()
            for (network in networks) network.tick()
        }
    }

    @SubscribeEvent @JvmStatic
    fun onServerStopped(event: ServerStoppedEvent) {
        networks.clear()
        dirtyNetworks.clear()
        invalidCables.clear()
        orphans.clear()
    }

    fun addNetwork(network: EnergyTransmissionNetwork) {
        networks += network
    }

    fun removeNetwork(network: EnergyTransmissionNetwork) {
        networks -= network
        dirtyNetworks -= network
    }

    fun markNetworkDirty(network: EnergyTransmissionNetwork) {
        dirtyNetworks += network
    }

    private val invalidCables = mutableSetOf<Cable>()

    fun invalidateCable(cable: Cable) {
        invalidCables += cable
    }

    private fun handleInvalidCables() {
        if (invalidCables.isEmpty()) return
        val copy = invalidCables.toSet()
        invalidCables.clear()
        for (cable in copy) {
            if (cable.orphan && cable.valid) continue
            val network = cable.network ?: continue
            network.invalidate()
            cable.setNetwork(null)
        }
    }

    private val orphans = mutableMapOf<GlobalPos, Cable>()

    fun addOrphan(orphan: Cable) {
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

    private fun combineOrphansToNetwork(start: Cable, orphans: Map<GlobalPos, Cable>): EnergyTransmissionNetwork {
        val level = start.level
        val queue = LinkedList<BlockPos>() // non-recursive solution
        val done = mutableSetOf<BlockPos>()
        val connectedCables = mutableSetOf<Cable>()
        val otherNetworks = mutableSetOf<EnergyTransmissionNetwork>()
        queue.push(start.pos)
        // find all connected orphan cables and networks
        while (queue.peek() != null) {
            val next = queue.removeFirst()
            if (!done.add(next)) continue
            val orphan = orphans[GlobalPos.of(level.dimension(), next)]
            if (orphan != null && orphan.valid && orphan.orphan) {
                connectedCables += orphan
                orphan.orphan = false
                for (side in Direction.values()) {
                    val relativePos = next.relative(side)
                    if (relativePos in done) continue
                    val blockEntity = level.getBlockEntity(relativePos) // TODO so many chunk lookups are expensive, cache?
                    if (blockEntity is CableBlockEntity) queue.addLast(relativePos)
                }
            } else {
                val blockEntity = level.getBlockEntity(next)
                if (blockEntity is CableBlockEntity) {
                    val network = blockEntity.cable.network
                    if (network != null) otherNetworks += network
                }
            }
        }
        val newNetwork = if (otherNetworks.size == 1) otherNetworks.first() else EnergyTransmissionNetwork(otherNetworks)
        newNetwork.addCables(connectedCables)
        return newNetwork
    }

    private fun handleDirtyNetworks() {
        if (dirtyNetworks.isEmpty()) return
        val copy = dirtyNetworks.toSet()
        dirtyNetworks.clear()
        for (network in copy) network.handleChanges()
    }
}
