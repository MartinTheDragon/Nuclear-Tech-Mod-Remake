package at.martinthedragon.nucleartech.block.entity.renderer.rbmk

import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKConsoleBlockEntity
import at.martinthedragon.nucleartech.block.entity.renderer.RotatedBlockEntityRenderer
import at.martinthedragon.nucleartech.rendering.SpecialModels
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.*
import com.mojang.math.Matrix4f
import com.mojang.math.Vector3f
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraftforge.client.model.renderable.SimpleRenderable
import kotlin.math.max
import kotlin.math.min

class RBMKConsoleRenderer(context: BlockEntityRendererProvider.Context) : RotatedBlockEntityRenderer<RBMKConsoleBlockEntity>(context) {
    private val font = context.font

    override fun getModel(blockEntity: RBMKConsoleBlockEntity) = SpecialModels.RBMK_CONSOLE.get()

    override fun renderRotated(blockEntity: RBMKConsoleBlockEntity, model: SimpleRenderable, partials: Float, matrix: PoseStack, buffers: MultiBufferSource, light: Int, overlay: Int) {
        super.renderRotated(blockEntity, model, partials, matrix, buffers, light, overlay)

        matrix.mulPose(Vector3f.YP.rotationDegrees(-90F))
        matrix.translate(0.0, 0.0, -0.5)

        RenderSystem.enableDepthTest()
        RenderSystem.setShader(GameRenderer::getPositionColorShader)
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
        val vertexBuilder = Tesselator.getInstance().builder
        vertexBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR)

        for ((index, column) in blockEntity.columns.withIndex()) {
            if (column == null) continue

            val y = -(index / 15) * 0.125F + 3.625F
            val z = -(index % 15) * 0.125F + 0.125F * 7F
            val matrix4f = matrix.last().pose()
            drawColumn(matrix4f, vertexBuilder, y, z, 0.65F + (index % 2) * 0.05F, column.data.getDouble("Heat") / column.data.getDouble("MaxHeat"))

            when (column.type) {
                RBMKConsoleBlockEntity.Column.Type.FUEL, RBMKConsoleBlockEntity.Column.Type.FUEL_REASIM -> drawFuel(matrix4f, vertexBuilder, y, z, column.data.getDouble("Enrichment"))
                RBMKConsoleBlockEntity.Column.Type.CONTROL -> drawControl(matrix4f, vertexBuilder, y, z, column.data.getDouble("RodLevel"))
                RBMKConsoleBlockEntity.Column.Type.CONTROL_AUTO -> drawControlAuto(matrix4f, vertexBuilder, y, z, column.data.getDouble("RodLevel"))
                else -> {}
            }
        }

        Tesselator.getInstance().end()
        RenderSystem.disableDepthTest()

        matrix.translate(-0.42, 3.5, -1.75)

        for ((index, screen) in blockEntity.screens.withIndex()) {
            matrix.pushPose()

            if (index % 2 == 0) matrix.translate(0.0, 0.0, 1.75 * 2)

            matrix.translate(0.0, -0.75 * (index / 2), 0.0)

            val text = screen.getDisplayText()
            val width = font.width(text)
            val height = font.lineHeight

            val scale = min(0.03F, 0.8F / max(width, 1))
            matrix.scale(scale, -scale, scale)
            matrix.mulPose(Vector3f.YP.rotationDegrees(90F))
            font.draw(matrix, text, -width / 2F, -height / 2F, 0x00FF00)

            matrix.popPose()
        }
    }

    private fun drawColumn(matrix4f: Matrix4f, buffer: VertexConsumer, y: Float, z: Float, color: Float, heat: Double) {
        val x = -0.3725F
        val width = 0.046875F // 0.0625 * 0.75
        val red = color + (1F - color) * heat.toFloat()
        buffer.vertex(matrix4f, x, y + width, z - width).color(red, color, color, 1F)/*.normal(1F, 0F, 0F)*/.endVertex()
        buffer.vertex(matrix4f, x, y + width, z + width).color(red, color, color, 1F)/*.normal(1F, 0F, 0F)*/.endVertex()
        buffer.vertex(matrix4f, x, y - width, z + width).color(red, color, color, 1F)/*.normal(1F, 0F, 0F)*/.endVertex()
        buffer.vertex(matrix4f, x, y - width, z - width).color(red, color, color, 1F)/*.normal(1F, 0F, 0F)*/.endVertex()
    }

    private fun drawFuel(matrix4f: Matrix4f, buffer: VertexConsumer, y: Float, z: Float, enrichment: Double) {
        drawDot(matrix4f, buffer, y, z, 0F, 0.25F + enrichment.toFloat() * 0.75F, 0F)
    }

    private fun drawControl(matrix4f: Matrix4f, buffer: VertexConsumer, y: Float, z: Float, level: Double) {
        drawDot(matrix4f, buffer, y, z, level.toFloat(), level.toFloat(), 0F)
    }

    private fun drawControlAuto(matrix4f: Matrix4f, buffer: VertexConsumer, y: Float, z: Float, level: Double) {
        drawDot(matrix4f, buffer, y, z, level.toFloat(), 0F, level.toFloat())
    }

    private fun drawDot(matrix4f: Matrix4f, buffer: VertexConsumer, y: Float, z: Float, r: Float, g: Float, b: Float) {
        val x = -0.3625F
        val width = 0.03125F
        val edge = 0.022097F

        buffer.vertex(matrix4f, x, y + width, z).color(r, g, b, 1F)/*.normal(1F, 0F, 0F)*/.endVertex()
        buffer.vertex(matrix4f, x, y + edge, z + edge).color(r, g, b, 1F)/*.normal(1F, 0F, 0F)*/.endVertex()
        buffer.vertex(matrix4f, x, y, z + width).color(r, g, b, 1F)/*.normal(1F, 0F, 0F)*/.endVertex()
        buffer.vertex(matrix4f, x, y - edge, z + edge).color(r, g, b, 1F)/*.normal(1F, 0F, 0F)*/.endVertex()

        buffer.vertex(matrix4f, x, y + edge, z - edge).color(r, g, b, 1F)/*.normal(1F, 0F, 0F)*/.endVertex()
        buffer.vertex(matrix4f, x, y + width, z).color(r, g, b, 1F)/*.normal(1F, 0F, 0F)*/.endVertex()
        buffer.vertex(matrix4f, x, y - edge, z - edge).color(r, g, b, 1F)/*.normal(1F, 0F, 0F)*/.endVertex()
        buffer.vertex(matrix4f, x, y, z - width).color(r, g, b, 1F)/*.normal(1F, 0F, 0F)*/.endVertex()

        buffer.vertex(matrix4f, x, y + width, z).color(r, g, b, 1F)/*.normal(1F, 0F, 0F)*/.endVertex()
        buffer.vertex(matrix4f, x, y - edge, z + edge).color(r, g, b, 1F)/*.normal(1F, 0F, 0F)*/.endVertex()
        buffer.vertex(matrix4f, x, y - width, z).color(r, g, b, 1F)/*.normal(1F, 0F, 0F)*/.endVertex()
        buffer.vertex(matrix4f, x, y - edge, z - edge).color(r, g, b, 1F)/*.normal(1F, 0F, 0F)*/.endVertex()
    }
}
