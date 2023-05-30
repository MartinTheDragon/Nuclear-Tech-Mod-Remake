package at.martinthedragon.nucleartech.coremodules.forge.common.capabilities

import at.martinthedragon.nucleartech.coremodules.forge.common.util.LazyOptional

interface Capability<T> {
    fun <R> orEmpty(toCheck: Capability<R>, instance: LazyOptional<T>): LazyOptional<R>
}
