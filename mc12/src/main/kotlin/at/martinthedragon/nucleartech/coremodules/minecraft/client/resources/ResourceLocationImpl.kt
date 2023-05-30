package at.martinthedragon.nucleartech.coremodules.minecraft.client.resources

import at.martinthedragon.nucleartech.sorcerer.Injection

@Injection @JvmInline
value class ResourceLocationImpl(val delegate: net.minecraft.util.ResourceLocation) : ResourceLocation {
    override val path: String get() = delegate.resourcePath
    override val namespace: String get() = delegate.resourceDomain

    override fun toString() = delegate.toString()

    class Factory : ResourceLocation.Factory {
        override fun create(location: String) = ResourceLocationImpl(net.minecraft.util.ResourceLocation(location))
        override fun create(namespace: String, path: String) = ResourceLocationImpl(net.minecraft.util.ResourceLocation(namespace, path))
        override fun isValidResourceLocation(location: String) = true // theoretically
    }
}
