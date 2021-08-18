package at.martinthedragon.nucleartech.screens

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.containers.SirenContainer
import at.martinthedragon.nucleartech.items.SirenTrack
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler

class SirenScreen(
        container: SirenContainer,
        playerInventory: PlayerInventory,
        title: ITextComponent
) : ContainerScreen<SirenContainer>(container, playerInventory, title) {
    private val texture = ResourceLocation(NuclearTech.MODID, "textures/gui/siren.png")

    init {
        imageWidth = 176
        imageHeight = 166
    }

    override fun renderBg(matrixStack: MatrixStack, partialTicks: Float, x: Int, y: Int) {
        @Suppress("DEPRECATION")
        RenderSystem.color4f(1f, 1f, 1f, 1f)
        minecraft!!.textureManager.bind(texture)
        blit(matrixStack, leftPos, topPos, 0, 0, xSize, ySize)
    }

    override fun renderLabels(matrix: MatrixStack, mouseX: Int, mouseY: Int) {
        super.renderLabels(matrix, mouseX, mouseY)

        val invMaybe = menu.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        val inv: IItemHandler
        if (!invMaybe.isPresent) return
        else inv = invMaybe.orElseThrow(::Error)
        val sirenTrack = inv.getStackInSlot(0).item
        if (sirenTrack is SirenTrack) {
            val trackName = sirenTrack.trackName
            val trackType = sirenTrack.trackType
            val trackRange = sirenTrack.trackRange
            val color = sirenTrack.color
            val textAreaMiddleX = 106F
            val textAreaStartY = 22F
            font.draw(matrix, trackName, textAreaMiddleX - font.width(trackName) * .5F, textAreaStartY + 5F, color)
            font.draw(matrix, trackType, textAreaMiddleX - font.width(trackType) * .5F, textAreaStartY + 15F, color)
            font.draw(matrix, trackRange, textAreaMiddleX - font.width(trackRange) * .5F, textAreaStartY + 25F, color)
        }
    }

    override fun render(matrix: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrix)
        super.render(matrix, mouseX, mouseY, partialTicks)
        renderTooltip(matrix, mouseX, mouseY)
    }
}
