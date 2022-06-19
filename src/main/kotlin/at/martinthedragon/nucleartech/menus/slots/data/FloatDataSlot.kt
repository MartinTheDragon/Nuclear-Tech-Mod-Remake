package at.martinthedragon.nucleartech.menus.slots.data

import at.martinthedragon.nucleartech.menus.NTechContainerMenu
import net.minecraft.network.FriendlyByteBuf

abstract class FloatDataSlot private constructor() : NTechDataSlot {
    private var value = 0F

    override fun getData(slot: Short) = FloatData(slot, get())

    override fun isDirty(): Boolean {
        val new = get()
        val dirty = new != value
        value = new
        return dirty
    }

    abstract fun get(): Float
    abstract fun set(value: Float)

    class FloatData(slot: Short, val value: Float) : NTechDataSlot.Data(Type.FLOAT, slot) {
        constructor(slot: Short, buffer: FriendlyByteBuf) : this(slot, buffer.readFloat())

        override fun writeToBuffer(buffer: FriendlyByteBuf) {
            super.writeToBuffer(buffer)
            buffer.writeFloat(value)
        }

        override fun handleDataUpdate(menu: NTechContainerMenu) {
            menu.handleDataUpdate(slot, value)
        }
    }

    companion object {
        @JvmStatic fun create(floatArray: FloatArray, index: Int) = object : FloatDataSlot() {
            override fun get() = floatArray[index]
            override fun set(value: Float) { floatArray[index] = value }
        }

        @JvmStatic fun create(getter: () -> Float, setter: (Float) -> Unit) = object : FloatDataSlot() {
            override fun get() = getter()
            override fun set(value: Float) { setter(value) }
        }

        @JvmStatic fun createUnsafe(getter: () -> Any, setter: (Any) -> Unit) = object : FloatDataSlot() {
            override fun get() = getter() as Float
            override fun set(value: Float) { setter(value) }
        }
    }
}
