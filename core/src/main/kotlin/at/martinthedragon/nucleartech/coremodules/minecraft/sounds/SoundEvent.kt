package at.martinthedragon.nucleartech.coremodules.minecraft.sounds

import at.martinthedragon.nucleartech.coremodules.forge.registries.IForgeRegistryEntry
import at.martinthedragon.nucleartech.coremodules.minecraft.client.resources.ResourceLocation
import at.martinthedragon.nucleartech.sorcerer.registries.RegistryCandidate

class SoundEvent(val location: ResourceLocation) : IForgeRegistryEntry, RegistryCandidate<SoundEvent> {
    @get:[JvmSynthetic JvmName("registryName_")]
    var registryName: ResourceLocation? = null

    override fun getRegistryName() = registryName
}
