package at.martinthedragon.nucleartech.screens

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.containers.LittleBoyContainer
import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent

class LittleBoyScreen(
    container: LittleBoyContainer,
    playerInventory: PlayerInventory,
    title: ITextComponent
) : ContainerScreen<LittleBoyContainer>(container, playerInventory, title) {
    private val texture = ResourceLocation(NuclearTech.MODID, "textures/gui/little_boy.png")

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
        minecraft!!.textureManager.bind(texture)
        blit(matrix, leftPos, topPos, 0, 0, xSize, ySize)

        val bombCompletion = menu.getBombCompletion()
        if (bombCompletion == 0) return
        if (bombCompletion shr 5 and 1 == 1) blit(matrix, leftPos + 142, topPos + 90, 176, 0, 16, 16)
        if (bombCompletion shr 4 and 1 == 1) blit(matrix, leftPos + 27, topPos + 87, 176, 16, 21, 22)
        if (bombCompletion shr 3 and 1 == 1) blit(matrix, leftPos + 27, topPos + 89, 176, 38, 21, 18)
        if (bombCompletion shr 2 and 1 == 1) blit(matrix, leftPos + 74, topPos + 94, 176, 57, 19, 8)
        if (bombCompletion shr 1 and 1 == 1) blit(matrix, leftPos + 92, topPos + 95, 176, 66, 12, 6)
        if (bombCompletion and 1 == 1) blit(matrix, leftPos + 107, topPos + 91, 176, 75, 16, 14)
    }
}
