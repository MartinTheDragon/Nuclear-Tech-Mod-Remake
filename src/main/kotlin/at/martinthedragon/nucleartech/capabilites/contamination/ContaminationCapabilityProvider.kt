package at.martinthedragon.nucleartech.capabilites.contamination

import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.common.util.LazyOptional

class ContaminationCapabilityProvider : ICapabilitySerializable<CompoundNBT> {
    private val contaminationHandler = EntityContaminationHandler()

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> =
        if (cap == CapabilityContaminationHandler.contaminationHandlerCapability) LazyOptional.of { contaminationHandler }.cast()
        else LazyOptional.empty()

    override fun serializeNBT(): CompoundNBT = contaminationHandler.serializeNBT()

    override fun deserializeNBT(nbt: CompoundNBT) {
        contaminationHandler.deserializeNBT(nbt)
    }
}
