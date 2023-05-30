package at.martinthedragon.nucleartech.block.entity.renderer.rbmk

import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKRodBlockEntity
import at.martinthedragon.nucleartech.block.rbmk.RBMKBaseBlock
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.rendering.NuclearRenderTypes
import at.martinthedragon.nucleartech.rendering.SpecialModels
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraftforge.client.model.renderable.MultipartTransforms
import kotlin.math.log10

open class RBMKRodRenderer(context: BlockEntityRendererProvider.Context) : RBMKElementRenderer<RBMKRodBlockEntity>(context) {
    override fun getModel(blockEntity: RBMKRodBlockEntity) = SpecialModels.RBMK_ROD_COLUMN.get()
    override val texture = ntm("textures/other/rbmk/rods.png")
    override val glassLidTexture = ntm("textures/other/rbmk/rods_glass.png")

    override fun renderExtra(blockEntity: RBMKRodBlockEntity, height: Int, partials: Float, matrix: PoseStack, buffers: MultiBufferSource, light: Int, overlay: Int) {
        matrix.pushPose()

        if (blockEntity.hasRod || !blockEntity.hasLevel()) {
            matrix.pushPose()

            val model = SpecialModels.RBMK_ROD_RODS.get()

            for (i in 0 until height) {
                model.render(matrix, buffers, renderType, light, overlay, partials, MultipartTransforms.EMPTY)
                matrix.translate(0.0, -1.0, 0.0)
            }

            matrix.popPose()
        }

        if (blockEntity.clientIsEmittingCherenkov && blockEntity.blockState.getValue(RBMKBaseBlock.LID_TYPE).seeThrough()) {
            matrix.pushPose()
            matrix.translate(0.5, 0.75, 0.5)

            val builder = buffers.getBuffer(NuclearRenderTypes.rbmkCherenkov)
            val matrix4f = matrix.last().pose()

            val intensity = (log10(blockEntity.clientCherenkovLevel) * 0.05).coerceIn(0.0, 0.2).toFloat()

            for (i in 0 until height * 4 - 1) {
                builder.vertex(matrix4f, -0.5F, -i * 0.25F, -0.5F).color(0.4F, 0.9F, 1F, intensity).endVertex()
                builder.vertex(matrix4f, -0.5F, -i * 0.25F, 0.5F).color(0.4F, 0.9F, 1F, intensity).endVertex()
                builder.vertex(matrix4f, 0.5F, -i * 0.25F, 0.5F).color(0.4F, 0.9F, 1F, intensity).endVertex()
                builder.vertex(matrix4f, 0.5F, -i * 0.25F, -0.5F).color(0.4F, 0.9F, 1F, intensity).endVertex()
            }

            matrix.popPose()
        }

        matrix.popPose()
    }
}
