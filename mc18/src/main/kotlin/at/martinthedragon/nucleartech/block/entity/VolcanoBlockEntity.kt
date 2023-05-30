package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.NTechTags
import at.martinthedragon.nucleartech.api.block.entities.TickingServerBlockEntity
import at.martinthedragon.nucleartech.block.VolcanoBlock
import at.martinthedragon.nucleartech.entity.VolcanicShrapnel
import at.martinthedragon.nucleartech.explosion.ExplosionVNT
import at.martinthedragon.nucleartech.fluid.NTechFluids
import at.martinthedragon.nucleartech.math.component1
import at.martinthedragon.nucleartech.math.component2
import at.martinthedragon.nucleartech.math.component3
import at.martinthedragon.nucleartech.math.toVec3Middle
import at.martinthedragon.nucleartech.particle.ModParticles
import at.martinthedragon.nucleartech.particle.sendParticles
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.phys.Vec3
import kotlin.math.abs

class VolcanoBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(BlockEntityTypes.volcanoBlockEntityType.get(), pos, state), TickingServerBlockEntity {
    private var volcanoTimer = 0

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        if (++volcanoTimer % 10 == 0) {
            if (hasVerticalChannel(state)) {
                blastMagmaChannel(level, pos)
                raiseMagma(level, pos)
            }

            blastMagmaChamber(level, pos, state)
            meltSurface(level, pos, state)
            surroundLava(level, pos)

            if (isSpewing(state)) spewBlobs(level, pos)
            if (isSmoking(state)) emitSmoke(level, pos)
        }

        if (volcanoTimer >= getUpdateRate(level, pos, state)) {
            volcanoTimer = 0

            if (canGrow(level, pos, state)) {
                level.setBlockAndUpdate(pos.above(), state)
                level.setBlockAndUpdate(pos, NTechFluids.volcanicLava.block.get().defaultBlockState())
            } else if (extinguishes(state)) {
                level.setBlockAndUpdate(pos, NTechFluids.volcanicLava.block.get().defaultBlockState())
            }
        }
    }

    private fun getUpdateRate(level: Level, pos: BlockPos, state: BlockState) = when {
        canGrow(level, pos, state) -> 60 * 60 * 20 / 250 // 250x per hour
        extinguishes(state) -> 60 * 60 * 20 // once per hour
        else -> 10
    }

    private fun canGrow(level: Level, pos: BlockPos, state: BlockState) = grows(state) && pos.y < level.maxBuildHeight - 40
    private fun grows(state: BlockState) = state.getValue(VolcanoBlock.GROWS)
    private fun extinguishes(state: BlockState) = state.getValue(VolcanoBlock.EXTINGUISHES)
    private fun smoldering(state: BlockState) = state.getValue(VolcanoBlock.SMOLDERING)

    private fun blastMagmaChannel(level: Level, pos: BlockPos) {
        val random = level.random
        ExplosionVNT.createStandard(level, pos.toVec3Middle().add(0.0, 1.0 + random.nextInt(15), 0.0), 7F).apply(volcanoExplosionAttributes).explode()
        ExplosionVNT.createStandard(level, Vec3(pos.x + 0.5 + random.nextGaussian() * 3.0, level.minBuildHeight.toDouble() + level.random.nextDouble(pos.y + 1.0 - level.minBuildHeight.toDouble()), pos.z + 0.5 + random.nextGaussian() * 3.0), 10F).apply(volcanoExplosionAttributes).explode()
    }

    private fun blastMagmaChamber(level: Level, pos: BlockPos, blockState: BlockState) {
        val size = magmaChamberSize(blockState)
        if (size <= 0) return
        val random = level.random
        for (i in 1..2) {
            val distance = size / i
            ExplosionVNT.createStandard(level, Vec3(pos.x + 0.5 + random.nextGaussian() * distance, pos.y + 0.5 + random.nextGaussian() * distance, pos.z + 0.5 + random.nextGaussian() * distance), 7F).apply(volcanoExplosionAttributes).explode()
        }
    }

    private fun raiseMagma(level: Level, pos: BlockPos) {
        val random = level.random
        val randomPos = pos.offset(random.nextInt(21) - 10, random.nextInt(16) - 5, random.nextInt(21) - 10)
        if (level.getBlockState(randomPos).isAir && level.getFluidState(randomPos.below()).`is`(NTechTags.Fluids.VOLCANIC_LAVA))
            level.setBlockAndUpdate(randomPos, NTechFluids.volcanicLava.block.get().defaultBlockState())
    }

    private fun surroundLava(level: Level, pos: BlockPos) {
        for (i in -1..1) for (j in -1..1) for (k in -1..1)
            if (i != 0 || j != 0 || k != 0)
                level.setBlockAndUpdate(pos.offset(i, j, k), NTechFluids.volcanicLava.block.get().defaultBlockState())
    }

    private fun meltSurface(level: Level, pos: BlockPos, state: BlockState) {
        val random = level.random
        val (count, radius, depth) = getSurfaceMeltingParams(state) ?: return
        for (i in 0 until count) {
            val x = (pos.x + random.nextGaussian() * radius).toInt()
            val z = (pos.z + random.nextGaussian() * radius).toInt()
            val y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z) - abs(random.nextGaussian() * depth).toInt() + 1

            val selectedPos = BlockPos(x, y, z)
            val selectedBlockState = level.getBlockState(selectedPos)
            if (!selectedBlockState.isAir && selectedBlockState.getExplosionResistance(level, selectedPos, null) < Blocks.OBSIDIAN.defaultBlockState().getExplosionResistance(level, selectedPos, null)) {
                level.setBlockAndUpdate(selectedPos, if (selectedBlockState.isSolidRender(level, selectedPos)) NTechFluids.volcanicLava.block.get().defaultBlockState() else Blocks.AIR.defaultBlockState())
            }
        }
    }

    private fun spewBlobs(level: Level, pos: BlockPos) {
        for (i in 0..2) {
            val random = level.random
            level.addFreshEntity(VolcanicShrapnel(level).apply {
                moveTo(pos.above().toVec3Middle())
                setDeltaMovement(random.nextGaussian() * 0.2, 1.0 + random.nextDouble(), random.nextGaussian() * 0.2)
            })
        }
    }

    private fun emitSmoke(level: Level, pos: BlockPos) {
        level as ServerLevel
        val (x, y, z) = pos.above(5).toVec3Middle()
        level.sendParticles(ModParticles.VOLCANO_SMOKE.get(), true, x, y, z, 1, 0.0, 0.0, 0.0, 0.0)
    }

    private fun hasVerticalChannel(blockState: BlockState) = !smoldering(blockState)
    private fun magmaChamberSize(blockState: BlockState) = if (smoldering(blockState)) 0.0 else 15.0
    private fun isSpewing(blockState: BlockState) = !smoldering(blockState)
    private fun isSmoking(blockState: BlockState) = !smoldering(blockState)

    private fun getSurfaceMeltingParams(blockState: BlockState): SurfaceMeltingParams? = when {
        smoldering(blockState) -> SurfaceMeltingParams(50, 50.0, 10.0)
        else -> null
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("VolcanoTimer", volcanoTimer)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        volcanoTimer = tag.getInt("VolcanoTimer")
    }

    companion object {
        val volcanoExplosionAttributes: ExplosionVNT.() -> Unit = {
            blockProcessor = ExplosionVNT.BlockProcessor.Default(dropChanceMutator = ExplosionVNT.DropChanceMutator.Default(0F), blockMutator = { explosion, pos -> explosion.level.setBlockAndUpdate(pos, NTechFluids.volcanicLava.block.get().defaultBlockState()) })
            entityProcessor = null
            syncer = null
        }
    }
}

/*
First: Count
Second: Radius
Third: Depth
 */
private typealias SurfaceMeltingParams = Triple<Int, Double, Double>
