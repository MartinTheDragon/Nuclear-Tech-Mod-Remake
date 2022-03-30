package at.martinthedragon.nucleartech.api.explosions

import net.minecraft.resources.ResourceLocation

public interface ExplosionAlgorithmRegistration {
    /** Registers a [factory] with the under a [key] to be later used via [getFactory] */
    public fun register(key: ResourceLocation, factory: NuclearExplosionFactory)

    /** Returns a registered [NuclearExplosionFactory] based on the [key] */
    public fun getFactory(key: ResourceLocation): NuclearExplosionFactory?

    /** Returns a [NuclearExplosionFactory] for the Mk4 Explosion algorithm */
    public fun getBuiltinDefault(): NuclearExplosionFactory
}
