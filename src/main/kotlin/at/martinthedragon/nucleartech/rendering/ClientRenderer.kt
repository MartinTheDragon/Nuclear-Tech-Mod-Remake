package at.martinthedragon.nucleartech.rendering

import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.Radiation
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiComponent
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.levelgen.SingleThreadedRandomSource
import net.minecraft.world.level.levelgen.synth.SimplexNoise
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.EntityViewRenderEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.random.Random

@Suppress("unused")
@Mod.EventBusSubscriber(Dist.CLIENT, modid = NuclearTech.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object ClientRenderer {
    private val minecraft = Minecraft.getInstance()

    @SubscribeEvent @JvmStatic
    fun renderOverlays(event: RenderGameOverlayEvent.Pre) {
        val player = minecraft.player ?: return

        if (event.type == RenderGameOverlayEvent.ElementType.LAYER) {
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

    private fun renderGeigerHUD(matrixStack: PoseStack, radValueIn: Float) {
        matrixStack.pushPose()
        RenderSystem.enableBlend()
        RenderSystem.defaultBlendFunc()
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F)

        val radiation = lastResult - previousResult

        if (System.currentTimeMillis() >= lastSurvey + 1000) {
            lastSurvey = System.currentTimeMillis()
            previousResult = lastResult
            lastResult = radValueIn
        }

        val barFillAmount = min(radValueIn / 1000F * 74F, 74F).toInt()
        val posX = 16
        val posY = minecraft.window.guiScaledHeight - 20

        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        RenderSystem.setShaderTexture(0, GEIGER_OVERLAY)
        GuiComponent.blit(matrixStack, posX, posY, 0, 0F, 0F, 94, 18, 128, 128)
        GuiComponent.blit(matrixStack, posX + 1, posY + 1, 0, 1F, 19F, barFillAmount, 16, 128, 128)

        // 74 = length of bar
        when {
            radiation >= 25 -> GuiComponent.blit(matrixStack, posX + 76, posY - 18, 0, 36F, 36F, 18, 18, 128, 128)
            radiation >= 10 -> GuiComponent.blit(matrixStack, posX + 76, posY - 18, 0, 18F, 36F, 18, 18, 128, 128)
            radiation >= 2.5 -> GuiComponent.blit(matrixStack, posX + 76, posY - 18, 0, 0F, 36F, 18, 18, 128, 128)
        }

        when {
            radiation > 1000 -> minecraft.font.draw(matrixStack, ">1000 RAD/s", posX.toFloat(), posY - 8F, 0xFF0000)
            radiation >= 1 -> minecraft.font.draw(matrixStack, "${radiation.roundToInt()} RAD/s", posX.toFloat(), posY - 8F, 0xFF0000)
            radiation > 0 -> minecraft.font.draw(matrixStack, "<1 RAD/s", posX.toFloat(), posY - 8F, 0xFF0000)
        }

        matrixStack.popPose()
    }

    @Mod.EventBusSubscriber(Dist.CLIENT, modid = NuclearTech.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    object CameraShake {
        private const val MAX_YAW = 20
        private const val MAX_PITCH = 20
        private const val MAX_ROLL = 8
        private const val MAX_X_OFFSET = 0.6
        private const val MAX_Z_OFFSET = 0.6
        private const val MAX_Y_OFFSET = 0.3

        private val perlinNoise = SimplexNoise(SingleThreadedRandomSource(Random.nextLong()))
        private var lastNanoTime = 0L
        private var intensityLevelRotational = 0.0
            set(value) { field = value.coerceIn(0.0, 1.0) }
        private var intensityLevelTranslational = 0.0
            set(value) { field = value.coerceIn(0.0, 1.0) }

        fun addIntensity(delta: Double) {
            intensityLevelRotational += delta
            intensityLevelTranslational += delta
        }

        fun setIntensityAtLeast(value: Double) {
            if (intensityLevelRotational < value) intensityLevelRotational = value
            if (intensityLevelTranslational < value) intensityLevelTranslational = value
        }

        fun addRotational(delta: Double) { intensityLevelRotational += delta }
        fun addTranslational(delta: Double) { intensityLevelTranslational += delta }
        fun setRotationalAtLeast(value: Double) { if (intensityLevelRotational < value) intensityLevelRotational = value }
        fun setTranslationalAtLeast(value: Double) { if (intensityLevelTranslational < value) intensityLevelTranslational = value }

        @SubscribeEvent @JvmStatic
        fun tick(event: EntityViewRenderEvent.CameraSetup) {
            if (intensityLevelRotational > 0.0) {
                val noiseInput = System.nanoTime() * 5E-8
                val shake = intensityLevelRotational * intensityLevelRotational
                event.yaw += (MAX_YAW * shake * perlinNoise.getValue(noiseInput, -noiseInput)).toFloat()
                event.pitch += (MAX_PITCH * shake * perlinNoise.getValue(noiseInput + 1000, -noiseInput - 1000)).toFloat()
                event.roll += (MAX_ROLL * shake * perlinNoise.getValue(noiseInput - 1000, -noiseInput + 1000)).toFloat()
                intensityLevelRotational -= .25 * (System.nanoTime() - lastNanoTime) * 1E-9 // takes four seconds to go from 1 to 0
            }

            if (intensityLevelTranslational > 0.0) {
                val noiseInput = System.nanoTime() * 5E-8
                val shake = intensityLevelTranslational * intensityLevelTranslational
                event.camera.move(
                    MAX_X_OFFSET * shake * perlinNoise.getValue(noiseInput + 500, -noiseInput - 500),
                    MAX_Y_OFFSET * shake * perlinNoise.getValue(noiseInput + 250, -noiseInput - 250),
                    MAX_Z_OFFSET * shake * perlinNoise.getValue(noiseInput - 500, -noiseInput + 500)
                )
                intensityLevelTranslational -= .25 * (System.nanoTime() - lastNanoTime) * 1E-9 // takes four seconds to go from 1 to 0
            }

            lastNanoTime = System.nanoTime()
        }
    }
}
