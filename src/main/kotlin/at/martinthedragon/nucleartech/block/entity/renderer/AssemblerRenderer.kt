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
    override fun render(assembler: AssemblerBlockEntity, partialTicks: Float, matrix: PoseStack, bufferSource: MultiBufferSource, light: Int, overlay: Int) {
        val level = assembler.level ?: return

        val body = SpecialModels.ASSEMBLER_BODY.get()
        val cog = SpecialModels.ASSEMBLER_COG.get()
        val slider = SpecialModels.ASSEMBLER_SLIDER.get()
        val arm = SpecialModels.ASSEMBLER_ARM.get()

        val state = assembler.blockState

        matrix.pushPose()
        matrix.translate(.5, .0, .5)
        matrix.mulPose(Vector3f.YN.rotationDegrees(state.getValue(HorizontalDirectionalBlock.FACING).toYRot()))
        matrix.translate(-.5, .0, -.5)
        body.render(matrix, bufferSource, RenderType::entitySmoothCutout, light, overlay, partialTicks, MultipartTransforms.EMPTY)

        val recipeID = assembler.recipeID
        if (recipeID != null) try {
            matrix.pushPose()
            matrix.translate(.0, .875, .0)
            val recipe = level.recipeManager.byKey(recipeID).orElseThrow(::RuntimeException)
            val stack = recipe.resultItem
            if (stack.item is BlockItem) {
                matrix.scale(.5F, .5F, .5F)
                matrix.translate(.0, -.875, -2.0)
            } else {
                matrix.mulPose(Vector3f.XP.rotationDegrees(-90F))
            }

            Minecraft.getInstance().itemRenderer.renderStatic(stack, ItemTransforms.TransformType.FIXED, light, overlay, matrix, bufferSource, state.getSeed(assembler.blockPos).toInt())
            matrix.popPose()
        } catch (ignored: Exception) {
        }

        matrix.pushPose()
        if (assembler.canProgress) {
            val offset = ((assembler.renderTick % 100 + partialTicks) * 10F).let { if (it > 500) 500 - (it - 500) else it }
            matrix.translate(offset * .003 - .75, .0, .0)
        }
        slider.render(matrix, bufferSource, RenderType::entitySmoothCutout, light, overlay, partialTicks, MultipartTransforms.EMPTY)

        if (assembler.canProgress) {
            val sway = sin((assembler.renderTick % 40 + partialTicks) * .5 / PI)
            matrix.translate(.0, .0, sway * .3)
        }
        arm.render(matrix, bufferSource, RenderType::entitySmoothCutout, light, overlay, partialTicks, MultipartTransforms.EMPTY)
        matrix.popPose()

        val rotation = if (assembler.canProgress) (assembler.renderTick % 36 + partialTicks) * 10F else 0F

        matrix.pushPose()
        matrix.translate(-.6, .75, 1.0625)
        matrix.mulPose(Vector3f.ZP.rotationDegrees(-rotation))
        cog.render(matrix, bufferSource, RenderType::entitySmoothCutout, light, overlay, partialTicks, MultipartTransforms.EMPTY)
        matrix.popPose()

        matrix.pushPose()
        matrix.translate(.6, .75, 1.0625)
        matrix.mulPose(Vector3f.ZP.rotationDegrees(rotation))
        cog.render(matrix, bufferSource, RenderType::entitySmoothCutout, light, overlay, partialTicks, MultipartTransforms.EMPTY)
        matrix.popPose()

        matrix.pushPose()
        matrix.translate(-.6, .75, -1.0625)
        matrix.mulPose(Vector3f.ZP.rotationDegrees(-rotation))
        cog.render(matrix, bufferSource, RenderType::entitySmoothCutout, light, overlay, partialTicks, MultipartTransforms.EMPTY)
        matrix.popPose()

        matrix.pushPose()
        matrix.translate(.6, .75, -1.0625)
        matrix.mulPose(Vector3f.ZP.rotationDegrees(rotation))
        cog.render(matrix, bufferSource, RenderType::entitySmoothCutout, light, overlay, partialTicks, MultipartTransforms.EMPTY)
        matrix.popPose()

        matrix.popPose()
    }

    override fun shouldRenderOffScreen(assembler: AssemblerBlockEntity) = true
}
