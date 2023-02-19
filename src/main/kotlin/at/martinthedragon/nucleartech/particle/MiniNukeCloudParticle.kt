package at.martinthedragon.nucleartech.particle

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.ParticleRenderType
import net.minecraft.client.particle.SpriteSet
import net.minecraft.client.particle.TextureSheetParticle
import net.minecraft.client.renderer.LightTexture
import net.minecraft.core.particles.SimpleParticleType

class MiniNukeCloudParticle private constructor(level: ClientLevel, x: Double, y: Double, z: Double, xd: Double, yd: Double, zd: Double, private val spriteSet: SpriteSet) : TextureSheetParticle(level, x, y, z, xd, yd, zd) {
    init {
        this.xd = xd
        this.yd = yd
        this.zd = zd

        if (yd > 0) {
            friction = 0.9F
            lifetime = if (yd > 0.1) 92 + random.nextInt(11) + (yd.coerceAtMost(10.0) * 20).toInt() else 72 + random.nextInt(11)
        } else if (yd == 0.0) {
            friction = 0.95F
            lifetime = 52 + random.nextInt(11)
        } else {
            friction = 0.85F
            lifetime = 122 + random.nextInt(31)
            age = 80
        }

        quadSize = 3F

        setSize(quadSize, quadSize)
        setSpriteFromAge(spriteSet)
    }

    override fun tick() {
        super.tick()
        setSpriteFromAge(spriteSet)
    }

    // FIXME translucent transparency is horrible: find a way to sort polys
    override fun getRenderType(): ParticleRenderType = ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT

    override fun getLightColor(partials: Float) = LightTexture.FULL_BRIGHT

    class Provider(private val spriteSet: SpriteSet) : ParticleProvider<SimpleParticleType> {
        override fun createParticle(options: SimpleParticleType, level: ClientLevel, x: Double, y: Double, z: Double, xd: Double, yd: Double, zd: Double) =
            MiniNukeCloudParticle(level, x, y, z, xd, yd, zd, spriteSet)
    }
}
