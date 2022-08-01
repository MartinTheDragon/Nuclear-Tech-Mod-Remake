package at.martinthedragon.nucleartech.block

import at.martinthedragon.nucleartech.NTechTags
import at.martinthedragon.nucleartech.capability.Capabilities
import at.martinthedragon.nucleartech.capability.contamination.addEffectFromSource
import at.martinthedragon.nucleartech.capability.contamination.effect.RadiationEffect
import at.martinthedragon.nucleartech.capability.contamination.hasEffectFromSource
import at.martinthedragon.nucleartech.capability.contamination.modifyEffectFromSourceIf
import at.martinthedragon.nucleartech.config.NuclearConfig
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import java.util.*

class GlowingMyceliumBlock(properties: Properties) : DeadGrassBlock(properties) {
    override fun stepOn(level: Level, pos: BlockPos, state: BlockState, entity: Entity) {
        if (entity !is LivingEntity) return
        val capability = Capabilities.getContamination(entity) ?: return
        val sourceName = registryName!!.toString()
        if (capability.hasEffectFromSource<RadiationEffect>(sourceName))
            capability.modifyEffectFromSourceIf<RadiationEffect>(sourceName, { it.timeLeft < it.maxTime }) { it.timeLeft = it.maxTime }
        else capability.addEffectFromSource(RadiationEffect(4F, 30 * 20, false, registryName!!.toString()))
    }

    override fun randomTick(state: BlockState, level: ServerLevel, pos: BlockPos, random: Random) {
        @Suppress("DEPRECATION")
        super.randomTick(state, level, pos, random)

        if (NuclearConfig.world.enableGlowingMyceliumSpread.get() && random.nextInt(8) == 0) for (i in -1..1) { // spread
            for (j in -1..1) {
                for (k in -1..1) {
                    val otherBlockPos = pos.offset(i, j, k)
                    val otherBlock = level.getBlockState(otherBlockPos)
                    if (otherBlock.isSolidRender(level, otherBlockPos) && canExist(state, level, otherBlockPos) && (otherBlock.`is`(NTechTags.Blocks.GLOWING_MYCELIUM_SPREADABLE))) {
                        level.setBlockAndUpdate(otherBlockPos, NTechBlocks.glowingMycelium.get().defaultBlockState())
                    }
                }
            }
        }

        // add new mushrooms
        if (level.isEmptyBlock(pos.above()) && random.nextInt(10) == 0 && NTechBlocks.glowingMushroom.get().defaultBlockState().canSurvive(level, pos.above())) {
            var count = 0
            for (i in -5..4) {
                for (j in -5..5) {
                    for (k in -5..4) {
                        val block = level.getBlockState(pos.offset(i, j, k))
                        if (block.`is`(NTechBlocks.glowingMushroom.get())) {
                            count++
                        }
                    }
                }
            }
            if (count == 0) level.setBlockAndUpdate(pos.above(), NTechBlocks.glowingMushroom.get().defaultBlockState())
        }
    }

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
