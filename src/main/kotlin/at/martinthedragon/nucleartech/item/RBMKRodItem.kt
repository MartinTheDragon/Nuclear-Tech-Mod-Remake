package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.TranslationKey
import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKFluxReceiver
import at.martinthedragon.nucleartech.extensions.*
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.util.Mth
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import java.util.function.Supplier
import kotlin.math.*

class RBMKRodItem(
    properties: Properties,
    val pellet: Supplier<out RBMKPelletItem>?,
    val yield: Double,
    val reactivity: Double,
    val selfRate: Double = 0.0,
    val burnFunc: BurnFunction = BurnFunction.LOGARITHMIC,
    val depletionFunc: DepleteFunction = DepleteFunction.GENTLE_SLOPE,
    val xenonGen: Double = 0.5,
    val xenonBurn: Double = 50.0,
    val heat: Double = 1.0,
    val meltingPoint: Double = 1000.0,
    val diffusion: Double = 0.2,
    val neutronType: RBMKFluxReceiver.NeutronType = RBMKFluxReceiver.NeutronType.SLOW,
    val releaseType: RBMKFluxReceiver.NeutronType = RBMKFluxReceiver.NeutronType.FAST,
    val fullName: TranslationKey = pellet?.get()?.fullName ?: throw NullPointerException("No full name for RBMK rod specified"),
) : Item(properties.stacksTo(1)) {
    fun burn(stack: ItemStack, fluxIn: Double, reactivityMod: Double = 1.0): Double {
        var flux = fluxIn + selfRate
        var xenon = getPoison(stack) - xenonBurnFunc(flux)
        flux *= (1.0 - getPoisonLevel(stack))
        xenon += xenonGenFunc(flux)
        setPoison(stack, xenon.coerceIn(0.0, 100.0))
        setYield(stack, (getYield(stack) - flux).coerceAtLeast(0.0))

        val fluxOut = reactivityFunc(flux, getEnrichment(stack)) * reactivityMod
        setCoreHeat(stack, getCoreHeat(stack) + fluxOut * heat)

        return fluxOut
    }

    fun updateHeat(stack: ItemStack, mod: Double, diffusionMod: Double = 1.0) {
        val coreHeat = getCoreHeat(stack)
        val hullHeat = getHullHeat(stack)

        if (coreHeat > hullHeat) {
            val mid = (coreHeat - hullHeat) / 2.0
            setCoreHeat(stack, coreHeat - mid * diffusion * diffusionMod * mod)
            setHullHeat(stack, hullHeat + mid * diffusion * diffusionMod * mod)
        }
    }

    fun provideHeat(stack: ItemStack, heat: Double, mod: Double, provision: Double = 0.2): Double {
        val hullHeat = getHullHeat(stack)

        if (hullHeat > meltingPoint) {
            val coreHeat = getCoreHeat(stack)
            val average = (heat + hullHeat + coreHeat) / 3.0
            setCoreHeat(stack, average)
            setHullHeat(stack, average)
            return average
        }

        if (hullHeat <= heat) return 0.0

        val value = (hullHeat - heat) / 2.0 * provision * mod
        setHullHeat(stack, hullHeat - value)
        return value
    }

    fun reactivityFunc(fluxIn: Double, enrichment: Double): Double {
        val flux = fluxIn * reactivityModByEnrichment(enrichment)
        return when (burnFunc) {
            BurnFunction.PASSIVE -> selfRate * enrichment
            BurnFunction.LOGARITHMIC -> log10(flux + 1) * .5 * reactivity
            BurnFunction.PLATEAU -> (1 - E.pow(-flux / 25.0)) * reactivity
            BurnFunction.ARCH -> max((flux - (flux * flux / 10_000.0)) / 100.0 * reactivity, 0.0)
            BurnFunction.SIGMOID -> reactivity / (1 + E.pow(-(flux - 50.0) / 10.0))
            BurnFunction.SQUARE_ROOT -> sqrt(flux) * reactivity / 10.0
            BurnFunction.LINEAR -> flux / 100.0 * reactivity
            BurnFunction.QUADRATIC -> flux * flux / 10_000.0 * reactivity
            BurnFunction.EXPERIMENTAL -> flux * (sin(fluxIn) + 1) * reactivity
        }
    }

    fun reactivityModByEnrichment(enrichment: Double) = when (depletionFunc) {
        DepleteFunction.LINEAR -> enrichment
        DepleteFunction.STATIC -> 1.0
        DepleteFunction.BOOSTED_SLOPE -> enrichment + sin((enrichment - 1) * (enrichment - 1) * PI)
        DepleteFunction.RAISING_SLOPE -> enrichment + sin(enrichment * PI) / 2.0
        DepleteFunction.GENTLE_SLOPE -> enrichment + sin(enrichment * PI) / 3.0
    }

    fun xenonGenFunc(flux: Double) = flux * xenonGen
    fun xenonBurnFunc(flux: Double) = (flux * flux) / xenonBurn

    fun getFuncDescription(stack: ItemStack): Component {
        if (burnFunc == BurnFunction.PASSIVE) return TextComponent(selfRate.toString()).red()

        val function = when (burnFunc) {
            BurnFunction.LOGARITHMIC -> "log10(%1\$s + 1) * 0.5 * %2\$s"
            BurnFunction.PLATEAU -> "(1 - e^(-%1\$s / 25)) * %2\$s"
            BurnFunction.ARCH -> "(%1\$s - %1\$s² / 10000) / 100 * %2\$s [0;∞]"
            BurnFunction.SIGMOID -> "%2\$s / (1 + e^(-(%1\$s - 50) / 10)"
            BurnFunction.SQUARE_ROOT -> "sqrt(%1\$s) * %2\$s / 10"
            BurnFunction.LINEAR -> "%1\$s / 100 * %2\$s"
            BurnFunction.QUADRATIC -> "%1\$s² / 10000 * %2\$s"
            BurnFunction.EXPERIMENTAL -> "%1\$s * (sin(%1\$s) + 1) * %2\$s"
            else -> "ERROR"
        }

        var enrichment = getEnrichment(stack)

        if (enrichment < 1) {
            enrichment = reactivityModByEnrichment(enrichment)
            val reactivity = TextComponent("${(this.reactivity * enrichment * 1000.0).toInt() / 1000.0}").yellow()
            val enrichmentEach = TextComponent(" (${(enrichment * 1000.0).toInt() / 10.0}%)").gold()
            return TranslatableComponent(function, if (selfRate > 0) TextComponent("(x").append(TextComponent(" + $selfRate").red()).append(")") else "x", reactivity).white().append(enrichmentEach)
        }

        return TranslatableComponent(function, if (selfRate > 0) TextComponent("(x").append(TextComponent(" + $selfRate").red()).append(")") else "x", reactivity).white()
    }

    override fun isBarVisible(stack: ItemStack) = getBarWidth(stack) < 13
    override fun getBarWidth(stack: ItemStack) = (getEnrichment(stack) * 13.0).roundToInt()

    override fun getBarColor(stack: ItemStack): Int {
        val f = max(0F, getEnrichment(stack).toFloat())
        return Mth.hsvToRgb(f / 3F, 1F, 1F)
    }

    override fun appendHoverText(stack: ItemStack, level: Level?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        with(tooltip) {
            add(fullName.italic().gray())
            // TODO digamma rod

            if (selfRate > 0 || burnFunc == BurnFunction.SIGMOID) {
                add(LangKeys.RBMK_ROD_SOURCE.red())
            }

            add(LangKeys.RBMK_ROD_DEPLETION.format((((yield - getYield(stack)) / yield) * 100_000.0).toInt() / 1000.0).green())
            add(LangKeys.RBMK_ROD_XENON.format((getPoison(stack) * 1000.0).toInt() / 1000.0).darkPurple())
            add(LangKeys.RBMK_ROD_SPLITS_WITH.format(neutronType.displayName).blue())
            add(LangKeys.RBMK_ROD_SPLITS_INTO.format(releaseType.displayName).blue())
            add(LangKeys.RBMK_ROD_FLUX_FUNC.format(getFuncDescription(stack)).yellow())
            add(LangKeys.RBMK_ROD_FUNC_TYPE.format(burnFunc.displayName).yellow())
            add(LangKeys.RBMK_ROD_XENON_GEN.format(TextComponent("x * $xenonGen").white()).yellow())
            add(LangKeys.RBMK_ROD_XENON_BURN.format(TextComponent("x² * $xenonBurn").white()).yellow())
            add(LangKeys.RBMK_ROD_HEAT.format("$heat°C").gold())
            add(LangKeys.RBMK_ROD_DIFFUSION.format("$diffusion½").gold())
            add(LangKeys.RBMK_ROD_HEAT_HULL.format((getHullHeat(stack) * 10.0).toInt() / 10.0).red())
            add(LangKeys.RBMK_ROD_HEAT_CORE.format((getCoreHeat(stack) * 10.0).toInt() / 10.0).red())
            add(LangKeys.RBMK_ROD_MELTING_POINT.format("$meltingPoint°C").darkRed())
        }
    }

    enum class BurnFunction(val displayName: Component) {
        PASSIVE(LangKeys.RBMK_BURN_PASSIVE.darkGreen()),
        LOGARITHMIC(LangKeys.RBMK_BURN_LOGARITHMIC.yellow()),
        PLATEAU(LangKeys.RBMK_BURN_EULER.green()),
        ARCH(LangKeys.RBMK_BURN_NEGATIVE_QUADRATIC.red()),
        SIGMOID(LangKeys.RBMK_BURN_SIGMOID.green()),
        SQUARE_ROOT(LangKeys.RBMK_BURN_SQUARE_ROOT.yellow()),
        LINEAR(LangKeys.RBMK_BURN_LINEAR.red()),
        QUADRATIC(LangKeys.RBMK_BURN_QUADRATIC.red()),
        EXPERIMENTAL(LangKeys.RBMK_BURN_SINE_SLOPE.red()),
    }

    enum class DepleteFunction {
        LINEAR,
        RAISING_SLOPE,
        BOOSTED_SLOPE,
        GENTLE_SLOPE,
        STATIC,
    }

    companion object {
        fun getEnrichment(stack: ItemStack) = getYield(stack) / (stack.item as RBMKRodItem).yield
        fun getPoisonLevel(stack: ItemStack) = getPoison(stack) / 100.0

        fun setYield(stack: ItemStack, yield: Double) = setDouble(stack, "yield", yield)
        fun getYield(stack: ItemStack) = getDouble(stack, "yield")

        fun setPoison(stack: ItemStack, xenon: Double) = setDouble(stack, "xenon", xenon)
        fun getPoison(stack: ItemStack) = getDouble(stack, "xenon")

        fun setCoreHeat(stack: ItemStack, heat: Double) = setDouble(stack, "core", heat)
        fun getCoreHeat(stack: ItemStack) = getDouble(stack, "core")

        fun setHullHeat(stack: ItemStack, heat: Double) = setDouble(stack, "hull", heat)
        fun getHullHeat(stack: ItemStack) = getDouble(stack, "hull")

        private fun setDouble(stack: ItemStack, key: String, value: Double) {
            if (stack.tag == null)
                setDefaults(stack)

            stack.tag?.putDouble(key, value)
        }

        private fun getDouble(stack: ItemStack, key: String): Double {
            if (stack.item !is RBMKRodItem) return 0.0

            if (stack.tag == null)
                setDefaults(stack)

            return stack.tag?.getDouble(key) ?: 0.0
        }

        private fun setDefaults(stack: ItemStack) {
            val item = stack.item
            if (item !is RBMKRodItem) return

            stack.orCreateTag
            setYield(stack, item.yield)
            setCoreHeat(stack, 20.0)
            setHullHeat(stack, 20.0)
        }
    }
}
