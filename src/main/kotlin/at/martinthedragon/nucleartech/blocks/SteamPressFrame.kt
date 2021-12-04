package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.blocks.entities.SteamPressBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.PushReaction
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class SteamPressFrame(properties: Properties) : Block(properties) {
    override fun getShape(state: BlockState, blocks: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape = frameShape
    override fun getInteractionShape(state: BlockState, blocks: BlockGetter, pos: BlockPos): VoxelShape = frameShape
    override fun isPathfindable(state: BlockState, blocks: BlockGetter, pos: BlockPos, type: PathComputationType) = false

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult) = openMenu<SteamPressBlockEntity>(level, pos.above(), player)

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, p_196243_5_: Boolean) {
        if (!level.isClientSide && !state.`is`(newState.block)) removeSteamPressStructure(level, pos)
        @Suppress("DEPRECATION") super.onRemove(state, level, pos, newState, p_196243_5_)
    }

    override fun playerWillDestroy(level: Level, pos: BlockPos, state: BlockState, player: Player) {
        if (!level.isClientSide && player.abilities.instabuild) removeSteamPressStructure(level, pos, false)
        super.playerWillDestroy(level, pos, state, player)
    }

    private fun removeSteamPressStructure(level: Level, pos: BlockPos, drop: Boolean = true) {
        val blockPos1 = pos.below()
        val blockPos2 = pos.above()

        if (level.getBlockState(blockPos1).block == ModBlocks.steamPressBase.get()) level.destroyBlock(blockPos1, drop)
        if (level.getBlockState(blockPos2).block == ModBlocks.steamPressTop.get()) level.destroyBlock(blockPos2, false)
    }

    override fun getPistonPushReaction(state: BlockState) = PushReaction.BLOCK

    companion object {
        private val bar1: VoxelShape = box(0.0, 0.0, 0.0, 2.0, 16.0, 2.0)
        private val bar2: VoxelShape = box(14.0, 0.0, 0.0, 16.0, 16.0, 2.0)
        private val bar3: VoxelShape = box(14.0, 0.0, 14.0, 16.0, 16.0, 16.0)
        private val bar4: VoxelShape = box(0.0, 0.0, 14.0, 2.0, 16.0, 16.0)
        val frameShape: VoxelShape = Shapes.or(bar1, bar2, bar3, bar4)
    }
}
