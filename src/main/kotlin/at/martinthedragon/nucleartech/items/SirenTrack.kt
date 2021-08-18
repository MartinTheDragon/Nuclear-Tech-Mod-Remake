package at.martinthedragon.nucleartech.items

import at.martinthedragon.nucleartech.CreativeTabs
import at.martinthedragon.nucleartech.NuclearTech
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
import java.util.function.Supplier

class SirenTrack(val soundSupplier: Supplier<SoundEvent>, val range: Int, val loop: Boolean, val color: Int) :
        Item(Properties().stacksTo(1).rarity(Rarity.UNCOMMON).tab(CreativeTabs.Templates.itemGroup))
{
    // lazy is used here because otherwise the descriptionId gets created too early and becomes item.minecraft.air
    val trackName: ITextComponent by lazy { TranslationTextComponent(descriptionId).withStyle(Style.EMPTY.withColor(Color.fromRgb(color))) }
    val trackType: ITextComponent = (
            if (loop) TranslationTextComponent("item.${NuclearTech.MODID}.siren_tracks.type_loop")
            else TranslationTextComponent("item.${NuclearTech.MODID}.siren_tracks.type_once"))
        .withStyle(Style.EMPTY.withColor(Color.fromRgb(color)))
    val trackRange: ITextComponent = TranslationTextComponent("item.${NuclearTech.MODID}.siren_tracks.range", range).withStyle(Style.EMPTY.withColor(Color.fromRgb(color)))

    override fun getDescription(): ITextComponent =
        TranslationTextComponent("item.${NuclearTech.MODID}.siren_tracks.siren_track")
            .append(" - ")
            .append(TranslationTextComponent(descriptionId))

    override fun getName(stack: ItemStack) = description

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
