package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.tileentities.SteamPressTopTileEntity
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.material.PushReaction
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.pathfinding.PathType
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorld
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks

class SteamPressTop(properties: Properties) : Block(properties) {
    override fun getShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos, context: ISelectionContext): VoxelShape = topShape
    override fun getInteractionShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos): VoxelShape = topShape
    override fun isPathfindable(state: BlockState, worldIn: IBlockReader, pos: BlockPos, type: PathType) = false

    override fun use(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockRayTraceResult
    ): ActionResultType {
        if (!world.isClientSide) {
            val tileEntity = world.getBlockEntity(pos)
            if (tileEntity is SteamPressTopTileEntity) NetworkHooks.openGui(player as ServerPlayerEntity, tileEntity, pos)
        }

        return ActionResultType.sidedSuccess(world.isClientSide)
    }

    override fun onRemove(
        state: BlockState,
        world: World,
        pos: BlockPos,
        newState: BlockState,
        p_196243_5_: Boolean
    ) { // code for dropping random and xp is in SteamPressBase
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

            if (worldIn.getBlockState(blockPos2).block == ModBlocks.steamPressBase.get())
                worldIn.destroyBlock(blockPos2, drop)
            if (worldIn.getBlockState(blockPos1).block == ModBlocks.steamPressFrame.get())
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
