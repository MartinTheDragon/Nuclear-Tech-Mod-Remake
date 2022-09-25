package at.martinthedragon.nucleartech.screen

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.block.entity.AbstractOilWellBlockEntity
import at.martinthedragon.nucleartech.energy.EnergyFormatter
import at.martinthedragon.nucleartech.menu.OilWellMenu
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.rendering.renderGuiFluidTank
import at.martinthedragon.nucleartech.screen.widgets.UpgradeInfoWidget
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.entity.player.Inventory
import net.minecraftforge.fluids.capability.templates.FluidTank

class OilWellScreen(menu: OilWellMenu, playerInventory: Inventory, title: Component) : AbstractContainerScreen<OilWellMenu>(menu, playerInventory, title) {
    private val texture = ntm("textures/gui/oil_derrick.png")

    override fun init() {
        super.init()
        addRenderableWidget(UpgradeInfoWidget(guiLeft + 156, guiTop + 3, 8, 8, menu, this::renderComponentTooltip))
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

        val oilWell = menu.blockEntity
        val energyScaled = oilWell.energy * 34 / oilWell.maxEnergy
        if (energyScaled > 0) blit(stack, guiLeft + 8, guiTop + 51 - energyScaled, 176, 34 - energyScaled, 16, energyScaled)

        val status = oilWell.status
        if (status != AbstractOilWellBlockEntity.STATUS_OK) {
            blit(stack, guiLeft + 35, guiTop + 17, 176 + 16 * (status - 1), 52, 16, 16)
        }

        if (oilWell.getTanks() < 3) {
            blit(stack, guiLeft + 34, guiTop + 36, 192, 0, 18, 34)
        } else {
            renderGuiFluidTank(stack, guiLeft + 40, guiTop + 69, 6, 32, blitOffset, oilWell.tanks[2])
        }

        renderGuiFluidTank(stack, guiLeft + 62, guiTop + 69, 16, 52, blitOffset, oilWell.oilTank)
        renderGuiFluidTank(stack, guiLeft + 107, guiTop + 69, 16, 52, blitOffset, oilWell.gasTank)
    }

    override fun renderTooltip(matrix: PoseStack, mouseX: Int, mouseY: Int) {
        super.renderTooltip(matrix, mouseX, mouseY)

        val oilWell = menu.blockEntity

        if (isHovering(8, 17, 16, 34, mouseX.toDouble(), mouseY.toDouble())) {
            renderComponentTooltip(matrix, listOf(
                LangKeys.ENERGY.get(),
                TextComponent("${EnergyFormatter.formatEnergy(oilWell.energy)}/${EnergyFormatter.formatEnergy(oilWell.maxEnergy)} HE")
            ), mouseX, mouseY, font)
        }

        if (isHovering(35, 17, 16, 16, mouseX.toDouble(), mouseY.toDouble())) {
            val status = when (oilWell.status) {
                AbstractOilWellBlockEntity.STATUS_OK -> LangKeys.OIL_WELL_STATUS_OK.get()
                AbstractOilWellBlockEntity.STATUS_NO_OIL_SOURCE -> LangKeys.OIL_WELL_STATUS_NO_OIL_SOURCE.get()
                AbstractOilWellBlockEntity.STATUS_ERROR -> LangKeys.OIL_WELL_STATUS_ERROR.get()
                AbstractOilWellBlockEntity.STATUS_OUT_OF_FLUID -> LangKeys.OIL_WELL_STATUS_OUT_OF_FLUID.get()
                AbstractOilWellBlockEntity.STATUS_LOOKING_FOR_OIL -> LangKeys.OIL_WELL_STATUS_LOOKING_FOR_OIL.get()
                AbstractOilWellBlockEntity.STATUS_NO_POWER -> LangKeys.OIL_WELL_STATUS_NO_POWER.get()
                else -> LangKeys.OIL_WELL_STATUS_ERROR.get()
            }
            renderComponentTooltip(matrix, listOf(status), mouseX, mouseY, font)
        }
        if (isHovering(62, 17, 16, 52, mouseX.toDouble(), mouseY.toDouble()))
            renderComponentTooltip(matrix, getTooltipFluidTank(oilWell.oilTank), mouseX, mouseY, font)
        if (isHovering(107, 17, 16, 52, mouseX.toDouble(), mouseY.toDouble()))
            renderComponentTooltip(matrix, getTooltipFluidTank(oilWell.gasTank), mouseX, mouseY, font)
        if (oilWell.getTanks() > 2 && isHovering(40, 37, 6, 32, mouseX.toDouble(), mouseY.toDouble()))
            renderComponentTooltip(matrix, getTooltipFluidTank(oilWell.tanks[2]), mouseX, mouseY, font)
    }

    private fun getTooltipFluidTank(fluidTank: FluidTank) = listOf(
        fluidTank.fluid.rawFluid.attributes.getDisplayName(fluidTank.fluid),
        TextComponent("${fluidTank.fluidAmount}/${fluidTank.capacity}mB")
    )
}
