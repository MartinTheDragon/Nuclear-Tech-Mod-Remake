package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.CreativeTabs
import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.extensions.append
import net.minecraft.network.chat.*
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import java.util.function.Supplier

class SirenTrackItem(val soundSupplier: Supplier<SoundEvent>, val range: Int, val loop: Boolean, val color: Int) :
    Item(Properties().stacksTo(1).rarity(Rarity.UNCOMMON).tab(CreativeTabs.Templates.tab))
{
    // lazy is used here because otherwise the descriptionId gets created too early and becomes item.minecraft.air
    val trackName: Component by lazy { TranslatableComponent(descriptionId).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(color))) }
    val trackType: Component = (if (loop) LangKeys.SIREN_TRACK_LOOP.get() else LangKeys.SIREN_TRACK_ONCE.get()).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(color)))
    val trackRange: Component = LangKeys.SIREN_TRACK_RANGE.format(range).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(color)))

    override fun getDescription(): Component = LangKeys.SIREN_TRACK_SIREN_TRACK.append(TextComponent(" - ")).append(TranslatableComponent(descriptionId))

    override fun getName(stack: ItemStack) = description

    override fun appendHoverText(itemStack: ItemStack, world: Level?, tooltips: MutableList<Component>, tooltipFlag: TooltipFlag) {
        tooltips += trackName
        tooltips += trackType
        tooltips += trackRange
    }
}
