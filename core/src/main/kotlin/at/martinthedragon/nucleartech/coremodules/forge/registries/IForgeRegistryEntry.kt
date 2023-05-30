package at.martinthedragon.nucleartech.coremodules.forge.registries

import at.martinthedragon.nucleartech.coremodules.minecraft.client.resources.ResourceLocation

interface IForgeRegistryEntry {
    fun getRegistryName(): ResourceLocation?
}
