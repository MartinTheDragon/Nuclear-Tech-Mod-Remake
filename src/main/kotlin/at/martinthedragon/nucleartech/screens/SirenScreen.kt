package at.martinthedragon.nucleartech.screens

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.items.SirenTrack
import at.martinthedragon.nucleartech.menus.SirenMenu
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler

class SirenScreen(
    container: SirenMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<SirenMenu>(container, playerInventory, title) {
    private val texture = ResourceLocation(NuclearTech.MODID, "textures/gui/siren.png")

    init {
        imageWidth = 176
        imageHeight = 166
    }

    override fun renderBg(matrixStack: PoseStack, partialTicks: Float, x: Int, y: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        RenderSystem.setShaderTexture(0, texture)
        blit(matrixStack, leftPos, topPos, 0, 0, xSize, ySize)
    }

    override fun renderLabels(matrix: PoseStack, mouseX: Int, mouseY: Int) {
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

    override fun render(matrix: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrix)
        super.render(matrix, mouseX, mouseY, partialTicks)
        renderTooltip(matrix, mouseX, mouseY)
    }
}
