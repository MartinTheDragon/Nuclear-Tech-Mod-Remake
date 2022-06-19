package at.martinthedragon.nucleartech.menus.slots.data

import at.martinthedragon.nucleartech.menus.NTechContainerMenu
import net.minecraft.network.FriendlyByteBuf

abstract class BooleanDataSlot private constructor() : NTechDataSlot {
    private var value = false

    override fun getData(slot: Short) = BooleanData(slot, get())

    override fun isDirty(): Boolean {
        val new = get()
        val dirty = new != value
        value = new
        return dirty
    }

    abstract fun get(): Boolean
    abstract fun set(value: Boolean)

    class BooleanData(slot: Short, val value: Boolean) : NTechDataSlot.Data(Type.BOOLEAN, slot) {
        constructor(slot: Short, buffer: FriendlyByteBuf) : this(slot, buffer.readBoolean())

        override fun writeToBuffer(buffer: FriendlyByteBuf) {
            super.writeToBuffer(buffer)
            buffer.writeBoolean(value)
        }

        override fun handleDataUpdate(menu: NTechContainerMenu) {
            menu.handleDataUpdate(slot, value)
        }
    }

    companion object {
        @JvmStatic fun create(booleanArray: BooleanArray, index: Int) = object : BooleanDataSlot() {
            override fun get() = booleanArray[index]
            override fun set(value: Boolean) { booleanArray[index] = value }
        }

        @JvmStatic fun create(getter: () -> Boolean, setter: (Boolean) -> Unit) = object : BooleanDataSlot() {
            override fun get() = getter()
            override fun set(value: Boolean) { setter(value) }
        }

        @JvmStatic fun createUnsafe(getter: () -> Any, setter: (Any) -> Unit) = object : BooleanDataSlot() {
            override fun get() = getter() as Boolean
            override fun set(value: Boolean) { setter(value) }
        }
    }
}
