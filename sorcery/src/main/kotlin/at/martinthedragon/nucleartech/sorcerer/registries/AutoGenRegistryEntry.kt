package at.martinthedragon.nucleartech.sorcerer.registries

interface AutoGenRegistryEntry<E, M> {

}

@Suppress("UNCHECKED_CAST")
fun <T> AutoGenRegistryEntry<*, *>.cast() = this as T
