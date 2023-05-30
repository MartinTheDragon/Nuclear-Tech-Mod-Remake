package at.martinthedragon.nucleartech.energy

import net.minecraftforge.energy.IEnergyStorage
import kotlin.math.min

open class LudicrousEnergyStorage(
    val capacity: Long,
    val maxReceive: Int,
    val maxExtract: Int = maxReceive,
    private var energy: Long = 0L
) : IEnergyStorage {
    override fun receiveEnergy(maxReceive: Int, simulate: Boolean): Int {
        if (!canReceive()) return 0
        val energyReceived = min(getActualMaxEnergyStored() - getActualEnergyStored(), min(this.maxReceive, maxReceive).toLong()).toInt()
        if (!simulate) energy += energyReceived
        return energyReceived
    }

    override fun extractEnergy(maxExtract: Int, simulate: Boolean): Int {
        if (!canExtract()) return 0
        val energyExtracted = min(energyStored, min(this.maxExtract, maxExtract))
        if (!simulate) energy -= energyExtracted
        return energyExtracted
    }

    override fun getEnergyStored() = if (energy > Int.MAX_VALUE) Int.MAX_VALUE else energy.toInt()

    fun getActualEnergyStored(): Long = energy

    override fun getMaxEnergyStored(): Int = if (capacity > Int.MAX_VALUE) Int.MAX_VALUE else capacity.toInt()

    fun getActualMaxEnergyStored(): Long = capacity

    override fun canExtract() = maxExtract > 0

    override fun canReceive() = maxReceive > 0
}
