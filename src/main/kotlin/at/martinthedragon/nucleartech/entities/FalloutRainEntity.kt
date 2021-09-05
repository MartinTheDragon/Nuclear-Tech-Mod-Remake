package at.martinthedragon.nucleartech.entities

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.NuclearTags
import net.minecraft.block.Blocks
import net.minecraft.block.IGrowable
import net.minecraft.block.RotatedPillarBlock
import net.minecraft.block.material.Material
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.IPacket
import net.minecraft.network.datasync.DataParameter
import net.minecraft.network.datasync.DataSerializers
import net.minecraft.network.datasync.EntityDataManager
import net.minecraft.tags.BlockTags
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.world.World
import net.minecraftforge.common.IPlantable
import net.minecraftforge.common.Tags
import net.minecraftforge.fml.network.NetworkHooks
import kotlin.math.PI

class FalloutRainEntity(entityType: EntityType<FalloutRainEntity>, world: World) : Entity(entityType, world) {
    var revProgress = 0
        private set
    var radProgress = 0
        private set

    override fun tick() {
        super.tick()

        if (!level.isClientSide) {
            for (i in 0 until 256) { // TODO make configurable
                val circumference = if (radProgress == 0) 1.0 else radProgress * PI * 4
                val part = 360.0 / circumference
                val vector = Vector3d(radProgress * .5, 0.0, 0.0)
                    .yRot((part * revProgress).toFloat())
                val distance = radProgress * 100 / getScale() * .5

                transformBlocks((x + vector.x).toInt(), (z + vector.z).toInt(), distance)

                revProgress++

                if (revProgress > circumference) {
                    revProgress = 0
                    radProgress++
                }

                if (radProgress > getScale() * 2) remove()
            }
        }

        // TODO configurable fallout rain
    }

    // TODO configurable transformations
    private fun transformBlocks(x: Int, z: Int, distance: Double) {
        var depth = 0
        for (y in 255 downTo 0) {
            val pos = BlockPos(x, y, z)
            val block = level.getBlockState(pos)

            if (block.isAir(level, pos)) continue

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
                block.block is IPlantable || block.block is IGrowable -> level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState())
                block.`is`(Tags.Blocks.SAND) -> {
                    if (random.nextInt(60) == 0)
                        level.setBlockAndUpdate(pos, if (block.`is`(Tags.Blocks.SAND_RED)) ModBlocks.redTrinitite.get().defaultBlockState() else ModBlocks.trinitite.get().defaultBlockState())
                    return
                }
                block.`is`(Blocks.CLAY) -> level.setBlockAndUpdate(pos, Blocks.TERRACOTTA.defaultBlockState()).also { return }
                block.`is`(Tags.Blocks.ORES_COAL) -> {
                    val randomValue = random.nextInt(150)
                    if (randomValue < 7) level.setBlockAndUpdate(pos, Blocks.DIAMOND_ORE.defaultBlockState())
                    else if (randomValue < 10) level.setBlockAndUpdate(pos, Blocks.EMERALD_ORE.defaultBlockState())
                    return
                }
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

    override fun readAdditionalSaveData(nbt: CompoundNBT) {
        setScale(nbt.getInt("Scale"))
        revProgress = nbt.getInt("RevProgress")
        radProgress = nbt.getInt("RadProgress")
    }

    override fun addAdditionalSaveData(nbt: CompoundNBT) {
        nbt.putInt("Scale", getScale())
        nbt.putInt("RevProgress", revProgress)
        nbt.putInt("RadProgress", radProgress)
    }

    override fun getAddEntityPacket(): IPacket<*> = NetworkHooks.getEntitySpawningPacket(this)

    fun getScale(): Int = entityData.get(SCALE).coerceAtLeast(1)

    fun setScale(scale: Int) = entityData.set(SCALE, scale)

    companion object {
        private val SCALE: DataParameter<Int> = EntityDataManager.defineId(FalloutRainEntity::class.java, DataSerializers.INT)
    }
}
