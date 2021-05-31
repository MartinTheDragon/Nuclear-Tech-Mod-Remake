package at.martinthedragon.ntm.items

import at.martinthedragon.ntm.CT
import net.minecraft.client.resources.I18n
import net.minecraft.item.Item
import net.minecraft.item.Rarity
import net.minecraft.util.SoundEvent
import net.minecraft.util.text.Color
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.Style
import net.minecraft.util.text.TranslationTextComponent

class SirenTrack(val sound: SoundEvent, val volume: Float, val loop: Boolean, track: String, val color: Int) :
        Item(Properties().stacksTo(1).rarity(Rarity.UNCOMMON).tab(CT.Templates.itemGroup)) {
    val trackName: ITextComponent = TranslationTextComponent("item.ntm.$track.name")
        .withStyle(Style.EMPTY.withColor(Color.fromRgb(color)))
    val trackType: ITextComponent = TranslationTextComponent("item.ntm.$track.type")
        .withStyle(Style.EMPTY.withColor(Color.fromRgb(color)))
    val trackRange: ITextComponent = TranslationTextComponent("item.ntm.$track.range")
        .withStyle(Style.EMPTY.withColor(Color.fromRgb(color)))

    init {
        setRegistryName(track)
    }
}
