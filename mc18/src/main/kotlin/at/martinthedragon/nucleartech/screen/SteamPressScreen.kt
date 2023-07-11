package at.martinthedragon.nucleartech.screen

import at.martinthedragon.nucleartech.block.entity.SteamPressBlockEntity
import at.martinthedragon.nucleartech.menu.PressMenu
import at.martinthedragon.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class SteamPressScreen(
    container: PressMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<PressMenu>(container, playerInventory, title) {
    private val texture = ntm("textures/gui/steam_press.png")

    init {
        imageWidth = 176
        imageHeight = 166
    }

    override fun render(matrix: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrix)
        super.render(matrix, mouseX, mouseY, partialTicks)
        renderTooltip(matrix, mouseX, mouseY)
    }

    override fun renderBg(matrixStack: PoseStack, partialTicks: Float, x: Int, y: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        RenderSystem.setShaderTexture(0, texture)
        blit(matrixStack, (width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize)

        val press = menu.blockEntity
        val powerPos = press.power * 12 / SteamPressBlockEntity.MAX_POWER
        blit(matrixStack, leftPos + 25, topPos + 16, 176, 14 + 18 * powerPos, 18, 18)

        val burnProgress = press.litTime * 13 / press.litDuration.coerceAtLeast(1)
        blit(matrixStack, leftPos + 27, topPos + 49 - burnProgress, 176, 13 - burnProgress, 13, burnProgress)

        if (press.progress > 0) {
            val pressProgress = press.progress * 16 / SteamPressBlockEntity.PRESS_TIME
            blit(matrixStack, leftPos + 79, topPos + 35, 194, 0, 18, pressProgress)
        }
    }
}
