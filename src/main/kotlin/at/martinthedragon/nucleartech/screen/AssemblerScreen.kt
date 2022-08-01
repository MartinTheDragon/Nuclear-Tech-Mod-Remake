package at.martinthedragon.nucleartech.screen

import at.martinthedragon.nucleartech.block.entity.AssemblerBlockEntity
import at.martinthedragon.nucleartech.energy.EnergyFormatter
import at.martinthedragon.nucleartech.menu.AssemblerMenu
import at.martinthedragon.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.entity.player.Inventory

class AssemblerScreen(
    container: AssemblerMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<AssemblerMenu>(container, playerInventory, title) {
    private val texture = ntm("textures/gui/assembler.png")

    init {
        imageWidth = 176
        imageHeight = 222
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

        val energyScaled = menu.getEnergy() * 52 / AssemblerBlockEntity.MAX_ENERGY
        if (energyScaled > 0) blit(stack, guiLeft + 116, guiTop + 70 - energyScaled, 176, 52 - energyScaled, 16, energyScaled)

        val progressScaled = menu.getProgress() * 83 / menu.getMaxProgress()
        if (progressScaled > 0) blit(stack, guiLeft + 45, guiTop + 82, 2, 222, progressScaled, 32)
    }

    override fun renderTooltip(matrix: PoseStack, mouseX: Int, mouseY: Int) {
        super.renderTooltip(matrix, mouseX, mouseY)

        if (isHovering(116, 18, 16, 52, mouseX.toDouble(), mouseY.toDouble()))
            renderComponentTooltip(matrix,
                listOf(
                    TranslatableComponent("energy.nucleartech"),
                    TextComponent("${EnergyFormatter.formatEnergy(menu.getEnergy())}/${EnergyFormatter.formatEnergy(AssemblerBlockEntity.MAX_ENERGY)} HE")
                ), mouseX, mouseY, font
            )
    }
}
