package at.martinthedragon.nucleartech.extensions

import net.minecraftforge.fluids.FluidStack

fun FluidStack.isRawFluidStackIdentical(other: FluidStack) = rawFluid == other.rawFluid && FluidStack.areFluidStackTagsEqual(this, other) && amount == other.amount
