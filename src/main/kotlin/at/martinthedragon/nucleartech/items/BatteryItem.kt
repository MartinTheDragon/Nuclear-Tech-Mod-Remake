package at.martinthedragon.nucleartech.items

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.energy.EnergyFormatter
import at.martinthedragon.nucleartech.energy.LudicrousEnergyStorage
import net.minecraft.ChatFormatting
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.util.Mth
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.Level
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.energy.CapabilityEnergy

open class BatteryItem(
    val capacity: Long,
    val chargeRate: Int,
    val dischargeRate: Int,
    properties: Properties
) : Item(properties.setNoRepair().stacksTo(1).durability(1000)) {
    val energyPerDamage = capacity / 1000

    override fun getDamage(stack: ItemStack?) = // to fix durability bar in tag-less stacks // TODO maybe consider calculating the damage here to avoid de-syncs
        if (!stack!!.hasTag()) getMaxDamage(stack) else stack.tag!!.getInt("Damage")

    override fun canBeDepleted() = false

    override fun canApplyAtEnchantingTable(stack: ItemStack?, enchantment: Enchantment?) = false

    override fun isBarVisible(stack: ItemStack?) = true

    override fun getBarColor(stack: ItemStack?) = Mth.color(0, 255, 100)

    override fun fillItemCategory(tab: CreativeModeTab, items: NonNullList<ItemStack>) {
        if (allowdedIn(tab)) {
            if (chargeRate > 0) items.add(ItemStack(this).apply { damageValue = 1000 })
            items.add(ItemStack(this).apply { orCreateTag.putLong("Energy", capacity) })
        }
    }

    override fun appendHoverText(stack: ItemStack, world: Level?, tooltip: MutableList<Component>, extra: TooltipFlag) {
        tooltip.add(TranslatableComponent("item.${NuclearTech.MODID}.batteries.energy_stored", EnergyFormatter.formatEnergy(stack.orCreateTag.getLong("Energy")), EnergyFormatter.formatEnergy(capacity)).withStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY))) // TODO configurable units
        tooltip.add(TranslatableComponent("item.${NuclearTech.MODID}.batteries.charge_rate", EnergyFormatter.formatEnergy(chargeRate)).withStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)))
        tooltip.add(TranslatableComponent("item.${NuclearTech.MODID}.batteries.discharge_rate", EnergyFormatter.formatEnergy(dischargeRate)).withStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)))
    }

    /*
    FIXME client-side duplication glitch
    For some reason, a duplication glitch occurs exceptionally often with this approach. Other mods' implementations also
    have that problem, but here, almost every tenth shift-click creates a dead copy on the client, even in machines from
    other mods. Behind this could be a tag modification even though the item does not exist anymore, and the client tries
    to save it anyway.
    */
    override fun initCapabilities(stack: ItemStack, nbt: CompoundTag?): ICapabilityProvider = object : ICapabilityProvider {
        @Suppress("UsePropertyAccessSyntax")
        private val energyCapability = LazyOptional.of { // doing it this way has the added benefit of the damage tag actually working ('cause it's delayed)
            object : LudicrousEnergyStorage(capacity, chargeRate, dischargeRate, stack.getOrCreateTag().getLong("Energy")) {
                override fun receiveEnergy(maxReceive: Int, simulate: Boolean): Int {
                    if (stack.isEmpty || stack.item !is BatteryItem) return 0
                    val energyTransferred = super.receiveEnergy(maxReceive, simulate)
                    if (!simulate) {
                        stack.damageValue = ((getActualMaxEnergyStored() - getActualEnergyStored()).toDouble() / energyPerDamage.toDouble()).toInt()
                        stack.getOrCreateTag().putLong("Energy", getActualEnergyStored())
                    }
                    return energyTransferred
                }

                override fun extractEnergy(maxExtract: Int, simulate: Boolean): Int {
                    if (stack.isEmpty || stack.item !is BatteryItem) return 0
                    val energyTransferred = super.extractEnergy(maxExtract, simulate)
                    if (!simulate) {
                        stack.damageValue = ((getActualMaxEnergyStored() - getActualEnergyStored()).toDouble() / energyPerDamage.toDouble()).toInt()
                        stack.getOrCreateTag().putLong("Energy", getActualEnergyStored())
                    }
                    return energyTransferred
                }
            }
        }

        override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> =
            if (cap == CapabilityEnergy.ENERGY) energyCapability.cast() else LazyOptional.empty()
    }
}
