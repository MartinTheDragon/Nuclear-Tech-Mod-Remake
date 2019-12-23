package at.martinthedragon.ntm.lib

import at.martinthedragon.ntm.items.ModItems
import at.martinthedragon.ntm.main.Main
import net.minecraft.client.resources.I18n
import net.minecraft.item.Item
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.Style
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

// import net.minecraft.util.text.TextComponent

// private val loreMap = emptyMap<String, String?>().toMutableMap()
// private var loreTickCount = 1200

/*fun getLoreStyleFromLanguageFile(): String {
    TODO()
}

fun formatLoreTextComponentByLanguageFile(textComponent: TextComponent, lineNumber: Int): TextComponent {
    TODO()
}*/

/*@Suppress("unused", "UNUSED_PARAMETER")
@SubscribeEvent
fun loadLore(event: TickEvent.ClientTickEvent) {
    if (event.phase == TickEvent.Phase.END) return
    if (loreTickCount >= 1200) {
        ModItems.itemBuffer.forEach {
            var i = 0
            while (true) {
                val loreKey = "${it.translationKey}.lore$i"
                if (I18n.format(loreKey) != loreKey) {
                    loreMap[loreKey] = I18n.format(loreKey)
                    i++
                } else break
            }
        }
        /*for (i in ModItems.itemBuffer.indices) {
            val loreKey = "${ModItems.itemBuffer[i].translationKey}.lore$i"
            val tmp = I18n.format(loreKey)
            if (tmp == loreKey) loreMap[loreKey] = null
            else loreMap[loreKey] = tmp
        }*/
        Main.LOGGER.debug("Lore loaded")
        loreTickCount = 0
    }
    loreTickCount++
}

fun getLore(loreKey: String): ITextComponent? = if (loreMap[loreKey] != null) StringTextComponent(loreMap[loreKey] ?: error("")).setStyle(Style().setColor(TextFormatting.GRAY)) else null

fun Item.getAllLoreOfItem(): List<ITextComponent>? {
    if (this !in ModItems.itemBuffer) return null
    val tmpLoreList = emptyList<ITextComponent>().toMutableList()
    while (true) {
        val tmp = getLore("${this.translationKey}.lore${tmpLoreList.size}")
        if (tmp != null) {
            tmpLoreList.add(tmp)
        } else break
    }
    tmpLoreList.ifEmpty { return null }
    return tmpLoreList
}*/
