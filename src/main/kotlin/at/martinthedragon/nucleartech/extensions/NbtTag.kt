package at.martinthedragon.nucleartech.extensions

import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag

fun CompoundTag.contains(key: String, type: Byte) = contains(key, type.toInt())

fun CompoundTag.getList(key: String, type: Byte): ListTag = getList(key, type.toInt())
