package at.martinthedragon.nucleartech.screens

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.containers.CombustionGeneratorContainer
import at.martinthedragon.nucleartech.rendering.renderGuiFluid
import at.martinthedragon.nucleartech.tileentities.CombustionGeneratorTileEntity
import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.fluid.Fluids
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent

class CombustionGeneratorScreen(
    container: CombustionGeneratorContainer,
    playerInventory: PlayerInventory,
    title: ITextComponent
) : ContainerScreen<CombustionGeneratorContainer>(container, playerInventory, title) {
    init {
        imageWidth = 176
        imageHeight = 166
    }

    override fun render(matrix: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrix)
        super.render(matrix, mouseX, mouseY, partialTicks)
        renderTooltip(matrix, mouseX, mouseY)
    }

    override fun renderBg(matrixStack: MatrixStack, partialTicks: Float, x: Int, y: Int) {
        minecraft!!.textureManager.bind(TEXTURE)
        blit(matrixStack, leftPos, topPos, 0, 0, xSize, ySize)

        val burnProgress = menu.getBurnProgress()
        blit(matrixStack, leftPos + 82, topPos + 50 - burnProgress, 192, 14 - burnProgress, 14, burnProgress)

        if (menu.getEnergy() > 0) {
            val energyScaled = menu.getEnergy() * 52 / CombustionGeneratorTileEntity.MAX_ENERGY
            blit(matrixStack, leftPos + 152, topPos + 69 - energyScaled, 176, 52 - energyScaled, 16, energyScaled)
        }

        if (menu.getWaterLevel() > 0) {
            val fluidLevelScaled = menu.getWaterLevel() * 52 / CombustionGeneratorTileEntity.MAX_WATER
            renderGuiFluid(matrixStack, leftPos + 8, topPos + 69, 16, fluidLevelScaled, blitOffset, Fluids.WATER.attributes)
        }
    }

    override fun renderTooltip(matrixStack: MatrixStack, mouseX: Int, mouseY: Int) {
        super.renderTooltip(matrixStack, mouseX, mouseY)

        if (isHovering(80, 36, 16, 16, mouseX.toDouble(), mouseY.toDouble()))
            renderWrappedToolTip(matrixStack, listOf(StringTextComponent("${menu.getBurnTime() / 20}s")), mouseX, mouseY, font)
        if (isHovering(8, 17, 16, 52, mouseX.toDouble(), mouseY.toDouble()))
            renderWrappedToolTip(matrixStack,
                listOf(
                    StringTextComponent("Water"),
                    StringTextComponent("${menu.getWaterLevel()}/${CombustionGeneratorTileEntity.MAX_WATER} mB")
                ), mouseX, mouseY, font
            )
        if (isHovering(152, 17, 16, 52, mouseX.toDouble(), mouseY.toDouble()))
            renderWrappedToolTip(matrixStack,
                listOf(
                    StringTextComponent("Energy"),
                    StringTextComponent("${menu.getEnergy() / 4F}/${CombustionGeneratorTileEntity.MAX_ENERGY / 4F} HE") // TODO make this somehow switch between HE and FE
                ), mouseX, mouseY, font
            )
    }

    companion object {
        val TEXTURE = ResourceLocation(NuclearTech.MODID, "textures/gui/combustion_generator.png")
    }
}
