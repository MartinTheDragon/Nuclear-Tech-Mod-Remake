package at.martinthedragon.nucleartech.extensions

import net.minecraftforge.common.util.LazyOptional

fun <T> LazyOptional<T>.getOrNull(): T? = if (!isPresent) null else resolve().get()

inline fun <T, R> LazyOptional<T>.ifPresentInline(action: (T) -> R): R? {
    val value = getOrNull() ?: return null
    return action(value)
}
