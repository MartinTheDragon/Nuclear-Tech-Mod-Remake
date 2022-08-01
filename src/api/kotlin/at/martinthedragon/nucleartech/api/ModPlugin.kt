package at.martinthedragon.nucleartech.api

import at.martinthedragon.nucleartech.api.explosion.ExplosionAlgorithmRegistration
import at.martinthedragon.nucleartech.api.hazard.radiation.HazmatRegistry
import net.minecraft.resources.ResourceLocation

/** Implementations must be annotated with [NuclearTechPlugin] and have a single constructor with no arguments for NTM to detect them. */
public interface ModPlugin {
    /** Must be a unique ID for this plugin */
    public val id: ResourceLocation

    public fun registerExplosions(explosions: ExplosionAlgorithmRegistration) {}

    public fun registerHazmatValues(hazmat: HazmatRegistry) {}

    /**
     * Called when the [runtime] is ready. The runtime can be stored in a variable for later use.
     */
    public fun onRuntime(runtime: NuclearTechRuntime) {}
}
