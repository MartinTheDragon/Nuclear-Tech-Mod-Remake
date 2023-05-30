package at.martinthedragon.nucleartech.coremodules.minecraft.nbt

import at.martinthedragon.nucleartech.coremodules.InjectionFactories
import at.martinthedragon.nucleartech.sorcerer.Injected

@Injected
interface CompoundTag : Tag {
    fun contains(key: String, type: Int): Boolean

    fun getInt(key: String): Int
    fun getLong(key: String): Long

    fun putInt(key: String, value: Int)
    fun putLong(key: String, value: Long)

    override fun toString(): String
    override fun equals(other: Any?): Boolean
    override fun hashCode(): Int

    interface Factory {
        fun create(): CompoundTag
    }
}

fun CompoundTag() = InjectionFactories.compoundTag.create()
