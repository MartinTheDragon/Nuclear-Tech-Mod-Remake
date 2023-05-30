@file:Suppress("FunctionName")

package at.martinthedragon.nucleartech.coremodules.minecraft.network.chat

import at.martinthedragon.nucleartech.coremodules.InjectionFactories
import at.martinthedragon.nucleartech.coremodules.minecraft.ChatFormatting
import at.martinthedragon.nucleartech.sorcerer.Injected
import java.util.function.Supplier

@Injected
interface Component {
    fun getStyle(): Style
    fun setStyle(style: Style): Component
    fun append(component: Component): Component

    interface Factory {
        fun text(text: String): Component
        fun translatable(key: String, vararg args: Any): Component
    }
}

fun TextComponent(text: String) = InjectionFactories.component.text(text)
fun TranslatableComponent(key: String, vararg args: Any) = InjectionFactories.component.translatable(key, args)

fun Component.append(text: String) = append(TextComponent(text))

fun Component.withStyle(format: Style) = setStyle(format.applyTo(getStyle()))
fun Component.withStyle(format: ChatFormatting) = setStyle(getStyle().applyFormat(format))
fun Component.withStyle(vararg formats: ChatFormatting) = setStyle(getStyle().applyFormats(*formats))

fun Component.black() = withStyle(ChatFormatting.BLACK)
fun Component.darkBlue() = withStyle(ChatFormatting.DARK_BLUE)
fun Component.darkGreen() = withStyle(ChatFormatting.DARK_GREEN)
fun Component.darkAqua() = withStyle(ChatFormatting.DARK_AQUA)
fun Component.darkRed() = withStyle(ChatFormatting.DARK_RED)
fun Component.darkPurple() = withStyle(ChatFormatting.DARK_PURPLE)
fun Component.gold() = withStyle(ChatFormatting.GOLD)
fun Component.gray() = withStyle(ChatFormatting.GRAY)
fun Component.darkGray() = withStyle(ChatFormatting.DARK_GRAY)
fun Component.blue() = withStyle(ChatFormatting.BLUE)
fun Component.green() = withStyle(ChatFormatting.GREEN)
fun Component.aqua() = withStyle(ChatFormatting.AQUA)
fun Component.red() = withStyle(ChatFormatting.RED)
fun Component.lightPurple() = withStyle(ChatFormatting.LIGHT_PURPLE)
fun Component.yellow() = withStyle(ChatFormatting.YELLOW)
fun Component.white() = withStyle(ChatFormatting.WHITE)
fun Component.obfuscated() = withStyle(ChatFormatting.OBFUSCATED)
fun Component.bold() = withStyle(ChatFormatting.BOLD)
fun Component.strikethrough() = withStyle(ChatFormatting.STRIKETHROUGH)
fun Component.underline() = withStyle(ChatFormatting.UNDERLINE)
fun Component.italic() = withStyle(ChatFormatting.ITALIC)
fun Component.reset() = withStyle(ChatFormatting.RESET)

fun Supplier<out Component>.black() = get().black()
fun Supplier<out Component>.darkBlue() = get().darkBlue()
fun Supplier<out Component>.darkGreen() = get().darkGreen()
fun Supplier<out Component>.darkAqua() = get().darkAqua()
fun Supplier<out Component>.darkRed() = get().darkRed()
fun Supplier<out Component>.darkPurple() = get().darkPurple()
fun Supplier<out Component>.gold() = get().gold()
fun Supplier<out Component>.gray() = get().gray()
fun Supplier<out Component>.darkGray() = get().darkGray()
fun Supplier<out Component>.blue() = get().blue()
fun Supplier<out Component>.green() = get().green()
fun Supplier<out Component>.aqua() = get().aqua()
fun Supplier<out Component>.red() = get().red()
fun Supplier<out Component>.lightPurple() = get().lightPurple()
fun Supplier<out Component>.yellow() = get().yellow()
fun Supplier<out Component>.white() = get().white()
fun Supplier<out Component>.obfuscated() = get().obfuscated()
fun Supplier<out Component>.bold() = get().bold()
fun Supplier<out Component>.strikethrough() = get().strikethrough()
fun Supplier<out Component>.underline() = get().underline()
fun Supplier<out Component>.italic() = get().italic()
fun Supplier<out Component>.reset() = get().reset()

fun Supplier<out Component>.append(component: Component): Component = get().append(component)
fun Component.append(component: Supplier<out Component>): Component = append(component.get())

fun Component.prependIntent(indent: String = "  ") = TextComponent(indent).append(this)

