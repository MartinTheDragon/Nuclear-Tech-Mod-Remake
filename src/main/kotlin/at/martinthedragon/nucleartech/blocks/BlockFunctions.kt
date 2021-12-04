package at.martinthedragon.nucleartech.blocks

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.Containers
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
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
inline fun <reified T : BaseContainerBlockEntity> setTileEntityCustomName(
    world: Level,
    pos: BlockPos,
    stack: ItemStack
) {
    if (stack.hasCustomHoverName()) {
        val tileEntity = world.getBlockEntity(pos)
        if (tileEntity is T) tileEntity.customName = stack.hoverName
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
inline fun <reified T : BaseContainerBlockEntity> dropTileEntityContents(
    state: BlockState,
    level: Level,
    pos: BlockPos,
    newState: BlockState,
    also: (tileEntity: T) -> Unit = {}
): Boolean {
    contract {
        callsInPlace(also, InvocationKind.AT_MOST_ONCE)
    }

    if (!state.`is`(newState.block)) {
        val tileEntity = level.getBlockEntity(pos)
        if (tileEntity is T) {
            Containers.dropContents(level, pos, tileEntity)
            also(tileEntity)
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
