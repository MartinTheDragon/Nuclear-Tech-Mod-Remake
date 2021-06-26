package at.martinthedragon.nucleartech.capabilites

import at.martinthedragon.nucleartech.radiation.EntityIrradiationHandler
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.common.util.LazyOptional

class IrradiationCapabilityProvider : ICapabilitySerializable<CompoundNBT> {
    private val irradiationHandler = EntityIrradiationHandler()

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> =
        if (cap == CapabilityIrradiationHandler.irradiationHandlerCapability) LazyOptional.of { irradiationHandler }.cast()
        else LazyOptional.empty()

    override fun serializeNBT(): CompoundNBT = irradiationHandler.serializeNBT()

    override fun deserializeNBT(nbt: CompoundNBT) {
        irradiationHandler.deserializeNBT(nbt)
    }
}
