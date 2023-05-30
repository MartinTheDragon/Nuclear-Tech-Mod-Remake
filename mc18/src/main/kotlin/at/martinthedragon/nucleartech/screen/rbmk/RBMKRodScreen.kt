package at.martinthedragon.nucleartech.screen.rbmk

import at.martinthedragon.nucleartech.item.RBMKRodItem
import at.martinthedragon.nucleartech.menu.rbmk.RBMKRodMenu
import at.martinthedragon.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class RBMKRodScreen(
    menu: RBMKRodMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<RBMKRodMenu>(menu, playerInventory, title) {
    private val texture = ntm("textures/gui/rbmk/rod.png")

    init {
        imageWidth = 176
        imageHeight = 186
        inventoryLabelY = imageHeight - 94
    }

    override fun render(stack: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(stack)
        super.render(stack, mouseX, mouseY, partialTicks)
        renderTooltip(stack, mouseX, mouseY)
    }

    override fun renderBg(stack: PoseStack, partialTicks: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
        RenderSystem.setShaderTexture(0, texture)
        blit(stack, guiLeft, guiTop, 0, 0, xSize, ySize)

        val rod = menu.blockEntity
        val rodStack = rod.getItem(0)
        if (rodStack.item is RBMKRodItem) {
            blit(stack, guiLeft + 34, guiTop + 21, 176, 0, 18, 67)

            val depletion = 1.0 - RBMKRodItem.getEnrichment(rodStack)
            blit(stack, guiLeft + 34, guiTop + 21, 194, 0, 18, (depletion * 67).toInt())

            val xenon = RBMKRodItem.getPoisonLevel(rodStack)
            val xenonScaled = (xenon * 58).toInt()
            blit(stack, guiLeft + 126, guiTop + 82 - xenonScaled, 212, 58 - xenonScaled, 14, xenonScaled)
        }
    }
}
