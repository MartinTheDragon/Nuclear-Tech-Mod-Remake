package at.martinthedragon.nucleartech.entities

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.math.toBlockPos
import net.minecraft.core.BlockPos
import net.minecraft.core.SectionPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.DoubleTag
import net.minecraft.nbt.ListTag
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.chunk.LevelChunk
import net.minecraft.world.phys.Vec3
import kotlin.math.*

class NukeExplosionRay(
    val world: Level,
    val pos: Vec3,
    val strength: Int,
    val length: Int
) {
    private val phased = NuclearConfig.explosions.phasedExplosions.get()

    private val tips = mutableListOf<Vec3>()
    val tipsCount: Int get() = tips.size
    var initialized = false
        private set
    private var processed = 0

    private val generalisedSpiralPointMax = (2.5 * PI * strength * strength).toInt()
    private var currentSpiralPoint = 1
    private var currentSpiralPointX = PI
    private var currentSpiralPointY = 0.0

    private fun generateSpiralPointUp() {
        if (currentSpiralPoint < generalisedSpiralPointMax) {
            val x = currentSpiralPoint++ / (generalisedSpiralPointMax - 1.0) * 2 - 1
            currentSpiralPointX = acos(x)

            val y = currentSpiralPointY + 3.6 / sqrt(generalisedSpiralPointMax.toDouble()) / sqrt(1 - x * x)
            currentSpiralPointY = y % (PI * 2)
        } else {
            currentSpiralPointX = 0.0
            currentSpiralPointY = 0.0
        }
    }

    private fun getSpherical2Cartesian(): Vec3 {
        val sinX = sin(currentSpiralPointX)
        val dx = sinX * cos(currentSpiralPointY)
        val dy = cos(currentSpiralPointX)
        val dz = sinX * sin(currentSpiralPointY)
        return Vec3(dx, dy, dz)
    }

    fun collectTips(count: Int) {
        var amountProcessed = 0

        while (currentSpiralPoint < generalisedSpiralPointMax) {
            generateSpiralPointUp()
            val vec = getSpherical2Cartesian()
            var remaining = strength.toFloat()
            var lastPos: Vec3? = null

            for (i in 0 until strength) {
                if (i > length) break

                val newPos = Vec3(
                    pos.x + vec.x * i,
                    pos.y + vec.y * i,
                    pos.z + vec.z * i
                )
                val newBlockPos = BlockPos(newPos)

                val fac = (100 - i.toDouble() / strength.toDouble() * 100) * 0.07

                val block = getBlockState(world, newBlockPos)

                remaining -= if (block.material.isLiquid) 2.5F.pow(7.5F - fac.toFloat())
                else block.getExplosionResistance(world, newBlockPos, null).pow(7.5F - fac.toFloat())

                if (remaining > 0 && !block.isAir) lastPos = newPos

                if (remaining <= 0 || i + 1 > length) {
                    if (tips.size < Int.MAX_VALUE - 100 && lastPos != null)
                        tips += lastPos
                    break
                }
            }
            if (++amountProcessed >= count) return
        }
        initialized = true // ready to process
    }

    fun processTips(count: Int) {
        var processedBlocks = 0
        var destroyedBlocks = 0

        if (phased) for (l in 0 until Int.MAX_VALUE) {
            if (destroyedBlocks >= count) return
            if (processedBlocks >= count * 50) return

            if (l > tipsCount - 1) break
            if (tips.isEmpty()) return

            val tip = tips.removeLast()

            removeBlock(world, BlockPos(tip), true)

            val vector = Vec3(
                tip.x - pos.x,
                tip.y - pos.y,
                tip.z - pos.z
            )

            val unitVector = vector.normalize()

            for (distance in 0..vector.length().toInt()) {
                val distanceDouble = distance.toDouble()
                val currentPos = unitVector.multiply(distanceDouble, distanceDouble, distanceDouble).add(pos).toBlockPos()
                if (!getBlockState(world, currentPos).isAir) {
                    removeBlock(world, currentPos)
                    destroyedBlocks++
                }
                processedBlocks++
            }
        } else for (l in 0 until Int.MAX_VALUE) {
            if (destroyedBlocks >= count) return
            if (processedBlocks >= count * 50) return

            if (l > tipsCount - 1) break
            if (tips.isEmpty()) return

            val tip = tips.removeLast()

            world.setBlock(BlockPos(tip), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL)

            val vector = Vec3(
                tip.x - pos.x,
                tip.y - pos.y,
                tip.z - pos.z
            )

            val unitVector = vector.normalize()

            for (distance in 0..vector.length().toInt()) {
                val distanceDouble = distance.toDouble()
                val currentPos = unitVector.multiply(distanceDouble, distanceDouble, distanceDouble).add(pos).toBlockPos()
                if (!getBlockState(world, currentPos).isAir) {
                    world.setBlock(currentPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL)
                    destroyedBlocks++
                }
                processedBlocks++
            }
        }
        processed += count
    }

    // do not try getting the entity's data using the /data command! the huge amount of data will halt the game. no kidding, this can be megabytes worth of data
    // TODO serialize efficiently
    fun serialize(): CompoundTag {
        val nbt = CompoundTag()
        val posNbt = ListTag()
        posNbt.add(DoubleTag.valueOf(pos.x))
        posNbt.add(DoubleTag.valueOf(pos.y))
        posNbt.add(DoubleTag.valueOf(pos.z))
        nbt.put("Pos", posNbt)
        nbt.putInt("Strength", strength)
        nbt.putInt("Length", length)

        val tipsDataX = LongArray(tipsCount) { tips[it].x.toRawBits() }
        val tipsDataY = LongArray(tipsCount) { tips[it].y.toRawBits() }
        val tipsDataZ = LongArray(tipsCount) { tips[it].z.toRawBits() }
        val tipsData = CompoundTag()
        tipsData.putLongArray("TipsDataX", tipsDataX)
        tipsData.putLongArray("TipsDataY", tipsDataY)
        tipsData.putLongArray("TipsDataZ", tipsDataZ)
        nbt.put("ExplosionData", tipsData)

        nbt.putBoolean("Initialized", initialized)
        nbt.putInt("Processed", processed)
        nbt.putInt("CurrentSpiralPoint", currentSpiralPoint)
        nbt.putDouble("CurrentSpiralPointX", currentSpiralPointX)
        nbt.putDouble("CurrentSpiralPointY", currentSpiralPointY)
        return nbt
    }

    private fun getBlockState(level: Level, pos: BlockPos): BlockState {
        if (level.isOutsideBuildHeight(pos)) return Blocks.VOID_AIR.defaultBlockState()
        val chunk = getChunk(level, SectionPos.blockToSectionCoord(pos.x), SectionPos.blockToSectionCoord(pos.z))
        return chunk.getBlockState(pos)
    }

    private val changedPositions = mutableMapOf<BlockPos, BlockState>()
    private val changedTips = mutableMapOf<BlockPos, BlockState>()

    private fun removeBlock(level: Level, pos: BlockPos, tip: Boolean = false) {
        if (level.isOutsideBuildHeight(pos)) return
        val oldState = getChunk(level, SectionPos.blockToSectionCoord(pos.x), SectionPos.blockToSectionCoord(pos.z)).setBlockState(pos, Blocks.AIR.defaultBlockState(), false)
        if (oldState != null) {
            if (tip) changedTips += pos to oldState
            else changedPositions += pos to oldState
        }
    }

    fun updateAllBlocks(level: Level) {
        if (!phased) return
        for ((pos, oldState) in changedPositions) level.markAndNotifyBlock(pos, getChunk(level, SectionPos.blockToSectionCoord(pos.x), SectionPos.blockToSectionCoord(pos.z)), oldState, Blocks.AIR.defaultBlockState(), 0b10, 0)
        for ((pos, oldState) in changedTips) level.markAndNotifyBlock(pos, getChunk(level, SectionPos.blockToSectionCoord(pos.x), SectionPos.blockToSectionCoord(pos.z)), oldState, Blocks.AIR.defaultBlockState(), 0b11, 512)
        (changedPositions.keys + changedTips.keys) // update the lighting starting with the highest blocks
            .groupBy { it.y }
            .toSortedMap(Comparator.reverseOrder())
            .forEach { (_, layerPositions) ->  layerPositions.forEach { level.chunkSource.lightEngine.checkBlock(it) }}
    }

    private val chunkCache = mutableMapOf<ChunkPos, LevelChunk>()

    // has more caching
    private fun getChunk(level: Level, x: Int, z: Int) = chunkCache.computeIfAbsent(ChunkPos(x, z)) {
        level.getChunk(x, z)
    }

    companion object {
        fun deserialize(world: Level, nbt: CompoundTag): NukeExplosionRay {
            val posNbt = nbt.getList("Pos", 6)
            val explosion = NukeExplosionRay(
                world,
                Vec3(posNbt.getDouble(0), posNbt.getDouble(1), posNbt.getDouble(2)),
                nbt.getInt("Strength"), nbt.getInt("Length")
            )

            val tipsData = nbt.getCompound("ExplosionData")
            val tipsDataX = tipsData.getLongArray("TipsDataX")
            val tipsDataY = tipsData.getLongArray("TipsDataY")
            val tipsDataZ = tipsData.getLongArray("TipsDataZ")
            val dataAmount = if (tipsDataX.size == tipsDataY.size && tipsDataX.size == tipsDataZ.size) tipsDataX.size else {
                NuclearTech.LOGGER.error("NukeExplosionRay @ ${explosion.pos} has corrupted explosion data. Salvaging...")
                min(tipsDataX.size, min(tipsDataY.size, tipsDataZ.size))
            }
            for (i in 0 until dataAmount) {
                val x = Double.fromBits(tipsDataX[i])
                val y = Double.fromBits(tipsDataY[i])
                val z = Double.fromBits(tipsDataZ[i])
                explosion.tips.add(Vec3(x, y , z))
            }
            explosion.apply {
                initialized = nbt.getBoolean("Initialized")
                processed = nbt.getInt("Processed")
                currentSpiralPoint = nbt.getInt("CurrentSpiralPoint")
                currentSpiralPointX = nbt.getDouble("CurrentSpiralPointX")
                currentSpiralPointY = nbt.getDouble("CurrentSpiralPointY")
            }
            return explosion
        }
    }
}
