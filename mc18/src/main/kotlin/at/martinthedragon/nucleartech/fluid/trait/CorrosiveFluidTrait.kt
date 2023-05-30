package at.martinthedragon.nucleartech.fluid.trait

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.api.fluid.trait.AttachedFluidTrait
import at.martinthedragon.nucleartech.extensions.gold
import com.google.gson.JsonObject
import net.minecraft.network.chat.Style
import net.minecraft.util.GsonHelper

class CorrosiveFluidTrait(styleModifier: Style) : FluidTraitImpl(styleModifier) {
    fun getCorrosionLevel(data: AttachedFluidTrait<*>) = data.tag.getInt("Corrosion")
    fun isStronglyCorrosive(data: AttachedFluidTrait<*>) = getCorrosionLevel(data) > 50

    override fun getName(data: AttachedFluidTrait<*>) = if (isStronglyCorrosive(data)) LangKeys.FLUID_TRAIT_CORROSIVE_STRONG.gold() else super.getName(data)

    override fun loadAdditionalData(json: JsonObject) = super.loadAdditionalData(json).apply {
        putFloat("Corrosion", GsonHelper.getAsFloat(json, "corrosion"))
    }
}
