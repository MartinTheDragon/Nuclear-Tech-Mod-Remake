package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.Component
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.ComponentImpl
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.Item
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.ItemStackImpl
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.LevelImpl
import at.martinthedragon.nucleartech.registries.WrappedRegistryObjectType
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.world.World

class ItemImplItem<out T : Item> : net.minecraft.item.Item(), WrappedRegistryObjectType<T> {
    lateinit var item: @UnsafeVariance T

    override fun getWrappedApi() = item

    override fun addInformation(stack: net.minecraft.item.ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        val wrappedTooltips = mutableListOf<Component>()
        item.appendHoverText(ItemStackImpl(stack), worldIn?.let(::LevelImpl), wrappedTooltips, flagIn.isAdvanced)
        @Suppress("UNCHECKED_CAST")
        wrappedTooltips as MutableList<ComponentImpl>
        tooltip += wrappedTooltips.map(ComponentImpl::formattedText)
    }
}
