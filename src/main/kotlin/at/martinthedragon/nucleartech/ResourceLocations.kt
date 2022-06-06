package at.martinthedragon.nucleartech

import net.minecraft.resources.ResourceLocation

fun ntm(path: String) = ResourceLocation(NuclearTech.MODID, path)

fun mc(path: String) = ResourceLocation("minecraft", path)

fun ResourceLocation.prependToPath(string: String) = ResourceLocation(namespace, "$string$path")
fun ResourceLocation.appendToPath(string: String) = ResourceLocation(namespace, "$path$string")
fun ResourceLocation.surroundPath(prepend: String, append: String = prepend) = ResourceLocation(namespace, "$prepend$path$append")
