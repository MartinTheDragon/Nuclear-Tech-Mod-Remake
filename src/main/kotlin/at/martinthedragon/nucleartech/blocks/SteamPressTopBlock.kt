package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.blocks.entities.BlockEntityTypes
import at.martinthedragon.nucleartech.blocks.entities.SteamPressBlockEntity
import at.martinthedragon.nucleartech.blocks.entities.createServerTickerChecked
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class SteamPressTopBlock(properties: Properties) : BaseEntityBlock(properties) {
    override fun getShape(state: BlockState, worldIn: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape = topShape
    override fun getInteractionShape(state: BlockState, worldIn: BlockGetter, pos: BlockPos): VoxelShape = topShape
    override fun isPathfindable(state: BlockState, worldIn: BlockGetter, pos: BlockPos, type: PathComputationType) = false

    override fun getRenderShape(state: BlockState) = RenderShape.MODEL

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult) = openMenu<SteamPressBlockEntity>(level, pos, player)

    // code for dropping is in SteamPressBase
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
        val blockPos2 = blockPos1.below()

        if (level.getBlockState(blockPos2).block == ModBlocks.steamPressBase.get()) level.destroyBlock(blockPos2, drop)
        if (level.getBlockState(blockPos1).block == ModBlocks.steamPressFrame.get()) level.destroyBlock(blockPos1, false)
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = SteamPressBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = if (level.isClientSide) null else createServerTickerChecked(type, BlockEntityTypes.steamPressHeadBlockEntityType.get())

    companion object {
        private val halfBox: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)
        private val topThing: VoxelShape = box(4.0, 8.0, 4.0, 12.0, 16.0, 12.0)
        val topShape: VoxelShape = Shapes.or(halfBox, topThing)
    }
}
