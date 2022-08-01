package at.martinthedragon.nucleartech.extensions

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.TextComponent

// Because platform types
private fun MutableComponent.style(formatting: ChatFormatting): MutableComponent = withStyle(formatting)
fun MutableComponent.black() = style(ChatFormatting.BLACK)
fun MutableComponent.darkBlue() = style(ChatFormatting.DARK_BLUE)
fun MutableComponent.darkGreen() = style(ChatFormatting.DARK_GREEN)
fun MutableComponent.darkAqua() = style(ChatFormatting.DARK_AQUA)
fun MutableComponent.darkRed() = style(ChatFormatting.DARK_RED)
fun MutableComponent.darkPurple() = style(ChatFormatting.DARK_PURPLE)
fun MutableComponent.gold() = style(ChatFormatting.GOLD)
fun MutableComponent.gray() = style(ChatFormatting.GRAY)
fun MutableComponent.darkGray() = style(ChatFormatting.DARK_GRAY)
fun MutableComponent.blue() = style(ChatFormatting.BLUE)
fun MutableComponent.green() = style(ChatFormatting.GREEN)
fun MutableComponent.aqua() = style(ChatFormatting.AQUA)
fun MutableComponent.red() = style(ChatFormatting.RED)
fun MutableComponent.lightPurple() = style(ChatFormatting.LIGHT_PURPLE)
fun MutableComponent.yellow() = style(ChatFormatting.YELLOW)
fun MutableComponent.white() = style(ChatFormatting.WHITE)
fun MutableComponent.obfuscated() = style(ChatFormatting.OBFUSCATED)
fun MutableComponent.bold() = style(ChatFormatting.BOLD)
fun MutableComponent.strikethrough() = style(ChatFormatting.STRIKETHROUGH)
fun MutableComponent.underline() = style(ChatFormatting.UNDERLINE)
fun MutableComponent.italic() = style(ChatFormatting.ITALIC)
fun MutableComponent.reset() = style(ChatFormatting.RESET)
fun Component.prependIntent(indent: String = "  ") = TextComponent(indent).append(this)
