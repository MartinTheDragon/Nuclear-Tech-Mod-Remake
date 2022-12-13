package at.martinthedragon.nucleartech.screen

import at.martinthedragon.nucleartech.block.entity.ElectricFurnaceBlockEntity
import at.martinthedragon.nucleartech.extensions.tooltipEnergyStorage
import at.martinthedragon.nucleartech.menu.ElectricFurnaceMenu
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.screen.widgets.UpgradeInfoWidget
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class ElectricFurnaceScreen(
    container: ElectricFurnaceMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<ElectricFurnaceMenu>(container, playerInventory, title) {
    private val texture = ntm("textures/gui/electric_furnace.png")

    init {
        imageWidth = 176
        imageHeight = 166
    }

    override fun init() {
        super.init()
        addRenderableWidget(UpgradeInfoWidget(guiLeft + 151, guiTop + 19, 8, 8, menu, this::renderComponentTooltip))
    }

    override fun render(matrix: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrix)
        super.render(matrix, mouseX, mouseY, partialTicks)
        renderTooltip(matrix, mouseX, mouseY)
    }

    override fun renderBg(matrix: PoseStack, partialTicks: Float, x: Int, y: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        RenderSystem.setShaderTexture(0, texture)
        blit(matrix, leftPos, topPos, 0, 0, xSize, ySize)

        val electricFurnace = menu.blockEntity
        if (electricFurnace.canProgress) {
            blit(matrix, leftPos + 56, topPos + 35, 176, 0, 14, 14)
        }

        val cookingProgressScaled = electricFurnace.progress * 22 / electricFurnace.maxProgress.coerceAtLeast(1)
        blit(matrix, leftPos + 80, topPos + 35, 177, 17, cookingProgressScaled, 16)

        if (electricFurnace.energy > 0) {
            val energyScaled = electricFurnace.energy * 52 / ElectricFurnaceBlockEntity.MAX_ENERGY
            blit(matrix, leftPos + 20, topPos + 69 - energyScaled, 200, 52 - energyScaled, 16, energyScaled)
        }
    }

    override fun renderTooltip(matrix: PoseStack, mouseX: Int, mouseY: Int) {
        super.renderTooltip(matrix, mouseX, mouseY)
        tooltipEnergyStorage(matrix, menu.blockEntity.energyStorage, 20, 17, 16, 52, mouseX, mouseY)
    }
}
