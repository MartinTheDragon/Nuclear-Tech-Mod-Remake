package at.martinthedragon.nucleartech.menu.slots.data

import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import net.minecraft.network.FriendlyByteBuf

abstract class DoubleDataSlot private constructor() : NTechDataSlot {
    private var value = 0.0

    override fun getData(slot: Short) = DoubleData(slot, get())

    override fun isDirty(): Boolean {
        val new = get()
        val dirty = new != value
        value = new
        return dirty
    }

    abstract fun get(): Double
    abstract fun set(value: Double)

    class DoubleData(slot: Short, val value: Double) : NTechDataSlot.Data(Type.DOUBLE, slot) {
        constructor(slot: Short, buffer: FriendlyByteBuf) : this(slot, buffer.readDouble())

        override fun writeToBuffer(buffer: FriendlyByteBuf) {
            super.writeToBuffer(buffer)
            buffer.writeDouble(value)
        }

        override fun handleDataUpdate(menu: NTechContainerMenu<*>) {
            menu.handleDataUpdate(slot, value)
        }
    }

    companion object {
        @JvmStatic fun create(doubleArray: DoubleArray, index: Int) = object : DoubleDataSlot() {
            override fun get() = doubleArray[index]
            override fun set(value: Double) { doubleArray[index] = value }
        }

        @JvmStatic fun create(getter: () -> Double, setter: (Double) -> Unit) = object : DoubleDataSlot() {
            override fun get() = getter()
            override fun set(value: Double) { setter(value) }
        }

        @JvmStatic fun createUnsafe(getter: () -> Any, setter: (Any) -> Unit) = object : DoubleDataSlot() {
            override fun get() = getter() as Double
            override fun set(value: Double) { setter(value) }
        }
    }
}
