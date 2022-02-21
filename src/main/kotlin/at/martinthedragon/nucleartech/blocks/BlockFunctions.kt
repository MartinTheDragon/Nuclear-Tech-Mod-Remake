package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.blocks.multi.MultiBlockPart
import at.martinthedragon.nucleartech.blocks.multi.MultiBlockPlacer
import at.martinthedragon.nucleartech.blocks.multi.RotatedMultiBlockPlacer
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.Containers
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.network.NetworkHooks
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Tailored for custom naming by [net.minecraft.world.level.block.Block.setPlacedBy]
 *
 * Sets the [customName][BaseContainerBlockEntity.setCustomName] of the BaseContainerBlockEntity [T] if the [ItemStack's][stack]
 * name is a [custom hover name][ItemStack.hasCustomHoverName].
 */
inline fun <reified T : BaseContainerBlockEntity> setBlockEntityCustomName(
    world: Level,
    pos: BlockPos,
    stack: ItemStack
) {
    if (stack.hasCustomHoverName()) {
        val blockEntity = world.getBlockEntity(pos)
        if (blockEntity is T) blockEntity.customName = stack.hoverName
    }
}

/**
 * Tailored for dropping inventories by [net.minecraft.world.level.block.Block.onRemove]
 *
 * Only drops the content when the Block was actually changed and returns `true` if so.
 *
 * The [also] function can be used to perform additional operations after the random were dropped.
 */
@OptIn(ExperimentalContracts::class)
inline fun <reified T : BaseContainerBlockEntity> dropBlockEntityContents(
    state: BlockState,
    level: Level,
    pos: BlockPos,
    newState: BlockState,
    also: (blockEntity: T) -> Unit = {}
): Boolean {
    contract {
        callsInPlace(also, InvocationKind.AT_MOST_ONCE)
    }

    if (!state.`is`(newState.block)) {
        val blockEntity = level.getBlockEntity(pos)
        if (blockEntity is T) {
            Containers.dropContents(level, pos, blockEntity)
            also(blockEntity)
            return true
        }
    }
    return false
}

@OptIn(ExperimentalContracts::class)
inline fun <reified T : BaseContainerBlockEntity> dropMultiBlockEntityContentsAndRemoveStructure(
    state: BlockState,
    level: Level,
    pos: BlockPos,
    newState: BlockState,
    placerFunc: (MultiBlockPlacer) -> Unit,
    also: (blockEntity: T) -> Unit = {}
): Boolean {
    contract {
        callsInPlace(placerFunc, InvocationKind.AT_MOST_ONCE)
        callsInPlace(also, InvocationKind.AT_MOST_ONCE)
    }

    if (!state.`is`(newState.block)) {
        val placer = RotatedMultiBlockPlacer(if (state.hasProperty(HorizontalDirectionalBlock.FACING)) state.getValue(HorizontalDirectionalBlock.FACING) else Direction.NORTH)
        placerFunc(placer)
        placer.getPlacementData().keys.forEach {
            val partPos = it.offset(pos)
            if (level.getBlockState(partPos).block is MultiBlockPart)
                level.destroyBlock(partPos, false)
        }

        val blockEntity = level.getBlockEntity(pos)
        if (blockEntity is T) {
            Containers.dropContents(level, pos, blockEntity)
            also(blockEntity)
            return true
        }
    }
    return false
}

inline fun <reified T : BaseContainerBlockEntity> openMenu(level: Level, pos: BlockPos, player: Player): InteractionResult {
    if (!level.isClientSide) {
        val blockEntity = level.getBlockEntity(pos)
        if (blockEntity is T) NetworkHooks.openGui(player as ServerPlayer, blockEntity, pos)
    }
    return InteractionResult.sidedSuccess(level.isClientSide)
}

inline fun <reified T, reified P> openMultiBlockMenu(level: Level, pos: BlockPos, player: Player): InteractionResult
    where T : BaseContainerBlockEntity,
          P : MultiBlockPart.MultiBlockPartBlockEntity
{
    if (!level.isClientSide) {
        val part = level.getBlockEntity(pos)
        if (part is P) {
            val blockEntity = level.getBlockEntity(part.core)
            if (blockEntity is T) NetworkHooks.openGui(player as ServerPlayer, blockEntity, part.core)
        }
    }
    return InteractionResult.sidedSuccess(level.isClientSide)
}
