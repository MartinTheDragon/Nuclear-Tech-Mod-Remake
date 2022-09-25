package at.martinthedragon.nucleartech.block.entity.renderer

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraftforge.client.model.renderable.MultipartTransforms
import net.minecraftforge.client.model.renderable.SimpleRenderable

abstract class GenericBlockEntityRenderer<T : BlockEntity>(@Suppress("UNUSED_PARAMETER") context: BlockEntityRendererProvider.Context) : BlockEntityRenderer<T> {
    protected open val renderType = RenderType::entityCutout
    abstract fun getModel(blockEntity: T): SimpleRenderable

    override fun render(blockEntity: T, partials: Float, matrix: PoseStack, buffers: MultiBufferSource, light: Int, overlay: Int) {
        matrix.pushPose()
        getModel(blockEntity).render(matrix, buffers, renderType, light, overlay, partials, MultipartTransforms.EMPTY)
        matrix.popPose()
    }

    override fun shouldRenderOffScreen(blockEntity: T): Boolean {
        val boundingBox = blockEntity.renderBoundingBox
        return boundingBox.xsize > 1 || boundingBox.ysize > 1 || boundingBox.zsize > 1
    }

    override fun getViewDistance() = 256
}
