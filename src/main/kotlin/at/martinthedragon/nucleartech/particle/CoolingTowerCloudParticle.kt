package at.martinthedragon.nucleartech.particle

import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.Camera
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.ParticleRenderType
import net.minecraft.client.particle.SpriteSet
import net.minecraft.client.particle.TextureSheetParticle
import kotlin.math.pow

class CoolingTowerCloudParticle private constructor(level: ClientLevel, x: Double, y: Double, z: Double, private val spriteSet: SpriteSet, private val options: CoolingTowerCloudParticleOptions) : TextureSheetParticle(level, x, y, z) {
    init {
        hasPhysics = false
        friction = 0.925F
        lifetime = options.lifetime
        setSpriteFromAge(spriteSet)
        val color = 0.9F + random.nextFloat() * 0.05F
        setColor(color, color, color)
        alpha = 0.25F
        setSize(options.scale, options.scale)
        quadSize = options.scale
    }

    override fun getRenderType(): ParticleRenderType = ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT

    override fun tick() {
        xo = x
        yo = y
        zo = z

        if (age++ >= lifetime) {
            remove()
            return
        }

        val relativeAge = age.toFloat() / lifetime.toFloat()

        val scale = options.scale + (options.maxScale * relativeAge - options.scale).pow(2)
        setSize(scale, scale)
        quadSize = scale

        if (yd < options.lift) yd = (yd + 0.01F).coerceAtMost(options.lift.toDouble())

        xd += (random.nextGaussian() * 0.075 + 0.02) * relativeAge
        zd += (random.nextGaussian() * 0.075 - 0.01) * relativeAge

        move(xd, yd, zd)

        xd *= friction
        yd *= friction
        zd *= friction
    }

    override fun render(consumer: VertexConsumer, camera: Camera, partials: Float) {
        alpha = 0.25F - ((age + partials) / lifetime * 0.25F).coerceAtMost(0.25F)
        super.render(consumer, camera, partials)
    }

    class Provider(private val spriteSet: SpriteSet) : ParticleProvider<CoolingTowerCloudParticleOptions> {
        override fun createParticle(options: CoolingTowerCloudParticleOptions, level: ClientLevel, x: Double, y: Double, z: Double, xd: Double, yd: Double, zd: Double) =
            CoolingTowerCloudParticle(level, x, y, z, spriteSet, options)
    }
}
