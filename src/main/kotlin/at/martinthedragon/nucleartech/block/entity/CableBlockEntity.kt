package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.api.block.entities.TickingServerBlockEntity
import at.martinthedragon.nucleartech.io.energy.Cable
import at.martinthedragon.nucleartech.io.energy.EnergyNetworks
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.energy.IEnergyStorage

class CableBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(BlockEntityTypes.cableBlockEntity.get(), pos, state), TickingServerBlockEntity {
    val cable = Cable(this)

    var loaded = false
        private set
    private var shouldRefresh = true

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        if (shouldRefresh) {
            cable.refresh()
            shouldRefresh = false
        }
    }

    fun refresh() {
        shouldRefresh = true
    }

    fun placeInWorld() {
        addToNetwork()
        cable.refresh()
    }

    fun neighborChanged(side: Direction) {
        cable.refresh(side)
    }

    override fun setRemoved() {
        super.setRemoved()
        removeFromNetwork()
        cable.remove()
    }

    override fun clearRemoved() {
        super.clearRemoved()
        addToNetwork()
    }

    fun addToNetwork() {
        loaded = true
        val level = level ?: return
        if (!level.isClientSide) {
            EnergyNetworks.addOrphan(cable)
        }
    }

    fun removeFromNetwork() {
        loaded = false
        val level = level ?: return
        if (!level.isClientSide) {
            EnergyNetworks.invalidateCable(cable)
        }
    }

    var capability = LazyOptional.empty<IEnergyStorage>()

    fun createCapability(): LazyOptional<IEnergyStorage> = if (cable.network == null) LazyOptional.empty() else LazyOptional.of { cable.network!! }

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
