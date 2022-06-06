package at.martinthedragon.nucleartech.api.explosions

import net.minecraft.resources.ResourceLocation

public interface ExplosionAlgorithmRegistration {
    /** Registers a [factory] with the under the corresponding [params] class to be later used via [getFactory] */
    public fun <P : ExplosionFactory.ExplosionParams> register(key: ResourceLocation, params: Class<out P>, factory: ExplosionFactory<P>)

    public fun <P : ExplosionFactory.ExplosionParams> getFactory(key: ResourceLocation): ExplosionFactory<P>?

    public fun <P : ExplosionFactory.ExplosionParams> getFactory(params: Class<out P>): ExplosionFactory<P>?

    public fun getParamsClass(key: ResourceLocation): Class<out ExplosionFactory.ExplosionParams>?

    public fun getParamsClass(factory: ExplosionFactory<*>): Class<out ExplosionFactory.ExplosionParams>?

    /** Returns a [ExplosionFactory] for the Mk4 Explosion algorithm */
    public fun getBuiltinDefault(): ExplosionFactory<NuclearExplosionMk4Params>

    public object Defaults {
        public val MK4: ResourceLocation = ResourceLocation("nucleartech", "nuclear_explosion_mk4")
        public val EXPLOSION_LARGE: ResourceLocation = ResourceLocation("nucleartech", "explosion_large")
    }
}
