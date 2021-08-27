package at.martinthedragon.nucleartech.entities.renderers.layers

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.entities.NuclearCreeperEntity
import net.minecraft.client.renderer.entity.IEntityRenderer
import net.minecraft.client.renderer.entity.layers.EnergyLayer
import net.minecraft.client.renderer.entity.model.CreeperModel
import net.minecraft.util.ResourceLocation

class NuclearCreeperChargeLayer(
    entityRenderer: IEntityRenderer<NuclearCreeperEntity, CreeperModel<NuclearCreeperEntity>>
) : EnergyLayer<NuclearCreeperEntity, CreeperModel<NuclearCreeperEntity>>(entityRenderer) {
    private val model = CreeperModel<NuclearCreeperEntity>(2F)

    override fun xOffset(p0: Float) = p0 * .01F
    override fun getTextureLocation() = POWER_LOCATION
    override fun model() = model

    companion object {
        private val POWER_LOCATION = ResourceLocation(NuclearTech.MODID, "textures/entity/nuclear_creeper/nuclear_creeper_armor.png")
    }
}
