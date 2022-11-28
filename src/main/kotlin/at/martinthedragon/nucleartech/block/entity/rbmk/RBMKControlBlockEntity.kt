package at.martinthedragon.nucleartech.block.entity.rbmk

import at.martinthedragon.nucleartech.api.block.entities.SoundLoopBlockEntity
import at.martinthedragon.nucleartech.api.block.entities.TickingClientBlockEntity
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.entity.RBMKDebris
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class RBMKControlBlockEntity(type: BlockEntityType<out RBMKControlBlockEntity>, pos: BlockPos, state: BlockState) : InventoryRBMKBaseBlockEntity(type, pos, state), TickingClientBlockEntity {
    open var rodLevel = 0.0
    open var targetLevel = 0.0

    open val fluxMultiplier: Double get() = rodLevel

    override val isLidRemovable = false

    var lastLevel = 0.0

    override fun clientTick(level: Level, pos: BlockPos, state: BlockState) {
        lastLevel = rodLevel
    }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        val lastLevel = rodLevel

        if (rodLevel < targetLevel) {
            rodLevel += SPEED * NuclearConfig.rbmk.controlSpeedMod.get()
            if (rodLevel > targetLevel) rodLevel = targetLevel
        }

        if (rodLevel > targetLevel) {
            rodLevel -= SPEED * NuclearConfig.rbmk.controlSpeedMod.get()
            if (rodLevel < targetLevel) rodLevel = targetLevel
        }

        if (lastLevel != rodLevel) {
            sendContinuousUpdatePacket()
            markDirty()
        }

        super.serverTick(level, pos, state)
    }

    override fun onMelt(reduce: Int) {
        super.onMelt(reduce)

        val count = 2 + levelUnchecked.random.nextInt(2)
        for (i in 0 until count) spawnDebris(RBMKDebris.DebrisType.ROD)
    }

    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(0, ItemStack.EMPTY)
    override fun isItemValid(slot: Int, stack: ItemStack) = false

    override val shouldPlaySoundLoop = false
    override val soundLoopEvent: SoundEvent get() = throw UnsupportedOperationException("No sound loop for RBMK control rods")
    override val soundLoopStateMachine = SoundLoopBlockEntity.NoopStateMachine(@Suppress("LeakingThis") this)


    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putDouble("RodLevel", rodLevel)
        tag.putDouble("TargetLevel", targetLevel)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        rodLevel = tag.getDouble("RodLevel")
        targetLevel = tag.getDouble("TargetLevel")
    }

    override fun getContinuousUpdateTag() = super.getContinuousUpdateTag().apply {
        putDouble("RodLevel", rodLevel)
    }

    override fun handleContinuousUpdatePacket(tag: CompoundTag) {
        rodLevel = tag.getDouble("RodLevel")
    }

    override fun handleUpdateTag(tag: CompoundTag) {
        super.handleUpdateTag(tag)
        handleContinuousUpdatePacket(tag)
    }

    override fun getConsoleData() = CompoundTag().apply {
        putDouble("RodLevel", rodLevel)
    }

    companion object {
        const val SPEED = 0.00277
    }
}
