package at.martinthedragon.nucleartech.blocks.multi

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB

class RotatedMultiBlockPlacer(val rotation: Rotation) : MultiBlockPlacer {
    constructor(direction: Direction) : this(getRotationFor(direction))

    private val structureMap = mutableMapOf<BlockPos, BlockState>()

    override fun place(relativePos: BlockPos, blockState: BlockState) {
        structureMap += relativePos.rotate(rotation) to blockState
    }

    override fun fill(from: BlockPos, to: BlockPos, blockState: BlockState) {
        BlockPos.betweenClosed(from, to).forEach {
            structureMap.putIfAbsent(it.rotate(rotation), blockState)
        }
    }

    override fun getPlacementData() = structureMap - BlockPos.ZERO

    override fun canPlaceAt(level: LevelAccessor, pos: BlockPos): Boolean = structureMap.keys.all { relativePos ->
        val actualPos = relativePos.offset(pos)
        !level.isOutsideBuildHeight(actualPos) && level.getEntities(null, AABB(actualPos)).isEmpty() && level.getBlockState(actualPos).material.isReplaceable
    }

    override fun finish(level: LevelAccessor, pos: BlockPos): Boolean {
        structureMap.remove(BlockPos.ZERO) // never place at origin
        for ((relativePos, state) in structureMap) if (!level.setBlock(relativePos.offset(pos), state, 11)) return false
        for (relativePos in structureMap.keys) {
            val blockEntity = level.getBlockEntity(relativePos.offset(pos))
            if (blockEntity is MultiBlockPart.MultiBlockPartBlockEntity) blockEntity.core = pos
        }
        structureMap.clear()
        return true
    }

    companion object {
        fun getRotationFor(direction: Direction) = when (direction) {
            Direction.SOUTH -> Rotation.NONE
            Direction.WEST -> Rotation.CLOCKWISE_90
            Direction.NORTH -> Rotation.CLOCKWISE_180
            Direction.EAST -> Rotation.COUNTERCLOCKWISE_90
            Direction.UP, Direction.DOWN -> Rotation.NONE
        }

        fun invert(rotation: Rotation) = when (rotation) {
            Rotation.NONE -> Rotation.NONE
            Rotation.CLOCKWISE_90 -> Rotation.COUNTERCLOCKWISE_90
            Rotation.CLOCKWISE_180 -> Rotation.CLOCKWISE_180
            Rotation.COUNTERCLOCKWISE_90 -> Rotation.CLOCKWISE_90
        }
    }
}
