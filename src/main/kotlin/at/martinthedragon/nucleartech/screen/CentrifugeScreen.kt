package at.martinthedragon.nucleartech.screen

import at.martinthedragon.nucleartech.block.entity.CentrifugeBlockEntity
import at.martinthedragon.nucleartech.menu.CentrifugeMenu
import at.martinthedragon.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import kotlin.math.min

class CentrifugeScreen(menu: CentrifugeMenu, playerInventory: Inventory, title: Component) : AbstractContainerScreen<CentrifugeMenu>(menu, playerInventory, title) {
    private val texture = ntm("textures/gui/centrifuge.png")

    init {
        imageWidth = 176
        imageHeight = 186
        inventoryLabelY = imageHeight - 94
    }

    override fun init() {
        super.init()
//        addRenderableWidget(UpgradeInfoWidget(guiLeft + 105, guiTop + 40, 8, 8, menu, this::renderComponentTooltip))
    }

    override fun render(stack: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(stack)
        super.render(stack, mouseX, mouseY, partialTicks)
        renderTooltip(stack, mouseX, mouseY)
    }

    override fun renderBg(stack: PoseStack, partialTicks: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
        RenderSystem.setShaderTexture(0, texture)
        blit(stack, guiLeft, guiTop, 0, 0, xSize, ySize)

        val centrifuge = menu.blockEntity
        val energyScaled = centrifuge.energy * 35 / CentrifugeBlockEntity.MAX_ENERGY
        if (energyScaled > 0) blit(stack, guiLeft + 9, guiTop + 48 - energyScaled, 176, 35 - energyScaled, 16, energyScaled)

        if (centrifuge.canProgress) {
            var progress = centrifuge.progress * 145 / centrifuge.maxProgress

            for (i in 0..3) {
                val partHeight = min(progress, 36)
                blit(stack, guiLeft + 65 + i * 20, guiTop + 50 - partHeight, 176, 71 - partHeight, 12, partHeight)
                progress -= partHeight
                if (progress <= 0) break
            }
        }
    }

    override fun renderLabels(stack: PoseStack, mouseX: Int, mouseY: Int) {
        font.draw(stack, playerInventoryTitle, inventoryLabelX.toFloat(), inventoryLabelY.toFloat(), 0x404040)
    }
}
