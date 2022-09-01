package at.martinthedragon.nucleartech.io.energy

import at.martinthedragon.nucleartech.block.entity.CableBlockEntity
import at.martinthedragon.nucleartech.extensions.getOrNull
import at.martinthedragon.nucleartech.math.DirectionMask
import net.minecraft.core.Direction
import net.minecraft.core.GlobalPos
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.energy.IEnergyStorage
import java.util.*

class Cable(val blockEntity: CableBlockEntity) {
    var orphan = true
    val valid get() = !blockEntity.isRemoved && blockEntity.loaded

    val level get() = blockEntity.level!!
    val pos get() = blockEntity.blockPos
    val globalPos get() = GlobalPos.of(level.dimension(), pos)
    val isClientSide get() = level.isClientSide

    var currentCableConnections = DirectionMask.NULL
        private set
    var currentConsumerConnections = DirectionMask.NULL
        private set

    val allConnections get() = currentCableConnections + currentConsumerConnections

    var network: EnergyTransmissionNetwork? = null
        private set

    private var users = EnumMap<_, IEnergyStorage>(Direction::class.java)

    fun refresh() {
        if (isClientSide) return
        val otherCables = getCableNeighbours()
        val consumers = getConsumers()
        for (side in consumers) getConsumer(side) // to update the user cache
        val changed = otherCables + consumers != allConnections
        var newCables = DirectionMask.NULL
        if (changed && otherCables != currentCableConnections)
            newCables = otherCables - currentCableConnections
        currentCableConnections = otherCables
        currentConsumerConnections = consumers
        if (newCables != DirectionMask.NULL)
            updateConnections(newCables)
        if (changed) connectionsChanged()
    }

    fun refresh(side: Direction) {
        if (isClientSide) return
        val hasCableNeighbor = hasCableNeighbour(side)
        val hasConsumer = hasConsumer(side)
        if (hasConsumer) getConsumer(side) // to update user cache
        var changed = false
        if ((hasCableNeighbor || hasConsumer) != side in allConnections) {
            updateConnections(DirectionMask(side))
            changed = true
        }
        currentCableConnections = currentCableConnections.set(side, hasCableNeighbor)
        currentConsumerConnections = currentConsumerConnections.set(side, hasConsumer)
        if (changed) connectionsChanged()
    }

    private fun getCableNeighbours(): DirectionMask {
        var sides = DirectionMask.NULL
        for (side in Direction.values()) {
            val otherCable = level.getBlockEntity(pos.relative(side)) ?: continue
            if (otherCable is CableBlockEntity) sides += side
        }
        return sides
    }

    fun hasCableNeighbour(side: Direction) = level.getBlockEntity(pos.relative(side)) is CableBlockEntity

    fun getConsumers(): DirectionMask {
        var sides = DirectionMask.NULL
        for (side in Direction.values()) {
            val consumerPos = pos.relative(side)
            if (!isClientSide && !level.isLoaded(consumerPos)) {
                blockEntity.refresh() // retry until it's loaded at some point
                continue
            }
            val consumer = level.getBlockEntity(consumerPos) ?: continue
            if (consumer is CableBlockEntity || !consumer.getCapability(CapabilityEnergy.ENERGY).isPresent) continue
            sides += side
        }
        return sides
    }

    fun hasConsumer(side: Direction): Boolean {
        val blockEntity = level.getBlockEntity(pos.relative(side))
        val isPresent = blockEntity != null && blockEntity !is CableBlockEntity && blockEntity.getCapability(CapabilityEnergy.ENERGY, side.opposite).isPresent
        if (!isPresent) userChanged(side, null)
        return isPresent
    }

    fun getConsumer(side: Direction): IEnergyStorage? {
        val blockEntity = level.getBlockEntity(pos.relative(side))
        return blockEntity?.getCapability(CapabilityEnergy.ENERGY, side.opposite)?.getOrNull().also { userChanged(side, it) }
    }

    fun updateConnections(sides: DirectionMask) {
        if (network == null) for (side in sides) {
            val blockEntity = level.getBlockEntity(pos.relative(side))
            if (blockEntity is CableBlockEntity)
                blockEntity.cable.refresh(side.opposite)
        }
    }

    fun connectionsChanged() {
        level.setBlockAndUpdate(pos, level.getBlockState(pos)
            .setValue(BlockStateProperties.UP, allConnections.up)
            .setValue(BlockStateProperties.DOWN, allConnections.down)
            .setValue(BlockStateProperties.NORTH, allConnections.north)
            .setValue(BlockStateProperties.EAST, allConnections.east)
            .setValue(BlockStateProperties.SOUTH, allConnections.south)
            .setValue(BlockStateProperties.WEST, allConnections.west)
        )
    }

    private fun userChanged(side: Direction, newUser: IEnergyStorage?) {
        if (users.put(side, newUser) !== newUser) {
            network?.userChanged(this, side)
        }
    }

    fun getCachedUser(side: Direction) = users[side]

    fun remove() {
        users.clear()
    }

    fun setNetwork(network: EnergyTransmissionNetwork?) {
        if (this.network == network) return
        this.network = network
        orphan = network == null
        blockEntity.capability.invalidate()
        blockEntity.capability = blockEntity.createCapability()
    }
}
