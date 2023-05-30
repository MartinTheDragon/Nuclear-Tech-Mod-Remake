package at.martinthedragon.nucleartech.coremodules.minecraft.world.item

import at.martinthedragon.nucleartech.sorcerer.Injection

@Injection @JvmInline
value class ItemStackImpl(val delegate: net.minecraft.item.ItemStack) : ItemStack {
    override val descriptionId: String get() = delegate.unlocalizedName
    override val hasCustomHoverName: Boolean get() = delegate.hasDisplayName()

    override fun toString() = delegate.toString()
}
