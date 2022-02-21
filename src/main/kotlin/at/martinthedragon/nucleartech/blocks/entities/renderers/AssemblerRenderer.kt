package at.martinthedragon.nucleartech.blocks.entities.renderers

import at.martinthedragon.nucleartech.blocks.entities.AssemblerBlockEntity
import at.martinthedragon.nucleartech.ntm
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
import net.minecraftforge.client.model.CompositeModel
import net.minecraftforge.client.model.data.EmptyModelData
import java.util.*
import kotlin.math.PI
import kotlin.math.sin

class AssemblerRenderer(@Suppress("UNUSED_PARAMETER") context: BlockEntityRendererProvider.Context) : BlockEntityRenderer<AssemblerBlockEntity> {
    private val assemblerModel = ntm("other/assembler")

    private val modelRenderer = Minecraft.getInstance().blockRenderer.modelRenderer
    private val itemRenderer = Minecraft.getInstance().itemRenderer
    private val modelManager = Minecraft.getInstance().modelManager

    override fun render(assembler: AssemblerBlockEntity, partialTicks: Float, matrix: PoseStack, bufferSource: MultiBufferSource, light: Int, overlay: Int) {
        val level = assembler.level ?: return

        val model = modelManager.getModel(assemblerModel) as? CompositeModel ?: return
        val body = model.getPart("body") ?: return // TODO missing model?
        val cog = model.getPart("cog") ?: return
        val slider = model.getPart("slider") ?: return
        val arm = model.getPart("arm") ?: return
        val state = assembler.blockState
        val pos = assembler.blockPos
        val random = Random()
        val seed = state.getSeed(pos)

        matrix.pushPose()
        matrix.translate(.5, .0, .5)
        matrix.mulPose(Vector3f.YN.rotationDegrees(state.getValue(HorizontalDirectionalBlock.FACING).toYRot()))
        matrix.translate(-.5, .0, -.5)
        modelRenderer.tesselateBlock(level, body, state, pos, matrix, bufferSource.getBuffer(RenderType.cutout()), false, random, seed, overlay, EmptyModelData.INSTANCE)

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

            itemRenderer.renderStatic(stack, ItemTransforms.TransformType.FIXED, light, overlay, matrix, bufferSource, seed.toInt())
            matrix.popPose()
        } catch (ignored: Exception) {}

        matrix.pushPose()
        if (assembler.isProgressing) {
            val offset = ((System.currentTimeMillis() % 5000).toInt() / 5).let { if (it > 500) 500 - (it - 500) else it }
            matrix.translate(offset * .003 - .75, .0, .0)
        }
        modelRenderer.tesselateBlock(level, slider, state, pos, matrix, bufferSource.getBuffer(RenderType.cutout()), false, random, seed, overlay, EmptyModelData.INSTANCE)

        if (assembler.isProgressing) {
            val sway = sin(System.currentTimeMillis() % 2000 * .5 / PI / 50)
            matrix.translate(.0, .0, sway * .3)
        }
        modelRenderer.tesselateBlock(level, arm, state, pos, matrix, bufferSource.getBuffer(RenderType.cutout()), false, random, seed, overlay, EmptyModelData.INSTANCE)
        matrix.popPose()

        val rotation = if (assembler.isProgressing) (System.currentTimeMillis() % (360 * 5)).toInt() / 5F else 0F

        matrix.pushPose()
        matrix.translate(-.6, .75, 1.0625)
        matrix.mulPose(Vector3f.ZP.rotationDegrees(-rotation))
        modelRenderer.tesselateWithoutAO(level, cog, state, pos, matrix, bufferSource.getBuffer(RenderType.cutout()), false, random, seed, overlay, EmptyModelData.INSTANCE)
        matrix.popPose()

        matrix.pushPose()
        matrix.translate(.6, .75, 1.0625)
        matrix.mulPose(Vector3f.ZP.rotationDegrees(rotation))
        modelRenderer.tesselateWithoutAO(level, cog, state, pos, matrix, bufferSource.getBuffer(RenderType.cutout()), false, random, seed, overlay, EmptyModelData.INSTANCE)
        matrix.popPose()

        matrix.pushPose()
        matrix.translate(-.6, .75, -1.0625)
        matrix.mulPose(Vector3f.ZP.rotationDegrees(-rotation))
        modelRenderer.tesselateWithoutAO(level, cog, state, pos, matrix, bufferSource.getBuffer(RenderType.cutout()), false, random, seed, overlay, EmptyModelData.INSTANCE)
        matrix.popPose()

        matrix.pushPose()
        matrix.translate(.6, .75, -1.0625)
        matrix.mulPose(Vector3f.ZP.rotationDegrees(rotation))
        modelRenderer.tesselateWithoutAO(level, cog, state, pos, matrix, bufferSource.getBuffer(RenderType.cutout()), false, random, seed, overlay, EmptyModelData.INSTANCE)
        matrix.popPose()

        matrix.popPose()
    }

    override fun shouldRenderOffScreen(assembler: AssemblerBlockEntity) = true
}
