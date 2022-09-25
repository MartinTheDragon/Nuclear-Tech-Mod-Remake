package at.martinthedragon.nucleartech.particle

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.PARTICLES
import at.martinthedragon.nucleartech.registerK
import com.mojang.serialization.Codec
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.particles.SimpleParticleType

object ModParticles {
    val CONTRAIL = PARTICLES.registerK("contrail") { createParticleType(false, ContrailParticleOptions.DESERIALIZER) { ContrailParticleOptions.CODEC }}
    val OIL_SPILL = PARTICLES.registerK("oil_spill") { SimpleParticleType(false) }
    val RUBBLE = PARTICLES.registerK("rubble") { createParticleType(false, RubbleParticleOptions.DESERIALIZER) { RubbleParticleOptions.CODEC }}
    val SMOKE = PARTICLES.registerK("smoke") { createParticleType(false, SmokeParticleOptions.DESERIALIZER) { SmokeParticleOptions.CODEC }}

    @Suppress("DEPRECATION")
    private fun <T : ParticleOptions> createParticleType(overrideLimiter: Boolean, deserializer: ParticleOptions.Deserializer<T>, codec: (ParticleType<T>) -> Codec<T>): ParticleType<T> =
        object : ParticleType<T>(overrideLimiter, deserializer) {
            override fun codec() = codec(this)
        }
}
