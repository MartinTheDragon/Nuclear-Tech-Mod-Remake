package at.martinthedragon.nucleartech.block

import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import java.util.*

class TrinititeBlock(properties: Properties) : Block(properties) {
    // TODO radiation effects
    // TODO automatic rad cleanup

    override fun animateTick(state: BlockState, world: Level, pos: BlockPos, random: Random) {
        if (random.nextInt(5) == 0) {
            world.addParticle(
                ParticleTypes.MYCELIUM,
                pos.x.toDouble() + random.nextDouble(),
                pos.y.toDouble() + 1.1,
                pos.z.toDouble() + random.nextDouble(),
                0.0, 0.0, 0.0
            )
        }
    }
}
