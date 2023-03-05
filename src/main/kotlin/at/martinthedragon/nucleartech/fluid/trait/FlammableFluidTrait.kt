package at.martinthedragon.nucleartech.fluid.trait

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.api.fluid.trait.AttachedFluidTrait
import at.martinthedragon.nucleartech.extensions.red
import at.martinthedragon.nucleartech.heat.HeatUnit
import at.martinthedragon.nucleartech.math.format
import at.martinthedragon.nucleartech.math.getPreferredUnit
import com.google.gson.JsonObject
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TextComponent
import net.minecraft.util.GsonHelper
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.BlockGetter
import net.minecraftforge.fluids.FluidStack

class FlammableFluidTrait(styleModifier: Style) : FluidTraitImpl(styleModifier) {
    fun getHeatEnergy(data: AttachedFluidTrait) = data.tag.getInt("HeatEnergy")

    override val isTooltipFlagReactive = true

    override fun appendHoverText(level: BlockGetter?, fluid: FluidStack, data: AttachedFluidTrait, tooltip: MutableList<Component>, flag: TooltipFlag) {
        super.appendHoverText(level, fluid, data, tooltip, flag)
        if (flag.isAdvanced) {
            val energy = getHeatEnergy(data)
            if (energy > 0) {
                tooltip += LangKeys.FLUID_TRAIT_ENERGY_INFO.format(TextComponent(HeatUnit.UnitType.HBM.getPreferredUnit(energy).format(energy)).red()).withStyle(styleModifier)
            }
        }
    }

    override fun loadAdditionalData(json: JsonObject) = super.loadAdditionalData(json).apply {
        putInt("HeatEnergy", GsonHelper.getAsInt(json, "heat_energy"))
    }
}
