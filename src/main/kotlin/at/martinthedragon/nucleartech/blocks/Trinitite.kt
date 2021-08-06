package at.martinthedragon.nucleartech.blocks

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.particles.ParticleTypes
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class Trinitite(properties: Properties) : Block(properties) {
    // TODO radiation effects
    // TODO automatic rad cleanup

    override fun animateTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
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
