package at.martinthedragon.nucleartech.coremodules.minecraft.client

import at.martinthedragon.nucleartech.coremodules.InjectionStatic
import at.martinthedragon.nucleartech.coremodules.minecraft.client.gui.screens.Screen
import at.martinthedragon.nucleartech.coremodules.minecraft.client.multiplayer.ClientLevel
import at.martinthedragon.nucleartech.coremodules.minecraft.client.player.LocalPlayer
import at.martinthedragon.nucleartech.coremodules.minecraft.client.resources.language.LanguageManager

interface Minecraft {
    val level: ClientLevel?
    val player: LocalPlayer?
    val languageManager: LanguageManager
    val screen: Screen

    companion object {
        val level: ClientLevel? get() = InjectionStatic.clientMinecraft.level
        val player: LocalPlayer? get() = InjectionStatic.clientMinecraft.player
        val languageManager: LanguageManager get() = InjectionStatic.clientMinecraft.languageManager
        val screen: Screen get() = InjectionStatic.clientMinecraft.screen
    }
}
