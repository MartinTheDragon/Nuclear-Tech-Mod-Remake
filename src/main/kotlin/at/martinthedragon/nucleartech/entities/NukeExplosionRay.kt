package at.martinthedragon.nucleartech.entities

import at.martinthedragon.nucleartech.NuclearTech
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.IntTag
import net.minecraft.nbt.ListTag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.phys.Vec3
import kotlin.math.*

class NukeExplosionRay(
    val world: Level,
    val pos: BlockPos,
    val strength: Int,
    val length: Int
) {
    private val tips = mutableListOf<Vec3>()
    val tipsCount: Int
        get() = tips.size
    private var startY = 0
    private var startCircumference = 0
    var initialized = false
        private set
    private var processed = 0

    fun collectTips(count: Int) {
        var amountProcessed = 0
        val bow = strength * PI
        val bowCount = ceil(bow).toInt()

        for (v in startY..bowCount) {
            val part = PI / bow
            val rot = part * -v
            val height = Vec3(0.0, -strength.toDouble(), 0.0).zRot(rot.toFloat())
            val y = height.y
            val sectionRad = sqrt(strength.toFloat().pow(2F) - y.toFloat().pow(2F))
            val circumference = 2 * PI * sectionRad

            for (r in startCircumference..circumference.toInt()) {
                val vec = Vec3(sectionRad.toDouble(), y, 0.0).normalize().yRot((360 / circumference * r).toFloat())
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

                    val block = world.getBlockState(newBlockPos)

                    remaining -= if (block.material.isLiquid) 2.5F.pow(7.5F - fac.toFloat())
                    else block.getExplosionResistance(world, newBlockPos, null).pow(7.5F - fac.toFloat())

                    if (remaining > 0 && !block.isAir) lastPos = newPos // this version of isAir will be removed in 1.17

                    if (remaining <= 0 || i + 1 > length) {
                        if (tips.size < Int.MAX_VALUE - 100 && lastPos != null)
                            tips += lastPos
                        break
                    }
                }

                amountProcessed++

                if (amountProcessed >= count) {
                    startY = v
                    startCircumference++
                    return
                }
            }
        }
        initialized = true // ready to process
    }

    fun processTips(count: Int) {
        var processedBlocks = 0
        var destroyedBlocks = 0

        for (l in 0 until Int.MAX_VALUE) {
            if (destroyedBlocks >= count) return
            if (processedBlocks >= count * 50) return

            if (l > tipsCount - 1) break
            if (tips.isEmpty()) return

            val tip = tips.removeLast()

            world.setBlockAndUpdate(BlockPos(tip), Blocks.AIR.defaultBlockState())

            val vector = Vec3(
                tip.x - pos.x,
                tip.y - pos.y,
                tip.z - pos.z
            )

            val normalVector = vector.normalize()

            for (distance in 0..vector.length().toInt()) {
                val distanceDouble = distance.toDouble()
                val currentPos = normalVector.multiply(distanceDouble, distanceDouble, distanceDouble)
                    .add(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble())
                    .let { BlockPos(it.x.roundToInt(), it.y.roundToInt(), it.z.roundToInt()) }
                if (!world.getBlockState(currentPos).isAir) {
                    world.setBlock(currentPos, Blocks.AIR.defaultBlockState(), 0b10) // only sends the removed block. does not update neighbors
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
        posNbt.add(IntTag.valueOf(pos.x))
        posNbt.add(IntTag.valueOf(pos.y))
        posNbt.add(IntTag.valueOf(pos.z))
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

        nbt.putInt("StartY", startY)
        nbt.putInt("StartCircumference", startCircumference)
        nbt.putBoolean("Initialized", initialized)
        nbt.putInt("Processed", processed)
        return nbt
    }

    companion object {
        fun deserialize(world: Level, nbt: CompoundTag): NukeExplosionRay {
            val posNbt = nbt.getList("Pos", 3)
            val explosion = NukeExplosionRay(
                world,
                BlockPos(posNbt.getInt(0), posNbt.getInt(1), posNbt.getInt(2)),
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
                startY = nbt.getInt("StartY")
                startCircumference = nbt.getInt("StartCircumference")
                initialized = nbt.getBoolean("Initialized")
                processed = nbt.getInt("Processed")
            }
            return explosion
        }
    }
}
