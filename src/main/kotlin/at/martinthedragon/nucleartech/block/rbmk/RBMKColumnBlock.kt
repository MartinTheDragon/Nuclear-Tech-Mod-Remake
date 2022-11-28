package at.martinthedragon.nucleartech.block.rbmk

import at.martinthedragon.nucleartech.block.entity.rbmk.InventoryRBMKBaseBlockEntity
import at.martinthedragon.nucleartech.block.openMenu
import at.martinthedragon.nucleartech.config.NuclearConfig
import net.minecraft.client.particle.ParticleEngine
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.BlockHitResult
import net.minecraftforge.client.IBlockRenderProperties
import java.util.function.Consumer

open class RBMKColumnBlock(properties: Properties) : Block(properties), RBMKPart {
    init { registerDefaultState(stateDefinition.any().setValue(LEVEL, 1)) }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) { builder.add(LEVEL) }

    override fun isPathfindable(state: BlockState, level: BlockGetter, pos: BlockPos, path: PathComputationType) = false
    override fun getRenderShape(state: BlockState) = RenderShape.INVISIBLE
    override fun getShadeBrightness(state: BlockState, level: BlockGetter, pos: BlockPos) = 1F

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, piston: Boolean) {
        destroyTopBlock(level, pos, state, false)
        removeAdjacent(level, pos.above())
        removeAdjacent(level, pos.below())
        @Suppress("DEPRECATION") super.onRemove(state, level, pos, newState, piston)
    }

    override fun playerDestroy(level: Level, player: Player, pos: BlockPos, state: BlockState, blockEntity: BlockEntity?, itemInHand: ItemStack) {
        destroyTopBlock(level, pos, state, true)
        super.playerDestroy(level, player, pos, state, blockEntity, itemInHand)
    }

    private fun removeAdjacent(level: Level, pos: BlockPos) {
        if (level.getBlockState(pos).block is RBMKColumnBlock) level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState())
    }

    private fun destroyTopBlock(level: Level, pos: BlockPos, state: BlockState, drops: Boolean) {
        val topPos = pos.offset(0, state.getValue(LEVEL), 0)
        if (level.getBlockState(topPos).block is RBMKBaseBlock)
            level.destroyBlock(topPos, drops)
    }

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult): InteractionResult {
        return openMenu<InventoryRBMKBaseBlockEntity>(level, pos.offset(0, state.getValue(LEVEL), 0), player)
    }

    override fun initializeClient(consumer: Consumer<IBlockRenderProperties>) {
        consumer.accept(object : IBlockRenderProperties {
            override fun addDestroyEffects(state: BlockState?, Level: Level?, pos: BlockPos?, manager: ParticleEngine?): Boolean {
                return true // prevent particles
            }
        })
    }

    override fun getTopBlockPos(level: Level, queryPos: BlockPos, state: BlockState): BlockPos =
        queryPos.offset(0, state.getValue(LEVEL), 0)

    companion object {
        val LEVEL: IntegerProperty = IntegerProperty.create("level", 1, 15)

        fun getPlacementHeight(): Int = NuclearConfig.rbmk.columnHeight.get()
    }
}
