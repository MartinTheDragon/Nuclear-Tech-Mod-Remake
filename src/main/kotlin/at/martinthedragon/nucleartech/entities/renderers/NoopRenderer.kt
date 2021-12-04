package at.martinthedragon.nucleartech.entities.renderers

import net.minecraft.client.renderer.culling.Frustum
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity

class NoopRenderer<T : Entity>(context: EntityRendererProvider.Context) : EntityRenderer<T>(context) {
    override fun getTextureLocation(p_110775_1_: T): ResourceLocation? = null
    override fun shouldRender(p_225626_1_: T, p_225626_2_: Frustum, p_225626_3_: Double, p_225626_5_: Double, p_225626_7_: Double) = false
}
