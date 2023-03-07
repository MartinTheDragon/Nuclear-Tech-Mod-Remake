package at.martinthedragon.nucleartech.screen

import at.martinthedragon.nucleartech.NTechTags
import at.martinthedragon.nucleartech.block.entity.TurbineBlockEntity
import at.martinthedragon.nucleartech.extensions.tooltipEnergyStorage
import at.martinthedragon.nucleartech.extensions.tooltipFluidTank
import at.martinthedragon.nucleartech.menu.TurbineMenu
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.rendering.renderGuiFluidTank
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class TurbineScreen(menu: TurbineMenu, playerInventory: Inventory, title: Component) : AbstractContainerScreen<TurbineMenu>(menu, playerInventory, title) {
    private val texture = ntm("textures/gui/turbine.png")

    override fun render(stack: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(stack)
        super.render(stack, mouseX, mouseY, partialTicks)
        renderTooltip(stack, mouseX, mouseY)
    }

    @Suppress("DEPRECATION")
    override fun renderBg(stack: PoseStack, partialTicks: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
        RenderSystem.setShaderTexture(0, texture)
        blit(stack, guiLeft, guiTop, 0, 0, xSize, ySize)

        val turbine = menu.blockEntity
        val energyScaled = turbine.energy * 34 / TurbineBlockEntity.MAX_ENERGY
        if (energyScaled > 0) blit(stack, guiLeft + 123, guiTop + 69 - energyScaled, 176, 34 - energyScaled, 7, energyScaled)

        val inputFluid = turbine.inputTank.fluid.rawFluid
        if (inputFluid.`is`(NTechTags.Fluids.STEAM)) blit(stack, guiLeft + 99, guiTop + 18, 183, 0, 14, 14)
        if (inputFluid.`is`(NTechTags.Fluids.HOT_STEAM)) blit(stack, guiLeft + 99, guiTop + 18, 183, 14, 14, 14)
        if (inputFluid.`is`(NTechTags.Fluids.SUPER_HOT_STEAM)) blit(stack, guiLeft + 99, guiTop + 18, 183, 28, 14, 14)
        if (inputFluid.`is`(NTechTags.Fluids.ULTRA_HOT_STEAM)) blit(stack, guiLeft + 99, guiTop + 18, 183, 42, 14, 14)

        renderGuiFluidTank(stack, guiLeft + 62, guiTop + 69, 16, 52, blitOffset, turbine.inputTank)
        renderGuiFluidTank(stack, guiLeft + 134, guiTop + 69, 16, 52, blitOffset, turbine.outputTank)
    }

    override fun renderTooltip(matrix: PoseStack, mouseX: Int, mouseY: Int) {
        super.renderTooltip(matrix, mouseX, mouseY)

        val turbine = menu.blockEntity
        tooltipEnergyStorage(matrix, turbine.energyStorage, 123, 35, 7, 34, mouseX, mouseY)
        tooltipFluidTank(matrix, turbine.inputTank, 62, 17, 16, 52, mouseX, mouseY)
        tooltipFluidTank(matrix, turbine.outputTank, 134, 17, 16, 52, mouseX, mouseY)
    }
}
