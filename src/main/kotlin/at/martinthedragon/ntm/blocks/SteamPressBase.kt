package at.martinthedragon.ntm.blocks

import at.martinthedragon.ntm.ModBlocks
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItemUseContext
import net.minecraft.item.ItemStack
import net.minecraft.pathfinding.PathType
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.IBooleanFunction
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.world.Explosion
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorld
import net.minecraft.world.World

class SteamPressBase(properties: Properties) : Block(properties) {
    override fun getShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos, context: ISelectionContext): VoxelShape = baseShape
    override fun getInteractionShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos): VoxelShape = baseShape
    override fun isPathfindable(state: BlockState, worldIn: IBlockReader, pos: BlockPos, type: PathType) = false

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState? {
        val blockPos1 = context.clickedPos.above()
        val blockPos2 = blockPos1.above()
        return if (context.level.getBlockState(blockPos1).canBeReplaced(context) && context.level.getBlockState(blockPos2).canBeReplaced(context))
            defaultBlockState()
        else null
    }

    override fun setPlacedBy(worldIn: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        if (!worldIn.isClientSide) {
            val blockPos1 = pos.above()
            val blockPos2 = blockPos1.above()
            worldIn.setBlockAndUpdate(blockPos1, ModBlocks.steamPressFrame.defaultBlockState())
            worldIn.setBlockAndUpdate(blockPos2, ModBlocks.steamPressTop.defaultBlockState())
            worldIn.updateNeighborsAt(pos, Blocks.AIR)
            state.updateNeighbourShapes(worldIn, pos, 0b11)
        }
    }

    override fun destroy(world: IWorld, pos: BlockPos, state: BlockState) {
        removeSteamPressStructure(world, pos)
    }

    private fun removeSteamPressStructure(worldIn: IWorld, pos: BlockPos) {
        if (!worldIn.isClientSide) {
            val blockPos1 = pos.above()
            val blockPos2 = blockPos1.above()

            if (worldIn.getBlockState(blockPos1).block == ModBlocks.steamPressFrame)
                worldIn.setBlock(blockPos1, Blocks.AIR.defaultBlockState(), 0b100011)
            if (worldIn.getBlockState(blockPos2).block == ModBlocks.steamPressTop)
                worldIn.setBlock(blockPos2, Blocks.AIR.defaultBlockState(), 0b100011)
        }
    }

    companion object {
        private val middleShape: VoxelShape = box(2.0, 0.0, 2.0, 14.0, 14.0, 14.0)
        private val leg1: VoxelShape = box(0.0, 0.0, 0.0, 2.0, 14.0, 2.0)
        private val leg2: VoxelShape = box(16.0, 0.0, 0.0, 14.0, 14.0, 2.0)
        private val leg3: VoxelShape = box(16.0, 0.0, 16.0, 14.0, 14.0, 14.0)
        private val leg4: VoxelShape = box(0.0, 0.0, 16.0, 2.0, 14.0, 14.0)
        private val pressBed: VoxelShape = box(0.0, 14.0, 0.0, 16.0, 16.0, 16.0)
        private val legs: VoxelShape = VoxelShapes.or(leg1, leg2, leg3, leg4)
        private val frame: VoxelShape = VoxelShapes.or(legs, pressBed)
        val baseShape: VoxelShape = VoxelShapes.or(middleShape, frame)
    }
}
