package at.martinthedragon.nucleartech.datagen.tag

import net.minecraft.data.tags.TagsProvider
import java.util.function.Supplier

fun <T> TagsProvider.TagAppender<T>.add(supplier: Supplier<out T>): TagsProvider.TagAppender<T> =
    add(supplier.get())

inline fun <reified T> TagsProvider.TagAppender<T>.add(vararg suppliers: Supplier<out T>): TagsProvider.TagAppender<T> =
    add(*suppliers.map(Supplier<out T>::get).toTypedArray())
