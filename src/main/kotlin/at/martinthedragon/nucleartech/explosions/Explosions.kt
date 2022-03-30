package at.martinthedragon.nucleartech.explosions

import at.martinthedragon.nucleartech.api.explosions.ExplosionAlgorithmRegistration
import at.martinthedragon.nucleartech.api.explosions.NuclearExplosionFactory
import at.martinthedragon.nucleartech.ntm
import net.minecraft.resources.ResourceLocation

object Explosions : ExplosionAlgorithmRegistration {
    private val builtin = ntm("nuclear_explosion_mk4")
    private val registrations = mutableMapOf<ResourceLocation, NuclearExplosionFactory>()

    override fun register(key: ResourceLocation, factory: NuclearExplosionFactory) {
        if (!registrations.contains(key)) registrations += key to factory
    }

    override fun getFactory(key: ResourceLocation): NuclearExplosionFactory? = registrations[key]

    override fun getBuiltinDefault(): NuclearExplosionFactory = registrations.getValue(builtin)
}
