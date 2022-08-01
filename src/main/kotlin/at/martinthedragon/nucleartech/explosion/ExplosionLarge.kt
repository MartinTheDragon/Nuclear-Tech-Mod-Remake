package at.martinthedragon.nucleartech.explosion

import at.martinthedragon.nucleartech.api.explosion.Explosion
import at.martinthedragon.nucleartech.api.explosion.ExplosionFactory
import at.martinthedragon.nucleartech.api.explosion.ExplosionLargeParams
import at.martinthedragon.nucleartech.particle.RubbleParticleOptions
import at.martinthedragon.nucleartech.particle.SmokeParticleOptions
import at.martinthedragon.nucleartech.particle.sendParticles
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.phys.Vec3
import kotlin.math.E
import kotlin.math.pow
import kotlin.random.Random
import net.minecraft.world.level.Explosion as VanillaExplosion

object ExplosionLarge : ExplosionFactory<ExplosionLargeParams> {

    override fun create(level: Level, pos: Vec3, strength: Float, params: ExplosionLargeParams): Explosion = ExplosionLargeInstance(level, pos, strength, params)

    fun spawnCloud(level: ServerLevel, pos: Vec3, count: Int) {
        level.sendParticles(
            SmokeParticleOptions(SmokeParticleOptions.Mode.Cloud, 1F),
            true,
            pos.x, pos.y, pos.z,
            count,
            0.0, 0.0, 0.0,
            1 + count / 1500.0
        )
    }

    fun spawnRubble(level: ServerLevel, pos: Vec3, count: Int) {
        level.sendParticles(
            RubbleParticleOptions(Blocks.STONE),
            true,
            pos.x, pos.y, pos.z,
            count,
            .75 * (1 + count / 50.0),
            1 + (count + Random.nextInt(count * 5)) / 25.0,
            .75 * (1 + count / 50.0),
            count / 15.0
        )
    }

    fun spawnShrapnel(level: ServerLevel, pos: Vec3, count: Int) {
        // TODO
    }

    fun cloudFunction(count: Int) = (850 * (1 - E.pow(-count / 15.0)) + 15).toInt()
    fun rubbleFunction(count: Int) = count * 2
    fun shrapnelFunction(count: Int) = count / 3

    private class ExplosionLargeInstance(val level: Level, val pos: Vec3, val strength: Float, val params: ExplosionLargeParams) : Explosion {
        override fun start(): Boolean {
            if (level.isClientSide) return false
            level as ServerLevel
            level.explode(null, pos.x, pos.y, pos.z, strength, params.fire, VanillaExplosion.BlockInteraction.DESTROY)
            if (params.cloud) spawnCloud(level, pos, cloudFunction(strength.toInt()))
            if (params.rubble) spawnRubble(level, pos, rubbleFunction(strength.toInt()))
            if (params.shrapnel) spawnShrapnel(level, pos, shrapnelFunction(strength.toInt()))
            return true
        }
    }
}
