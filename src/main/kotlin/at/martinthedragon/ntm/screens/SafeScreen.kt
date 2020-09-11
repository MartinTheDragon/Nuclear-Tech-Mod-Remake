package at.martinthedragon.ntm.screens

import at.martinthedragon.ntm.Main
import at.martinthedragon.ntm.containers.SafeContainer
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent

class SafeScreen(
        container: SafeContainer,
        playerInventory: PlayerInventory,
        title: ITextComponent
) : ContainerScreen<SafeContainer>(container, playerInventory, title) {
    private val texture = ResourceLocation(Main.MODID, "textures/gui/safe.png")

    init {
        xSize = 175
        ySize = 167
    }

    override fun func_230450_a_(matrixStack: MatrixStack, param1: Float, param2: Int, param3: Int) {
        @Suppress("DEPRECATION")
        RenderSystem.color4f(1f, 1f, 1f, 1f)
        Minecraft.getInstance().textureManager.bindTexture(texture)
        func_238474_b_(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize)
    }

    override fun func_230430_a_(p_230430_1_: MatrixStack, p_230430_2_: Int, p_230430_3_: Int, p_230430_4_: Float) {
        super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_)
        func_230459_a_(p_230430_1_, p_230430_2_, p_230430_3_)
    }
}
