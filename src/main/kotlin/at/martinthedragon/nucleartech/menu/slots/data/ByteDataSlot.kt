package at.martinthedragon.nucleartech.menu.slots.data

import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import net.minecraft.network.FriendlyByteBuf

abstract class ByteDataSlot private constructor() : NTechDataSlot {
    private var value = 0.toByte()

    override fun getData(slot: Short) = ByteData(slot, get())

    override fun isDirty(): Boolean {
        val new = get()
        val dirty = new != value
        value = new
        return dirty
    }

    abstract fun get(): Byte
    abstract fun set(value: Byte)

    class ByteData(slot: Short, val value: Byte) : NTechDataSlot.Data(Type.BYTE, slot) {
        constructor(slot: Short, buffer: FriendlyByteBuf) : this(slot, buffer.readByte())

        override fun writeToBuffer(buffer: FriendlyByteBuf) {
            super.writeToBuffer(buffer)
            buffer.writeByte(value.toInt())
        }

        override fun handleDataUpdate(menu: NTechContainerMenu<*>) {
            menu.handleDataUpdate(slot, value)
        }
    }

    companion object {
        @JvmStatic fun create(byteArray: ByteArray, index: Int) = object : ByteDataSlot() {
            override fun get() = byteArray[index]
            override fun set(value: Byte) { byteArray[index] = value }
        }

        @JvmStatic fun create(getter: () -> Byte, setter: (Byte) -> Unit) = object : ByteDataSlot() {
            override fun get() = getter()
            override fun set(value: Byte) { setter(value) }
        }

        @JvmStatic fun createUnsafe(getter: () -> Any, setter: (Any) -> Unit) = object : ByteDataSlot() {
            override fun get() = getter() as Byte
            override fun set(value: Byte) { setter(value) }
        }
    }
}
