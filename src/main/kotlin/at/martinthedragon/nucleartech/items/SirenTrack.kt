package at.martinthedragon.nucleartech.items

import at.martinthedragon.nucleartech.CreativeTabs
import at.martinthedragon.nucleartech.NuclearTech
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TextColor
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import java.util.function.Supplier

class SirenTrack(val soundSupplier: Supplier<SoundEvent>, val range: Int, val loop: Boolean, val color: Int) :
    Item(Properties().stacksTo(1).rarity(Rarity.UNCOMMON).tab(CreativeTabs.Templates.itemGroup))
{
    // lazy is used here because otherwise the descriptionId gets created too early and becomes item.minecraft.air
    val trackName: Component by lazy { TranslatableComponent(descriptionId).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(color))) }
    val trackType: Component = (
            if (loop) TranslatableComponent("item.${NuclearTech.MODID}.siren_tracks.type_loop")
            else TranslatableComponent("item.${NuclearTech.MODID}.siren_tracks.type_once"))
        .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(color)))
    val trackRange: Component = TranslatableComponent("item.${NuclearTech.MODID}.siren_tracks.range", range).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(color)))

    override fun getDescription(): Component =
        TranslatableComponent("item.${NuclearTech.MODID}.siren_tracks.siren_track")
            .append(" - ")
            .append(TranslatableComponent(descriptionId))

    override fun getName(stack: ItemStack) = description

    override fun appendHoverText(
        itemStack: ItemStack,
        world: Level?,
        tooltips: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        tooltips += trackName
        tooltips += trackType
        tooltips += trackRange
    }
}
