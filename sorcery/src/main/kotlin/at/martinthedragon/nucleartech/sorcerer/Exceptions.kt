package at.martinthedragon.nucleartech.sorcerer

internal fun unresolved(name: String): Nothing =
    throw IllegalStateException("Couldn't resolve $name")
