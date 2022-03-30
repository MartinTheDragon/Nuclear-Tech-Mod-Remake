package at.martinthedragon.nucleartech.api.explosions

import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

/**
 * Implementations should be registered in [ExplosionAlgorithmRegistration]
 *
 * @see[ExplosionAlgorithmRegistration.register]
 * @see[ExplosionAlgorithmRegistration.getFactory]
 */
public interface NuclearExplosionFactory {
    /**
     * Creates an explosion in [level] at [pos] with a given [strength] value and returns whether the explosion was successfully created.
     *
     * @param[hasFallout] Whether the explosion is expected to have fallout and radiation effects.
     * @param[extraFallout] If the explosion [hasFallout], extra fallout should be applied. Can be negative, but should be positive.
     * @param[muted] Disables sound effects.
     * @param[withVfx] Whether visual effects should be shown.
     */
    public fun create(
        level: Level,
        pos: Vec3,
        strength: Int,
        hasFallout: Boolean = true,
        extraFallout: Int = 0,
        muted: Boolean = false,
        withVfx: Boolean = true
    ): Boolean
}
