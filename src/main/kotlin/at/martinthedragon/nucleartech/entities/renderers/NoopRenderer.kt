package at.martinthedragon.nucleartech.entities.renderers

import net.minecraft.client.renderer.culling.ClippingHelper
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation

class NoopRenderer<T : Entity>(entityRendererManager: EntityRendererManager) : EntityRenderer<T>(entityRendererManager) {
    override fun getTextureLocation(p_110775_1_: T): ResourceLocation? = null
    override fun shouldRender(p_225626_1_: T, p_225626_2_: ClippingHelper, p_225626_3_: Double, p_225626_5_: Double, p_225626_7_: Double) = false
}
