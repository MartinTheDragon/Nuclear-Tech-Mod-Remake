package at.martinthedragon.ntm.items

import at.martinthedragon.ntm.CreativeTabs
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Rarity
import net.minecraft.util.SoundEvent
import net.minecraft.util.text.Color
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.Style
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World

class SirenTrack(val sound: SoundEvent, val volume: Float, val loop: Boolean, track: String, val color: Int) :
        Item(Properties().stacksTo(1).rarity(Rarity.UNCOMMON).tab(CreativeTabs.Templates.itemGroup)) {
    val trackName: ITextComponent = TranslationTextComponent("item.ntm.$track.name")
        .withStyle(Style.EMPTY.withColor(Color.fromRgb(color)))
    val trackType: ITextComponent = TranslationTextComponent("item.ntm.$track.type")
        .withStyle(Style.EMPTY.withColor(Color.fromRgb(color)))
    val trackRange: ITextComponent = TranslationTextComponent("item.ntm.$track.range")
        .withStyle(Style.EMPTY.withColor(Color.fromRgb(color)))

    override fun appendHoverText(
        itemStack: ItemStack,
        world: World?,
        tooltips: MutableList<ITextComponent>,
        tooltipFlag: ITooltipFlag
    ) {
        tooltips += trackName
        tooltips += trackType
        tooltips += trackRange
    }
}
