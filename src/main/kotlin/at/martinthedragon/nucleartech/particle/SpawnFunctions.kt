package at.martinthedragon.nucleartech.particle

import net.minecraft.core.particles.ParticleOptions
import net.minecraft.server.level.ServerLevel

fun <T : ParticleOptions> ServerLevel.sendParticles(
    particle: T,
    biggerRange: Boolean,
    x: Double, y: Double, z: Double,
    count: Int,
    dx: Double, dy: Double, dz: Double,
    maxSpeed: Double
) {
    for (player in players())
        sendParticles(player, particle, biggerRange, x, y, z, count, dx, dy, dz, maxSpeed)
}
