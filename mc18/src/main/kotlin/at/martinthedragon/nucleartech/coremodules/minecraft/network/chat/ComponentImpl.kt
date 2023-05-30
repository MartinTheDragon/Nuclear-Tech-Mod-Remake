package at.martinthedragon.nucleartech.coremodules.minecraft.network.chat

import at.martinthedragon.nucleartech.sorcerer.Injection
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent

@JvmInline @Injection
value class ComponentImpl(val delegate: net.minecraft.network.chat.MutableComponent) : Component {
    fun getString(): String = delegate.string

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
        override fun text(text: String) = ComponentImpl(TextComponent(text))
        override fun translatable(key: String, vararg args: Any) = ComponentImpl(TranslatableComponent(key, args))
    }
}
