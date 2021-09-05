package at.martinthedragon.nucleartech.screens

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.containers.FatManContainer
import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent

class FatManScreen(
    container: FatManContainer,
    playerInventory: PlayerInventory,
    title: ITextComponent
) : ContainerScreen<FatManContainer>(container, playerInventory, title) {
    private val texture = ResourceLocation(NuclearTech.MODID, "textures/gui/fat_man.png")

    override fun render(matrix: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrix)
        super.render(matrix, mouseX, mouseY, partialTicks)
        renderTooltip(matrix, mouseX, mouseY)
    }

    override fun renderBg(matrix: MatrixStack, partialTicks: Float, mouseX: Int, mouseY: Int) {
        minecraft!!.textureManager.bind(texture)
        blit(matrix, leftPos, topPos, 0, 0, xSize, ySize)

        val bombCompletion = menu.getBombCompletion()
        if (bombCompletion shr 5 and 1 == 1) blit(matrix, leftPos + 82, topPos + 19, 176, 0, 24, 24)
        if (bombCompletion shr 4 and 1 == 1) blit(matrix, leftPos + 106, topPos + 19, 200, 0, 24, 24)
        if (bombCompletion shr 3 and 1 == 1) blit(matrix, leftPos + 82, topPos + 43, 176, 24, 24, 24)
        if (bombCompletion shr 2 and 1 == 1) blit(matrix, leftPos + 106, topPos + 43, 200, 24, 24, 24)
        if (bombCompletion shr 6 and 1 == 1) blit(matrix, leftPos + 134, topPos + 35, 176, 48, 16, 16)
    }
}
