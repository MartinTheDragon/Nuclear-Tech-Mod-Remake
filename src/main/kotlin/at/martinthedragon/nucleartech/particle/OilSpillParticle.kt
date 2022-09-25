package at.martinthedragon.nucleartech.particle

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.ParticleRenderType
import net.minecraft.client.particle.SpriteSet
import net.minecraft.client.particle.TextureSheetParticle
import net.minecraft.core.particles.SimpleParticleType

class OilSpillParticle private constructor(level: ClientLevel, x: Double, y: Double, z: Double, private val sprites: SpriteSet) : TextureSheetParticle(level, x, y, z, 0.0, 0.0, 0.0) {
    init {
        lifetime = random.nextInt(4) + 15
        setSpriteFromAge(sprites)
        val color = .1F // TODO randomisation over several particles
        setColor(color, color, color)
        scale(3F)
        xd *= .1
        yd *= .1
        zd *= .1
    }

    override fun getRenderType(): ParticleRenderType = ParticleRenderType.PARTICLE_SHEET_OPAQUE

    override fun tick() {
        super.tick()
        scale(.95F)
        setSpriteFromAge(sprites)
    }

    class Provider(private val sprites: SpriteSet) : ParticleProvider<SimpleParticleType> {
        override fun createParticle(options: SimpleParticleType, level: ClientLevel, x: Double, y: Double, z: Double, xd: Double, yd: Double, zd: Double) =
            OilSpillParticle(level, x, y, z, sprites)
    }
}
