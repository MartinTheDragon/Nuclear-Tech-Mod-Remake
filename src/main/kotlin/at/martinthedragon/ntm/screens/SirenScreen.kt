package at.martinthedragon.ntm.screens

import at.martinthedragon.ntm.Main
import at.martinthedragon.ntm.containers.SirenContainer
import at.martinthedragon.ntm.items.SirenTrack
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraftforge.items.CapabilityItemHandler

class SirenScreen(
        container: SirenContainer,
        playerInventory: PlayerInventory,
        title: ITextComponent
) : ContainerScreen<SirenContainer>(container, playerInventory, title) {
    private val texture = ResourceLocation(Main.MODID, "textures/gui/siren.png")

    init {
        imageWidth = 175
        imageHeight = 165
    }

    override fun renderBg(matrixStack: MatrixStack, partialTicks: Float, x: Int, y: Int) {
        @Suppress("DEPRECATION")
        RenderSystem.color4f(1f, 1f, 1f, 1f)
        minecraft!!.textureManager.bind(texture)
        blit(matrixStack, (width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize)
    }

    override fun renderLabels(matrix: MatrixStack, mouseX: Int, mouseY: Int) {
        super.renderLabels(matrix, mouseX, mouseY)

        val inv = menu.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)
        val sirenTrack = inv.getStackInSlot(0).item
        val trackName: ITextComponent
        val trackType: ITextComponent
        val trackRange: ITextComponent
        val color: Int

        if (sirenTrack is SirenTrack) {
            trackName = sirenTrack.trackName
            trackType = sirenTrack.trackType
            trackRange = sirenTrack.trackRange
            color = sirenTrack.color
        } else return

        font.draw(matrix, trackName, 123f / 2 - 12, 51f / 2 + 2, color)
        font.draw(matrix, trackType, 123f / 2 - 12, 51f / 2 + 12, color)
        font.draw(matrix, trackRange, 123f / 2 - 12, 51f / 2 + 22, color)
    }

    override fun render(matrix: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrix)
        super.render(matrix, mouseX, mouseY, partialTicks)
        renderTooltip(matrix, mouseX, mouseY)
    }
}
