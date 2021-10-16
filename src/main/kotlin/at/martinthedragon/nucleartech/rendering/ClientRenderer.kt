package at.martinthedragon.nucleartech.rendering

import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.Radiation
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.AbstractGui
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import kotlin.math.min
import kotlin.math.roundToInt

@Suppress("unused")
@Mod.EventBusSubscriber(Dist.CLIENT, modid = NuclearTech.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object ClientRenderer {
    private val minecraft = Minecraft.getInstance()

    @SubscribeEvent @JvmStatic
    fun renderOverlays(event: RenderGameOverlayEvent.Pre) {
        val player = minecraft.player ?: return

        if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
            // TODO check for FSB armor

            if (player.inventory.hasAnyOf(setOf(ModItems.geigerCounter.get()))) {
                renderGeigerHUD(event.matrixStack, Radiation.getEntityIrradiation(player))
            }
        }
    }

    private val GEIGER_OVERLAY = ResourceLocation(NuclearTech.MODID, "textures/gui/hud/geiger_hud_overlay.png")
    private var lastSurvey = 0L
    private var previousResult = 0F
    private var lastResult = 0F

    private fun renderGeigerHUD(matrixStack: MatrixStack, radValueIn: Float) {
        matrixStack.pushPose()
        RenderSystem.enableBlend()
        RenderSystem.defaultBlendFunc()
        @Suppress("DEPRECATION")
        RenderSystem.color4f(1F, 1F, 1F, 1F)

        val radiation = lastResult - previousResult

        if (System.currentTimeMillis() >= lastSurvey + 1000) {
            lastSurvey = System.currentTimeMillis()
            previousResult = lastResult
            lastResult = radValueIn
        }

        val barFillAmount = min(radValueIn / 1000F * 74F, 74F).toInt()
        val posX = 16
        val posY = minecraft.window.guiScaledHeight - 20

        minecraft.textureManager.bind(GEIGER_OVERLAY)
        AbstractGui.blit(matrixStack, posX, posY, 0, 0F, 0F, 94, 18, 128, 128)
        AbstractGui.blit(matrixStack, posX + 1, posY + 1, 0, 1F, 19F, barFillAmount, 16, 128, 128)

        // 74 = length of bar
        when {
            radiation >= 25 -> AbstractGui.blit(matrixStack, posX + 76, posY - 18, 0, 36F, 36F, 18, 18, 128, 128)
            radiation >= 10 -> AbstractGui.blit(matrixStack, posX + 76, posY - 18, 0, 18F, 36F, 18, 18, 128, 128)
            radiation >= 2.5 -> AbstractGui.blit(matrixStack, posX + 76, posY - 18, 0, 0F, 36F, 18, 18, 128, 128)
        }

        when {
            radiation > 1000 -> minecraft.font.draw(matrixStack, ">1000 RAD/s", posX.toFloat(), posY - 8F, 0xFF0000)
            radiation >= 1 -> minecraft.font.draw(matrixStack, "${radiation.roundToInt()} RAD/s", posX.toFloat(), posY - 8F, 0xFF0000)
            radiation > 0 -> minecraft.font.draw(matrixStack, "<1 RAD/s", posX.toFloat(), posY - 8F, 0xFF0000)
        }

        matrixStack.popPose()
    }
}
