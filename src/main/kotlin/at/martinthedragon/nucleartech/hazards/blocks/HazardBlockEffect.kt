package at.martinthedragon.nucleartech.hazards.blocks

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.state.BlockState
import java.util.*

interface HazardBlockEffect {
    /** Gets called every second */
    fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: Random)
}
