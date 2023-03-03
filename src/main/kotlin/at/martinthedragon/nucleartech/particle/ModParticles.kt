package at.martinthedragon.nucleartech.particle

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.PARTICLES
import at.martinthedragon.nucleartech.registerK
import com.mojang.serialization.Codec
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.particles.SimpleParticleType

object ModParticles {
    val CONTRAIL = PARTICLES.registerK("contrail") { createParticleType(false, ContrailParticleOptions.DESERIALIZER) { ContrailParticleOptions.CODEC }}
    val MINI_NUKE_CLOUD = PARTICLES.registerK("mini_nuke_cloud") { SimpleParticleType(true) }
    val MINI_NUKE_CLOUD_BALEFIRE = PARTICLES.registerK("mini_nuke_cloud_balefire") { SimpleParticleType(true) }
    val MINI_NUKE_FLASH = PARTICLES.registerK("mini_nuke_flash") { SimpleParticleType(true) }
    val OIL_SPILL = PARTICLES.registerK("oil_spill") { SimpleParticleType(false) }
    val RBMK_FIRE = PARTICLES.registerK("rbmk_fire") { SimpleParticleType(false) }
    val RBMK_MUSH = PARTICLES.registerK("rbmk_mush") { createParticleType(false, RBMKMushParticleOptions.DESERIALIZER) { RBMKMushParticleOptions.CODEC } }
    val ROCKET_FLAME = PARTICLES.registerK("rocket_flame") { SimpleParticleType(true) }
    val RUBBLE = PARTICLES.registerK("rubble") { createParticleType(false, RubbleParticleOptions.DESERIALIZER) { RubbleParticleOptions.CODEC }}
    val SHOCKWAVE = PARTICLES.registerK("shockwave") { SimpleParticleType(true) }
    val SMOKE = PARTICLES.registerK("smoke") { createParticleType(false, SmokeParticleOptions.DESERIALIZER) { SmokeParticleOptions.CODEC }}
    val VOLCANO_SMOKE = PARTICLES.registerK("volcano_smoke") { SimpleParticleType(true) }

    @Suppress("DEPRECATION")
    private fun <T : ParticleOptions> createParticleType(overrideLimiter: Boolean, deserializer: ParticleOptions.Deserializer<T>, codec: (ParticleType<T>) -> Codec<T>): ParticleType<T> =
        object : ParticleType<T>(overrideLimiter, deserializer) {
            override fun codec() = codec(this)
        }
}
