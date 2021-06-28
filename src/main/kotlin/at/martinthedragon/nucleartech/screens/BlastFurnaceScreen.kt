package at.martinthedragon.nucleartech.screens

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.containers.BlastFurnaceContainer
import at.martinthedragon.nucleartech.tileentities.BlastFurnaceTileEntity
import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent

class BlastFurnaceScreen(
    container: BlastFurnaceContainer,
    playerInventory: PlayerInventory,
    title: ITextComponent
) : ContainerScreen<BlastFurnaceContainer>(container, playerInventory, title) {
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
        minecraft!!.textureManager.bind(TEXTURE)
        blit(matrixStack, (width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize)

        if (menu.getBurnTime() > 0) {
            val burnTime = menu.getBurnTime() * 52 / BlastFurnaceTileEntity.maxBurnTime
            blit(matrixStack, leftPos + 44, topPos + 70 - burnTime, 201, 53 - burnTime, 16, burnTime)
        }

        val blastProgress = menu.getBlastProgress() * 24 / BlastFurnaceTileEntity.maxBlastTime
        blit(matrixStack, leftPos + 101, topPos + 35, 176, 14, blastProgress + 1, 17)

        if (menu.getBurnTime() > 0 && menu.canBlast()) {
            blit(matrixStack, leftPos + 63, topPos + 37, 176, 0, 14, 14)
        }
    }

    override fun renderTooltip(matrixStack: MatrixStack, mouseX: Int, mouseY: Int) {
        super.renderTooltip(matrixStack, mouseX, mouseY)

        if (isHovering(44, 18, 16, 52, mouseX.toDouble(), mouseY.toDouble()))
            renderWrappedToolTip(matrixStack, listOf(StringTextComponent("${menu.getBurnTime()}/${BlastFurnaceTileEntity.maxBurnTime}")), mouseX, mouseY, font)
    }

    companion object {
        val TEXTURE = ResourceLocation(NuclearTech.MODID, "textures/gui/blast_furnace.png")
    }
}
