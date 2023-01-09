package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.networking.BlockEntityUpdateMessage
import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.Connection
import net.minecraft.network.protocol.PacketFlow
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.items.IItemHandlerModifiable
import net.minecraftforge.network.PacketDistributor

open class SyncedBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState) : BlockEntity(type, pos, state), BlockEntityWrapper {
    override val blockPosWrapped: BlockPos get() = super.getBlockPos()
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
            @Suppress("DEPRECATION")
            if (level.hasChunkAt(worldPosition))
                level.getChunkAt(worldPosition).isUnsaved = true
            if (updateRedstone && supportsComparators && !level.isClientSide && !blockState.isAir) {
                level.updateNeighbourForOutputSignal(worldPosition, blockState.block)
            }
        }
    }

    override fun handleUpdateTag(tag: CompoundTag) {
        handleContinuousUpdatePacket(tag)
    }

    override fun getUpdateTag() = getContinuousUpdateTag()
    override fun getUpdatePacket(): ClientboundBlockEntityDataPacket = ClientboundBlockEntityDataPacket.create(this)
    override fun onDataPacket(net: Connection, pkt: ClientboundBlockEntityDataPacket) {
        if (isClientSide() && net.direction == PacketFlow.CLIENTBOUND) {
            val tag = pkt.tag ?: return
            handleUpdateTag(tag)
        }
    }

    // some data only needs to be sent the first time, so this is a reduced version
    open fun getContinuousUpdateTag(): CompoundTag = super.getUpdateTag()
    open fun sendContinuousUpdatePacket() {
        if (isClientSide()) NuclearTech.LOGGER.warn("BlockEntity at $blockPos requested update packet from client side")
        else if (isRemoved) NuclearTech.LOGGER.warn("BlockEntity at $blockPos requested update packet but was already removed")
        else NuclearPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with { getLevelUnchecked().getChunkAt(worldPosition) }, BlockEntityUpdateMessage(blockPos, getContinuousUpdateTag()))
    }

    open fun handleContinuousUpdatePacket(tag: CompoundTag) {}

    fun isClientSide() = level?.isClientSide == true

    @JvmSynthetic
    @JvmName("getLevelUnchecked_")
    fun getLevelUnchecked() = level!!
}
