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
import net.minecraft.world.World

class SteamPressBase(properties: Properties) : Block(properties) {
    override fun getShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos, context: ISelectionContext): VoxelShape = baseShape
    override fun getRaytraceShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos): VoxelShape = baseShape
    override fun allowsMovement(state: BlockState, worldIn: IBlockReader, pos: BlockPos, type: PathType) = false

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState? {
        val blockPos1 = context.pos.up()
        val blockPos2 = blockPos1.up()
        return if (context.world.getBlockState(blockPos1).isReplaceable(context) && context.world.getBlockState(blockPos2).isReplaceable(context))
            defaultState
        else null
    }

    override fun onBlockPlacedBy(worldIn: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack)
        if (!worldIn.isRemote) {
            val blockPos1 = pos.up()
            val blockPos2 = blockPos1.up()
            worldIn.setBlockState(blockPos1, ModBlocks.steamPressFrame.defaultState, 0b11)
            worldIn.setBlockState(blockPos2, ModBlocks.steamPressTop.defaultState, 0b11)
            worldIn.func_230547_a_(pos, Blocks.AIR)
            state.updateNeighbours(worldIn, pos, 0b11)
        }
    }

    override fun onBlockHarvested(worldIn: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        removeSteamPressStructure(worldIn, pos)
        super.onBlockHarvested(worldIn, pos, state, player)
    }

    override fun onExplosionDestroy(worldIn: World, pos: BlockPos, explosionIn: Explosion) =
            removeSteamPressStructure(worldIn, pos)

    private fun removeSteamPressStructure(worldIn: World, pos: BlockPos) {
        if (!worldIn.isRemote) {
            val blockPos1 = pos.up()
            val blockPos2 = blockPos1.up()

            if (worldIn.getBlockState(blockPos1).block == ModBlocks.steamPressFrame)
                worldIn.setBlockState(blockPos1, Blocks.AIR.defaultState, 0b100011)
            if (worldIn.getBlockState(blockPos2).block == ModBlocks.steamPressTop)
                worldIn.setBlockState(blockPos2, Blocks.AIR.defaultState, 0b100011)
        }
    }

    companion object {
        private val middleShape: VoxelShape = makeCuboidShape(2.0, 0.0, 2.0, 14.0, 14.0, 14.0)
        private val leg1: VoxelShape = makeCuboidShape(0.0, 0.0, 0.0, 2.0, 14.0, 2.0)
        private val leg2: VoxelShape = makeCuboidShape(16.0, 0.0, 0.0, 14.0, 14.0, 2.0)
        private val leg3: VoxelShape = makeCuboidShape(16.0, 0.0, 16.0, 14.0, 14.0, 14.0)
        private val leg4: VoxelShape = makeCuboidShape(0.0, 0.0, 16.0, 2.0, 14.0, 14.0)
        private val pressBed: VoxelShape = makeCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0)
        private val legs: VoxelShape = VoxelShapes.or(leg1, leg2, leg3, leg4)
        private val frame: VoxelShape = VoxelShapes.or(legs, pressBed)
        val baseShape: VoxelShape = VoxelShapes.or(middleShape, frame)
    }
}
