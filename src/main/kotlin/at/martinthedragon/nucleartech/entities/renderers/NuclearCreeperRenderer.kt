package at.martinthedragon.nucleartech.entities.renderers

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.entities.NuclearCreeperEntity
import at.martinthedragon.nucleartech.entities.renderers.layers.NuclearCreeperChargeLayer
import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.client.renderer.entity.model.CreeperModel
import net.minecraft.util.ResourceLocation
import kotlin.math.sin

class NuclearCreeperRenderer(entityRendererManager: EntityRendererManager) :
    MobRenderer<NuclearCreeperEntity, CreeperModel<NuclearCreeperEntity>>(entityRendererManager, CreeperModel(), .5F)
{
    init {
        addLayer(NuclearCreeperChargeLayer(this))
    }

    override fun scale(entity: NuclearCreeperEntity, matrix: MatrixStack, partialTicks: Float) {
        val swelling = entity.getSwelling(partialTicks)
        val pulse = 1F + sin(swelling * 100F) * swelling * .01F
        var swellingForScale = swelling.coerceIn(0F, 1F)
        swellingForScale *= swellingForScale
        swellingForScale *= swellingForScale
        val horizontalScale = (1F + swellingForScale * .4F) * pulse
        val verticalScale = (1F + swellingForScale * .1F) / pulse
        matrix.scale(horizontalScale, verticalScale, horizontalScale)
    }

    override fun getWhiteOverlayProgress(entity: NuclearCreeperEntity, partialTicks: Float): Float {
        val swelling = entity.getSwelling(partialTicks)
        return if ((swelling * 10F).toInt() % 2 == 0) 0F else swelling.coerceIn(.5F, 1F)
    }

    override fun getTextureLocation(p0: NuclearCreeperEntity) = NUCLEAR_CREEPER_LOCATION

    companion object {
        private val NUCLEAR_CREEPER_LOCATION = ResourceLocation(NuclearTech.MODID, "textures/entity/nuclear_creeper/nuclear_creeper.png")
    }
}
