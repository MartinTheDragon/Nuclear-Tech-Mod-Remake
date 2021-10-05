package at.martinthedragon.nucleartech.hazards.blocks

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.server.ServerWorld
import java.util.*

interface HazardBlockEffect {
    /** Gets called every second */
    fun tick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random)
}
