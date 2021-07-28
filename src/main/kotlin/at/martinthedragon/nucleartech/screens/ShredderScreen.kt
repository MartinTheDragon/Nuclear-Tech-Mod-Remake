package at.martinthedragon.nucleartech.screens

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.containers.ShredderContainer
import at.martinthedragon.nucleartech.energy.EnergyFormatter
import at.martinthedragon.nucleartech.tileentities.ShredderTileEntity
import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TranslationTextComponent

class ShredderScreen(
    container: ShredderContainer,
    playerInventory: PlayerInventory,
    title: ITextComponent
) : ContainerScreen<ShredderContainer>(container, playerInventory, title) {
    init {
        imageWidth = 176
        imageHeight = 222
        inventoryLabelY = imageHeight - 94
    }

    override fun render(matrix: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrix)
        super.render(matrix, mouseX, mouseY, partialTicks)
        renderTooltip(matrix, mouseX, mouseY)
    }

    override fun renderBg(matrix: MatrixStack, partialTicks: Float, mouseX: Int, mouseY: Int) {
        minecraft!!.textureManager.bind(TEXTURE)
        blit(matrix, leftPos, topPos, 0, 0, xSize, ySize)

        val shreddingProgressScaled = menu.getShreddingProgress() * 33 / ShredderTileEntity.SHREDDING_TIME
        if (shreddingProgressScaled > 0) {
            blit(matrix, leftPos + 64, topPos + 90, 177, 54, shreddingProgressScaled, 13)
        }

        val energyScaled = menu.getEnergy() * 88 / ShredderTileEntity.MAX_ENERGY
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

    override fun renderTooltip(matrix: MatrixStack, mouseX: Int, mouseY: Int) {
        super.renderTooltip(matrix, mouseX, mouseY)

        if (isHovering(8, 16, 16, 88, mouseX.toDouble(), mouseY.toDouble()))
            renderWrappedToolTip(matrix,
                listOf(
                    TranslationTextComponent("energy.nucleartech"),
                    StringTextComponent("${EnergyFormatter.formatEnergy(menu.getEnergy())}/${EnergyFormatter.formatEnergy(ShredderTileEntity.MAX_ENERGY)} HE")
                ), mouseX, mouseY, font
            )
    }

    companion object {
        val TEXTURE = ResourceLocation(NuclearTech.MODID, "textures/gui/shredder.png")
    }
}
