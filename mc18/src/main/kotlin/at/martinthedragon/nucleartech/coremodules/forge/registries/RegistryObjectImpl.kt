package at.martinthedragon.nucleartech.coremodules.forge.registries

import at.martinthedragon.nucleartech.coremodules.minecraft.client.resources.ResourceLocation
import at.martinthedragon.nucleartech.coremodules.minecraft.client.resources.ResourceLocationImpl
import at.martinthedragon.nucleartech.registries.WrappedRegistryObjectType

@JvmInline
value class RegistryObjectImpl<T : IForgeRegistryEntry>(val delegate: net.minecraftforge.registries.RegistryObject<out WrappedRegistryObjectType<T>>) : RegistryObject<T> {
    override val id: ResourceLocation get() = ResourceLocationImpl(delegate.id)
    override fun get() = delegate.get().getWrappedApi()
}
