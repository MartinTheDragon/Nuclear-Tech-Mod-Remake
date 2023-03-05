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

class HeatableFluidTrait(styleModifier: Style) : FluidTraitImpl(styleModifier) {
    fun getEfficiency(data: AttachedFluidTrait, heatingType: HeatingType) = data.tag.getFloat(heatingType.serializedName)
    fun getFluidAmountRequired(data: AttachedFluidTrait) = data.tag.getInt("Requires")
    fun getFluidAmountProduced(data: AttachedFluidTrait) = data.tag.getInt("Produces")

    fun getFluidHeatingTo(data: AttachedFluidTrait): Fluid? {
        val fluidString = data.tag.getString("HeatsTo")
        if (!ResourceLocation.isValidResourceLocation(fluidString)) return null
        val fluidLocation = ResourceLocation(fluidString)
        return if (ForgeRegistries.FLUIDS.containsKey(fluidLocation)) ForgeRegistries.FLUIDS.getValue(fluidLocation) else null
    }

    fun getHeatEnergy(data: AttachedFluidTrait) = data.tag.getInt("HeatEnergy")

    override val isTooltipFlagReactive = true

    override fun appendHoverText(level: BlockGetter?, fluid: FluidStack, data: AttachedFluidTrait, tooltip: MutableList<Component>, flag: TooltipFlag) {
        for (heatingType in HeatingType.values()) {
            val efficiency = getEfficiency(data, heatingType)
            if (efficiency > 0F) {
                tooltip += ComponentUtils.wrapInSquareBrackets(heatingType.translationKey.get().withStyle(styleModifier)).withStyle(styleModifier)

                if (flag.isAdvanced)
                    tooltip += LangKeys.FLUID_TRAIT_EFFICIENCY.format(efficiency * 100F).withStyle(styleModifier)
            }
        }
    }

    override fun loadAdditionalData(json: JsonObject) = super.loadAdditionalData(json).apply {
        val boilerEfficiency = GsonHelper.getAsFloat(json, HeatingType.Boiler.serializedName, 0F)
        val heatExchangerEfficiency = GsonHelper.getAsFloat(json, HeatingType.HeatExchanger.serializedName, 0F)
        if (boilerEfficiency == 0F && heatExchangerEfficiency == 0F) throw JsonParseException("At least one efficiency needs to be specified")
        putFloat(HeatingType.Boiler.serializedName, boilerEfficiency)
        putFloat(HeatingType.HeatExchanger.serializedName, heatExchangerEfficiency)
        putInt("Requires", GsonHelper.getAsInt(json, "requires"))
        putInt("Produces", GsonHelper.getAsInt(json, "produces"))
        val heatsTo = GsonHelper.getAsString(json, "heats_to")
        if (!ResourceLocation.isValidResourceLocation(heatsTo)) throw JsonParseException("Invalid resource location heats_to '$heatsTo'")
        if (!ForgeRegistries.FLUIDS.containsKey(ResourceLocation(heatsTo))) throw JsonParseException("Couldn't find fluid $heatsTo")
        putString("HeatsTo", heatsTo)
        putInt("HeatEnergy", GsonHelper.getAsInt(json, "heat_energy"))
    }

    enum class HeatingType(val translationKey: TranslationKey, private val serializedName: String) : StringRepresentable {
        Boiler(LangKeys.FLUID_TRAIT_HEATABLE_BOILER, "boiler"),
        HeatExchanger(LangKeys.FLUID_TRAIT_HEATABLE_HEAT_EXCHANGER, "heat_exchanger")
        ;

        override fun getSerializedName() = serializedName
    }
}
