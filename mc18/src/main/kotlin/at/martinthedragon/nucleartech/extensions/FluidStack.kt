package at.martinthedragon.nucleartech.extensions

import net.minecraft.nbt.CompoundTag
import net.minecraftforge.fluids.FluidStack

fun FluidStack.isRawFluidStackIdentical(other: FluidStack) = rawFluid == other.rawFluid && FluidStack.areFluidStackTagsEqual(this, other) && amount == other.amount

fun FluidStack.writeToNBTRaw(nbt: CompoundTag) = nbt.apply {
    putString("FluidName", rawFluid.registryName!!.toString())
    putInt("Amount", amount)

    if (tag != null) {
        put("Tag", tag)
    }
}
