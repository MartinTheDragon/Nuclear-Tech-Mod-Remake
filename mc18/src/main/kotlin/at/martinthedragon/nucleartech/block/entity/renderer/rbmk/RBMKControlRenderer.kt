package at.martinthedragon.nucleartech.block.entity.renderer.rbmk

import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKControlBlockEntity
import at.martinthedragon.nucleartech.block.rbmk.RBMKBaseBlock
import at.martinthedragon.nucleartech.rendering.SpecialModels
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.block.ModelBlockRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.client.model.data.EmptyModelData

abstract class RBMKControlRenderer<T : RBMKControlBlockEntity>(context: BlockEntityRendererProvider.Context) : RBMKElementRenderer<T>(context) {
    override fun getModel(blockEntity: T) = SpecialModels.RBMK_CONTROL_ROD_COLUMN.get()
    override fun getLidModel(blockEntity: T) = SpecialModels.RBMK_CONTROL_ROD_LID.get()
    override val glassLidTexture: ResourceLocation get() = throw UnsupportedOperationException("RBMK control rods don't have glass lids")

    override fun renderLid(blockEntity: T, height: Int, lidType: RBMKBaseBlock.LidType, modelRenderer: ModelBlockRenderer, blockState: BlockState, partials: Float, matrix: PoseStack, buffers: MultiBufferSource, light: Int, overlay: Int) {
        matrix.pushPose()

        val level = blockEntity.lastLevel + (blockEntity.rodLevel - blockEntity.lastLevel) * partials
        matrix.translate(0.0, level, 0.0)
        modelRenderer.renderModel(matrix.last(), buffers.getBuffer(renderType(texture)), blockState, getLidModel(blockEntity), 1F, 1F, 1F, light, overlay, EmptyModelData.INSTANCE)

        matrix.popPose()
    }
}
