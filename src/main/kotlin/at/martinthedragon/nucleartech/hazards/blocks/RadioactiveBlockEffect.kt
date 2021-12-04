package at.martinthedragon.nucleartech.hazards.blocks

import at.martinthedragon.nucleartech.world.ChunkRadiation
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.state.BlockState
import java.util.*

class RadioactiveBlockEffect(val radiationPerTick: Float) : HazardBlockEffect {
    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: Random) {
        ChunkRadiation.incrementRadiation(level, pos, radiationPerTick)
    }
}
