package at.martinthedragon.nucleartech.items

import at.martinthedragon.nucleartech.extensions.getItems
import at.martinthedragon.nucleartech.extensions.toStupidMojangList
import net.minecraft.sounds.SoundSource
import net.minecraft.world.Container
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.ItemHandlerHelper
import net.minecraftforge.items.ItemStackHandler
import kotlin.math.min

fun canTransferItem(from: ItemStack, to: ItemStack, inventory: Container? = null): Boolean = when {
    from.isEmpty -> false
    to.isEmpty -> true
    !to.sameItem(from) -> false
    else -> to.count + from.count <= (inventory?.maxStackSize ?: 64) && to.count + from.count <= to.maxStackSize
}

inline fun transferItemsBetweenItemHandlers(first: IItemHandler, second: IItemHandler, amount: Int = Int.MAX_VALUE, invert: Boolean = false, filter: (ItemStack) -> Boolean = { true }) {
    val from: IItemHandler
    val to: IItemHandler
    if (invert) {
        from = second
        to = first
    } else {
        from = first
        to = second
    }

    var remaining = amount
    for (originSlot in 0 until from.slots) {
        val impostorStack = from.extractItem(originSlot, remaining, true)
        if (impostorStack == ItemStack.EMPTY || !filter(impostorStack)) continue
        val originalCount = impostorStack.count
        for (destinationSlot in 0 until to.slots) {
            if (!to.isItemValid(destinationSlot, impostorStack)) continue
            if (remaining <= 0) return
            val remainingCount = to.insertItem(destinationSlot, impostorStack, true).count
            if (remainingCount < originalCount) {
                remaining -= originalCount - to.insertItem(destinationSlot, from.extractItem(originSlot, originalCount - remainingCount, false), false).count
            }
        }
    }
}

// from give command, use server-side only
fun giveItemToInventory(player: Player, item: ItemLike, amount: Int) {
    var amountLeft = amount
    while (true) {
        if (amountLeft <= 0) break

        val newStack = ItemStack(item, amount)
        val possibleAmount = min(newStack.maxStackSize, amountLeft)
        newStack.count = possibleAmount
        ItemHandlerHelper.giveItemToPlayer(player, newStack)

        amountLeft -= possibleAmount
    }
}

@Deprecated("This util method already exists in ItemHandlerHelper", ReplaceWith("ItemHandlerHelper.giveItemToPlayer(player, stack)", "net.minecraftforge.items.ItemHandlerHelper"))
fun giveItemToInventory(player: Player, stack: ItemStack) {
    val successful = player.inventory.add(stack)
    if (successful && stack.isEmpty) {
        stack.count = 1
        player.drop(stack, false)?.makeFakeItem()
        // cannot use player.playSound here because we already checked for server
        player.level.playSound(
            null, player.x, player.y, player.z,
            net.minecraft.sounds.SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS,
            .2F, ((player.random.nextFloat() - player.random.nextFloat()) * .7F + 1F) * 2F
        )
        player.inventoryMenu.broadcastChanges()
    } else {
        val droppedStack = player.drop(stack, false)
        if (droppedStack != null) {
            droppedStack.setNoPickUpDelay()
            droppedStack.owner = player.uuid
        }
    }
}

fun insertAllItemsStacked(inventory: IItemHandler, stacks: Collection<ItemStack>, simulate: Boolean): List<ItemStack> {
    if (stacks.all(ItemStack::isEmpty)) return emptyList()

    if (simulate) {
        val handler = ItemStackHandler(inventory.getItems().toStupidMojangList())
        val remainingStacks = mutableListOf<ItemStack>()
        for (stack in stacks) {
            val remainder = ItemHandlerHelper.insertItemStacked(handler, stack.copy(), false)
            if (!remainder.isEmpty) remainingStacks += remainder
        }
        return remainingStacks
    } else {
        val remainingStacks = mutableListOf<ItemStack>()
        for (stack in stacks) {
            val remainder = ItemHandlerHelper.insertItemStacked(inventory, stack.copy(), false)
            if (!remainder.isEmpty) remainingStacks += remainder
        }
        return remainingStacks
    }
}
