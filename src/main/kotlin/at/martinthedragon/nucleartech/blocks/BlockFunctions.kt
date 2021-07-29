package at.martinthedragon.nucleartech.blocks

import net.minecraft.block.BlockState
import net.minecraft.inventory.InventoryHelper
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.LockableTileEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Tailored for custom naming by [net.minecraft.block.Block.setPlacedBy]
 *
 * Sets the [customName][LockableTileEntity.setCustomName] of the LockableTileEntity [T] if the [ItemStack's][stack]
 * name is a [custom hover name][ItemStack.hasCustomHoverName].
 */
inline fun <reified T : LockableTileEntity> setTileEntityCustomName(
    world: World,
    pos: BlockPos,
    stack: ItemStack
) {
    if (stack.hasCustomHoverName()) {
        val tileEntity = world.getBlockEntity(pos)
        if (tileEntity is T) tileEntity.customName = stack.hoverName
    }
}

/**
 * Tailored for dropping inventories by [net.minecraft.block.Block.onRemove]
 *
 * Only drops the content when the Block was actually changed and returns `true` if so.
 *
 * The [also] function can be used to perform additional operations after the random were dropped.
 */
@OptIn(ExperimentalContracts::class)
inline fun <reified T : LockableTileEntity> dropTileEntityContents(
    state: BlockState,
    world: World,
    pos: BlockPos,
    newState: BlockState,
    also: (tileEntity: T) -> Unit = {}
): Boolean {
    contract {
        callsInPlace(also, InvocationKind.AT_MOST_ONCE)
    }

    if (!state.`is`(newState.block)) {
        val tileEntity = world.getBlockEntity(pos)
        if (tileEntity is T) {
            InventoryHelper.dropContents(world, pos, tileEntity)
            also(tileEntity)
            return true
        }
    }
    return false
}
