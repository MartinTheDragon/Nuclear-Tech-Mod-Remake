package at.martinthedragon.nucleartech.io.energy

import at.martinthedragon.nucleartech.io.TransmitterNetwork
import it.unimi.dsi.fastutil.ints.IntIntPair
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.minecraftforge.energy.IEnergyStorage
import java.util.*

class EnergyNetwork : TransmitterNetwork<Cable, EnergyNetwork, IEnergyStorage>, IEnergyStorage {
    constructor() : super()
    constructor(networks: Collection<EnergyNetwork>, uuid: UUID = UUID.randomUUID()) : super(networks, uuid)

    override fun self() = this
    override fun compatibleWith(other: EnergyNetwork) = true

    override val networkEmitter = EnergyEmissionTicker()

    // TODO better distribution: doesn't keep track of last receiver and therefore leaves a few out
    override fun receiveEnergy(maxReceive: Int, simulate: Boolean): Int {
        if (maxReceive == 0) return 0

        val targets = networkMembers.values().filter { it.receiveEnergy(maxReceive, true) > 0 }.toMutableList()

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

        val sources = networkMembers.values().filter { it.extractEnergy(maxExtract, true) > 0 }.toMutableList()
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

    override fun getEnergyStored() = networkMembers.values().fold(0) { acc, iEnergyStorage -> acc + iEnergyStorage.energyStored }
    override fun getMaxEnergyStored() = networkMembers.values().fold(0) { acc, iEnergyStorage -> acc + iEnergyStorage.maxEnergyStored }

    override fun canExtract() = true
    override fun canReceive() = true

    inner class EnergyEmissionTicker : NetworkEmissionTicker() {
        override fun isEmitter(member: IEnergyStorage) = member.extractEnergy(Int.MAX_VALUE, true) > 0
        override fun isReceiver(member: IEnergyStorage) = member.receiveEnergy(Int.MAX_VALUE, true) > 0

        override fun getPossibleAmountToDistribute(emitters: List<IEnergyStorage>, receivers: List<IEnergyStorage>): Int {
            val maxEmission = emitters.fold(0) { acc, emitter -> acc + emitter.extractEnergy(Int.MAX_VALUE, true) }.let { if (it < 0) Int.MAX_VALUE else it }
            val maxConsumption = receivers.fold(0) { acc, receiver -> acc + receiver.receiveEnergy(Int.MAX_VALUE, true) }.let { if (it < 0) Int.MAX_VALUE else it }
            return maxEmission.coerceAtMost(maxConsumption)
        }

        override fun transfer(emitter: IEnergyStorage?, receiver: IEnergyStorage, amount: Int, additional: Int, extra: Int): IntIntPair {
            val emitted = emitter?.extractEnergy(amount + additional, false) ?: 0
            val received = receiver.receiveEnergy(emitted + extra, false)
            return IntIntPair.of(emitted - received, amount - emitted)
        }
    }
}
