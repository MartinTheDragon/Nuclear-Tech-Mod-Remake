package at.martinthedragon.nucleartech.coremodules.minecraft.nbt

import at.martinthedragon.nucleartech.sorcerer.Injection
import net.minecraft.nbt.NBTTagCompound

@JvmInline @Injection
value class CompoundTagImpl(val delegate: NBTTagCompound) : CompoundTag {
    override fun contains(key: String, type: Int) = delegate.hasKey(key, type)

    override fun getInt(key: String) = delegate.getInteger(key)

    override fun toString() = delegate.toString()

    class Factory : CompoundTag.Factory {
        override fun create() = CompoundTagImpl(NBTTagCompound())
    }
}
