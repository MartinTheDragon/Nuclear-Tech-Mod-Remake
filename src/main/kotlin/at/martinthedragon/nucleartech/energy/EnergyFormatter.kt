package at.martinthedragon.nucleartech.energy

import at.martinthedragon.nucleartech.LangKeys
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.TextComponent

object EnergyFormatter {
    enum class EnergyUnit(val ratio: Double, val decimals: Int) {
        HE(.25, 2),
        FE(1.0, 0)
    }

    @JvmOverloads @JvmStatic
    fun formatEnergy(amount: Int, unit: EnergyUnit = EnergyUnit.HE, withUnit: Boolean = false): String =
        formatEnergy(amount.toLong(), unit, withUnit)

    // dirty but performant implementation
    @JvmOverloads @JvmStatic
    fun formatEnergy(amount: Long, unit: EnergyUnit = EnergyUnit.HE, withUnit: Boolean = false): String {
        val amountInUnit = amount * unit.ratio
        val valueForSuffix: Double
        val suffix = when {
            amountInUnit >= 1_000_000_000_000_000_000L -> 'E'.also { valueForSuffix = amountInUnit * 0.000000000000000001 }
            amountInUnit >= 1_000_000_000_000_000L -> 'P'.also { valueForSuffix = amountInUnit * 0.000000000000001 }
            amountInUnit >= 1_000_000_000_000 -> 'T'.also { valueForSuffix = amountInUnit * 0.000000000001 }
            amountInUnit >= 1_000_000_000 -> 'G'.also { valueForSuffix = amountInUnit * 0.000000001 }
            amountInUnit >= 1_000_000 -> 'M'.also { valueForSuffix = amountInUnit * 0.000001 }
            amountInUnit >= 1_000 -> 'k'.also { valueForSuffix = amountInUnit * 0.001 }
            else -> {
                valueForSuffix = amountInUnit
                'ß' // do not use null so types aren't boxed
            }
        }

        val string = "%.${unit.decimals}f".format(Minecraft.getInstance().languageManager.selected.javaLocale, valueForSuffix) + if (suffix == 'ß') "" else suffix
        return if (withUnit) string + unit.name else string
    }

    fun formatTooltip(amount: Int, max: Int, unit: EnergyUnit = EnergyUnit.HE) =
        formatTooltip(amount.toLong(), max.toLong(), unit)

    @JvmStatic
    fun formatTooltip(amount: Long, max: Long, unit: EnergyUnit = EnergyUnit.HE) = listOf(
        LangKeys.ENERGY.get(),
        TextComponent("${formatEnergy(amount, unit)}/${formatEnergy(max, unit, true)}")
    )
}
