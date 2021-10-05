package at.martinthedragon.nucleartech.hazards.blocks

import at.martinthedragon.nucleartech.world.ChunkRadiation
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.server.ServerWorld
import java.util.*

class RadioactiveBlockEffect(val radiationPerTick: Float) : HazardBlockEffect {
    override fun tick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        ChunkRadiation.incrementRadiation(world, pos, radiationPerTick)
    }
}
