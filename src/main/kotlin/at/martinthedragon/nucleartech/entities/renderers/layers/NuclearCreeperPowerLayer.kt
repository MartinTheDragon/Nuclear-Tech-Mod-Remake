package at.martinthedragon.nucleartech.entities.renderers.layers

import at.martinthedragon.nucleartech.entities.NuclearCreeper
import at.martinthedragon.nucleartech.ntm
import net.minecraft.client.model.CreeperModel
import net.minecraft.client.model.geom.EntityModelSet
import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer

class NuclearCreeperPowerLayer(parent: RenderLayerParent<NuclearCreeper, CreeperModel<NuclearCreeper>>, entityModelSet: EntityModelSet) : EnergySwirlLayer<NuclearCreeper, CreeperModel<NuclearCreeper>>(parent) {
    private val POWER_LOCATION = ntm("textures/entity/nuclear_creeper/nuclear_creeper_armor.png")
    private val model = CreeperModel<NuclearCreeper>(entityModelSet.bakeLayer(ModelLayers.CREEPER_ARMOR))

    override fun xOffset(offset: Float) = offset * .01F
    override fun getTextureLocation() = POWER_LOCATION
    override fun model() = model
}
