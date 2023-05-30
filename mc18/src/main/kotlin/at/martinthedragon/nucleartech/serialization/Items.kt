package at.martinthedragon.nucleartech.serialization

import at.martinthedragon.nucleartech.extensions.getList
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.Tag
import net.minecraft.world.item.ItemStack
import kotlin.experimental.and

// ContainerHelper#saveAllItems but more usable
fun saveItemsToTag(tag: CompoundTag, items: Collection<ItemStack>, force: Boolean = true): CompoundTag {
    val listTag = ListTag()
    for ((index, item) in items.withIndex()) if (!item.isEmpty) {
        val slotTag = CompoundTag()
        slotTag.putByte(NBTKeys.SLOT, index.toByte())
        item.save(slotTag)
        listTag.add(slotTag)
    }
    if (!listTag.isEmpty() || force)
        tag.put(NBTKeys.ITEMS, listTag)

    return tag
}

fun <L : MutableList<ItemStack>> loadItemsFromTagToList(tag: CompoundTag, destination: L): L {
    val listTag = tag.getList(NBTKeys.ITEMS, Tag.TAG_COMPOUND)
    for (index in 0 until listTag.size) {
        val slotTag = listTag.getCompound(index)
        val slot = slotTag.getByte(NBTKeys.SLOT) and 0xFF.toByte()
        if (slot in destination.indices)
            destination[slot.toInt()] = ItemStack.of(slotTag)
    }
    return destination
}
