package at.martinthedragon.nucleartech.extensions

import net.minecraft.resources.ResourceLocation

fun ResourceLocation.prependToPath(string: String) = ResourceLocation(namespace, "$string$path")
fun ResourceLocation.appendToPath(string: String) = ResourceLocation(namespace, "$path$string")
fun ResourceLocation.surroundPath(prepend: String, append: String = prepend) = ResourceLocation(namespace, "$prepend$path$append")

fun ResourceLocation.removePrefixFromPath(prefix: String) = ResourceLocation(namespace, path.removePrefix(prefix))
fun ResourceLocation.removeSuffixFromPath(suffix: String) = ResourceLocation(namespace, path.removeSuffix(suffix))
fun ResourceLocation.removeSurroundingFromPath(string: String) = ResourceLocation(namespace, path.removeSurrounding(string))
