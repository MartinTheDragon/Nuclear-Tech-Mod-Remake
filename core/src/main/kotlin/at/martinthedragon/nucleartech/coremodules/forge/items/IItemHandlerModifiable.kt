package at.martinthedragon.nucleartech.coremodules.forge.items

import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.ItemStack

interface IItemHandlerModifiable : IItemHandler {
    fun setStackInSlot(slot: Int, stack: ItemStack)
}
