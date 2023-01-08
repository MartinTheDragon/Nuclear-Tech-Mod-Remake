package at.martinthedragon.nucleartech.fluid

import at.martinthedragon.nucleartech.NTechTags
import at.martinthedragon.nucleartech.block.NTechBlocks
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.FluidState
import net.minecraftforge.fluids.ForgeFlowingFluid
import java.util.*

abstract class VolcanicLavaFluid(properties: Properties) : ForgeFlowingFluid(properties) {
    override fun getTickDelay(level: LevelReader) = if (level.dimensionType().ultraWarm()) super.getTickDelay(level) / 3 else super.getTickDelay(level)

    override fun isRandomlyTicking() = true

    override fun randomTick(level: Level, pos: BlockPos, fluidState: FluidState, random: Random) {
        if (!level.isClientSide) {
            val belowPos = pos.relative(Direction.DOWN)
            if (level.getFluidState(belowPos).`is`(NTechTags.Fluids.VOLCANIC_LAVA) && random.nextInt(3) != 0) return // if we aren't touching the ground, skip most of the time

            var lavaCount = 0
            var basaltCount = 0

            for (direction in Direction.values()) {
                val relativePos = pos.relative(direction)
                if (checkVolcanicLava(level, relativePos)) {
                    lavaCount++
                    continue
                }
                if (checkBasalt(level, relativePos))
                    basaltCount++
            }

            if (!isSource(fluidState) && lavaCount < 2 || random.nextBoolean() && lavaCount < 5) {
                val resultBlock = when (random.nextInt(200)) {
                    in 0..1 -> NTechBlocks.basaltSulfurOre.get().defaultBlockState()
                    2 -> NTechBlocks.basaltAsbestosOre.get().defaultBlockState()
                    3 -> NTechBlocks.basaltFluoriteOre.get().defaultBlockState()
                    in 4..13 -> if (lavaCount + basaltCount == 6 && lavaCount < 3 && checkVolcanoHint(level, pos.above(10))) NTechBlocks.basaltVolcanicGemOre.get().defaultBlockState() else Blocks.BASALT.defaultBlockState()
                    else -> Blocks.BASALT.defaultBlockState()
                }
                level.setBlockAndUpdate(pos, resultBlock)
            }
        }
    }

    private fun checkVolcanoHint(level: Level, pos: BlockPos) = checkVolcanicLava(level, pos) || checkBasalt(level, pos)
    private fun checkVolcanicLava(level: Level, pos: BlockPos) = level.getFluidState(pos).`is`(NTechTags.Fluids.VOLCANIC_LAVA)
    private fun checkBasalt(level: Level, pos: BlockPos) = level.getBlockState(pos).run { `is`(Blocks.BASALT) || `is`(NTechTags.Blocks.ORES_IN_GROUND_BASALT) }

    override fun animateTick(level: Level, pos: BlockPos, fluidState: FluidState, random: Random) {
        val abovePos = pos.above()
        if (level.getBlockState(abovePos).isAir && !level.getBlockState(abovePos).isSolidRender(level, abovePos)) {
            if (random.nextInt(100) == 0) {
                val d0 = pos.x.toDouble() + random.nextDouble()
                val d1 = pos.y.toDouble() + 1.0
                val d2 = pos.z.toDouble() + random.nextDouble()
                level.addParticle(ParticleTypes.LAVA, d0, d1, d2, 0.0, 0.0, 0.0)
                level.playLocalSound(d0, d1, d2, SoundEvents.LAVA_POP, SoundSource.BLOCKS, 0.2f + random.nextFloat() * 0.2f, 0.9f + random.nextFloat() * 0.15f, false)
            }
            if (random.nextInt(200) == 0) {
                level.playLocalSound(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), SoundEvents.LAVA_AMBIENT, SoundSource.BLOCKS, 0.2f + random.nextFloat() * 0.2f, 0.9f + random.nextFloat() * 0.15f, false)
            }
        }
    }

    class Flowing(properties: Properties) : VolcanicLavaFluid(properties) {
        override fun createFluidStateDefinition(builder: StateDefinition.Builder<Fluid, FluidState>) {
            super.createFluidStateDefinition(builder)
            builder.add(LEVEL)
        }

        override fun getAmount(fluidState: FluidState): Int = fluidState.getValue(LEVEL)
        override fun isSource(fluidState: FluidState) = false
    }

    class Source(properties: Properties) : VolcanicLavaFluid(properties) {
        override fun getAmount(fluidState: FluidState) = 8
        override fun isSource(fluidState: FluidState) = true
    }
}
