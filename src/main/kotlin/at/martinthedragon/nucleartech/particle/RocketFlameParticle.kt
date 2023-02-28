package at.martinthedragon.nucleartech.particle

import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Vector3f
import net.minecraft.client.Camera
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.ParticleRenderType
import net.minecraft.client.particle.SpriteSet
import net.minecraft.client.particle.TextureSheetParticle
import net.minecraft.client.renderer.LightTexture
import net.minecraft.core.particles.SimpleParticleType
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

class RocketFlameParticle private constructor(level: ClientLevel, x: Double, y: Double, z: Double, xd: Double, yd: Double, zd: Double, private val sprites: SpriteSet) : TextureSheetParticle(level, x, y, z) {
    private val randomSeeds = random.longs(10).toArray()

    init {
        this.xd = xd
        this.yd = yd
        this.zd = zd
        lifetime = 300 + random.nextInt(50)
        friction = 0.91F
        setSpriteFromAge(sprites)
        setSize(2.6F, 2.6F)
    }

    override fun tick() {
        super.tick()
        setSpriteFromAge(sprites)
    }

    override fun getLightColor(partials: Float) = LightTexture.FULL_BRIGHT

    override fun getRenderType(): ParticleRenderType = ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT

    override fun render(consumer: VertexConsumer, camera: Camera, partials: Float) {
        for (seed in randomSeeds) {
            random.setSeed(seed)

            val relativeAge = (age + partials) / lifetime
            val baseColor = random.nextFloat() * .3F
            val darkener = 1F - min(relativeAge * 4F, 1F)
            val color = Vector3f(
                darkener + baseColor,
                0.6F * darkener + baseColor,
                baseColor
            )
            color.mul((1F / (darkener + baseColor)).coerceAtMost(1F))
            setColor(color.x(), color.y(), color.z())
            alpha = sqrt(1F - min(relativeAge, 1F)) * .75F

            quadSize = random.nextFloat() * .5F + .1F + relativeAge * 2F

            val previousXo = xo
            val previousYo = yo
            val previousZo = zo
            val previousX = x
            val previousY = y
            val previousZ = z

            val previousSpread = (age / lifetime * 4F).pow(1.5F) + 1F
            val spread = ((age + 1) / lifetime * 4F).pow(1.5F) + 1F

            val offsetX = random.nextGaussian().toFloat() * .2F
            val offsetY = random.nextGaussian().toFloat() * .5F
            val offsetZ = random.nextGaussian().toFloat() * .2F

            xo += offsetX * previousSpread
            yo += offsetY * previousSpread
            zo += offsetZ * previousSpread

            x += offsetX * spread
            y += offsetY * spread
            z += offsetZ * spread

            super.render(consumer, camera, partials)

            xo = previousXo
            yo = previousYo
            zo = previousZo
            x = previousX
            y = previousY
            z = previousZ
        }
    }

    class Provider(private val spriteSet: SpriteSet) : ParticleProvider<SimpleParticleType> {
        override fun createParticle(options: SimpleParticleType, level: ClientLevel, x: Double, y: Double, z: Double, xd: Double, yd: Double, zd: Double) =
            RocketFlameParticle(level, x, y, z, xd, yd, zd, spriteSet)
    }
}
