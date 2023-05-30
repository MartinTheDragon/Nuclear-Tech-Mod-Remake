package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.NTech
import at.martinthedragon.nucleartech.coremodules.forge.items.IItemHandlerModifiable
import at.martinthedragon.nucleartech.coremodules.forge.network.PacketDistributor
import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPos
import at.martinthedragon.nucleartech.coremodules.minecraft.nbt.CompoundTag
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.Level
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.entity.BlockEntity
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.entity.BlockEntityType
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state.BlockState
import at.martinthedragon.nucleartech.net.NTechNetMessages
import at.martinthedragon.nucleartech.net.msg.BlockEntityUpdateMessage

open class SyncedBlockEntity(type: BlockEntityType<out SyncedBlockEntity>, blockPos: BlockPos, blockState: BlockState) : BlockEntity(type, blockPos, blockState), BlockEntityWrapper {
    override val blockPosWrapped: BlockPos get() = super.blockPos
    override val levelWrapped: Level? get() = level
    override val levelUnchecked: Level get() = getLevelUnchecked()

    override val hasInventory = false
    override fun getInventory(): IItemHandlerModifiable = throw UnsupportedOperationException("No inventory implemented")

    protected open val supportsComparators = false // not implemented yet
    override fun setChanged() {
        setChanged(true)
    }

    open fun markDirty() {
        setChanged(false)
    }

    // comparator support isn't even implemented most of the time, so no need to update those everytime
    protected open fun setChanged(updateRedstone: Boolean) {
        val level = level
        if (level != null) {
            if (level.hasChunkAt(blockPos))
                level.getChunkAt(blockPos).isUnsaved = true
            if (updateRedstone && supportsComparators && !isClientSide() && !blockState.isAir) {
                level.updateNeighbourForOutputSignal(blockPos, blockState.block)
            }
        }
    }

    override fun handleUpdateTag(tag: CompoundTag) {
        handleContinuousUpdatePacket(tag)
    }

    override fun getUpdateTag() = getContinuousUpdateTag()

    // some data only needs to be sent the first time, so this is a reduced version
    open fun getContinuousUpdateTag(): CompoundTag = super.getUpdateTag()
    open fun sendContinuousUpdatePacket() {
        if (isClientSide()) NTech.LOGGER.warn("BlockEntity at $blockPos requested update packet from client side")
        else if (isRemoved) NTech.LOGGER.warn("BlockEntity at $blockPos requested update packet but was already removed")
        else NTechNetMessages.channel.send(PacketDistributor.TRACKING_CHUNK.with(getLevelUnchecked().getChunkAt(blockPos)), BlockEntityUpdateMessage(blockPos, getContinuousUpdateTag()))
    }

    open fun handleContinuousUpdatePacket(tag: CompoundTag) {}

    fun isClientSide() = level?.isClientSide == true

    @JvmSynthetic
    @JvmName("getLevelUnchecked_")
    fun getLevelUnchecked() = level!!
}
