package at.martinthedragon.ntm.blocks

import at.martinthedragon.ntm.ModBlocks
import at.martinthedragon.ntm.tileentities.SteamPressTopTileEntity
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.material.PushReaction
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.pathfinding.PathType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorld
import net.minecraft.world.World

class SteamPressTop(properties: Properties) : Block(properties) {
    override fun getShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos, context: ISelectionContext): VoxelShape = topShape
    override fun getInteractionShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos): VoxelShape = topShape
    override fun isPathfindable(state: BlockState, worldIn: IBlockReader, pos: BlockPos, type: PathType) = false

    override fun onRemove(
        state: BlockState,
        world: World,
        pos: BlockPos,
        newState: BlockState,
        p_196243_5_: Boolean
    ) {
        if (!world.isClientSide && !state.`is`(newState.block))
            removeSteamPressStructure(world, pos)

        @Suppress("DEPRECATION")
        super.onRemove(state, world, pos, newState, p_196243_5_)
    }

    override fun playerWillDestroy(
        world: World,
        pos: BlockPos,
        state: BlockState,
        player: PlayerEntity
    ) {
        if (!world.isClientSide && player.abilities.instabuild)
            removeSteamPressStructure(world, pos, false)

        super.playerWillDestroy(world, pos, state, player)
    }

    private fun removeSteamPressStructure(worldIn: IWorld, pos: BlockPos, drop: Boolean = true) {
        if (!worldIn.isClientSide) {
            val blockPos1 = pos.below()
            val blockPos2 = blockPos1.below()

            if (worldIn.getBlockState(blockPos2).block == ModBlocks.steamPressBase)
                worldIn.destroyBlock(blockPos2, drop)
            if (worldIn.getBlockState(blockPos1).block == ModBlocks.steamPressFrame)
                worldIn.destroyBlock(blockPos1, false)
        }
    }

    override fun getPistonPushReaction(state: BlockState) = PushReaction.BLOCK

    override fun hasTileEntity(state: BlockState?): Boolean = true

    override fun createTileEntity(state: BlockState?, world: IBlockReader?) = SteamPressTopTileEntity()

    companion object {
        private val halfBox: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)
        private val topThing: VoxelShape = box(4.0, 8.0, 4.0, 12.0, 16.0, 12.0)
        val topShape: VoxelShape = VoxelShapes.or(halfBox, topThing)
    }
}
