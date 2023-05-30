package at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.entity

import at.martinthedragon.nucleartech.coremodules.InjectionStatic
import at.martinthedragon.nucleartech.coremodules.forge.common.capabilities.Capability
import at.martinthedragon.nucleartech.coremodules.forge.common.capabilities.CapabilityProvider
import at.martinthedragon.nucleartech.coremodules.forge.common.util.LazyOptional
import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPos
import at.martinthedragon.nucleartech.coremodules.minecraft.core.Direction
import at.martinthedragon.nucleartech.coremodules.minecraft.nbt.CompoundTag
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.Level
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state.BlockState

abstract class BlockEntity(val type: BlockEntityType<*>, val blockPos: BlockPos, val blockState: BlockState) : CapabilityProvider<BlockEntity> {
    var level: Level? = null

    var isRemoved: Boolean = false

    open fun setChanged() {
        val level = level
        if (level != null) {
            static.setChanged(level, blockPos, blockState)
        }
    }

    open fun getUpdateTag() = CompoundTag()
    open fun handleUpdateTag(tag: CompoundTag) {}

    fun triggerEvent(paramA: Int, paramB: Int): Boolean = false

    override fun <T : Any> getCapability(cap: Capability<T>, side: Direction?) =
        static.getCapability(this, cap, side)

    interface Static {
        fun setChanged(level: Level, pos: BlockPos, state: BlockState)
        fun <T : Any> getCapability(blockEntity: BlockEntity, cap: Capability<T>, side: Direction?): LazyOptional<T>
    }

    companion object {
        val static get() = InjectionStatic.blockEntity
    }
}
