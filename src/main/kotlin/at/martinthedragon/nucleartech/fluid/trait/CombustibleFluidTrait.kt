package at.martinthedragon.nucleartech.fluid.trait

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.TranslationKey
import at.martinthedragon.nucleartech.api.fluid.trait.AttachedFluidTrait
import at.martinthedragon.nucleartech.energy.EnergyUnit
import at.martinthedragon.nucleartech.extensions.red
import at.martinthedragon.nucleartech.math.format
import at.martinthedragon.nucleartech.math.getPreferredUnit
import com.google.gson.JsonObject
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TextComponent
import net.minecraft.util.GsonHelper
import net.minecraft.util.StringRepresentable
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.BlockGetter
import net.minecraftforge.fluids.FluidStack

class CombustibleFluidTrait(styleModifier: Style) : FluidTraitImpl(styleModifier) {
    fun getCombustionEnergy(data: AttachedFluidTrait<*>) = data.tag.getInt("Energy")
    fun getFuelGrade(data: AttachedFluidTrait<*>) = FuelGrade.values().find { it.serializedName == data.tag.getString("FuelGrade") } ?: FuelGrade.Low

    override val isTooltipFlagReactive = true

    override fun appendHoverText(level: BlockGetter?, fluid: FluidStack, data: AttachedFluidTrait<*>, tooltip: MutableList<Component>, flag: TooltipFlag) {
        super.appendHoverText(level, fluid, data, tooltip, flag)
        if (flag.isAdvanced) {
            val energy = getCombustionEnergy(data)
            val fuelGrade = getFuelGrade(data)
            if (energy > 0) {
                tooltip += LangKeys.FLUID_TRAIT_ENERGY_INFO.format(TextComponent(EnergyUnit.UnitType.HBM.getPreferredUnit(energy).format(energy)).red()).withStyle(styleModifier)
                tooltip += LangKeys.FLUID_TRAIT_COMBUSTIBLE_FUEL_GRADE.format(fuelGrade.translationKey.red()).withStyle(styleModifier)
            }
        }
    }

    override fun loadAdditionalData(json: JsonObject) = super.loadAdditionalData(json).apply {
        putInt("Energy", GsonHelper.getAsInt(json, "energy"))
        putString("FuelGrade", GsonHelper.getAsString(json, "grade"))
    }

    enum class FuelGrade(val translationKey: TranslationKey, private val serializedName: String) : StringRepresentable {
        Low(LangKeys.FLUID_TRAIT_COMBUSTIBLE_FUEL_GRADE_LOW, "low"),
        Medium(LangKeys.FLUID_TRAIT_COMBUSTIBLE_FUEL_GRADE_MEDIUM, "medium"),
        High(LangKeys.FLUID_TRAIT_COMBUSTIBLE_FUEL_GRADE_HIGH, "high"),
        Aviation(LangKeys.FLUID_TRAIT_COMBUSTIBLE_FUEL_GRADE_AERO, "aero"),
        Gaseous(LangKeys.FLUID_TRAIT_COMBUSTIBLE_FUEL_GRADE_GAS, "gas"),
        ;

        override fun getSerializedName() = serializedName
    }
}
