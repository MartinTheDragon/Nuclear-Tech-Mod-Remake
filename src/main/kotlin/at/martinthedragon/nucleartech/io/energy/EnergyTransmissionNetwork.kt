package at.martinthedragon.nucleartech.io.energy

import at.martinthedragon.nucleartech.math.DirectionMask
import com.google.common.collect.HashBasedTable
import it.unimi.dsi.fastutil.objects.Object2ByteOpenHashMap
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraftforge.energy.IEnergyStorage
import java.util.*
import kotlin.experimental.or

class EnergyTransmissionNetwork(val uuid: UUID = UUID.randomUUID()) : IEnergyStorage {
    constructor(networks: Collection<EnergyTransmissionNetwork>, uuid: UUID = UUID.randomUUID()) : this(uuid) {
        createFromMergingNetworks(networks)
    }

    var level: Level? = null
        private set

    private val cables = mutableSetOf<Cable>()
    private val users = HashBasedTable.create<BlockPos, Direction, IEnergyStorage>(10, 6)
    private val changedUsers = Object2ByteOpenHashMap<Cable>() // Cable -> DirectionMask

    fun tick() {
        val required = receiveEnergy(Int.MAX_VALUE, true)
        receiveEnergy(extractEnergy(required, false), false)
    }

    private fun createFromMergingNetworks(networks: Collection<EnergyTransmissionNetwork>) {
        for (network in networks) {
            takeoverNetworkMembers(network)
            network.remove()
        }
        register()
    }

    private fun takeoverNetworkMembers(from: EnergyTransmissionNetwork) {
        for (otherCable in from.cables) {
            cables += otherCable
            otherCable.setNetwork(this)
        }
        newCables.addAll(from.newCables)
        users.putAll(from.users)
        for ((cable, mask) in from.changedUsers) {
            changedUsers.mergeByte(cable, mask, Byte::or)
        }
    }

    private val newCables = mutableSetOf<Cable>()

    fun addCables(cables: Collection<Cable>) {
        newCables.addAll(cables)
        if (level == null && cables.isNotEmpty())
            level = cables.first().level
    }

    fun handleChanges() {
        if (newCables.isNotEmpty()) {
            for (cable in newCables) {
                if (cable.valid) {
                    for (side in Direction.values()) {
                        handleChangedCableOnSide(cable, side)
                    }
                    cable.setNetwork(this)
                    cables += cable
                }
            }
            newCables.clear()
        }

        if (!changedUsers.isEmpty()) {
            for ((changed, byte) in changedUsers) {
                val directions = DirectionMask(byte)
                if (changed.valid) for (side in directions)
                    handleChangedCableOnSide(changed, side)
            }
            changedUsers.clear()
        }
    }

    fun handleChangedCableOnSide(cable: Cable, side: Direction) {
        val cachedUser = cable.getCachedUser(side)
        val userPos = cable.pos.relative(side)
        if (cachedUser != null)
            users.put(userPos, side.opposite, cachedUser)
        else
            users.remove(userPos, side.opposite)
    }

    fun userChanged(cable: Cable, side: Direction) {
        changedUsers.mergeByte(cable, DirectionMask.getBitForDirection(side), Byte::or)
        EnergyNetworks.markNetworkDirty(this)
    }

    fun invalidate() {
        cables.removeIf { !it.valid } // remove any already invalid cables
        for (cable in cables) if (cable.valid) {
            cable.setNetwork(null)
            EnergyNetworks.addOrphan(cable)
        }
        remove()
    }

    fun register() {
        EnergyNetworks.addNetwork(this)
    }

    fun remove() {
        cables.clear()
        newCables.clear()
        users.clear()
        changedUsers.clear()
        EnergyNetworks.removeNetwork(this)
    }

    // TODO better distribution: doesn't keep track of last receiver and therefore leaves a few out
    override fun receiveEnergy(maxReceive: Int, simulate: Boolean): Int {
        if (maxReceive == 0) return 0

        val targets = users.values().filter { it.receiveEnergy(maxReceive, true) > 0 }.toMutableList()

        if (simulate) {
            val targetMap = Object2IntOpenHashMap(targets.toTypedArray(), IntArray(targets.size))
            while (targets.isNotEmpty() && targetMap.values.sum() < maxReceive) {
                val remaining = maxReceive - targetMap.values.sum()
                val iterator = targets.iterator()
                while (iterator.hasNext()) {
                    val target = iterator.next()
                    val received = target.receiveEnergy(remaining / targets.size, true)
                    val expectedTotal = targetMap.getInt(target) + received
                    val accumulatedReceived = target.receiveEnergy(expectedTotal, true)
                    if (accumulatedReceived <= expectedTotal) {
                        targetMap[target] = accumulatedReceived
                        iterator.remove()
                        continue
                    }
                    targetMap.addTo(target, received)
                }
            }
            return targetMap.values.sum()
        } else {
            var received = 0
            while (targets.isNotEmpty() && received < maxReceive) {
                val remaining = maxReceive - received
                val iterator  = targets.iterator()
                while (iterator.hasNext()) {
                    val target = iterator.next()
                    val previousReceived = received
                    received += target.receiveEnergy(remaining / targets.size, false)
                    if (previousReceived == received) iterator.remove()
                }
            }
            return received
        }
    }

    override fun extractEnergy(maxExtract: Int, simulate: Boolean): Int {
        if (maxExtract == 0 || energyStored <= 0) return 0

        val sources = users.values().filter { it.extractEnergy(maxExtract, true) > 0 }.toMutableList()
        var extracted = 0
        do {
            val lastExtracted = extracted
            val iterator = sources.iterator()
            while (iterator.hasNext()) {
                val source = iterator.next()
                extracted += source.extractEnergy((maxExtract - extracted) / sources.size, simulate)
                if (source.energyStored <= 0) iterator.remove()
            }
            if (lastExtracted == extracted) break
        } while (sources.isNotEmpty() && extracted < maxExtract)
        return extracted
    }

    override fun getEnergyStored() = users.values().fold(0) { acc, iEnergyStorage -> acc + iEnergyStorage.energyStored }
    override fun getMaxEnergyStored() = users.values().fold(0) { acc, iEnergyStorage -> acc + iEnergyStorage.maxEnergyStored }

    override fun canExtract() = true
    override fun canReceive() = true
}
