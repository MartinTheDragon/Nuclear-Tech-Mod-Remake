package at.martinthedragon.nucleartech.coremodules.minecraft.client.resources

import at.martinthedragon.nucleartech.coremodules.InjectionFactories
import at.martinthedragon.nucleartech.sorcerer.Injected

@Injected
interface ResourceLocation {
    val path: String
    val namespace: String

    override fun toString(): String
    override fun equals(other: Any?): Boolean
    override fun hashCode(): Int

    interface Factory {
        fun create(location: String): ResourceLocation
        fun create(namespace: String, path: String): ResourceLocation
        fun isValidResourceLocation(location: String): Boolean
    }

    companion object {
        fun isValidResourceLocation(location: String) = InjectionFactories.resourceLocation.isValidResourceLocation(location)
    }
}

fun ResourceLocation(location: String) = InjectionFactories.resourceLocation.create(location)
fun ResourceLocation(namespace: String, path: String) = InjectionFactories.resourceLocation.create(namespace, path)
