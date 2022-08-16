package at.martinthedragon.nucleartech.screen

import at.martinthedragon.nucleartech.block.entity.CombustionGeneratorBlockEntity
import at.martinthedragon.nucleartech.energy.EnergyFormatter
import at.martinthedragon.nucleartech.menu.CombustionGeneratorMenu
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
    private val texture = ntm("textures/gui/combustion_generator.png")

    init {
        imageWidth = 176
        imageHeight = 166
    }

    override fun render(matrix: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrix)
        super.render(matrix, mouseX, mouseY, partialTicks)
        renderTooltip(matrix, mouseX, mouseY)
    }

    override fun renderBg(matrix: PoseStack, partials: Float, x: Int, y: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        RenderSystem.setShaderTexture(0, texture)
        blit(matrix, guiLeft, guiTop, 0, 0, xSize, ySize)

        val combustionGenerator = menu.blockEntity
        val burnProgress = combustionGenerator.litTime * 14 / combustionGenerator.litDuration.coerceAtLeast(1)
        blit(matrix, guiLeft + 82, guiTop + 50 - burnProgress, 192, 14 - burnProgress, 14, burnProgress)

        if (combustionGenerator.energy > 0) {
            val energyScaled = combustionGenerator.energy * 52 / CombustionGeneratorBlockEntity.MAX_ENERGY
            blit(matrix, guiLeft + 152, guiTop + 69 - energyScaled, 176, 52 - energyScaled, 16, energyScaled)
        }

        if (combustionGenerator.water > 0) {
            val fluidLevelScaled = combustionGenerator.water * 52 / CombustionGeneratorBlockEntity.MAX_WATER
            renderGuiFluid(matrix, guiLeft + 8, guiTop + 69, 16, fluidLevelScaled, blitOffset, Fluids.WATER.attributes)
        }
    }

    override fun renderTooltip(matrixStack: PoseStack, mouseX: Int, mouseY: Int) {
        super.renderTooltip(matrixStack, mouseX, mouseY)

        if (isHovering(80, 36, 16, 16, mouseX.toDouble(), mouseY.toDouble()))
            renderComponentTooltip(matrixStack, listOf(TextComponent("${menu.blockEntity.litTime / 20}s")), mouseX, mouseY, font)
        if (isHovering(8, 17, 16, 52, mouseX.toDouble(), mouseY.toDouble()))
            renderComponentTooltip(matrixStack,
                listOf(
                    TranslatableComponent("block.minecraft.water"),
                    TextComponent("${menu.blockEntity.water}/${CombustionGeneratorBlockEntity.MAX_WATER} mB")
                ), mouseX, mouseY, font
            )
        if (isHovering(152, 17, 16, 52, mouseX.toDouble(), mouseY.toDouble()))
            renderComponentTooltip(matrixStack, EnergyFormatter.formatTooltip(menu.blockEntity.energy, CombustionGeneratorBlockEntity.MAX_ENERGY), mouseX, mouseY, font)
    }
}
