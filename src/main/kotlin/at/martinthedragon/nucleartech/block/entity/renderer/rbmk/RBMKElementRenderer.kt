package at.martinthedragon.nucleartech.block.entity.renderer.rbmk

import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKBase
import at.martinthedragon.nucleartech.block.rbmk.RBMKBaseBlock
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.rendering.SpecialModels
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.block.ModelBlockRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.client.model.data.EmptyModelData

abstract class RBMKElementRenderer<T>(@Suppress("UNUSED_PARAMETER") context: BlockEntityRendererProvider.Context) : BlockEntityRenderer<T>
    where T : BlockEntity,
          T : RBMKBase
{
    protected open val renderType = RenderType::entityCutout
    abstract fun getModel(blockEntity: T): BakedModel
    open fun getLidModel(blockEntity: T) = SpecialModels.RBMK_LID.get()
    abstract val texture: ResourceLocation
    protected open val glassLidTexture = ntm("textures/other/rbmk/blank_glass.png")

    override fun render(blockEntity: T, partials: Float, matrix: PoseStack, buffers: MultiBufferSource, light: Int, overlay: Int) {
        matrix.pushPose()

        val blockState = blockEntity.blockState
        val modelRenderer = Minecraft.getInstance().blockRenderer.modelRenderer

        matrix.pushPose()
        val model = getModel(blockEntity)
        val buffer = buffers.getBuffer(renderType(texture))
        val height = if (blockEntity.hasLevel()) blockEntity.getColumnHeight() else 4
        for (i in 0 until height) {
            modelRenderer.renderModel(matrix.last(), buffer, blockState, model, 1F, 1F, 1F, light, overlay, EmptyModelData.INSTANCE)
            matrix.translate(0.0, -1.0, 0.0)
        }
        matrix.popPose()

        val lid = blockState.getValue(RBMKBaseBlock.LID_TYPE)
        renderLid(blockEntity, height, lid, modelRenderer, blockState, partials, matrix, buffers, light, overlay)

        renderExtra(blockEntity, height, partials, matrix, buffers, light, overlay)

        matrix.popPose()
    }

    open fun renderLid(blockEntity: T, height: Int, lidType: RBMKBaseBlock.LidType, modelRenderer: ModelBlockRenderer, blockState: BlockState, partials: Float, matrix: PoseStack, buffers: MultiBufferSource, light: Int, overlay: Int) {
        if (lidType.isLid()) {
            matrix.pushPose()

            val renderType = if (lidType == RBMKBaseBlock.LidType.LEAD_GLASS) renderType(glassLidTexture) else renderType(texture)
            modelRenderer.renderModel(matrix.last(), buffers.getBuffer(renderType), blockState, getLidModel(blockEntity), 1F, 1F, 1F, light, overlay, EmptyModelData.INSTANCE)

            matrix.popPose()
        }
    }

    open fun renderExtra(blockEntity: T, height: Int, partials: Float, matrix: PoseStack, buffers: MultiBufferSource, light: Int, overlay: Int) {}

    override fun shouldRenderOffScreen(blockEntity: T): Boolean = true
    override fun getViewDistance() = 256
}
