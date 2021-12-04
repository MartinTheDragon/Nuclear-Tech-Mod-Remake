package at.martinthedragon.nucleartech.rendering

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.world.inventory.InventoryMenu
import net.minecraftforge.fluids.FluidAttributes

// Adapted from Mekanism: https://github.com/mekanism/Mekanism/blob/807a081b149dd258e2d8475b8dfafd8b9bceb01f/src/main/java/mekanism/client/gui/GuiUtils.java#L178
fun renderGuiFluid(
    matrixStack: PoseStack,
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    blitOffset: Int,
    fluidAttributes: FluidAttributes
) {
    val sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidAttributes.stillTexture)
    RenderSystem.setShader(GameRenderer::getPositionTexShader)
    RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
    RenderSystem.setShaderTexture(0, sprite.atlas().location())
    val textureWidth = sprite.width
    val textureHeight = sprite.height
    val xTileCount = width / textureWidth
    val xRemainder = width - xTileCount * textureWidth
    val yTileCount = height / textureHeight
    val yRemainder = height - yTileCount * textureHeight
    val uMin = sprite.u0
    val uMax = sprite.u1
    val vMin = sprite.v0
    val vMax = sprite.v1
    val uDif = uMax - uMin
    val vDif = vMax - vMin
    RenderSystem.enableBlend()
    val fluidColor = fluidAttributes.color
    RenderSystem.setShaderColor(
        (fluidColor shr 16 and 0xFF) / 255F,
        (fluidColor shr 8 and 0xFF) / 255F,
        (fluidColor and 0xFF) / 255F,
        (fluidColor shr 24 and 0xFF) / 255F
    )
    val vertexBuffer = Tesselator.getInstance().builder
    vertexBuffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX)
    val matrix4f = matrixStack.last().pose()
    for (xTile in 0..xTileCount) {
        val tileWidth = if (xTile == xTileCount) xRemainder else textureWidth
        if (tileWidth == 0) break
        val tileX = x + xTile * textureWidth
        val maskRight = textureWidth - tileWidth
        val shiftedX = tileX + textureWidth - maskRight
        val uLocalDif = uDif * maskRight / textureWidth
        val uLocalMax = uMax - uLocalDif
        for (yTile in 0..yTileCount) {
            val tileHeight = if (yTile == yTileCount) yRemainder else textureHeight
            if (tileHeight == 0) break
            val tileY = y - (yTile + 1) * textureHeight
            val maskTop = textureHeight - tileHeight
            val vLocalDif = vDif * maskTop / textureHeight
            val vLocalMin = vMin + vLocalDif
            vertexBuffer.vertex(matrix4f, tileX.toFloat(), (tileY + textureHeight).toFloat(), blitOffset.toFloat()).uv(uMin, vMax).endVertex()
            vertexBuffer.vertex(matrix4f, shiftedX.toFloat(), (tileY + textureHeight).toFloat(), blitOffset.toFloat()).uv(uLocalMax, vMax).endVertex()
            vertexBuffer.vertex(matrix4f, shiftedX.toFloat(), (tileY + maskTop).toFloat(), blitOffset.toFloat()).uv(uLocalMax, vLocalMin).endVertex()
            vertexBuffer.vertex(matrix4f, tileX.toFloat(), (tileY + maskTop).toFloat(), blitOffset.toFloat()).uv(uMin, vLocalMin).endVertex()
        }
    }
    Tesselator.getInstance().end()
    RenderSystem.disableBlend()
    RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
}
