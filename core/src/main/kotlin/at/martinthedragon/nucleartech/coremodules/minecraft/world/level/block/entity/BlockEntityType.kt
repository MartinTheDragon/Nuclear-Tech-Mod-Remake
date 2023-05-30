package at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.entity

import at.martinthedragon.nucleartech.coremodules.forge.registries.IForgeRegistryEntry
import at.martinthedragon.nucleartech.coremodules.minecraft.client.resources.ResourceLocation

class BlockEntityType<T : BlockEntity> : IForgeRegistryEntry {
    @get:[JvmSynthetic JvmName("registryName_")]
    var registryName: ResourceLocation? = null

    override fun getRegistryName() = registryName
}
