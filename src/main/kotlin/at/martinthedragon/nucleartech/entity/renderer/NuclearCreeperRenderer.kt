package at.martinthedragon.nucleartech.entity.renderer

import at.martinthedragon.nucleartech.entity.NuclearCreeper
import at.martinthedragon.nucleartech.entity.renderer.layers.NuclearCreeperPowerLayer
import at.martinthedragon.nucleartech.ntm
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.model.CreeperModel
import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.MobRenderer
import kotlin.math.sin

class NuclearCreeperRenderer(context: EntityRendererProvider.Context) : MobRenderer<NuclearCreeper, CreeperModel<NuclearCreeper>>(context, CreeperModel(context.bakeLayer(ModelLayers.CREEPER)), .5F) {
    private val NUCLEAR_CREEPER_LOCATION = ntm("textures/entity/nuclear_creeper/nuclear_creeper.png")

    init {
        addLayer(NuclearCreeperPowerLayer(this, context.modelSet))
    }

    override fun scale(entity: NuclearCreeper, matrix: PoseStack, partialTicks: Float) {
        val swelling = entity.getSwelling(partialTicks)
        val pulse = 1F + sin(swelling * 100F) * swelling * .01F
        var swellingForScale = swelling.coerceIn(0F, 1F)
        swellingForScale *= swellingForScale
        swellingForScale *= swellingForScale
        val horizontalScale = (1F + swellingForScale * .4F) * pulse
        val verticalScale = (1F + swellingForScale * .1F) / pulse
        matrix.scale(horizontalScale, verticalScale, horizontalScale)
    }

    override fun getWhiteOverlayProgress(entity: NuclearCreeper, partialTicks: Float): Float {
        val swelling = entity.getSwelling(partialTicks)
        return if ((swelling * 10F).toInt() % 2 == 0) 0F else swelling.coerceIn(.5F, 1F)
    }

    override fun getTextureLocation(p0: NuclearCreeper) = NUCLEAR_CREEPER_LOCATION
}
