package at.martinthedragon.nucleartech.math

import at.martinthedragon.nucleartech.coremodules.OPTIMIZED
import at.martinthedragon.nucleartech.coremodules.minecraft.client.Minecraft

/**
 * An interface to be implemented by an enum containing units. The enum constants are expected to be in order of their [ratio].
 */
interface ResourceUnit<U, T>
    where U : ResourceUnit<U, T>,
          U : Enum<U>,
          T : ResourceUnit.UnitType<U, T>
{
    /**
     * How much one of this unit equates to the input in [format].
     * The first and [UnitType.defaultGetter] unit of this [unitType] should have a ratio of `1.0`.
     *
     * For example, if the base unit is conceptually a milliliter, that value will be inputted in [format].
     * So for the milliliter unit the ratio would be `1.0`, and for a liter it would be `1000.0`
     */
    val ratio: Double

    /**
     * Combined with [ratio], it determines when [UnitType.getPreferredUnit] will start switching to this unit from a smaller one,
     * so that the inputted value is larger than [ratio] multiplied by [leniency]. A value of `1` means no leniency.
     */
    val leniency: Int

    /**
     * How many decimals points are to be shown in the string returned by [format].
     */
    val decimals: Int

    /**
     * Prefixed to the [unitType]'s [UnitType.unitString] in [format].
     */
    val unitPrefix: String

    /**
     * The general [UnitType], for conversion between units.
     */
    val unitType: T

    /**
     * Formats the [amount] based on the properties of this enum constant.
     * The decimal separator depends on the selected language in the [Minecraft] client.
     */
    fun format(amount: Long, withUnitName: Boolean = true): String {
        val amountInUnit = amount / ratio
        val string = "%.${decimals}f".format(OPTIMIZED.getSelectedLocale(), amountInUnit) + ' ' + unitPrefix
        return if (withUnitName) string + unitType.unitString else string
    }

    /**
     * A general common type of units between different actual [ResourceUnit]s.
     * Should be implemented as an enum.
     *
     * @see ResourceUnit.unitType
     */
    interface UnitType<U, T>
        where U : ResourceUnit<U, T>,
              U : Enum<U>,
              T : UnitType<U, T>
    {
        /**
         * The shorthand name for this unit type.
         *
         * For example, if the general unit type is liters, the string would be `"L"`.
         */
        val unitString: String

        /**
         * The default unit if [getPreferredUnit] cannot determine a better one.
         * Usually the one with the smallest [ResourceUnit.ratio].
         *
         * Needs to be a function because of cyclic enum references, which can lead to NPE.
         */
        val defaultGetter: () -> U

        /**
         * Attempts to find the most suitable [ResourceUnit] for the inputted [amount] based on all units' [ResourceUnit.ratio] and [ResourceUnit.leniency].
         */
        fun getPreferredUnit(amount: Long): U {
            val defaultUnit = defaultGetter()
            // don't worry about performance, classes and enum constants are cached
            val applicableUnits = defaultUnit.javaClass.enumConstants ?: return defaultUnit
            return applicableUnits.filter { it.unitType == this }.findLast { it.ratio * it.leniency <= amount } ?: defaultUnit
        }
    }

    companion object {
        const val MILLI = 0.001
        const val CENTI = 0.01
        const val DECI = 0.1
        const val ONE = 1.0
        const val KILO = 1_000.0
        const val MEGA = 1_000_000.0
        const val GIGA = 1_000_000_000.0
        const val TERA = 1_000_000_000_000.0
        const val PETA = 1_000_000_000_000_000.0
        const val EXA = 1_000_000_000_000_000_000.0

        const val PREFIX_MILLI = "m"
        const val PREFIX_CENTI ="c"
        const val PREFIX_DECI = "d"
        const val PREFIX_ONE = ""
        const val PREFIX_KILO = "k"
        const val PREFIX_MEGA = "M"
        const val PREFIX_GIGA = "G"
        const val PREFIX_TERA = "T"
        const val PREFIX_PETA = "P"
        const val PREFIX_EXA = "E"
    }
}

fun ResourceUnit<*, *>.format(amount: Int, withUnitName: Boolean = true) = format(amount.toLong(), withUnitName)
fun ResourceUnit.UnitType<*, *>.getPreferredUnit(amount: Int) = getPreferredUnit(amount.toLong())

fun ResourceUnit.UnitType<*, *>.formatStorageFilling(fill: Long, maxFill: Long) =
    "${getPreferredUnit(fill).format(fill, true)} / ${getPreferredUnit(maxFill).format(maxFill, true)}"

fun ResourceUnit.UnitType<*, *>.formatStorageFilling(fill: Int, maxFill: Int) = formatStorageFilling(fill.toLong(), maxFill.toLong())
