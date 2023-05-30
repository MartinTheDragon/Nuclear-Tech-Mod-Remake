package at.martinthedragon.nucleartech.coremodules.minecraft.client.resources.language

class I18nImpl : I18n {
    override fun get(key: String, vararg args: Any?): String = net.minecraft.client.resources.I18n.format(key, args)
    override fun exists(key: String): Boolean = net.minecraft.client.resources.I18n.hasKey(key)
}
