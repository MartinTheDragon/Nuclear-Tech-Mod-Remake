package at.martinthedragon.nucleartech.fluid.trait

import at.martinthedragon.nucleartech.api.fluid.trait.AttachedFluidTrait
import at.martinthedragon.nucleartech.world.ChunkRadiation
import com.google.gson.JsonObject
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Style
import net.minecraft.util.GsonHelper
import net.minecraft.world.level.Level
import net.minecraftforge.fluids.FluidStack

class RadioactiveFluidTrait(styleModifier: Style) : FluidTraitImpl(styleModifier) {
    fun getRadiation(data: AttachedFluidTrait<*>) = data.tag.getFloat("Radiation")

    override fun releaseFluidInWorld(level: Level, pos: BlockPos, fluid: FluidStack, data: AttachedFluidTrait<*>) {
        super.releaseFluidInWorld(level, pos, fluid, data)
        ChunkRadiation.incrementRadiation(level, pos, fluid.amount * getRadiation(data))
    }

    override fun loadAdditionalData(json: JsonObject) = super.loadAdditionalData(json).apply {
        putFloat("Radiation", GsonHelper.getAsFloat(json, "radiation"))
    }
}
