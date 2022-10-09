package at.martinthedragon.nucleartech.io

import at.martinthedragon.nucleartech.block.entity.transmitters.AbstractTransmitterBlockEntity
import at.martinthedragon.nucleartech.math.DirectionMask
import net.minecraft.core.Direction
import net.minecraft.core.GlobalPos
import net.minecraft.world.level.block.entity.BlockEntity
import java.util.*

abstract class AbstractTransmitter<TRANSMITTER, NETWORK, MEMBER>(val blockEntity: AbstractTransmitterBlockEntity)
    where TRANSMITTER : AbstractTransmitter<TRANSMITTER, NETWORK, MEMBER>,
          NETWORK : TransmitterNetwork<TRANSMITTER, NETWORK, MEMBER>
{
    var orphan = true
    val valid get() = !blockEntity.isRemoved && blockEntity.loaded

    val level get() = blockEntity.level!!
    val pos get() = blockEntity.blockPos
    val globalPos get() = GlobalPos.of(level.dimension(), pos)
    val isClientSide get() = level.isClientSide

    var currentTransmitterConnections = DirectionMask.NULL
        protected set
    var currentMemberConnections = DirectionMask.NULL
        protected set

    val allConnections get() = currentTransmitterConnections + currentMemberConnections

    var network: NETWORK? = null
        private set

    private var members = EnumMap<_, MEMBER>(Direction::class.java)

    fun refresh() {
        if (isClientSide) return
        val otherTransmitters = getTransmitterNeighbours()
        val memberSides = getMemberSides()
        for (side in memberSides) getMember(side) // to update the user cache
        val changed = otherTransmitters + memberSides != allConnections
        var newTransmitters = DirectionMask.NULL
        if (changed && otherTransmitters != currentTransmitterConnections)
            newTransmitters = otherTransmitters - currentTransmitterConnections
        currentTransmitterConnections = otherTransmitters
        currentMemberConnections = memberSides
        if (newTransmitters != DirectionMask.NULL)
            updateConnections(newTransmitters)
        if (changed) connectionsChanged()
    }

    fun refresh(side: Direction) {
        if (isClientSide) return
        val hasTransmitterNeighbour = hasTransmitterNeighbour(side)
        val hasMember = hasMember(side)
        if (hasMember) getMember(side) // to update user cache
        var changed = false
        if ((hasTransmitterNeighbour || hasMember) != side in allConnections) {
            updateConnections(DirectionMask(side))
            changed = true
        }
        currentTransmitterConnections = currentTransmitterConnections.set(side, hasTransmitterNeighbour)
        currentMemberConnections = currentMemberConnections.set(side, hasMember)
        if (changed) connectionsChanged()
    }

    private fun getTransmitterNeighbours(): DirectionMask {
        var sides = DirectionMask.NULL
        for (side in Direction.values()) {
            if (hasTransmitterNeighbour(side))
                sides += side
        }
        return sides
    }

    fun hasTransmitterNeighbour(side: Direction) = level.getBlockEntity(pos.relative(side))?.let { isValidTransmitterNeighbour(it, side) } == true

    fun getMemberSides(): DirectionMask {
        var sides = DirectionMask.NULL
        for (side in Direction.values()) {
            val memberPos = pos.relative(side)
            if (!isClientSide && !level.isLoaded(memberPos)) {
                blockEntity.refresh() // retry until it's loaded at some point
                continue
            }
            val member = level.getBlockEntity(memberPos) ?: continue
            if (isNeighboringTransmitter(member, side) || !isValidNetworkMember(member, side)) continue
            sides += side
        }
        return sides
    }

    private fun hasMember(side: Direction): Boolean {
        val blockEntity = level.getBlockEntity(pos.relative(side))
        val isPresent = blockEntity != null && !isNeighboringTransmitter(blockEntity, side) && isValidNetworkMember(blockEntity, side)
        if (!isPresent) memberChanged(side, null)
        return isPresent
    }

    fun getMember(side: Direction): MEMBER? {
        val blockEntity = level.getBlockEntity(pos.relative(side)) ?: return null
        return getMember(blockEntity, side).also { memberChanged(side, it) }
    }

    protected abstract fun self(): TRANSMITTER
    abstract fun isNeighboringTransmitter(blockEntity: BlockEntity, side: Direction?): Boolean
    abstract fun isValidTransmitterNeighbour(blockEntity: BlockEntity, side: Direction?): Boolean
    abstract fun isValidNetworkMember(blockEntity: BlockEntity, side: Direction): Boolean
    protected abstract fun getMember(blockEntity: BlockEntity, side: Direction): MEMBER?
    abstract fun createNetworkByMerging(networks: Collection<NETWORK>): NETWORK
    abstract fun compatibleWith(network: NETWORK): Boolean

    @JvmName("createNetworkByMergingUnsafe")
    @Suppress("UNCHECKED_CAST")
    fun createNetworkByMerging(networks: Collection<TransmitterNetwork<*, *, *>>) = createNetworkByMerging(networks as Collection<NETWORK>)

    fun updateConnections(sides: DirectionMask) {
        if (network == null) for (side in sides) {
            val blockEntity = level.getBlockEntity(pos.relative(side))
            if (blockEntity is AbstractTransmitterBlockEntity && isValidTransmitterNeighbour(blockEntity, side))
                blockEntity.transmitter.refresh(side.opposite)
        }
    }

    open fun connectionsChanged() {}

    private fun memberChanged(side: Direction, newMember: MEMBER?) {
        if (members.put(side, newMember) !== newMember) {
            network?.transmitterChanged(self(), side)
        }
    }

    fun getCachedMember(side: Direction) = members[side]

    fun remove() {
        members.clear()
    }

    fun setNetwork(network: NETWORK?) {
        if (this.network == network) return
        val oldNetwork = this.network
        this.network = network
        orphan = network == null
        onNetworkChanged(oldNetwork, network)
    }

    open fun onNetworkChanged(oldNetwork: NETWORK?, newNetwork: NETWORK?) {}
}
