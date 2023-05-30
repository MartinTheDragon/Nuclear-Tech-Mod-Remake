package at.martinthedragon.nucleartech.coremodules

interface DelegatedApiImpl<T : Any> {
    val delegate: T
}
