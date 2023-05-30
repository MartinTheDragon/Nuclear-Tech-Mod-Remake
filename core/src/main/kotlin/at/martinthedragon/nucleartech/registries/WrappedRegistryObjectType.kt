package at.martinthedragon.nucleartech.registries

import at.martinthedragon.nucleartech.coremodules.forge.registries.IForgeRegistryEntry

interface WrappedRegistryObjectType<out T : IForgeRegistryEntry> {
    fun getWrappedApi(): T
}
