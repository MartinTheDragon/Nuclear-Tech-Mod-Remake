package at.martinthedragon.nucleartech.rendering

import at.martinthedragon.nucleartech.math.toVector3f
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.vertex.IVertexBuilder
import net.minecraft.block.BlockState
import net.minecraft.client.renderer.BlockModelRenderer
import net.minecraft.client.renderer.model.BakedQuad
import net.minecraft.client.renderer.model.IBakedModel
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.Direction
import net.minecraft.util.math.vector.Vector4f
import net.minecraftforge.client.model.data.IModelData
import org.lwjgl.system.MemoryStack
import kotlin.experimental.and
import java.util.Random as JavaRandom

// Note: For it to work, it needs special values in the RenderType
@Suppress("unused")
fun BlockModelRenderer.renderModelAlpha(
    matrix: MatrixStack.Entry,
    builder: IVertexBuilder,
    blockState: BlockState?,
    model: IBakedModel,
    red: Float,
    green: Float,
    blue: Float,
    alpha: Float,
    light: Int,
    overlay: Int,
    modelData: IModelData
) {
    val random = JavaRandom()
    for (direction in Direction.values()) {
        random.setSeed(42L)
        renderQuadListAlpha(matrix, builder, red, green, blue, alpha, model.getQuads(blockState, direction, random, modelData), light, overlay)
    }
    random.setSeed(42L)
    renderQuadListAlpha(matrix, builder, red, green, blue, alpha, model.getQuads(blockState, null, random, modelData), light, overlay)
}

private fun renderQuadListAlpha(
    matrix: MatrixStack.Entry,
    builder: IVertexBuilder,
    red: Float,
    green: Float,
    blue: Float,
    alpha: Float,
    quads: List<BakedQuad>,
    light: Int,
    overlay: Int
) {
    for (quad in quads) {
        val redClamped: Float
        val greenClamped: Float
        val blueClamped: Float
        val alphaClamped: Float
        if (quad.isTinted) {
            redClamped = red.coerceIn(0F, 1F)
            greenClamped = green.coerceIn(0F, 1F)
            blueClamped = blue.coerceIn(0F, 1F)
            alphaClamped = alpha.coerceIn(0F, 1F)
        } else {
            redClamped = 1F
            greenClamped = 1F
            blueClamped = 1F
            alphaClamped = 1F
        }

        builder.putBulkDataAlpha(matrix, quad, redClamped, greenClamped, blueClamped, alphaClamped, light, overlay)
    }
}

fun IVertexBuilder.putBulkDataAlpha(
    matrix: MatrixStack.Entry,
    quad: BakedQuad,
    red: Float,
    green: Float,
    blue: Float,
    alpha: Float,
    light: Int,
    overlay: Int
) = putBulkDataAlpha(matrix, quad, floatArrayOf(1F, 1F, 1F, 1F), red, green, blue, alpha, intArrayOf(light, light, light, light), overlay, false)

fun IVertexBuilder.putBulkDataAlpha(
    matrix: MatrixStack.Entry,
    quad: BakedQuad,
    colorMultipler: FloatArray,
    red: Float,
    green: Float,
    blue: Float,
    alpha: Float,
    light: IntArray,
    overlay: Int,
    readExistingColor: Boolean
) {
    val vertices = quad.vertices
    val normal = quad.direction.normal.toVector3f()
    val matrixNewPose = matrix.pose()
    normal.transform(matrix.normal())

    val memoryStack = MemoryStack.stackPush()
    val byteBuffer = memoryStack.malloc(DefaultVertexFormats.BLOCK.vertexSize)
    val intBuffer = byteBuffer.asIntBuffer()

    for (i in 0 until vertices.size / 8) {
        intBuffer.clear()
        intBuffer.put(vertices, i * 8, 8)
        val x = byteBuffer.getFloat(0)
        val y = byteBuffer.getFloat(4)
        val z = byteBuffer.getFloat(8)
        val colorRed: Float
        val colorGreen: Float
        val colorBlue: Float
        if (readExistingColor) {
            val inputRed = (byteBuffer.get(12) and 0xFF.toByte()) / 255F
            val inputGreen = (byteBuffer.get(13) and 0xFF.toByte()) / 255F
            val inputBlue = (byteBuffer.get(14) and 0xFF.toByte()) / 255F
            colorRed = inputRed * colorMultipler[i] * red
            colorGreen = inputGreen * colorMultipler[i] * green
            colorBlue = inputBlue * colorMultipler[i] * blue
        } else {
            colorRed = colorMultipler[i] * red
            colorGreen = colorMultipler[i] * green
            colorBlue = colorMultipler[i] * blue
        }

        val bakedLight = applyBakedLighting(light[i], byteBuffer)
        val u = byteBuffer.getFloat(16)
        val v = byteBuffer.getFloat(20)
        val position = Vector4f(x, y, z, 1F)
        position.transform(matrixNewPose)
        applyBakedNormals(normal, byteBuffer, matrix.normal())
        vertex(
            position.x(), position.y(), position.z(),
            colorRed, colorGreen, colorBlue, alpha,
            u, v,
            overlay,
            bakedLight,
            normal.x(), normal.y(), normal.z()
        )
    }

    memoryStack.pop()
}
