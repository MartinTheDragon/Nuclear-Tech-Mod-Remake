package at.martinthedragon.nucleartech.block.entity.transmitters

import at.martinthedragon.nucleartech.api.block.entities.TickingServerBlockEntity
import at.martinthedragon.nucleartech.block.entity.SyncedBlockEntity
import at.martinthedragon.nucleartech.io.AbstractTransmitter
import at.martinthedragon.nucleartech.io.TransmitterNetworks
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class AbstractTransmitterBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState) : SyncedBlockEntity(type, pos, state), TickingServerBlockEntity {
    abstract val transmitter: AbstractTransmitter<*, *, *>

    var loaded = false
        protected set
    protected var shouldRefresh = true

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        if (shouldRefresh) {
            transmitter.refresh()
            shouldRefresh = false
        }
    }

    fun refresh() {
        shouldRefresh = true
    }

    fun placeInWorld() {
        addToNetwork()
        transmitter.refresh()
    }

    fun neighborChanged(side: Direction) {
        transmitter.refresh(side)
    }

    override fun setRemoved() {
        super.setRemoved()
        removeFromNetwork()
        transmitter.remove()
    }

    override fun clearRemoved() {
        super.clearRemoved()
        addToNetwork()
    }

    fun addToNetwork() {
        loaded = true
        val level = level ?: return
        if (!level.isClientSide) {
            TransmitterNetworks.addOrphan(transmitter)
        }
    }

    fun removeFromNetwork() {
        loaded = false
        val level = level ?: return
        if (!level.isClientSide) {
            TransmitterNetworks.invalidateTransmitter(transmitter)
        }
    }
}
