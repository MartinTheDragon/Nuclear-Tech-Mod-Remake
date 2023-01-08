package at.martinthedragon.nucleartech.particle

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.SmokeParticle
import net.minecraft.client.particle.SpriteSet
import net.minecraft.core.particles.SimpleParticleType

class VolcanoSmokeParticle(
    level: ClientLevel,
    x: Double, y: Double, z: Double,
    xd: Double, yd: Double, zd: Double,
    spriteSet: SpriteSet
) : SmokeParticle(level, x, y, z, xd, yd, zd, 100F, spriteSet) {
    init {
        this.xd = random.nextGaussian() * .2 + xd
        this.yd = random.nextDouble() + 2.5 + yd
        this.zd = random.nextGaussian() * .2 + zd
        hasPhysics = false
        lifetime = 200 + random.nextInt(50)
        setSpriteFromAge(spriteSet)
    }

    class Provider(private val spriteSet: SpriteSet) : ParticleProvider<SimpleParticleType> {
        override fun createParticle(type: SimpleParticleType, level: ClientLevel, x: Double, y: Double, z: Double, xd: Double, yd: Double, zd: Double) =
            VolcanoSmokeParticle(level, x, y, z, xd, yd, zd, spriteSet)
    }
}
