package at.martinthedragon.nucleartech.item

import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.energy.EnergyStorage

class BatteryOfInfinityItem(properties: Properties) : BatteryItem(0, 0, 0, properties) {
    override fun isBarVisible(stack: ItemStack) = false

    override fun fillItemCategory(tab: CreativeModeTab, items: NonNullList<ItemStack>) {
        if (allowdedIn(tab)) items.add(ItemStack(this))
    }

    override fun appendHoverText(stack: ItemStack, world: Level?, tooltip: MutableList<Component>, extra: TooltipFlag) {}

    override fun initCapabilities(stack: ItemStack, nbt: CompoundTag?): ICapabilityProvider = object : ICapabilityProvider {
        private val energyStorage = object : EnergyStorage(0, 0, 0, 0) {
            override fun canExtract() = true
            override fun canReceive() = true
            override fun extractEnergy(maxExtract: Int, simulate: Boolean) = maxExtract
            override fun receiveEnergy(maxReceive: Int, simulate: Boolean) = maxReceive
            override fun getEnergyStored() = Int.MAX_VALUE
            override fun getMaxEnergyStored() = Int.MAX_VALUE
        }

        private val energyCapability = LazyOptional.of(this::energyStorage)

        override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> =
            if (cap == CapabilityEnergy.ENERGY) energyCapability.cast() else LazyOptional.empty()
    }
}
