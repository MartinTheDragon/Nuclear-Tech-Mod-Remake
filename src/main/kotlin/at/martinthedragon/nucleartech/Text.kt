package at.martinthedragon.nucleartech

import net.minecraft.network.chat.TranslatableComponent

fun ntmTranslationString(key: String) = "${NuclearTech.MODID}.$key"
fun ntmTranslation(key: String) = TranslatableComponent(ntmTranslationString(key))
fun ntmTranslation(key: String, vararg args: Any) = TranslatableComponent(ntmTranslationString(key), *args)
