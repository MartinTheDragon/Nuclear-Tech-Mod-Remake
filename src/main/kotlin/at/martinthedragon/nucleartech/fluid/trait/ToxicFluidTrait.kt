package at.martinthedragon.nucleartech.fluid.trait

import at.martinthedragon.nucleartech.api.fluid.trait.AttachedFluidTrait
import com.google.gson.JsonObject
import net.minecraft.network.chat.Style
import net.minecraft.util.GsonHelper

class ToxicFluidTrait(styleModifier: Style) : FluidTraitImpl(styleModifier) {
    fun getLevel(data: AttachedFluidTrait<*>) = data.tag.getInt("Level")
    fun isWithering(data: AttachedFluidTrait<*>) = data.tag.getBoolean("Wither")

    override fun loadAdditionalData(json: JsonObject) = super.loadAdditionalData(json).apply {
        putInt("Level", GsonHelper.getAsInt(json, "level"))
        putBoolean("Wither", GsonHelper.getAsBoolean(json, "wither"))
    }
}
