package at.martinthedragon.nucleartech.particle

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.ParticleRenderType
import net.minecraft.client.particle.SpriteSet
import net.minecraft.client.particle.TextureSheetParticle
import kotlin.math.abs

class SmokeParticle private constructor(level: ClientLevel, x: Double, y: Double, z: Double, xd: Double, yd: Double, zd: Double, private val options: SmokeParticleOptions, private val sprites: SpriteSet) : TextureSheetParticle(level, x, y, z, xd, yd, zd) {
    init {
        this.xd = xd
        this.yd = yd * 0.66666
        this.zd = zd
        lifetime = 100 + random.nextInt(40)
        friction = .76F
        speedUpWhenYMotionIsBlocked = true
        setSpriteFromAge(sprites)
        if (options.mode == SmokeParticleOptions.Mode.Cloud && random.nextBoolean()) this.yd = abs(this.yd)
        val color = random.nextFloat() * .25F + .25F
        setColor(color, color, color)
        quadSize = random.nextFloat() + .5F
        if (options.mode == SmokeParticleOptions.Mode.Digamma) {
            quadSize = 5F
            hasPhysics = false
            setColor(.5F + random.nextFloat() * .2F, 0F, 0F)
        }
    }

    override fun getLightColor(partials: Float) = if (options.mode == SmokeParticleOptions.Mode.Digamma) 240 else super.getLightColor(partials)
    override fun getRenderType(): ParticleRenderType = ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT

    override fun tick() {
        super.tick()
        alpha = 1 - (age / lifetime.toFloat())
        setSpriteFromAge(sprites)
    }

    class Provider(private val spriteSet: SpriteSet) : ParticleProvider<SmokeParticleOptions> {
        override fun createParticle(options: SmokeParticleOptions, level: ClientLevel, x: Double, y: Double, z: Double, xd: Double, yd: Double, zd: Double) =
            SmokeParticle(level, x, y, z, xd, yd, zd, options, spriteSet)
    }
}
