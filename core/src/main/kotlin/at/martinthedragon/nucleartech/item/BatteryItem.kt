package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.coremodules.forge.common.capabilities.Capability
import at.martinthedragon.nucleartech.coremodules.forge.common.capabilities.ICapabilityProvider
import at.martinthedragon.nucleartech.coremodules.forge.common.util.LazyOptional
import at.martinthedragon.nucleartech.coremodules.forge.energy.CapabilityEnergy
import at.martinthedragon.nucleartech.coremodules.minecraft.core.Direction
import at.martinthedragon.nucleartech.coremodules.minecraft.nbt.CompoundTag
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.Component
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.gray
import at.martinthedragon.nucleartech.coremodules.minecraft.util.Mth
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.CreativeModeTab
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.Item
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.ItemStack
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.enchantment.Enchantment
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.Level
import at.martinthedragon.nucleartech.energy.EnergyUnit
import at.martinthedragon.nucleartech.energy.LudicrousEnergyStorage
import at.martinthedragon.nucleartech.math.format
import at.martinthedragon.nucleartech.math.getPreferredUnit
import at.martinthedragon.nucleartech.sorcerer.Linkage

open class BatteryItem(
    val capacity: Long,
    val chargeRate: Int,
    val dischargeRate: Int,
    properties: Properties
) : Item(properties.copy(stacksTo = 1, canRepair = false, maxDamage = 1000)) {
    val energyPerDamage = capacity / 1000

    override fun getDamage(itemStack: ItemStack) = if (!itemStack.hasTag()) getMaxDamage(itemStack) else itemStack.getTag()!!.getInt("Damage") // to fix durability bar in tag-less stacks // TODO maybe consider calculating the damage here to avoid de-syncs
    override fun canBeDepleted() = false
    override fun isBarVisible(@Linkage.Unused itemStack: ItemStack) = false
    override fun getBarColor(@Linkage.Unused itemStack: ItemStack) = Mth.color(0, 255, 100)

    override fun canApplyAtEnchantingTable(@Linkage.Unused itemStack: ItemStack, @Linkage.Unused enchantment: Enchantment) = false

    override fun fillItemCategory(tab: CreativeModeTab, items: MutableList<ItemStack>) {
        if (allowedIn(tab)) {
            if (chargeRate > 0) items += ItemStack(this).apply { damageValue = 1000 }
            items += ItemStack(this).apply { getOrCreateTag().putLong("Energy", capacity) }
        }
    }

    override fun appendHoverText(itemStack: ItemStack, @Linkage.Unused level: Level?, tooltip: MutableList<Component>, @Linkage.Unused extended: Boolean) {
        val amount = itemStack.getOrCreateTag().getLong("Energy")
        tooltip.add(LangKeys.ENERGY_ENERGY_STORED.format(EnergyUnit.UnitType.HBM.getPreferredUnit(amount).format(amount, false), EnergyUnit.UnitType.HBM.getPreferredUnit(capacity).format(capacity, false)).gray()) // TODO configurable units
        tooltip.add(LangKeys.ENERGY_CHARGE_RATE.format(EnergyUnit.UnitType.HBM.getPreferredUnit(chargeRate).format(chargeRate, false)).gray())
        tooltip.add(LangKeys.ENERGY_DISCHARGE_RATE.format(EnergyUnit.UnitType.HBM.getPreferredUnit(dischargeRate).format(dischargeRate, false)).gray())
    }

    /*
    FIXME client-side duplication glitch
    For some reason, a duplication glitch occurs exceptionally often with this approach. Other mods' implementations also
    have that problem, but here, almost every tenth shift-click creates a dead copy on the client, even in machines from
    other mods. Behind this could be a tag modification even though the item does not exist anymore, and the client tries
    to save it anyway.
    */
    override fun initCapabilities(itemStack: ItemStack, @Linkage.Unused nbt: CompoundTag?): ICapabilityProvider = object : ICapabilityProvider {
        private val energyCapability = LazyOptional.of { // doing it this way has the added benefit of the damage tag actually working ('cause it's delayed)
            object : LudicrousEnergyStorage(capacity, chargeRate, dischargeRate, itemStack.getOrCreateTag().getLong("Energy")) {
                override fun receiveEnergy(maxReceive: Int, simulate: Boolean): Int {
                    if (itemStack.isEmpty || itemStack.item !is BatteryItem) return 0
                    val energyTransferred = super.receiveEnergy(maxReceive, simulate)
                    if (!simulate) {
                        itemStack.damageValue = ((getActualMaxEnergyStored() - getActualEnergyStored()).toDouble() / energyPerDamage.toDouble()).toInt()
                        itemStack.getOrCreateTag().putLong("Energy", getActualEnergyStored())
                    }
                    return energyTransferred
                }

                override fun extractEnergy(maxExtract: Int, simulate: Boolean): Int {
                    if (itemStack.isEmpty || itemStack.item !is BatteryItem) return 0
                    val energyTransferred = super.extractEnergy(maxExtract, simulate)
                    if (!simulate) {
                        itemStack.damageValue = ((getActualMaxEnergyStored() - getActualEnergyStored()).toDouble() / energyPerDamage.toDouble()).toInt()
                        itemStack.getOrCreateTag().putLong("Energy", getActualEnergyStored())
                    }
                    return energyTransferred
                }
            }
        }

        override fun <T : Any> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> =
            if (cap == CapabilityEnergy.ENERGY) energyCapability.cast() else LazyOptional.empty()
    }
}
