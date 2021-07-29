package at.martinthedragon.nucleartech.items

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.energy.EnergyFormatter
import at.martinthedragon.nucleartech.energy.LudicrousEnergyStorage
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.enchantment.Enchantment
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.Direction
import net.minecraft.util.NonNullList
import net.minecraft.util.math.MathHelper
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.Style
import net.minecraft.util.text.TextFormatting
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World
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
    private val energyPerDamage = capacity / 1000

    override fun canBeDepleted() = false

    override fun canApplyAtEnchantingTable(stack: ItemStack?, enchantment: Enchantment?) = false

    override fun showDurabilityBar(stack: ItemStack?) = true

    override fun getRGBDurabilityForDisplay(stack: ItemStack?) = MathHelper.color(0, 255, 100)

    override fun fillItemCategory(tab: ItemGroup, items: NonNullList<ItemStack>) {
        if (allowdedIn(tab)) {
            if (chargeRate > 0) items.add(ItemStack(this).apply { damageValue = 1000 })
            items.add(ItemStack(this).apply { orCreateTag.putLong("Energy", capacity) })
        }
    }

    override fun appendHoverText(stack: ItemStack, world: World?, tooltip: MutableList<ITextComponent>, extra: ITooltipFlag) {
        tooltip.add(TranslationTextComponent("item.${NuclearTech.MODID}.batteries.energy_stored", EnergyFormatter.formatEnergy(stack.orCreateTag.getLong("Energy")), EnergyFormatter.formatEnergy(capacity)).withStyle(Style.EMPTY.applyFormat(TextFormatting.GRAY))) // TODO configurable units
        tooltip.add(TranslationTextComponent("item.${NuclearTech.MODID}.batteries.charge_rate", EnergyFormatter.formatEnergy(chargeRate)).withStyle(Style.EMPTY.applyFormat(TextFormatting.GRAY)))
        tooltip.add(TranslationTextComponent("item.${NuclearTech.MODID}.batteries.discharge_rate", EnergyFormatter.formatEnergy(dischargeRate)).withStyle(Style.EMPTY.applyFormat(TextFormatting.GRAY)))
    }

    /*
    FIXME client-side duplication glitch
    For some reason, a duplication glitch occurs exceptionally often with this approach. Other mods' implementations also
    have that problem, but here, almost every tenth shift-click creates a dead copy on the client, even in machines from
    other mods. Behind this could be a tag modification even though the item does not exist anymore, and the client tries
    to save it anyway.
    */
    override fun initCapabilities(stack: ItemStack, nbt: CompoundNBT?): ICapabilityProvider = object : ICapabilityProvider {
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
