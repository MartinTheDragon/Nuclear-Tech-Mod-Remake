package at.martinthedragon.nucleartech.screens

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.containers.ElectricFurnaceContainer
import at.martinthedragon.nucleartech.energy.EnergyFormatter
import at.martinthedragon.nucleartech.tileentities.ElectricFurnaceTileEntity
import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TranslationTextComponent

class ElectricFurnaceScreen(
    container: ElectricFurnaceContainer,
    playerInventory: PlayerInventory,
    title: ITextComponent
) : ContainerScreen<ElectricFurnaceContainer>(container, playerInventory, title) {
    init {
        imageWidth = 176
        imageHeight = 166
    }

    override fun render(matrix: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrix)
        super.render(matrix, mouseX, mouseY, partialTicks)
        renderTooltip(matrix, mouseX, mouseY)
    }

    override fun renderBg(matrix: MatrixStack, partialTicks: Float, x: Int, y: Int) {
        minecraft!!.textureManager.bind(TEXTURE)
        blit(matrix, leftPos, topPos, 0, 0, xSize, ySize)

        if (menu.getCookingProgress() > 0) {
            blit(matrix, leftPos + 57, topPos + 37, 176, 0, 14, 14)

            val cookingProgressScaled = menu.getCookingProgress() * 22 / ElectricFurnaceTileEntity.COOKING_TIME
            blit(matrix, leftPos + 80, topPos + 35, 177, 14, cookingProgressScaled, 16)
        }

        if (menu.getEnergy() > 0) {
            val energyScaled = menu.getEnergy() * 52 / ElectricFurnaceTileEntity.MAX_ENERGY
            blit(matrix, leftPos + 20, topPos + 69 - energyScaled, 200, 52 - energyScaled, 16, energyScaled)
        }
    }

    override fun renderTooltip(matrix: MatrixStack, mouseX: Int, mouseY: Int) {
        super.renderTooltip(matrix, mouseX, mouseY)

        if (isHovering(20, 17, 16, 52, mouseX.toDouble(), mouseY.toDouble()))
            renderWrappedToolTip(matrix,
                listOf(
                    TranslationTextComponent("energy.nucleartech"),
                    StringTextComponent("${EnergyFormatter.formatEnergy(menu.getEnergy())}/${EnergyFormatter.formatEnergy(ElectricFurnaceTileEntity.MAX_ENERGY)} HE")
                ), mouseX, mouseY, font
            )
    }

    companion object {
        val TEXTURE = ResourceLocation(NuclearTech.MODID, "textures/gui/electric_furnace.png")
    }
}
