package at.martinthedragon.ntm.items

import at.martinthedragon.ntm.CT
import net.minecraft.item.Item
import net.minecraft.item.Rarity
import net.minecraft.util.SoundEvent
import net.minecraft.util.text.Color
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.Style
import net.minecraft.util.text.TranslationTextComponent

class SirenTrack(val sound: SoundEvent, val volume: Float, val loop: Boolean, track: String, color: Color) :
        Item(Properties().maxStackSize(1).rarity(Rarity.UNCOMMON).group(CT.Templates.itemGroup)) {
    val trackName: ITextComponent = TranslationTextComponent("item.ntm.$track.name")
            .func_240703_c_(Style.field_240709_b_.func_240718_a_(color))
    val trackType: ITextComponent = TranslationTextComponent("item.ntm.$track.type")
            .func_240703_c_(Style.field_240709_b_.func_240718_a_(color))
    val trackRange: ITextComponent = TranslationTextComponent("item.ntm.$track.range")
            .func_240703_c_(Style.field_240709_b_.func_240718_a_(color))

    init {
        setRegistryName(track)
    }
}
