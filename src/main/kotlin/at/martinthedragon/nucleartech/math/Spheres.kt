package at.martinthedragon.nucleartech.math

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.CommonLevelAccessor
import net.minecraft.world.level.block.BaseFireBlock
import net.minecraft.world.level.block.Block

inline fun placeSpherical(blockPos: BlockPos, radius: Int, placer: (pos: BlockPos) -> Unit) {
    val maxDistanceSquared = radius * radius
    for (x in -radius until radius) for (y in -radius until radius) for (z in -radius until radius) {
        val distanceSquared = x * x + y * y + z * z
        if (distanceSquared < maxDistanceSquared)
            placer(blockPos.offset(x, y, z))
    }
}

fun burnSphericalFlammable(level: CommonLevelAccessor, blockPos: BlockPos, radius: Int) {
    placeSpherical(blockPos, radius) {
        val fire = BaseFireBlock.getState(level, it)
        if (!level.getBlockState(it).isSolidRender(level, it) && Direction.values().any { direction -> level.getBlockState(it.relative(direction)).isFlammable(level, it, direction.opposite) })
            level.setBlock(it, fire, Block.UPDATE_ALL)
    }
}

fun burnSpherical(level: CommonLevelAccessor, blockPos: BlockPos, radius: Int) {
    placeSpherical(blockPos, radius) {
        val fire = BaseFireBlock.getState(level, it)
        if (!level.getBlockState(it).isSolidRender(level, it) && fire.canSurvive(level, it))
            level.setBlock(it, fire, Block.UPDATE_ALL)
    }
}
