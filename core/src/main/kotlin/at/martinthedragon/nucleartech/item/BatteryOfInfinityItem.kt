package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.coremodules.forge.common.capabilities.Capability
import at.martinthedragon.nucleartech.coremodules.forge.common.capabilities.ICapabilityProvider
import at.martinthedragon.nucleartech.coremodules.forge.common.util.LazyOptional
import at.martinthedragon.nucleartech.coremodules.forge.energy.CapabilityEnergy
import at.martinthedragon.nucleartech.coremodules.forge.energy.IEnergyStorage
import at.martinthedragon.nucleartech.coremodules.minecraft.core.Direction
import at.martinthedragon.nucleartech.coremodules.minecraft.nbt.CompoundTag
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.Component
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.CreativeModeTab
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.ItemStack
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.Level

class BatteryOfInfinityItem(properties: Properties) : BatteryItem(0, 0, 0, properties) {
    override fun isBarVisible(itemStack: ItemStack) = false

    override fun fillItemCategory(tab: CreativeModeTab, items: MutableList<ItemStack>) {
        if (allowedIn(tab)) items.add(ItemStack(this))
    }

    override fun appendHoverText(itemStack: ItemStack, level: Level?, tooltip: MutableList<Component>, extended: Boolean) {}

    override fun initCapabilities(itemStack: ItemStack, nbt: CompoundTag?): ICapabilityProvider = object : ICapabilityProvider {
        private val energyStorage = object : IEnergyStorage {
            override fun canExtract() = true
            override fun canReceive() = true
            override fun extractEnergy(maxExtract: Int, simulate: Boolean) = maxExtract
            override fun receiveEnergy(maxReceive: Int, simulate: Boolean) = maxReceive
            override fun getEnergyStored() = Int.MAX_VALUE
            override fun getMaxEnergyStored() = Int.MAX_VALUE
        }

        private val energyCapability = LazyOptional.of(this::energyStorage)

        override fun <T : Any> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> =
            if (cap == CapabilityEnergy.ENERGY) energyCapability.cast() else LazyOptional.empty()
    }
}
