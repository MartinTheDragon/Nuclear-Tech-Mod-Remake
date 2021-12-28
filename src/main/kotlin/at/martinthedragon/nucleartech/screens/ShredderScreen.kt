package at.martinthedragon.nucleartech.screens

import at.martinthedragon.nucleartech.blocks.entities.ShredderBlockEntity
import at.martinthedragon.nucleartech.energy.EnergyFormatter
import at.martinthedragon.nucleartech.menus.ShredderMenu
import at.martinthedragon.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.entity.player.Inventory

class ShredderScreen(
    container: ShredderMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<ShredderMenu>(container, playerInventory, title) {
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
        RenderSystem.setShaderTexture(0, TEXTURE)
        blit(matrix, leftPos, topPos, 0, 0, xSize, ySize)

        val shreddingProgressScaled = menu.getShreddingProgress() * 33 / ShredderBlockEntity.SHREDDING_TIME
        if (shreddingProgressScaled > 0) {
            blit(matrix, leftPos + 64, topPos + 90, 177, 54, shreddingProgressScaled, 13)
        }

        val energyScaled = menu.getEnergy() * 88 / ShredderBlockEntity.MAX_ENERGY
        if (energyScaled > 0) {
            blit(matrix, leftPos + 8, topPos + 106 - energyScaled, 176, 160 - energyScaled, 16, energyScaled)
        }

        val leftBladeState = menu.getLeftBladeState()
        if (leftBladeState != 0) {
            val leftBladeTextureOffset = when (leftBladeState) {
                1 -> 0
                2 -> 18
                3 -> 36
                else -> 0
            }
            blit(matrix, leftPos + 43, topPos + 71, 176, leftBladeTextureOffset, 18, 18)
        }

        val rightBladeState = menu.getRightBladeState()
        if (rightBladeState != 0) {
            val rightBladeTextureOffset = when (rightBladeState) {
                1 -> 0
                2 -> 18
                3 -> 36
                else -> 0
            }
            blit(matrix, leftPos + 79, topPos + 71, 194, rightBladeTextureOffset, 18, 18)
        }
    }

    override fun renderTooltip(matrix: PoseStack, mouseX: Int, mouseY: Int) {
        super.renderTooltip(matrix, mouseX, mouseY)

        if (isHovering(8, 16, 16, 88, mouseX.toDouble(), mouseY.toDouble()))
            renderComponentTooltip(matrix,
                listOf(
                    TranslatableComponent("energy.nucleartech"),
                    TextComponent("${EnergyFormatter.formatEnergy(menu.getEnergy())}/${EnergyFormatter.formatEnergy(ShredderBlockEntity.MAX_ENERGY)} HE")
                ), mouseX, mouseY, font
            )
    }

    companion object {
        val TEXTURE = ntm("textures/gui/shredder.png")
    }
}
