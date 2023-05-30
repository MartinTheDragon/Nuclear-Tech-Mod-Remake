package at.martinthedragon.nucleartech.extensions

import net.minecraft.core.NonNullList

fun <T> Collection<T>.toStupidMojangList(): NonNullList<T> {
    val stupidMojangList = NonNullList.createWithCapacity<T>(size)
    for (element in this) stupidMojangList.add(element)
    return stupidMojangList
}
