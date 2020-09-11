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
import net.minecraft.world.World

class SteamPressTop(properties: Properties) : Block(properties) {
    override fun getShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos, context: ISelectionContext): VoxelShape = topShape
    override fun getRaytraceShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos): VoxelShape = topShape
    override fun allowsMovement(state: BlockState, worldIn: IBlockReader, pos: BlockPos, type: PathType) = false

    override fun onBlockHarvested(worldIn: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        removeSteamPressStructure(worldIn, pos)
        super.onBlockHarvested(worldIn, pos, state, player)
    }

    override fun onExplosionDestroy(worldIn: World, pos: BlockPos, explosionIn: Explosion) =
            removeSteamPressStructure(worldIn, pos)

    private fun removeSteamPressStructure(worldIn: World, pos: BlockPos) {
        if (!worldIn.isRemote) {
            val blockPos1 = pos.down()
            val blockPos2 = blockPos1.down()

            if (worldIn.getBlockState(blockPos1).block == ModBlocks.steamPressFrame)
                worldIn.setBlockState(blockPos1, Blocks.AIR.defaultState, 0b100011)
            if (worldIn.getBlockState(blockPos2).block == ModBlocks.steamPressBase)
                worldIn.setBlockState(blockPos2, Blocks.AIR.defaultState, 0b100011)
        }
    }

    companion object {
        private val halfBox: VoxelShape = makeCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)
        private val topThing: VoxelShape = makeCuboidShape(4.0, 8.0, 4.0, 12.0, 16.0, 12.0)
        val topShape: VoxelShape = VoxelShapes.or(halfBox, topThing)
    }
}
