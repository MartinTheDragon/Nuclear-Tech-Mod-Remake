package at.martinthedragon.nucleartech.api.explosion

import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

/**
 * Implementations should be registered in [ExplosionAlgorithmRegistration]
 *
 * @see[ExplosionAlgorithmRegistration.register]
 * @see[ExplosionAlgorithmRegistration.getFactory]
 */
public fun interface ExplosionFactory<in T : ExplosionFactory.ExplosionParams> {
    public fun create(
        level: Level,
        pos: Vec3,
        strength: Float,
        params: T
    ): Explosion?

    public interface ExplosionParams
}

public fun <P : ExplosionFactory.ExplosionParams> ExplosionFactory<P>.createAndStart(level: Level, pos: Vec3, strength: Float, params: P): Boolean =
    create(level, pos, strength, params)?.start() == true
