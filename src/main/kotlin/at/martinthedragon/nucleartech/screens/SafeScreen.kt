package at.martinthedragon.nucleartech.screens

import at.martinthedragon.nucleartech.menus.SafeMenu
import at.martinthedragon.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class SafeScreen(
    container: SafeMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<SafeMenu>(container, playerInventory, title) {
    private val texture = ntm("textures/gui/safe.png")

    init {
        imageWidth = 176
        imageHeight = 168
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
        blit(matrixStack, leftPos, topPos, 0, 0, xSize, ySize)
    }
}
