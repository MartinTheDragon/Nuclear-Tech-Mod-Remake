package at.martinthedragon.nucleartech.screens

import at.martinthedragon.nucleartech.blocks.entities.CombustionGeneratorBlockEntity
import at.martinthedragon.nucleartech.energy.EnergyFormatter
import at.martinthedragon.nucleartech.menus.CombustionGeneratorMenu
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.rendering.renderGuiFluid
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.level.material.Fluids

class CombustionGeneratorScreen(
    container: CombustionGeneratorMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<CombustionGeneratorMenu>(container, playerInventory, title) {
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
        blit(matrixStack, leftPos, topPos, 0, 0, xSize, ySize)

        val burnProgress = menu.getBurnProgress()
        blit(matrixStack, leftPos + 82, topPos + 50 - burnProgress, 192, 14 - burnProgress, 14, burnProgress)

        if (menu.getEnergy() > 0) {
            val energyScaled = menu.getEnergy() * 52 / CombustionGeneratorBlockEntity.MAX_ENERGY
            blit(matrixStack, leftPos + 152, topPos + 69 - energyScaled, 176, 52 - energyScaled, 16, energyScaled)
        }

        if (menu.getWaterLevel() > 0) {
            val fluidLevelScaled = menu.getWaterLevel() * 52 / CombustionGeneratorBlockEntity.MAX_WATER
            renderGuiFluid(matrixStack, leftPos + 8, topPos + 69, 16, fluidLevelScaled, blitOffset, Fluids.WATER.attributes)
        }
    }

    override fun renderTooltip(matrixStack: PoseStack, mouseX: Int, mouseY: Int) {
        super.renderTooltip(matrixStack, mouseX, mouseY)

        if (isHovering(80, 36, 16, 16, mouseX.toDouble(), mouseY.toDouble()))
            renderComponentTooltip(matrixStack, listOf(TextComponent("${menu.getBurnTime() / 20}s")), mouseX, mouseY, font)
        if (isHovering(8, 17, 16, 52, mouseX.toDouble(), mouseY.toDouble()))
            renderComponentTooltip(matrixStack,
                listOf(
                    TranslatableComponent("block.minecraft.water"),
                    TextComponent("${menu.getWaterLevel()}/${CombustionGeneratorBlockEntity.MAX_WATER} mB")
                ), mouseX, mouseY, font
            )
        if (isHovering(152, 17, 16, 52, mouseX.toDouble(), mouseY.toDouble()))
            renderComponentTooltip(matrixStack,
                listOf(
                    TranslatableComponent("energy.nucleartech"),
                    TextComponent("${EnergyFormatter.formatEnergy(menu.getEnergy())}/${EnergyFormatter.formatEnergy(CombustionGeneratorBlockEntity.MAX_ENERGY)} HE") // TODO make this somehow switch between HE and FE
                ), mouseX, mouseY, font
            )
    }

    companion object {
        val TEXTURE = ntm("textures/gui/combustion_generator.png")
    }
}
