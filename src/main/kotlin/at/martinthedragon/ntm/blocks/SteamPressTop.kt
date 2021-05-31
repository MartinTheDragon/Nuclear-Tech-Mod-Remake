package at.martinthedragon.ntm.blocks

import at.martinthedragon.ntm.ModBlocks
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.pathfinding.PathType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.world.Explosion
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorld
import net.minecraft.world.World

class SteamPressTop(properties: Properties) : Block(properties) {
    override fun getShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos, context: ISelectionContext): VoxelShape = topShape
    override fun getInteractionShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos): VoxelShape = topShape
    override fun isPathfindable(state: BlockState, worldIn: IBlockReader, pos: BlockPos, type: PathType) = false

    override fun destroy(world: IWorld, pos: BlockPos, state: BlockState) {
        removeSteamPressStructure(world, pos)
    }

    private fun removeSteamPressStructure(worldIn: IWorld, pos: BlockPos) {
        if (!worldIn.isClientSide) {
            val blockPos1 = pos.below()
            val blockPos2 = blockPos1.below()

            if (worldIn.getBlockState(blockPos1).block == ModBlocks.steamPressFrame)
                worldIn.setBlock(blockPos1, Blocks.AIR.defaultBlockState(), 0b100011)
            if (worldIn.getBlockState(blockPos2).block == ModBlocks.steamPressBase)
                worldIn.setBlock(blockPos2, Blocks.AIR.defaultBlockState(), 0b100011)
        }
    }

    companion object {
        private val halfBox: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)
        private val topThing: VoxelShape = box(4.0, 8.0, 4.0, 12.0, 16.0, 12.0)
        val topShape: VoxelShape = VoxelShapes.or(halfBox, topThing)
    }
}
