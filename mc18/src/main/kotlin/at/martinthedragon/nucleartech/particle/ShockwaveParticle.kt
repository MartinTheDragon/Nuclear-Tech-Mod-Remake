package at.martinthedragon.nucleartech.particle

import at.martinthedragon.nucleartech.NuclearTech
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.*
import net.minecraft.client.Camera
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.ParticleRenderType
import net.minecraft.client.particle.SpriteSet
import net.minecraft.client.particle.TextureSheetParticle
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.texture.TextureAtlas
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.util.Mth
import kotlin.math.E
import kotlin.math.pow

class ShockwaveParticle private constructor(level: ClientLevel, x: Double, y: Double, z: Double, private val sprites: SpriteSet) : TextureSheetParticle(level, x, y, z) {
    init {
        lifetime = 25
        setSpriteFromAge(sprites)
    }

    override fun getRenderType(): ParticleRenderType = object : ParticleRenderType {
        override fun begin(buffer: BufferBuilder, textureManager: TextureManager) {
            RenderSystem.depthMask(true)
            @Suppress("DEPRECATION")
            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES)
            RenderSystem.enableBlend()
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA)
            RenderSystem.disableCull()
            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE)
        }

        override fun end(tesselator: Tesselator) {
            tesselator.end()
            RenderSystem.enableCull()
        }

        override fun toString() = "${NuclearTech.MODID}_PARTICLE_SHEET_CUSTOM_SHOCKWAVE"
    }

    override fun tick() {
        super.tick()
        setSpriteFromAge(sprites)
    }

    override fun render(vertexConsumer: VertexConsumer, camera: Camera, partials: Float) {
        val position = camera.position
        val x = Mth.lerp(partials.toDouble(), xo, x) - position.x
        val y = Mth.lerp(partials.toDouble(), yo, y) - position.y
        val z = Mth.lerp(partials.toDouble(), zo, z) - position.z

        alpha = 1F - (age + partials) / (lifetime + 1)
        quadSize = (1.0 - E.pow((age + partials) * -.125)).toFloat() * 45F

        val u0 = u0
        val u1 = u1
        val v0 = v0
        val v1 = v1
        val color = LightTexture.FULL_BRIGHT
        vertexConsumer.vertex(x - quadSize, y - .25, z - quadSize).uv(u1, v1).color(rCol, gCol, bCol, alpha).uv2(color).endVertex()
        vertexConsumer.vertex(x - quadSize, y - .25, z + quadSize).uv(u1, v0).color(rCol, gCol, bCol, alpha).uv2(color).endVertex()
        vertexConsumer.vertex(x + quadSize, y - .25, z + quadSize).uv(u0, v0).color(rCol, gCol, bCol, alpha).uv2(color).endVertex()
        vertexConsumer.vertex(x + quadSize, y - .25, z - quadSize).uv(u0, v1).color(rCol, gCol, bCol, alpha).uv2(color).endVertex()
    }

    override fun shouldCull() = false

    class Provider(private val spriteSet: SpriteSet) : ParticleProvider<SimpleParticleType> {
        override fun createParticle(options: SimpleParticleType, level: ClientLevel, x: Double, y: Double, z: Double, xd: Double, yd: Double, zd: Double) =
            ShockwaveParticle(level, x, y, z, spriteSet)
    }
}
