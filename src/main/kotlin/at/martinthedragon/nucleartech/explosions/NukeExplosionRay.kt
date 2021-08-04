package at.martinthedragon.nucleartech.explosions

import net.minecraft.block.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.world.World
import kotlin.math.*

class NukeExplosionRay(
    val world: World,
    val pos: BlockPos,
    val strength: Int,
    val length: Int
) {
    private val tips = mutableListOf<Vector3d>()
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
            val height = Vector3d(0.0, -strength.toDouble(), 0.0).zRot(rot.toFloat())
            val y = height.y
            val sectionRad = sqrt(strength.toFloat().pow(2F) - y.toFloat().pow(2F))
            val circumference = 2 * PI * sectionRad

            for (r in startCircumference..circumference.toInt()) {
                val vec = Vector3d(sectionRad.toDouble(), y, 0.0).normalize().yRot((360 / circumference * r).toFloat())
                var remaining = strength.toFloat()
                var lastPos: Vector3d? = null

                for (i in 0 until strength) {
                    if (i > length) break
                    val newPos = Vector3d(
                        pos.x + vec.x * i,
                        pos.y + vec.y * i,
                        pos.z + vec.z * i
                    )

                    val newBlockPos = BlockPos(newPos)

                    val fac = (100 - i.toDouble() / strength.toDouble() * 100) * 0.07

                    val block = world.getBlockState(newBlockPos)

                    remaining -= if (block.material.isLiquid) 2.5F.pow(7.5F - fac.toFloat())
                    else block.getExplosionResistance(world, newBlockPos, null).pow(7.5F - fac.toFloat())

                    if (remaining > 0 && !block.isAir(world, pos)) lastPos = newPos // this version of isAir will be removed in 1.17

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

            val vector = Vector3d(
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
                if (!world.getBlockState(currentPos).isAir(world, currentPos)) {
                    world.setBlock(currentPos, Blocks.AIR.defaultBlockState(), 0b10) // only sends the removed block. does not update neighbors
                    destroyedBlocks++
                }
                processedBlocks++
            }
        }
        processed += count
    }
}
