package at.martinthedragon.nucleartech.block

import at.martinthedragon.nucleartech.api.world.dropExperience
import at.martinthedragon.nucleartech.block.entity.SteamPressBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.Containers
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.PushReaction
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.Vec3
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class SteamPressBaseBlock(properties: Properties) : Block(properties) {
    override fun getShape(state: BlockState, blocks: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape = baseShape
    override fun getInteractionShape(state: BlockState, blocks: BlockGetter, pos: BlockPos): VoxelShape = baseShape
    override fun isPathfindable(state: BlockState, blocks: BlockGetter, pos: BlockPos, type: PathComputationType) = false

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        val blockPos1 = context.clickedPos.above()
        val blockPos2 = blockPos1.above()
        return if (context.level.getBlockState(blockPos1).canBeReplaced(context) && context.level.getBlockState(blockPos2).canBeReplaced(context))
            defaultBlockState()
        else null
    }

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult) = openMenu<SteamPressBlockEntity>(level, pos.above(2), player)

    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        if (!level.isClientSide) {
            val blockPos1 = pos.above()
            val blockPos2 = blockPos1.above()
            level.setBlockAndUpdate(blockPos1, NTechBlocks.steamPressFrame.get().defaultBlockState())
            level.setBlockAndUpdate(blockPos2, NTechBlocks.steamPressTop.get().defaultBlockState())
            level.updateNeighborsAt(pos, Blocks.AIR)
            state.updateNeighbourShapes(level, pos, 0b11)
        }
    }

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, p_196243_5_: Boolean) {
        if (!level.isClientSide && !state.`is`(newState.block)) removeSteamPressStructure(level, pos)
        @Suppress("DEPRECATION") super.onRemove(state, level, pos, newState, p_196243_5_)
    }

    private fun removeSteamPressStructure(level: Level, pos: BlockPos) {
        val blockPos1 = pos.above()
        val blockPos2 = blockPos1.above()

        val tileEntity = level.getBlockEntity(blockPos2)
        if (tileEntity is SteamPressBlockEntity) {
            Containers.dropContents(level, pos, tileEntity)
            level.dropExperience(Vec3.atCenterOf(pos), tileEntity.getExperienceToDrop(null))
        }

        if (level.getBlockState(blockPos1).block == NTechBlocks.steamPressFrame.get()) level.destroyBlock(blockPos1, false)
        if (level.getBlockState(blockPos2).block == NTechBlocks.steamPressTop.get()) level.destroyBlock(blockPos2, false)
    }

    override fun getPistonPushReaction(state: BlockState) = PushReaction.BLOCK

    companion object {
        private val middleShape: VoxelShape = box(2.0, 0.0, 2.0, 14.0, 14.0, 14.0)
        private val leg1: VoxelShape = box(0.0, 0.0, 0.0, 2.0, 14.0, 2.0)
        private val leg2: VoxelShape = box(14.0, 0.0, 0.0, 16.0, 14.0, 2.0)
        private val leg3: VoxelShape = box(14.0, 0.0, 14.0, 16.0, 14.0, 16.0)
        private val leg4: VoxelShape = box(0.0, 0.0, 14.0, 2.0, 14.0, 16.0)
        private val pressBed: VoxelShape = box(0.0, 14.0, 0.0, 16.0, 16.0, 16.0)
        private val legs: VoxelShape = Shapes.or(leg1, leg2, leg3, leg4)
        private val frame: VoxelShape = Shapes.or(legs, pressBed)
        val baseShape: VoxelShape = Shapes.or(middleShape, frame)
    }
}
