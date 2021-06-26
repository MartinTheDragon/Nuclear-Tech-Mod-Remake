package at.martinthedragon.ntm.blocks

import at.martinthedragon.ntm.ModBlocks
import at.martinthedragon.ntm.tileentities.SteamPressTopTileEntity
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.material.PushReaction
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.inventory.InventoryHelper
import net.minecraft.item.BlockItemUseContext
import net.minecraft.item.ItemStack
import net.minecraft.pathfinding.PathType
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.world.*
import net.minecraftforge.fml.network.NetworkHooks

class SteamPressBase(properties: Properties) : Block(properties) {
    override fun getShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos, context: ISelectionContext): VoxelShape = baseShape
    override fun getInteractionShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos): VoxelShape = baseShape
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
            val tileEntity = world.getBlockEntity(pos.above(2))
            if (tileEntity is SteamPressTopTileEntity) NetworkHooks.openGui(player as ServerPlayerEntity, tileEntity, pos.above(2))
        }

        return ActionResultType.sidedSuccess(world.isClientSide)
    }

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
            worldIn.setBlockAndUpdate(blockPos1, ModBlocks.steamPressFrame.get().defaultBlockState())
            worldIn.setBlockAndUpdate(blockPos2, ModBlocks.steamPressTop.get().defaultBlockState())
            worldIn.updateNeighborsAt(pos, Blocks.AIR)
            state.updateNeighbourShapes(worldIn, pos, 0b11)
        }
    }

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

    private fun removeSteamPressStructure(worldIn: World, pos: BlockPos) {
        if (!worldIn.isClientSide) {
            val blockPos1 = pos.above()
            val blockPos2 = blockPos1.above()

            val tileEntity = worldIn.getBlockEntity(blockPos2)
            if (tileEntity is SteamPressTopTileEntity) {
                InventoryHelper.dropContents(worldIn, pos, tileEntity)
                tileEntity.getRecipesToAwardAndPopExperience(worldIn, Vector3d.atCenterOf(pos))
            }

            if (worldIn.getBlockState(blockPos1).block == ModBlocks.steamPressFrame.get())
                worldIn.destroyBlock(blockPos1, false)
            if (worldIn.getBlockState(blockPos2).block == ModBlocks.steamPressTop.get())
                worldIn.destroyBlock(blockPos2, false)
        }
    }

    override fun getPistonPushReaction(state: BlockState) = PushReaction.BLOCK

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
