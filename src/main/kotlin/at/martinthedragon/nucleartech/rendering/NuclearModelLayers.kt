package at.martinthedragon.nucleartech.rendering

import at.martinthedragon.nucleartech.NuclearTech
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.resources.ResourceLocation

object NuclearModelLayers {
    val STEAM_PRESS = createLocation("steam_press")

    private fun createLocation(name: String, sub: String = "main") = ModelLayerLocation(ResourceLocation(NuclearTech.MODID, name), sub)
}
