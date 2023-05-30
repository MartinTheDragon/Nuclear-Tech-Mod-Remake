package at.martinthedragon.nucleartech.energy

import net.minecraft.world.item.ItemStack
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.energy.IEnergyStorage
import kotlin.math.min

/**
 * Tries to transfer the specified [amount] of energy [from] an [IEnergyStorage][IEnergyStorage] [to][to] another and
 * returns the amount transferred.
 *
 * Negative values for [amount] are allowed, but may not always work and are unrecommended.
 */
@JvmOverloads
fun transferEnergy(from: IEnergyStorage, to: IEnergyStorage, amount: Int = Int.MAX_VALUE): Int =
    if (to.energyStored < to.maxEnergyStored)
        to.receiveEnergy(from.energyStored, true).let { maxPossible ->
            return if (maxPossible > 0) {
                to.receiveEnergy(from.extractEnergy(min(amount, maxPossible), false), false)
            } else 0
        }
    else 0

/**
 * Tries to transfer the specified [amount] of energy [from] an ItemStack [to] another and returns the amount
 * transferred. Both ItemStacks need to have the [Energy Capability][CapabilityEnergy.ENERGY].
 *
 * Negative values for [amount] are allowed, but may not always work and are unrecommended.
 */
@JvmOverloads
fun transferEnergy(from: ItemStack, to: ItemStack, amount: Int = Int.MAX_VALUE): Int =
    from.getCapability(CapabilityEnergy.ENERGY).map { fromStorage ->
        to.getCapability(CapabilityEnergy.ENERGY).map { toStorage ->
            transferEnergy(fromStorage, toStorage, amount)
        }.orElse(0)
    }.orElse(0)

/**
 * Tries to transfer the specified [amount] of energy from an [ItemStack][from] to a [to] and returns the
 * amount transferred. The [from] needs to have the [Energy Capability][CapabilityEnergy.ENERGY].
 *
 * Negative values for [amount] are allowed, but may not always work and are unrecommended.
 */
@JvmOverloads
fun transferEnergy(from: ItemStack, to: IEnergyStorage, amount: Int = Int.MAX_VALUE): Int =
    from.getCapability(CapabilityEnergy.ENERGY).map { itemStackEnergy ->
        transferEnergy(itemStackEnergy, to, amount)
    }.orElse(0)

/**
 * Tries to transfer the specified [amount] of energy from a [from] to an [ItemStack][to] and returns the
 * amount transferred. The [to] needs to have the [Energy Capability][CapabilityEnergy.ENERGY].
 *
 * Negative values for [amount] are allowed, but may not always work and are unrecommended.
 */
@JvmOverloads
fun transferEnergy(from: IEnergyStorage, to: ItemStack, amount: Int = Int.MAX_VALUE): Int =
    to.getCapability(CapabilityEnergy.ENERGY).map { itemStackEnergy ->
        transferEnergy(from, itemStackEnergy, amount)
    }.orElse(0)
