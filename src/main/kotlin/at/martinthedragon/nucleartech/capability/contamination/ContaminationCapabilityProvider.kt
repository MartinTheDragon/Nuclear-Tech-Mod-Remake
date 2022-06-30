package at.martinthedragon.nucleartech.capability.contamination

import at.martinthedragon.nucleartech.capability.Capabilities
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.common.util.LazyOptional

class ContaminationCapabilityProvider : ICapabilitySerializable<CompoundTag> {
    private val contaminationHandler = EntityContaminationHandler()

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> =
        if (cap == Capabilities.CONTAMINATION_CAPABILITY) LazyOptional.of { contaminationHandler }.cast()
        else LazyOptional.empty()

    override fun serializeNBT(): CompoundTag = contaminationHandler.serializeNBT()

    override fun deserializeNBT(nbt: CompoundTag) {
        contaminationHandler.deserializeNBT(nbt)
    }
}
