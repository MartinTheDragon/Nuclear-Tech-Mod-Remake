package at.martinthedragon.nucleartech.block.entity.renderer

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.block.entity.PumpjackBlockEntity
import at.martinthedragon.nucleartech.rendering.SpecialModels
import com.google.common.collect.ImmutableMap
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import com.mojang.math.Vector3f
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.world.phys.Vec3
import net.minecraftforge.client.model.renderable.MultipartTransforms
import net.minecraftforge.client.model.renderable.SimpleRenderable
import kotlin.math.sin

class PumpjackRenderer(context: BlockEntityRendererProvider.Context) : RotatedBlockEntityRenderer<PumpjackBlockEntity>(context) {
    override fun getModel(blockEntity: PumpjackBlockEntity) = SpecialModels.PUMPJACK.get()

    override fun renderRotated(blockEntity: PumpjackBlockEntity, model: SimpleRenderable, partials: Float, matrix: PoseStack, buffers: MultiBufferSource, light: Int, overlay: Int) {
        val rotation = if (blockEntity.canProgress)
            blockEntity.rotO + (blockEntity.rot - blockEntity.rotO) * partials
        else blockEntity.rot.toFloat()

        matrix.pushPose()
        if (!NuclearTech.polaroidBroken) matrix.setIdentity()

        matrix.pushPose()
        matrix.translate(0.0, 1.5, -5.0)
        matrix.mulPose(Vector3f.XP.rotationDegrees(rotation - 90F))
        matrix.translate(0.0, -1.5, 5.0)
        val rotorMatrix = matrix.last().pose()
        matrix.popPose()

        matrix.pushPose()
        matrix.translate(0.0, 3.5, -3.0)
        matrix.mulPose(Vector3f.XP.rotationDegrees((Math.toDegrees(sin(Math.toRadians(rotation.toDouble()))) * .25).toFloat()))
        matrix.translate(0.0, -3.5, 3.0)
        val headMatrix = matrix.last().pose()
        matrix.popPose()

        matrix.pushPose()
        matrix.translate(0.0, -sin(Math.toRadians(rotation.toDouble())), 0.0)
        val carriageMatrix = matrix.last().pose()
        matrix.popPose()

        matrix.popPose()

        val transforms = MultipartTransforms.of(ImmutableMap.copyOf(mapOf(
            "Rotor" to rotorMatrix,
            "Head" to headMatrix,
            "Carriage" to carriageMatrix
        )))

        model.render(matrix, buffers, renderType, light, overlay, partials, transforms)

        matrix.pushPose()
        matrix.translate(.5, .0, .5)

        RenderSystem.setShader(GameRenderer::getPositionColorShader)
//        Minecraft.getInstance().gameRenderer.lightTexture().turnOnLightLayer() // TODO fix light layer rendering
        RenderSystem.enableDepthTest()
        RenderSystem.disableCull()

        val poseStack = RenderSystem.getModelViewStack().apply {
            pushPose()
            mulPoseMatrix(matrix.last().pose())
        }
        RenderSystem.applyModelViewMatrix()

        val backPos = Vec3(0.0, 0.0, -2.0).xRot((-sin(Math.toRadians(rotation.toDouble())) * .25).toFloat())
        val rot = Vec3(0.0, 0.5, 0.0).xRot(-(Math.toRadians(rotation - 90.0).toFloat()))

        val vertexBuffer = Tesselator.getInstance().builder
        vertexBuffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR)

        val dist = 0.03125

        val colorMiddleGray = 0xFF080808u.toInt()
        val colorDarkGray = 0xFF333333u.toInt()

        RenderSystem.setShaderColor(.5F, .5F, .5F, 1F)

        vertexBuffer.vertex(-0.53125, 1.5 + rot.y, -5.5 + rot.z - 0.0625).color(colorMiddleGray).endVertex()
        vertexBuffer.vertex(-0.53125, 1.5 + rot.y, -5.5 + rot.z + 0.0625).color(colorMiddleGray).endVertex()
        vertexBuffer.vertex(-0.53125, 3.5 + backPos.y, -3.5 + backPos.z - 0.0625).color(colorMiddleGray).endVertex()
        vertexBuffer.vertex(-0.53125, 3.5 + backPos.y, -3.5 + backPos.z + 0.0625).color(colorMiddleGray).endVertex()

        vertexBuffer.vertex(0.53125, 1.5 + rot.y, -5.5F + rot.z - 0.0625F).color(colorMiddleGray).endVertex()
        vertexBuffer.vertex(0.53125, 1.5 + rot.y, -5.5F + rot.z + 0.0625F).color(colorMiddleGray).endVertex()
        vertexBuffer.vertex(0.53125, 3.5 + backPos.y, -3.5F + backPos.z - 0.0625F).color(colorMiddleGray).endVertex()
        vertexBuffer.vertex(0.53125, 3.5 + backPos.y, -3.5F + backPos.z + 0.0625F).color(colorMiddleGray).endVertex()

        RenderSystem.setShaderColor(.2F, .2F, .2F, 1F)

        val width = .25
        val height = -sin(Math.toRadians(rotation.toDouble()))

        val pRot = -(sin(Math.toRadians(rotation.toDouble())) * .25).toFloat()
        val frontPos = Vec3(0.0, 0.0, 1.0).xRot(pRot)
        val cutlet = 360.0 / 32.0

        for (i in -1..1 step 2) {
            var frontRad = Vec3(0.0, 0.0, 2.5 + dist).xRot(pRot).xRot(-(Math.toRadians(cutlet * -3)).toFloat())

            for (j in 0 until 4) {
                var sumY = frontPos.y + frontRad.y
                var sumZ = if (frontRad.y < 0) 3.5 + dist * .5 else frontPos.z + frontRad.z

                vertexBuffer.vertex((width - dist) * i, 3.5 + sumY, -3.5 + sumZ).color(colorDarkGray).endVertex()
                vertexBuffer.vertex((width + dist) * i, 3.5 + sumY, -3.5 + sumZ).color(colorDarkGray).endVertex()

                frontRad = frontRad.xRot(-(Math.toRadians(cutlet)).toFloat())
                sumY = frontPos.y + frontRad.y
                sumZ = if (frontRad.y < 0) 3.5 + dist * .5 else frontPos.z + frontRad.z

                vertexBuffer.vertex((width + dist) * i, 3.5 + sumY, -3.5 + sumZ).color(colorDarkGray).endVertex()
                vertexBuffer.vertex((width - dist) * i, 3.5 + sumY, -3.5 + sumZ).color(colorDarkGray).endVertex()
            }

            val sumY = frontPos.y + frontRad.y
            val sumZ = if (frontRad.y < 0) 3.5 + dist * .5 else frontPos.z + frontRad.z

            vertexBuffer.vertex((width + dist) * i, 3.5 + sumY, -3.5 + sumZ).color(colorDarkGray).endVertex()
            vertexBuffer.vertex((width - dist) * i, 3.5 + sumY, -3.5 + sumZ).color(colorDarkGray).endVertex()

            vertexBuffer.vertex((width - dist) * i, 2 + height, 0.0).color(colorDarkGray).endVertex()
            vertexBuffer.vertex((width + dist) * i, 2 + height, 0.0).color(colorDarkGray).endVertex()
        }

        vertexBuffer.vertex(dist, height + 1.5, dist).color(colorDarkGray).endVertex()
        vertexBuffer.vertex(-dist, height + 1.5, -dist).color(colorDarkGray).endVertex()
        vertexBuffer.vertex(-dist, 0.75, -dist).color(colorDarkGray).endVertex()
        vertexBuffer.vertex(dist, 0.75, dist).color(colorDarkGray).endVertex()
        vertexBuffer.vertex(-dist, height + 1.5, dist).color(colorDarkGray).endVertex()
        vertexBuffer.vertex(dist, height + 1.5, -dist).color(colorDarkGray).endVertex()
        vertexBuffer.vertex(dist, 0.75, -dist).color(colorDarkGray).endVertex()
        vertexBuffer.vertex(-dist, 0.75, dist).color(colorDarkGray).endVertex()

        Tesselator.getInstance().end()
        poseStack.popPose()
        RenderSystem.applyModelViewMatrix()
        RenderSystem.enableCull()
//        Minecraft.getInstance().gameRenderer.lightTexture().turnOffLightLayer()

        matrix.popPose()
    }
}
