package at.martinthedragon.nucleartech.coremodules.minecraft.client.resources.language

class I18nImpl : I18n {
    override fun get(key: String, vararg args: Any?): String = net.minecraft.client.resources.language.I18n.get(key, *args)
    override fun exists(key: String): Boolean = net.minecraft.client.resources.language.I18n.exists(key)
}
