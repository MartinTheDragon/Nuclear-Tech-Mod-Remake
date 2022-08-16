package at.martinthedragon.nucleartech.screen

import at.martinthedragon.nucleartech.block.entity.ShredderBlockEntity
import at.martinthedragon.nucleartech.energy.EnergyFormatter
import at.martinthedragon.nucleartech.menu.ShredderMenu
import at.martinthedragon.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class ShredderScreen(
    container: ShredderMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<ShredderMenu>(container, playerInventory, title) {
    private val texture = ntm("textures/gui/shredder.png")

    init {
        imageWidth = 176
        imageHeight = 222
        inventoryLabelY = imageHeight - 94
    }

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

        val shredder = menu.blockEntity
        val shreddingProgressScaled = shredder.progress * 33 / ShredderBlockEntity.SHREDDING_TIME
        if (shreddingProgressScaled > 0) {
            blit(matrix, leftPos + 64, topPos + 90, 177, 54, shreddingProgressScaled, 13)
        }

        val energyScaled = shredder.energy * 88 / ShredderBlockEntity.MAX_ENERGY
        if (energyScaled > 0) {
            blit(matrix, leftPos + 8, topPos + 106 - energyScaled, 176, 160 - energyScaled, 16, energyScaled)
        }

        val leftBladeState = menu.getLeftBladeState()
        if (leftBladeState != 0) blit(matrix, leftPos + 43, topPos + 71, 176, leftBladeState * 18 - 18, 18, 18)

        val rightBladeState = menu.getRightBladeState()
        if (rightBladeState != 0) blit(matrix, leftPos + 79, topPos + 71, 194, rightBladeState * 18 - 18, 18, 18)
    }

    override fun renderTooltip(matrix: PoseStack, mouseX: Int, mouseY: Int) {
        super.renderTooltip(matrix, mouseX, mouseY)

        if (isHovering(8, 16, 16, 88, mouseX.toDouble(), mouseY.toDouble()))
            renderComponentTooltip(matrix, EnergyFormatter.formatTooltip(menu.blockEntity.energy, ShredderBlockEntity.MAX_ENERGY), mouseX, mouseY, font)
    }
}
