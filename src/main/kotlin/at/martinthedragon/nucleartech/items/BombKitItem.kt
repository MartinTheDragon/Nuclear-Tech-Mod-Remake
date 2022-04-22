package at.martinthedragon.nucleartech.items

import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.SoundEvents
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.Level
import java.util.function.Supplier

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

        for ((itemSupplier, amount) in items) giveItemToInventory(player, itemSupplier.get(), amount)
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
