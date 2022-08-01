package at.martinthedragon.nucleartech.entity

import at.martinthedragon.nucleartech.config.FalloutConfig
import at.martinthedragon.nucleartech.config.NuclearConfig
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
import net.minecraft.world.phys.Vec3
import net.minecraftforge.common.IForgeShearable
import net.minecraftforge.common.IPlantable
import net.minecraftforge.network.NetworkHooks
import kotlin.math.hypot

class FalloutRain(entityType: EntityType<FalloutRain>, world: Level) : Entity(entityType, world) {
    override fun tick() {
        if (!level.isClientSide && firstTick && chunksToProcess.isEmpty() && outerChunksToProcess.isEmpty()) gatherChunks()

        super.tick()

        if (!level.isClientSide) {
            if (chunksToProcess.isNotEmpty()) {
                val chunkPos = chunksToProcess.removeLast()
                for (x in chunkPos.minBlockX..chunkPos.maxBlockX) for (z in chunkPos.minBlockZ..chunkPos.maxBlockZ) {
                    transformBlocks(x, z, hypot((x - blockX).toFloat(), (z - blockZ).toFloat()) / getScale())
                }
            } else if (outerChunksToProcess.isNotEmpty()) {
                val chunkPos = outerChunksToProcess.removeLast()
                for (x in chunkPos.minBlockX..chunkPos.maxBlockX) for (z in chunkPos.minBlockZ..chunkPos.maxBlockZ) {
                    val distance = hypot((x - blockX).toFloat(), (z - blockZ).toFloat())
                    if (distance <= getScale()) transformBlocks(x, z, distance / getScale())
                }
            } else remove(RemovalReason.DISCARDED)
        }

        // TODO configurable fallout rain
    }

    private val chunksToProcess = mutableListOf<ChunkPos>()
    private val outerChunksToProcess = mutableListOf<ChunkPos>()

    private fun gatherChunks() {
        val chunks = mutableSetOf<ChunkPos>()
        val outerChunks = mutableSetOf<ChunkPos>()
        val outerRange = getScale()
        val adjustedMaxAngle = 24 * outerRange / 32
        // multiply by 2 for more precision
        for (angle in 0..adjustedMaxAngle * 2) outerChunks += ChunkPos(BlockPos(Vec3(outerRange.toDouble(), .0, .0).yRot(angle * .5F / (adjustedMaxAngle / 360F))).offset(blockPosition()))
        for (distance in 0..outerRange step 8) for (angle in 0..adjustedMaxAngle * 2) {
            val chunkPos = ChunkPos(BlockPos(Vec3(distance.toDouble(), .0, .0).yRot(angle * .5F / (adjustedMaxAngle / 360F))).offset(blockPosition()))
            if (chunkPos !in outerChunks) chunks += chunkPos
        }

        chunksToProcess.addAll(chunks.reversed())
        outerChunksToProcess.addAll(outerChunks)
    }

    private val transformations = FalloutConfig.FalloutTransformation.loadFromConfig()
    private val collectiveMaxTransformationDepth = transformations.maxOf(FalloutConfig.FalloutTransformation::transformationDepth)

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

            if (transformations.firstOrNull { it.shouldTransform(block, distance, depth) }?.also { it.transform(block).let { newBlock -> level.setBlockAndUpdate(pos, newBlock) }}?.let { it.transformationDepth < 0 } == true) continue

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
