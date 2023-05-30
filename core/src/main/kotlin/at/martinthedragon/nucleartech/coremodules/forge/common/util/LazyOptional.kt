package at.martinthedragon.nucleartech.coremodules.forge.common.util

import at.martinthedragon.nucleartech.coremodules.InjectionStatic
import java.util.function.Supplier

interface LazyOptional<T> {
    fun invalidate()

    fun <X> cast(): LazyOptional<X>

    interface Static {
        fun <T> of(supplier: Supplier<T>?): LazyOptional<T>
        fun <T> empty(): LazyOptional<T>
    }
    companion object {
        fun <T> of(supplier: Supplier<T>?) = InjectionStatic.lazyOptional.of(supplier)
        fun <T> empty() = InjectionStatic.lazyOptional.empty<T>()
    }
}
