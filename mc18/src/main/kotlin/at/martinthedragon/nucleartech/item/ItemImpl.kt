package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.Component
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.ComponentImpl
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.Item
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.ItemStackImpl
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.LevelImpl
import at.martinthedragon.nucleartech.registries.WrappedRegistryObjectType
import net.minecraft.world.item.TooltipFlag

class ItemImplItem<out T : Item>(val item: T) : net.minecraft.world.item.Item(Properties()), WrappedRegistryObjectType<T> {
    override fun getWrappedApi() = item

    override fun appendHoverText(itemStack: net.minecraft.world.item.ItemStack, level: net.minecraft.world.level.Level?, tooltip: MutableList<net.minecraft.network.chat.Component>, flag: TooltipFlag) {
        val wrappedTooltips = mutableListOf<Component>()
        item.appendHoverText(ItemStackImpl(itemStack), level?.let(::LevelImpl), wrappedTooltips, flag.isAdvanced)
        @Suppress("UNCHECKED_CAST")
        wrappedTooltips as MutableList<ComponentImpl>
        tooltip += wrappedTooltips.map(ComponentImpl::delegate)
    }
}
