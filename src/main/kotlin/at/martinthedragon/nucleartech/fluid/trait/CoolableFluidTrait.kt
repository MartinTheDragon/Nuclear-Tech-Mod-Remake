package at.martinthedragon.nucleartech.fluid.trait

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.TranslationKey
import at.martinthedragon.nucleartech.api.fluid.trait.AttachedFluidTrait
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentUtils
import net.minecraft.network.chat.Style
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.GsonHelper
import net.minecraft.util.StringRepresentable
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.registries.ForgeRegistries

class CoolableFluidTrait(styleModifier: Style) : FluidTraitImpl(styleModifier) {
    fun getEfficiency(data: AttachedFluidTrait<*>, coolingType: CoolingType) = data.tag.getFloat(coolingType.serializedName)
    fun getFluidAmountRequired(data: AttachedFluidTrait<*>) = data.tag.getInt("Requires")
    fun getFluidAmountProduced(data: AttachedFluidTrait<*>) = data.tag.getInt("Produces")

    fun getFluidCoolingTo(data: AttachedFluidTrait<*>): Fluid? {
        val fluidString = data.tag.getString("CoolsTo")
        if (!ResourceLocation.isValidResourceLocation(fluidString)) return null
        val fluidLocation = ResourceLocation(fluidString)
        return if (ForgeRegistries.FLUIDS.containsKey(fluidLocation)) ForgeRegistries.FLUIDS.getValue(fluidLocation) else null
    }

    fun getHeatEnergy(data: AttachedFluidTrait<*>) = data.tag.getInt("HeatEnergy")

    override val isTooltipFlagReactive = true

    override fun appendHoverText(level: BlockGetter?, fluid: FluidStack, data: AttachedFluidTrait<*>, tooltip: MutableList<Component>, flag: TooltipFlag) {
        for (coolingType in CoolingType.values()) {
            val efficiency = getEfficiency(data, coolingType)
            if (efficiency > 0F) {
                tooltip += ComponentUtils.wrapInSquareBrackets(coolingType.translationKey.get().withStyle(styleModifier)).withStyle(styleModifier)

                if (flag.isAdvanced)
                    tooltip += LangKeys.FLUID_TRAIT_EFFICIENCY.format(efficiency * 100F).withStyle(styleModifier)
            }
        }
    }

    override fun loadAdditionalData(json: JsonObject) = super.loadAdditionalData(json).apply {
        val turbineEfficiency = GsonHelper.getAsFloat(json, CoolingType.Turbine.serializedName, 0F)
        val heatExchangerEfficiency = GsonHelper.getAsFloat(json, CoolingType.HeatExchanger.serializedName, 0F)
        if (turbineEfficiency == 0F && heatExchangerEfficiency == 0F) throw JsonParseException("At least one efficiency needs to be specified")
        putFloat(CoolingType.Turbine.serializedName, turbineEfficiency)
        putFloat(CoolingType.HeatExchanger.serializedName, heatExchangerEfficiency)
        putInt("Requires", GsonHelper.getAsInt(json, "requires"))
        putInt("Produces", GsonHelper.getAsInt(json, "produces"))
        val coolsTo = GsonHelper.getAsString(json, "cools_to")
        if (!ResourceLocation.isValidResourceLocation(coolsTo)) throw JsonParseException("Invalid resource location cools_to '$coolsTo'")
        if (!ForgeRegistries.FLUIDS.containsKey(ResourceLocation(coolsTo))) throw JsonParseException("Couldn't find fluid $coolsTo")
        putString("CoolsTo", coolsTo)
        putInt("HeatEnergy", GsonHelper.getAsInt(json, "heat_energy"))
    }

    enum class CoolingType(val translationKey: TranslationKey, private val serializedName: String) : StringRepresentable {
        Turbine(LangKeys.FLUID_TRAIT_COOLABLE_TURBINE, "turbine"),
        HeatExchanger(LangKeys.FLUID_TRAIT_COOLABLE_HEAT_EXCHANGER, "heat_exchanger")
        ;

        override fun getSerializedName() = serializedName
    }
}
