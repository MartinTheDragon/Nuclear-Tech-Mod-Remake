package at.martinthedragon.nucleartech.entities

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.NuclearTags
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.tags.BlockTags
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.material.Material
import net.minecraft.world.phys.Vec3
import net.minecraftforge.common.IPlantable
import net.minecraftforge.common.Tags
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
                    transformBlocks(x, z, hypot((x - blockX).toFloat(), (z - blockZ).toFloat()) * 100.0 / getScale())
                }
            } else if (outerChunksToProcess.isNotEmpty()) {
                val chunkPos = outerChunksToProcess.removeLast()
                for (x in chunkPos.minBlockX..chunkPos.maxBlockX) for (z in chunkPos.minBlockZ..chunkPos.maxBlockZ) {
                    val distance = hypot((x - blockX).toFloat(), (z - blockZ).toFloat())
                    if (distance <= getScale()) transformBlocks(x, z, distance * 100.0 / getScale())
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
        val adjustedMaxAngle = 20 * outerRange / 32
        for (angle in 0..adjustedMaxAngle) outerChunks += ChunkPos(BlockPos(Vec3(outerRange.toDouble(), .0, .0).yRot(angle / (adjustedMaxAngle / 360F))).offset(blockPosition()))
        for (distance in 0..outerRange step 8) for (angle in 0..adjustedMaxAngle) {
            val chunkPos = ChunkPos(BlockPos(Vec3(distance.toDouble(), .0, .0).yRot(angle / (adjustedMaxAngle / 360F))).offset(blockPosition()))
            if (chunkPos !in outerChunks) chunks += chunkPos
        }

        chunksToProcess.addAll(chunks.reversed())
        outerChunksToProcess.addAll(outerChunks)
    }

    // TODO configurable transformations
    private fun transformBlocks(x: Int, z: Int, distance: Double) {
        var depth = 0
        for (y in 255 downTo 0) {
            val pos = BlockPos(x, y, z)
            val block = level.getBlockState(pos)

            if (block.isAir) continue

            // TODO place fallout

            if (block.isFlammable(level, pos, Direction.UP) && random.nextInt(5) == 0)
                level.setBlockAndUpdate(pos.above(), Blocks.FIRE.defaultBlockState())

            when {
                block.`is`(BlockTags.LEAVES) -> level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState())
                block.`is`(Tags.Blocks.STONE) -> {
                    depth++
                    when {
                        distance < 5 -> level.setBlockAndUpdate(pos, ModBlocks.hotSellafite.get().defaultBlockState())
                        distance < 15 -> level.setBlockAndUpdate(pos, ModBlocks.sellafite.get().defaultBlockState())
                        distance < 75 -> level.setBlockAndUpdate(pos, ModBlocks.slakedSellafite.get().defaultBlockState())
                        else -> return
                    }
                    if (depth > 2) return
                }
                block.`is`(Blocks.GRASS_BLOCK) || block.`is`(Blocks.PODZOL) -> level.setBlockAndUpdate(pos, ModBlocks.deadGrass.get().defaultBlockState()).also { return }
                block.`is`(Blocks.MYCELIUM) -> level.setBlockAndUpdate(pos, ModBlocks.glowingMycelium.get().defaultBlockState()).also { return }
                // FIXME drops tall flowers, e.g. peonies
                block.block is IPlantable || block.block is BonemealableBlock -> level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState())
                block.`is`(Tags.Blocks.SAND) -> {
                    if (random.nextInt(20) == 0)
                        level.setBlockAndUpdate(pos, if (block.`is`(Tags.Blocks.SAND_RED)) ModBlocks.redTrinitite.get().defaultBlockState() else ModBlocks.trinitite.get().defaultBlockState())
                    return
                }
                block.`is`(Blocks.CLAY) -> level.setBlockAndUpdate(pos, Blocks.TERRACOTTA.defaultBlockState()).also { return }
                block.`is`(Tags.Blocks.ORES_COAL) -> {
                    val randomValue = random.nextInt(150)
                    if (randomValue < 20) level.setBlockAndUpdate(pos, Blocks.DIAMOND_ORE.defaultBlockState())
                    else if (randomValue < 30) level.setBlockAndUpdate(pos, Blocks.EMERALD_ORE.defaultBlockState())
                    return
                }
                block.`is`(Blocks.SNOW_BLOCK) || block.`is`(Blocks.SNOW) -> level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState())
                block.`is`(BlockTags.LOGS_THAT_BURN) -> level.setBlockAndUpdate(pos, ModBlocks.charredLog.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, block.getOptionalValue(RotatedPillarBlock.AXIS).orElse(Direction.Axis.Y)))
                block.`is`(Blocks.RED_MUSHROOM_BLOCK) || block.`is`(Blocks.BROWN_MUSHROOM_BLOCK) -> level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState())
                block.`is`(Blocks.MUSHROOM_STEM) -> level.setBlockAndUpdate(pos, ModBlocks.charredLog.get().defaultBlockState())
                block.material == Material.WOOD && block.isSolidRender(level, pos) && !block.`is`(ModBlocks.charredLog.get()) -> level.setBlockAndUpdate(pos, ModBlocks.charredPlanks.get().defaultBlockState())
                block.`is`(NuclearTags.Blocks.ORES_URANIUM) && !block.`is`(ModBlocks.scorchedUraniumOre.get()) -> {
                    if (random.nextInt(100) == 0) level.setBlockAndUpdate(pos, ModBlocks.schrabidiumOre.get().defaultBlockState())
                    else level.setBlockAndUpdate(pos, ModBlocks.scorchedUraniumOre.get().defaultBlockState())
                    return
                }
                block.`is`(ModBlocks.netherUraniumOre.get()) -> {
                    if (random.nextInt(100) == 0) level.setBlockAndUpdate(pos, ModBlocks.netherSchrabidiumOre.get().defaultBlockState())
                    else level.setBlockAndUpdate(pos, ModBlocks.scorchedNetherUraniumOre.get().defaultBlockState())
                    return
                }
                block.isSolidRender(level, pos) -> return
            }
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
