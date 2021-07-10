package at.martinthedragon.nucleartech.energy

import net.minecraftforge.energy.EnergyStorage

class EnergyStorageExposed(
    capacity: Int,
    maxReceive: Int,
    maxExtract: Int,
    energy: Int
) : EnergyStorage(capacity, maxReceive, maxExtract, energy) {
    constructor(capacity: Int) : this(capacity, capacity, capacity, 0)
    constructor(capacity: Int, maxTransfer: Int) : this(capacity, maxTransfer, maxTransfer, 0)
    constructor(capacity: Int, maxReceive: Int, maxExtract: Int) : this(capacity, maxReceive, maxExtract, 0)

    var energy: Int
        get() = super.energy
        set(value) {
            super.energy = value
        }
}
