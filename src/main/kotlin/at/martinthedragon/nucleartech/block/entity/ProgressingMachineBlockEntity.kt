package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.api.block.entities.TickingServerBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class ProgressingMachineBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState) : BaseMachineBlockEntity(type, pos, state), TickingServerBlockEntity {
    abstract val maxProgress: Int
    var progress: Int = 0
        protected set
    var canProgress = false
        protected set

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        val couldProgress = canProgress
        canProgress = checkCanProgress()
        if (canProgress) {
            tickProgress()
            progress++

            if (progress >= maxProgress) {
                onProgressFinished()
                progress = 0
                sendContinuousUpdatePacket()
            }
        } else progress = 0
        if (couldProgress != canProgress) {
            markDirty()
            sendContinuousUpdatePacket()
        }
    }

    protected abstract fun checkCanProgress(): Boolean
    protected open fun tickProgress() {}
    protected abstract fun onProgressFinished()

    override val shouldPlaySoundLoop get() = canProgress && !isRemoved

    override fun getContinuousUpdateTag() = super.getContinuousUpdateTag().apply {
        putBoolean("IsProgressing", canProgress)
    }

    override fun handleContinuousUpdatePacket(tag: CompoundTag) {
        super.handleContinuousUpdatePacket(tag)
        canProgress = tag.getBoolean("IsProgressing")
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("Progress", progress)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        progress = tag.getInt("Progress")
    }
}
