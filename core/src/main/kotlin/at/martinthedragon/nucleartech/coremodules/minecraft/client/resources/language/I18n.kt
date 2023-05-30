package at.martinthedragon.nucleartech.coremodules.minecraft.client.resources.language

import at.martinthedragon.nucleartech.coremodules.InjectionStatic

interface I18n {
    fun get(key: String, vararg args: Any?): String
    fun exists(key: String): Boolean

    companion object {
        fun get(key: String, vararg args: Any?) = InjectionStatic.clientI18n.get(key, args)
        fun exists(key: String) = InjectionStatic.clientI18n.exists(key)
    }
}
