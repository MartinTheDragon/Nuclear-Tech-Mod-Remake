package at.martinthedragon.nucleartech.block.rbmk

import at.martinthedragon.nucleartech.particle.ModParticles
import at.martinthedragon.nucleartech.particle.sendParticles
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import java.util.*

open class RBMKBurningDebrisBlock(properties: Properties) : RBMKDebrisBlock(properties) {
    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: Random) {
        level.scheduleTick(pos, this, getNextTickSchedule(random))

        if (random.nextInt(5) == 0) {
            spawnFlame(level, pos, random)
            level.playSound(null, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F)
        }

        // TODO meltdown gas

        // TODO extinguish
    }

    override fun onPlace(oldState: BlockState, level: Level, pos: BlockPos, newState: BlockState, p_220082_5_: Boolean) {
        if (level is ServerLevel) {
            level.scheduleTick(pos, this, getNextTickSchedule(level.random))
            if (level.random.nextInt(3) == 0) spawnFlame(level, pos, level.random)
        }
    }

    private fun spawnFlame(level: ServerLevel, pos: BlockPos, random: Random) {
        level.sendParticles(
            ModParticles.RBMK_FIRE.get(), true,
            pos.x + 0.25 + random.nextDouble() * 0.5,
            pos.y + 1.75,
            pos.z + 0.25 + random.nextDouble() * 0.5,
            1,
            0.0, 0.0, 0.0, 0.0
        )
    }

    protected open fun getNextTickSchedule(random: Random) = 100 + random.nextInt(20)
}
