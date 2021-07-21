package at.martinthedragon.nucleartech.containers.slots

import at.martinthedragon.nucleartech.tileentities.ExperienceRecipeResultTileEntity
import at.martinthedragon.nucleartech.tileentities.dropExperienceAndAwardRecipes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler
import kotlin.math.min

open class ExperienceResultSlot(
    val tileEntity: ExperienceRecipeResultTileEntity,
    val player: PlayerEntity,
    inventory: IItemHandler,
    index: Int,
    xPosition: Int, yPosition: Int
) : SlotItemHandler(inventory, index, xPosition, yPosition) {
    var removeCount = 0

    final override fun mayPlace(stack: ItemStack) = false

    override fun remove(amount: Int): ItemStack {
        if (hasItem()) removeCount += min(amount, item.count)
        return super.remove(amount)
    }

    override fun onTake(player: PlayerEntity, itemStack: ItemStack): ItemStack {
        checkTakeAchievements(itemStack)
        return super.onTake(player, itemStack)
    }

    override fun onQuickCraft(itemStack: ItemStack, amount: Int) {
        removeCount += amount
        checkTakeAchievements(itemStack)
    }

    override fun checkTakeAchievements(itemStack: ItemStack) {
        itemStack.onCraftedBy(player.level, player, removeCount)
        if (!player.level.isClientSide)
            tileEntity.dropExperienceAndAwardRecipes(player)
        removeCount = 0
    }
}
