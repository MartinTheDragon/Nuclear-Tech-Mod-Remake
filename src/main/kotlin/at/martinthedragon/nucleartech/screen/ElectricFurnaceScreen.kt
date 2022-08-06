package at.martinthedragon.nucleartech.screen

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.block.entity.ElectricFurnaceBlockEntity
import at.martinthedragon.nucleartech.energy.EnergyFormatter
import at.martinthedragon.nucleartech.menu.ElectricFurnaceMenu
import at.martinthedragon.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.entity.player.Inventory

class ElectricFurnaceScreen(
    container: ElectricFurnaceMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<ElectricFurnaceMenu>(container, playerInventory, title) {
    init {
        imageWidth = 176
        imageHeight = 166
    }

    override fun render(matrix: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrix)
        super.render(matrix, mouseX, mouseY, partialTicks)
        renderTooltip(matrix, mouseX, mouseY)
    }

    override fun renderBg(matrix: PoseStack, partialTicks: Float, x: Int, y: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        RenderSystem.setShaderTexture(0, TEXTURE)
        blit(matrix, leftPos, topPos, 0, 0, xSize, ySize)

        if (menu.getCookingProgress() > 0) {
            blit(matrix, leftPos + 57, topPos + 37, 176, 0, 14, 14)

            val cookingProgressScaled = menu.getCookingProgress() * 22 / ElectricFurnaceBlockEntity.COOKING_TIME
            blit(matrix, leftPos + 80, topPos + 35, 177, 14, cookingProgressScaled, 16)
        }

        if (menu.getEnergy() > 0) {
            val energyScaled = menu.getEnergy() * 52 / ElectricFurnaceBlockEntity.MAX_ENERGY
            blit(matrix, leftPos + 20, topPos + 69 - energyScaled, 200, 52 - energyScaled, 16, energyScaled)
        }
    }

    override fun renderTooltip(matrix: PoseStack, mouseX: Int, mouseY: Int) {
        super.renderTooltip(matrix, mouseX, mouseY)

        if (isHovering(20, 17, 16, 52, mouseX.toDouble(), mouseY.toDouble()))
            renderComponentTooltip(matrix,
                listOf(
                    LangKeys.ENERGY.get(),
                    TextComponent("${EnergyFormatter.formatEnergy(menu.getEnergy())}/${EnergyFormatter.formatEnergy(ElectricFurnaceBlockEntity.MAX_ENERGY)} HE")
                ), mouseX, mouseY, font
            )
    }

    companion object {
        val TEXTURE = ntm("textures/gui/electric_furnace.png")
    }
}
