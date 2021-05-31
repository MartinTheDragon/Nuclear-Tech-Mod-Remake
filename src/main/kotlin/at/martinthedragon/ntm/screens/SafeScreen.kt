package at.martinthedragon.ntm.screens

import at.martinthedragon.ntm.Main
import at.martinthedragon.ntm.containers.SafeContainer
import at.martinthedragon.ntm.items.SirenTrack
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraftforge.items.CapabilityItemHandler

class SafeScreen(
        container: SafeContainer,
        playerInventory: PlayerInventory,
        title: ITextComponent
) : ContainerScreen<SafeContainer>(container, playerInventory, title) {
    private val texture = ResourceLocation(Main.MODID, "textures/gui/safe.png")

    init {
        imageWidth = 175
        imageHeight = 167
    }

    override fun render(matrix: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrix)
        super.render(matrix, mouseX, mouseY, partialTicks)
        renderTooltip(matrix, mouseX, mouseY)
    }

    override fun renderBg(matrixStack: MatrixStack, partialTicks: Float, x: Int, y: Int) {
        @Suppress("DEPRECATION")
        RenderSystem.color4f(1f, 1f, 1f, 1f)
        minecraft!!.textureManager.bind(texture)
        blit(matrixStack, (width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize)
    }
}
