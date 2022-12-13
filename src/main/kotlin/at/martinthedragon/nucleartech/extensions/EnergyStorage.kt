package at.martinthedragon.nucleartech.extensions

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.energy.EnergyUnit
import at.martinthedragon.nucleartech.math.formatStorageFilling
import net.minecraft.network.chat.TextComponent
import net.minecraftforge.energy.EnergyStorage

fun EnergyStorage.getTooltip() = listOf(
    LangKeys.ENERGY.get(),
    TextComponent(EnergyUnit.UnitType.HBM.formatStorageFilling(energyStored, maxEnergyStored))
)
