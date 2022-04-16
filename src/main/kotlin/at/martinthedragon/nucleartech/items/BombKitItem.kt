package at.martinthedragon.nucleartech.items

import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.Level
import java.util.function.Supplier
import kotlin.math.min

class BombKitItem(
    val items: Map<out Supplier<out ItemLike>, Int>,
    val hazmatTier: Int,
    val color: Int,
    properties: Properties
) : Item(properties.stacksTo(1)) {
    init {
        allKits += this
    }

    override fun use(world: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand).copy()
        stack.shrink(1)
        if (world.isClientSide) {
            player.playSound(SoundEvents.randomUnpack.get(), 1F, 1F)
            return InteractionResultHolder.success(stack)
        }

        if (!player.isCreative) giveHazmat(player)

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
                        net.minecraft.sounds.SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS,
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
        return InteractionResultHolder.consume(stack)
    }

    private fun giveHazmat(player: Player) {
        when (hazmatTier) {
            0 -> {
                player.setItemSlot(EquipmentSlot.HEAD, ModItems.hazmatHelmet.get().defaultInstance)
                player.setItemSlot(EquipmentSlot.CHEST, ModItems.hazmatChestplate.get().defaultInstance)
                player.setItemSlot(EquipmentSlot.LEGS, ModItems.hazmatLeggings.get().defaultInstance)
                player.setItemSlot(EquipmentSlot.FEET, ModItems.hazmatBoots.get().defaultInstance)
            }
            1 -> {
                player.setItemSlot(EquipmentSlot.HEAD, ModItems.advancedHazmatHelmet.get().defaultInstance)
                player.setItemSlot(EquipmentSlot.CHEST, ModItems.advancedHazmatChestplate.get().defaultInstance)
                player.setItemSlot(EquipmentSlot.LEGS, ModItems.advancedHazmatLeggings.get().defaultInstance)
                player.setItemSlot(EquipmentSlot.FEET, ModItems.advancedHazmatBoots.get().defaultInstance)
            }
            2 -> {
                player.setItemSlot(EquipmentSlot.HEAD, ModItems.reinforcedHazmatHelmet.get().defaultInstance)
                player.setItemSlot(EquipmentSlot.CHEST, ModItems.reinforcedHazmatChestplate.get().defaultInstance)
                player.setItemSlot(EquipmentSlot.LEGS, ModItems.reinforcedHazmatLeggings.get().defaultInstance)
                player.setItemSlot(EquipmentSlot.FEET, ModItems.reinforcedHazmatBoots.get().defaultInstance)
            }
        }
    }

    companion object {
        val allKits = mutableListOf<BombKitItem>()
    }
}
