package at.martinthedragon.nucleartech.sorcerer.registries

interface RegistryCandidate<out T> {

}

val <T> RegistryCandidate<T>.mirroredEntry: AutoGenRegistryEntry<T, *>
    get() = TODO()


