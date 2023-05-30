package at.martinthedragon.nucleartech.coremodules.minecraft.world.item

import at.martinthedragon.nucleartech.sorcerer.Injection

@Injection @JvmInline
value class ItemStackImpl(val delegate: net.minecraft.world.item.ItemStack) : ItemStack {
    override val descriptionId: String get() = delegate.descriptionId

    override val hasCustomHoverName: Boolean get() = delegate.hasCustomHoverName()

    override fun toString() = delegate.toString()
}
