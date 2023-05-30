package at.martinthedragon.nucleartech.extensions

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.BaseComponent
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.TextComponent
import java.util.function.Supplier

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

fun Supplier<out MutableComponent>.black() = get().black()
fun Supplier<out MutableComponent>.darkBlue() = get().darkBlue()
fun Supplier<out MutableComponent>.darkGreen() = get().darkGreen()
fun Supplier<out MutableComponent>.darkAqua() = get().darkAqua()
fun Supplier<out MutableComponent>.darkRed() = get().darkRed()
fun Supplier<out MutableComponent>.darkPurple() = get().darkPurple()
fun Supplier<out MutableComponent>.gold() = get().gold()
fun Supplier<out MutableComponent>.gray() = get().gray()
fun Supplier<out MutableComponent>.darkGray() = get().darkGray()
fun Supplier<out MutableComponent>.blue() = get().blue()
fun Supplier<out MutableComponent>.green() = get().green()
fun Supplier<out MutableComponent>.aqua() = get().aqua()
fun Supplier<out MutableComponent>.red() = get().red()
fun Supplier<out MutableComponent>.lightPurple() = get().lightPurple()
fun Supplier<out MutableComponent>.yellow() = get().yellow()
fun Supplier<out MutableComponent>.white() = get().white()
fun Supplier<out MutableComponent>.obfuscated() = get().obfuscated()
fun Supplier<out MutableComponent>.bold() = get().bold()
fun Supplier<out MutableComponent>.strikethrough() = get().strikethrough()
fun Supplier<out MutableComponent>.underline() = get().underline()
fun Supplier<out MutableComponent>.italic() = get().italic()
fun Supplier<out MutableComponent>.reset() = get().reset()

fun Supplier<out BaseComponent>.append(component: Component): MutableComponent = get().append(component)
fun BaseComponent.append(component: Supplier<out Component>): MutableComponent = append(component.get())

fun Component.prependIntent(indent: String = "  ") = TextComponent(indent).append(this)
