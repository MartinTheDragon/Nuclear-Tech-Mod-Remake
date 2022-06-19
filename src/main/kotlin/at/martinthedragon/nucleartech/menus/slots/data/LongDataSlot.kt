package at.martinthedragon.nucleartech.menus.slots.data

import at.martinthedragon.nucleartech.menus.NTechContainerMenu
import net.minecraft.network.FriendlyByteBuf

abstract class LongDataSlot private constructor() : NTechDataSlot {
    private var value = 0L

    override fun getData(slot: Short) = LongData(slot, get())

    override fun isDirty(): Boolean {
        val new = get()
        val dirty = new != value
        value = new
        return dirty
    }

    abstract fun get(): Long
    abstract fun set(value: Long)

    class LongData(slot: Short, val value: Long) : NTechDataSlot.Data(Type.LONG, slot) {
        constructor(slot: Short, buffer: FriendlyByteBuf) : this(slot, buffer.readLong())

        override fun writeToBuffer(buffer: FriendlyByteBuf) {
            super.writeToBuffer(buffer)
            buffer.writeLong(value)
        }

        override fun handleDataUpdate(menu: NTechContainerMenu) {
            menu.handleDataUpdate(slot, value)
        }
    }

    companion object {
        @JvmStatic fun create(longArray: LongArray, index: Int) = object : LongDataSlot() {
            override fun get() = longArray[index]
            override fun set(value: Long) { longArray[index] = value }
        }

        @JvmStatic fun create(getter: () -> Long, setter: (Long) -> Unit) = object : LongDataSlot() {
            override fun get() = getter()
            override fun set(value: Long) { setter(value) }
        }

        @JvmStatic fun createUnsafe(getter: () -> Any, setter: (Any) -> Unit) = object : LongDataSlot() {
            override fun get() = getter() as Long
            override fun set(value: Long) { setter(value) }
        }
    }
}
