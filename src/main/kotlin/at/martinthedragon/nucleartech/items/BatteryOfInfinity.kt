package at.martinthedragon.nucleartech.items

import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.Direction
import net.minecraft.util.NonNullList
import net.minecraft.util.text.ITextComponent
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.energy.EnergyStorage

class BatteryOfInfinity(properties: Properties) : BatteryItem(0, 0, 0, properties) {
    override fun showDurabilityBar(stack: ItemStack?) = false

    override fun fillItemCategory(tab: ItemGroup, items: NonNullList<ItemStack>) {
        if (allowdedIn(tab)) items.add(ItemStack(this))
    }

    override fun appendHoverText(stack: ItemStack, world: World?, tooltip: MutableList<ITextComponent>, extra: ITooltipFlag) {}

    override fun initCapabilities(stack: ItemStack, nbt: CompoundNBT?): ICapabilityProvider = object : ICapabilityProvider {
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
