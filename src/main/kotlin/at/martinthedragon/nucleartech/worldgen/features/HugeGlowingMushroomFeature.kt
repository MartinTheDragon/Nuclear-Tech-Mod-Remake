package at.martinthedragon.nucleartech.worldgen.features

import at.martinthedragon.nucleartech.NuclearTags
import com.mojang.serialization.Codec
import net.minecraft.core.BlockPos
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.HugeMushroomBlock
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration
import java.util.*

class HugeGlowingMushroomFeature(codec: Codec<HugeMushroomFeatureConfiguration>) : AbstractHugeMushroomFeature(codec) {
    override fun getTreeRadiusForHeight(maxHeight: Int, p_225563_2_: Int, radius: Int, currentHeight: Int): Int =
        when {
            currentHeight > maxHeight -> 0
            currentHeight == 0 -> 1
            currentHeight == maxHeight - 7 -> 1
            currentHeight == maxHeight - 5 -> (radius - 2).coerceAtLeast(0)
            currentHeight == maxHeight - 1 -> (radius - 1).coerceAtLeast(0)
            currentHeight == maxHeight -> 1
            currentHeight >= maxHeight - 4 -> radius
            else -> 0
        }

    override fun makeCap(
        world: LevelAccessor,
        random: Random,
        pos: BlockPos,
        height: Int,
        mutablePos: BlockPos.MutableBlockPos,
        config: HugeMushroomFeatureConfiguration
    ) {
        val maxRadius = config.foliageRadius
        for (currentHeight in 0..height) {
            val radius = getTreeRadiusForHeight(height, -1, maxRadius, currentHeight)
            for (radiusOffsetX in -radius..radius) {
                for (radiusOffsetZ in -radius..radius) {
                    if (radiusOffsetX == 0 && radiusOffsetZ == 0) continue

                    val negativeXSide = radiusOffsetX == -radius
                    val positiveXSide = radiusOffsetX == radius
                    val negativeZSide = radiusOffsetZ == -radius
                    val positiveZSide = radiusOffsetZ == radius
                    val down = radius > getTreeRadiusForHeight(height, -1, maxRadius, currentHeight -1)
                    val up = radius > getTreeRadiusForHeight(height, -1, maxRadius, currentHeight + 1)

                    mutablePos.setWithOffset(pos, radiusOffsetX, currentHeight, radiusOffsetZ)
                    if (world.getBlockState(mutablePos).isSolidRender(world, mutablePos)) {
                        setBlock(world, mutablePos, config.capProvider.getState(random, pos).setValue(HugeMushroomBlock.UP, up).setValue(HugeMushroomBlock.DOWN, down).setValue(HugeMushroomBlock.WEST, negativeXSide).setValue(HugeMushroomBlock.EAST, positiveXSide).setValue(HugeMushroomBlock.NORTH, negativeZSide).setValue(HugeMushroomBlock.SOUTH, positiveZSide))
                    }
                }
            }
        }
        setBlock(world, mutablePos.setWithOffset(pos, 0, height, 0), config.capProvider.getState(random, pos).setValue(HugeMushroomBlock.DOWN, false))
    }

    override fun isValidPosition(
        world: LevelAccessor,
        pos: BlockPos,
        height: Int,
        mutablePos: BlockPos.MutableBlockPos,
        config: HugeMushroomFeatureConfiguration
    ): Boolean {
        val i = pos.y
        return if (i >= 1 && i + height + 1 < 256) {
            val block = world.getBlockState(pos.below())
            if (!block.`is`(NuclearTags.Blocks.GLOWING_MUSHROOM_GROW_BLOCK)) {
                false
            } else {
                for (j in 0..height) {
                    val k = getTreeRadiusForHeight(height, -1, config.foliageRadius, j)
                    for (l in -k..k) {
                        for (i1 in -k..k) {
                            val blockstate = world.getBlockState(mutablePos.setWithOffset(pos, l, j, i1))
                            if (!blockstate.isAir && !blockstate.`is`(BlockTags.LEAVES)) return false
                        }
                    }
                }
                true
            }
        } else {
            false
        }
    }

    override fun getTreeHeight(random: Random): Int {
        var height = random.nextInt(3) + 9
        if (random.nextInt(16) == 0) height += 5
        return height
    }
}
