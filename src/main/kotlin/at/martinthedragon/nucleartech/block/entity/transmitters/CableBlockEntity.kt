package at.martinthedragon.nucleartech.block.entity.transmitters

import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.io.energy.Cable
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.energy.IEnergyStorage

class CableBlockEntity(pos: BlockPos, state: BlockState) : AbstractTransmitterBlockEntity(BlockEntityTypes.cableBlockEntityType.get(), pos, state) {
    override val transmitter = Cable(this)

    var capability: LazyOptional<IEnergyStorage> = LazyOptional.empty()

    fun createCapability(): LazyOptional<IEnergyStorage> = if (transmitter.network == null) LazyOptional.empty() else LazyOptional.of { transmitter.network!! }

    override fun <T : Any> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (!remove && cap == CapabilityEnergy.ENERGY) return capability.cast()
        return super.getCapability(cap, side)
    }

    override fun invalidateCaps() {
        super.invalidateCaps()
        capability.invalidate()
    }

    override fun reviveCaps() {
        super.reviveCaps()
        capability = createCapability()
    }
}
