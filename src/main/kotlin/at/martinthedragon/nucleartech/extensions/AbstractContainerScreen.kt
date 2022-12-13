package at.martinthedragon.nucleartech.extensions

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraftforge.energy.EnergyStorage
import net.minecraftforge.fluids.capability.templates.FluidTank

fun AbstractContainerScreen<*>.tooltipEnergyStorage(matrix: PoseStack, energyStorage: EnergyStorage, startX: Int, startY: Int, width: Int, height: Int, mouseX: Int, mouseY: Int) {
    val mouseXNormalized = mouseX - guiLeft
    val mouseYNormalized = mouseY - guiTop
    if (mouseXNormalized in startX..startX + width && mouseYNormalized in startY..startY + height) {
        renderComponentTooltip(matrix, energyStorage.getTooltip(), mouseX, mouseY, minecraft.font)
    }
}

fun AbstractContainerScreen<*>.tooltipFluidTank(matrix: PoseStack, tank: FluidTank, startX: Int, startY: Int, width: Int, height: Int, mouseX: Int, mouseY: Int) {
    val mouseXNormalized = mouseX - guiLeft
    val mouseYNormalized = mouseY - guiTop
    if (mouseXNormalized in startX..startX + width && mouseYNormalized in startY..startY + height) {
        renderComponentTooltip(matrix, tank.getTooltip(), mouseX, mouseY, minecraft.font)
    }
}
