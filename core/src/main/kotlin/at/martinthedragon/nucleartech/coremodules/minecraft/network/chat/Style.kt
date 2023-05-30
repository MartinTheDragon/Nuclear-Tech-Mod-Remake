package at.martinthedragon.nucleartech.coremodules.minecraft.network.chat

import at.martinthedragon.nucleartech.coremodules.minecraft.ChatFormatting

interface Style {
    fun applyTo(style: Style): Style
    fun applyFormat(format: ChatFormatting): Style
    fun applyFormats(vararg formats: ChatFormatting): Style
}
