package at.martinthedragon.nucleartech.menu.slots.data

import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import net.minecraft.network.FriendlyByteBuf

abstract class IntDataSlot private constructor() : NTechDataSlot {
    private var value = 0

    override fun getData(slot: Short) = IntData(slot, get())

    override fun isDirty(): Boolean {
        val new = get()
        val dirty = new != value
        value = new
        return dirty
    }

    abstract fun get(): Int
    abstract fun set(value: Int)

    class IntData(slot: Short, val value: Int) : NTechDataSlot.Data(Type.INT, slot) {
        constructor(slot: Short, buffer: FriendlyByteBuf) : this(slot, buffer.readInt())

        override fun writeToBuffer(buffer: FriendlyByteBuf) {
            super.writeToBuffer(buffer)
            buffer.writeInt(value)
        }

        override fun handleDataUpdate(menu: NTechContainerMenu<*>) {
            menu.handleDataUpdate(slot, value)
        }
    }

    companion object {
        @JvmStatic fun create(intArray: IntArray, index: Int) = object : IntDataSlot() {
            override fun get() = intArray[index]
            override fun set(value: Int) { intArray[index] = value }
        }

        @JvmStatic fun create(getter: () -> Int, setter: (Int) -> Unit) = object : IntDataSlot() {
            override fun get() = getter()
            override fun set(value: Int) { setter(value) }
        }

        @JvmStatic fun createUnsafe(getter: () -> Any, setter: (Any) -> Unit) = object : IntDataSlot() {
            override fun get() = getter() as Int
            override fun set(value: Int) { setter(value) }
        }
    }
}
