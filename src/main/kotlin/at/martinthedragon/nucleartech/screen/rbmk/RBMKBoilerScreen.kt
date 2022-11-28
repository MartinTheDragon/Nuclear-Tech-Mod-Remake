package at.martinthedragon.nucleartech.screen.rbmk

import at.martinthedragon.nucleartech.extensions.tooltipFluidTank
import at.martinthedragon.nucleartech.fluid.NTechFluids
import at.martinthedragon.nucleartech.menu.rbmk.RBMKBoilerMenu
import at.martinthedragon.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.player.Inventory

class RBMKBoilerScreen(
    menu: RBMKBoilerMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<RBMKBoilerMenu>(menu, playerInventory, title) {
    private val texture = ntm("textures/gui/rbmk/boiler.png")

    init {
        imageWidth = 176
        imageHeight = 186
        inventoryLabelY = imageHeight - 94
    }

    // TODO actual buttons
    override fun mouseClicked(x: Double, y: Double, button: Int): Boolean {
        if (x.toInt() in guiLeft + 33 .. guiLeft + 53 && y.toInt() in guiTop + 21 .. guiTop + 85 && menu.clickMenuButton(minecraft!!.player!!, 0)) {
            Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1F))
            minecraft!!.gameMode!!.handleInventoryButtonClick(menu.containerId, 0)
            return true
        }

        return super.mouseClicked(x, y, button)
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

        val boiler = menu.blockEntity
        val water = boiler.waterTank.fluidAmount * 58 / boiler.waterTank.capacity
        blit(stack, guiLeft + 126, guiTop + 82 - water, 176, 58 - water, 14, water)

        var steam = boiler.steamTank.fluidAmount * 22 / boiler.steamTank.capacity
        if (steam > 0) steam++
        if (steam > 22) steam++
        blit(stack, guiLeft + 91, guiTop + 65 - steam, 190, 24 - steam, 4, steam)

        val steamType = boiler.steamTank.fluid.rawFluid
        val steamTypeGuiXOffset = when {
            steamType.isSame(NTechFluids.steam.source.get()) -> 194
            steamType.isSame(NTechFluids.steamHot.source.get()) -> 208
            steamType.isSame(NTechFluids.steamSuperHot.source.get()) -> 222
            steamType.isSame(NTechFluids.steamUltraHot.source.get()) -> 236
            else -> 194
        }

        blit(stack, guiLeft + 36, guiTop + 24, steamTypeGuiXOffset, 0, 14, 58)
    }

    override fun renderTooltip(matrix: PoseStack, mouseX: Int, mouseY: Int) {
        super.renderTooltip(matrix, mouseX, mouseY)
        tooltipFluidTank(matrix, menu.blockEntity.waterTank, 126, 24, 15, 58, mouseX, mouseY)
        tooltipFluidTank(matrix, menu.blockEntity.steamTank, 89, 39, 8, 28, mouseX, mouseY)
    }
}
