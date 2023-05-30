package at.martinthedragon.nucleartech.coremodules.forge.energy

interface IEnergyStorage {
    fun receiveEnergy(maxReceive: Int, simulate: Boolean): Int
    fun extractEnergy(maxExtract: Int, simulate: Boolean): Int
    fun getEnergyStored(): Int
    fun getMaxEnergyStored(): Int
    fun canExtract(): Boolean
    fun canReceive(): Boolean
}
