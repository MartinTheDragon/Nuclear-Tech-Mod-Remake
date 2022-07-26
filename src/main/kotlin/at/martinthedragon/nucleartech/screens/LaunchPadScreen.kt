package at.martinthedragon.nucleartech.screens

import at.martinthedragon.nucleartech.blocks.entities.LaunchPadBlockEntity
import at.martinthedragon.nucleartech.energy.EnergyFormatter
import at.martinthedragon.nucleartech.menus.LaunchPadMenu
import at.martinthedragon.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.entity.player.Inventory

class LaunchPadScreen(
    container: LaunchPadMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<LaunchPadMenu>(container, playerInventory, title) {
    private val texture = ntm("textures/gui/launch_pad.png")

    override fun render(matrix: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrix)
        super.render(matrix, mouseX, mouseY, partialTicks)
        renderTooltip(matrix, mouseX, mouseY)
    }

    override fun renderBg(stack: PoseStack, partials: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
        RenderSystem.setShaderTexture(0, texture)
        blit(stack, guiLeft, guiTop, 0, 0, xSize, ySize)

        if (menu.getEnergy() > 0) {
            val energyScaled = menu.getEnergy() * 160 / LaunchPadBlockEntity.MAX_ENERGY
            blit(stack, guiLeft + 8, guiTop + 53, 8, 166, energyScaled, 16)
        }
    }

    override fun renderTooltip(matrix: PoseStack, mouseX: Int, mouseY: Int) {
        super.renderTooltip(matrix, mouseX, mouseY)

        if (isHovering(8, 53, 160, 16, mouseX.toDouble(), mouseY.toDouble()))
            renderComponentTooltip(matrix,
                listOf(
                    TranslatableComponent("energy.nucleartech"),
                    TextComponent("${EnergyFormatter.formatEnergy(menu.getEnergy())}/${EnergyFormatter.formatEnergy(LaunchPadBlockEntity.MAX_ENERGY)} HE")
                ), mouseX, mouseY, font
            )
    }
}
