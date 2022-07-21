package at.martinthedragon.nucleartech.menus.slots.data

import at.martinthedragon.nucleartech.menus.NTechContainerMenu
import net.minecraft.network.FriendlyByteBuf

abstract class ShortDataSlot private constructor() : NTechDataSlot {
    private var value = 0.toShort()

    override fun getData(slot: Short) = ShortData(slot, get())

    override fun isDirty(): Boolean {
        val new = get()
        val dirty = new != value
        value = new
        return dirty
    }

    abstract fun get(): Short
    abstract fun set(value: Short)

    class ShortData(slot: Short, val value: Short) : NTechDataSlot.Data(Type.SHORT, slot) {
        constructor(slot: Short, buffer: FriendlyByteBuf) : this(slot, buffer.readShort())

        override fun writeToBuffer(buffer: FriendlyByteBuf) {
            super.writeToBuffer(buffer)
            buffer.writeShort(value.toInt())
        }

        override fun handleDataUpdate(menu: NTechContainerMenu<*>) {
            menu.handleDataUpdate(slot, value)
        }
    }

    companion object {
        @JvmStatic fun create(shortArray: ShortArray, index: Int) = object : ShortDataSlot() {
            override fun get() = shortArray[index]
            override fun set(value: Short) { shortArray[index] = value }
        }

        @JvmStatic fun create(getter: () -> Short, setter: (Short) -> Unit) = object : ShortDataSlot() {
            override fun get() = getter()
            override fun set(value: Short) { setter(value) }
        }

        @JvmStatic fun createUnsafe(getter: () -> Any, setter: (Any) -> Unit) = object : ShortDataSlot() {
            override fun get() = getter() as Short
            override fun set(value: Short) { setter(value) }
        }
    }
}
