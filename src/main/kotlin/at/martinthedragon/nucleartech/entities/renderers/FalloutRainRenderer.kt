package at.martinthedragon.nucleartech.entities.renderers

import at.martinthedragon.nucleartech.explosions.FalloutRainEntity
import net.minecraft.client.renderer.culling.ClippingHelper
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.util.ResourceLocation

class FalloutRainRenderer(entityRendererManager: EntityRendererManager) : EntityRenderer<FalloutRainEntity>(entityRendererManager) {
    override fun getTextureLocation(p_110775_1_: FalloutRainEntity): ResourceLocation {
        TODO("Not yet implemented")
    }

    override fun shouldRender(
        p_225626_1_: FalloutRainEntity,
        p_225626_2_: ClippingHelper,
        p_225626_3_: Double,
        p_225626_5_: Double,
        p_225626_7_: Double
    ): Boolean = false // TODO
}
