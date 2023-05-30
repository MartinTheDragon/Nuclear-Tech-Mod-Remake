package at.martinthedragon.nucleartech.registries

import at.martinthedragon.nucleartech.coremodules.forge.registries.IForgeRegistryEntry
import at.martinthedragon.nucleartech.coremodules.forge.registries.RegistryObject

interface Registry<T : IForgeRegistryEntry> {
    fun <I : T> register(name: String, sup: () -> I): RegistryObject<I>
}
