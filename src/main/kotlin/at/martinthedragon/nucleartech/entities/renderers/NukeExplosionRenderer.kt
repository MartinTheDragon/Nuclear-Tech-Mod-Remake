package at.martinthedragon.nucleartech.entities.renderers

import at.martinthedragon.nucleartech.explosions.NukeExplosionEntity
import net.minecraft.client.renderer.culling.ClippingHelper
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.util.ResourceLocation

class NukeExplosionRenderer(entityRendererManager: EntityRendererManager) : EntityRenderer<NukeExplosionEntity>(entityRendererManager) {
    override fun getTextureLocation(p_110775_1_: NukeExplosionEntity): ResourceLocation {
        TODO("Not yet implemented")
    }

    override fun shouldRender(
        p_225626_1_: NukeExplosionEntity,
        p_225626_2_: ClippingHelper,
        p_225626_3_: Double,
        p_225626_5_: Double,
        p_225626_7_: Double
    ) = false // TODO
}
