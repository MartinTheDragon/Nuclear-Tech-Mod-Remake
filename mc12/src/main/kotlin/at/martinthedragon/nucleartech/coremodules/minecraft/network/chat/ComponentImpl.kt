package at.martinthedragon.nucleartech.coremodules.minecraft.network.chat

import at.martinthedragon.nucleartech.sorcerer.Injection
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.TextComponentTranslation

@JvmInline @Injection
value class ComponentImpl(val delegate: net.minecraft.util.text.ITextComponent) : Component {
    val formattedText: String get() = delegate.formattedText

    override fun getStyle(): Style {
        TODO("Not yet implemented")
    }

    override fun setStyle(style: Style): Component {
        TODO("Not yet implemented")
    }

    override fun append(component: Component): Component {
        TODO("Not yet implemented")
    }

    override fun toString() = delegate.toString()

    class Factory : Component.Factory {
        override fun text(text: String) = ComponentImpl(TextComponentString(text))
        override fun translatable(key: String, vararg args: Any) = ComponentImpl(TextComponentTranslation(key, args))
    }
}
