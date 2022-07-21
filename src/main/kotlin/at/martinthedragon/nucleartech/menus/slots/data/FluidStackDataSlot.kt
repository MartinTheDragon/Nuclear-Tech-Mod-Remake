package at.martinthedragon.nucleartech.menus.slots.data

import at.martinthedragon.nucleartech.extensions.writeRawFluidStack
import at.martinthedragon.nucleartech.fluid.MutableFluidTank
import at.martinthedragon.nucleartech.menus.NTechContainerMenu
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.fluids.FluidStack

abstract class FluidStackDataSlot private constructor() : NTechDataSlot {
    private var value = FluidStack.EMPTY

    override fun getData(slot: Short) = FluidStackData(slot, get())

    override fun isDirty(): Boolean {
        val new = get()
        val dirty = !new.isFluidEqual(value) || new.amount != value.amount
        if (dirty) value = new.copy()
        return dirty
    }

    abstract fun get(): FluidStack
    abstract fun set(value: FluidStack)

    class FluidStackData(slot: Short, val value: FluidStack) : NTechDataSlot.Data(Type.FLUID_STACK, slot) {
        constructor(slot: Short, buffer: FriendlyByteBuf) : this(slot, buffer.readFluidStack())

        override fun writeToBuffer(buffer: FriendlyByteBuf) {
            super.writeToBuffer(buffer)
            buffer.writeRawFluidStack(value)
        }

        override fun handleDataUpdate(menu: NTechContainerMenu<*>) {
            menu.handleDataUpdate(slot, value)
        }
    }

    companion object {
        @JvmStatic fun create(fluidTank: MutableFluidTank, clientSide: Boolean = false): FluidStackDataSlot = create(fluidTank::getFluid, if (clientSide) fluidTank::forceFluid else fluidTank::setFluid)

        @JvmStatic fun create(getter: () -> FluidStack, setter: (FluidStack) -> Unit) = object : FluidStackDataSlot() {
            override fun get() = getter()
            override fun set(value: FluidStack) { setter(value) }
        }

        @JvmStatic fun createUnsafe(getter: () -> Any, setter: (Any) -> Unit) = object : FluidStackDataSlot() {
            override fun get() = getter() as FluidStack
            override fun set(value: FluidStack) { setter(value) }
        }
    }
}
