package at.martinthedragon.nucleartech.screen

import at.martinthedragon.nucleartech.block.entity.BlastFurnaceBlockEntity
import at.martinthedragon.nucleartech.menu.BlastFurnaceMenu
import at.martinthedragon.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.entity.player.Inventory

class BlastFurnaceScreen(
    container: BlastFurnaceMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<BlastFurnaceMenu>(container, playerInventory, title) {
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
        RenderSystem.setShaderTexture(0, TEXTURE)
        blit(matrixStack, (width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize)

        if (menu.getBurnTime() > 0) {
            val burnTime = menu.getBurnTime() * 52 / BlastFurnaceBlockEntity.MAX_BURN_TIME
            blit(matrixStack, leftPos + 44, topPos + 70 - burnTime, 201, 53 - burnTime, 16, burnTime)
        }

        val blastProgress = menu.getBlastProgress() * 24 / BlastFurnaceBlockEntity.MAX_BLAST_TIME
        blit(matrixStack, leftPos + 101, topPos + 35, 176, 14, blastProgress + 1, 17)

        if (menu.getBurnTime() > 0 && menu.canBlast()) {
            blit(matrixStack, leftPos + 63, topPos + 37, 176, 0, 14, 14)
        }
    }

    override fun renderTooltip(matrixStack: PoseStack, mouseX: Int, mouseY: Int) {
        super.renderTooltip(matrixStack, mouseX, mouseY)

        if (isHovering(44, 18, 16, 52, mouseX.toDouble(), mouseY.toDouble()))
            renderComponentTooltip(matrixStack, listOf(TextComponent("${menu.getBurnTime()}/${BlastFurnaceBlockEntity.MAX_BURN_TIME}")), mouseX, mouseY, font)
    }

    companion object {
        val TEXTURE = ntm("textures/gui/blast_furnace.png")
    }
}
