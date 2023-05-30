package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPos
import at.martinthedragon.nucleartech.coremodules.minecraft.nbt.CompoundTag
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.Level

interface TargetDesignator {
    fun hasValidTarget(level: Level, tag: CompoundTag, launchPos: BlockPos): Boolean
    fun getTargetPos(level: Level, tag: CompoundTag, launchPos: BlockPos): BlockPos
}
