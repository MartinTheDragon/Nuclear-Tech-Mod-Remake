package at.martinthedragon.nucleartech.entity

import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.fallout.FalloutTransformation
import at.martinthedragon.nucleartech.fallout.FalloutTransformationManager
import at.martinthedragon.nucleartech.math.hackedHypot
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraftforge.common.IForgeShearable
import net.minecraftforge.common.IPlantable
import net.minecraftforge.network.NetworkHooks
import kotlin.math.ceil
import kotlin.math.floor

class FalloutRain(entityType: EntityType<FalloutRain>, world: Level) : Entity(entityType, world) {
    override fun tick() {
        if (!level.isClientSide && firstTick && chunksToProcess.isEmpty() && outerChunksToProcess.isEmpty()) gatherChunks()

        super.tick()

        if (!level.isClientSide) {
            if (chunksToProcess.isNotEmpty()) {
                val chunkPos = chunksToProcess.removeLast()
                for (x in chunkPos.minBlockX..chunkPos.maxBlockX) for (z in chunkPos.minBlockZ..chunkPos.maxBlockZ) {
                    transformBlocks(x, z, hackedHypot((x - blockX).toFloat(), (z - blockZ).toFloat()) / getScale())
                }
            } else if (outerChunksToProcess.isNotEmpty()) {
                val chunkPos = outerChunksToProcess.removeLast()
                for (x in chunkPos.minBlockX..chunkPos.maxBlockX) for (z in chunkPos.minBlockZ..chunkPos.maxBlockZ) {
                    val distance = hackedHypot((x - blockX).toFloat(), (z - blockZ).toFloat())
                    if (distance <= getScale()) transformBlocks(x, z, distance / getScale())
                }
            } else remove(RemovalReason.DISCARDED)
        }

        // TODO configurable fallout rain
    }

    private val chunksToProcess = mutableListOf<ChunkPos>()
    private val outerChunksToProcess = mutableListOf<ChunkPos>()

    private fun gatherChunks() {
        val radius = getScale()
        val sectionRadius = radius / 16F

        val minX = floor(x / 16F - sectionRadius).toInt()
        val maxX = ceil(x / 16F + sectionRadius).toInt()
        val minZ = floor(z / 16F - sectionRadius).toInt()
        val maxZ = ceil(z / 16F + sectionRadius).toInt()

        val centerX = x.toFloat() / 16F
        val centerZ = z.toFloat() / 16F

        val chunks = mutableListOf<ChunkPos>()

        for (x in minX..maxX) for (z in minZ..maxZ) {
            val xOffset = x - centerX
            val zOffset = z - centerZ
            // check each edge of the chunk square against the circle's radius
            val circleMinXMinZ = xOffset * xOffset + zOffset * zOffset
            val circleMinXMaxZ = xOffset * xOffset + (zOffset + 1) * (zOffset + 1)
            val circleMaxXMinZ = (xOffset + 1) * (xOffset + 1) + zOffset * zOffset
            val circleMaxXMaxZ = (xOffset + 1) * (xOffset + 1) + (zOffset + 1) * (zOffset + 1)
            val sectionRadius2 = sectionRadius * sectionRadius
            if (sectionRadius2 in circleMinXMinZ..circleMinXMaxZ || // left edge
                sectionRadius2 in circleMinXMinZ..circleMaxXMinZ || // bottom edge
                sectionRadius2 in circleMaxXMinZ..circleMaxXMaxZ || // right edge
                sectionRadius2 in circleMinXMaxZ..circleMaxXMaxZ || // top edge
                sectionRadius2 in circleMinXMaxZ..circleMinXMinZ || // reverse left edge
                sectionRadius2 in circleMaxXMinZ..circleMinXMinZ || // reverse bottom edge
                sectionRadius2 in circleMaxXMaxZ..circleMaxXMinZ || // reverse right edge
                sectionRadius2 in circleMaxXMaxZ..circleMinXMaxZ) { // reverse top edge
                outerChunksToProcess += ChunkPos(x, z)
            } else if (circleMinXMinZ < sectionRadius2 || circleMinXMaxZ < sectionRadius2 || circleMaxXMinZ < sectionRadius2 || circleMaxXMaxZ < sectionRadius2)
                chunks += ChunkPos(x, z)
        }

        if (NuclearConfig.fallout.emulateSpiral.get()) {
            val centerXInt = centerX.toInt()
            val centerZInt = centerZ.toInt()

            chunksToProcess += chunks
                .map { ChunkPos(it.x - centerXInt, it.z - centerZInt) }
                .sortedByDescending { it.x * it.x + it.z * it.z } // "spiralize" the chunks to make the transformations start from the middle
                .map { ChunkPos(it.x + centerXInt, it.z + centerZInt) }
        } else {
            chunksToProcess += chunks
        }
    }

    private val transformations = FalloutTransformationManager.getAllTransformations()
    private val collectiveMaxTransformationDepth = transformations.maxOfOrNull(FalloutTransformation::maxDepth) ?: 1

    private fun transformBlocks(x: Int, z: Int, distance: Float) {
        var depth = 0
        for (y in 255 downTo 0) {
            val pos = BlockPos(x, y, z)
            val block = level.getBlockState(pos)

            if (block.isAir) continue

            // TODO place fallout

            if (NuclearConfig.fallout.removePlants.get() && (block.block is IPlantable || block.block is IForgeShearable)) {
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState())
                continue
            }
            if (NuclearConfig.fallout.burnFlammables.get() && random.nextInt(5) == 0 && block.isFlammable(level, pos, Direction.UP)) level.setBlockAndUpdate(pos.above(), Blocks.FIRE.defaultBlockState())

            val pair = transformations.firstNotNullOfOrNull { it.process(block, random, distance, depth)?.let { state -> it to state } }
            if (pair != null) {
                val (transformation, transformed) = pair
                level.setBlockAndUpdate(pos, transformed)
                if (transformation.maxDepth < 0) continue
            }

            if (block.isSolidRender(level, pos)) depth++
            if (depth >= collectiveMaxTransformationDepth) return
        }
    }

    override fun defineSynchedData() {
        entityData.define(SCALE, 0)
    }

    override fun readAdditionalSaveData(nbt: CompoundTag) {
        setScale(nbt.getInt("Scale"))
        chunksToProcess.addAll(nbt.getLongArray("Chunks").map(::ChunkPos))
        outerChunksToProcess.addAll(nbt.getLongArray("OuterChunks").map(::ChunkPos))
    }

    override fun addAdditionalSaveData(nbt: CompoundTag) {
        nbt.putInt("Scale", getScale())
        nbt.putLongArray("Chunks", chunksToProcess.map(ChunkPos::toLong))
        nbt.putLongArray("OuterChunks", outerChunksToProcess.map(ChunkPos::toLong))
    }

    override fun getAddEntityPacket(): Packet<*> = NetworkHooks.getEntitySpawningPacket(this)

    fun getScale(): Int = entityData.get(SCALE).coerceAtLeast(1)

    fun setScale(scale: Int) = entityData.set(SCALE, scale)

    companion object {
        private val SCALE: EntityDataAccessor<Int> = SynchedEntityData.defineId(FalloutRain::class.java, EntityDataSerializers.INT)
    }
}
