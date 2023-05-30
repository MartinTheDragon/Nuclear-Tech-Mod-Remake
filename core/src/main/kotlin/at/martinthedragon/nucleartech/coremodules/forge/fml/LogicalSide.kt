package at.martinthedragon.nucleartech.coremodules.forge.fml

enum class LogicalSide {
    CLIENT, SERVER;

    val isClient get() = this == CLIENT
    val isServer get() = this == SERVER
}
