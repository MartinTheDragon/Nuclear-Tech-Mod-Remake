package at.martinthedragon.nucleartech.screen.rbmk

import at.martinthedragon.nucleartech.menu.rbmk.RBMKManualControlMenu
import at.martinthedragon.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.player.Inventory

class RBMKManualControlScreen(
    menu: RBMKManualControlMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<RBMKManualControlMenu>(menu, playerInventory, title) {
    private val texture = ntm("textures/gui/rbmk/manual_control.png")

    init {
        imageWidth = 176
        imageHeight = 186
        inventoryLabelY = imageHeight - 94
    }

    // TODO actual buttons
    override fun mouseClicked(x: Double, y: Double, button: Int): Boolean {
        for (i in 0..4) {
            if (x.toInt() in guiLeft + 118..guiLeft + 148 && y.toInt() in guiTop + 26 + i * 11..guiTop + 36 + i * 11 && menu.clickMenuButton(minecraft!!.player!!, i)) {
                Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1F))
                minecraft!!.gameMode!!.handleInventoryButtonClick(menu.containerId, i)
                return true
            }

            if (x.toInt() in guiLeft + 28..guiLeft + 40 && y.toInt() in guiTop + 26 + i * 11..guiTop + 36 + i * 11 && menu.clickMenuButton(minecraft!!.player!!, i + 5)) {
                Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1F))
                minecraft!!.gameMode!!.handleInventoryButtonClick(menu.containerId, i + 5)
                return true
            }
        }

        return super.mouseClicked(x, y, button)
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

        val height = (56 * (1.0 - menu.blockEntity.rodLevel)).toInt()
        if (height > 0) blit(stack, guiLeft + 75, guiTop + 29, 176, 56 - height, 8, height)

        val color = menu.blockEntity.color
        if (color != null) blit(stack, guiLeft + 28, guiTop + 26 + color.ordinal * 11, 184, color.ordinal * 10, 12, 10)
    }

    override fun renderTooltip(matrix: PoseStack, mouseX: Int, mouseY: Int) {
        super.renderTooltip(matrix, mouseX, mouseY)
        if (isHovering(71, 29, 16, 56, mouseX.toDouble(), mouseY.toDouble()))
            renderComponentTooltip(matrix, listOf(TextComponent("${(menu.blockEntity.rodLevel * 100).toInt()}%")), mouseX, mouseY, font)
    }
}
