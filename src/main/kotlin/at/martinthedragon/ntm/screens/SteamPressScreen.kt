package at.martinthedragon.ntm.screens

import at.martinthedragon.ntm.Main
import at.martinthedragon.ntm.containers.PressContainer
import at.martinthedragon.ntm.tileentities.SteamPressTopTileEntity
import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent

// TODO recipe book
class SteamPressScreen(
    container: PressContainer,
    playerInventory: PlayerInventory,
    title: ITextComponent
) : ContainerScreen<PressContainer>(container, playerInventory, title) {
    private val texture = ResourceLocation(Main.MODID, "textures/gui/steam_press.png")

    init {
        imageWidth = 176
        imageHeight = 166
    }

    override fun render(matrix: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrix)
        super.render(matrix, mouseX, mouseY, partialTicks)
        renderTooltip(matrix, mouseX, mouseY)
    }

    override fun renderBg(matrixStack: MatrixStack, partialTicks: Float, x: Int, y: Int) {
        minecraft!!.textureManager.bind(texture)
        blit(matrixStack, (width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize)

        val powerPos = menu.getPower() * 12 / SteamPressTopTileEntity.maxPower
        blit(matrixStack, leftPos + 25, topPos + 16, 176, 14 + 18 * powerPos, 18, 18)

        val burnProgress = menu.getBurnProgress()
        blit(matrixStack, leftPos + 27, topPos + 49 - burnProgress, 176, 13 - burnProgress, 13, burnProgress)

        if (menu.isPressing()) {
            val pressProgress = menu.getPressProgress() * 16 / SteamPressTopTileEntity.pressTotalTime
            blit(matrixStack, leftPos + 79, topPos + 35, 194, 0, 18, pressProgress)
        }
    }
}
