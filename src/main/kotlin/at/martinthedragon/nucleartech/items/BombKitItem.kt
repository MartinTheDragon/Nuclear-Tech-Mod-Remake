package at.martinthedragon.nucleartech.items

import at.martinthedragon.nucleartech.SoundEvents
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.IItemProvider
import net.minecraft.util.SoundCategory
import net.minecraft.world.World
import java.util.function.Supplier
import kotlin.math.min
import net.minecraft.util.SoundEvents as MinecraftSounds

class BombKitItem(
    val items: Map<out Supplier<out IItemProvider>, Int>,
    val color: Int,
    properties: Properties
) : Item(properties) {
    init {
        allKits += this
    }

    override fun use(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        val stack = player.getItemInHand(hand).copy()
        stack.shrink(1)
        if (world.isClientSide) {
            player.playSound(SoundEvents.randomUnpack.get(), 1F, 1F)
            return ActionResult.success(stack)
        }

        for ((itemSupplier, amount) in items) {
            val item = itemSupplier.get()
            var amountLeft = amount
            while (true) {
                if (amountLeft <= 0) break

                @Suppress("DEPRECATION")
                val possibleAmount = min(item.asItem().maxStackSize, amountLeft)
                val newStack = ItemStack(item, possibleAmount)
                val successful = player.inventory.add(newStack)
                if (successful && newStack.isEmpty) {
                    newStack.count = 1
                    player.drop(newStack, false)?.makeFakeItem()
                    // cannot use player.playSound here because we already checked for server
                    player.level.playSound(null, player.x, player.y, player.z,
                        MinecraftSounds.ITEM_PICKUP, SoundCategory.PLAYERS,
                        .2F, ((player.random.nextFloat() - player.random.nextFloat()) * .7F + 1F) * 2F
                    )
                    player.inventoryMenu.broadcastChanges()
                } else {
                    val droppedStack = player.drop(newStack, false)
                    if (droppedStack != null) {
                        droppedStack.setNoPickUpDelay()
                        droppedStack.owner = player.uuid
                    }
                }

                amountLeft -= possibleAmount
            }
        }
        return ActionResult.consume(stack)
    }

    companion object {
        val allKits = mutableListOf<BombKitItem>()
    }
}
