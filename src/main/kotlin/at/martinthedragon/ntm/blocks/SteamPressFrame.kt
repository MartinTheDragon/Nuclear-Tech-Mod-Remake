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

class SteamPressFrame(properties: Properties) : Block(properties) {
    override fun getShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos, context: ISelectionContext): VoxelShape = frameShape
    override fun getRaytraceShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos): VoxelShape = frameShape
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
            val blockPos2 = pos.up()

            if (worldIn.getBlockState(blockPos1).block == ModBlocks.steamPressBase)
                worldIn.setBlockState(blockPos1, Blocks.AIR.defaultState, 0b100011)
            if (worldIn.getBlockState(blockPos2).block == ModBlocks.steamPressTop)
                worldIn.setBlockState(blockPos2, Blocks.AIR.defaultState, 0b100011)
        }
    }

    companion object {
        private val bar1: VoxelShape = makeCuboidShape(0.0, 0.0, 0.0, 2.0, 16.0, 2.0)
        private val bar2: VoxelShape = makeCuboidShape(14.0, 0.0, 0.0, 16.0, 16.0, 2.0)
        private val bar3: VoxelShape = makeCuboidShape(14.0, 0.0, 14.0, 16.0, 16.0, 16.0)
        private val bar4: VoxelShape = makeCuboidShape(0.0, 0.0, 14.0, 2.0, 16.0, 16.0)
        val frameShape: VoxelShape = VoxelShapes.or(bar1, bar2, bar3, bar4)
    }
}
