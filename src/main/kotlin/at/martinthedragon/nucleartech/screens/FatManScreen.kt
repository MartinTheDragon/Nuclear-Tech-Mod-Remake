package at.martinthedragon.nucleartech.screens

import at.martinthedragon.nucleartech.menus.FatManMenu
import at.martinthedragon.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class FatManScreen(
    container: FatManMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<FatManMenu>(container, playerInventory, title) {
    private val texture = ntm("textures/gui/fat_man.png")

    override fun render(matrix: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrix)
        super.render(matrix, mouseX, mouseY, partialTicks)
        renderTooltip(matrix, mouseX, mouseY)
    }

    override fun renderBg(matrix: PoseStack, partialTicks: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        RenderSystem.setShaderTexture(0, texture)
        blit(matrix, leftPos, topPos, 0, 0, xSize, ySize)

        val bombCompletion = menu.getBombCompletion()
        if (bombCompletion shr 5 and 1 == 1) blit(matrix, leftPos + 82, topPos + 19, 176, 0, 24, 24)
        if (bombCompletion shr 4 and 1 == 1) blit(matrix, leftPos + 106, topPos + 19, 200, 0, 24, 24)
        if (bombCompletion shr 3 and 1 == 1) blit(matrix, leftPos + 82, topPos + 43, 176, 24, 24, 24)
        if (bombCompletion shr 2 and 1 == 1) blit(matrix, leftPos + 106, topPos + 43, 200, 24, 24, 24)
        if (bombCompletion shr 6 and 1 == 1) blit(matrix, leftPos + 134, topPos + 35, 176, 48, 16, 16)
    }
}
