package at.martinthedragon.nucleartech.extensions

import net.minecraft.resources.ResourceLocation

fun ResourceLocation.prependToPath(string: String) = ResourceLocation(namespace, "$string$path")
fun ResourceLocation.appendToPath(string: String) = ResourceLocation(namespace, "$path$string")
fun ResourceLocation.surroundPath(prepend: String, append: String = prepend) = ResourceLocation(namespace, "$prepend$path$append")
