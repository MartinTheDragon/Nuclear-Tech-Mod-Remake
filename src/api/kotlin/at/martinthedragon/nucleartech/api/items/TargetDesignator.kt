package at.martinthedragon.nucleartech.api.items

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.Level

public interface TargetDesignator {
    public fun hasValidTarget(level: Level, tag: CompoundTag, launchPos: BlockPos): Boolean
    public fun getTargetPos(level: Level, tag: CompoundTag, launchPos: BlockPos): BlockPos
}
