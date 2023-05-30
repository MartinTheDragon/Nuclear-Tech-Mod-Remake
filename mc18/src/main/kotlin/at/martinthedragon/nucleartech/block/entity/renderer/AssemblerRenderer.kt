package at.martinthedragon.nucleartech.block.entity.renderer

import at.martinthedragon.nucleartech.block.entity.AssemblerBlockEntity
import at.martinthedragon.nucleartech.rendering.SpecialModels
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Vector3f
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.block.model.ItemTransforms
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraftforge.client.model.renderable.MultipartTransforms
import kotlin.math.PI
import kotlin.math.sin

class AssemblerRenderer(@Suppress("UNUSED_PARAMETER") context: BlockEntityRendererProvider.Context) : BlockEntityRenderer<AssemblerBlockEntity> {
    override fun render(assembler: AssemblerBlockEntity, partials: Float, matrix: PoseStack, buffers: MultiBufferSource, light: Int, overlay: Int) {
        val body = SpecialModels.ASSEMBLER_BODY.get()
        val cog = SpecialModels.ASSEMBLER_COG.get()
        val slider = SpecialModels.ASSEMBLER_SLIDER.get()
        val arm = SpecialModels.ASSEMBLER_ARM.get()

        matrix.pushPose()
        matrix.translate(.5, .0, .5)
        matrix.mulPose(Vector3f.YN.rotationDegrees(assembler.blockState.getValue(HorizontalDirectionalBlock.FACING).toYRot()))
        matrix.translate(-.5, .0, -.5)
        body.render(matrix, buffers, RenderType::entityCutoutNoCull, light, overlay, partials, MultipartTransforms.EMPTY)

        val recipe = assembler.recipe
        if (recipe != null) {
            matrix.pushPose()
            matrix.translate(.0, .875, .0)
            val stack = recipe.resultItem
            if (stack.item is BlockItem) {
                matrix.scale(.5F, .5F, .5F)
                matrix.translate(.0, -.875, -2.0)
            } else {
                matrix.mulPose(Vector3f.XP.rotationDegrees(-90F))
            }

            Minecraft.getInstance().itemRenderer.renderStatic(stack, ItemTransforms.TransformType.FIXED, light, overlay, matrix, buffers, assembler.blockState.getSeed(assembler.blockPos).toInt())
            matrix.popPose()
        }

        matrix.pushPose()
        if (assembler.canProgress) {
            val offset = ((assembler.renderTick % 100 + partials) * 10F).let { if (it > 500) 500 - (it - 500) else it }
            matrix.translate(offset * .003 - .75, .0, .0)
        }
        slider.render(matrix, buffers, RenderType::entityCutoutNoCull, light, overlay, partials, MultipartTransforms.EMPTY)

        if (assembler.canProgress) {
            val sway = sin((assembler.renderTick % 40 + partials) * .5 / PI)
            matrix.translate(.0, .0, sway * .3)
        }
        arm.render(matrix, buffers, RenderType::entityCutoutNoCull, light, overlay, partials, MultipartTransforms.EMPTY)
        matrix.popPose()

        val rotation = if (assembler.canProgress) (assembler.renderTick % 36 + partials) * 10F else 0F

        matrix.pushPose()
        matrix.translate(-.6, .75, 1.0625)
        matrix.mulPose(Vector3f.ZP.rotationDegrees(-rotation))
        cog.render(matrix, buffers, RenderType::entityCutoutNoCull, light, overlay, partials, MultipartTransforms.EMPTY)
        matrix.popPose()

        matrix.pushPose()
        matrix.translate(.6, .75, 1.0625)
        matrix.mulPose(Vector3f.ZP.rotationDegrees(rotation))
        cog.render(matrix, buffers, RenderType::entityCutoutNoCull, light, overlay, partials, MultipartTransforms.EMPTY)
        matrix.popPose()

        matrix.pushPose()
        matrix.translate(-.6, .75, -1.0625)
        matrix.mulPose(Vector3f.ZP.rotationDegrees(-rotation))
        cog.render(matrix, buffers, RenderType::entityCutoutNoCull, light, overlay, partials, MultipartTransforms.EMPTY)
        matrix.popPose()

        matrix.pushPose()
        matrix.translate(.6, .75, -1.0625)
        matrix.mulPose(Vector3f.ZP.rotationDegrees(rotation))
        cog.render(matrix, buffers, RenderType::entityCutoutNoCull, light, overlay, partials, MultipartTransforms.EMPTY)
        matrix.popPose()

        matrix.popPose()
    }

    override fun shouldRenderOffScreen(assembler: AssemblerBlockEntity) = true
    override fun getViewDistance() = 256
}
