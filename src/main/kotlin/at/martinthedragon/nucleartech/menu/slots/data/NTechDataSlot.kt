package at.martinthedragon.nucleartech.menu.slots.data

import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.fluids.FluidStack
import kotlin.reflect.KClass

sealed interface NTechDataSlot {
    fun getData(slot: Short): Data
    fun isDirty(): Boolean

    sealed class Data(val type: Type, val slot: Short) {
        enum class Type(val type: KClass<*>, private val default: Any, val createTracker: (getter: () -> Any, setter: (Any) -> Unit) -> NTechDataSlot, val bufferLoader: (slot: Short, buffer: FriendlyByteBuf) -> Data) {
            BOOLEAN(Boolean::class, false, BooleanDataSlot::createUnsafe, BooleanDataSlot::BooleanData),
            BYTE(Byte::class, 0.toByte(), ByteDataSlot::createUnsafe, ByteDataSlot::ByteData),
            SHORT(Short::class, 0.toShort(), ShortDataSlot::createUnsafe, ShortDataSlot::ShortData),
            INT(Int::class, 0, IntDataSlot::createUnsafe, IntDataSlot::IntData),
            LONG(Long::class, 0L, LongDataSlot::createUnsafe, LongDataSlot::LongData),
            FLOAT(Float::class, 0F, FloatDataSlot::createUnsafe, FloatDataSlot::FloatData),
            DOUBLE(Double::class, 0.0, DoubleDataSlot::createUnsafe, DoubleDataSlot::DoubleData),

            FLUID_STACK(FluidStack::class, FluidStack.EMPTY, FluidStackDataSlot::createUnsafe, FluidStackDataSlot::FluidStackData),
            ;

            @Suppress("UNCHECKED_CAST")
            fun <T> getDefault() = default as T

            companion object {
                fun getForClass(type: KClass<*>) = values().find { it.type == type }
            }
        }

        open fun writeToBuffer(buffer: FriendlyByteBuf) {
            buffer.writeEnum(type)
            buffer.writeShort(slot.toInt())
        }

        abstract fun handleDataUpdate(menu: NTechContainerMenu<*>)

        companion object {
            fun readFromBuffer(buffer: FriendlyByteBuf): Data {
                val type = buffer.readEnum(Type::class.java)
                val slot = buffer.readShort()
                return type.bufferLoader(slot, buffer)
            }
        }
    }
}
