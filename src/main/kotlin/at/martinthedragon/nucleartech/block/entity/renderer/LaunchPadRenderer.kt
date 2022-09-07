package at.martinthedragon.nucleartech.block.entity.renderer

import at.martinthedragon.nucleartech.block.entity.LaunchPadBlockEntity
import at.martinthedragon.nucleartech.item.MissileItem
import at.martinthedragon.nucleartech.rendering.SpecialModels
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraftforge.client.model.data.EmptyModelData
import net.minecraftforge.client.model.renderable.MultipartTransforms

class LaunchPadRenderer(@Suppress("UNUSED_PARAMETER") context: BlockEntityRendererProvider.Context) : BlockEntityRenderer<LaunchPadBlockEntity> {
    private val modelRenderer = Minecraft.getInstance().blockRenderer.modelRenderer

    override fun render(launchPad: LaunchPadBlockEntity, partials: Float, matrix: PoseStack, buffers: MultiBufferSource, light: Int, overlay: Int) {
        val model = SpecialModels.LAUNCH_PAD.get()
        val missileItem = launchPad.missileItem.item as? MissileItem<*>

        matrix.pushPose()
        matrix.translate(.5, .0, .5)
        model.render(matrix, buffers, RenderType::entityCutoutNoCull, light, overlay, partials, MultipartTransforms.EMPTY)

        if (missileItem != null) {
            val missileModel = SpecialModels.getBakedModel(missileItem.renderModel)
            val renderBuffer = buffers.getBuffer(RenderType.entityCutoutNoCull(missileItem.renderTexture))
            val renderScale = missileItem.renderScale
            matrix.pushPose()
            matrix.translate(0.0, 1.0, 0.0)
            matrix.scale(renderScale, renderScale, renderScale)
            modelRenderer.renderModel(matrix.last(), renderBuffer, null, missileModel, 1F, 1F, 1F, light, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE)
            matrix.popPose()
        }

        matrix.popPose()
    }

    override fun shouldRenderOffScreen(launchPad: LaunchPadBlockEntity) = true
    override fun getViewDistance() = 512
}
