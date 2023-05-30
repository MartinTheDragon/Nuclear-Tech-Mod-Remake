package at.martinthedragon.nucleartech.coremodules.minecraft.client.resources

import at.martinthedragon.nucleartech.sorcerer.Injection

@Injection @JvmInline
value class ResourceLocationImpl(val delegate: net.minecraft.resources.ResourceLocation) : ResourceLocation {
    override val path: String get() = delegate.path
    override val namespace: String get() = delegate.namespace

    override fun toString() = delegate.toString()

    class Factory : ResourceLocation.Factory {
        override fun create(location: String) = ResourceLocationImpl(net.minecraft.resources.ResourceLocation(location))
        override fun create(namespace: String, path: String) = ResourceLocationImpl(net.minecraft.resources.ResourceLocation(namespace, path))
        override fun isValidResourceLocation(location: String) = net.minecraft.resources.ResourceLocation.isValidResourceLocation(location)
    }
}
