package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.block.rbmk.RBMKBaseBlock
import at.martinthedragon.nucleartech.block.rbmk.RBMKColumnBlock
import at.martinthedragon.nucleartech.rendering.CustomBEWLR
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.gameevent.GameEvent
import net.minecraftforge.client.IItemRenderProperties
import java.util.function.Consumer
import java.util.function.Supplier

open class RBMKColumnBlockItem(topBlock: RBMKBaseBlock, override val blockEntityFunc: (pos: BlockPos, state: BlockState) -> BlockEntity, properties: Properties) : BlockItem(topBlock, properties), SpecialRenderingBlockEntityItem {
    override val blockStateForRendering: BlockState get() = super.getBlock().defaultBlockState()

    private val columnBlock: Supplier<out RBMKColumnBlock> = topBlock.column

    override fun place(context: BlockPlaceContext): InteractionResult {
        val height = RBMKColumnBlock.getPlacementHeight().coerceAtLeast(0)

        val level = context.level
        val pos = context.clickedPos
        val player = context.player
        val itemStack = context.itemInHand

        val placementStates = mutableMapOf<BlockPlaceContext, BlockState>()

        // check if possible
        for (i in 0 until height) {
            val nextContext = BlockPlaceContext.at(context, pos.offset(0, i, 0), Direction.DOWN)
            if (!nextContext.replacingClickedOnBlock()) return InteractionResult.FAIL

            val stateToPlace = (if (i + 1 == height) block.getStateForPlacement(nextContext) else getPlacementState(nextContext, height - i - 1, height - 1)) ?: return InteractionResult.FAIL
            if (!canPlace(nextContext, stateToPlace)) return InteractionResult.FAIL
            else placementStates[nextContext] = stateToPlace
        }

        // actually place
        placementStates.entries.forEachIndexed { index, (nextContext, blockState) ->
            if (!placeBlock(nextContext, blockState)) {
                // if unsuccessful, undo placements
                for (undoContext in placementStates.keys.take(index))
                    undoContext.level.setBlockAndUpdate(undoContext.clickedPos, Blocks.AIR.defaultBlockState())
                return InteractionResult.FAIL
            }

            val nextLevel = nextContext.level
            val nextPos = nextContext.clickedPos

            val placedBlockState = nextLevel.getBlockState(nextPos)
            if (placedBlockState.`is`(blockState.block)) {
                placedBlockState.block.setPlacedBy(nextLevel, nextPos, placedBlockState, player, itemStack)
                if (player is ServerPlayer) {
                    CriteriaTriggers.PLACED_BLOCK.trigger(player, nextPos, itemStack)
                }
            }
            nextLevel.gameEvent(player, GameEvent.BLOCK_PLACE, nextPos)
        }

        val anyPlacedState = placementStates.values.first()
        val sound = anyPlacedState.getSoundType(level, pos, player)
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        level.playSound(player, pos, getPlaceSound(anyPlacedState, level, pos, player), SoundSource.BLOCKS, (sound.volume + 1F) / 2F, sound.pitch * 0.8F)
        if (player == null || !player.abilities.instabuild)
            itemStack.shrink(1)

        return InteractionResult.sidedSuccess(level.isClientSide)
    }

    final override fun getPlacementState(context: BlockPlaceContext): BlockState? {
        throw UnsupportedOperationException()
    }

    open fun getPlacementState(context: BlockPlaceContext, offset: Int, maxOffset: Int): BlockState? = columnBlock.get().getStateForPlacement(context)?.setValue(RBMKColumnBlock.LEVEL, offset)

    override fun registerBlocks(blockToItemMap: MutableMap<Block, Item>, itemIn: Item) {
        super.registerBlocks(blockToItemMap, itemIn)
        blockToItemMap[columnBlock.get()] = itemIn
    }

    override fun removeFromBlockToItemMap(blockToItemMap: MutableMap<Block, Item>, itemIn: Item) {
        super.removeFromBlockToItemMap(blockToItemMap, itemIn)
        blockToItemMap.remove(columnBlock.get())
    }

    override fun initializeClient(consumer: Consumer<IItemRenderProperties>) {
        consumer.accept(CustomBEWLR.DefaultRenderProperties)
    }
}
