package at.martinthedragon.nucleartech.entity.renderer

import at.martinthedragon.nucleartech.entity.Shrapnel
import at.martinthedragon.nucleartech.rendering.NuclearModelLayers
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Vector3f
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture

class VolcanicShrapnelRenderer(context: EntityRendererProvider.Context) : ShrapnelRenderer(context) {
    private val rotationVector = Vector3f(1F, 1F, 1F).apply(Vector3f::normalize)
    private val shrapnelModel = ShrapnelModel(Minecraft.getInstance().entityModels.bakeLayer(NuclearModelLayers.SHRAPNEL))

    override fun render(shrapnel: Shrapnel, yaw: Float, partialTicks: Float, matrix: PoseStack, buffers: MultiBufferSource, light: Int) {
        super.render(shrapnel, yaw, partialTicks, matrix, buffers, light)

        matrix.pushPose()
        matrix.mulPose(Vector3f.XP.rotationDegrees(180F))
        matrix.mulPose(rotationVector.rotationDegrees((shrapnel.tickCount % 360) * 10 + partialTicks))
        matrix.scale(3F, 3F, 3F) // scale up volcanic shrapnel

        val consumer = buffers.getBuffer(shrapnelModel.renderType(getTextureLocation(shrapnel)))
        shrapnelModel.renderToBuffer(matrix, consumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F)

        matrix.popPose()
    }
}
