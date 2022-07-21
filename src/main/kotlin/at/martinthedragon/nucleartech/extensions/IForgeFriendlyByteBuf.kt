package at.martinthedragon.nucleartech.extensions

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.common.extensions.IForgeFriendlyByteBuf
import net.minecraftforge.fluids.FluidStack


private fun IForgeFriendlyByteBuf.self() = this as FriendlyByteBuf

fun IForgeFriendlyByteBuf.writeRawFluidStack(stack: FluidStack): Unit = with(self()) {
    if (stack.rawFluid == Fluids.EMPTY) {
        writeBoolean(false)
    } else {
        writeBoolean(true)
        writeRegistryId(stack.rawFluid)
        writeVarInt(stack.amount)
        writeNbt(stack.tag)
    }
}
