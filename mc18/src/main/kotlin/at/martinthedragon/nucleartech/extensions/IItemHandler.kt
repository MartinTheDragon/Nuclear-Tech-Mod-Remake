package at.martinthedragon.nucleartech.extensions

import net.minecraftforge.items.IItemHandler

fun IItemHandler.getItems() = buildList {
    for (slot in 0 until slots) add(getStackInSlot(slot).copy())
}
